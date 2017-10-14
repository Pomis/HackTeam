package kekify.io.hackteam.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kekify.io.hackteam.R;

public class ChooseActivity extends AppCompatActivity {

    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.rl_solo)
    RelativeLayout rlSolo;
    @BindView(R.id.rl_group)
    RelativeLayout rlGroup;

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
        rlSolo.animate()
                .x(-500).setDuration(500)
                //.alpha(0)
                .setInterpolator(new FastOutLinearInInterpolator()).start();

    }
}
