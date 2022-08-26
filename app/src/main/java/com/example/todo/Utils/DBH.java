package com.example.todo.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.Modeel.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DBH extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DBName="TODO_DB";
    private static final String TableName="TODO_TABLE";
    private static final String COL_1="ID";
    private static final String COL_2="TASK";
    private static final String COL_3="STATUS";


//constructor for DBH
    public DBH(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//creating table
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TableName+"(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");

    }

    @Override
//updating table
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
db.execSQL("DROP TABLE IF EXISTS "+TableName);
onCreate(db);
    }

    public void insertTask(ToDoModel model)
    {
        //inserting a new task
        db =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(COL_2 , model.getTask()); //getting name from getter
        values.put(COL_3 , 0); //default is unchecked
        db.insert(TableName , null , values);
    }

    public void updateTask(int id , String task)
    {
        //updating existing  new task
        db =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(COL_2 , task);
        db.update(TableName, values,"ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id , int status)
    {
//updating existing  task status
        db =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(COL_3,status);
        db.update(TableName, values,"ID=?", new String[]{String.valueOf(id)});
    }

    public void DeleteTask(int id)
    {
        db =this.getWritableDatabase();
        db.delete(TableName,"ID=?", new String[]{String.valueOf(id)});
    }


    @SuppressLint("Range")
    public List<ToDoModel> gettAllTasks()
    {
        db =this.getWritableDatabase();
        Cursor cursor=null;
        List<ToDoModel> modelList=new ArrayList<>();

        db.beginTransaction();
        try{
            cursor=db.query(TableName,null,null,null,null,null,null);
            if (cursor != null)
            {
if (cursor.moveToFirst())
do{
ToDoModel task=new ToDoModel();
task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
task.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
modelList.add(task);


}while (cursor.moveToNext());
            }

        }finally {
            db.endTransaction();
            cursor.close();
        }

        return modelList;
    }


}
