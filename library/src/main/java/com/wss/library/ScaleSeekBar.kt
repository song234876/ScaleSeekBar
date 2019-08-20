package com.wss.library

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import com.wss.library.utils.SizeUtils

/**
 * *******************************
 * 猿代码: Wss
 * Email: wusongsong@nineton.cn
 * 时间轴：2019-06-25 17:44
 * *******************************
 *
 * 描述：
 *
 */
class ScaleSeekBar : View {

    /** 进度条画笔*/
    private var mProgressPaint = Paint()
    /** 进度条画笔颜色*/
    private var mProgressColor = 0
    /** 线条画笔*/
    private var mLinePaint = Paint()
    /** 线条画笔颜色*/
    private var mLineColor = 0
    /** 刻度值画笔*/
    private var mTextPaint = Paint()
    /** 刻度值画笔颜色*/
    private var mTextColor = 0
    /** 默认间距*/
    private var DEFALT_PADDING: Int = SizeUtils.dp2px(10f)
    /** 坐标左边点*/
    private var lineLeft = 0f
    /** 坐标上边点*/
    private var lineTop = 0f
    /** 坐标右边点*/
    private var lineRight = 0f
    /** 坐标下边点*/
    private var lineBottom = 0f
    /** 坐标线宽高度*/
    private var lineWidth = 2f
    /** 坐标圆角值*/
    private var lineCorners = 0f
    /** 刻度值间距*/
    private var scaleDistance = 0f
    /** 大刻度高度*/
    private var bigScaleHeight = SizeUtils.dp2px(10f)
    /** 小刻度高度*/
    private var smallScaleHeight = SizeUtils.dp2px(6f)
    /** 刻度左边点*/
    private var bigScaleLeft = 0f
    /** 刻度上边点*/
    private var bigScaleTop = 0f
    /** 刻度右边点*/
    private var bigScaleRight = 0f
    /** 刻度下边点*/
    private var bigScaleBottom = 0f
    /** 大刻度圆角*/
    private var bigScaleCorners = 0f
    /** 最大刻度值*/
    private var maxScaleValue = 50f
    /** 最小刻度值*/
    private var minScaleValue = 0f
    /** 每个刻度代表单位值*/
    private var eachScaleValue = 1f
    /** 大小刻度换算权重*/
    private var unitWeight = 10
    /** 单位名称*/
    private var unitName = ""
    /** 单位值高度*/
    private var unitAreaHeight = SizeUtils.dp2px(18f)
    /** 单位值高度*/
    private var centerDistance = SizeUtils.dp2px(12f)
    /** 指示器宽度*/
    private var thumbWidth: Int = SizeUtils.dp2px(20f)
    /** 指示器高度*/
    private var thumbHeight: Int = SizeUtils.dp2px(20f)
    private var thumbDrawableId: Int = R.drawable.rsb_default_thumb
    private var thumbBitmap: Bitmap? = null
    private var currPercent: Float = 0f
    private var firstScaleValue = 20f
    private var progressWidth = 0f
    private var viewHeight: Int = 0
    private var viewWidth: Int = 0
    private var touchDownX: Float = 0f
    private var mListener: OnSeekChangeListener? = null
    private var limitMinValue = 10f
    private var limitMinPercent = 0f
    private var limitMaxValue = maxScaleValue
    private var limitMaxPercent = 1f

    constructor(context: Context?) : this(context = context, attrs = null)
    constructor(context: Context?, attrs: AttributeSet?) : this(
        context = context,
        attrs = attrs,
        defStyleAttr = 0
    )
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
        initPaint()
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val array = context!!.obtainStyledAttributes(attrs, R.styleable.ScaleSeekBar)
        mTextColor = array.getColor(R.styleable.ScaleSeekBar_scaleTextColor, Color.parseColor("#A8A8A8"))
        mProgressColor = array.getColor(R.styleable.ScaleSeekBar_scaleFgColor, Color.parseColor("#FFFFFF"))
        mLineColor = array.getColor(R.styleable.ScaleSeekBar_scaleBgColor, Color.parseColor("#61615F"))
        DEFALT_PADDING = array.getDimensionPixelSize(R.styleable.ScaleSeekBar_defaultPadding, SizeUtils.dp2px(10f))
        bigScaleHeight = array.getDimensionPixelSize(R.styleable.ScaleSeekBar_bigScaleHeight, SizeUtils.dp2px(10f))
        smallScaleHeight = array.getDimensionPixelSize(R.styleable.ScaleSeekBar_smallScaleHeight, SizeUtils.dp2px(6f))
        unitAreaHeight = array.getDimensionPixelSize(R.styleable.ScaleSeekBar_unitAreaHeight, SizeUtils.dp2px(18f))
        centerDistance = array.getDimensionPixelSize(R.styleable.ScaleSeekBar_centerDistance, SizeUtils.dp2px(12f))
        thumbWidth = array.getDimensionPixelSize(R.styleable.ScaleSeekBar_thumbWidth, SizeUtils.dp2px(20f))
        thumbHeight = array.getDimensionPixelSize(R.styleable.ScaleSeekBar_thumbHeight, SizeUtils.dp2px(20f))
        eachScaleValue = array.getFloat(R.styleable.ScaleSeekBar_eachScaleValue,1f)
        maxScaleValue = array.getFloat(R.styleable.ScaleSeekBar_maxScaleValue,50f)
        minScaleValue = array.getFloat(R.styleable.ScaleSeekBar_minScaleValue,0f)
        limitMinValue = array.getFloat(R.styleable.ScaleSeekBar_limitMinValue,0f)
        limitMaxValue = array.getFloat(R.styleable.ScaleSeekBar_limitMaxValue,maxScaleValue)
        firstScaleValue = array.getFloat(R.styleable.ScaleSeekBar_firstScaleValue,0f)
        unitWeight = array.getInt(R.styleable.ScaleSeekBar_unitWeight,10)
        unitName = if (array.getString(R.styleable.ScaleSeekBar_unitName) == null) "" else array.getString(R.styleable.ScaleSeekBar_unitName)!!
        array.recycle()


