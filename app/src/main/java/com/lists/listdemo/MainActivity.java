package com.lists.listdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ListView listView;
//    MyAsyncTask notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);


        String item = "";
        MyAsyncTask notifications= new MyAsyncTask();
        notifications.execute();

//        if(notifications.getStatus() ==  AsyncTask.Status.FINISHED){
            try {
                item = notifications.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("HAKIKISHA ALERT");
        arrayList.add("HAKIKISHA ALERT");
        arrayList.add("REQUEST MONEY");
        arrayList.add("MONEY RECEIVED");
        arrayList.add(item);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Opened: " +i+ " " +arrayList.get(i).toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... params) {
            //GET request
            OkHttpClient client = new OkHttpClient();

            String url = "https://run.mocky.io/v3/6552e781-44cc-460d-af0b-d552b5171c12";

            String item = "";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                item =  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return item;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //handle results
        }
    }

}