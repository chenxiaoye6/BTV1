package com.btv.Bean;

/**
 * Created by Administrator on 2017/1/12.
 */

public class WarningDAffBusi {
    String name;
    String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "WarningDAffBusi{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
