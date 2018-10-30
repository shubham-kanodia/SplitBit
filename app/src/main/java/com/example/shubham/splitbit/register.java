package com.example.shubham.splitbit;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class register extends Activity implements View.OnClickListener {
    private EditText usr_name;
    private EditText pass;
    private String usr_name_str;
    private String pass_str;
    private static final String TAG="mymsgs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        usr_name=findViewById(R.id.reg_user_name);
        pass=findViewById(R.id.reg_user_pass);
        View submit_btn=findViewById(R.id.reg_submit_btn);
        submit_btn.setOnClickListener(this);

    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.reg_submit_btn:
                usr_name_str=usr_name.getText().toString();
                pass_str=pass.getText().toString();
                BackgroundTask bt=new BackgroundTask();
                bt.execute(usr_name_str,pass_str);
        }
    }
    public void changeIntent(){
        Intent i=new Intent(this,firstpage.class);
        startActivity(i);
    }
    class httpConnectionSupporter{
        String info_url;

        public httpConnectionSupporter(String info_url){
            this.info_url=info_url;
        }
        String answerData(String[] arr_name,String[] arr_val){
            String query_string="";
            for(int i=0;i<arr_name.length;i++){
                try{
                    query_string+=URLEncoder.encode(arr_name[i],"UTF-8")+"="+URLEncoder.encode(arr_val[i],"UTF-8");
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
                inputStream.close();
                httpURLConnection.disconnect();
                return "Data inserted";
            }
            catch(MalformedURLException e){

            }
            catch(Exception e){

            }
            return "Insertion Failed";
        }
    }
    class BackgroundTask extends AsyncTask<String,Void,String>{
        private String info_url;
        private Context context;
        private SharedPreferences sp;
        private SharedPreferences.Editor edt;
        private static final String Name="nameKey";
        private static final String pass="passKey";
        private String s1;
        private String s2;
        @Override
        protected String doInBackground(String... args) {
            s1=args[0];
            s2=args[1];
            httpConnectionSupporter hcs=new httpConnectionSupporter(info_url);
            String[] arr_name={"name","password"};
            String[] arr_val={s1,s2};
            String ret=hcs.answerData(arr_name,arr_val);
            return ret;
        }

        @Override
        protected void onPreExecute() {
            info_url="https://shubhamkanodia123.000webhostapp.com/contri.php";
        }

        @Override
        protected void onPostExecute(String s) {
            if(s=="Data inserted"){
                context = getApplicationContext();
                sp=getApplicationContext().getSharedPreferences("credentials",0);
                edt=sp.edit();
                edt.putString(Name,s1);
                edt.putString(pass,s2);
                edt.commit();
                changeIntent();
            }
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }
}
