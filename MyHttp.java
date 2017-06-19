package com.example.administrator.retrofitdemo;

import android.util.MutableByte;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * date:2017/6/16 0016
 * authom:贾雪茹
 * function:
 */

public interface MyHttp {

    @GET("Bwei/login")
    public Call<Bean> getData(@QueryMap Map<String,String> map);

    @FormUrlEncoded//post请求只支持form表单提交；
    @POST("Bwei/login")
    public Call<Bean> getPost(@Field("username") String username,@Field("password") String password,@Field("postkey") String postkey);

    @FormUrlEncoded
    @POST("Bwei/login")
    public Call<Bean> myPost(@FieldMap Map<String,String> map);

    @GET("Bwei/{type}")
    public Call<Bean> getPath(@Path("type") String username,@QueryMap Map<String,String> map);

    @POST("Bwei/login")
    public Call<Bean> postBody(@Body Mybean bean);

    //上传图片；
    @Multipart//允许上传图片；
    @POST("Bwei/upload")//上传图片用Post请求；
    public Call<MyPhone> upLoadPhone(@Part("image") MultipartBody boty);


}
