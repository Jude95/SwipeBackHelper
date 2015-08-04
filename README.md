# SwipeBackHelper  Activity滑动关闭
代码修改自[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)  
去掉了左滑上滑等不常用功能。  
将滑动部分与Activity解耦.在自己的Activity中3个生命周期中加一行代码即可
解决可设置滑动范围只有半个屏幕的限制。   
![swipeback.png](swipeback.png)

##依赖
`compile 'com.jude:swipebackhelper:1.1.0'`

##配置
在`style.xml`中添加`<item name="android:windowIsTranslucent">true</item>`
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

如果需要可以进行如下参数设置，如下：

        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。百分比。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(Color.BLUE)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
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

