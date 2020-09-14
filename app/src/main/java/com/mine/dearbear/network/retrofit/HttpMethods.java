package com.mine.dearbear.network.retrofit;


import com.mine.dearbear.bean.BookAddResult;
import com.mine.dearbear.bean.BookSizeResult;
import com.mine.dearbear.rxjava.RxSchedulersUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by on 2017/3/6.
 */

public class HttpMethods {
    private volatile static HttpMethods sInstance;

    public static String sBASE_URL = "www.baidu.com";
    public static String sUrl = sBASE_URL;
    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit retrofit;
    private NetService mNetService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new UserConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(sUrl)
                .build();

        mNetService = retrofit.create(NetService.class);
    }

    //获取单例
    public static HttpMethods getInstance() {
        if (sInstance == null) {
            synchronized (HttpMethods.class) {
                if (sInstance == null) {
                    sInstance = new HttpMethods();
                    return sInstance;
                }
            }
        }
        return sInstance;
    }


    /***
     * 新增图片
     * @param singleObserver
     */
    public void bookAdd(SingleObserver<List<BookAddResult>> singleObserver, File[] files) {
        Map<String, RequestBody> filesMap = new HashMap<>();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            filesMap.put("file\"; filename=\"" + file.getName(), requestBody);
        }
        Single observable = mNetService.bookAdd(filesMap);
        observable
                .map(httpResultTFunction())
                .compose(RxSchedulersUtils.applySingleAsync())
                .subscribe(singleObserver);
    }


    /***
     * 已加载样本库大小
     * @param singleObserver
     */
    public void bookSize(SingleObserver<BookSizeResult> singleObserver) {
        Single observable = mNetService.bookSize();
        observable
                .map(httpResultTFunction())
                .compose(RxSchedulersUtils.applySingleAsync())
                .subscribe(singleObserver);
    }

    private static <T> Function<HttpResult<T>, T> httpResultTFunction() {
        return new Function<HttpResult<T>, T>() {
            @Override
            public T apply(HttpResult<T> tHttpResult) throws Exception {
                if (!"SUCCESS".equals(tHttpResult.getResult())) {
                    throw new ApiException("result:" + tHttpResult.getResult() + "; message:" + tHttpResult.getMessage());
                }
                return tHttpResult.getData();
            }
        };
    }
}
