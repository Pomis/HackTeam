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
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import kekify.io.hackteam.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by romanismagilov on 15.10.17.
 */
@NonReusable
@Layout(R.layout.item_invite)
public class TeamModel {
    @View(R.id.iv_avatar)
    private ImageView iv_avatar;

    @View(R.id.tv_name)
    private TextView tv_name;

    @View(R.id.tv_roles)
    private TextView tvRoles;

    @View(R.id.b_invite)
    private Button bInvite;

    @View(R.id.b_invited)
    private Button bInvited;

    @View(R.id.tv_descr)
    private TextView tvDescr;



    private int avatarUrl;
    private Context context;
    private PlaceHolderView placeHolderView;
    private String name;

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public void setRoles(String roles) {
        this.roles = roles;
    }

    private String roles;


    public TeamModel(int avatarUrl, Context context, PlaceHolderView placeHolderView,
                          String name, String description) {
        this.avatarUrl = avatarUrl;
        this.context = context;
        this.placeHolderView = placeHolderView;
        this.name = name;
        this.description = description;

    }

    @Resolve
    private void onResolved() {
        Glide.with(context).load(avatarUrl).apply(RequestOptions.circleCropTransform()).into(iv_avatar);
        tv_name.setText(name);
        tvDescr.setText(description);
        tvRoles.setText(roles);

    }

    @Click(R.id.b_invite)
    private void onCardClick() {
        bInvite.setVisibility(GONE);
        bInvited.setVisibility(VISIBLE);
    }
}
