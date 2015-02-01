package com.m.rabbit.utils;

import com.m.rabbit.view.DynamicHeightImageView;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;

public class VUtils {
	/**
	 * Utility method to make getting a View via findViewById() more safe &
	 * simple.
	 * 
	 * - Casts view to appropriate type based on expected return value - Handles
	 * & logs invalid casts
	 * 
	 * @param context
	 *            The current Context or Activity that this method is called
	 *            from
	 * @param id
	 *            R.id value for view
	 * @return View object, cast to appropriate type based on expected return
	 *         value.
	 * @throws ClassCastException
	 *             if cast to the expected type breaks.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById(Activity context, int id) {
		T view = null;
		View genericView = context.findViewById(id);
		try {
			view = (T) (genericView);
		} catch (Exception ex) {
			String message = "Can't cast view (" + id + ") to a "
					+ view.getClass() + ".  Is actually a "
					+ genericView.getClass() + ".";
			LLog.e("PercolateAndroidUtils", message);
			throw new ClassCastException(message);
		}

		return view;
	}

	/**
	 * Utility method to make getting a View via findViewById() more safe &
	 * simple.
	 * 
	 * - Casts view to appropriate type based on expected return value - Handles
	 * & logs invalid casts
	 * 
	 * @param parentView
	 *            Parent View containing the view we are trying to get
	 * @param id
	 *            R.id value for view
	 * @return View object, cast to appropriate type based on expected return
	 *         value.
	 * @throws ClassCastException
	 *             if cast to the expected type breaks.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById(View parentView, int id) {
		T view = null;
		View genericView = parentView.findViewById(id);
		try {
			view = (T) (genericView);
		} catch (Exception ex) {
			String message = "Can't cast view (" + id + ") to a "
					+ view.getClass() + ".  Is actually a "
					+ genericView.getClass() + ".";
			Log.e("PercolateAndroidUtils", message);
			throw new ClassCastException(message);
		}

		return view;
	}

	/**
	 * Get text as String from EditView. <b>Note:</b> returns "" for null
	 * EditText, not a NullPointerException
	 * 
	 * @param view
	 *            EditView to get text from
	 * @return the text
	 */
	public static String getText(TextView view) {
		String text = "";
		if (view != null) {
			text = view.getText().toString();
		} else {
			LLog.e("PercolateAndroidUtils",
					"Null view given to getText().  \"\" will be returned.");
		}
		return text;
	}

	/**
	 * Get text as String from EditView. <b>Note:</b> returns "" for null
	 * EditText, not a NullPointerException
	 * 
	 * @param context
	 *            The current Context or Activity that this method is called
	 *            from
	 * @param id
	 *            Id for the TextView/EditView to get text from
	 * @return the text
	 */
	public static String getText(Activity context, int id) {
		TextView view = findViewById(context, id);

		String text = "";
		if (view != null) {
			text = view.getText().toString();
		} else {
			LLog.e("PercolateAndroidUtils",
					"Null view given to getText().  \"\" will be returned.");
		}
		return text;
	}

	/**
	 * Append given text String to the provided view (one of TextView or
	 * EditText).
	 * 
	 * @param view
	 *            View to update
	 * @param toAppend
	 *            String text
	 */
	public static void appendText(TextView view, String toAppend) {
		String currentText = getText(view);
		view.setText(currentText + toAppend);
	}

	/**
	 * Go away keyboard, nobody likes you.
	 * 
	 * @param context
	 *            The current Context or Activity that this method is called
	 *            from
	 * @param field
	 *            field that holds the keyboard focus
	 */
	public static void setKeyboardVisibility(Context context, View field,boolean isVisible) {
		try {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (isVisible) {
				imm.showSoftInput(field, 0);
			}else{
				imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
			}
		} catch (Exception ex) {
			LLog.e("PercolateAndroidUtils",
					"Error occurred trying to hide the keyboard.  Exception="
							+ ex);
		}
	}

