package rxandroidapp.com.etognfd.testface.retrofit.api;

/**
 * Created by 14537 on 2017/11/20.
 */

public class RequestCommand {

    public static APIInterface getServiceApi() {
        return ServiceCreate.getServiceCreate();
    }
}
