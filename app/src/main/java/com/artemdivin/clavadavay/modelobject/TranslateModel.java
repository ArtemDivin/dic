package com.artemdivin.clavadavay.modelobject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateModel {

    @SerializedName("def")
    @Expose
    private List<Def> def = null;

    public List<Def> getDef() {
        return def;
    }
}
