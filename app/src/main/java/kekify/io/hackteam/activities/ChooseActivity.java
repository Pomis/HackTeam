package kekify.io.hackteam.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kekify.io.hackteam.R;
import kekify.io.hackteam.models.Project;
import kekify.io.hackteam.models.RoleItem;

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

        moveToFront(tvEmpty, 150);
        moveToFront(tvSoon, 100);

    }

    @OnClick(R.id.rl_group)
    void group() {
        currentState = IDEA;

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
                Project project = new Project(
                        metIdea.getText().toString(),
                        roles
                );
                CandidatesActivity.start(this);
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
