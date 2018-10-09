package com.example.admin.jsonimagtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PopulationAdapter extends BaseAdapter {
    Context context;
    List<PopulationGS> populationGSList;
    ImageView imageView;


    //ImageView flag;


    public PopulationAdapter(Context context, List<PopulationGS> populationGSList) {
        this.context = context;
        this.populationGSList = populationGSList;
        //this.flag=flag;


    }

    @Override
    public int getCount() {
        return populationGSList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View cview = LayoutInflater.from(context).inflate(R.layout.texts, viewGroup, false);
        TextView name = cview.findViewById(R.id.rank);
        name.setText(populationGSList.get(i).getRank());

        TextView name1 = cview.findViewById(R.id.population);
        name1.setText(populationGSList.get(i).getPopulation());

        TextView name2 = cview.findViewById(R.id.country);
        name2.setText(populationGSList.get(i).getCountry());
ImageView imageView=cview.findViewById(R.id.flag);
        String url = populationGSList.get(i).getFlag();
        Log.i("***********",url);
        new DownloadPage(imageView).execute(url);
       // Picasso.get().load(url).into(imageView);

        view = cview;
        return view;

    }

    private class DownloadPage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadPage(ImageView imageView) {
            this.imageView = imageView;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
           String image_url = strings[0];
           StringBuilder sb = new StringBuilder(image_url);
            sb.insert(4, 's');
            String furl=sb.toString();
            try {


                InputStream input = new URL(furl).openStream();

                bitmap = BitmapFactory.decodeStream(input);
                input.close();
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
//Log.i("***********",String.valueOf(bitmap.getByteCount()));
            imageView.setImageBitmap(bitmap);
        }
    }
}


