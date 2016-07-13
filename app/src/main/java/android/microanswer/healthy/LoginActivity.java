package android.microanswer.healthy;

import android.content.Intent;
import android.microanswer.healthy.bean.User;
import android.microanswer.healthy.tools.InternetServiceTool;
import android.microanswer.healthy.view.SimpleViewFocusHinter;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener, CheckBox.OnCheckedChangeListener, View.OnFocusChangeListener {
    private static final String loginurl = "http://www.tngou.net/api/oauth2/login";//用户登陆
    private static final String userurl = "http://www.tngou.net/api/user";//获取用户信息
    public static final int REQUEST_LOGIN = 4;

    private ActionBar actionbar;
    private EditText etaccount, et_pwd;
    private TextView forgotpwd, noaccount;
    private Button submit;
    private ImageView qq, sina;
    private CheckBox seepwd;

    public static final String userObjectFileName = "user.data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
    }

    private void initview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_login_toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setHomeButtonEnabled(true);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        seepwd = (CheckBox) findViewById(R.id.activity_login_checkbox_seepwd);
        seepwd.setOnCheckedChangeListener(this);
        etaccount = (EditText) findViewById(R.id.activity_login_edittext_acount);
        etaccount.setOnFocusChangeListener(this);
        et_pwd = (EditText) findViewById(R.id.activity_login_edittext_password1);
        et_pwd.setOnFocusChangeListener(this);
        forgotpwd = (TextView) findViewById(R.id.activity_login_textview_forgotpwd);
        forgotpwd.setOnClickListener(this);
        noaccount = (TextView) findViewById(R.id.activity_login_textview_noaccount);
        noaccount.setOnClickListener(this);
        noaccount.setOnFocusChangeListener(this);
        submit = (Button) findViewById(R.id.activity_login_submit);
        submit.setOnClickListener(this);
        submit.setOnFocusChangeListener(this);
        qq = (ImageView) findViewById(R.id.activity_login_qq);
        qq.setOnClickListener(this);
        sina = (ImageView) findViewById(R.id.activity_login_sina);
        sina.setOnClickListener(this);
    }

    private String getRightUrl(String account, String pwd) {
        return loginurl + "?client_id=" + client_id + "&client_secret=" + client_secret + "&name=" + account + "&password=" + pwd;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    /**
     * 字符串判断空
     *
     * @param ss
     * @return
     */
    private boolean n(String ss) {
        return TextUtils.isEmpty(ss);
    }


    private final int LOGIN_REQUEST_THREAD = 3;
    private final int GET_USER_INFO_THREAD = 4;

    @Override
    public void onClick(View v) {
        v.requestFocus();
        switch (v.getId()) {
            case R.id.activity_login_submit:
                String name = etaccount.getText().toString().trim();
                if (n(name)) {
                    toast(getString(R.string.acounthint), POSOTION_TOP);
                    return;
                }
                String pwd = et_pwd.getText().toString().trim();
                if (n(pwd)) {
                    toast(getString(R.string.pwdhint), POSOTION_TOP);
                    return;
                }
                submit.setText(getString(R.string.logining));
                submit.setEnabled(false);
                final String url = getRightUrl(name, pwd);
                runOnOtherThread(new OtherThreadTask<String, Object>() {
                    @Override
                    public Map<String, Object> getTaskParams() {
                        Map<String, Object> param = new HashMap<String, Object>();
                        param.put("url", url);
                        param.put("user", User.getUser());
                        return param;
                    }

                    @Override
                    public Message run(Map<String, Object> params) {
                        String murl = (String) params.get("url");
                        User user = (User) params.get("user");
                        String respones = "{}";
                        try {
                            respones = InternetServiceTool.request(murl);
                            try {
                                respones = respones == null ? "{}" : respones;
                                JSONObject jo = new JSONObject(respones);
                                if (jo.getBoolean("status")) {
                                    user.setAccess_token(jo.getString("access_token"));
                                    user.setRefresh_token(jo.getString("refresh_token"));
                                    user.setId(jo.getInt("id"));
                                } else {
                                    respones = jo.getString("msg");
                                }
                            } catch (Exception e) {
//                                e.printStackTrace();
                                respones = getString(R.string.login_error) + "\n[服务器连接失败]";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Map<String, Object> datas = new HashMap<String, Object>();
                        datas.put("res", respones);
                        datas.put("user", user);
                        Message message = new Message();
                        message.obj = datas;
                        return message;
                    }
                }, LOGIN_REQUEST_THREAD);


                break;
            case R.id.activity_login_qq:
                break;
            case R.id.activity_login_sina:
                break;
            case R.id.activity_login_textview_noaccount:
                jumpForResultTo(RegActivity.class, true, RegActivity.REG_ACCOUNT);
                break;
            case R.id.activity_login_textview_forgotpwd:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RegActivity.REG_ACCOUNT) {
            toast("注册成功，正在登录", POSOTION_TOP);
            requestuserinfo();
        }

    }


    private void requestuserinfo() {
        runOnOtherThread(new OtherThreadTask<String, String>() {

            @Override
            public Map<String, String> getTaskParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("url", userurl + "?access_token=" + User.getUser().getAccess_token());
                return param;
            }

            @Override
            public Message run(Map<String, String> params) {
                String uurl = params.get("url");
                String res = "";
                ObjectOutputStream objectOutputStream = null;
                try {
                    res = InternetServiceTool.request(uurl);
                    JSONObject jsondata = new JSONObject(res);

                    if (jsondata.getBoolean("status")) {
                        User user = User.getUser();
                        try {
                            user.setId(jsondata.getInt("id"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setAccount(jsondata.getString("account"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        try {
//                            user.setAvatar(jsondata.getString("avatar"));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        try {
                            user.setBirth(new Date(jsondata.getLong("birth")));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setCity(jsondata.getString("city"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setDomain(jsondata.getString("domain"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setEmail(jsondata.getString("email"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setGender(jsondata.getInt("gender"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setHome(jsondata.getString("home"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setIntegral(jsondata.getInt("integral"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setIsemail(jsondata.getInt("isemail"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setIsphone(jsondata.getInt("isphone"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setPhone(jsondata.getString("phone"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setProvince(jsondata.getString("province"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setQq(jsondata.getString("qq"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setSignature(jsondata.getString("signature"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setTime(jsondata.getLong("time"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setTitle(jsondata.getString("title"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setWeibo(jsondata.getString("weibo"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            user.setWeiboid(jsondata.getInt("weiboid"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        objectOutputStream = new ObjectOutputStream(new FileOutputStream(getAppInternalWorkDir().getAbsolutePath() + "/" + userObjectFileName));
                        objectOutputStream.writeObject(user);
                        objectOutputStream.flush();
                        Log.e("LoginActivity",user.toString());
                    } else {
                        try {
                            res = jsondata.getString("msg") + "\n请重试";
                        } catch (Exception e) {
                            res = "我们这边出了一点小问题，请重试";
                        }
                    }


                } catch (Exception e) {
                            e.printStackTrace();
                    res = "服务器连接失败,请重试";
                } finally {
                    try {
                        if (objectOutputStream != null)
                            objectOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Message msg = new Message();
                msg.obj = res;
                return msg;
            }
        }, GET_USER_INFO_THREAD);
    }

    @Override
    public synchronized void onOtherThreadRunEnd(int id, Message msg) {
        super.onOtherThreadRunEnd(id, msg);
        if (id == LOGIN_REQUEST_THREAD) {
            Map<String, Object> datas = (Map<String, Object>) msg.obj;
            submit.setText(getString(R.string.login));
            submit.setEnabled(true);
            if (n(User.getUser().getAccess_token())) {
                toast(datas.get("res").toString(), POSOTION_TOP);//登录失败
            } else {//登录成功
                requestuserinfo();
            }
        } else if (id == GET_USER_INFO_THREAD) {
            String ress = msg.obj.toString();
            if (ress.contains("请重试")) {
                toast(ress, POSOTION_TOP);
            } else {
                setResult(REQUEST_LOGIN);
                finish();
            }

        }
    }

    public void finish() {
        SimpleViewFocusHinter.dismassMaked();
        super.finish();
    }

    private HideReturnsTransformationMethod hideReturnsTransformationMethod = new HideReturnsTransformationMethod();
    private PasswordTransformationMethod passwordTransformationMethod = PasswordTransformationMethod.getInstance();

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            et_pwd.setTransformationMethod(passwordTransformationMethod);
        } else {
            et_pwd.setTransformationMethod(hideReturnsTransformationMethod);
        }

        et_pwd.setSelection(et_pwd.length());

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof EditText) {
            if (hasFocus) {
                SimpleViewFocusHinter.makeSimpleViewFocusHinter(this, v, "" + (v).getContentDescription().toString()).show();
            } else {
                SimpleViewFocusHinter.dismassMaked();
            }
        } else {
            SimpleViewFocusHinter.dismassMaked();
        }
    }
}
