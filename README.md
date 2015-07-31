# SwipeBackHelper  Activity滑动关闭
代码修改自[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)  
去掉了左滑上滑等不常用功能。  
将滑动部分与Activity解耦.在自己的Activity中加一行代码即可实现。  
解决可设置滑动范围只有半个屏幕的限制。   
![swipeback.png](swipeback.png)

##依赖
`compile 'com.jude:swipebackhelper:1.0.0'`

##使用
在`style.xml`中添加`<item name="android:windowIsTranslucent">true</item>`  
在Activity的`onCreate()`加一句`SwipeBackHelper.prepare(this);`  
OK.

