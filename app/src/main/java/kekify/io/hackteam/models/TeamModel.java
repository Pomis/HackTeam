package kekify.io.hackteam.models;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import kekify.io.hackteam.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by romanismagilov on 15.10.17.
 */

public class TeamModel {
    @View(R.id.iv_avatar)
    private ImageView iv_avatar;

    @View(R.id.tv_name)
    private TextView tv_name;

    @View(R.id.tv_roles)
    private TextView tvRoles;

    @View(R.id.phv_roles)
    private PlaceHolderView phvRoles;

    @View(R.id.phv_skills)
    private PlaceHolderView phvSkills;

    @View(R.id.b_invite)
    private Button bInvite;

    @View(R.id.b_invited)
    private Button bInvited;

    private int avatarUrl;
    private Context context;
    private PlaceHolderView placeHolderView;
    private String name;
    private String surname;
    private String skills;
    private String roles;


    public TeamModel(int avatarUrl, Context context, PlaceHolderView placeHolderView,
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
        tvRoles.setText(roles);
        String[] skillArray = skills.split(";");
        phvSkills.getBuilder().setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        for (String skill : skillArray) {
            phvSkills.addView(new SkillItem(skill));
        }
    }

    @Click(R.id.b_invite)
    private void onCardClick() {
        bInvite.setVisibility(GONE);
        bInvited.setVisibility(VISIBLE);
    }
}
