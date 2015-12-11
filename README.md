# SwipeBackHelper  

[中文](https://github.com/Jude95/SwipeBackHelper/blob/master/README.md) | [English](https://github.com/Jude95/SwipeBackHelper/blob/master/README_en.md)

有与微信相同的下级activity联动效果。  
滑动部分与Activity解耦.在自己的Activity中3个生命周期中加一行代码即可。  
实现原理，在Activity的Window的decorView与它的子View中间插入一个View来进行手势处理与位移效果。  

部分代码修改自[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)  
![swipeback.png](swipeback.gif)

##依赖
`compile 'com.jude:swipebackhelper:2.1.4'`

##配置
在`style.xml`中添加

    <item name="android:windowIsTranslucent">true</item>
    <item name="android:activityOpenEnterAnimation">@anim/slide_in_right</item>
    <item name="android:activityCloseExitAnimation">@anim/slide_out_right</item>

**注意:MIUI特殊处理**  给你的主Activity(永远在最底层不会滑动关闭)单独设置一个主题

    <style name="MainTheme" parent="AppTheme">
        <item name="android:windowIsTranslucent">false</item>
    </style>

在你的Activity中添加一下几个生命周期，即可。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

如果需要可在`SwipeBackHelper.onCreate()`之后进行如下参数设置：

    SwipeBackHelper.getCurrentPage(this)//获取当前页面
        .setSwipeBackEnable(true)//设置是否可滑动
        .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
        .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
        .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
        .setScrimColor(Color.BLUE)//底层阴影颜色
        .setClosePercent(0.8f)//触发关闭Activity百分比
        .setSwipeRelateEnable(false)//是否与下一级activity联动(微信效果)。默认关
        .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
        .addListener(new SwipeListener() {//滑动监听

            @Override
            public void onScroll(float percent, int px) {//滑动的百分比与距离
            }

            @Override
            public void onEdgeTouch() {//当开始滑动
            }

            @Override
            public void onScrollToClose() {//当滑动关闭
            }
        });

License
-------

    Copyright 2015 Jude

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
