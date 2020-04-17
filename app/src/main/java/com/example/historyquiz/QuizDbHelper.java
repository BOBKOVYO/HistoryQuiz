package com.example.historyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.QuoteSpan;

import androidx.annotation.Nullable;
import com.example.historyquiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyQuiz4.db";
    private static final int DATABASE_VERSION = 6;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +

                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        final String SQL_CREATE_BESTSCORES_TABLE = "CREATE TABLE " +
                BestScoresTable.TABLE_NAME + " ( " +
                BestScoresTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BestScoresTable.COLUMN_DIFFICULTY + " TEXT, " +
                BestScoresTable.COLUMN_BESTSCORES + " INTEGER, " +
                BestScoresTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_BESTSCORES_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BestScoresTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Русские земли и княжества в Средние века (XII – сер. XV вв.)");
        insertCategory(c1);
        Category c2 = new Category("Российская Федерация");
        insertCategory(c2);
    }

    public void addCategory(Category category){
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories){
        db = getWritableDatabase();
        for(Category category : categories){
            insertCategory(category);
        }
    }

    public void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Стрельцами называли ...?",
                "Княжеских управителей", "служилых людей, состовляющих постоянное войско, вооружённых огнестрельным оружием", "Торговцев", 2,
                Question.DIFFICULTY_EASY, 1);
        insertQuestion(q1);
        Question q2 = new Question("Какое из приведенных ниже понятий связано с ордынским игом?",
                "посадник", "архиепископ", "ярлык", 3,
                Question.DIFFICULTY_EASY, 1);
        insertQuestion(q2);
        Question q3 = new Question("Как назывались погодные записи исторических событий?",
                "житиями", "летописями", "сказаниями", 2,
                Question.DIFFICULTY_EASY, 1);
        insertQuestion(q3);
        Question q4 = new Question("Что не относится к причинам раздробленности Руси?",
                "рост городов", "усиление власти киевского князя", "зарождение дворянства", 2,
                Question.DIFFICULTY_MEDIUM, 1);
        insertQuestion(q4);
        Question q5 = new Question("Ордынское войско на Куликовом поле возглавил хан ...",
                "Мамая", "Ахмат", "Батый", 1,
                Question.DIFFICULTY_MEDIUM, 1);
        insertQuestion(q5);
        Question q6 = new Question("Какой год считается началом русского крестового похода в степь?",
                "1111 г.", "1112 г.", "1115 г.", 1,
                Question.DIFFICULTY_HARD, 1);
        insertQuestion(q6);
        Question q7 = new Question("Из суждений А и Б верно?\n" +
                "\n" +
                "А. Российская цивилизация развивалась исключительно за счёт влияния других цивилизаций.\n" +
                "\n" +
                "Б. Российская цивилизация оказала заметный вклад в развитие мировой цивилизации.",
                "только А", "только Б", "и А, и Б", 2,
                Question.DIFFICULTY_EASY, 2);
        insertQuestion(q7);
        Question q8 = new Question("Какое событие, связанное с внешней политикой России, относится к 1992 – 1999 гг.?",
                "вступление в блок НАТО", "возведение берлинской стены", "вхождение в «восьмерку» ведущих стран мира", 3,
                Question.DIFFICULTY_MEDIUM, 2);
        insertQuestion(q8);
        Question q9 = new Question("По Конституции России Верховным Главнокомандующим Вооруженными Силами страны является ...",
                "министр обороны", "премьер-министр", "Президент РФ", 3,
                Question.DIFFICULTY_MEDIUM, 2);
        insertQuestion(q9);
        Question q10 = new Question("Что из названного относилось к событиям противостояния законодательной и исполнительной власти в России в октябре 1993 г.?",
                "штурм Белого Дома в Москве", "отставка президента РФ Б.Н.Ельцина", "заключение мирного соглашения о преодалении кризиса", 1,
                Question.DIFFICULTY_HARD, 2);
        insertQuestion(q10);
        Question q11 = new Question("В каком году была принята действующая Конституция РФ?",
                "1991 г.", "1993 г.", "1995 г.", 2,
                Question.DIFFICULTY_HARD, 2);
        insertQuestion(q11);
    }

    public void addBestScore(CategoryScore categoryScore){
        db = getWritableDatabase();
        insertCategoryScore(categoryScore);
    }

    public void insertCategoryScore(CategoryScore categoryScore) {
        ContentValues cv = new ContentValues();
        cv.put(BestScoresTable.COLUMN_DIFFICULTY, categoryScore.getDifficulty());
        cv.put(BestScoresTable.COLUMN_BESTSCORES, categoryScore.getBestscore());
        cv.put(BestScoresTable.COLUMN_CATEGORY_ID, categoryScore.getCategoryID());
        db.insert(BestScoresTable.TABLE_NAME, null, cv);
    }

    public void updateCategoryScore(CategoryScore categoryScore){
        db.execSQL("UPDATE " + BestScoresTable.TABLE_NAME + " SET " + BestScoresTable.COLUMN_BESTSCORES + " = " + categoryScore.getBestscore() +" WHERE " + BestScoresTable.COLUMN_DIFFICULTY + " = '" + categoryScore.getDifficulty() + "' AND " + BestScoresTable.COLUMN_CATEGORY_ID + " = " + categoryScore.getCategoryID());
    }

    public ArrayList<CategoryScore> getAllCategoryScores() {
        ArrayList<CategoryScore> categoryScoreList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + BestScoresTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                CategoryScore categoryScore = new CategoryScore();
                categoryScore.setId(c.getInt(c.getColumnIndex(BestScoresTable._ID)));
                categoryScore.setBestscore(c.getInt(c.getColumnIndex(BestScoresTable.COLUMN_BESTSCORES)));
                categoryScore.setDifficulty(c.getString(c.getColumnIndex(BestScoresTable.COLUMN_DIFFICULTY)));
                categoryScore.setCategoryID(c.getInt(c.getColumnIndex(BestScoresTable.COLUMN_CATEGORY_ID)));
                categoryScoreList.add(categoryScore);
            } while (c.moveToNext());
        }

        c.close();
        return categoryScoreList;
    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    public void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
