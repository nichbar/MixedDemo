package work.nich.mixeddemo.view

import android.app.Activity
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import work.nich.mixeddemo.R
import work.nich.mixeddemo.common.DisplayUtils
import work.nich.mixeddemo.data.CategoryEntity
import work.nich.mixeddemo.databinding.ItemCategoryBinding
import work.nich.mixeddemo.widget.SubCategoryView

class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var categoryList: List<CategoryEntity> = arrayListOf()
    var expandStatusMap: HashMap<Int, Boolean> = HashMap()

    private var sixteenDp: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (sixteenDp == 0) sixteenDp = DisplayUtils.dp2px(parent.context, 16f)
        return CategoryViewHolder(DataBindingUtil.inflate((parent.context as Activity).layoutInflater, R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.binding.category = categoryList[position]
                holder.bindCategory(
                        category = categoryList[position],
                        expandableStatusMap = expandStatusMap,
                        isExpended = expandStatusMap[position] != null && expandStatusMap[position] == true,
                        marginTop = sixteenDp)
            }
        }
    }

    internal class CategoryViewHolder(var binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindCategory(category: CategoryEntity, expandableStatusMap: HashMap<Int, Boolean>, isExpended: Boolean, marginTop: Int) {
            category.data?.let {
                var subCategoryView: SubCategoryView? = null

                binding.containerUnexpandable.removeAllViews()
                val unexpandableSize = if (it.size > 6) 6 else it.size
                val unexpandableCategoryList = it.subList(0, unexpandableSize)
                unexpandableCategoryList.forEachIndexed({ index, c ->
                    when (index % 3) {
                        0 -> {
                            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
                            if (subCategoryView != null) params.setMargins(0, marginTop, 0, 0)

                            subCategoryView = SubCategoryView(binding.root.context)

                            subCategoryView?.layoutParams = params

                            binding.containerUnexpandable.addView(subCategoryView)
                            subCategoryView?.setLeftCategory(c)
                        }
                        1 -> subCategoryView?.setCenterCategory(c)
                        2 -> subCategoryView?.setRightCategory(c)
                    }
                })

                if (it.size > 7) {
                    val extraCategoryList = it.subList(6, it.size)

                    val subCategoryViewLinearLayout = LinearLayout(binding.root.context)
                    subCategoryViewLinearLayout.orientation = LinearLayout.VERTICAL
                    subCategoryView = SubCategoryView(binding.root.context)

                    binding.containerExpandable.removeAllViews()
                    binding.containerExpandable.addView(subCategoryViewLinearLayout)
                    binding.containerExpandable.setExpanded(isExpended, false)
                    binding.containerExpandable.setOnExpansionUpdateListener { _, state ->
                        when (state) {
                            0 -> {
                                binding.ivToggle.setImageResource(R.drawable.ic_category_arrow_down)
                                expandableStatusMap[adapterPosition] = false
                            }
                            3 -> {
                                binding.ivToggle.setImageResource(R.drawable.ic_category_arrow_up)
                                expandableStatusMap[adapterPosition] = true
                            }
                        }
                    }

                    binding.ivToggle.visibility = View.VISIBLE
                    binding.ivToggle.setOnClickListener { binding.containerExpandable.toggle() }
                    when {
                        isExpended -> binding.ivToggle.setImageResource(R.drawable.ic_category_arrow_up)
                        else -> binding.ivToggle.setImageResource(R.drawable.ic_category_arrow_down)
                    }

                    extraCategoryList.forEachIndexed({ index, c ->
                        when (index % 3) {
                            0 -> {
                                subCategoryView = SubCategoryView(binding.root.context)

                                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
                                params.setMargins(0, marginTop, 0, 0)
                                subCategoryView?.layoutParams = params

                                subCategoryViewLinearLayout.addView(subCategoryView)
                                subCategoryView?.setLeftCategory(c)
                            }
                            1 -> subCategoryView?.setCenterCategory(c)
                            2 -> subCategoryView?.setRightCategory(c)
                        }
                    })
                }
            }
        }
    }
}