package com.magicbeans.xgate.net.nethelper;

import android.text.TextUtils;

import com.ins.common.utils.DateUtil;
import com.ins.common.utils.TimeUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.ParamHelper;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.utils.CipherUtil;

import java.util.Date;
import java.util.Map;

/**
 * Created by Marie on 2018/1/22.
 * 管理token刷新及获取用户信息的网络请求
 */

public class NetTokenHelper {
    private static NetTokenHelper instance;

    private NetTokenHelper() {
    }

    public static synchronized NetTokenHelper getInstance() {
        if (instance == null) instance = new NetTokenHelper();
        return instance;
    }

    /////////////////////////////////////

    //更新用户信息
    public void netUpdateUserProfile(final String nickname, final String surname, final String Gender, final Date birthday, final UserProfileCallback callback) {
        Token token = AppData.App.getToken();
        if (token == null) return;
        Map<String, Object> param = new NetParam()
                .put("accountID", token.getAccountID())
                .put("token", token.getToken())
                .put("nickname", nickname)
                .put("surname", surname)
                .put("Gender", Gender)
                .put("dbDay", DateUtil.getDay(birthday))
                .put("dbMonth", DateUtil.getMouth(birthday))
                .put("birthdayYear", DateUtil.getYear(birthday))
                .build();
        NetApi.NI().updateUserProfile(param).enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (com.getReponseCode() != 1) {
                    if (callback != null) {
                        User user = AppData.App.getUser();
                        if (!TextUtils.isEmpty(nickname)) user.setNickname(nickname);
                        if (!TextUtils.isEmpty(surname)) user.setSurname(surname);
                        if (!TextUtils.isEmpty(Gender)) user.setGender(Gender);
                        if (!TextUtils.isEmpty(Gender))
                            user.setDayOfBirthday(DateUtil.getDay(birthday) + "");
                        if (!TextUtils.isEmpty(Gender))
                            user.setMonthOfBirthday(DateUtil.getMouth(birthday) + "");
                        if (!TextUtils.isEmpty(Gender))
                            user.setYearOfBirthday(DateUtil.getYear(birthday) + "");
                        callback.onSuccess(status, user, msg);
                    }
                } else {
                    onError(com.getReponseCode(), "修改失败：reponseCode:" + com.getReponseCode());
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status, msg);
            }
        });
    }

    //使用token获取用户信息
    public void netGetUserProfile(final String accountID, final String token, final UserProfileCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("accountID", accountID)
                .put("token", token)
                .build();
        NetApi.NI().getUserProfile(param).enqueue(new STCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                if (callback != null) {
                    if (TextUtils.isEmpty(user.getResponseCode())) {
                        callback.onSuccess(status, user, msg);
                    } else if ("102".equals(user.getResponseCode())) {
                        //TODO:如果getResponseCode是102代表token失效。这时应该刷新token再重新调用接口
                        netRefreshToken(new RefreshTokenCallback() {
                            @Override
                            public void onSuccess(String newToken) {
                                netGetUserProfile(accountID, newToken, callback);
                            }
                        });
                    } else {
                        callback.onError(Integer.parseInt(user.getResponseCode()), user.getResponseMsg());
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status, msg);
            }
        });
    }

    //刷新token
    public void netRefreshToken(final RefreshTokenCallback callback) {
        String deviceId = "a12d5d9993241b00cf9a2070484f57ca";
        String deviceType = ParamHelper.getDeviceType();
        String accountID = Token.getLocalAccountId();
        String token = Token.getLocalToken();
        String time = TimeUtil.getTimeFor(" yyyyMMddHHmmss", new Date());
        String encryptData = CipherUtil.getInstance().encryptRefreshTokenData(accountID, deviceId, deviceType, time, token);

        Map<String, Object> param = new NetParam()
                .put("deviceId", deviceId)
                .put("deviceType", deviceType)
                .put("accountID", accountID)
                .put("token", token)
                .put("time", time)
                .put("encryptData", encryptData)
                .build();
        NetApi.NI().refreshToken(param).enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (com.getReponseCode() == 0) {
                    //token刷新成功后立即存储
                    Token saveToken = AppData.App.getToken();
                    saveToken.setToken(com.getToken());
                    AppData.App.saveToken(saveToken);
                    if (callback != null) callback.onSuccess(com.getToken());
                } else {
                    onError(com.getReponseCode(), "刷新token失败:" + com.getReponseCode());
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    public interface UserProfileCallback {
        void onSuccess(int status, User user, String msg);

        void onError(int status, String msg);
    }

    public interface RefreshTokenCallback {
        void onSuccess(String newToken);
    }
}
