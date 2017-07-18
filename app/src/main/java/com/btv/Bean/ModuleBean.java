package com.btv.Bean;

/**
 * Created by Administrator on 2017/7/11.
 */

public class ModuleBean {

    private String imageUrl;
    private String name;
    private String num;

    public ModuleBean(String imageUrl, String name, String num) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.num = num;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
