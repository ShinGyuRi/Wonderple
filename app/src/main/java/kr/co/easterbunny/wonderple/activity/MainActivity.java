package kr.co.easterbunny.wonderple.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.library.ParentActivity;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
