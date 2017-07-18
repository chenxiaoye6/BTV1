package com.btv.Bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/14.
 */

public class LvDataBean implements Serializable {
    ArrayList<ArrayList<String>> list;

    public ArrayList<ArrayList<String>> getList() {
        return list;
    }

    public void setList(ArrayList<ArrayList<String>> list) {
        this.list = list;
    }
}
