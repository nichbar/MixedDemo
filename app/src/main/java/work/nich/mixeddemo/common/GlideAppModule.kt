package work.nich.mixeddemo.common

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.MemoryCacheAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideAppModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        val options: RequestOptions = RequestOptions().format(DecodeFormat.PREFER_RGB_565)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()

        builder.setDefaultRequestOptions(options)
                .setDefaultTransitionOptions(Drawable::class.java, DrawableTransitionOptions.withCrossFade())
                .setDiskCache(ExternalPreferredCacheDiskCacheFactory(context))
                .setMemoryCache(MemoryCacheAdapter())
    }
}