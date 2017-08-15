/**
 * 
 */
package com.UniSuper.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author gagan_000
 *
 */
public class BaseClass {

	
	/**
	 * @param orignalFilePath
	 * @param destFilePath
	 */
	public static void moveFiles(String orignalFilePath, String destFilePath){
		InputStream inStream = null;
		OutputStream outStream = null;

	    	try{

	    	    File afile =new File(orignalFilePath);
	    	    File bfile =new File(destFilePath);

	    	    inStream = new FileInputStream(afile);
	    	    outStream = new FileOutputStream(bfile);

	    	    byte[] buffer = new byte[1024];

	    	    int length;
	    	    //copy the file content in bytes
	    	    while ((length = inStream.read(buffer)) > 0){

	    	    	outStream.write(buffer, 0, length);

	    	    }

	    	    inStream.close();
	    	    outStream.close();
	    	    System.out.println("File is copied successful! "+orignalFilePath);

	    	}catch(IOException e){
	    		System.out.println("File not there - "+orignalFilePath);
	    	}
		
	}
}
