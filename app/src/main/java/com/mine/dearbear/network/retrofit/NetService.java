package com.mine.dearbear.network.retrofit;


import com.mine.dearbear.bean.BookAddResult;
import com.mine.dearbear.bean.BookSizeResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;


/**
 * Created by on 2017/3/6.
 */

public interface NetService {

    // 新增图片
    @Multipart
    @POST("book/add")
    Single<HttpResult<List<BookAddResult>>> bookAdd(@PartMap Map<String, RequestBody> filesBody);

    // 已加载库大小
    @POST("book/size")
    Single<HttpResult<BookSizeResult>> bookSize();

}
