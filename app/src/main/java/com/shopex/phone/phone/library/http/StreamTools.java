package com.shopex.phone.phone.library.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {
	
	/**
	 *
	 * @param is
	 * @return
	 * 把流对象转成String
	 * 
	 */
	public static String readStream(InputStream is){
		
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			
			while((len = is.read(buffer)) != -1){
				baos.write(buffer, 0, len);
			}
			is.close();
			
			String result = new String(baos.toByteArray(),"UTF-8");
			baos.close();
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		

	}

}
