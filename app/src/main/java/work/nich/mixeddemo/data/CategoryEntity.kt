package work.nich.mixeddemo.data

import com.google.gson.annotations.SerializedName

data class CategoryEntity(
        @SerializedName("_id") var id: String? = "",
        var icon: String? = "",
        var name: String? = "",
        var data: List<CategoryEntity>? = null)