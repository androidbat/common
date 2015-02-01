package com.m.rabbit.filetask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GetFileTask extends FileTask{
	private  List<InputStream> list;
	public GetFileTask(String filePath,List<InputStream> list){
		this.list=list;
		sourcePath=filePath;
	}
	@Override
	protected Object fileOperate() {
		if(sourcePath!=null){
			try{
				File file=new File(sourcePath);
				if(file.exists()){
					if(file.isFile()){
						setList(file);
						return list;
					}else if(file.isDirectory()){
						return getList(sourcePath);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private List<InputStream> getList(String sourceDir) throws IOException {
//		List<ByteArrayInputStream> list=new ArrayList<ByteArrayInputStream>();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
//                File sourceFile = file[i];
            	setList(file[i]);
            }else if (file[i].isDirectory()) {
            	// 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                getList(dir1);
            }
        }
        return list;
    }
	
	private void setList(File file){
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			fis.skip(1);
//	        ByteArrayInputStream bis=new ByteArrayInputStream(fstr.getBytes());
	        list.add(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
