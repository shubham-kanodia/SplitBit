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
public class accept_page extends Activity implements OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_page);
        //View payment_btn=findViewById(R.id.payment);
        //payment_btn.setOnClickListener(this);
    }
    public void onClick(View v){
        switch(v.getId()){
            //case R.id.payment:
                //
        }
    }
}
