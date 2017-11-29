package rxandroidapp.com.etognfd.testface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.DetectionConfig;
import com.megvii.livenessdetection.Detector;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.livenesslib.LivenessActivity;
import com.megvii.livenesslib.util.ConUtil;
import com.megvii.livenesslib.util.SharedUtil;

import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by 14537 on 2017/11/20.
 */

public class FaceIdActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;

    private String uuid;
    private SharedUtil mSharedUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fase_layout);
        init();
        netWorkWarranty();
    }

    /**
     * 设置语言
     */
    @Override
    protected void onResume() {
        super.onResume();
        String language_save = mSharedUtil.getStringValueByKey("language");
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (!language.equals(language_save))
            showLanguage(language);
    }

    protected void showLanguage(String language) {
        // 设置应用语言类
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("zh")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            config.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(config, dm);
        mSharedUtil.saveStringValue("language", language);
        freshView();
    }

    private void freshView() {
        Intent intent = new Intent(this, FaceIdActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void init() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        uuid = ConUtil.getUUIDString(FaceIdActivity.this);
        mSharedUtil = new SharedUtil(FaceIdActivity.this);
    }

    /**
     * 网络授权
     */
    private void netWorkWarranty() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(FaceIdActivity.this);
                LivenessLicenseManager livenessLicenseManager = new LivenessLicenseManager(FaceIdActivity.this);
                manager.registerLicenseManager(livenessLicenseManager);
                manager.takeLicenseFromNetwork(uuid);
                if (livenessLicenseManager.checkCachedLicense() > 0) {
                    mHandler.sendEmptyMessage(1);
                } else {
                    mHandler.sendEmptyMessage(2);
                }
            }
        }).start();

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(FaceIdActivity.this, "网络授权", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(FaceIdActivity.this, "网络授权失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        requestCameraPerm();
    }

    /**
     * 6.0相机权限申请
     */
    private void requestCameraPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        EXTERNAL_STORAGE_REQ_CAMERA_CODE);
            } else {
                enterNextPage();
            }
        } else {
            enterNextPage();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == EXTERNAL_STORAGE_REQ_CAMERA_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {// Permission Granted
                ConUtil.showToast(this, "获取相机权限失败");
            } else
                enterNextPage();
        }
    }
    public static final int EXTERNAL_STORAGE_REQ_CAMERA_CODE = 10;
    private static final int PAGE_INTO_LIVENESS = 100;
    private void enterNextPage() {
        Log.e("tag","enterNextPage");
        startActivityForResult(new Intent(this, LivenessActivity.class), PAGE_INTO_LIVENESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAGE_INTO_LIVENESS && resultCode == RESULT_OK) {
//            String result = data.getStringExtra("result");
//            String delta = data.getStringExtra("delta");
//            Serializable images=data.getSerializableExtra("images");
            Bundle bundle=data.getExtras();
//            ResultActivity.startActivity(this, bundle);
        }
    }
}
