package com.example.canary;

import android.content.Context;

import java.io.File;

public class Project {
    // Current project being worked on
    public static Project instance = new Project();

    private String name;
    private File dir;

    public void setInstance(String name, Context context) {
        this.name = name;
        this.dir = context.getDir(name, Context.MODE_PRIVATE);
    }

    public String getName() {
        return name;
    }

    public File getDir() {
        return dir;
    }

    private Project() {

    }
}