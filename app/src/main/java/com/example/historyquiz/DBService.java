package com.example.historyquiz;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DBService {
    String server_name = "https://historyquizapp.000webhostapp.com";
    HttpURLConnection conn;
    Cursor cursor;

    public DBService() {
    }


    //-----------------------------


    public void insertCategoryScore(CategoryScore categoryScore) {
        try {
            AsyncClass asyncClass = new AsyncClass();
            JSONArray ja = asyncClass.execute("addCategoryScore", String.valueOf(categoryScore.getBestscore()),categoryScore.getDifficulty(),String.valueOf(categoryScore.getCategoryID())).get();
        } catch (Exception ex) {
        };
    }

    public void updateCategoryScore(CategoryScore categoryScore){
        try {
            AsyncClass asyncClass = new AsyncClass();
            JSONArray ja = asyncClass.execute("updateCategoryScore", String.valueOf(categoryScore.getBestscore()),categoryScore.getDifficulty(),String.valueOf(categoryScore.getCategoryID())).get();
        } catch (Exception ex) {
        };
    }

    public ArrayList<CategoryScore> getAllCategoryScores() throws JSONException {
        ArrayList<CategoryScore> categoryScoreList = new ArrayList<>();
        AsyncClass asyncClass = new AsyncClass();
        JSONArray ja = null;
        try {
            ja = asyncClass.execute("getAllCategoryScores").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jo;
        int i=0;
        while (ja != null && i < ja.length()) {
            jo = ja.getJSONObject(i);
            CategoryScore categoryScore = new CategoryScore();
            categoryScore.setId(Integer.parseInt(jo.getString("_id")));
            categoryScore.setBestscore(Integer.parseInt(jo.getString("bestscore")));
            categoryScore.setDifficulty(jo.getString("difficulty"));
            categoryScore.setCategoryID(Integer.parseInt(jo.getString("category_id")));
            categoryScoreList.add(categoryScore);
            i++;
        }
        return categoryScoreList;
    }

    public List<Category> getAllCategories() throws JSONException {
        List<Category> categoryList = new ArrayList<>();
        AsyncClass asyncClass = new AsyncClass();
        JSONArray ja = null;
        try {
            ja = asyncClass.execute("getAllCategories").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jo;
        int i=0;
        while (ja != null && i < ja.length()) {
            jo = ja.getJSONObject(i);
            Category category = new Category();
            category.setId(Integer.parseInt(jo.getString("_id")));
            category.setName(jo.getString("name"));
            categoryList.add(category);
            i++;
        }
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() throws JSONException {
        ArrayList<Question> questionList = new ArrayList<>();
        AsyncClass asyncClass = new AsyncClass();
        JSONArray ja = null;
        try {
            ja = asyncClass.execute("getAllQuestions").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jo;
        int i=0;
        while (ja != null && i < ja.length()) {
            jo = ja.getJSONObject(i);
            Question question = new Question();
            question.setId(Integer.parseInt(jo.getString("_id")));
            question.setQuestion(jo.getString("question"));
            question.setOption1(jo.getString("option1"));
            question.setOption2(jo.getString("option2"));
            question.setOption3(jo.getString("option3"));
            question.setAnswerNr(Integer.parseInt(jo.getString("answer_nr")));
            question.setDifficulty(jo.getString("difficulty"));
            question.setCategoryID(Integer.parseInt(jo.getString("category_id")));
            questionList.add(question);
            i++;
        }
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) throws JSONException {
        ArrayList<Question> questionList = getAllQuestions();
        ArrayList<Question> _questionList = new ArrayList<>();
        for (Question question: questionList
             ) {
            if(question.getCategoryID() == categoryID && question.getDifficulty() == difficulty){
                _questionList.add(question);
            }
        }
        return _questionList;
    }

}
