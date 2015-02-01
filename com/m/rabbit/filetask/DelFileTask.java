package com.m.rabbit.filetask;

import java.io.File;

public class DelFileTask extends FileTask{
	public DelFileTask(String filePath){
		sourcePath=filePath;
	}
	@Override
	protected Object fileOperate() {
		if(sourcePath!=null){
			try{
				File file=new File(sourcePath);
				if(file.exists()){
					if(file.isFile()){
						file.delete();
						return SUCCESS;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return FAIL;
	}
	
}
