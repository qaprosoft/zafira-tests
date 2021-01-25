package com.qaprosoft.zafira.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    public static List<String> readList(String str)  {
        Gson googleJson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> jsonObjList = googleJson.fromJson(str, listType);
        return jsonObjList;
    }
    }
