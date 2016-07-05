# SwipeBackHelper 
[中文](https://github.com/Jude95/SwipeBackHelper/blob/master/README_ch.md) | [English](https://github.com/Jude95/SwipeBackHelper/blob/master/README.md)

Only three lines of code makes activity swipe close.
Part of the code modified from [SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)  

![swipeback.png](swipeback.gif)

## Dependency
```groovy
compile 'com.jude:swipebackhelper:3.1.2'
```

##Usage
Add this to`style.xml`:
```xml
    //if you don't set this the background will be black when you slide.
    <item name="android:windowIsTranslucent">true</item>
    //set the right in/out animation of Activity,you can change this to yours
    <item name="android:windowAnimationStyle">@style/SlideRightAnimation</item>
```
**Attention**  set up a separate theme for your main activity which should be at bottom and can't be swipe.and set the Activity no swipe by `setSwipeBackEnable(false)`
```xml
    <style name="MainTheme" parent="AppTheme">
        <item name="android:windowIsTranslucent">false</item>
    </style>
```
Add this code into your activity's lifecycle。
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
If you need more setting,you can use this after `SwipeBackHelper.onCreate()`：
```java
    SwipeBackHelper.getCurrentPage(this)//get current instance
        .setSwipeBackEnable(true)//on-off 
        .setSwipeEdge(200)//set the touch area。200 mean only the left 200px of screen can touch to begin swipe.
        .setSwipeEdgePercent(0.2f)//0.2 mean left 20% of screen can touch to begin swipe.
        .setSwipeSensitivity(0.5f)//sensitiveness of the gesture。0:slow  1:sensitive
        .setScrimColor(Color.BLUE)//color of Scrim below the activity
        .setClosePercent(0.8f)//close activity when swipe over this 
        .setSwipeRelateEnable(false)//if should move together with the following Activity
        .setSwipeRelateOffset(500)//the Offset of following Activity when setSwipeRelateEnable(true)
.setDisallowInterceptTouchEvent(true)//your view can hand the events first.default false;
        .addListener(new SwipeListener() {

            @Override
            public void onScroll(float percent, int px) {
            }

            @Override
            public void onEdgeTouch() {
            }

            @Override
            public void onScrollToClose() {
            }
        });
```
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
