package com.artemdivin.clavadavay.db.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Words extends RealmObject {

    @PrimaryKey
    private String original;
    private String translate;

    public Words() {
    }

    public Words(String original, String translate) {
        this.original = original;
        this.translate = translate;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
