package com.m.rabbit.constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.m.rabbit.bean.User;
import com.m.rabbit.utils.AppUtils;
import com.m.rabbit.utils.MD5Utils;
import com.m.rabbit.utils.PropertiesUtils;
import com.m.rabbit.utils.StringUtils;
import com.m.rabbit.utils.ToastUtils;
import com.m.rabbit.utils.UrlEncoder;
import com.niuyu.shop.manager.UserManager;

public class ShopUrlFactory {
	
	public static int PHONE = 2;
	public static String platform;
    public static String HTTP_HEAD;
	static {
		HTTP_HEAD = PropertiesUtils.getValueA(PropertiesUtils.PROPERTIES_COMMON_PATH, PropertiesUtils.SERVER_PATH).trim();
        platform = AppConstants.getInstance().getmPlatform();
    }
	
	public static final String URL_UPDATE_VERSION = HTTP_HEAD+ "/checkVesions?appType=%s&appCode=%s";
	public static final String URL_RESET_PASSWORD = HTTP_HEAD+ "/mtvis/userMessage!doResetPassword.action?userName=%s";
	public static final String URL_LOGIN = HTTP_HEAD+ "/login?loginName=%s&pwd=%s";
	public static final String URL_LOGOUT = HTTP_HEAD+ "/logout?meId=%s";
	public static final String URL_REGIST = HTTP_HEAD+ "/regist?loginName=%s&type=1&pwd=%s&rePwd=%s&smsId=%s&captcha=%s";
	
	public static final String ON_SELL_GOODS = HTTP_HEAD+"/OnSellGoods?page=%s&pageSize=%s&saleType=%s";
	public static final String GET_GOODS_BY_TYPE = HTTP_HEAD+"/OnSellGoods?page=%s&pageSize=%s&type=%s&saleType=%s&gid=%s&context=%s";
	public static final String PRE_SALE = HTTP_HEAD+"/PresaleGoods?page=%s&pageSize=%s";
	public static final String CLASS = HTTP_HEAD+"/dictSet?typeCode=0000";
	public static final String ORDER = HTTP_HEAD+"/torder?gid=%s&userphone=%s";
	public static final String COUPON = HTTP_HEAD+"/queryCoupon?meId=%s";
	
	public static final String PROJECT_PATH = HTTP_HEAD+"/welcomePage?"+ "starProjectDictCode={0}&page={1}&pageSize={2}";
	public static final String PROJECT_PATH_TEST = HTTP_HEAD+"/welcomePage?"+ "starProjectDictCode={0}";
	public static final String URL_CREATEORDER = HTTP_HEAD+ "/createOrder?meId=%s&goodinfo=%s&sxf=%s&kdf=%s&zj=%s&sjrdh=%s&sjrxm=%s&sheng=%s&shi=%s&qu=%s&jd=%s&ddbz=%s&couId=%s";
	public static final String URL_GET_ORDER = HTTP_HEAD+ "/queryOrder?meId=%s&zt=%s";
	public static final String URL_SHARE = HTTP_HEAD+ "/wxViewGoods.shop?method=viewShareGoods&gid=%s";
	public static final String URL_GOODS_ATTR= HTTP_HEAD+ "/goodsLx?bh=%s";
	
	public static final String URL_ADD_CART = HTTP_HEAD+ "/addShoppingCart?meId=%s&gid=%s&lxbh=%s&dj=%s&ddsl=%s&spzj=%s";
	public static final String URL_DELETE_CART = HTTP_HEAD+ "/delShoppingCart?meId=%s&shopId=%s";
	public static final String URL_GET_CARTS = HTTP_HEAD+ "/queryShoppingCart?meId=%s";
	public static final String URL_COMPANY = HTTP_HEAD+ "/company";
	public static final String URL_QUERY_ADDRESS = HTTP_HEAD+ "/queryAddress?meId=%s&aid=%s";
	public static final String URL_DEL_ADDRESS = HTTP_HEAD+ "/delAddress?meId=%s&aid=%s";
	public static final String URL_UPDATE_ADDRESS = HTTP_HEAD+ "/updateAddress?meId=%s&aid=%s&sheng=%s&shi=%s&qu=%s&jd=%s&post=%s&sjrxm=%s&sjrdh=%s&sfcy=%s";
	public static final String URL_ADD_ADDRESS = HTTP_HEAD+ "/addAddress?meId=%s&aid=%s&sheng=%s&shi=%s&qu=%s&jd=%s&post=%s&sjrxm=%s&sjrdh=%s&sfcy=%s";
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param type
	 * @param saleType 销售方法（0全部，1电视，2手机）saleType
	 * @return
	 */
	public static String getOnSellGoods(int page,int pageSize,int saleType){
		return String.format(ON_SELL_GOODS, page,pageSize,saleType);
	}
	
