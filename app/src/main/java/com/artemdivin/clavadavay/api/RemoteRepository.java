package com.artemdivin.clavadavay.api;

import com.artemdivin.clavadavay.modelobject.Def;
import com.artemdivin.clavadavay.modelobject.Resource;
import com.artemdivin.clavadavay.modelobject.TranslateModel;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RemoteRepository {
    private final static String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private final static String API_KEY = "trnsl.1.1.20180706T042158Z.c089233d98aa2f79.d506ba474b68f70a0bd92a4a298b079d87629695";
    private final static String API_KEY_DIC = "dict.1.1.20180709T064703Z.b060765046d3913e.afb6ee081fac6d9c0858a01548e94cb7a33665b9";
    private Api api;

    public RemoteRepository() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.api = retrofit.create(Api.class);
    }

    public Flowable<Resource<TranslateModel>> getTranslateWords(String word) {
      return api.detectLang(API_KEY, word)
                .flatMap(responseModel -> api.translateWithVariant(API_KEY_DIC,getDirection(responseModel.getLang()), word))
                .map(Resource::success)
                .startWith(Resource.loading(null))
                .onErrorReturn(Resource::error)
                .subscribeOn(Schedulers.io());
    }

    public String getDirection(String lang){
        if (lang.equals("ru")){
            return "ru-en";
        }else{
            return lang + "-ru";
        }
    }

}
