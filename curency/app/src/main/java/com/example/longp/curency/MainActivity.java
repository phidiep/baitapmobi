package com.example.longp.curency;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public final String URL1 = "http://rate-exchange-1.appspot.com/currency?from=USD&to=VND";
    public final String URL2 = "http://rate-exchange-1.appspot.com/currency?from=EUR&to=VND";
    public final String URL3 = "http://rate-exchange-1.appspot.com/currency?from=JPY&to=VND";
    public final String URL4 = "http://rate-exchange-1.appspot.com/currency?from=GBP&to=VND";
    String res_1 ;
    String res_2 ;
    String res_3 ;
    String res_4 ;
    String text;
    int usd = 0;
    int eur = 0;
    int jpy =0;
    int gbp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient client = new OkHttpClient();
        getDataJson(client);

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    public void getDataJson(OkHttpClient client){
        Request request1 = new Request.Builder()
                .url(URL1)
                .build();
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("BK-201 URL",response.body().toString());
                res_1 = response.body().string();
                try {
                    JSONObject JObject  = new JSONObject(res_1);
                    usd = JObject.getInt("rate");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("json",e.toString());
                }
            }
        });
        Request request2 = new Request.Builder()
                .url(URL2)
                .build();
        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("BK-201 URL",response.body().toString());
                res_2 = response.body().string();
                try {
                    JSONObject JObject  = new JSONObject(res_2);
                    eur = JObject.getInt("rate");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("json",e.toString());
                }
            }
        });
        Request request3 = new Request.Builder()
                .url(URL3)
                .build();
        client.newCall(request3).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("BK-201 URL",response.body().toString());
                res_3 = response.body().string();
                try {
                    JSONObject JObject  = new JSONObject(res_3);
                    jpy = JObject.getInt("rate");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("json",e.toString());
                }
            }
        });
        Request request4 = new Request.Builder()
                .url(URL4)
                .build();
        client.newCall(request4).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("BK-201 URL",response.body().toString());
                res_4 = response.body().string();
                try {
                    JSONObject JObject  = new JSONObject(res_4);
                    gbp = JObject.getInt("rate");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("json",e.toString());
                }
            }
        });
    }
public  void showEx (View v){
    SharedPreferences pref = getSharedPreferences("currency",MODE_PRIVATE);
    if(isNetworkConnected()){
        OkHttpClient client = new OkHttpClient();
        getDataJson(client);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("usd",usd);
        edit.putInt("eur",eur);
        edit.putInt("jpy",jpy);
        edit.putInt("gbp",gbp);
        edit.commit();
    }
    else
    {
        usd = pref.getInt("usd",0);
        eur = pref.getInt("eur",0);
        jpy = pref.getInt("jpy",0);
        gbp = pref.getInt("gbp",0);
    }
    TextView usdt = (TextView) findViewById(R.id.textView8);
    TextView eurt = (TextView) findViewById(R.id.textView9);
    TextView jpyt = (TextView) findViewById(R.id.textView10);
    TextView gbpt = (TextView) findViewById(R.id.textView11);
    usdt.setText(Integer.toString(usd));
    eurt.setText(Integer.toString(eur));
    jpyt.setText(Integer.toString(jpy));
    gbpt.setText(Integer.toString(gbp));
}

    public void exchange (View v){

        final TextView result = (TextView) findViewById(R.id.resultText);
        int vnd = 0;
        Spinner mySpinner =(Spinner) findViewById(R.id.spinner);
        text = mySpinner.getSelectedItem().toString();
        final TextView result2 = (TextView) findViewById(R.id.textView3);
        try{
            EditText mnField = (EditText) findViewById(R.id.moneyField);
            int money = Integer.parseInt(mnField.getText().toString());

            switch (text) {
                case "USD":
                    vnd = money * usd;
                    break;
                case "EUR":
                    vnd = money * eur ;
                    break;
                case "JPY":
                    vnd = money *jpy;
                    break;
                case "GBP":
                    vnd = money *gbp;
                    break;
            }
            result.setText(Integer.toString(vnd));
        }
        catch(NumberFormatException e)
        {
            Context context =this;
            new AlertDialog.Builder(context)
                    .setTitle("Input is null")
                    .setMessage("Please input some number!!!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
