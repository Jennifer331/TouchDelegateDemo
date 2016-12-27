package cn.leixiaoyue.touchdelegatedemo;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View mLarge;
    private View mMiddle;
    private View mTarget;
    private TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView() {
        mTitle = (TextView)findViewById(R.id.title);
        mTitle.setText("set delegate to parent");
        mLarge = findViewById(R.id.large);
        mMiddle = findViewById(R.id.middle);
        mTarget = findViewById(R.id.target);

        mMiddle.post(new Runnable() {
            @Override
            public void run() {
                Rect delegateArea = new Rect();
                mTarget.getHitRect(delegateArea);

                delegateArea.left -= 150;
                delegateArea.top -= 150;
                delegateArea.right += 150;
                delegateArea.bottom += 150;

                if (View.class.isInstance(mTarget.getParent())) {
                    ((View) mTarget.getParent()).setTouchDelegate(new TouchDelegate(delegateArea, mTarget));
                }
                Log.v("littleHappy", delegateArea.toString());
            }
        });
    }

    private void initListener() {
        mTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
}
