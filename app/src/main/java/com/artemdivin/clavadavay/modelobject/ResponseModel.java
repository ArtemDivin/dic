package com.artemdivin.clavadavay.modelobject;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("code")
    private  String code;

    @SerializedName("lang")
    private  String lang;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
