package rxandroidapp.com.etognfd.testface;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rong360.app.crawler.CrawlerCallBack;
import com.rong360.app.crawler.CrawlerManager;
import com.rong360.app.crawler.CrawlerStatus;

import java.util.HashMap;

/**
 * Created by 14537 on 2017/11/28.
 */

public class RongActivity extends AppCompatActivity {

    private Button btn_rong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rong);
        initSDK();
        initView();
        initClick();
    }

    private void initClick() {
        btn_rong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRong(RongActivity.this);
            }
        });
    }

    private void testRong(Context context) {
        CrawlerCallBack crawlerCallBack = new CrawlerCallBack() {
            @Override
            public void onStatus(CrawlerStatus crawlerStatus) {
                Toast.makeText(RongActivity.this,crawlerStatus.status+"--", Toast.LENGTH_LONG).show();
            }
        };
        CrawlerStatus crawlerStatus = new CrawlerStatus();
        crawlerStatus.taskid = String.valueOf(System.currentTimeMillis());
        crawlerStatus.type = "alipay";
        crawlerStatus.appname = "rxandroidapp.com.etognfd.testface";
        crawlerStatus.privatekey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALYMr7C+/WH+LUMver/Be1OSvcdtnTaUsyNb/wSOgojGKZ/hkhktAHUyQPzkMLgTEY3+IAk7dSYYI6XsOv1QAE8WcyRp9MeOvlnDxFT03pNivK64Ig874BDdLtY2mC0Gyisx7ZSGgo5jtJeyiP7DPu/TI9B1fKOpALIuqqRengIrAgMBAAECgYEAqp23JvHIbUeXSgk8hGyj1Y6IeFh96AJy5We52J5rLh5yMlpo31lyvWOszpZaJpoQcMBDbAOiZmJnPoDoRDHvJpr9nNRiqbyLpt9Dl4sN6DHFqNvYXiyshmiZuyjoDm8D9cM1OtgWs6nRF0WUA50Hm99H1GxbhDMEbtgVqQFPgbECQQDhfB/fzXVvLShhYad4gKTOvT8BYQEH6Aw98bzBTO64pQ/TECDnDedvTd3Q+YalE1HEidq0/+fVS8dTDikw1js5AkEAzq/BORfYPBhe1yP1hrbN4cBqDTvtPh1oj4zGa+gi5X+aPP8Lj2kzHM+FSGqByY5IGx1m36SZDkBYLVBHLwdUgwJBAMFqT40SGGG9Qp4FvjnHX5dXDeyI4PF/QsD/0wGclEkejWxmsVKAwrze9h6Da9O9tq338frAaIS188xyoTKbEUECQBiLqM/F9CkW39R+8b6g4QxpVG7j6gXRakR5/Gp+M/67S67ovw+195r5TiPOekFcg1WtBTWUD5UwsQgYm4uLPE8CQQDaMH5m9NDAzEO1jt7n1CvXl9TZfK0fyAno0749SqVXuYtEqJITED2j9D1Vb8+LPXYMXPEorKLGk/iEiOuJaaHq";//必要参数 需要申请传入;
        crawlerStatus.merchant_id = "1000072";
        Toast.makeText(RongActivity.this,this.getPackageName(), Toast.LENGTH_LONG).show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user_id", "1234566");
        crawlerStatus.hashMap = params;
        CrawlerManager.getInstance(context).setDebug(true);
        CrawlerManager.getInstance(context).setHost("http://openapi.rong360.com/");
        CrawlerManager.getInstance(context).startCrawlerByType(
                crawlerCallBack, crawlerStatus);
    }

    private void initView() {
        btn_rong = (Button) findViewById(R.id.btn_rong);
    }

    private void initSDK() {
//        CrawlerManager.getInstance(RongActivity.this).init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CrawlerManager.getInstance(RongActivity.this.getApplication()).unregistAllCallBack();
    }
}
