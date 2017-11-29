package rxandroidapp.com.etognfd.testface.vo;

import java.io.Serializable;

/**
 * Created by 14537 on 2017/11/24.
 */

public class RotateBean implements Serializable {

    private int imgId;
    private String imgUrl;

    public RotateBean(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