	public static String getLogout(String meId){
		return String.format(URL_LOGOUT, meId);
	}
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param type
	 * @param saleType 销售方法（0全部，1电视，2手机）saleType
	 * @param gid 详情
	 * @param context 按名称或者内容搜索
	 * @param orderBy 排序方式： 
						0上架时间（升序）
						1上架时间（降序）
						2价格（升序）
						3价格（降序）
						4评分（升序）
						5评分（降序）
						6电视端销量（升序）
						7电视端销量（降序）
						8移动端销量（升序）
						9移动端销量（降序）
						10总销量（升序）
						11总销量（降序）
						12收藏数（升序）
						13收藏数（降序）
						14默认

	 * @return
	 */
	public static String getGoodsByType(int page,int pageSize,String type,int saleType,String gid,String context){
		return String.format(GET_GOODS_BY_TYPE,page,pageSize,type,saleType,gid,context);
	}
	
	public static String getPreSale(int page,int pageSize){
		return String.format(PRE_SALE, page,pageSize);
	}
	
	public static String getClasses(){
		return CLASS;
	}
	
	public static String addOrder(String pid,String usePhone){
		return String.format(ORDER,pid,usePhone);
	}
	
	public static String getCoupon(String meid){
		return String.format(COUPON,meid);
	}
	
	/**
	 * 
	 * @param meId 会员编号
	 * @param goodinfo 商品ID-商品编号-数量-商品属性：goodinfo="gid-gno-count-bh|gid-gno-count-bh"目前BH以#代替
	 * @param sxf 手续费（没有以0补）
	 * @param kdf 快递费（没有以0补）
	 * @param zj 订单总额
	 * @param sjrdh 电话
	 * @param sjrxm 姓名
	 * @param sheng 省
	 * @param shi 市
	 * @param qu 区
	 * @param jd 接到
	 * @param ddbz 订单特别说明（非必填项）
	 * @return
	 */
	public static String createOrder(String meId,String goodinfo,String sxf,String kdf,String zj,String sjrdh,String sjrxm,String sheng,String shi,String qu,String jd,String ddbz,String coId) {
		return String.format(URL_CREATEORDER, meId,goodinfo,sxf,kdf,zj,sjrdh,sjrxm,sheng,shi,qu,jd,ddbz,coId);
	}
	
	public static String getOrder(String meId,String zt){
		return String.format(URL_GET_ORDER, meId,zt);
	}
	
	public static String addShoppingCart(String meId,String gId,String lxbh,double dj,int count,double spzj){
		return String.format(URL_ADD_CART, meId,gId,lxbh,dj,count,spzj);
	}
	
	public static String delShoppingCart(String meid,String shopId){
		return String.format(URL_DELETE_CART,meid,shopId);
	}
	
	public static String getShoppingCarts(String meId){
		return String.format(URL_GET_CARTS, meId);
	}
	
	public static String addFavorite(String meId,String gId){
		return String.format(createUrl("/addFavorite?","meId","gid"), meId,gId);
	}
	
	public static String delFavorite(String meid,String gId){
		return String.format(createUrl("/delFavorite?","meId","gid"),meid,gId);
	}
	
	public static String getFavorites(String meId){
		return String.format(createUrl("/queryFavorite?","meId","gid"), meId,"1");
	}
	
	public static String queryMemberImg(){
		return String.format(createUrl("/queryMemberImg?"));
	}
	
	/**
	 * 	1：QQ分享
		2：朋友圈分享
		3:微信好友分享
	 * @return
	 */
	public static String addShareLog(String gid,int shareType){
		return String.format(createUrl("/addShareLog?","gid","shareType"),gid,shareType);
	}
	
