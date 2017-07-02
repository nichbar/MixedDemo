package work.nich.mixeddemo.tools;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import work.nich.mixeddemo.R;


/**
 * Created by nich on 2016/11/11. UI操作的一些工具类
 */

public class UItools {

	//标签颜色
	private static int[] HASH_COLORS = new int[] { R.color.tag_col_1,
			R.color.tag_col_2, R.color.tag_col_3, R.color.tag_col_4,
			R.color.tag_col_5, R.color.tag_col_6, R.color.tag_col_7,
			R.color.tag_col_8
	};

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 从文字的哈希值里中选择颜色
	 * 
	 * @param word
	 *            文字
	 * @return 颜色
	 */
	public static int colorFromHash(Context context, String word) {
		if (context == null)
			return Color.BLUE;

		int color = HASH_COLORS[0];
		if (!TextUtils.isEmpty(word)) {
			color = HASH_COLORS[Math.abs(word.hashCode()) % HASH_COLORS.length];
		}
		return context.getResources().getColor(color);
	}
}
