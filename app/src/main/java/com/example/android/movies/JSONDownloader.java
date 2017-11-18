package com.example.android.movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Guto on 15/10/2017.
 */


public class JSONDownloader extends AsyncTaskLoader<String> {
    Context context;
    private String jsonURL;
    RecyclerView recyclerView;
    String imageURL = "http://image.tmdb.org/t/p/w342";
    Boolean parse;
    ArrayList<Movie> movies = new ArrayList<>();


    public JSONDownloader(Context context, String jsonURL, RecyclerView recyclerView) {
        super(context);
        this.context = context;
        this.jsonURL = jsonURL;
        this.recyclerView = recyclerView;
    }

    @Override
    public String loadInBackground() {
        return download();
    }

    private String download() {
        Object connection = Connector.connect(jsonURL);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }
        try {
            HttpURLConnection con = (HttpURLConnection) connection;
            if (con.getResponseCode() == con.HTTP_OK) {
                //GET INPUT FROM STREAM
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer jsonData = new StringBuffer();
                //READ
                while ((line = br.readLine()) != null) {
                    jsonData.append(line + "\n");
                }
                //CLOSE RESOURCES
                br.close();
                is.close();
                //RETURN JSON
                return jsonData.toString();
            } else {
                return "Error " + con.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }

    boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


//public class JSONDownloader extends AsyncTask<Void, Void, String> {
//    Context context;
//    private String jsonURL;
//    RecyclerView recyclerView;
//    String imageURL="http://image.tmdb.org/t/p/w342";
//    Boolean parse;
//    ArrayList<Movie> movies =new ArrayList<>();
//
//    public JSONDownloader(Context context, String jsonURL, RecyclerView recyclerView) {
//        this.context = context;
//        this.jsonURL = jsonURL;
//        this.recyclerView = recyclerView;
//    }
//
//    public JSONDownloader() {}
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        if (isNetworkAvailable(context)) {
//            Log.v("Ocorreu conexão com a ", "internet");
//        }
//    }
//
//    @Override
//    protected String doInBackground(Void... params) {
//        Log.v("URL DO MAIN: ",jsonURL);
//
//        return download();
//    }
//
//    @Override
//    protected void onPostExecute(String jsonData) {
//        super.onPostExecute(jsonData);
//        if (jsonData.startsWith("Error")) {
//            String error = jsonData;
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//        } else {
//
//            Log.v("URL DOWNLOAD: ",jsonData);
//
////            new JSONParser(context, jsonData, recyclerView);
//
//            Log.v("URL IMAGEM: ",imageURL);
//
//            parse = new JSON(jsonData,imageURL,movies).parse();
//
//            Log.v("PARSE: ", String.valueOf(parse));
//
//            if(parse){
//                recyclerView.setAdapter(new FilmeAdapter(context, movies));
//            }else {
//                Toast.makeText(context, "Unable To Parse,Check Your Log output", Toast.LENGTH_SHORT).show();
//            }
//            //PARSER
////            new JSONParser(context, jsonData, recyclerView).execute();
//        }
//    }
//
//    //DOWNLOAD
//    private String download() {
//        Object connection= Connector.connect(jsonURL);
//        if(connection.toString().startsWith("Error"))
//        {
//            return connection.toString();
//        }
//        try
//        {
//            HttpURLConnection con= (HttpURLConnection) connection;
//            if(con.getResponseCode()==con.HTTP_OK)
//            {
//                //GET INPUT FROM STREAM
//                InputStream is=new BufferedInputStream(con.getInputStream());
//                BufferedReader br=new BufferedReader(new InputStreamReader(is));
//                String line;
//                StringBuffer jsonData=new StringBuffer();
//                //READ
//                while ((line=br.readLine()) != null)
//                {
//                    jsonData.append(line+"\n");
//                }
//                //CLOSE RESOURCES
//                br.close();
//                is.close();
//                //RETURN JSON
//                return jsonData.toString();
//            }else
//            {
//                return "Error "+con.getResponseMessage();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error "+e.getMessage();
//        }
//}
//
//    boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//}

