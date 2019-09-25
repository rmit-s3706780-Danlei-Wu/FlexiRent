package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
/**
 * Tool class for reading and writing files
 * @author Administrator
 *
 */
public class FileWriteAndRead {
    
        
    public static void main(String[] args){
		
	}
	
	public static String readFile(File file)
	{
		FileInputStream inputStream = null;
		InputStreamReader Streamreader=null;
		BufferedReader reader=null;
		StringBuffer buffer=null;
	try {
		 inputStream = new FileInputStream(file);
		 Streamreader=new InputStreamReader(inputStream);
		 reader=new BufferedReader(Streamreader);
		 buffer=new StringBuffer();
		 String string="";
		while((string=reader.readLine())!=null)
		{
			buffer.append(string).append("\n");
		}
		
	 } 
	 catch (IOException e) {
                    // TODO Auto-generated catch block

	}
	finally{
		try {
			reader.close();
			inputStream.close();
		    Streamreader.close();
		} catch (IOException e) {
                    // TODO Auto-generated catch block

		}
	}
		return buffer.toString();
	}
	
	public static String readFileByPath(String filePath)
	{
		File file=new File(filePath);
		FileInputStream inputStream = null;
		InputStreamReader Streamreader=null;
		BufferedReader reader=null;
		StringBuffer buffer=null;
	try {
		 inputStream = new FileInputStream(file);
		 Streamreader=new InputStreamReader(inputStream);
		 reader=new BufferedReader(Streamreader);
		 buffer=new StringBuffer();
		 String string="";
		while((string=reader.readLine())!=null)
		{
			buffer.append(string+"\n");
		}
		
	 } 
	 catch (IOException e) {
                    // TODO Auto-generated catch block

	}
	finally{
		try {
			reader.close();
			inputStream.close();
		    Streamreader.close();
		} catch (IOException e) {
                    // TODO Auto-generated catch block

		}
	}
		return buffer.toString();
	}

	public static boolean writerFileByLine(String content,String fileName)
	{
		File file=new File(fileName);
		PrintWriter writer=null;
		boolean isSuccess=false;
	 try {
		writer=new PrintWriter(file);
		writer.print(content);
		writer.flush();
		isSuccess=true;
		} catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block

		}finally{
			if(writer!=null)
			{
				try{
					writer.close();
				}catch(Exception e){
				}
			}
		}
		
		return isSuccess;
	}
	public static boolean writerFileByfile(String content,File file)
	{
		PrintWriter writer=null;
		boolean isSuccess=false;
	 try {
		writer=new PrintWriter(file);
		writer.print(content);
		writer.flush();
		isSuccess=true;
		} catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block

		}finally{
			if(writer!=null)
			{
				try{
					writer.close();
				}catch(Exception e){
				}
			}
		}
		
		return isSuccess;
	}
    public static void copyFile(String oldPath, String newPath) {  
        try {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPath);  
            if (oldfile.exists()) { 
                InputStream inStream = new FileInputStream(oldPath); 
                FileOutputStream fs = new FileOutputStream(newPath);  
                byte[] buffer = new byte[1444];  
                while ( (byteread = inStream.read(buffer)) != -1) {  
                    bytesum += byteread; 
                    System.out.println(bytesum);  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
                fs.close();  
            }  
        }  
        catch (Exception e) {  
            System.out.println("Copy the error");  
            e.printStackTrace();  
        }  
    } 
    public static void delFolder(String folderPath) {
        try {
           delAllFile(folderPath); 
           String filePath = folderPath;
           filePath = filePath.toString();
           java.io.File myFilePath = new java.io.File(filePath);
           myFilePath.delete(); 
        } catch (Exception e) {
          e.printStackTrace(); 
        }
        
   }
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
          return flag;
        }
        if (!file.isDirectory()) {
          return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
           if (path.endsWith(File.separator)) {
              temp = new File(path + tempList[i]);
           } else {
               temp = new File(path + File.separator + tempList[i]);
           }
           if (temp.isFile()) {
              temp.delete();
           }
           if (temp.isDirectory()) {
              delAllFile(path + "/" + tempList[i]);
              delFolder(path + "/" + tempList[i]);
              flag = true;
           }
        }
        return flag;
      }
}