	/**
	 * Convert view to an image. Can be used to make animations smoother.
	 * 
	 * @param context
	 *            The current Context or Activity that this method is called
	 *            from
	 * @param viewToBeConverted
	 *            View to convert to a Bitmap
	 * @return Bitmap object that can be put in an ImageView. Will look like the
	 *         converted viewToBeConverted.
	 */
	public static Bitmap viewToImage(Context context, WebView viewToBeConverted) {
		int extraSpace = 2000; // because getContentHeight doesn't always return
								// the full screen height.
		int height = viewToBeConverted.getContentHeight() + extraSpace;

		Bitmap viewBitmap = Bitmap.createBitmap(viewToBeConverted.getWidth(),
				height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(viewBitmap);
		viewToBeConverted.draw(canvas);

		// If the view is scrolled, cut off the top part that is off the screen.
		try {
			int scrollY = viewToBeConverted.getScrollY();
			if (scrollY > 0) {
				viewBitmap = Bitmap.createBitmap(viewBitmap, 0, scrollY,
						viewToBeConverted.getWidth(), height - scrollY);
			}
		} catch (Exception ex) {
			LLog.e("PercolateAndroidUtils",
					"Could not remove top part of the webview image.  ex=" + ex);
		}

		return viewBitmap;
	}

	/**
	 * Method used to set text for a TextView
	 * 
	 * @param context
	 *            The current Context or Activity that this method is called
	 *            from
	 * @param field
	 *            R.id.xxxx value for the text field.
	 * @param text
	 *            Text to place in the text field.
	 */
	public static void setText(Activity context, int field, String text) {
		View view = context.findViewById(field);
		if (view instanceof TextView) {
			((TextView) view).setText(text);
		} else {
			LLog.e("PercolateAndroidUtils",
					"ViewUtils.setText() given a field that is not a TextView");
		}
	}

	/**
	 * Method used to set text for a TextView
	 * 
	 * @param parentView
	 *            The View used to call findViewId() on
	 * @param field
	 *            R.id.xxxx value for the text field.
	 * @param text
	 *            Text to place in the text field.
	 */
	public static void setText(View parentView, int field, String text) {
		View view = parentView.findViewById(field);
		if (view instanceof TextView) {
			((TextView) view).setText(text);
		} else {
			LLog.e("PercolateAndroidUtils",
					"ViewUtils.setText() given a field that is not a TextView");
		}
	}

	/**
	 * Sets visibility of the given view to <code>View.GONE</code>.
	 * 
	 * @param context
	 *            The current Context or Activity that this method is called
	 *            from
	 * @param id
	 *            R.id.xxxx value for the view to
	 *            hide"expected textView to throw a ClassCastException" +
	 *            textView
	 */
	public static void hideView(Activity context, int id) {
		if (context != null) {
			View view = context.findViewById(id);
			if (view != null) {
				view.setVisibility(View.GONE);
			} else {
				LLog.e("PercolateAndroidUtils","View does not exist.  Could not hide it.");
			}
		}
	}

	/**
	 * Sets visibility of the given view to <code>View.VISIBLE</code>.
	 * 
	 * @param context
	 *            The current Context or Activity that this method is called
	 *            from
	 * @param id
	 *            R.id.xxxx value for the view to show
	 */
	public static void showView(Activity context, int id) {
		if (context != null) {
			View view = context.findViewById(id);
			if (view != null) {
				view.setVisibility(View.VISIBLE);
			} else {
				LLog.e("PercolateAndroidUtils","View does not exist.  Could not hide it.");
			}
		}
	}
	
	public static void setOnClickListener(OnClickListener onClickListener,View... views){
    	if (views != null) {
			for (int i = 0; i < views.length; i++) {
				views[i].setOnClickListener(onClickListener);
			}
		}
    }
	
	/**
	 * SetLayoutParam height according bitmap's ratio and width
	 * @param view
	 * @param width
	 * @param bm
	 */
	public static void setLayoutParamFromBm(View view,int width,Bitmap bm){
		if (bm != null) {
			
			try {
				DynamicHeightImageView div = (DynamicHeightImageView) view;
				if (div != null) {
					div.setHeightRatio(((float)bm.getHeight())/bm.getWidth());
				}
			} catch (Exception e) {
				LayoutParams layoutParams = view.getLayoutParams();
				layoutParams.height = (int) (width * ((float)bm.getHeight())/bm.getWidth());
				layoutParams.width = width;
				view.setLayoutParams(layoutParams);
			}
		}
    }
	
	/**
	 * if width or height is equels -1,do not set corresponding layoutparams
	 * @param view
	 * @param width -1 ignore
	 * @param height
	 */
	public static void setLayoutParams(View view,int width,int height){
    	LayoutParams layoutParams = view.getLayoutParams();
    	if (height != -1) {
    		layoutParams.height = height;
		}
    	if (width != -1) {
    		layoutParams.width = width;
		}
		view.setLayoutParams(layoutParams);
    }
	
	private static final int MEASURED_STATE_MASK = 0xff000000;
	private static final int MEASURED_STATE_TOO_SMALL = 0x01000000;
	public static int resolveSizeAndState(int size, int measureSpec,
			int childMeasuredState) {
		int result = size;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		switch (specMode) {
		case MeasureSpec.UNSPECIFIED:
			result = size;
			break;
		case MeasureSpec.AT_MOST:
			if (specSize < size) {
				result = specSize | MEASURED_STATE_TOO_SMALL;
			} else {
				result = size;
			}
			break;
		case MeasureSpec.EXACTLY:
			result = specSize;
			break;
		}
		return result | (childMeasuredState & MEASURED_STATE_MASK);
	}

}
