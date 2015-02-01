package com.m.rabbit.constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import android.R.integer;

import com.m.rabbit.bean.User;
import com.m.rabbit.config.MyConfig;
import com.m.rabbit.utils.MD5Utils;
import com.m.rabbit.utils.PropertiesUtils;

public class UrlFactory {
	public static String platform;
    public static String HTTP_HEAD;
	static {
		HTTP_HEAD = PropertiesUtils.getValueA(PropertiesUtils.PROPERTIES_COMMON_PATH, PropertiesUtils.SERVER_PATH).trim();
        platform = AppConstants.getInstance().getmPlatform();
    }
	public static final String URL_RESET_PASSWORD = HTTP_HEAD+ "/mtvis/userMessage!doResetPassword.action?userName=%s";
	public static final String URL_LOGIN = HTTP_HEAD+ "/login?loginName=%s&pwd=%s";
	public static final String URL_REGIST = HTTP_HEAD+ "/regist?loginName=%s&type=1&pwd=%s&rePwd=%s";
	
	public static final String PROJECT_PATH = HTTP_HEAD+"/welcomePage?"+ "starProjectDictCode={0}&page={1}&pageSize={2}";
	public static final String PROJECT_PATH_TEST = HTTP_HEAD+"/welcomePage?"+ "starProjectDictCode={0}";
	public static final String URL_ATTENTION = HTTP_HEAD+ "/projectMember?partType=%s&pId=%s&meId=%s";
	public static final String URL_CHECK_TEL_URL = HTTP_HEAD+ "/regist?loginName=%s&type=0";
	public static final String URL_REQUEST_VERIFY_CODE = HTTP_HEAD+ "/sms?loginName=%s&type=0";
	public static final String URL_CHECK_VERIFY_CODE = HTTP_HEAD+ "/sms?smsId=%s&type=1&captcha=%s&loginName=%s";
	public static final String URL_USER_PROJECT = HTTP_HEAD+ "/userProject?meId=%s&type=%s&page=%s&pageSize=%s";
	public static final String URL_PROJECT_DETAIL = HTTP_HEAD+ "/projectDetail?pId=%s&userId=%s";
	public static final String URL_ADD_DELETE_FRIEND = HTTP_HEAD+ "/addFriend?fromUserId=%s&toUserId=%s&type=%s";
	public static final String URL_GET_FRIENDS = HTTP_HEAD+ "/refreshFriend?fromUserId=%s";
	public static final String URL_REQUEST_MESSAGE = HTTP_HEAD+ "/takeMessageLoop";
	public static final String URL_ADD_FRIEND_CONFIRM = HTTP_HEAD+ "/addFriendConfirm?fromUserId=%s&toUserId=%s&status=%s";
	public static final String URL_UPDATE_VERSION = HTTP_HEAD+ "/checkVesions?appType=%s&appCode=%s";
	public static final String URL_UPLOAD = HTTP_HEAD+ "/fileUpload";
	public static final String URL_SHOW_PROJECT = HTTP_HEAD+ "/showProject?page=%s&pageSize=%s&attention=%s&seqtype=%s";
	public static final String ADD_PROJECT = HTTP_HEAD+ "/addProject?json=%s";
	
	/**
	 * 获取消息，如果没有登录userId传null
	 * @param userId
	 * @return
	 */
	public static String getMessageUrl(String userId){
		if(userId==null || userId.equals("")){
			return URL_REQUEST_MESSAGE; //没有登录时
		}else{
			String messageUrl=URL_REQUEST_MESSAGE+"?userId=%s";//登录时
			return String.format(messageUrl, userId);
		}
	}
	
	/**
	 * 获取正在进行中的项目
	 * @param projectId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public static String getProject(String projectId,int page,int pageSize){
//		return MessageFormat.format(PROJECT_PATH_TEST,projectId);
		return MessageFormat.format(PROJECT_PATH,projectId,page,pageSize);
	}
	
	/**
	 * 重置密码接口
	 * 
	 * @param username
	 * @return
	 */
	public static String getResetPasswordUrl(String emailName) {
		String url = String.format(URL_RESET_PASSWORD, emailName);
		return url;
	}
	public static String getCheckTelphoneUrl(String tel) {
		if(tel==null){
			return null;
		}
		String url = String.format(URL_CHECK_TEL_URL, tel);
		return url;
	}
	public static String getVerifyCodeUrl(String tel) {
		if(tel==null){
			return null;
		}
		String url = String.format(URL_REQUEST_VERIFY_CODE, tel);
		return url;
	}
	public static String getCheckVerifyCodeUrl(String smsId,String tel,String verifyCode) {
		if(verifyCode==null){
			return null;
		}
		String url = String.format(URL_CHECK_VERIFY_CODE, smsId,verifyCode,tel);
		return url;
	}

	public static String getLoginUrl(String account, String password) {
		if(password!=null && !password.equals("")){
			password=MD5Utils.getMD5Lower(password);
		}else{
			return null; 
		}
		String url = String.format(URL_LOGIN, account, password);
		return url;
	}
	public static String getAttentionUrl(String projectId) {
		if(projectId==null || projectId.equals("")){
			return null;
		}
		String userId=MyConfig.getUserId();
		if(userId==null){
			return null;
		}
		String url = String.format(URL_ATTENTION,UIConstant.Attention.ATTENTION, projectId, userId);
		return url;
	}
	
	public static String getRegistUrl(String loginname,
			String password,String password2) {
		if (password != null && !password.equals("")) {
			password = MD5Utils.getMD5Lower(password);
		} else {
			return null;
		}
		if (password2 != null && !password2.equals("")) {
			password2 = MD5Utils.getMD5Lower(password2);
		} else {
			return null;
		}
		if (loginname != null && !loginname.trim().equals("")) {
			try {
				loginname = URLEncoder.encode(loginname.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return null;
		}
		String url = String.format(URL_REGIST, loginname,password,password2);
		return url;
	}
	
	/**
	 * 
	 * @param meId 会员id
	 * @param type (0关注，1参与，2发起)
	 */
	public static String getUserProject(String meId,int type,int page,int pageSize){
		return MessageFormat.format(URL_USER_PROJECT,meId,type,page,pageSize);
	}
	
	public static String getProjectDetail(String pid,String userId){
		return String.format(URL_PROJECT_DETAIL, pid,userId);
	}
	
	/**
	 * 
	 * @param type(0添加好友，1删除好友)
	 * @param toUserId
	 * @return
	 */
	public static String addDelFriend(String fromUserId,String toUserId,String type){
		return String.format(URL_ADD_DELETE_FRIEND, fromUserId,toUserId,type);
	}
	
	public static String getFriends(String fromUserId){
		return String.format(URL_GET_FRIENDS, fromUserId);
	}
	
	/**
	 * 
	 * @param fromUserId 当前用户的编号
	 * @param toUserId 要添加好友的ID
	 * @param status 0同意 1拒绝
	 * @return
	 */
	public static String addFriendConfirm(String fromUserId,String toUserId,int status){
		return String.format(URL_ADD_FRIEND_CONFIRM, fromUserId,toUserId,status);
	}

	public static String getUpdateUrl(String versionCode) {
		return  String.format(URL_UPDATE_VERSION, "0",versionCode);
	}
	
	public static String getUpload(){
		return URL_UPLOAD;
	}
	
	public static String getShowProject(int page,int pageSize,String attention,String seqtype) {
		return String.format(URL_SHOW_PROJECT, page, pageSize, attention, seqtype);
	}
	
	public static String addProject(String json) {
		return String.format(ADD_PROJECT, json);
	}

}
