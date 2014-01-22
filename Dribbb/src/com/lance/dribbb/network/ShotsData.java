package com.lance.dribbb.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lance.dribbb.adapter.ContentShotsAdapter;
import com.lance.dribbb.views.FooterState;

public class ShotsData {

  private RequestQueue mRequestQueue;
  private List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
  
  public ShotsData(Activity a) {
    mRequestQueue = Volley.newRequestQueue(a);
  }
  
  public List<Map<String, Object>> getList() {
    return list;
  }
  
  public void getShotsRefresh(String url, final GridView gridView, final ContentShotsAdapter adapter, final FooterState f) {
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, 
        new Response.Listener<JSONObject>() {

          @Override
          public void onResponse(JSONObject arg0) {
            try {
              initShotsList(arg0);
              adapter.notifyDataSetChanged();
              f.setState(FooterState.State.Idle);
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        }, new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError arg0) {
            
          }
        });
    mRequestQueue.add(jsonObjectRequest);
  }

  private void initShotsList(JSONObject jsonObject) throws JSONException {
    int respond_count = jsonObject.getInt("per_page");
    JSONArray array = jsonObject.getJSONArray("shots");
    for (int i = 0; i < respond_count; i++) {
      Map<String, Object> map = new HashMap<String, Object>();
      
      //shots
      map.put("id", array.getJSONObject(i).getString("id"));
      map.put("title", array.getJSONObject(i).getString("title"));
      map.put("image_url", array.getJSONObject(i).getString("image_url"));
      map.put("image_teaser_url", array.getJSONObject(i).getString("image_teaser_url"));
      map.put("views_count", array.getJSONObject(i).getString("views_count"));
      map.put("likes_count", array.getJSONObject(i).getString("likes_count"));
      
      //player
      map.put("player_name", array.getJSONObject(i).getJSONObject("player").getString("name").toString());
      map.put("player_avatar_url", array.getJSONObject(i).getJSONObject("player").getString("avatar_url").toString());
      list.add(map);
    }
  }
}
