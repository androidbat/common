package com.m.rabbit.filetask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFileTask extends FileTask{
	private boolean deleteSourceFile=false;
	public CopyFileTask(String sourcePath,String targetPath,boolean delSource){
		this.sourcePath=sourcePath;
		this.targetPath=targetPath;
		this.deleteSourceFile=delSource;
	}
	@Override
	protected Object fileOperate() {
		if(sourcePath!=null && targetPath!=null){
			try{
				File file=new File(sourcePath);
				if(file.exists()){
					if(file.isFile()){
						File file2=new File(targetPath);
						if(!file2.exists()){
							if(file2.createNewFile()){
								copyFile(file,file2);
								if(deleteSourceFile){
									file.delete();
								}
								return SUCCESS;
							}
						}
						return FAIL;
					}else if(file.isDirectory()){
						copyDirectiory(sourcePath,targetPath);
						return SUCCESS;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return FAIL;
	}
	 // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
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

 // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                if(targetFile.createNewFile()){
                	copyFile(sourceFile, targetFile);
				}
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

}
