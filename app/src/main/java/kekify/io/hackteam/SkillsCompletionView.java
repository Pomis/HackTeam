package kekify.io.hackteam;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;

import kekify.io.hackteam.models.RoleItem;
import kekify.io.hackteam.models.SkillItem;

/**
 * Created by romanismagilov on 14.10.17.
 */

public class SkillsCompletionView extends TokenCompleteTextView<SkillItem> {
    public SkillsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArrayList<String> skills = new ArrayList<>();

    @Override
    protected View getViewForObject(SkillItem skill) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) l.inflate(R.layout.item_role, (ViewGroup) getParent(), false).findViewById(R.id.tv_role);
        view.setText(skill.name);
        skills.add(skill.name);
        return view;
    }

    @Override
    protected SkillItem defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new SkillItem(completionText);
        } else {
            return new SkillItem(completionText.substring(0, index));
        }
    }
}