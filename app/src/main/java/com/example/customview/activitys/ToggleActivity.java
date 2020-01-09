package com.example.customview.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.customview.R;
import com.example.customview.toggleview.ToggleView;
import com.example.customview.utils.ToastUtils;

public class ToggleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);
        ((ToggleView) findViewById(R.id.tog_top)).setOnOpenChange(new ToggleView.OnOpenChange() {
            @Override
            public void OpenChange(boolean openstate) {
                if(openstate){
                    ToastUtils.showToast(ToggleActivity.this,"打开111");
                }else {
                    ToastUtils.showToast(ToggleActivity.this,"关闭111");
                }
            }
        });
        ((ToggleView) findViewById(R.id.tog_bottom)).setOnOpenChange(new ToggleView.OnOpenChange() {
            @Override
            public void OpenChange(boolean openstate) {
                if(openstate){
                    ToastUtils.showToast(ToggleActivity.this,"打开222");
                }else {
                    ToastUtils.showToast(ToggleActivity.this,"关闭222");
                }
            }
        });
    }
}
