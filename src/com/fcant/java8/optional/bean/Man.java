package com.fcant.java8.optional.bean;

/**
 * Man
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 13:59:35 2020/2/23/0023
 */
public class Man {
    private Godness godness;

    public Man() {
    }

    public Man(Godness godness) {
        this.godness = godness;
    }

    public Godness getGodness() {
        return godness;
    }

    public void setGodness(Godness godness) {
        this.godness = godness;
    }

    @Override
    public String toString() {
        return "Man{" +
                "godness=" + godness +
                '}';
    }
}
