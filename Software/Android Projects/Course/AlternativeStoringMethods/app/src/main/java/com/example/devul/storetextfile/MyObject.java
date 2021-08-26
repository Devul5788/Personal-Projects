package com.example.devul.storetextfile;

import org.json.JSONObject;

import java.io.Serializable;


public  class MyObject implements Serializable{

    private static final long serialVersionUID = 1L;
    String hello_code;
    String lang="2";
    String hello_no;
    public MyObject()
    {
    }
    public String getHello_code() {
        return hello_code;
    }
    public void setHello_code(String hello_code) {
        this.hello_code = hello_code;
    }
    public String getHello_no() {
        return hello_no;
    }
    public void setHello_no(String hello_no) {
        this.hello_no = hello_no;
    }
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }




}
