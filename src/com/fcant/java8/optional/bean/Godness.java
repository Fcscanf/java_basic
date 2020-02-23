package com.fcant.java8.optional.bean;

/**
 * Godness
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 13:58:15 2020/2/23/0023
 */
public class Godness {
    private String name;

    public Godness(String name) {
        this.name = name;
    }

    public Godness() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Godness{" +
                "name='" + name + '\'' +
                '}';
    }
}

