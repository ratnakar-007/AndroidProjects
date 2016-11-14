package com.example.ratnakarsharma.sqlitebasics.dbPackage.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.ratnakarsharma.sqlitebasics.model.Todo;

import java.util.ArrayList;

import static com.example.ratnakarsharma.sqlitebasics.dbPackage.DbStrings.*;
import static com.example.ratnakarsharma.sqlitebasics.dbPackage.tables.TodoTable.Columns.ID;

/**
 * Created by Ratnakar Sharma on 10/9/2016.
 */

public class TodoTable {

    public static final String TABLE_NAME = "todos";
    private static final String TAG = "myTag";

    public interface Columns {
        String ID = "id";
        String NAME = "name";
        String DONE = "done";
    }

    public static String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME + LBR
                    + Columns.ID + TYPE_INT_PK + COMMA
                    + Columns.NAME + TYPE_TEXT + COMMA
                    + Columns.DONE + TYPE_INT
                    + RBR + TERM;

    public static String[] FULL_PROJECTION = {
            Columns.ID, Columns.NAME, Columns.DONE
    };

    public static String UPD_TABLE_1_2 = "";

    public static long addNewTodo(SQLiteDatabase db, Todo newTodo) {

        ContentValues cv = new ContentValues();
        cv.put(Columns.NAME, newTodo.getTask());
        cv.put(Columns.ID, newTodo.getId());
        cv.put(Columns.DONE, newTodo.isDone() ? 1 : 0);

        return db.insert(TABLE_NAME,
                null,
                cv);
    }

    public static boolean updateTodo(SQLiteDatabase db, Todo newTodo){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.NAME, newTodo.getTask());
        //contentValues.put(Columns.ID, newTodo.getId());
        int id = Integer.valueOf(newTodo.getId());

        int k = db.update(TABLE_NAME, contentValues, "ID = "+id, null);
        if (k!=0)
        {
            return true;
        }
        else return false;

    }


    public static ArrayList<Todo> getAllTodos(SQLiteDatabase db) {
        ArrayList<Todo> todoList = new ArrayList<>();

        /* WHERE manufacturer = SONY AND price > 300
        *  WHERE manufacturer = SONY; DROP TABLE products;
        *  "manufacturer = ? AND price > ? " = selection
        *  ["SONY", 300] = selectionArgs
        *
         */

        Cursor c = db.query(TABLE_NAME,
                FULL_PROJECTION,
                null,
                null,
                null,
                null,
                ID + ORDER_DESC);

        int colId = c.getColumnIndex(ID);
        int colName = c.getColumnIndex(Columns.NAME);
        int colDone = c.getColumnIndex(Columns.DONE);

        while (c.moveToNext()) {
            todoList.add(new Todo(
                    c.getString(colId),
                    c.getString(colName),
                    1 == c.getInt(colDone)
            ));
        }

        c.close();

        return todoList;
    }

    public static Todo getTodo(int id) {
        return null;
    }
}