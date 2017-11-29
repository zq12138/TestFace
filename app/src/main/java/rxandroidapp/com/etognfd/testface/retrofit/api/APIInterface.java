package rxandroidapp.com.etognfd.testface.retrofit.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rxandroidapp.com.etognfd.testface.retrofit.api.bean.BaseBean;
import rxandroidapp.com.etognfd.testface.retrofit.api.bean.ImageCodeBean;
import rxandroidapp.com.etognfd.testface.vo.BaseVo;

/**
 * Created by 14537 on 2017/11/17.
 * 通讯录城市
 */

public interface APIInterface {
    @POST("/webdf/df/query")
    @FormUrlEncoded
    Call<BaseVo.DeviceInfo> query(@Field("partnerId") String partnerId, @Field("verifyKey") String verifyKey,
                                  @Field("platform") String platform, @Field("tokenKey") String tokenKey);

    @POST("/webdf/device/query")
    @FormUrlEncoded
    Call<BaseVo.DeviceInfo> query(@Field("partnerId") String partnerId, @Field("verifyKey") String verifyKey,
                                  @Field("platform") String platform, @Field("tokenKey") String tokenKey, @Field("fetchCallRecords") String fetchCallRecords);


    @POST("/clweb/api/hfund/supportcity")
    @FormUrlEncoded
    Call<String> queryCity(@Field("partnerId") String partnerId);

    //partner_code=合作方标识&partner_key=合作方密钥
//    channel_type	String	必填 渠道类型。运营商是 YYS
//    channel_code	String	必填 渠道编码。运营商是 100000
//    real_name	String	必填 真实姓名。支持中文、英文和"."，不支持其他特殊字
//    identity_code	String	必填 身份证号码。18位和15位数字，末尾是数字或X。
//    字段名称	类型	字段说明
//    user_mobile	String	必填 手机号码，11位数字。
//    passback_params	String	可选 透传参数。用于异步回调的透传参数，字符长度小于
    @POST("/octopus/task.unify.create/v3")
    @FormUrlEncoded
    Call<String> querySJMH(@Field("partner_code") String partner_code,
                           @Field("partner_key") String partner_key,
                           @Field("channel_type") String channel_type,
                           @Field("channel_code") String channel_code,
                           @Field("real_name") String real_name,
                           @Field("identity_code") String identity_code,
                           @Field("user_mobile") String user_mobile
//                          , @Field("passback_params") String passback_params
    );

    /**
     * 数聚魔盒
     */
    @POST("/octopus/login.fields.query/v1")
    @FormUrlEncoded
    Call<BaseBean> queryLearn(
            @Field("partner_code") String partner_code,
            @Field("partner_key") String partner_key,
            @Field("channel_type") String channel_type,
            @Field("channel_code") String channel_code);

    @POST("/octopus/chsi.captcha/v1")
    @FormUrlEncoded
    Call<ImageCodeBean> queryCaptcha(
            @Field("partner_code") String partner_code,
            @Field("partner_key") String partner_key,
            @Field("task_id") String task_id);

    @POST("/octopus/chsi.acquire/v1")
    @FormUrlEncoded
    Call<BaseBean> queryLogin(
            @Field("partner_code") String partner_code,
            @Field("partner_key") String partner_key,
            @Field("task_id") String task_id,
            @Field("user_name") String user_name,
            @Field("user_pass") String user_pass,
            @Field("auth_code") String user_code
    );

    @POST("/octopus/task.query/v1")
    @FormUrlEncoded
    Call<BaseBean> querySelect(
            @Field("partner_code") String partner_code,
            @Field("partner_key") String partner_key,
            @Field("task_id") String task_id
    );

}
