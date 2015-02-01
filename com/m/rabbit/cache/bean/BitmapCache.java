package com.m.rabbit.cache.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.m.rabbit.utils.BitmapDecodeUtils;
import com.m.rabbit.utils.LLog;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapCache implements Serializable {
    private static final long serialVersionUID = -8952656679955404227L;
    private byte[] mBytes = null;
    public BitmapCache(ByteArrayOutputStream outputStream) {
        if (outputStream == null) {
            return;
        }
        mBytes = outputStream.toByteArray();
        try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Bitmap getBitmap() {
        if (mBytes == null) {
            return null;
        }
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inSampleSize=1;
		bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		Rect outPadding=new  Rect(0,0,0,0);
		ByteArrayInputStream in = new ByteArrayInputStream(mBytes);
		Bitmap bm =BitmapFactory.decodeStream(in, outPadding, bmpFactoryOptions);
		return bm;
    }
    
    public Bitmap getBitmap(int width,int height) {
		return getBitmap(width, height, false);
    }
    
    public Bitmap getBitmap(int width,int height,boolean isCompress) {
    	if (mBytes == null) {
            return null;
        }
    	BitmapFactory.Options bmpFactoryOptions = null;
		try {
			bmpFactoryOptions = new BitmapFactory.Options();
			bmpFactoryOptions.inSampleSize=1;
			bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
			
			if (width > 0 || height > 0 ) {
				bmpFactoryOptions.inJustDecodeBounds = true;
				BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length, bmpFactoryOptions);
				BitmapDecodeUtils.calculateInSampleSize(bmpFactoryOptions, width, height);
				LLog.d("bitmap1",bmpFactoryOptions.outWidth +" - "+bmpFactoryOptions.outHeight);
				bmpFactoryOptions.inJustDecodeBounds = false;
				LLog.d("bitmap1","bmpFactoryOptions.inSampleSize "+bmpFactoryOptions.inSampleSize);
			}
			
			Bitmap bmBitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length, bmpFactoryOptions);
			if (isCompress) {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bmBitmap.compress(CompressFormat.PNG, 100, stream);
				mBytes = stream.toByteArray();
			}
			return bmBitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

}
