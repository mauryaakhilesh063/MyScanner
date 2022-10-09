package com.example.myscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    TextView textView, langi, latti, ws;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        fetchApi();
    }
    private void init(){
        langi= findViewById(R.id.longitu);
        latti= findViewById(R.id.lattitu);
        ws= findViewById(R.id.wspeed);
    }
    private void fetchApi(){
        final TextView textView = (TextView) findViewById(R.id.text);
        RequestQueue apiRequest = Volley.newRequestQueue(this);
        String url ="https://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=dce4f9f9afe08008088b9ee229a10bb4";
        // Request a string response from the provided URL.
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
//success
               Log.e("Response", response.toString());
               Gson gson = new Gson();
             WeatherDetails details=  gson.fromJson(response.toString(),WeatherDetails.class);
             String str = details.getWind().getSpeed();
             Log.e("Wind Speed",str);
             ws.setText("Wind Speed is :"+ details.getWind().getSpeed());
           }
       },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                   }
               }
       );
        apiRequest.add(jsonObjectRequest);
            }

}