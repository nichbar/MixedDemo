package work.nich.mixeddemo.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ImageHelper {

    public static void loadImage(Context context, int id, ImageView imageView) {
        GlideApp.with(context)
                .load(id)
                .into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, int placeholder) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, Drawable placeholder) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }


    public static void loadImageWithCircleCrop(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .circleCrop()
                .into(imageView);
    }

    public static void loadRoundCornerImage(Context context, String url, ImageView imageView, int Radius) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(Radius));

        GlideApp.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }
}