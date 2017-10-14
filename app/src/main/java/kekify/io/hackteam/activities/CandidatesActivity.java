package kekify.io.hackteam.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mindorks.placeholderview.PlaceHolderView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kekify.io.hackteam.R;
import kekify.io.hackteam.models.CandidatesItem;

public class CandidatesActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    MaterialSpinner msRoles;
    @BindView(R.id.b_send)
    Button bSend;
    @BindView(R.id.candidatesView)
    PlaceHolderView candidatesView;

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
        candidatesView.getBuilder().setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        candidatesView.addView(new CandidatesItem(R.drawable.avatar, this.getApplicationContext(),
                candidatesView, "Djavid", "Khalilov", "Java;Gradle;Retrofit", "Android dev"));
        candidatesView.addView(new CandidatesItem(R.drawable.avatar2, this.getApplicationContext(),
                candidatesView, "Aleksandr", "Naumov", "Sketch;Zeplin", "Designer"));

    }

    private void readIntent() {

    }

    void initSpinner() {
//        msRoles.setItems(getIntent().getStringArrayListExtra("arr") != null ?
//                getIntent().getStringArrayListExtra("arr"):
//                "Android dev", "iOS dev", "Designer");
        msRoles.setItems(getIntent().getStringArrayListExtra("arr"));
        msRoles.setOnItemSelectedListener(
                (view, position, id, item) ->  {});
    }

}
