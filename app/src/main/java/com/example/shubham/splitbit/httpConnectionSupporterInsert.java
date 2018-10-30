package com.example.shubham.splitbit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class httpConnectionSupporterInsert{
    String info_url;
    private static final String TAG = "mymsgs";
    public httpConnectionSupporterInsert(String info_url){
        this.info_url=info_url;
    }
    String answerData(String[] arr_name,String[] arr_val){
        String query_string="";
        for(int i=0;i<arr_name.length;i++){
            try{
                query_string+= URLEncoder.encode(arr_name[i],"UTF-8")+"="+URLEncoder.encode(arr_val[i],"UTF-8");
                if(i!=arr_name.length-1){
                    query_string=query_string+"&";}
            }
            catch(UnsupportedEncodingException e){
                Log.d(TAG,"Encoding Failed");
            }
        }
        //Log.d(TAG,query_string);
        try{
            URL url=new URL(info_url);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(query_string);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
            String data="";
            String line;
            while((line=br.readLine())!=null){
                data+=line;
            }
            data=data.trim();
            //Log.d(TAG,String.valueOf(data.equals("Verified")));
            inputStream.close();
            httpURLConnection.disconnect();
            return data;
        }
        catch(MalformedURLException e){

        }
        catch(Exception e){
            Log.d(TAG,e.toString());
        }
        return "Insertion Failed";
    }
}
