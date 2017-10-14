package kekify.io.hackteam.models;

/**
 * Created by romanismagilov on 14.10.17.
 */

public class SkillItem {
    public String name;

    @Override
    public String toString() {
        return "" + name;
    }

    public SkillItem(String name) {
        this.name = name;
    }
}
