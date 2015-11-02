package com.jude.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jude.swipbackhelper.SwipeBackHelper;


public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.open){
            if(Math.random()>1)
                startActivity(new Intent(this,ScrollActivity.class));
            else
                startActivity(new Intent(this,StaticActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
