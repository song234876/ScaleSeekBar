# ScaleSeekBar
[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg)](https://android-arsenal.com/api?level=23) 
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![](https://jitpack.io/v/song234876/ScaleSeekBar.svg)](https://jitpack.io/#song234876/ScaleSeekBar)
#### 介绍
这是一个带刻度值seerkbar,高度自适应，不需要设置高度，可以直接使用wrapcontent属性，支持自定义，可能设置最大、最小刻度值、初始刻度值、刻度值大小、刻度单位、刻度换算值，可以设置滑动监听。

<p align="center">
	<img src="https://github.com/song234876/ScaleSeekBar/blob/master/preview/show.gif" alt="Sample"  width="35%" height="35%">
	<p align="center">
		<em>运行效果图</em>
	</p>
</p>

#### 添加依赖
步骤1 添加Jitpack仓库地址在project级的build.gradle文件中:
```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
步骤2 添加项目依赖
```java
dependencies {
    implementation 'com.github.song234876:ScaleSeekBar:1.0.0'
}
```

#### 使用说明
布局文件
```java
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
```kotlin
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
|eachScaleValue|float|每个刻度代表的值|
|firstScaleValue|float|初始刻度值|
|limitMinValue|float|可以滑动的最小值|
|maxScaleValue|float|最大刻度值|
|minScaleValue|float|最小刻度值|
|unitName|string|单位名称|
|unitWeight|int|大小刻度换算值(1cm=10mm)|
