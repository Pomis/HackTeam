package kekify.io.hackteam.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kekify.io.hackteam.R;

import static kekify.io.hackteam.activities.ChooseActivity.State.CHOOSE;
import static kekify.io.hackteam.activities.ChooseActivity.State.SOLO;

public class ChooseActivity extends AppCompatActivity {
    enum State{
        CHOOSE, SOLO, GROUP
    }
    State currentState = CHOOSE;

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

    public static void start(Context context) {
        Intent starter = new Intent(context, ChooseActivity.class);
        context.startActivity(starter);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
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
        CandidatesActivity.start(this);
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
}
