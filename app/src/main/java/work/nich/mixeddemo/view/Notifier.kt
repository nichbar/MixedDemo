package work.nich.mixeddemo.view

import android.app.Activity
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import work.nich.mixeddemo.R
import java.lang.ref.WeakReference

class Notifier private constructor() {

    companion object {

        private var activityWeakReference: WeakReference<Activity>? = null

        @JvmStatic
        fun create(activity: Activity?): Notifier {
            if (activity == null) {
                throw IllegalArgumentException("Activity cannot be null!")
            }

            val notifier = Notifier()

            // Hide current NotifierView, if one is active
            clearCurrent(activity)

            notifier.setActivity(activity)
            notifier.notifierView = NotifierView(activity)

            return notifier
        }

        /**
         * Cleans up the currently showing notifierView view, if one is present
         *
         * @param activity The current Activity
         */
        @JvmStatic
        fun clearCurrent(activity: Activity?) {
            (activity?.window?.decorView as? ViewGroup)?.let {
                //Find all NotifierView Views in Parent layout
                for (i in 0..it.childCount) {
                    val childView = if (it.getChildAt(i) is NotifierView) it.getChildAt(i) as NotifierView else null
                    if (childView != null && childView.windowToken != null) {
                        ViewCompat.animate(childView).alpha(0f).withEndAction(getRemoveViewRunnable(childView))
                    }
                }
            }
        }

        @JvmStatic
        fun hide() {
            activityWeakReference?.get()?.let {
                clearCurrent(it)
            }
        }

        private fun getRemoveViewRunnable(childView: NotifierView?): Runnable {
            return Runnable {
                childView?.let {
                    (childView.parent as? ViewGroup)?.removeView(childView)
                }
            }
        }
    }

    private var notifierView: NotifierView? = null

    private val activityDecorView: ViewGroup?
        get() {
            var decorView: ViewGroup? = null

            activityWeakReference?.get()?.let {
                decorView = it.window.decorView as ViewGroup
            }

            return decorView
        }

    fun show(): NotifierView? {
        activityWeakReference?.get()?.let {
            it.runOnUiThread {
                activityDecorView?.addView(notifierView)
            }
        }

        return notifierView
    }

    fun setText(text: String): Notifier {
        notifierView?.setText(text)

        return this
    }

    fun setDuration(time: Long): Notifier {
        notifierView?.duration = time

        return this
    }

    fun setOnClickListener(onClickListener: View.OnClickListener): Notifier {
        notifierView?.findViewById<View>(R.id.cardView)?.setOnClickListener(onClickListener)

        return this
    }

    private fun setActivity(activity: Activity) {
        activityWeakReference = WeakReference(activity)
    }
}