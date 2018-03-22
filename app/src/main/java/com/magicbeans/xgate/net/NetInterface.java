package com.magicbeans.xgate.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    /**
     * 通用请求方法
     */
    @GET
    Call<ResponseBody> commonNetwork(@Url String url);

    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////


    //##################################################################
    //#########               登录及其注册流程
    //##################################################################

    /**
     * 检查OpenId是否存在，存在则会登录并返回用户token
     */
//    @FormUrlEncoded
    @GET("/app/apiCheckAccountExist.aspx?ID=xGate")
    Call<ResponseBody> checkOpenidExist(@QueryMap(encoded = true) Map<String, Object> param);

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

    //##################################################################
    //#########               接口
    //##################################################################

    /**
     * 首页获取Banner
     */
    @GET("/app/RotationBanner.aspx")
    Call<ResponseBody> netHomeBanner(@QueryMap Map<String, Object> param);

    /**
     * 首页获取今日秒杀时限
     */
    @GET("/ajaxDailySpecials.aspx")
    Call<ResponseBody> netDailySaleTime(@QueryMap Map<String, Object> param);

    /**
     * 产品列表
     * CatgId  功能
     * brandId 品牌
     * typeId  类别
     * sort    排序（producttype, alphabetical, popularity, save, lowerprice）
     * page    页码
     */
    @GET("/app/productList.aspx")
    Call<ResponseBody> netProductList(@QueryMap Map<String, Object> param);

    /**
     * 产品搜索列表
     * searchFiel  搜索关键字
     * page    页码
     */
    @GET("/app/prodSearch.aspx")
    Call<ResponseBody> netProductSearch(@QueryMap Map<String, Object> param);

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
     * 首页获取今日秒杀
     */
    @GET("/app/promotionList.aspx?OthCatgId=90")
    Call<ResponseBody> netHomeTodayList(@QueryMap Map<String, Object> param);

    /**
     * 首页获取今日秒杀
     */
    @GET("/app/promotionList.aspx?OthCatgId=19")
    Call<ResponseBody> netHomeSaleList(@QueryMap Map<String, Object> param);

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
     * 首页获取每日精选
     */
    @GET("/app/promotionList.aspx?OthCatgId=89")
    Call<ResponseBody> netHomeSelectList(@QueryMap Map<String, Object> param);

    /**
     * 对评论点赞
     * CommentID
     * type 0：踩   1：赞
     */
    @GET("/ajaxProdReviewFB.aspx")
    Call<ResponseBody> netZanRecomment(@QueryMap Map<String, Object> param);

    //##################################################################
    //#########             不允许进行参数编码的API
    //##################################################################

    //##################################################################
    //#########                     用户相关
    //##################################################################

    /**
     * 获取用户信息
     * accountID
     * action
     * token
     */
    @GET("/app/apiUserProfile.aspx?action=get")
    Call<ResponseBody> getUserProfile(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 更新用户信息
     * accountID
     * action
     * token
     */
    @GET("/app/apiUserProfile.aspx?action=update")
    Call<ResponseBody> updateUserProfile(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 刷新token
     * deviceId
     * deviceType
     * accountID
     * time
     * token
     * encryptData
     */
    @GET("/app/refreshToken.aspx")
    Call<ResponseBody> refreshToken(@QueryMap(encoded = true) Map<String, Object> param);

    //##################################################################
    //#########               2018/1/23 购物车
    //##################################################################

    /**
     * 添加购物车
     * ProdId
     * token
     * qty
     */
    @GET("/app/apiShopcart.aspx?ID=xGate&act=additem")
    Call<ResponseBody> netAddShopCart(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 更新购物车 （数量）
     * ProdId
     * token
     * qty
     */
    @GET("/app/apiShopcart.aspx?ID=xGate&act=updatecart")
    Call<ResponseBody> netUpdateShopCart(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 批量更新购物车 （数量）
     * AppProds ：185255_3,187916_1,210939_1
     * token
     */
    @GET("/app/apiShopcart.aspx?ID=xGate&act=updatecart")
    Call<ResponseBody> netBatchUpdateShopCart(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 批量添加商品到购物车
     * AppProds ：185255_3,187916_1,210939_1
     * token
     */
    @GET("/app/apiShopcart.aspx?ID=xGate&act=additem")
    Call<ResponseBody> netBatchAddShopCart(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 删除购物车商品
     * ProdId
     * token
     */
    @GET("/app/apiShopcart.aspx?ID=xGate&act=remove")
    Call<ResponseBody> netRemoveShopCart(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 获取购物车
     * token
     */
    @GET("/app/apiShopcart.aspx?ID=xGate")
    Call<ResponseBody> netGetShopCartList(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 获取商品价格运费等信息
     * token
     * AppProds
     */
    @GET("/app/apiShopcartItems.aspx?ID=xGate&flag=1")
    Call<ResponseBody> netGetShopCartInfo(@QueryMap(encoded = true) Map<String, Object> param);


    //##################################################################
    //#########               2018/2/2 地址管理
    //##################################################################

    /**
     * 获取地址列表
     * AccountID
     * token
     * addrtype 1: Billing Address 2: Delivery Address
     */
    @GET("/app/apiAddressBook.aspx?ID=xGate&action=get")
    Call<ResponseBody> netGetAddressList(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 添加地址
     * AccountID
     * token
     * addrtype 1: Billing Address 2: Delivery Address
     * ...
     */
    @GET("/app/apiAddressBook.aspx?ID=xGate&action=add")
    Call<ResponseBody> netAddAddress(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 更新地址
     * AddId
     * ...
     */
    @GET("/app/apiAddressBook.aspx?ID=xGate&action=update")
    Call<ResponseBody> netUpdateAddress(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 删除地址
     * AccountID
     * token
     * AddId
     */
    @GET("/app/apiAddressBook.aspx?ID=xGate&action=delete")
    Call<ResponseBody> netDeleteAddress(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 设置默认地址
     * AccountID
     * token
     * AddId
     * addrtype 1: Billing Address 2: Delivery Address
     * action ：1 SETDEFS : Set default shipping address 2 SETDEFB : Set default billing address
     */
    @GET("/app/apiAddressBook.aspx?ID=xGate")
    Call<ResponseBody> netSetDefaultAddress(@QueryMap(encoded = true) Map<String, Object> param);

    //##################################################################
    //#########               2018/1/23 下单
    //##################################################################

    /**
     * checkout
     */
    @POST("/app/apiCheckout.aspx?ID=xGate")
    Call<ResponseBody> netCheckout(@Body RequestBody requestBody);

    /**
     * 下单
     */
    @POST("/app/apiPlaceOrder.aspx?ID=xGate")
    Call<ResponseBody> netAddOrder(@Body RequestBody requestBody);

    /**
     * 获取订单列表
     */
    //@POST("/app/ajaxOrderSummary.aspx")   // 原来的接口不适合移动端的设计，使用下面新的api
    @POST("/app/apiOrderSummaryNew.aspx")
    Call<ResponseBody> netOrderHistory(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * 获取订单详情
     */
    @POST("/app/ajaxOrderDetails.aspx?siteID=xgate")
    Call<ResponseBody> netOrderDetail(@QueryMap(encoded = true) Map<String, Object> param);

    //##################################################################
    //#########               2018/2/12 支付
    //##################################################################

    /**
     * adyen checkout
     */
//    @POST("/RedirectmWeChat.aspx?siteID=xgate")
    @POST("/RedirectmWeChatServer.aspx?siteID=xgate")
    Call<ResponseBody> adyenPaySetup(@QueryMap Map<String, Object> param);

    /**
     * paypal getToken
     * accountID
     * token
     */
    @POST("/app/apiGenPaypalToken.aspx")
    Call<ResponseBody> apiGetPaypalToken(@QueryMap(encoded = true) Map<String, Object> param);

    /**
     * paypal pay
     * accountID
     * token
     * payment_method_nonce
     * amt
     */
    @POST("   ")
    Call<ResponseBody> apiPaypalPay(@QueryMap(encoded = true) Map<String, Object> param);
    //##################################################################
    //#########               2018/2/12 支付
    //##################################################################

    /**
     * term
     */
    @POST("/prodSearchkey.aspx")
    Call<ResponseBody> netAutoComplete(@QueryMap Map<String, Object> param);

}
