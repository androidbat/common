package com.m.rabbit.constant;

public class UIConstant {
	public static int PAGE_SIZE=10;
	public static boolean useCache=true;
	public final static String REQUEST_RESULT_SUCCESS ="SUC_001";
	public static class ProjectId{
		public final static String STAR = "00000000";
	    public final static String OTHER = "00000001";
	}
	public static class Attention{
		public final static String ATTENTION = "00030001";
		public final static String JOIN_IN = "00030002";
	}
	public static class Login{
		public final static String LOGIN_SUCCESS ="SUC_001";
	}
	public static class Register{
		public final static int REGIST_SUCCESS = 0;
		public final static String TEL_EXIST = "REG_003";
		public final static int ACCOUNT_EXIST =2;
		public final static int NICKNAME_EXIST = 3;
	}
	public static class Modify{
		public final static int MODIFY_SUCCESS = 0;
		public final static int MODIFY_FAUIL = 1;
	}
	public static class Sign{
		public final static int SIGN_SUCCESS = 0;
		public final static int SIGN_FAUIL = 1;
		public final static int SIGNED= 2;
	}
	
}
