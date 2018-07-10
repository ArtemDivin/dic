package com.artemdivin.clavadavay.api;

import com.artemdivin.clavadavay.modelobject.ResponseModel;
import com.artemdivin.clavadavay.modelobject.TranslateModel;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("https://translate.yandex.net/api/v1.5/tr.json/detect")
    Flowable<ResponseModel> detectLang (@Field("key") String apiKey, @Field("text") String textLangDetect);

    @FormUrlEncoded
    @POST(" https://dictionary.yandex.net/api/v1/dicservice.json/lookup")
    Flowable<TranslateModel> translateWithVariant (@Field("key") String apiKey, @Field("lang") String directionTranslate, @Field("text") String textForTranslate);

}
