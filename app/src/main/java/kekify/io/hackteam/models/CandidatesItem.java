package kekify.io.hackteam.models;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.io.File;

import kekify.io.hackteam.R;

/**
 * Created by djavid on 14.10.17.
 */


//@Animate(Animation.CARD_LEFT_IN_DESC)
@NonReusable
@Layout(R.layout.candidates_item)
public class CandidatesItem {
    @View(R.id.iv_avatar)
    private ImageView iv_avatar;

    @View(R.id.tv_name)
    private TextView tv_name;

    @View(R.id.phv_roles)
    private PlaceHolderView phvRoles;

    @View(R.id.phv_skills)
    private PlaceHolderView phvSkills;

    private int avatarUrl;
    private Context context;
    private PlaceHolderView placeHolderView;
    private String name;
    private String surname;
    private String skills;
    private String roles;


    public CandidatesItem(int avatarUrl, Context context, PlaceHolderView placeHolderView,
                          String name, String surname, String skills, String roles) {
        this.avatarUrl = avatarUrl;
        this.context = context;
        this.placeHolderView = placeHolderView;
        this.name = name;
        this.surname = surname;
        this.skills = skills;
        this.roles = roles;
    }

    @Resolve
    private void onResolved() {
        Glide.with(context).load(avatarUrl).apply(RequestOptions.circleCropTransform()).into(iv_avatar);
        tv_name.setText(name + " " + surname);
        String[] skillArray = skills.split(";");
        for (String skill : skillArray) {
            phvRoles.addView(new SkillItem(skill));
        }
    }

    @Click(R.id.card_candidates)
    private void onCardClick() {
        Toast.makeText(context, "Click!", Toast.LENGTH_LONG).show();
    }

}
