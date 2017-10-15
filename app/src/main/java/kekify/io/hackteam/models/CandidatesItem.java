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
    private String email;


    public CandidatesItem(Context context, PlaceHolderView placeHolderView, String fullname,
                          String skills, List<String> roles, int id, String role, String email) {
        this.context = context;
        this.placeHolderView = placeHolderView;
        this.fullname = fullname;
        this.skills = skills;
        this.roles = roles;
        this.id = id;
        this.role = role;
        this.email = email;
    }

    @Resolve
    private void onResolved() {
        int ran = (int) (Math.random() * 9);
        String url = "https://kayceenilson.files.wordpress.com/2014/04/famous-people-bipolar-disorder-19.jpg";

        switch (ran) {
            case 0:
                url = "https://kayceenilson.files.wordpress.com/2014/04/famous-people-bipolar-disorder-19.jpg";
                break;
            case 1:
                url = "http://www.5588158.com/image/i.pinimg.com/736x/96/42/81/964281940996c9d3ee6aaa8330b54361--famous-portraits-mahatma-gandhi.jpg";
                break;
            case 2:
                url = "http://www.ofad.org/files/imagecache/daily_picture/daily-photo/recent-and-random-portraits_8.jpg";
                break;
            case 3:
                url = "http://pm1.narvii.com/5866/45cc61eb9e3eaefdc26be045e9f9fbfdf916f45b_hq.jpg";
                break;
            case 4:
                url = "https://www.rockstart.com/wp-content/uploads/2012/06/bas-300x300.jpg";
                break;
            case 5:
                url = "http://img.izismile.com/img/img3/20100428/640/she_makes_random_640_01.jpg";
                break;
            case 6:
                url = "http://ic.pics.livejournal.com/roman_yerokhin/7052661/24455/24455_original.jpg";
                break;
            case 7:
                url = "https://pp.userapi.com/c626223/v626223877/230b9/eu4GzHtQ8J4.jpg";
                break;
            case 8:
                url = "https://pp.userapi.com/c604420/v604420249/1f5c4/SdFH4Wtnp4A.jpg";
                break;
        }

        Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(iv_avatar);
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

        String token = App.getAppInstance().getPreferencesWrapper().getAuthToken("twist");
        int ws_id = App.getAppInstance().getPreferencesWrapper().getWorkspaceId();

        repository.addUser(token, ws_id, email)
                .compose(RxUtils.applySingleSchedulers())
                .subscribe(workspace -> {
                    System.out.println("Added user with email: " + email);
                }, Throwable::printStackTrace);
    }

}
