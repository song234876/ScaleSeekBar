# ScaleSeekBar

#### 效果图

#### 添加依赖
步骤1 添加Jitpack仓库地址在project级的build.gradle文件中:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
步骤2 添加项目依赖
```
dependencies {
    implementation 'com.github.song234876:ScaleSeekBar:1.0.0'
}
```

#### 使用说明
布局文件
```
<com.wss.library.ScaleSeekBar
            android:id="@+id/mSpeedSb"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:eachScaleValue="0.2"
            app:firstScaleValue="1"
            app:limitMinValue="0.2"
            app:maxScaleValue="4"
            app:minScaleValue="0"
            app:unitName="x"
            app:unitWeight="5" />
```
设置监听
```
mSpeedSb.setOnSeekChangeListener(object :ScaleSeekBar.OnSeekChangeListener{
            override fun onSeek(progress: Float) {
                Log.d("wss","onSeek$progress")
            }

            override fun OnSeekEnd(progress: Float) {
                Log.d("wss","OnSeekEnd")
            }
        })
```

自定义属性列表

|属性名|属性类型|含义|  
|:--:|:--:|:--:|
|eachScaleValue|float|每个刻度代表的间隔值|
|firstScaleValue|float|第一次显示时的刻度值|
|limitMinValue|float|可以滑动的最小值|
|maxScaleValue|float|最大刻度值|
|minScaleValue|float|最小刻度值|
|unitName|string|单位名称|
|unitWeight|int|大小刻度换算值(1cm=10mm)|
