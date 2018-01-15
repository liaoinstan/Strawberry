package com.magicbeans.xgate.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
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
    //#########               登录及其注册流程
    //##################################################################

    /**
     * 检查OpenId是否存在，存在则会登录并返回用户token
     */
    @FormUrlEncoded
    @POST("/app/apiCheckAccountExist.aspx?ID=xGate")
    Call<ResponseBody> checkOpenidExist(@FieldMap Map<String, Object> param);

    /**
     * 使用openId创建一个账户
     * OpenId
     * OpenIdType 1: WeChat 2: Weibo 3: QQ
     * Email
     * Mobile
     * DeviceId
     * deviceType : android
     * DisplayName
     * Language
     * HeadImageURL
     * Gender:0: Female 1: Male
     */
    @POST("/app/apiCreateAccount.aspx?ID=xGate")
    Call<ResponseBody> createAccount(@Body RequestBody requestBody);

    /**
     * 合并已存在用户
     * OpenId
     * OpenIdType 1: WeChat 2: Weibo 3: QQ
     * Email
     * Password
     * DeviceId
     * deviceType : android
     * DisplayName
     * Language
     * HeadImageURL
     * Gender:0: Female 1: Male
     */
    @POST("/app/apiUpdateAccount.aspx?ID=xGate")
    Call<ResponseBody> mergeAccount(@Body RequestBody requestBody);

    /**
     * 获取用户信息
     * accountID
     * action
     * token
     */
//    @GET("/app/apiUserProfile.aspx")
    @GET
    Call<ResponseBody> getUserProfile(@Url String url, @QueryMap Map<String, Object> param);

    //##################################################################
    //#########               接口
    //##################################################################

    /**
     * 首页获取Banner
     */
    @GET("/app/RotationBanner.aspx")
    Call<ResponseBody> netHomeBanner(@QueryMap Map<String, Object> param);

    /**
     * 首页获取今日秒杀
     */
    @GET("/app/promotionList.aspx?OthCatgId=90")
    Call<ResponseBody> netHomeSaleList(@QueryMap Map<String, Object> param);

    /**
     * 首页获取今日秒杀时限
     */
    @GET("/ajaxDailySpecials.aspx")
    Call<ResponseBody> netDailySaleTime(@QueryMap Map<String, Object> param);

    /**
     * 产品列表
     * CatgId 功能
     * brandId 品牌
     * typeId
     * sort 排序（producttype, alphabetical, popularity, save, lowerprice）
     * page
     */
    @GET("/app/productList.aspx")
    Call<ResponseBody> netProductList(@QueryMap Map<String, Object> param);

    /**
     * 产品详情
     * ProdId 产品id
     * currId 货币类型
     */
    @GET("/app/apiProdDetail.aspx")
    Call<ResponseBody> netProductDetail(@QueryMap Map<String, Object> param);

    /**
     * 产品评论
     * ProdId 产品id
     * Page
     * region cn
     */
    @GET("/ajaxProdReview.aspx")
    Call<ResponseBody> netProductReview(@QueryMap Map<String, Object> param);

    /**
     * 品牌列表
     */
    @GET("/app/shopByBrand.aspx")
    Call<ResponseBody> netBrandList(@QueryMap Map<String, Object> param);

    /**
     * 注册
     * signupemail
     * password
     * repassword
     * signupFirstname ?
     * signupLastname ?
     * isSubscribe
     */
    @GET("https://secure.strawberrynet.com/app/apiAccountSignup.aspx")
    Call<ResponseBody> netSignUp(@QueryMap Map<String, Object> param);

    //##################################################################
    //#########               2017/12/13 改版后新增
    //##################################################################

    /**
     * 精选品牌
     */
    @GET("https://demo2017.strawberrynet.com/app/apiTopBrands.aspx")
    Call<ResponseBody> netHomeSelectBrand(@QueryMap Map<String, Object> param);

    /**
     * 获取推荐产品列表
     * https://cn.strawberrynet.com/app/promotionList.aspx?OthCatgId=supervaluezone&PCId=2&TypeId=39
     */
    @GET("/app/promotionList.aspx?OthCatgId=21")
    Call<ResponseBody> netRecommendList(@QueryMap Map<String, Object> param);

    /**
     * 获取一级分类列表
     */
    @GET("app/categoryList.aspx")
    Call<ResponseBody> netMainCategory(@QueryMap Map<String, Object> param);

    /**
     * 获取二级三级分类列表
     * CatgId  一级分类ID
     */
    @GET("https://demo2017.strawberrynet.com/app/apiSubCategories.aspx")
    Call<ResponseBody> netSubCategory(@QueryMap Map<String, Object> param);


    //##################################################################
    //#########               2017/12/13 首页模块变更接口
    //##################################################################

    /**
     * 首页获取品牌好货
     */
    @GET("/app/promotionList.aspx?OthCatgId=17")
    Call<ResponseBody> netHomePromotionList(@QueryMap Map<String, Object> param);

    /**
     * 首页获取王牌单品
     */
    @GET("/app/promotionList.aspx?OthCatgId=bestseller")
    Call<ResponseBody> netHomeSingleList(@QueryMap Map<String, Object> param);

    /**
     * 首页获取新品上市
     */
    @GET("/app/promotionList.aspx?OthCatgId=17&PCId=2&TypeId=39")
    Call<ResponseBody> netHomeNewList(@QueryMap Map<String, Object> param);

    /**
     * 首页获取精品推荐
     */
    @GET("/app/promotionList.aspx?OthCatgId=supervaluezone&PCId=2&TypeId=39")
    Call<ResponseBody> netHomeRecommendList(@QueryMap Map<String, Object> param);

    /**
     * 首页获取清仓优惠
     */
    @GET("/app/promotionList.aspx?OthCatgId=89")
    Call<ResponseBody> netHomeClearList(@QueryMap Map<String, Object> param);

    /**
     * 对评论点赞
     * CommentID
     * type 0：踩   1：赞
     */
    @GET("/ajaxProdReviewFB.aspx")
    Call<ResponseBody> netZanRecomment(@QueryMap Map<String, Object> param);
}
