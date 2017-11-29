package rxandroidapp.com.etognfd.testface;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.bqs.risk.df.android.OnBqsDFContactsListener;
import com.bqs.risk.df.android.OnBqsDFListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rxandroidapp.com.etognfd.testface.bqs.Global;
import rxandroidapp.com.etognfd.testface.bqs.PermissionUtils;
import rxandroidapp.com.etognfd.testface.retrofit.api.Config;
import rxandroidapp.com.etognfd.testface.retrofit.api.RequestCommand;
import rxandroidapp.com.etognfd.testface.retrofit.api.ServiceCreate;
import rxandroidapp.com.etognfd.testface.vo.BaseVo;

/**
 * Created by 14537 on 2017/11/17.
 */

public class BaiQiShiActivity extends AppCompatActivity implements OnBqsDFListener {
    //获取6.0运行时权限列表，第一个参数：是否授权gps，第二个参数：是否授权通讯录，第三个参数：是否授权通话记录
    String[] requestPermissions = BqsDF.getRuntimePermissions(true, true, true);
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bqs_main);
        PermissionUtils.requestMultiPermissions(this, requestPermissions, mPermissionGrant);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPost();
            }
        });


    }


    public void getPost() {

        Call<BaseVo.DeviceInfo> call = ServiceCreate.getBQSServiceCreate().query("demo", "8d1ac2011a7d4485ad151d892ce9549a", "android", BqsDF.getTokenKey(), "true");
        call.enqueue(new Callback<BaseVo.DeviceInfo>() {
            @Override
            public void onResponse(Call<BaseVo.DeviceInfo> call, Response<BaseVo.DeviceInfo> response) {
                Log.i("tag", "----");

            }

            @Override
            public void onFailure(Call<BaseVo.DeviceInfo> call, Throwable t) {

            }
        });
    }

    /**
     * 授权结果，该回调不管权限是拒绝还是同意都会进入该回调方法
     */
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, String[] requestPermissions) {
            Global.authRuntimePermissions = true;
            /**
             * 因为在启动页进行运行时权限授权，所以要在授权结果回调中出发一次初始化
             */
            initBqsDFSDK();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(requestCode, requestPermissions, grantResults, requestPermissions, mPermissionGrant);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * Global.authRuntimePermissions:用于判断运行时权限是否授权
         * BqsDF.canInitBqsSDK()：用于控制不被频繁初始化。如果设备指纹采集成功了，app不被kill的情况下30分钟内不会重复提交设备信息
         */
        if (Global.authRuntimePermissions && BqsDF.canInitBqsSDK()) {
            initBqsDFSDK();
        }
    }

    protected void initBqsDFSDK() {
        //1、添加设备信息采集回调
        BqsDF.setOnBqsDFListener(this);
        BqsDF.setOnBqsDFContactsListener(new OnBqsDFContactsListener() {
            @Override
            public void onGatherResult(boolean gatherStatus) {
                Log.i("tag", "通讯录采集状态 gatherStatus=" + gatherStatus);
            }

            @Override
            public void onSuccess(String tokenKey) {
                Log.i("tag", "通讯录采集成功");
            }

            @Override
            public void onFailure(String resultCode, String resultDesc) {
                Log.i("tag", "通讯集失败 resultCode=" + resultCode + " resultDesc=" + resultDesc);
            }
        });
        //BqsDF.setOnBqsDFCallRecordListener(...);

        //2、配置初始化参数
        BqsParams params = new BqsParams();
        params.setPartnerId("8d1ac2011a7d4485ad151d892ce9549a");//商户编号
        params.setTestingEnv(false);//false是生产环境 true是测试环境
        params.setGatherGps(true);
        params.setGatherContact(false);
        params.setGatherCallRecord(true);
        //3、执行初始化
        BqsDF.initialize(this, params);
        //采集通讯录,第一个参数：是否采集通讯录，第二个参数：是否采集通话记录
        BqsDF.commitContactsAndCallRecords(false, false);
        BqsDF.commitLocation();
        //BqsDF.commitLocation(longitude, latitude);
        //4、提交tokenkey到贵公司服务器
        String tokenkey = BqsDF.getTokenKey();
        //注意：上传tokenkey时最好再停留几百毫秒的时间（给SDK预留上传设备信息的时间）
        new CountDownTimer(500, 500) {
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                submitTokenkey();
            }
        }.start();
    }
    /**
     * 提交tokenkey到贵公司服务器
     */
    private void submitTokenkey() {
        String tokenkey = BqsDF.getTokenKey();
        Log.i("tag", "提交tokenkey:" + tokenkey);
        //发起Http请求
        //....
    }

    @Override
    public void onSuccess(String s) {
        //回调的tokenkey和通过BqsDF.getTokenKey()拿到的值都是一样的
        Log.i("tag", "白骑士SDK采集设备信息成功 tokenkey=" + s);
    }

    @Override
    public void onFailure(String s, String s1) {
        Log.i("tag", "白骑士SDK采集设备信息失败 resultCode=" + s + " resultDesc=" + s1);
    }
}
