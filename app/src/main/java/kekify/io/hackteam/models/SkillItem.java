package kekify.io.hackteam.models;

import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import kekify.io.hackteam.R;


@NonReusable
@Layout(R.layout.item_skill)
public class SkillItem {
    public String name;

    @View(R.id.tv_skill)
    TextView tvSkill;

    @Override
    public String toString() {
        return "" + name;
    }

    public SkillItem(String name) {
        this.name = name;
    }

    @Resolve
    void onResolved() {
        tvSkill.setText(name);
    }
}
