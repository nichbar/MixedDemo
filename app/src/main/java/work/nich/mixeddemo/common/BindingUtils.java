package work.nich.mixeddemo.common;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import work.nich.mixeddemo.MixedApplication;

/**
 * Here to place all the global bindingAdapter.
 * If a bindingAdapter does only execute in one specific view, then place bindingAdapter in that view.
 */
public class BindingUtils {

    @BindingAdapter({"image"})
    public static void setImage(ImageView view, String imageUrl) {
        ImageHelper.loadImage(view.getContext(), imageUrl, view);
    }

    @BindingAdapter({"image", "placeHolder"})
    public static void setImage(ImageView view, String imageUrl, Drawable placeHolder) {
        ImageHelper.loadImage(view.getContext(), imageUrl, view, placeHolder);
    }

    @BindingAdapter({"roundCornerImage"})
    public static void setRoundCornerImage(ImageView view, String imageUrl) {
        ImageHelper.loadRoundCornerImage(view.getContext(), imageUrl, view, DisplayUtils.dp2px(MixedApplication.getApp(), 12));
    }

    @BindingAdapter({"formatTimestamp"})
    public static void setTime(TextView textView, long time) {
        Date date = new Date(time * 1000);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));

        textView.setText(sdf.format(date));
    }

    @BindingAdapter({"formatTimestampToMinute"})
    public static void setTimePreciseToMinute(TextView textView, long time) {
        Date date = new Date(time);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));

        textView.setText(sdf.format(date));
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter({"floatToText"})
    public static void setText(TextView textView, float value) {
        textView.setText(Float.toString(value));
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter({"longToText"})
    public static void setText(TextView textView, long value) {
        textView.setText(Float.toString(value));
    }

    @BindingAdapter({"visible"})
    public static void setVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"visibleIfExist"})
    public static void setVisibility(View view, Object o) {
        if (o != null) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"visibleIfNotExist"})
    public static void adjustVisibility(View view, Object o) {
        if (o == null) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"htmlText"})
    public static void setHtmlText(TextView textView, String text) {
        if (text == null) return;
        textView.setText(Html.fromHtml(text.replace("\n", "<br>")));
    }

}
