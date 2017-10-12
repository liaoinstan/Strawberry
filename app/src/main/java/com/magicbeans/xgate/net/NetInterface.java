package com.magicbeans.xgate.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * API接口
 */
public interface NetInterface {

    /**
     * 测试网络连接，无实际意义
     */
    @FormUrlEncoded
    @POST("/api/user/sendMessage")
    Call<ResponseBody> netTest(@FieldMap Map<String, Object> param);

    /**
     * 上传资源文件
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadFile(@Url String url, @Part MultipartBody.Part file);

    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////


    //##################################################################
    //#########               登录注册个人中心
    //##################################################################

    /**
     * 登录
     * java.lang.String phone, java.lang.String password, java.lang.Integer deviceType, java.lang.Integer deviceToken, java.lang.Integer isWechat
     */
    @FormUrlEncoded
    @POST("/api/user/login")
    Call<ResponseBody> login(@FieldMap Map<String, Object> param);

    /**
     * 更新用户信息
     * java.lang.String showName, java.lang.String avatar, java.lang.Integer cityId, java.lang.Integer definition
     */
    @FormUrlEncoded
    @POST("/api/user/updateUser")
    Call<ResponseBody> updateUserWithToken(@Header("token") String token, @FieldMap Map<String, Object> param);

    //##################################################################
    //#########               接口
    //##################################################################

    /**
     * 首页获取今日秒杀
     */
    @GET("/app/promotionList.aspx?OthCatgId=90")
    Call<ResponseBody> netHomeSaleList(@QueryMap Map<String, Object> param);

    /**
     * 首页获取品牌好货
     */
    @GET("/app/promotionList.aspx?OthCatgId=17")
    Call<ResponseBody> netHomeSingleList(@QueryMap Map<String, Object> param);

    /**
     * 获取推荐产品列表
     */
    @GET("/app/promotionList.aspx?OthCatgId=21")
    Call<ResponseBody> netRecommendList(@QueryMap Map<String, Object> param);

    /**
     * 产品列表
     * CatgId 功能
     * brandId 品牌
     * typeId
     * sort 排序（producttype, alphabetical, popularity, save, lowerprice）
     */
    @GET("/app/productList.aspx")
    Call<ResponseBody> netProductList(@QueryMap Map<String, Object> param);

    /**
     * 品牌列表
     */
    @GET("/app/shopByBrand.aspx")
    Call<ResponseBody> netBrandList(@QueryMap Map<String, Object> param);
}
