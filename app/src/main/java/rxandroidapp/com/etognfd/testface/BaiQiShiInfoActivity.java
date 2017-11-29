package rxandroidapp.com.etognfd.testface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.RequestParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rxandroidapp.com.etognfd.testface.retrofit.api.Config;
import rxandroidapp.com.etognfd.testface.retrofit.api.RequestCommand;
import rxandroidapp.com.etognfd.testface.retrofit.api.ServiceCreate;

/**
 * Created by 14537 on 2017/11/20.
 */

public class BaiQiShiInfoActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bqsinfo);
        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPost();
            }
        });
    }


    private void getPost() {
        Call<String> call = ServiceCreate.getServiceCreate().queryCity("demo");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("tag","-----");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
