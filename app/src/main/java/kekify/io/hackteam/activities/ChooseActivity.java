package kekify.io.hackteam.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kekify.io.hackteam.App;
import kekify.io.hackteam.DataRepository;
import kekify.io.hackteam.R;
import kekify.io.hackteam.RxUtils;
import kekify.io.hackteam.models.CandidatesItem;
import kekify.io.hackteam.models.Project;
import kekify.io.hackteam.models.RoleItem;
import kekify.io.hackteam.models.TeamModel;
import kekify.io.hackteam.models.User;

import static kekify.io.hackteam.activities.ChooseActivity.State.CHOOSE;
import static kekify.io.hackteam.activities.ChooseActivity.State.IDEA;
import static kekify.io.hackteam.activities.ChooseActivity.State.ROLES;
import static kekify.io.hackteam.activities.ChooseActivity.State.SOLO;

public class ChooseActivity extends AppCompatActivity {
    @BindView(R.id.met_idea)
    MaterialEditText metIdea;
    @BindView(R.id.ll_idea_first)
    LinearLayout llIdeaFirst;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.rl_solo)
    RelativeLayout rlSolo;
    @BindView(R.id.rl_group)
    RelativeLayout rlGroup;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.tv_soon)
    TextView tvSoon;
    @BindView(R.id.b_next_step)
    Button bNextStep;
    @BindView(R.id.phv_roles)
    PlaceHolderView phvRoles;
    @BindView(R.id.ll_idea_second)
    LinearLayout llIdeaSecond;
    @BindView(R.id.phv_invites)
    PlaceHolderView phvInvites;


    enum State {
        CHOOSE, SOLO, IDEA, ROLES
    }

    State currentState = CHOOSE;
    ArrayList<String> roles = new ArrayList<>();


    public static void start(Context context) {
        Intent starter = new Intent(context, ChooseActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
        initList();
    }

    @OnClick(R.id.rl_solo)
    void solo() {
        currentState = SOLO;
        moveAway(tvLabel, 150);
        moveAway(rlSolo, 50);
        moveAway(rlGroup, 0);

        TeamModel model = new TeamModel(R.drawable.avatar2, this, phvInvites, "Tony Mantana's Team",
                "I want to do Car-sharing Real Time Mobile App for the Berlin reality."
        );
        DataRepository repository = new DataRepository();
        repository.getInvitations(App.getAppInstance().getPreferencesWrapper().getId())
                .compose(RxUtils.applySingleSchedulers())
                .subscribe(list -> {
                    if (list != null && list.size() > 0) {
                        model.setRoles(list.get(0).role);
                        model.setDescription(list.get(0).project.getDescription());
                        ArrayList<TeamModel> invites = new ArrayList<>();
                        invites.add(model);
                        moveToFront(phvInvites, 0);
                        phvInvites.addView(invites.get(0));
                    } else {
                        moveToFront(tvEmpty, 150);
                        moveToFront(tvSoon, 100);
                    }
                });


    }

    @OnClick(R.id.rl_group)
    void group() {
        currentState = IDEA;
        metIdea.setEnabled(true);
        metIdea.setVisibility(View.VISIBLE);
        moveAway(tvLabel, 150);
        moveAway(rlSolo, 50);
        moveAway(rlGroup, 0);

        moveToFront(llIdeaFirst, 0);
        moveToFront(bNextStep, 50);
    }

    @OnClick(R.id.b_next_step)
    void nextStep() {
        switch (currentState) {
            case IDEA:
                currentState = ROLES;
                moveAway(llIdeaFirst, 0);
                moveToFront(llIdeaSecond, 0);
                break;

            case ROLES:
                Project project = new Project(metIdea.getText().toString(), roles);

                int id = App.getAppInstance().getPreferencesWrapper().getId();
                DataRepository repository = new DataRepository();
                String access_token = App.getAppInstance().getPreferencesWrapper().getAuthToken("twist");

                repository.createProject(project, id)
                        .compose(RxUtils.applySingleSchedulers())
                        .subscribe(projectId -> {
                            System.out.println("Set projectId" + project);
                            App.getAppInstance().getPreferencesWrapper().setProjectId(projectId);

                            repository.addWorkspace(access_token, "my_workspace" + new Random().nextInt(1000))
                                    .compose(RxUtils.applySingleSchedulers())
                                    .subscribe(workspace -> {
                                        App.getAppInstance().getPreferencesWrapper().setWorkspaceId(workspace.getId());
                                        System.out.println("Set workspace id" + workspace.getId());
                                        CandidatesActivity.start(this, roles);
                                    }, error -> {
                                        error.printStackTrace();
                                    });
                        }, error -> {
                            error.printStackTrace();
                            Toast.makeText(this.getApplicationContext(),
                                    "Something gone wrong!", Toast.LENGTH_LONG).show();
                        });


                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (currentState) {
            case SOLO:
                moveToFront(tvLabel, 150);
                moveToFront(rlSolo, 50);
                moveToFront(rlGroup, 0);


                moveAway(tvEmpty, 150);
                moveAway(tvSoon, 100);
                moveAway(phvInvites, 0);

                currentState = CHOOSE;
                break;
        }
    }

    void moveAway(View view, int delay) {
        view.animate()
                .translationX(-1500).setDuration(500)
                .alpha(0).setStartDelay(delay)
                .setInterpolator(new FastOutLinearInInterpolator()).start();
    }

    void moveToFront(View view, int delay) {
        view.animate()
                .translationX(0).setDuration(500)
                .alpha(1).setStartDelay(delay)
                .setInterpolator(new FastOutSlowInInterpolator()).start();
    }


    void initList() {
        phvRoles.getBuilder().setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
        phvRoles.addView(new RoleItem("Team manager", this, roles));
        phvRoles.addView(new RoleItem("iOS dev", this, roles));
        phvRoles.addView(new RoleItem("Front-end dev", this, roles));
        phvRoles.addView(new RoleItem("Designer", this, roles));
        phvRoles.addView(new RoleItem("Analyst", this, roles));
        phvRoles.addView(new RoleItem("Team supporter", this, roles));
        phvRoles.addView(new RoleItem("Android dev", this, roles));
        phvRoles.addView(new RoleItem("Back-end dev", this, roles));
        phvRoles.addView(new RoleItem("ML/DL dev", this, roles));
        phvRoles.addView(new RoleItem("Blockchain dev", this, roles));
    }

}
