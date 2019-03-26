package com.muzi.repairtime.http.api;

import com.muzi.repairtime.entity.NoticeEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface NoticeApi {

    /**
     * 获取所有公告
     *
     * @return
     */
    @FormUrlEncoded
    @POST("announcement/getAnnouncements")
    Observable<NoticeEntity> getAnnouncements(@Field("currentPage") int currentPage);

}
