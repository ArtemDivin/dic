package com.artemdivin.clavadavay.repository;

import com.artemdivin.clavadavay.db.entity.Words;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

public class LocalRepository {
    private Realm realm;

    public LocalRepository() {
        realm = Realm.getDefaultInstance();
    }

    public void saveWord(Words word) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(word);
        realm.commitTransaction();
    }

    public void deleteWord(String word) {
        realm.beginTransaction();
        realm.where(Words.class).equalTo("original", word).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public Flowable<RealmResults<Words>> getAllWords() {
        return realm.where(Words.class).findAll().sort("original").asFlowable();
    }

    public void closeDB() {
        if (realm != null) {
            realm.close();
        }
    }
}
