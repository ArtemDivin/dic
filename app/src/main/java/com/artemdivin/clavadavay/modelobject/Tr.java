package com.artemdivin.clavadavay.modelobject;

import com.artemdivin.clavadavay.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tr {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static List<Tr> getEmptyList(){
        Tr tr = new Tr();
        tr.setText("Нет вариантов");
        List<Tr> list = new ArrayList<>();
        list.add(tr);
        return list;

    }
}
