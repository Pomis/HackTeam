package kekify.io.hackteam.fragments;


import android.Manifest;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mindorks.placeholderview.PlaceHolderView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tokenautocomplete.FilteredArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kekify.io.hackteam.CircleSurface;
import kekify.io.hackteam.R;
import kekify.io.hackteam.SkillsCompletionView;
import kekify.io.hackteam.activities.CandidatesActivity;
import kekify.io.hackteam.models.RoleItem;
import kekify.io.hackteam.models.SkillItem;

import static kekify.io.hackteam.fragments.WizardFragment.Step.CAMERA;
import static kekify.io.hackteam.fragments.WizardFragment.Step.KEYWORDS;
import static kekify.io.hackteam.fragments.WizardFragment.Step.NAME;
import static kekify.io.hackteam.fragments.WizardFragment.Step.ROLE;
=======
import kekify.io.hackteam.activities.LoginActivity;
>>>>>>> 90d58b5891794f8b93fea6b1ac9ae198281d0451

/**
 * A simple {@link Fragment} subclass.
 */
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
                new String[] {
                        Manifest.permission.CAMERA
                },
                123);
    }

    private void initList() {
        phvRoles.getBuilder().setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
        phvRoles.addView(new RoleItem("Team manager", getContext()));
        phvRoles.addView(new RoleItem("iOS dev", getContext()));
        phvRoles.addView(new RoleItem("Front-end dev", getContext()));
        phvRoles.addView(new RoleItem("Designer", getContext()));
        phvRoles.addView(new RoleItem("Analyst", getContext()));
        phvRoles.addView(new RoleItem("Team supporter", getContext()));
        phvRoles.addView(new RoleItem("Android dev", getContext()));
        phvRoles.addView(new RoleItem("Back-end dev", getContext()));
        phvRoles.addView(new RoleItem("ML/DL dev", getContext()));
        phvRoles.addView(new RoleItem("Blockchain dev", getContext()));

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
        Intent intent = new Intent(getContext(), CandidatesActivity.class);
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
}
