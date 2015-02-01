package com.m.rabbit.utils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.util.Linkify;
import android.util.Patterns;

@SuppressLint("NewApi")
public class StringUtils {
    public static final int UNIT_MB = 1048576;
    public static final int UNIT_KB = 1024;

    public static String getSize(long size) {
        StringBuffer sb = new StringBuffer();
        if (size >= UNIT_MB) {
            long mb = size >> 20;
            sb.append(mb);
            long leftSize = size & 0xFFFFF;
            leftSize = leftSize * 100 / UNIT_MB;
            if (leftSize != 0) {
                sb.append('.');
                if (leftSize < 10) {
                    sb.append('0');
                }
                sb.append(leftSize);
            }
            sb.append("MB");
        } else if (size >= UNIT_KB) {
            long kb = size >> 10;
            sb.append(kb);
            long leftSize = size & 0x3FF;
            leftSize = leftSize * 100 / UNIT_KB;
            if (leftSize != 0) {
                sb.append('.');
                if (leftSize < 10) {
                    sb.append('0');
                }
                sb.append(leftSize).append("KB");
            }
        } else {
            sb.append(size).append('B');
        }
        return sb.toString();
    }

    public static boolean checkEmailUserName(String email) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            // This regex is really slow.
            // TODO: remove this branch after updating minSDKVersion to 8.
            String check = "^([a-z0-9A-Z-_]+[-|_|\\.]?)+[a-z0-9A-Z_-]@([a-z0-9A-Z_+]+(-[a-z0-9A-Z_+]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            return matcher.matches();
        }
    }

    public static boolean checkCellPhone(String phoneNum) {
        String check = "^((13[0-9])|(15[^4,//D])|(18[025-9]))\\d{8}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(phoneNum);
        return matcher.matches();
    }

    public static int getFirstVaildPosition(String[] strings) {
        int size = strings.length;
        for (int i = 0; i < size; i++) {
            if (isNotEmpty(strings[i])) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str.trim());
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static String converVideoSize(long videoSize) {
        int kSize = 1024;
        int mSize = kSize * kSize;
        int gSize = mSize * kSize;
        try {
            if (videoSize > gSize) {
                return BigDecimal.valueOf(((float) videoSize) / ((float) gSize)).setScale(1, BigDecimal.ROUND_HALF_UP)
                        + "G";
            } else if (videoSize > mSize) {
                return BigDecimal.valueOf(((float) videoSize) / ((float) mSize)).setScale(1, BigDecimal.ROUND_HALF_UP)
                        + "M";
            } else if (videoSize > kSize) {
                return BigDecimal.valueOf(((float) videoSize) / ((float) kSize)).setScale(1, BigDecimal.ROUND_HALF_UP)
                        + "K";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return videoSize + "byte";
    }

    public static boolean checkPhone(String phone) {
        if (isNumber(phone)) {
            return true;
        }
        return false;
    }

    public static boolean isNumber(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isLetter(char ch) {
    	if(ch>='a' && ch<='z'){
			return true;
		}else if(ch>='A' && ch<='Z'){
			return true;
		}
    	return false;
    }
    
    /**
     * 判断是不是字母、数字、下划线
     */
    public static boolean isCharacter(String str){
    	for (int i = 0; i < str.length(); i++) {
    		if (!isLetter(str.charAt(i)) && !Character.isDigit(str.charAt(i)) && str.charAt(i)!='_') {
    			return false;
    		}
    	}
    	return true;
    }

    public static String transferSpecialChar(String sourceData) {
        if (sourceData != null) {
            if (sourceData.contains("&amp;")) { // &amp; ---> &
                sourceData = sourceData.replaceAll("&amp;", "&");
            }
            if (sourceData.contains("&lt;")) {
                sourceData = sourceData.replaceAll("&lt;", "&#60;"); // &lt -- <
            }
            if (sourceData.contains("&gt;")) {
                sourceData = sourceData.replaceAll("&gt;", "&#62;"); // &gt -- >
            }
            Pattern pattern = Pattern.compile("&#\\d{2,4};");
            Matcher matcher = pattern.matcher(sourceData);
            while (matcher.find()) {
                String temp = matcher.group();
                int number = Integer.parseInt(temp.replace("&#", "").replace(";", ""), 10);
                char c = (char) number;
                sourceData = sourceData.replace(temp, c + "");
            }
        }
        // return sourceData;
        return replaceSameChars(sourceData, '\n');
    }

    public static String replaceSameChars(String sourceData, char destData) {
        if (sourceData != null) {
            StringBuffer sb = new StringBuffer();
            boolean bFound = false;
            for (int i = 0; i < sourceData.length(); i++) {
                if (sourceData.charAt(i) != destData) {
                    bFound = false;
                    sb.append(sourceData.charAt(i));
                } else {
                    if (!bFound) {
                        bFound = true;
                        sb.append(sourceData.charAt(i));
                    }
                }
            }// end for
            if (sb.length() > 0) {
                return sb.toString();
            }
        }
        return sourceData;

    }

    /**
     * 字符串是否合法
     * 
     * @param valueChars
     * @return
     */
    public static boolean isValidChars(String valueChars, String field) {
        boolean valid = false;
        if (StringUtils.isNotEmpty(valueChars) && StringUtils.isNotEmpty(field)) {
            if ("uploadFile".equals(field)) {
                if (valueChars == null || valueChars.length() > 255)
                    valid = false;
                else {
                    // valid =
                    // !Pattern.compile("[\\\\\\/\\:\\*\\?\\\"\\|\\<\\>]",
                    // Pattern.CASE_INSENSITIVE).matcher(valueChars).find();
                    ArrayList<Character> characters = new ArrayList<Character>();
                    characters.add('%');
                    a: for (int i = 0; i < characters.size(); i++) {
                        Character character = characters.get(i);
                        String charValue = String.valueOf(character);
                        if (valueChars.contains(charValue)) {
                            valid = false;
                            break a;
                        }
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    private static final double TEN_THOUSAND = 10000d;

    public static String getCommentNum(long count) {
        if (count > 0 && count < TEN_THOUSAND) {
            return String.valueOf(count);
        } else if (count >= TEN_THOUSAND && count < 10 * TEN_THOUSAND) {

            return String.valueOf(Math.round(count * 10d / TEN_THOUSAND) / 10d + "万");

        } else if (count >= 10 * TEN_THOUSAND && count < 100 * TEN_THOUSAND) {

            return String.valueOf(Math.round(count / TEN_THOUSAND) + "万");

        } else if (count >= 100 * TEN_THOUSAND && count < 1000 * TEN_THOUSAND) {

            return String.valueOf(Math.round(count / (100d * TEN_THOUSAND)) + "百万");

        } else if (count >= 1000 * TEN_THOUSAND) {

            return String.valueOf(Math.round(count / (1000d * TEN_THOUSAND)) + "千万");

        } else {
            return "";
        }
    }

    public static SpannableStringBuilder getStyledScore(String score) {
        int length = score.length();
        int index = score.indexOf(".");
        SpannableStringBuilder spannable = new SpannableStringBuilder(score);

        CharacterStyle spanSize1 = new RelativeSizeSpan((float) 1.2);
        spannable.setSpan(spanSize1, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        CharacterStyle spanSize2 = new RelativeSizeSpan((float) 0.8);
        spannable.setSpan(spanSize2, index + 1, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        CharacterStyle spanRed = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(spanRed, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
    
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.replace("-", ""); 
    } 
    
	/**
	 * 检测字符串中是否包含汉字
	 * 
	 * @param sequence
	 * @return
	 */
	public static boolean checkChinese(String sequence) {
		final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
		boolean result = false;
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(sequence);
		result = matcher.find();
		return result;
	}

	/**
	 * 检测字符串中只能包含：中文、数字、下划线(_)、横线(-)
	 * 
	 * @param sequence
	 * @return
	 */
	public static boolean checkNickname(String sequence) {
		final String format = "[^\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w-_]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(sequence);
		return !matcher.find();
	}
	
	/**
     * Uses androids android.util.Patterns.EMAIL_ADDRESS to check if an email address is valid.
     *
     * @param email Address to check
     * @return true if the <code>email</code> is a valid email address.
     */
    public final static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    
    public final static SpannableString getLinkText(String message){
    	final SpannableString s = new SpannableString(message); //Make links clickable
	    Linkify.addLinks(s, Linkify.ALL);
	    return s;
    }
    
    public final static String getEncodeString(String str){
		try {
			return URLEncoder.encode(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
