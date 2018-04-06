package app.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//Volley
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by guillaume on 06/04/18.
 */

public class HttpRequest {

    private Context context;

    public HttpRequest(Context ctx)
    {
        context = ctx;
    }

    public void Get(final String url, Response.Listener<String> listener)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR ON GET REQUEST URL = " + url);
            }
        });

        queue.add(stringRequest);
    }

    public void GetImage(final String url, Response.Listener<Bitmap> listener)
    {
        Volley.newRequestQueue(context).add(new ImageRequest(url, listener, 1024, 1024, null, null));
    }

    public void GetPhotos(String url, final ListView listView, final int limit)
    {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
                ArrayList<String> titles = parsePhotoTitle(response, limit);

                AdapterList adapterList = new AdapterList(context, photos, titles);

                parsePhotoBitmap(response, photos, adapterList, listView, limit);

                listView.setAdapter(adapterList);
            }
        };

        Get(url, listener);
    }

    public void parsePhotoBitmap(String str, final ArrayList<Bitmap> list,
                                 final AdapterList adapterList, final ListView listView, int limit)
    {
        int pos = str.indexOf("id\": " + limit);
        String s = str.substring(0, pos);
        int idx = s.lastIndexOf("},");
        String s1 = s.substring(0, idx) + "}\n]";

        try {
            JSONArray jsonArray = new JSONArray(s1);
            JSONObject jsonobject = null;

            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonobject = jsonArray.getJSONObject(i);
                String imageUrl = jsonobject.getString("thumbnailUrl");
                Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        list.add(response);
                        listView.setAdapter(adapterList);
                    }};

                GetImage(imageUrl, listener);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> parsePhotoTitle(String str, int limit)
    {
        ArrayList<String> list = new ArrayList<String>();
        String text = new String();

        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < limit; i++)
            {
                text = "";
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                String title = jsonobject.getString("title");
                String id = jsonobject.getString("id");
                String album = jsonobject.getString("albumId");
                text = title + "\n" + "id : " + id + " from album : " + album;

                list.add(text);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void GetArticles(String url, final ListView listView)
    {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> list = parseArticles(response);
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.article, R.id.article, list);

                listView.setAdapter(arrayAdapter);
            }
        };

        Get(url, listener);
    }

    public ArrayList<String> parseArticles(String str)
    {
        ArrayList<String> list = new ArrayList<String>();
        String text = new String();

        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                text = "";
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                String userId = jsonobject.getString("userId");
                String id = jsonobject.getString("id");
                String title = jsonobject.getString("title");
                String body = jsonobject.getString("body");

                text = "Article n: " + id + " made by :" + userId + "\n\n";
                text += title + "\n" + body;

                list.add(text);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
