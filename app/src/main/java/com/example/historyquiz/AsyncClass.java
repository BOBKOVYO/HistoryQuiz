package com.example.historyquiz;

import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class AsyncClass extends AsyncTask<String, Void, JSONArray> {

    public String server_name = "https://historyquizapp.000webhostapp.com";
    public HttpURLConnection conn;

    private JSONArray addCategoryScore(String bestscore, String difficulty, String category_id){
        try {
            String post_url = server_name + "/quiz_bestscores.php?action=insert&bestscore=" + bestscore + "&difficulty=" + difficulty + "&category_id="+ category_id;
            URL url = new URL(post_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            conn.getResponseCode();
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            conn.disconnect();
        }
    }

    private JSONArray updateCategoryScore(String bestscore, String difficulty, String category_id){
        try {
            String post_url = server_name + "/quiz_bestscores.php?action=update&bestscore=" + bestscore + "&difficulty=" + difficulty + "&category_id="+ category_id;
            URL url = new URL(post_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            conn.getResponseCode();
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            conn.disconnect();
        }
    }

    private JSONArray getAllCategoryScores(){
        String answer, lnk;
        try {
            lnk = server_name + "/quiz_bestscores.php?action=select";
            conn = (HttpURLConnection) new URL(lnk)
                    .openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            answer = sb.toString();
            br.close();
            JSONArray ja = new JSONArray();
            while (answer.indexOf("{") != -1) {
                int l = answer.indexOf("{"), r = answer.indexOf("}");
                String str = answer.substring(l, r + 1);
                JSONObject jObject = new JSONObject(str);
                ja.put(jObject);
                answer = answer.substring(r + 1);

            }
            return ja;
        }catch (Exception e){
            return null;
        }
    }

    private JSONArray getAllQuestions(){
        String answer, lnk;
        try {
            lnk = server_name + "/quiz_questions.php?action=select";
            conn = (HttpURLConnection) new URL(lnk)
                    .openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            answer = sb.toString();
            br.close();
            JSONArray ja = new JSONArray();
            while (answer.indexOf("{") != -1) {
                int l = answer.indexOf("{"), r = answer.indexOf("}");
                String str = answer.substring(l, r + 1);
                JSONObject jObject = new JSONObject(str);
                ja.put(jObject);
                answer = answer.substring(r + 1);

            }
            return ja;
        }catch (Exception e){
            return null;
        }
    }

    private JSONArray getQuestions(String categoryID, String difficulty){
        String answer, lnk;
        try {
            lnk = server_name + "/quiz_questions.php?action=select&category_id=" + categoryID + "&difficulty" + difficulty;
            conn = (HttpURLConnection) new URL(lnk)
                    .openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            answer = sb.toString();
            br.close();
            JSONArray ja = new JSONArray();
            while (answer.indexOf("{") != -1) {
                int l = answer.indexOf("{"), r = answer.indexOf("}");
                String str = answer.substring(l, r + 1);
                JSONObject jObject = new JSONObject(str);
                if(jObject.getInt("category_id") == Integer.parseInt(categoryID) && jObject.getString("difficulty") == difficulty)
                    ja.put(jObject);
                answer = answer.substring(r + 1);

            }
            return ja;
        }catch (Exception e){
            return null;
        }
    }

    private JSONArray getAllCategories(){
        String answer, lnk;
        try {
            lnk = server_name + "/quiz_categories.php?action=select";
            conn = (HttpURLConnection) new URL(lnk)
                    .openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            answer = sb.toString();
            br.close();
            JSONArray ja = new JSONArray();
            while (answer.indexOf("{") != -1) {
                int l = answer.indexOf("{"), r = answer.indexOf("}");
                String str = answer.substring(l, r + 1);
                JSONObject jObject = new JSONObject(str);
                ja.put(jObject);
                answer = answer.substring(r + 1);

            }
            return ja;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        if (strings[0].equals("addCategoryScore"))
            return addCategoryScore(strings[1], strings[2], strings[3]);
        if (strings[0].equals("updateCategoryScore"))
            return updateCategoryScore(strings[1], strings[2], strings[3]);
        if (strings[0].equals("getAllCategoryScores"))
            return getAllCategoryScores();
        if (strings[0].equals("getAllQuestions"))
            return getAllQuestions();
        if (strings[0].equals("getAllCategories"))
            return getAllCategories();
        if (strings[0].equals("getQuestions"))
            return getQuestions(strings[1], strings[2]);
        return null;
    }

}