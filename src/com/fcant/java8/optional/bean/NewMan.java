package com.fcant.java8.optional.bean;

import java.util.Optional;

/**
 * NewMan
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 14:05:04 2020/2/23/0023
 */
public class NewMan {

    private Optional<Godness> godness = Optional.empty();

    public NewMan(Optional<Godness> godness) {
        this.godness = godness;
    }

    public NewMan() {
    }

    public Optional<Godness> getGodness() {
        return godness;
    }

    public void setGodness(Optional<Godness> godness) {
        this.godness = godness;
    }

    @Override
    public String toString() {
        return "NewMan{" +
                "godness=" + godness +
                '}';
    }
}
