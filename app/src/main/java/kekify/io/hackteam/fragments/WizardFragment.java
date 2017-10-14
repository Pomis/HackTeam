package kekify.io.hackteam.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kekify.io.hackteam.R;
import kekify.io.hackteam.activities.Candidates;

/**
 * A simple {@link Fragment} subclass.
 */
public class WizardFragment extends Fragment {


    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.met_name)
    MaterialEditText metName;
    @BindView(R.id.b_next)
    Button bNext;
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

    @OnClick(R.id.b_next)
    void onClick() {
        Intent intent = new Intent(getContext(), Candidates.class);

        startActivity(intent);

    }
}
