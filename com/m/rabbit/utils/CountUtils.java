package com.m.rabbit.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;

/**
 * <h2>Utility methods for formatting counts</h2>
 *
 * <h3>Common uses:</h3>
 * <code>CountUtil.{@link #getFormattedCount getFormattedCount}(1200000);</code> // returns &quot;1.2m&quot;<br />
 */

public class CountUtils {

    /**
     * @see #getFormattedCount(Long)
     */
	public static String getFormattedCount(int count) {
		return getFormattedCount(Long.valueOf(count));
	}

    /**
     * @see #getFormattedCount(Long)
     */
	public static String getFormattedCount(String count) {
		return getFormattedCount(Long.parseLong(count));
	}
	
	/**
	 * Used to format a given number into a short representation.
     *
     * Examples:
     *  Given 9100, will return "9.1k".
     *  Given 8100000, will return "8.1m"
     *  Given 10, will return 10"
     *
	 * @param count Value to convert.
	 * @return Formatted value (see examples)
	 */
	public static String getFormattedCount(Long count) {
		final String unit;
        final Double dbl;
        final DecimalFormat format = new DecimalFormat("#.#");
		if (count<1000) {
			return format.format(count);
		} else if(count<1000000) {
			unit = "k";
			dbl = count/1000.0;
        } else if(count<1000000000) {
			unit = "m";
			dbl = count/1000000.0;
        } else {
            unit = "b";
            dbl = count/1000000000.0;
        }
		return format.format(dbl)+unit;
	}
	
	private static StringBuilder mFormatBuilder;
    private static Formatter mFormatter;
	public static String stringForTime(int timeMs) {
		if (mFormatBuilder == null) {
			mFormatBuilder = new StringBuilder();
			mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
		}
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%02d:%02d",minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d",minutes, seconds).toString();
        }
    }
}
