package kekify.io.hackteam.fragments;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tokenautocomplete.FilteredArrayAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kekify.io.hackteam.App;
import kekify.io.hackteam.CircleSurface;
import kekify.io.hackteam.DataRepository;
import kekify.io.hackteam.R;
import kekify.io.hackteam.RxUtils;
import kekify.io.hackteam.SkillsCompletionView;
import kekify.io.hackteam.activities.ChooseActivity;
import kekify.io.hackteam.models.RoleItem;
import kekify.io.hackteam.models.SkillItem;

import static kekify.io.hackteam.fragments.WizardFragment.Step.CAMERA;
import static kekify.io.hackteam.fragments.WizardFragment.Step.KEYWORDS;
import static kekify.io.hackteam.fragments.WizardFragment.Step.NAME;
import static kekify.io.hackteam.fragments.WizardFragment.Step.ROLE;

import kekify.io.hackteam.models.User;


public class WizardFragment extends Fragment {

    @BindView(R.id.ll_first)
    LinearLayout llFirst;
    @BindView(R.id.b_next_step)
    Button bNextStep;
    @BindView(R.id.ll_second)
    LinearLayout llSecond;
    @BindView(R.id.phv_roles)
    PlaceHolderView phvRoles;
    @BindView(R.id.searchView)
    SkillsCompletionView searchView;
    @BindView(R.id.ll_third)
    LinearLayout llThird;
    @BindView(R.id.sv_camera)
    CircleSurface svCamera;
    @BindView(R.id.ll_fourth)
    LinearLayout llFourth;

    public ArrayList<String> roles = new ArrayList<>();

    enum Step {
        NAME, ROLE, KEYWORDS, CAMERA
    }

    ArrayAdapter<SkillItem> skillAdapter;

    Step currentStep = NAME;

    @BindView(R.id.met_name)
    MaterialEditText metName;

    Unbinder unbinder;

    public WizardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wizard, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{
                        Manifest.permission.CAMERA
                },
                123);
    }

    private void initList() {
        phvRoles.getBuilder().setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
        phvRoles.addView(new RoleItem("Team manager", getActivity(), roles));
        phvRoles.addView(new RoleItem("iOS dev", getActivity(), roles));
        phvRoles.addView(new RoleItem("Front-end dev", getActivity(), roles));
        phvRoles.addView(new RoleItem("Designer", getActivity(), roles));
        phvRoles.addView(new RoleItem("Analyst", getActivity(), roles));
        phvRoles.addView(new RoleItem("Team supporter", getActivity(), roles));
        phvRoles.addView(new RoleItem("Android dev", getActivity(), roles));
        phvRoles.addView(new RoleItem("Back-end dev", getActivity(), roles));
        phvRoles.addView(new RoleItem("ML/DL dev", getActivity(), roles));
        phvRoles.addView(new RoleItem("Blockchain dev", getActivity(), roles));

        SkillItem[] skillItems = new SkillItem[]{
                new SkillItem("kekeke"),
                new SkillItem("MySQL"),
                new SkillItem("C#"),
                new SkillItem("gradle")

        };


        skillAdapter = new FilteredArrayAdapter<SkillItem>(getContext(), android.R.layout.simple_list_item_1, skillItems) {
            @Override
            protected boolean keepObject(SkillItem obj, String mask) {
                mask = mask.toLowerCase();
                return obj.name.toLowerCase().startsWith(mask);
            }
        };

        searchView.setAdapter(skillAdapter);
    }


    void openDashboard() {

        User user = new User(metName.getText().toString(), getSkills(), roles);
        user.setTwist_email(App.getAppInstance().getPreferencesWrapper().getEmail());
        user.setTwist_id(App.getAppInstance().getPreferencesWrapper().getTwistId());

        createUser(user);

        ChooseActivity.start(getContext());
    }

    @OnClick(R.id.b_next_step)
    void nextStep() {
        switch (currentStep) {
            case NAME:
                currentStep = ROLE;
                swap(llFirst, llSecond);

                break;

            case ROLE:
                currentStep = KEYWORDS;
                swap(llSecond, llThird);
                searchView.setFocusableInTouchMode(true);
                searchView.requestFocus();
                break;

            case KEYWORDS:
                currentStep = CAMERA;
                swap(llThird, llFourth);

                initCamera();
                break;

            case CAMERA:
                openDashboard();
                break;

        }
    }

    void swap(View first, View last) {
        first.animate()
                .translationYBy(-500)
                .alpha(0)
                .setDuration(500)
                .setInterpolator(new AccelerateInterpolator())
                .start();
        last.animate()
                .translationY(0)
                .alpha(1)
                .setDuration(500)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    private void initCamera() {
        Camera camera;
        camera = Camera.open(1);
        Camera.Parameters params = camera.getParameters();
        params.setRotation(180);
        camera.setDisplayOrientation(90);
        camera.setParameters(params);
        SurfaceHolder holder = svCamera.getHolder();
        try {
            Log.d("kek", "surface created");
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String getSkills() {
        String res = "";
        for (int i = 0; i < searchView.skills.size(); i++) {
            res += searchView.skills.get(i);
            if (i < searchView.skills.size() - 1)
                res += ";";
        }
        return res;
    }

    public void createUser(User user) {
        DataRepository repository = new DataRepository();

        repository.createUser(user)
                .compose(RxUtils.applySingleSchedulers())
                .subscribe(id -> {
                    Toast.makeText(getContext(),
                            "You have successfully registered!", Toast.LENGTH_SHORT);

                    App.getAppInstance().getPreferencesWrapper().setId(id);
                    System.out.println("Set id: " + id);
                }, error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(),
                            "Something gone wrong!", Toast.LENGTH_LONG).show();
                });
    }

}
