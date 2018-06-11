package work.nich.mixeddemo.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import work.nich.mixeddemo.BaseActivity
import work.nich.mixeddemo.R
import work.nich.mixeddemo.data.CategoryEntity
import work.nich.mixeddemo.widget.ExperienceView
import work.nich.mixeddemo.widget.ProgressCircle

class CustomViewActivity : BaseActivity() {

    @BindView(R.id.recyclerview)
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_view_layout)
        ButterKnife.bind(this)

        val subList = arrayListOf<CategoryEntity>()
        subList.add(CategoryEntity(name = "三国争霸"))
        subList.add(CategoryEntity(name = "跑车"))
        subList.add(CategoryEntity(name = "悠悠球"))
        subList.add(CategoryEntity(name = "神风"))
        subList.add(CategoryEntity(name = "佐助"))
        subList.add(CategoryEntity(name = "兰博基尼"))
        subList.add(CategoryEntity(name = "x"))
        subList.add(CategoryEntity(name = "y"))
        subList.add(CategoryEntity(name = "z"))
        subList.add(CategoryEntity(name = "zoom"))

        val primeList = arrayListOf<CategoryEntity>()

        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "不是不能用", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "还行", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "沙拉黑", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "GON!", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "不错", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "三国", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "跑车", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "神风", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "几座", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "秦", data = subList))
        primeList.add(CategoryEntity(icon = "https://img.shouji.com.cn/simg/20180122/2018012217738039.png", name = "汉", data = subList))

        val adapter = CategoryAdapter()
        adapter.categoryList = primeList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun setProgress(view: View) {
        (view as ProgressCircle).setProgress(90)
    }

    fun setExperienceProgress(view: View) {
        (view as ExperienceView).setExperience(43999)
    }
}
