package rxandroidapp.com.etognfd.testface.util;


import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class KeyUtil {

    public static String API_KEY="hGSIgfBCbo2dlaju_NnJ4GXMEYmtIoag";
    public static String API_SECRET="5_lpUC0-t_xZHRJctevst93UV8h5mWZG";

    public static boolean isReadKey(Context context) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count = -1;
        try {
            inputStream = context.getAssets().open("key");
            while ((count = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, count);
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String str = new String(byteArrayOutputStream.toByteArray());
        String authKey = "hGSIgfBCbo2dlaju_NnJ4GXMEYmtIoag";
        String authScrect = "5_lpUC0-t_xZHRJctevst93UV8h5mWZG";
        try {
            String[] strs = str.split(";");
            authKey = strs[0].trim();
            authScrect = strs[1].trim();
        } catch (Exception e) {
        }
        API_KEY = authKey;
        API_SECRET = authScrect;
        if (API_KEY == null || API_SECRET == null)
            return false;

        return true;
    }


}
