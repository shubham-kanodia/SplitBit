package com.example.shubham.splitbit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
public class splitpage extends Activity implements OnClickListener {
    private static final String Name="nameKey";
    private Context context;
    private SharedPreferences sp;
    private Editor edt;
    private String user_name;
    private String split_usr;
    private String splt_amt;
    private int amt;
    EditText total_amt;
    EditText split_user;
    EditText split_amt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        sp=getApplicationContext().getSharedPreferences("credentials",0);
        edt=sp.edit();
        setContentView(R.layout.splitpage);
        EditText total_amt = findViewById(R.id.total_amt);
        EditText split_user = findViewById(R.id.usr_add);
        EditText split_amt = findViewById(R.id.split_amt);
        Button btn_add = findViewById(R.id.add_user_btn);
        Button btn_close = findViewById(R.id.transaction_btn);
        btn_add.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.add_user_btn:
                user_name = sp.getString(Name,"");
                split_usr = split_user.getText().toString();
                splt_amt = split_amt.getText().toString();
                amt=amt+Integer.parseInt(splt_amt);
                BackgroundTask bt=new BackgroundTask();
                bt.execute(new String[]{user_name,split_usr,splt_amt});
                break;
            case R.id.transaction_btn:
                int amt_to_add = Integer.parseInt(total_amt.getText().toString())-amt;
                String amt_str = amt_to_add+"";
                BackgroundTask bt2=new BackgroundTask();
                bt2.execute(new String[]{user_name,user_name,amt_str});
                break;
        }
    }
    class BackgroundTask extends AsyncTask<String,Void,String> {
        private String info_url;

        @Override
        protected String doInBackground(String... strings) {
            String s1=strings[0];
            String s2=strings[1];
            String s3=strings[2];
            String[] arr_name={"from_usr","to_usr","amt","status"};
            String[] arr_val={s1,s2,s3,"pending"};
            httpConnectionSupporterInsert hcsi=new httpConnectionSupporterInsert(info_url);
            return hcsi.answerData(arr_name,arr_val);

        }
        @Override
        protected void onPreExecute() {
            info_url="https://shubhamkanodia123.000webhostapp.com/add_transaction.php";
        }
        @Override
        protected void onPostExecute(String s) {
            if(s.equals("Verified")){
                //Log.d(TAG,"RH");

            }
            else{
                android.widget.Toast.makeText(getApplicationContext(),s,android.widget.Toast.LENGTH_LONG).show();
            }

        }
    }
}

