package cn.leixiaoyue.touchdelegatedemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

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
        mTitle.setText("set delegate to parent's parent");
        mLarge = findViewById(R.id.large);
        mLarge.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mMiddle = findViewById(R.id.middle);
        mMiddle.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mTarget = findViewById(R.id.target);

        mLarge.post(new Runnable() {
            @Override
            public void run() {
                Rect middleDelegateArea = new Rect();
                mMiddle.getHitRect(middleDelegateArea);
                Rect littleDelegateArea = new Rect();
                mTarget.getHitRect(littleDelegateArea);
                Rect delegateArea = new Rect();
                delegateArea.left = middleDelegateArea.left + littleDelegateArea.left;
                delegateArea.top = middleDelegateArea.top + littleDelegateArea.top;
                delegateArea.right = delegateArea.left + littleDelegateArea.width();
                delegateArea.bottom = delegateArea.top + littleDelegateArea.height();

                delegateArea.left -= 150;
                delegateArea.top -= 150;
                delegateArea.right += 150;
                delegateArea.bottom += 150;

                if (View.class.isInstance(mTarget.getParent().getParent())) {
                    ((View) mTarget.getParent().getParent()).setTouchDelegate(new TouchDelegate(delegateArea, mTarget));
                }
                Log.v("littleHappy", delegateArea.toString());
            }
        });
    }

    private void initListener() {
        mTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this, "clicked!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}
