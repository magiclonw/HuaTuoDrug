package com.magiclon.huatuodrug.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;

import com.magiclon.huatuodrug.model.CommonDrugBean;
import com.magiclon.huatuodrug.model.TermsBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * MagicLon
 */
public class DBManager {
    private static final String ASSETS_NAME = "huatuo.db";
    private static final String DB_NAME = "huatuo.db";
    private static final String TABLE_NAME_COMMONDRUG = "commondrug";
    private static final int BUFFER_SIZE = 1024;
    private static String DB_PATH;
    private static Context mContext;
    private final String TABLE_NAME_HISTORY = "history";
    private final String TABLE_NAME_TERMSHISTORY = "termshistory";
    private final String TABLE_NAME_ALISHISTORY = "alishistory";
    private final String TABLE_NAME_SUBJECTHISTORY = "subjecthistory";
    private final String TABLE_NAME_TERMS = "talkterms";
    private final String TABLE_NAME_DRUGALIS = "drugalis";
    private final String TABLE_NAME_SUBJECT = "subject";


    private static DBManager instance = new DBManager();

    public static DBManager getInstance(Context context) {
        mContext = context;
        DB_PATH = File.separator + "data" + Environment.getDataDirectory().getAbsolutePath() + File.separator + context.getPackageName() + File.separator + "databases" + File.separator;
        return instance;
    }

    public DBManager() {
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void copyDBFile() {
        File dir = new File(DB_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<CommonDrugBean> getAllCommonDrug() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery("select id,title,content from " + TABLE_NAME_COMMONDRUG, null);
        List<CommonDrugBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            result.add(new CommonDrugBean(id, title, content));
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<TermsBean> getAllTerms(String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String table = TABLE_NAME_TERMS;
        if (type.equals("2")) {
            table = TABLE_NAME_DRUGALIS;
        } else if (type.equals("3")) {
            table = TABLE_NAME_SUBJECT;
        }
        Cursor cursor = db.rawQuery("select pid,pname from " + table + " group by pid", null);
        List<TermsBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String pid = cursor.getString(0);
            String pname = cursor.getString(1);

            result.add(new TermsBean(pid, pname));
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<TermsBean> getAllSecondTerms(String type,String id) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String table = TABLE_NAME_SUBJECT;
        Cursor cursor = db.rawQuery("select sid,sname from " + table +" where pid="+id, null);
        List<TermsBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String pid = cursor.getString(0);
            String pname = cursor.getString(1);
            result.add(new TermsBean(pid, pname));
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<TermsBean> getSomeTermsDetail(String pid, String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String table = TABLE_NAME_TERMS;
        if (type.equals("2")) {
            table = TABLE_NAME_DRUGALIS;
        }
        Cursor cursor = db.rawQuery("select content from " + table + " where pid=" + pid, null);
        List<TermsBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String content = cursor.getString(0);
            result.add(new TermsBean(content));
        }
        cursor.close();
        db.close();
        return result;
    }
    public List<TermsBean> getSomeTermsSecondDetail(String pid, String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String table = TABLE_NAME_SUBJECT;
        Cursor cursor = db.rawQuery("select scontent from " + table + " where sid=" + pid, null);
        List<TermsBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String content = cursor.getString(0);
            result.add(new TermsBean(content));
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<CommonDrugBean> getSomeCommonDrug(String edt) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String slike = " like '%" + edt + "%' or ";
        Cursor cursor = db.rawQuery("select id,title,content from " + TABLE_NAME_COMMONDRUG + " where title" + slike + "content" + slike + "maindrug" + slike + "assistdrug" + slike + "nutrition" + slike + "tea" + slike + "mathine like '%" + edt + "%'", null);
        List<CommonDrugBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            result.add(new CommonDrugBean(id, title, content));
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<TermsBean> getSomeTerms(String edt, String type,String pid) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor=null;
        String table = "";
        if(type.equals("1")){
            table = TABLE_NAME_TERMS;
            cursor = db.rawQuery("select content from " + table + " where content like '%" + edt + "%'", null);
        }else if (type.equals("2")) {
            table = TABLE_NAME_DRUGALIS;
            cursor = db.rawQuery("select content from " + table + " where content like '%" + edt + "%'", null);
        }else if(type.equals("3")){
            table = TABLE_NAME_SUBJECT;
            cursor = db.rawQuery("select scontent from " + table + " where scontent like '%" + edt + "%'", null);
        }else if(type.equals("second3")){
            table = TABLE_NAME_SUBJECT;
            cursor = db.rawQuery("select scontent from " + table + " where scontent like '%" + edt + "%' and pid="+pid, null);
        }
        List<TermsBean> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String content = cursor.getString(0);
            result.add(new TermsBean(content));
        }
        cursor.close();
        db.close();
        return result;
    }

    public CommonDrugBean getDiseaseDetail(String id) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = db.rawQuery(String.format("select * from %s where id=?", TABLE_NAME_COMMONDRUG), new String[]{id});
        CommonDrugBean commonDrugBean = null;
        while (cursor.moveToNext()) {
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String maindrug = cursor.getString(3);
            String assistdrug = cursor.getString(4);
            String nutrition = cursor.getString(5);
            String tea = cursor.getString(6);
            String mathine = cursor.getString(7);
            commonDrugBean = new CommonDrugBean(id, title, content, maindrug, assistdrug, nutrition, tea, mathine);
        }
        cursor.close();
        db.close();
        return commonDrugBean;
    }

    /**
     * 读取所有历史纪录
     *
     * @return result 读取所有历史纪录
     */
    public List<String> getAllHistory(String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        String table = TABLE_NAME_HISTORY;
        if (type.equals("1")) {
            table = TABLE_NAME_TERMSHISTORY;
        } else if (type.equals("2")) {
            table = TABLE_NAME_ALISHISTORY;
        }else if(type.equals("3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }else if(type.equals("second3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }
        Cursor cursor = db.rawQuery("select * from " + table, null);
        List<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            result.add(name);
        }
        cursor.close();
        db.close();
        return result;
    }

    public void insertHistory(String name, String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        db.beginTransaction();
        String table = TABLE_NAME_HISTORY;
        if (type.equals("1")) {
            table = TABLE_NAME_TERMSHISTORY;
        } else if (type.equals("2")) {
            table = TABLE_NAME_ALISHISTORY;
        }else if(type.equals("3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }else if(type.equals("second3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }
        db.execSQL("insert into " + table + "(name) select '" + name + "' where not exists(select * from " + table + " where name='" + name + "')");
       db.setTransactionSuccessful();
       db.endTransaction();
        db.close();
    }

    public void deleteOneHistory(String name, String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        db.beginTransaction();
        String table = TABLE_NAME_HISTORY;
        if (type.equals("1")) {
            table = TABLE_NAME_TERMSHISTORY;
        } else if (type.equals("2")) {
            table = TABLE_NAME_ALISHISTORY;
        }else if(type.equals("3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }else if(type.equals("second3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }
        db.delete(table, "name=?", new String[]{name});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void deleteAllHistory(String type) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        db.beginTransaction();
        String table = TABLE_NAME_HISTORY;
        if (type.equals("1")) {
            table = TABLE_NAME_TERMSHISTORY;
        } else if (type.equals("2")) {
            table = TABLE_NAME_ALISHISTORY;
        }else if(type.equals("3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }else if(type.equals("second3")){
            table = TABLE_NAME_SUBJECTHISTORY;
        }
        db.delete(table, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

}
