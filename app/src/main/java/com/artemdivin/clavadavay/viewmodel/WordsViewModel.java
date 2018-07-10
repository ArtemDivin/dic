package com.artemdivin.clavadavay.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;

import com.artemdivin.clavadavay.modelobject.Resource;
import com.artemdivin.clavadavay.modelobject.TranslateModel;
import com.artemdivin.clavadavay.db.entity.Words;
import com.artemdivin.clavadavay.repository.LocalRepository;
import com.artemdivin.clavadavay.api.RemoteRepository;

import io.realm.RealmResults;

public class WordsViewModel extends ViewModel {

    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;

    public WordsViewModel() {
        if (remoteRepository == null) {
            remoteRepository = new RemoteRepository();
        }
        if (localRepository == null){
            localRepository = new LocalRepository();
        }
    }

    public LiveData<Resource<TranslateModel>> getWordsRemote(String word) {
        return LiveDataReactiveStreams.fromPublisher(remoteRepository.getTranslateWords(word));
    }

    public LiveData<RealmResults<Words>> getAllWorldsLocal() {
        return LiveDataReactiveStreams.fromPublisher(localRepository.getAllWords());
    }

    public void saveResult(Words word) {
        localRepository.saveWord(word);
    }

    @Override
    protected void onCleared() {
        if (localRepository != null){
            localRepository.closeDB();
        }
        super.onCleared();
    }

    public void deleteWord(String word) {
        localRepository.deleteWord(word);
    }
}
