package com.dom.player.utils;

import com.dom.player.bean.AmusementNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendom on 16-11-9.
 */

public class JsonUtils {

    public static List<AmusementNews> getJson(String result){
        try {
            List<AmusementNews> list = new ArrayList<AmusementNews>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray array = jsonObject.getJSONArray("newslist");
            for (int i=0;i<array.length();i++){
                AmusementNews news = new AmusementNews();
                JSONObject object = array.getJSONObject(i);
                String ctime = object.getString("ctime");
                String title = object.getString("title");
                String description= object.getString("description");
                String picUrl = object.getString("picUrl");
                String url = object.getString("url");
                news.setCtime(ctime);
                news.setTitle(title);
                news.setDescription(description);
                news.setPicUrl(picUrl);
                news.setUrl(url);
                list.add(news);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
