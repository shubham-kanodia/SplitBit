package com.example.shubham.splitbit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.content.SharedPreferences.Editor;

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
public class MainActivity extends Activity implements OnClickListener {
    private static String user_name;
    private static String pass_word;
    private EditText et_usrname;
    private EditText et_password;
    private Context context;
    private SharedPreferences sp;
    private Editor edt;
    private static final String TAG = "mymsgs";
    private static final String Name="nameKey";
    private static final String pass="passKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        sp=getApplicationContext().getSharedPreferences("credentials",0);
        edt=sp.edit();
        setContentView(R.layout.activity_main);
        View submit_btn=findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
        View reg_button=findViewById(R.id.register);
        reg_button.setOnClickListener(this);
        if(sp.contains(Name)){
            Intent i=new Intent(this,firstpage.class);
            startActivity(i);
        }
        et_usrname=findViewById(R.id.user_name);
        et_password=findViewById(R.id.user_pass);
    }
    public void changeIntent(){
        Intent i=new Intent(this,firstpage.class);
        startActivity(i);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.submit_btn:
                user_name=et_usrname.getText().toString();
                pass_word=et_password.getText().toString();
                BackgroundTask bt=new BackgroundTask();
                bt.execute(user_name,pass_word);
                break;
            case R.id.register:
                Intent i =new Intent(this, register.class);
                startActivity(i);
        }
    }

    class BackgroundTask extends AsyncTask<String,Void,String>{
        private String info_url;

        @Override
        protected String doInBackground(String... strings) {
            String s1=strings[0];
            String s2=strings[1];
            String[] arr_name={"name","password"};
            String[] arr_val={s1,s2};
            httpConnectionSupporterInsert hcsi=new httpConnectionSupporterInsert(info_url);
            return hcsi.answerData(arr_name,arr_val);

        }
        @Override
        protected void onPreExecute() {
            info_url="https://shubhamkanodia123.000webhostapp.com/verify.php";
        }
        @Override
        protected void onPostExecute(String s) {
            if(s.equals("Verified")){
                //Log.d(TAG,"RH");
                edt.putString(Name,user_name);
                edt.putString(pass,pass_word);
                edt.commit();
                changeIntent();

            }
            else{
                android.widget.Toast.makeText(getApplicationContext(),s,android.widget.Toast.LENGTH_LONG).show();
            }

        }
    }
}

