package com.example.shubham.splitbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import android.view.View;
import android.view.View.OnClickListener;
public class firstpage extends Activity implements OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);
        View split_btn=findViewById(R.id.split_btn);
        View check_btn=findViewById(R.id.check_btn);
        View accpt_btn=findViewById(R.id.accept_requests);
        split_btn.setOnClickListener(this);
        check_btn.setOnClickListener(this);
        accpt_btn.setOnClickListener(this);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.split_btn:
                Intent i = new Intent(this,splitpage.class);
                startActivity(i);
        }
    }
}