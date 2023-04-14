package com.lilei.fitness.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class user extends BmobUser {
    private String shengao;
    private String tizhong;
    private String sex;

    @Override
    public String toString() {
        return "user{" +
                "shengao='" + shengao + '\'' +
                ", tizhong='" + tizhong + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getShengao() {
        return shengao;
    }

    public void setShengao(String shengao) {
        this.shengao = shengao;
    }

    public String getTizhong() {
        return tizhong;
    }

    public void setTizhong(String tizhong) {
        this.tizhong = tizhong;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public user() {
    }

    public user(String shengao, String tizhong, String sex) {
        this.shengao = shengao;
        this.tizhong = tizhong;
        this.sex = sex;
    }


    @Override
    public String getTableName() {
        return "_User";
    }
}
