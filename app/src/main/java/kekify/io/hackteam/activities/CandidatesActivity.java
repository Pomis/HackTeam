package kekify.io.hackteam.activities;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kekify.io.hackteam.R;
import kekify.io.hackteam.models.CandidatesItem;

public class CandidatesActivity extends AppCompatActivity {

    @BindView(R.id.candidatesView)
    PlaceHolderView candidatesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates);
        ButterKnife.bind(this);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(candidatesView);

        candidatesView.getBuilder().setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        candidatesView.addView(new CandidatesItem(R.drawable.avatar, this.getApplicationContext(),
                candidatesView, "Djavid", "Khalilov"));
        candidatesView.addView(new CandidatesItem(R.drawable.avatar2, this.getApplicationContext(),
                candidatesView, "Aleksandr", "Naumov"));

    }

}
