package work.nich.mixeddemo.view

import android.animation.*
import android.content.Context
import android.graphics.Path
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_notifier.view.*
import work.nich.mixeddemo.R

class NotifierView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : FrameLayout(context, attrs, defStyle) {

    companion object {
        const val SCALE_MINI = 0.2F
        const val SCALE_DEFAULT = 1F

        const val DEFAULT_DURATION = 500L
    }

    internal var onShowListener: OnShowNotificationListener? = null
    internal var onHideListener: OnHideNotificationListener? = null

    lateinit var expandAnimator: ValueAnimator
    lateinit var shrinkAnimator: ValueAnimator

    lateinit var translateUpAnimator: ObjectAnimator
    lateinit var translateDownAnimator: ObjectAnimator

    lateinit var translateToLeftAnimator: ObjectAnimator
    lateinit var translateToRightAnimator: ObjectAnimator

    lateinit var zoomInAnimator: ObjectAnimator
    lateinit var zoomOutAnimator: ObjectAnimator

    var showAnimatorSet: AnimatorSet
    var hideAnimatorSet: AnimatorSet

    var rightToLeftPath: Path
    var leftToRightPath: Path

    var zoomInPath: Path
    var zoomOutPath: Path

    var veryRight: Float = 0F
    var veryBottom: Float = 0F
    var centerX: Float = 0F

    var textWidth: Int = 0
    var cardViewWidth: Int = 0

    internal var duration = DEFAULT_DURATION

    init {
        inflate(context, R.layout.view_notifier, this)

        ViewCompat.setTranslationZ(this, Integer.MAX_VALUE.toFloat())

        cardView.scaleX = SCALE_MINI
        cardView.scaleY = SCALE_MINI

        rightToLeftPath = Path()
        leftToRightPath = Path()
        zoomInPath = Path()
        zoomOutPath = Path()

        showAnimatorSet = AnimatorSet()
        hideAnimatorSet = AnimatorSet()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        centerX = (left + right - cardViewWidth) / 2F

        // TODO Provide method to change these absolute offset.
        val r = right - dp2px(72F).toFloat()
        val b = bottom - dp2px(145F).toFloat()

        rightToLeftPath.moveTo(r, b)
        rightToLeftPath.lineTo(centerX, b)

        leftToRightPath.moveTo(centerX, b)
        leftToRightPath.lineTo(r, b)

        zoomInPath.moveTo(r, b + dp2px(80F))
        zoomInPath.lineTo(r, b)

        zoomOutPath.moveTo(r, b)
        zoomOutPath.lineTo(r, b + dp2px(80F))

        // Only set
        if (veryRight != r || veryBottom != b) {
            veryRight = r
            veryBottom = b
            initAnimator()
        }
    }

    private fun initAnimator() {
        expandAnimator = ValueAnimator.ofFloat(0F, 1F)
        expandAnimator.duration = DEFAULT_DURATION
        expandAnimator.addUpdateListener { a ->
            val progress = a?.animatedValue as Float
            tvText.width = (textWidth * progress).toInt()
        }

        shrinkAnimator = ValueAnimator.ofFloat(1F, 0F)
        shrinkAnimator.duration = DEFAULT_DURATION
        shrinkAnimator.addUpdateListener { a ->
            val progress = a?.animatedValue as Float
            tvText.width = (textWidth * progress).toInt()
        }
        shrinkAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationEnd(animation: Animator?) {
                val lp = FrameLayout.LayoutParams(cardView.layoutParams)
                lp.gravity = Gravity.NO_GRAVITY
                cardView.layoutParams = lp
            }

            override fun onAnimationCancel(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationStart(animation: Animator?) {
                // Do nothing.X
            }
        })

        translateToLeftAnimator = ObjectAnimator.ofFloat(cardView, "translationX", veryRight, centerX)
        translateToLeftAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationEnd(animation: Animator?) {
                onShowListener?.onShow()

                val lp = FrameLayout.LayoutParams(cardView.layoutParams)
                lp.gravity = Gravity.CENTER_HORIZONTAL
                cardView.layoutParams = lp
                cardView.translationX = 0f

                expandAnimator.start()
                shrinkAfterDelay()
            }

