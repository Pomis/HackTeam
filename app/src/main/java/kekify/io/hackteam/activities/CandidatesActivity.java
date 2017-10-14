package kekify.io.hackteam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kekify.io.hackteam.R;

public class CandidatesActivity extends AppCompatActivity {

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.candidatesView)
    PlaceHolderView candidatesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates);
        ButterKnife.bind(this);

        candidatesView.getBuilder().setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

}
