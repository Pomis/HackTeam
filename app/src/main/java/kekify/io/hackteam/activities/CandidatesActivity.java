package kekify.io.hackteam.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kekify.io.hackteam.App;
import kekify.io.hackteam.DataRepository;
import kekify.io.hackteam.R;
import kekify.io.hackteam.RxUtils;
import kekify.io.hackteam.models.CandidatesItem;
import kekify.io.hackteam.models.User;

public class CandidatesActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    MaterialSpinner msRoles;
    @BindView(R.id.candidatesView)
    PlaceHolderView candidatesView;
    @BindView(R.id.tv_pages)
    TextView tvPages;

    LinearLayoutManager lm;

    private String selected;

    public static void start(Context context, ArrayList<String> roles) {
        Intent starter = new Intent(context, CandidatesActivity.class);
        starter.putExtra("arr", roles);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates);
        ButterKnife.bind(this);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(candidatesView);

        initSpinner();
        readIntent();
        lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        candidatesView.getBuilder().setLayoutManager(lm);

        updatePageCounter();
        candidatesView.getBuilder().setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        candidatesView.addView(new CandidatesItem(R.drawable.avatar, this.getApplicationContext(),
//                candidatesView, "Djavid", "Java;Gradle;Retrofit", "Android dev"));
//        candidatesView.addView(new CandidatesItem(R.drawable.avatar2, this.getApplicationContext(),
//                candidatesView, "Aleksandr", "Sketch;Zeplin", "Designer"));

    }

    private void readIntent() {

    }

    void initSpinner() {
//        msRoles.setItems(getIntent().getStringArrayListExtra("arr") != null ?
//                getIntent().getStringArrayListExtra("arr"):
//                "Android dev", "iOS dev", "Designer");

        msRoles.setItems(getIntent().getStringArrayListExtra("arr"));
        selected = getIntent().getStringArrayListExtra("arr").get(0);
        getCandidates();

        msRoles.setOnItemSelectedListener((view, position, id, item) -> {
            Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
            selected = item.toString();
            candidatesView.removeAllViews();
            getCandidates();
        });
    }

    void updatePageCounter() {
        candidatesView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (lm.findFirstCompletelyVisibleItemPosition() >= 0)
                    tvPages.setText((lm.findFirstCompletelyVisibleItemPosition() + 1)
                            + "/" + candidatesView.getAdapter().getItemCount());
            }
        });

    }

    public void getCandidates() {
        DataRepository repository = new DataRepository();
        int projectId = App.getAppInstance().getPreferencesWrapper().getProjectId();

        repository.searchCandidates(projectId)
                .compose(RxUtils.applySingleSchedulers())
                .subscribe(list -> {
                    for (User user: list) {
                        //System.out.print(" (1)(" + user.getName() + " " + user.getId() + ") ");
                        for (String role: user.getRoles()) {
                            //System.out.print(" (2)(" + role + ") ");
                            if (role.equals(selected)) {
                                CandidatesItem item = new CandidatesItem(this.getApplicationContext(),
                                        candidatesView, user.getName(), user.getSkills(),
                                        user.getRoles(), user.getId(), selected);

                                candidatesView.addView(item);

                                break;
                            }
                        }
                    }
                });
    }

}
