package cn.zeffect.demo.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

import cn.zeffect.demo.okhttputils.OkHttpUtils;


public class MainActivity extends Activity implements View.OnClickListener {
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }


    private void testGet() {
        HashMap<String, String> param = new HashMap<>();
        param.put("action", "register");
        param.put("username", "11");
        param.put("password", "11");
        param.put("md5", "123456789");
        OkHttpUtils.get("http://api.zeffect.cn/interface.php", null, param, handler);
    }

    private void testPostParam() {
        HashMap<String, String> param = new HashMap<>();
        param.put("action", "register");
        param.put("username", "11");
        param.put("password", "11");
        param.put("md5", "123456789");
        OkHttpUtils.postParam("http://api.zeffect.cn/interface.php", null, param, handler);
    }

    private void testPostJson() {
    }

    private void testUpload() {

    }

    private void testDownload() {
        OkHttpUtils.download("http://i4.piimg.com/7572/b3f1f92014580003.jpg", Environment.getExternalStorageDirectory().getAbsolutePath(), System.currentTimeMillis() + ".jpg", handler);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                mTextView.setText(mTextView.getText().toString().trim() + "\n" + msg.obj.toString().trim());
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                testGet();
                break;
            case R.id.button2:
                testPostParam();
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.button5:
                testDownload();
                break;
        }
    }
}
