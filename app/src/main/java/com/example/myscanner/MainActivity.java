package com.example.myscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},101);
            }
        }
        button1 = findViewById(R.id.generateButton);
        button2 = findViewById(R.id.scanButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenerateActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCode();
            }
        });
        TextView userName = (TextView) findViewById(R.id.userName);
        TextView password = (TextView) findViewById(R.id.password);
        Button submitButton = (Button) findViewById(R.id.submit);
        //admin authentication
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals("admin") && password.getText().toString().equals("1111")){
                    //correct
                    Toast.makeText(MainActivity.this, "Login Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i1 = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(i1);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }else
                    // incorrect
                Toast.makeText(MainActivity.this,"Login Failed!!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result == null) {
            super.onActivityResult(requestCode,resultCode,data);
        }else
        {
            String r = result.getContents();
            if(r==null){
                //
            }else
            {
              showBox(r);
            }
        }
    }

    private void showBox(String r) {
        AlertDialog.Builder bl =new AlertDialog.Builder(this);
        bl.setTitle("Scanned Result");
        bl.setMessage(r);
        bl.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        bl.create().show();

    }

    private void generateCode() {
        Toast.makeText(this, "Generate Code", Toast.LENGTH_SHORT).show();
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }
    private void scanCode() { }
}