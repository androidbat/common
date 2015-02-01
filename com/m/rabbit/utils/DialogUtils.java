package com.m.rabbit.utils;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

/**
 * provide dialog
 * 
 */
public class DialogUtils {

    public static final int SOURCE_POSITIVE = -5;
    public static final int SOURCE_NEUTRAL = -6;
    public static final int SOURCE_NEGATIVE = -7;
	public static final int SOURCE_RADIO_BUTTON = -10;

    /**
     * simple message dialog
     * 
     * @param context
     * @param title
     * @return
     */
    public static Dialog buildDialog(Context context, String title, String ok) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                context);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setOnKeyListener(new OnKeyListener(){
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                    KeyEvent event) {
                return true;
            }
        });
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	try {
					dialog.dismiss();
				} catch (Exception e) {
				    
				}
            }
        });
        return builder.create();
    }
    /**
     * 
     * @author mingrenhan
     * 2011-11-16
     * @param context
     * @param title
     * @param message 
     * @param positiveButtonText positive button
     * @param neutralButtonText neutral button
     * @param negativeButtonText negative button
     * @param onClickListener event listener
     * @return
     */
    public static AlertDialog getMessageDialog(Context context,String title, String message, String positiveButtonText, String neutralButtonText, String negativeButtonText,
            final DialogOnClickListener onClickListener) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setOnKeyListener(new OnKeyListener(){
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
            
        });
        if (null != message && message.trim().length() > 0) {
            builder.setMessage(message);
        }
        if (null != title && title.trim().length() > 0) {
            builder.setTitle(title);
        }
        setButtons(builder, positiveButtonText, neutralButtonText, negativeButtonText, onClickListener);
        return builder.create();
    }

    public static AlertDialog getMessageDialog(Context context,String title, String message, String positiveButtonText,
            String negativeButtonText, OnClickListener positiveListener) {
        AlertDialog dialog;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        if (null != message && message.trim().length() > 0) {
            builder.setMessage(message);
        }
        if (null != title && title.trim().length() > 0) {
            builder.setTitle(title);
        }
        builder.setNegativeButton(negativeButtonText, new OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(positiveButtonText, positiveListener);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
    /**
     * 等待的对话框
     * 
     * @param context
     * @param title 标题
     * @param msg 内容
     */
    public static ProgressDialog getProgressDialog(Context context, String title, String msg) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(0);
        mProgressDialog.setCanceledOnTouchOutside(false);
        return mProgressDialog;
    }
    /**
     * 等待的对话框
     * 
     * @param context
     * @param title 标题
     * @param msg 内容
     */
    public static ProgressDialog getProgressDialog(Context context, String title, String msg,int theme) {
    	ProgressDialog mProgressDialog = new ProgressDialog(context,theme);
    	mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	mProgressDialog.setTitle(title);
    	mProgressDialog.setMessage(msg);
    	mProgressDialog.setIndeterminate(false);
    	mProgressDialog.setCancelable(true);
    	mProgressDialog.setMax(100);
    	mProgressDialog.setProgress(0);
    	mProgressDialog.setCanceledOnTouchOutside(false);
    	return mProgressDialog;
    }
    
    /**
     * Custom dialog
     * 
     * @author mingrenhan 2011-8-29 14:10:23
     * @param context
     * @param title
     * @param iconId
     * @param view
     * @param positiveButtonText
     * @param neutralButtonText
     * @param negativeButtonText
     * @param onClickListener
     * @return
     */
    public static AlertDialog getCustomDialog(Context context, String title,int iconId,
            View view, String positiveButtonText, String neutralButtonText,
            String negativeButtonText,
            final DialogOnClickListener onClickListener) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(view!=null)
            builder.setView(view);
        if (null != title && title.trim().length() > 0) {
            builder.setTitle(title);
        }
        if(iconId>0){
            builder.setIcon(iconId);
        }
        if (null != positiveButtonText
                && positiveButtonText.trim().length() > 0) {
            builder.setPositiveButton(positiveButtonText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                int whichButton) {
                            if (null != onClickListener) {
                                onClickListener.onDialogClick(dialog,
                                        whichButton, SOURCE_POSITIVE);
                            }
                        }
                    });
        }
        if (null != neutralButtonText && neutralButtonText.trim().length() > 0) {
            builder.setNeutralButton(neutralButtonText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                int whichButton) {
                            if (null != onClickListener) {
                                onClickListener.onDialogClick(dialog,
                                        whichButton, SOURCE_NEUTRAL);
                            }
                        }
                    });
        }
        if (null != negativeButtonText
                && negativeButtonText.trim().length() > 0) {
            builder.setNegativeButton(negativeButtonText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                int whichButton) {
                            if (null != onClickListener) {
                                onClickListener.onDialogClick(dialog,
                                        whichButton, SOURCE_NEGATIVE);
                            }
                        }
                    });
        }
        dialog = builder.create();
        return dialog;
    }
    
    /**
	 * 单选列表对话框
	 * @param context
	 * @param title
	 *            标题
	 * @param data
	 *            list数据
	 * @param positiveButtonText
	 *            正向按钮的文字描，没有则不创建这个按钮
	 * @param neutralButtonText
	 *            中间按钮的文字描，没有则不创建这个按钮
	 * @param negativeButtonText
	 *            反向按钮的文字描 ，没有则不创建这个按钮
	 * @param onClickListener
	 *            监听onClick函数被触发的事件
	 */
	public static AlertDialog getRadioButtonDialog(Context context, String title, String[] data,int checkItem, String positiveButtonText, String neutralButtonText,
			String negativeButtonText, final DialogOnClickListener onClickListener) {
		if (null == data || data.length == 0) {
			return null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if(title!=null){
			builder.setTitle(title);
		}
		builder.setSingleChoiceItems(data, checkItem, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onClickListener.onDialogClick(dialog, which, SOURCE_RADIO_BUTTON);
			}
		});
		setButtons(builder, positiveButtonText, neutralButtonText, negativeButtonText, onClickListener);
		return builder.create();
	}

    public interface DialogOnClickListener {
        void onDialogClick(DialogInterface dialog, int whichButton, int source);
    }
    
    /**
     * 
     * @param builder
     * @param positiveButtonText
     * @param neutralButtonText
     * @param negativeButtonText
     * @param onClickListener
     */
    private static void setButtons(AlertDialog.Builder builder, String positiveButtonText, String neutralButtonText, String negativeButtonText,
            final DialogOnClickListener onClickListener) {
        
        if (null != positiveButtonText && positiveButtonText.trim().length() > 0) {
            builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (null != onClickListener) {
                        onClickListener.onDialogClick(dialog, whichButton, SOURCE_POSITIVE);
                    }
                }
            });
        }

        if (null != neutralButtonText && neutralButtonText.trim().length() > 0) {
            builder.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (null != onClickListener) {
                        onClickListener.onDialogClick(dialog, whichButton, SOURCE_NEUTRAL);
                    }
                }
            });
        }

        if (null != negativeButtonText && negativeButtonText.trim().length() > 0) {
            builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (null != onClickListener) {
                        onClickListener.onDialogClick(dialog, whichButton, SOURCE_NEGATIVE);
                    }
                }
            });
        }
    }
    
    
    public static void safeShowDialog(Dialog dialog){
        try{            
            if(dialog!=null){
                dialog.show();
            }
        }catch(Exception e){
            if(dialog!=null){
            	try {
					dialog.dismiss();
				} catch (Exception e2) {

				}
                dialog=null;
            }
        }catch(Error e){
            if(dialog!=null){
            	try {
					dialog.dismiss();
				} catch (Exception e2) {

				}
                dialog=null;
            }
        }
    }
}
