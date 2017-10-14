package kekify.io.hackteam.models;

import android.content.Context;
import android.widget.TextView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.ArrayList;

import kekify.io.hackteam.R;

/**
 * Created by romanismagilov on 14.10.17.
 */

@NonReusable
@Layout(R.layout.item_role)
public class RoleItem {
    String name;
    Context context;
    ArrayList<String> selected;

    @View(R.id.tv_role)
    transient TextView textView;

    @View(R.id.tv_role_inverse)
    transient TextView textViewInversed;


    public RoleItem(String name, Context context, ArrayList<String> selected) {
        this.name = name;
        this.context = context;
        this.selected = selected;
    }

    @Resolve
    void resolve() {
        textView.setText(name);
        textViewInversed.setText(name);
    }

    @Click(R.id.tv_role)
    void onClick() {
        textView.animate().alpha(0).setDuration(500).start();
        textViewInversed.animate().alpha(1).setDuration(300).start();
        selected.add(name);
    }
}
