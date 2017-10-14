package kekify.io.hackteam.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kekify.io.hackteam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {


    @BindView(R.id.camera)
    CameraView cvCamera;
    Unbinder unbinder;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initCamera();
    }

    void initCamera(){
        cvCamera.start();
        cvCamera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {

                super.onPictureTaken(jpeg);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        cvCamera.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        cvCamera.destroy();
    }
}
