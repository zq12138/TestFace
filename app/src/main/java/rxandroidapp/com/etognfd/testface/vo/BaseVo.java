package rxandroidapp.com.etognfd.testface.vo;

/**
 * Created by 14537 on 2017/11/20.
 * 设备
 */

public class BaseVo {
    private String resultCode;
    private String resultDesc;
    private DeviceInfo resultData;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public DeviceInfo getResultData() {
        return resultData;
    }

    public void setResultData(DeviceInfo resultData) {
        this.resultData = resultData;
    }

    public class DeviceInfo {
        private String deviceId;
        private String smartId;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getSmartId() {
            return smartId;
        }

        public void setSmartId(String smartId) {
            this.smartId = smartId;
        }
    }
}

