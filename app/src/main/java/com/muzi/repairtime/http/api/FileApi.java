package com.muzi.repairtime.http.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 作者: lipeng
 * 时间: 2019/4/20
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface FileApi {

    /**
     * 单个文件上传
     *
     * @param parts
     * @return
     */
    @Multipart
    @POST("springmvc-4/springmvc/upload")
    Observable<ResponseBody> simpleUpload(@Part MultipartBody.Part parts);

    @POST("springmvc-4/springmvc/upload")
    Observable<ResponseBody> simpleUpload(@Body RequestBody body);

}
