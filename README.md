# ScaleSeekBar

#### 效果图

![show.gif](https://github.com/song234876/ScaleSeekBar/blob/master/preview/show.gif)

<p align="center">
	<img src="https://img-blog.csdn.net/20180904102458446?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xvdmVjaHJpczAw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70" alt="Sample"  width="250" height="140">
	<p align="center">
		<em>图片示例2</em>
	</p>
</p>
 ———————————————— 
版权声明：本文为CSDN博主「伊织__」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/lovechris00/article/details/82379382

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
