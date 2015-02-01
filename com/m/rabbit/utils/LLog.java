package com.m.rabbit.utils;

import android.util.Log;


public class LLog {
    private static  String TAG = "mmm";
    public static  boolean SHOW_LOG = false;
    public static  boolean SHOW_ERROR = false;
    
    public static void printStackTrace(Throwable e) {
        if (SHOW_LOG) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }
    
    static{
    	String logEnable = PropertiesUtils.getDomainValue("log", "");
    	if ("yes".equals(logEnable)) {
    		SHOW_LOG = true;
		}else{
			SHOW_LOG = false;
		}
    }
    
    @SuppressWarnings("rawtypes")
	public static String makeTag(Class cls) {
		return cls.getSimpleName();
	}

    public static void v(String msg) {
        if (SHOW_LOG) {
            try {
                Log.v(TAG, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void i(String msg) {
        if (SHOW_LOG) {
            try {
                Log.i(TAG, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void d(String msg) {
        if (SHOW_LOG) {
            try {
                Log.d(TAG, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void w(String msg) {
        if (SHOW_LOG) {
            try {
                Log.w(TAG, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void e(String msg) {
        if (SHOW_LOG) {
            try {
                Log.e(TAG, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void v(String tag, String msg) {
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.v(tag, msg);
                }
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.i(tag, msg);
                }
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }
    
    public static void i(String tag, Object msg) {
        if (SHOW_LOG) {
            try {
                if (msg != null) {
                    Log.i(tag, msg+"");
                }
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (SHOW_LOG) {
            try {
                Log.d(tag, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (SHOW_LOG) {
            try {
                Log.w(tag, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (SHOW_LOG) {
            try {
                Log.e(tag, msg);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }
    
    public static void e(String tag, String msg, Throwable th) {
        if (SHOW_LOG) {
            try {
                Log.e(tag, msg, th);
            } catch (Exception e) {
                LLog.printStackTrace(e);
            } catch (Error e) {
                LLog.printStackTrace(e);
            }
        }
    }

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String tAG) {
        TAG = tAG;
    }

    public static void setSHOW_LOG(boolean sHOW_LOG) {
        SHOW_LOG = sHOW_LOG;
    }
}
