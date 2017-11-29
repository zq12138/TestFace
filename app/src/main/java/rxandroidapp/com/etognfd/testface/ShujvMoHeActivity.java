package rxandroidapp.com.etognfd.testface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rxandroidapp.com.etognfd.testface.retrofit.api.Config;
import rxandroidapp.com.etognfd.testface.retrofit.api.RequestCommand;
import rxandroidapp.com.etognfd.testface.retrofit.api.ServiceCreate;
import rxandroidapp.com.etognfd.testface.retrofit.api.bean.BaseBean;
import rxandroidapp.com.etognfd.testface.retrofit.api.bean.ImageCodeBean;

/**
 * Created by 14537 on 2017/11/21.
 * https://api.shujumohe.com/octopus/task.unify.create/v3" +
 * "?partner_code=qixinjr_mohe&partner_key=307b65dfe55040ca89be17729f9066a0
 * https://open.shujumohe.com/box/yys?box_token=3BDA012XXXXXXXXXX
 * cb=https://www.test.com/path1/path2
 * <p>checkAuthentication fail
 * https://api.shujumohe.com/octopus/task.unify.create/v3?partner_code=合作方标识&partner_key=合作方密钥 =合作方标识
 *能把18310863427 这个账号的认证信息改成 央安 这个吗（icbc 央安   4417125253755080 身份证 540929195603253956 718290 借记卡 1000000） 并且把绑定银行卡认证通过
 */
public class ShujvMoHeActivity extends AppCompatActivity implements View.OnClickListener {
    WebView webView;
    Button btn_login, btn_image, btn_certification, btn_select;
    ImageView imageV;
    EditText edit_imagecode, edit_info, edit_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sjme_layout);
        imageV = (ImageView) findViewById(R.id.imageView);
        edit_imagecode = (EditText) findViewById(R.id.edit_imagecode);
        edit_info = (EditText) findViewById(R.id.edit_info);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_image = (Button) findViewById(R.id.btn_image);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_certification = (Button) findViewById(R.id.btn_certification);
        btn_login.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        btn_certification.setOnClickListener(this);
        btn_select.setOnClickListener(this);

//        Config.URL = "https://api.shujumohe.com";
        //partner_code=合作方标识&partner_key=合作方密钥
//    channel_type	String	必填 渠道类型。运营商是 YYS
//    channel_code	String	必填 渠道编码。运营商是 100000
//    real_name	String	必填 真实姓名。支持中文、英文和"."，不支持其他特殊字
//    identity_code	String	必填 身份证号码。18位和15位数字，末尾是数字或X。
//    字段名称	类型	字段说明
//    user_mobile	String	必填 手机号码，11位数字。
//    passback_params	String	可选 透传参数。用于异步回调的透传参数，字符长度小于
//        Call<String> call = ServiceCreate.getSJMHServiceCreate().
//                querySJMH("qixinjr_mohe", "307b65dfe55040ca89be17729f9066a0",
//                        "YYS", "100000", "张强", "372922199408217873", "18310863427");
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.e("tag", "---" + response.message());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//            }
//        });
//        webView = (WebView) findViewById(R.id.web_view);
//        setting();
//        webView.loadUrl("https://open.shujumohe.com/box/yys?box_token=3BDA012XXXXXXXXXX&cb=https://www.test.com/path1/path2&real_name=张强&identity_code=372922199408217873");
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i("tag", "----"+url);
//                return true;
//            }
//        });
    }

    private void setting() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
//支持插件
//        webSettings.setPluginsEnabled(true);
//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                requestToken();
                break;
            case R.id.btn_image:
                requestImageCode();
                break;
            case R.id.btn_certification:
                requestLogin();
                break;
            case R.id.btn_select:
                requestUserInfo();
                break;
        }
    }

    private void requestUserInfo() {

        Call<BaseBean> userInfo = ServiceCreate.createSJMHApi().querySelect("qixinjr_mohe",
                "307b65dfe55040ca89be17729f9066a0",
                aseBean.getTask_id());

        userInfo.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                Toast.makeText(ShujvMoHeActivity.this, "--" + aseBean.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {

            }
        });

    }

    private void requestLogin() {

        Call<BaseBean> user = ServiceCreate
                .createSJMHApi()
                .queryLogin("qixinjr_mohe",
                        "307b65dfe55040ca89be17729f9066a0",
                        aseBean.getTask_id(),
                        edit_info.getText().toString(),
                        edit_pwd.getText().toString(),
                        edit_imagecode.getText().toString()
                );
        user.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                Toast.makeText(ShujvMoHeActivity.this, "--" + aseBean.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {

            }
        });

    }

    BaseBean aseBean;

    private void requestToken() {
        Call<BaseBean> callLearn = ServiceCreate.createSJMHApi().queryLearn("qixinjr_mohe", "307b65dfe55040ca89be17729f9066a0", "CHSI", "000000");
        callLearn.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                aseBean = response.body();
                Toast.makeText(ShujvMoHeActivity.this, "--" + aseBean.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                Log.i("ttt", "-----------------------");
                t.printStackTrace();
            }
        });

    }

    private void requestImageCode() {
        Call<ImageCodeBean> callImageBean =
                ServiceCreate
                        .createSJMHApi()
                        .queryCaptcha("qixinjr_mohe", "307b65dfe55040ca89be17729f9066a0", aseBean.getTask_id());
        callImageBean.enqueue(new Callback<ImageCodeBean>() {
            @Override
            public void onResponse(Call<ImageCodeBean> call, Response<ImageCodeBean> response) {
                ImageCodeBean imageCode = response.body();
                byte[] image = Base64.decode(imageCode.getData(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageV.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Call<ImageCodeBean> call, Throwable t) {

            }
        });

    }
}
