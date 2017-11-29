package rxandroidapp.com.etognfd.testface.retrofit.api.bean;

/**
 * Created by 14537 on 2017/11/22.
 * {"code":1070,"message":"用户认证失败"}
 * {
 "code": 2005,
 "data": [
 {
 "captcha": true,
 "name": "login_type",
 "label": "登录",
 "fields": [
 {
 "name": "user_name",
 "label": "邮箱/手机号/身份证号",
 "type": "text"
 },
 {
 "name": "user_pass",
 "label": "密码",
 "type": "password"
 }
 ],
 "value": "0"
 }
 ],
 "message": "登录要素请求成功",
 "task_id": "TASKCHSI0000001511333588197429132"
 }
 */

public class BaseBean {
    private String code;
    private String message;
    private String task_id;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
