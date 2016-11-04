# SwipeBackHelper  
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwipeBackHelper-green.svg?style=true)](https://android-arsenal.com/details/1/3611)  

[中文](https://github.com/Jude95/SwipeBackHelper/blob/master/README_ch.md) | [English](https://github.com/Jude95/SwipeBackHelper/blob/master/README.md)

有与微信相同的下级activity联动效果。  
滑动部分与Activity解耦.在自己的Activity中3个生命周期中加一行代码即可。  
实现原理，在Activity的Window的decorView与它的子View中间插入一个View来进行手势处理与位移效果。  

部分代码修改自[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)  
![swipeback.png](swipeback.gif)

##依赖
```groovy
compile 'com.jude:swipebackhelper:3.1.2'
```
##配置
在`style.xml`中添加
```xml
    //背景透明，不设滑动关闭时背景就是黑的。
    <item name="android:windowIsTranslucent">true</item>
    //Activity右滑进出的动画，觉得这个不好看随便换成自己的
    <item name="android:windowAnimationStyle">@style/SlideRightAnimation</item>
```
**注意:MIUI特殊处理**  给你的主Activity(永远在最底层不会滑动关闭)单独设置一个主题
```xml
    <style name="MainTheme" parent="AppTheme">
        <item name="android:windowIsTranslucent">false</item>//就是关掉这个Activity的透明背景
    </style>
```
再手动关闭这个页面的滑动关闭,使用`setSwipeBackEnable(false)`。

在你的Activity中添加一下几个生命周期，即可。
```java
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
```
如果需要可在`SwipeBackHelper.onCreate()`之后进行如下参数设置：
```java
    SwipeBackHelper.getCurrentPage(this)//获取当前页面
        .setSwipeBackEnable(true)//设置是否可滑动
        .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
        .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
        .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
        .setScrimColor(Color.BLUE)//底层阴影颜色
        .setClosePercent(0.8f)//触发关闭Activity百分比
        .setSwipeRelateEnable(false)//是否与下一级activity联动(微信效果)。默认关
        .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
        .setDisallowInterceptTouchEvent(true)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
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
```
License
-------

    Copyright 2016 Jude

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