	public static String updateUser(User user){
		if (user == null) {
			return "updateUser null";
		}
		StringBuilder sBuilder = new StringBuilder(String.format(createUrl("/updateMemberBaseInfo?","meId"),user.meId));
		if (StringUtils.isNotEmpty(user.meName)) {
			sBuilder.append("&meName="+user.meName);
		}
		if (StringUtils.isNotEmpty(user.age)) {
			sBuilder.append("&age="+user.age);
		}
		if (StringUtils.isNotEmpty(user.sex)) {
			sBuilder.append("&sex="+user.sex);
		}
		if (StringUtils.isNotEmpty(user.img)) {
			sBuilder.append("&img="+user.img);
		}
		if (StringUtils.isNotEmpty(user.address)) {
			sBuilder.append("&address="+UrlEncoder.encode(user.address));
		}
		if (StringUtils.isNotEmpty(user.phone)) {
			sBuilder.append("&phone="+user.phone);
		}
		return sBuilder.toString();
//		return String.format(createUrl("/updateMemberBaseInfo?","meId","meName","sex","age","img","address","phone"),user.meId,user.meName,user.sex,user.age,user.img,UrlEncoder.encode(user.address),user.phone);
	}
	
	public static String updateUser(User user,String field,String value){
		return String.format(createUrl("/updateMemberBaseInfo?","meId",field),user.meId,value)+getUserSign(user);
	}
	
	public static String queryUser(){
		return String.format(createUrl("/queryMemberBaseInfo?"));
	}
	
	public static String queryBanner(int platform){
		return String.format(createUrl("/queryBanner?","type"),platform);
	}
	
	public static String exchangePaperCoupon(String code){
		return String.format(createUrl("/exchangePaperCoupon?","code"),code);
	}
	
	public static String queryAddrsss(String meId,String aid){
		return String.format(URL_QUERY_ADDRESS, meId,aid);
	}
	
	public static String addAddress(String meId,String aid,String sheng,String shi,String qu,String jd,String post,String sjrxm,String sjrdh,String sfcy){
		return String.format(URL_ADD_ADDRESS, meId,aid,sheng,shi,qu,jd,post,sjrxm,sjrdh,sfcy);
	}
	
	public static String updateAddress(String meId,String aid,String sheng,String shi,String qu,String jd,String post,String sjrxm,String sjrdh,String sfcy){
		return String.format(URL_UPDATE_ADDRESS, meId,aid,sheng,shi,qu,jd,post,sjrxm,sjrdh,sfcy);
	}
	
	public static String delAddress(String meId,String aid){
		return String.format(URL_DEL_ADDRESS, meId,aid);
	}
	
	public static String getCompany(){
		return URL_COMPANY;
	}
	
	public static String getShareUrl(String gid){
		return String.format(URL_SHARE, gid);
	}
	
	public static String getGoodsAttr(String attr){
		return String.format(URL_GOODS_ATTR, attr);
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

	public static String getLoginUrl(String account, String password) {
		if(password!=null && !password.equals("")){
			password=MD5Utils.getMD5Lower(password);
		}else{
			return null; 
		}
		String url = String.format(URL_LOGIN, account, password);
		return url;
	}
	
	public static String getRegistUrl(String loginname,
			String password,String password2,String smsID,String verify) {
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
		String url = String.format(URL_REGIST, loginname,password,password2,smsID,verify);
		return url;
	}
	
	/**
	 * appType	手机类型（ANDROID：0,IPHONE：1,其它：2)
		appCode	应用版本号（4位数字）	String	4
	 * @return
	 */
	public static String getUpdateUrl(String versionCode) {
		return  String.format(URL_UPDATE_VERSION, "0",versionCode);
	}
	
	private static String createUrl(String head,String... fields){
		StringBuilder sb = new StringBuilder();
		sb.append(HTTP_HEAD);
		sb.append(head);
		for (int i = 0; i < fields.length; i++) {
			if (i != 0 ) {
				sb.append("&"); 
			}
			sb.append(fields[i]+"=%s");
		}
		return sb.toString();
	}
	
	public static String getUserSign(User user){
		try {
			return "&kid="+user.kid+"&sign="+MD5Utils.getMD5Lower(user.meId+user.kid+user.key);
		} catch (Exception e) {
			ToastUtils.showToast(AppUtils.mContext, "生成用户签名失败");
		}
		return "";
	}
	
}
