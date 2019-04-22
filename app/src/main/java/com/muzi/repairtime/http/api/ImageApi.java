package com.muzi.repairtime.http.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: lipeng
 * 时间: 2019/4/22
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface ImageApi {

    /**
     * 获取图片
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("imageFile/getImagePathes")
    Observable<ResponseBody> getImage(@Field("o_id") int id);

}
