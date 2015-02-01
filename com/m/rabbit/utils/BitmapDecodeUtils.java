package com.m.rabbit.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class BitmapDecodeUtils {
	
	/**
	 * According to the high and width  to decodeFile
	 * @param pathName
	 * @param reqWidth
	 * @param reqHeight
	 * @return bm
	 */
	public static Bitmap decodeFile(String pathName,int reqWidth,int reqHeight){
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(pathName,bmpFactoryOptions);
		bmpFactoryOptions.inSampleSize = calculateInSampleSize(bmpFactoryOptions, reqWidth, reqHeight);
		bmpFactoryOptions.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName,bmpFactoryOptions);
	}
	
	/**
	 * According to the one size  to decodeFile
	 */
	public static Bitmap decodeFile(String pathName,int maxSize){
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(pathName,bmpFactoryOptions);
		int reqWidth, reqHeight;
		if (bmpFactoryOptions.outHeight ==0 || bmpFactoryOptions.outWidth ==0) {
			return null;
		}
		if (bmpFactoryOptions.outWidth >= bmpFactoryOptions.outHeight) {
			reqWidth = maxSize;
			reqHeight = reqWidth *bmpFactoryOptions.outHeight/bmpFactoryOptions.outWidth;
		}else{
			reqHeight = maxSize;
			reqWidth = reqHeight * bmpFactoryOptions.outWidth/bmpFactoryOptions.outHeight;
		}
		bmpFactoryOptions.inSampleSize = calculateInSampleSize(bmpFactoryOptions, reqWidth, reqHeight);
		bmpFactoryOptions.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName,bmpFactoryOptions);
	}
	
	
    
	/**
	 * DecodeResource bitmap that config is Bitmap.Config.RGB_565
	 */
	public static Bitmap decodeResource(Context context,int resId) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(context.getResources().openRawResource(resId),null,opts);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth){
		return calculateInSampleSize(options,reqWidth,0);
	}
	
    public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (reqHeight == 0 ) {
			reqHeight = 4096;
		}
        
        if (reqWidth == 0) {
			reqWidth = 4096;
		}
        
        
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
            
        	while ((width / inSampleSize) > 4096
                    || (height / inSampleSize) > 4096) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).
            
            long totalPixels = (width/inSampleSize) * (height /inSampleSize);

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight*2;

            if (totalReqPixelsCap >  0 ) {
            	while (totalPixels > totalReqPixelsCap) {
            		inSampleSize *= 2;
            		totalPixels /= 4;
            	}
            }
        }
        
        options.inSampleSize = inSampleSize;
        return inSampleSize;
    }
    
    public static void saveBitmapToFile(Bitmap photo, String fileName) {
		File file = new File(fileName);
		FileOutputStream out = null;
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();  
			out = new FileOutputStream(file);
			photo.compress(CompressFormat.PNG, 70, out);
			out.flush();
		} catch (Exception e) {
		} finally {
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
				}
			}
		}
	}
    
}
