package com.m.rabbit.upload.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m.rabbit.constant.UIConstant;
import com.m.rabbit.constant.UrlFactory;
import com.m.rabbit.upload.bean.UpLoadTaskInfo;
import com.m.rabbit.upload.bean.UploadResult;
import com.m.rabbit.upload.bean.UploadResultData;
import com.m.rabbit.utils.FileUtils;
import com.m.rabbit.utils.LLog;
import com.m.rabbit.utils.StringUtils;


public class UploadHttpAction {
//    private static String ADD_UPLOAD_URL = "http://192.168.1.100:8080/fileUpload";

    private final static int TYPE_UPLOAD_START = 1;
    private final static int TYPE_UPLOAD_DATA = 2;
    private final static int TYPE_UPLOAD_END = 3;
	private static String 	   FILE_NAME="fileName"; //文件名
	private static String	   TITLE="title";//标题
	private static String 	   FILE_TYPE="file_type";//类型 :图片，视频，文档，压缩包
	private static String 	   TYPE="type";//一个文件分三步走，第一步1（代表传文件相关的属性），第二步2（代表传文件），第三步3（代表传完）
	private static String 	   DESP="desp";//描述
    private static String 	   FROM_USER_ID="fromUserId";
    private static String 	   TO_USER_ID="toUserId";
    private static String 	   FILE_SIZE="fileSize";
    private static String      NET_TYPE = "nettype";
    private static String      UPLOAD_FROM = "uploadFrom";
    private static String 	   ID = "id";
    private static String 	   PARTNO = "partNo";
    private static String 	   PARTSIZE = "partsize";
    
    public static final int STATA_ERROR = 500;
    public static final int CONNECTION_SUCCEED = 400;
    
    public static final String METHOD_NMAE = "m";
    public static final String MODIFY_NMAE = "updateInfo";
    
    public static final String NET_WIFI = "wifi";

    public boolean addUpload(String netType,UpLoadTaskInfo entity,boolean needProxy) throws MalformedURLException, IOException {
    	UploadHttpConnection conn = new UploadHttpConnection(UrlFactory.getUpload());
        PostFormData postFormData = new PostFormData();
        HashMap<String, String> paramsMap = new HashMap<String, String>();
//        long time = new Date().getTime();
        paramsMap.put(TITLE, URLEncoder.encode(entity.getTitle(),"gbk"));
        paramsMap.put(NET_TYPE, netType);
        paramsMap.put(FROM_USER_ID, entity.getFromUserId());
        paramsMap.put(TO_USER_ID, entity.getToUserId());
        paramsMap.put(FILE_NAME, URLEncoder.encode(entity.getFileName(),"gbk"));
        paramsMap.put(FILE_TYPE, entity.getType());
        paramsMap.put(TYPE, TYPE_UPLOAD_START+"");
        long fileSize = 0 ;
        if(StringUtils.isNotEmpty(entity.getFilePath())){
            fileSize = new File(entity.getFilePath()).length();
        }
        paramsMap.put(FILE_SIZE, String.valueOf(fileSize));
        //description
        if(StringUtils.isNotEmpty(entity.getDesp())){
            paramsMap.put(DESP, URLEncoder.encode(entity.getDesp(),"gbk"));
        }
        //uploadFrom
        paramsMap.put(UPLOAD_FROM, "00");//uploadFrom=00 表示android上传
        byte[] data = postFormData.buildPostFormBoby(paramsMap);
        String result=conn.post(data,needProxy);
        return parseResult(result,entity);
    }
    
    private boolean parseResult(String result,UpLoadTaskInfo entity){
    	if(!StringUtils.isNotEmpty(result) || result.equals(UploadHttpConnection.CONNECTION_ERR)){
    		return false;
    	}
    	Type type = new TypeToken<UploadResultData<UploadResult>>(){}.getType();
    	UploadResultData<UploadResult> mydata=new Gson().fromJson(result.toString(),type);
		if(mydata!=null && mydata.data!=null && mydata.data.size()>0){
			UploadResult r=mydata.data.get(0);
			if(r.code.equals(UIConstant.REQUEST_RESULT_SUCCESS)){
				if(entity!=null){
					entity.setId(r.fid);
					entity.setUpLoadUrl(r.url);
					entity.setUpload_head_success(true);
				}
				return true;
			}
		}
		return false;
    }

    public boolean uploadVideoNextPart(UpLoadTaskInfo entity,boolean needProxy) throws IOException, MalformedURLException, Exception {
    	FileInputStream in = null;
        FileUtils fileProxy = null;
        UploadHttpConnection conn = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            conn = new UploadHttpConnection(entity.getUpLoadUrl());
            fileProxy = new FileUtils(entity.getFilePath());

            if (entity.getUploadedPart() < entity.getUploadTotal()) {
                MultipartFormData multipartFormData = new MultipartFormData();
                byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] filebyte = new byte[UpLoadTaskInfo.PART_FILE_SIZE];

                in = fileProxy
                        .getFileInputStreamNeedClose(entity.getUploadedPart() * UpLoadTaskInfo.PART_FILE_SIZE);
                int length = in.read(filebyte);
                if (length != -1) {
                    byteArrayOutputStream.write(filebyte, 0, length);
                    multipartFormData.setfileData(fileProxy.getFileName(), byteArrayOutputStream.toByteArray());
                }

                multipartFormData.setPostData(TYPE, String.valueOf(TYPE_UPLOAD_DATA));
                multipartFormData.setPostData(ID, entity.getId());
                multipartFormData.setPostData(PARTNO, String.valueOf(entity.getUploadedPart() + 1));
                multipartFormData.setPostData(PARTSIZE, UpLoadTaskInfo.PART_FILE_SIZE + "");
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", multipartFormData.getContentType());

                byte[] data = multipartFormData.buildMultipartFormDataPostBody();
                String result = conn.post(map, data,needProxy);
                LLog.d("222222222222222222rrrrrrrrrrrrrrrrr");
                return parseResult(result,null);
            } else {
            	LLog.d("222222222222222222eeeeeeeeeeeeeeeee");
                throw new Exception("Upload has completed");
            }
        } catch (Exception e) {
            LLog.printStackTrace(e);
            return false ;
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LLog.printStackTrace(e);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                }catch (Exception e) {
                    // TODO: handle exception
                    LLog.printStackTrace(e);
                }
            }
            if (fileProxy != null) {
                fileProxy.close();
            }
        }
    }

    public boolean endUpload(UpLoadTaskInfo entity,boolean needProxy) throws MalformedURLException, IOException {
        UploadHttpConnection conn = new UploadHttpConnection(UrlFactory.getUpload());
//        UploadHttpConnection conn = new UploadHttpConnection(entity.getUpLoadUrl());
        
        PostFormData postFormData = new PostFormData();
        HashMap<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put(TYPE, String.valueOf(TYPE_UPLOAD_END));
        paramsMap.put(ID, String.valueOf(entity.getId()));
        paramsMap.put(FILE_SIZE, String.valueOf(entity.getFileSize()));
        paramsMap.put(FILE_NAME, entity.getFileName());
        String result =conn.post(postFormData.buildPostFormBoby(paramsMap),needProxy);
        LLog.d("end-------------");
        return parseResult(result,null);
    }
}