            override fun onAnimationCancel(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationStart(animation: Animator?) {
                // Do nothing.
            }
        })
        translateToLeftAnimator.duration = DEFAULT_DURATION

        translateToRightAnimator = ObjectAnimator.ofFloat(cardView, "translationX", centerX, veryRight)
        translateToRightAnimator.duration = DEFAULT_DURATION

        translateUpAnimator = ObjectAnimator.ofFloat(cardView, "translationY", veryBottom + dp2px(100F), veryBottom)
        translateUpAnimator.duration = DEFAULT_DURATION
        translateUpAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationEnd(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationCancel(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationStart(animation: Animator?) {
                cardView.translationX = veryRight
            }
        })

        translateDownAnimator = ObjectAnimator.ofFloat(cardView, "translationY", veryBottom, veryBottom + dp2px(100F))
        translateDownAnimator.duration = DEFAULT_DURATION

        zoomInAnimator = ObjectAnimator.ofPropertyValuesHolder(cardView, PropertyValuesHolder.ofFloat("scaleX", SCALE_DEFAULT),
                PropertyValuesHolder.ofFloat("scaleY", SCALE_DEFAULT))
        zoomInAnimator.duration = DEFAULT_DURATION

        zoomOutAnimator = ObjectAnimator.ofPropertyValuesHolder(cardView, PropertyValuesHolder.ofFloat("scaleX", SCALE_MINI),
                PropertyValuesHolder.ofFloat("scaleY", SCALE_MINI))
        zoomOutAnimator.duration = DEFAULT_DURATION
        zoomOutAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationEnd(animation: Animator?) {
                removeFromParent()
            }

            override fun onAnimationCancel(animation: Animator?) {
                // Do nothing.
            }

            override fun onAnimationStart(animation: Animator?) {
                // Do nothing.
            }

        })

        showAnimatorSet.play(translateUpAnimator).with(zoomInAnimator).before(translateToLeftAnimator)
        showAnimatorSet.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        showAnimatorSet.removeAllListeners()
        hideAnimatorSet.removeAllListeners()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()
        return super.onTouchEvent(event)
    }

    private fun shrinkAfterDelay() {
        postDelayed({ shrink() }, duration)
    }

    fun shrink() {
        shrinkAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                // Ignored.
            }

            override fun onAnimationEnd(animation: Animator?) {
                hide()
            }

            override fun onAnimationCancel(animation: Animator?) {
                // Ignored.
            }

            override fun onAnimationStart(animation: Animator?) {
                // Ignored.
            }
        })
        shrinkAnimator.start()
    }

    internal fun removeFromParent() {
        clearAnimation()
        visibility = View.GONE

        postDelayed(object : Runnable {
            override fun run() {
                try {
                    if (parent == null) {
                        Log.e(javaClass.simpleName, "getParent() returning Null")
                    } else {
                        try {
                            (parent as ViewGroup).removeView(this@NotifierView)

                            onHideListener?.onHide()
                        } catch (ex: Exception) {
                            Log.e(javaClass.simpleName, "Cannot remove from parent layout")
                        }
                    }
                } catch (ex: Exception) {
                    Log.e(javaClass.simpleName, Log.getStackTraceString(ex))
                }
            }
        }, 100)
    }

    fun setText(text: String) {
        if (!TextUtils.isEmpty(text)) {
            tvText.text = text
            tvText.measure(0, 0)
            textWidth = tvText.measuredWidth
            tvText.width = 0

            cardView.measure(0, 0)
            cardViewWidth = cardView.measuredWidth
        }
    }

    fun hide() {
        hideAnimatorSet.play(translateDownAnimator).with(zoomOutAnimator).after(translateToRightAnimator)
        hideAnimatorSet.start()
    }

    fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.applicationContext.resources.displayMetrics).toInt()
    }

    interface OnShowNotificationListener {
        fun onShow()
    }

    interface OnHideNotificationListener {
        fun onHide()
    }

}