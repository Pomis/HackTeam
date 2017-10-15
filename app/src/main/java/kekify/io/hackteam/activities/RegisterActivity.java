package kekify.io.hackteam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kekify.io.hackteam.App;
import kekify.io.hackteam.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (App.getAppInstance().getPreferencesWrapper().getAuthToken("twist").isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        if (App.getAppInstance().getPreferencesWrapper().getId() != -1) {
            ChooseActivity.start(this);
        }
    }
}
