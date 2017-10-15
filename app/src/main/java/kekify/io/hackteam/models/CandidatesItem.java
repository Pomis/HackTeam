package kekify.io.hackteam.models;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.io.File;
import java.util.List;

import kekify.io.hackteam.App;
import kekify.io.hackteam.DataRepository;
import kekify.io.hackteam.R;
import kekify.io.hackteam.RxUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by djavid on 14.10.17.
 */


//@Animate(Animation.CARD_LEFT_IN_DESC)
@NonReusable
@Layout(R.layout.item_candidate)
public class CandidatesItem {
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
    private String fullname;
    private String skills;
    private List<String> roles;
    private int id;
    private String role;


    public CandidatesItem(Context context, PlaceHolderView placeHolderView, String fullname,
                          String skills, List<String> roles, int id, String role) {
        this.context = context;
        this.placeHolderView = placeHolderView;
        this.fullname = fullname;
        this.skills = skills;
        this.roles = roles;
        this.id = id;
        this.role = role;
    }

    @Resolve
    private void onResolved() {
        //Glide.with(context).load(avatarUrl).apply(RequestOptions.circleCropTransform()).into(iv_avatar);
        tv_name.setText(fullname);

        String s_roles = "";
        for (int i = 0; i < roles.size(); i++) {
            s_roles += roles.get(i);
            if (i != roles.size() - 1)
                s_roles += ", ";
        }

        tvRoles.setText(s_roles);
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

        System.out.println("UserId " + id);
        int project_id = App.getAppInstance().getPreferencesWrapper().getProjectId();


        DataRepository repository = new DataRepository();
        repository.sendInvitation(project_id, role, id)
                .compose(RxUtils.applyCompletableSchedulers())
                .subscribe(() -> {}, Throwable::printStackTrace);
    }

}
