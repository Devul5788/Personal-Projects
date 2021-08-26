package com.example.devul.customlistview;

public class MyClass
{
    public String name, src;
    public int img;

    MyClass()
    {
    }

    MyClass(String nm,int img, String src)
    {
        this.name=nm;
        this.img=img;
        this.src=src;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}

