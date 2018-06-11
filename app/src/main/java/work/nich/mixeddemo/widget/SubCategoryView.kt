package work.nich.mixeddemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import work.nich.mixeddemo.R
import work.nich.mixeddemo.common.ToastUtils
import work.nich.mixeddemo.data.CategoryEntity

class SubCategoryView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    var leftTv: TextView
    var centerTv: TextView
    var rightTv: TextView

    init {
        View.inflate(context, R.layout.layout_sub_category, this)

        leftTv = findViewById(R.id.tv_left_sub_category)
        centerTv = findViewById(R.id.tv_center_sub_category)
        rightTv = findViewById(R.id.tv_right_sub_category)
    }

    fun setLeftCategory(category: CategoryEntity) {
        setCategory(leftTv, category)
    }

    fun setCenterCategory(category: CategoryEntity) {
        setCategory(centerTv, category)
    }

    fun setRightCategory(category: CategoryEntity) {
        setCategory(rightTv, category)
    }

    private fun setCategory(tv: TextView, category: CategoryEntity) {
        tv.text = category.name
        tv.setOnClickListener { ToastUtils.showShortToast(category.name) }
    }

}