        limitMinPercent = limitMinValue / maxScaleValue
        limitMaxPercent = limitMaxValue / maxScaleValue
        currPercent = firstScaleValue / maxScaleValue
        setThumbDrawableId(thumbDrawableId, thumbWidth, thumbHeight)
    }

    private fun initPaint() {
        mProgressPaint.isAntiAlias = true
        mProgressPaint.color = mProgressColor
        mProgressPaint.style = Paint.Style.FILL
        mProgressPaint.strokeWidth = lineWidth
        mProgressPaint.strokeCap = Paint.Cap.ROUND

        mLinePaint.isAntiAlias = true
        mLinePaint.color = mLineColor
        mLinePaint.style = Paint.Style.FILL
        mLinePaint.strokeWidth = lineWidth
        mLinePaint.strokeCap = Paint.Cap.ROUND


        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = 32f
        mTextPaint.color = mTextColor
        mTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightModule = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        when (heightModule) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {

                viewHeight =
                    (2 * DEFALT_PADDING + unitAreaHeight + thumbHeight + centerDistance + paddingTop + paddingBottom).toInt()
            }
            MeasureSpec.EXACTLY -> {
                viewHeight = heightSize + paddingTop + paddingBottom
            }
        }

        viewWidth = (widthSize + paddingLeft + paddingRight + 4 * DEFALT_PADDING).toInt()

        setMeasuredDimension(viewWidth, viewHeight)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawXLine(canvas)
    }


    /**
     * 画X轴
     */
    private fun drawXLine(canvas: Canvas) {
        lineLeft = (2 * DEFALT_PADDING).toFloat()
        lineRight = (width - 2 * DEFALT_PADDING).toFloat()
        progressWidth = lineRight - lineLeft
        lineTop = DEFALT_PADDING + unitAreaHeight + centerDistance + thumbHeight / 2f - lineWidth / 2f
        lineBottom = lineTop + lineWidth
        lineCorners = (lineBottom - lineTop) * 0.45f
        canvas.drawRoundRect(lineLeft, lineTop, lineRight, lineBottom, lineCorners, lineCorners, mLinePaint)

        scaleDistance = (width - 4f * DEFALT_PADDING) / (maxScaleValue / eachScaleValue)
        var totalScaleNum = (maxScaleValue / eachScaleValue).toInt()
        var currScaleNum = (maxScaleValue * currPercent / eachScaleValue).toInt()
        for (item in 0..totalScaleNum) {
            if ((minScaleValue + item * eachScaleValue) % (unitWeight * eachScaleValue) == 0f) {

                bigScaleLeft = lineLeft + item * scaleDistance
                bigScaleRight = bigScaleLeft + lineWidth
                bigScaleTop = DEFALT_PADDING + unitAreaHeight + centerDistance + thumbHeight / 2f - bigScaleHeight / 2f
                bigScaleBottom = bigScaleTop + bigScaleHeight
                bigScaleCorners = (bigScaleBottom - bigScaleTop) * 0.45f

                val fm = mTextPaint.fontMetricsInt
                val baseLineY = DEFALT_PADDING + unitAreaHeight - (fm.bottom + fm.top) / 2f
                val baseLineX = bigScaleLeft

                canvas.drawText(
                    "${(minScaleValue + item * eachScaleValue).toInt()}$unitName",
                    baseLineX,
                    baseLineY,
                    mTextPaint
                )

                canvas.drawRoundRect(
                    bigScaleLeft, bigScaleTop, bigScaleRight, bigScaleBottom,
                    bigScaleCorners, bigScaleCorners, mLinePaint
                )
            } else {
                bigScaleLeft = lineLeft + item * scaleDistance
                bigScaleRight = bigScaleLeft + lineWidth
                bigScaleTop =
                    DEFALT_PADDING + unitAreaHeight + centerDistance + thumbHeight / 2f - smallScaleHeight / 2f
                bigScaleBottom = bigScaleTop + smallScaleHeight
                bigScaleCorners = (bigScaleBottom - bigScaleTop) * 0.45f

                canvas.drawRoundRect(
                    bigScaleLeft, bigScaleTop, bigScaleRight, bigScaleBottom,
                    bigScaleCorners, bigScaleCorners, mLinePaint
                )
            }
        }

        canvas.drawRoundRect(
            lineLeft,
            lineTop,
            lineLeft + progressWidth * currPercent,
            lineBottom,
            lineCorners,
            lineCorners,
            mProgressPaint
        )

        for (item in 0..currScaleNum) {
            if ((minScaleValue + item * eachScaleValue) % (unitWeight * eachScaleValue) == 0f) {

                bigScaleLeft = lineLeft + item * scaleDistance
                bigScaleRight = bigScaleLeft + lineWidth
                bigScaleTop = DEFALT_PADDING + unitAreaHeight + centerDistance + thumbHeight / 2f - bigScaleHeight / 2f
                bigScaleBottom = bigScaleTop + bigScaleHeight
                bigScaleCorners = (bigScaleBottom - bigScaleTop) * 0.45f

                canvas.drawRoundRect(
                    bigScaleLeft, bigScaleTop, bigScaleRight, bigScaleBottom,
                    bigScaleCorners, bigScaleCorners, mProgressPaint
                )
            } else {
                bigScaleLeft = lineLeft + item * scaleDistance
                bigScaleRight = bigScaleLeft + lineWidth
                bigScaleTop =
                    DEFALT_PADDING + unitAreaHeight + centerDistance + thumbHeight / 2f - smallScaleHeight / 2f
                bigScaleBottom = bigScaleTop + smallScaleHeight
                bigScaleCorners = (bigScaleBottom - bigScaleTop) * 0.45f

                canvas.drawRoundRect(
                    bigScaleLeft, bigScaleTop, bigScaleRight, bigScaleBottom,
                    bigScaleCorners, bigScaleCorners, mProgressPaint
                )
            }
        }


        canvas.save()
        canvas.translate(progressWidth * currPercent + 2 * DEFALT_PADDING - thumbWidth / 2f, 0f)
        canvas.drawBitmap(thumbBitmap!!, 0f, DEFALT_PADDING + unitAreaHeight + centerDistance.toFloat(), null)
        canvas.restore()

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchDownX = event.x
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                touchDownX = event.x
                currPercent = (touchDownX - DEFALT_PADDING * 2) / progressWidth
                if (touchDownX < (DEFALT_PADDING * 2 + limitMinPercent * progressWidth)) {
                    currPercent = limitMinPercent
                }
                if (touchDownX > limitMaxPercent * progressWidth + DEFALT_PADDING * 2) {
                    currPercent = limitMaxPercent
                }

                mListener?.onSeek(Math.round(maxScaleValue * currPercent * 10) / 10f)

                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                mListener?.OnSeekEnd(Math.round(maxScaleValue * currPercent * 10) / 10f)
            }
        }
        return super.onTouchEvent(event)
    }

    interface OnSeekChangeListener {
        fun onSeek(progress: Float)
        fun OnSeekEnd(progress: Float)
    }


    @SuppressLint("ObsoleteSdkInt")
    fun setThumbDrawableId(@DrawableRes thumbDrawableId: Int, width: Int, height: Int) {
        if (thumbDrawableId != 0 && resources != null && width > 0 && height > 0) {
            this.thumbDrawableId = thumbDrawableId
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                thumbBitmap = drawableToBitmap(width, height, resources!!.getDrawable(thumbDrawableId, null))
            } else {
                thumbBitmap = drawableToBitmap(width, height, resources!!.getDrawable(thumbDrawableId))
            }
        }
    }

    fun setOnSeekChangeListener(listener: OnSeekChangeListener) {
        this.mListener = listener
    }


    fun drawableToBitmap(width: Int, height: Int, drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            if (drawable is BitmapDrawable) {
                bitmap = drawable.bitmap
                if (bitmap != null && bitmap.height > 0) {
                    val matrix = Matrix()
                    val scaleWidth = width * 1.0f / bitmap.width
                    val scaleHeight = height * 1.0f / bitmap.height
                    matrix.postScale(scaleWidth, scaleHeight)
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                    return bitmap
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap!!)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }


    fun setFirstScaleValue(value: Float){
        firstScaleValue = value
        currPercent = firstScaleValue / maxScaleValue
        invalidate()
    }

}