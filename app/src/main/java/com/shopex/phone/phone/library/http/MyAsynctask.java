package com.shopex.phone.phone.library.http;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
/**
 * 
 * @author samsung
 *异步网络请求
 */
public class MyAsynctask extends AsyncTask<String, Integer, String>{
	private Map<String, String> params;
	private BackResult br;
	protected static final String GET="GET";
	protected static final String POST="POST";
	protected static final String SEND="SEND";
	private String json;
	private boolean flag=true;
	private Context context;
	

	public MyAsynctask(Context context,Map<String, String> params,BackResult br){
		this.params=params;
		this.br=br;
		this.context=context;
	}
	
	public MyAsynctask(Map<String, String> params,BackResult br){
		this.params=params;
		this.br=br;
	}
	/**
	 *
	 */
	 @Override  
     protected void onPreExecute() {  
     }  
	 /**
	  *
	  */
     @Override  
     protected String doInBackground(String... path) {  
    	 if(params.get("isupfile")==null){
    		
    	 if(params.get("type").equals(GET)){
    	
 			try {
 				
 				StringBuilder sb = new StringBuilder();  
 		        sb.append(params.get("path")).append("?");  
 		  	
 		        if (params != null && params.size() != 0) {  
 		        	Set<String> keys=params.keySet();
 		        	Iterator<String> iter=keys.iterator();
 		        	while(iter.hasNext()){
 		        		String key=iter.next();
 		        		if (!key.equals("path")&&!key.equals("type")) {
 		        			String value=params.get(key);
 		        			sb.append(key).append("=").append(URLEncoder.encode(value, "utf-8"));
 		        			sb.append("&");
						}
 		       	}
 		            sb.deleteCharAt(sb.length() - 1);
 		            System.out.println(sb.toString()+"--------");
 		        }  
 				URL url = new URL(sb.toString());
 				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 				conn.setRequestMethod("GET");
 				conn.setConnectTimeout(3000);
 				int code = conn.getResponseCode();
 				 System.out.println("------------------"+code);
 				if (200 == code) {
 					InputStream is = conn.getInputStream();
 					json=StreamTools.readStream(is);
 					flag=true;
 					} else {
 						flag=false;
 						json="code不等于200";
 							}
 						} catch (Exception e) {
 							flag=false;
 							json=e.getMessage();
 							e.printStackTrace();
 						}
 					}
 	    if (params.get("type").equals(POST)) {
         StringBuilder sb = new StringBuilder(); 
         try {
         if(params!=null &params.size()!=0){  
        	 
        		Set<String> keys=params.keySet();
		        	Iterator<String> iter=keys.iterator();
		        	while(iter.hasNext()){
		        		String key=iter.next();
		        		if (!key.equals("path")&&!key.equals("type")) {
		        			String value=params.get(key);
		        			sb.append(key).append("=").append(URLEncoder.encode(value, "utf-8"));
		        			sb.append("&");
					}
		       	}
             sb.deleteCharAt(sb.length()-1);  
         }  
         byte[] entity = sb.toString().getBytes();  
         
          URL url = new URL(params.get("path"));
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
          conn.setConnectTimeout(2000);  
          conn.setRequestMethod("POST");  
          conn.setDoOutput(true);  
          conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
          conn.setRequestProperty("Content-Length", entity.length+"");  
          OutputStream out = conn.getOutputStream();  
          out.write(entity);  
          out.flush();    
          out.close();  
          System.out.println(conn.getResponseCode());
          if (conn.getResponseCode() == 200) {
        	 flag=true;
         	 InputStream is=conn.getInputStream();
         	 json=StreamTools.readStream(is);
         	 
          }else {
        	InputStream is=conn.getInputStream();
          	json=StreamTools.readStream(is);
        	flag=false;
          }  
         } catch (Exception e) {
        	 flag=false;
        	 json=e.getMessage().toString();
 			e.printStackTrace();
 		}  
      }  
  }else {
	  	 String BOUNDARY = UUID.randomUUID().toString(); 
	     String PREFIX = "--" ;
	     String LINE_END = "\r\n"; 
         StringBuilder sb = new StringBuilder();  
     if(params!=null &params.size()!=0){  
    	Set<String> keys=params.keySet();
     	Iterator<String> iter=keys.iterator();
     	while(iter.hasNext()){
     		String key=iter.next();
     		if (!key.equals("path")&&!key.equals("type")&&!key.equals("isupfile")) {
     			String value=params.get(key);
     			sb.append("--" + BOUNDARY + "\r\n");  
                sb.append("Content-Disposition: form-data; name=\"" + key+"\"\r\n");  
                sb.append("\r\n");  
                sb.append(URLEncoder.encode(value) + "\r\n");
			}
    	 
         }  
     }
     byte[] entity = sb.toString().getBytes();  
     try {
           URL url=new URL(params.get("path"));
           File file =new File(params.get("isupfile"));

           HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
          conn.setDoOutput(true);  
          conn.setUseCaches(false);  
          conn.setConnectTimeout(2000); 
          conn.setRequestMethod("POST");  
          conn.setRequestProperty("connection", "keep-alive");    
          conn.setRequestProperty("Content-Type", "multipart/form-data"+ ";boundary=" + BOUNDARY);   
      
          OutputStream outputSteam=conn.getOutputStream();    
      	  DataOutputStream dos= new DataOutputStream(outputSteam);   
          dos.write(entity); 
          StringBuffer buffer = new StringBuffer();    
          buffer.append(PREFIX);    
          buffer.append(BOUNDARY); 
          buffer.append(LINE_END);    
          buffer.append("Content-Disposition: form-data; name=\"imgFile\"; filename=\"" + URLEncoder.encode(file.getName()) + LINE_END);   
          buffer.append("Content-Type: application/octet-stream; charset="+"UTF-8"+LINE_END);
          buffer.append(LINE_END);
          dos.write(buffer.toString().getBytes());   
         
          InputStream is = context.getAssets().open(file.getName());   
          byte[] bytes = new byte[1024];    
          int len = 0;
          while((len=is.read(bytes))!=-1)    
          {    
             dos.write(bytes, 0, len);    
          }    
          is.close();    
          ///r/n
          dos.write(LINE_END.getBytes());    
        //  -------------------------------7db372eb000e2--/r/n
          byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();    
          dos.write(end_data);    
          dos.flush();  
          dos.close();
      if (conn.getResponseCode() == 200) {  
     	InputStream is1 = conn.getInputStream();
		json=StreamTools.readStream(is1);
     	/*StringBuilder builder=new StringBuilder();
 		BufferedReader reader=new BufferedReader(new InputStreamReader(is1));
 		String line=null;
 		while (((line=reader.readLine()))!=null) {
 			builder.append(line);
         }  
 		reader.close();
 		json=reader.toString();*/
 		flag=true;
 	
      }
     } catch (Exception e) {
    	 flag=false;
    	 json="有异常";
		e.printStackTrace();
		}  
     }
   
  
         return json;  
}  
    
     /**
      * @param progresses
      */
     @Override  
     protected void onProgressUpdate(Integer... progresses) {  
     }  
   /**
    * @param result
    */
     @Override  
     protected void onPostExecute(String result) {  
    	 br.BackResult(flag,json);
     }  
 /**
  */
     @Override  
     protected void onCancelled() {  
     }  
     
 }  
