package com.example.admin.jsonimagtext;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

   public class JsomImageText extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    private ListView listView;
    private List<PopulationGS> populationGS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        String url="https://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
        new ResponseAsync().execute(url);
        }
              class ResponseAsync extends AsyncTask<String, Void, String> {

                  @Override
                  protected void onPreExecute() {
                      super.onPreExecute();
                      mProgressDialog = new ProgressDialog(JsomImageText.this);
                      mProgressDialog.setTitle("get response Tutorial");
                      mProgressDialog.setMessage("Loading...");
                      mProgressDialog.setIndeterminate(false);
                      mProgressDialog.show();
                  }

                  @Override
                  protected String doInBackground(String... url) {
                      String respURL = url[0];

                      try {
                          // Download Image from URL
                          URL ur = new URL(respURL);
                          InputStream is = ur.openConnection().getInputStream();


                          StringBuffer buffer = new StringBuffer();
                          BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                          String line;
                          while ((line = reader.readLine()) != null) {
                              buffer.append(line + "\n");
                          }


                          return buffer.toString();

                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                      return null;
                  }

                  @Override
                  protected void onPostExecute(String s) {
                      List<PopulationGS> populationGSList=new ArrayList<>();
                      try {
                          JSONObject root_object=new JSONObject(s);
                          JSONArray worldpopulation_array = root_object.getJSONArray("worldpopulation");

                          for (int i = 0; i < worldpopulation_array.length(); i++) {
                              JSONObject list_obj = worldpopulation_array.getJSONObject(i);
                              PopulationGS item = new PopulationGS();

                              item.setRank(list_obj.getString("rank"));
                              item.setPopulation(list_obj.getString("population"));
                              item.setCountry(list_obj.getString("country"));
                              item.setFlag(list_obj.getString("flag"));
                              // item.setFlag(list_obj.getString("flag"));
                             //item.setFlag((Bitmap) list_obj.get("flag"));

                              populationGSList.add(item);

                      }
                      }
                      catch (JSONException e) {
                          e.printStackTrace();
                      }
                      PopulationAdapter myAdapter=new PopulationAdapter(getApplicationContext(),populationGSList);
                      listView.setAdapter(myAdapter);
                      mProgressDialog.dismiss();
                  }
                  }
              }
