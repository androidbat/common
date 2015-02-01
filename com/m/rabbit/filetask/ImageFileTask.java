package com.m.rabbit.filetask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFileTask extends FileTask{
	private boolean deleteSourceFile=false; 
	private boolean encrypt=true;
	private Object lock=new Object();
	public ImageFileTask(String sourcePath,String targetPath,boolean delSource,boolean encrypt){
		this.sourcePath=sourcePath;
		this.targetPath=targetPath;
		this.deleteSourceFile=delSource;
		this.encrypt=encrypt;
	}
	@Override
	protected Object fileOperate() {
		synchronized(lock){
			if(sourcePath!=null && targetPath!=null){
				try{
					File file=new File(sourcePath);
					if(file.exists()){
						if(file.isFile()){
//							String newName=getNewName(file);
							File file2=new File(targetPath);
							if(!file2.exists()){
								if(file2.createNewFile()){
									if(encrypt){
										copyFile(file,file2);
									}else{
										restoreFile(file,file2);
									}
									if(deleteSourceFile){
										file.delete();
									}
									return SUCCESS;
								}
							}
							return FAIL;
						}else if(file.isDirectory()){
							copyDirectiory(sourcePath,targetPath,encrypt);
							return SUCCESS;
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return FAIL;
	}
	 // 加密文件
    private static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
//        FileOutputStream outStream=null;
        try {
        	if(targetFile==null){
        		return;
        	}
        	if(!targetFile.exists()){
        		if(targetFile.createNewFile()){
    			}else{
    				return;
    			}
        	}
        	
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
//            inBuff.toString().getBytes();
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            byte[] bb=new byte[1];
            outBuff.write(bb[0]);//开头输入1混淆原文件
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    // 解密文件
    public static void restoreFile(File sourceFile, File targetFile) throws IOException {
    	BufferedInputStream inBuff = null;
    	BufferedOutputStream outBuff = null;
//        FileOutputStream outStream=null;
    	try {
    		if(targetFile==null){
    			return;
    		}
    		if(!targetFile.exists()){
    			if(targetFile.createNewFile()){
    			}else{
    				return;
    			}
    		}
    		
    		// 新建文件输入流并对它进行缓冲
    		inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
    		// 新建文件输出流并对它进行缓冲
    		outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
    		
    		// 缓冲数组
    		byte[] b = new byte[1024 * 5];
    		int len;
    		inBuff.skip(1);
    		while ((len = inBuff.read(b)) != -1) {
    			outBuff.write(b, 0, len);
    		}
    		// 刷新此缓冲的输出流
    		outBuff.flush();
    	} finally {
    		// 关闭流
    		if (inBuff != null)
    			inBuff.close();
    		if (outBuff != null)
    			outBuff.close();
    	}
    }

    private static ByteArrayInputStream getStream(FileInputStream in){
		try {
			String fstr=in.toString();
	        fstr=fstr.substring(1);
	        ByteArrayInputStream bis=new ByteArrayInputStream(fstr.getBytes());
	        return bis;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
 // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir,boolean encrypt) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                
                String newName=getNewName(file[i]);
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + newName);
                if(targetFile.createNewFile()){
//                	copyFile(sourceFile, targetFile);
                	if(encrypt){
						copyFile(sourceFile,targetFile);
					}else{
						restoreFile(sourceFile,targetFile);
					}
				}
            }else if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2,encrypt);
            }
        }
    }

    private static String getNewName(File file){
    	if(file==null){
    		return null;
    	}
    	String filename=file.getName();
        String newName=filename;
        if(filename!=null && filename.length()>0){  
        	int ii = filename.lastIndexOf(".");
        	if(ii>-1 && ii<filename.length()){
        		newName = filename.substring(0, ii); //--文件名
//        		extention = filename.substring(i+1); //--扩展名
        		}
        }
        return newName;
    }
}
