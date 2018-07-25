package com.study.yuyong.activitytest;
/**
 * @date on 2018/7/11
 * @author yuyong
 * @Email yu1183688986@163.com
 * @describe 继承抽象类SQLiteOpenHelper来实现自己的数据库帮助类完成SQLite数据库的创建
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    //创建数据库的SQL语句
    public static final String CREATE_BOOK = "create table book ("
            +"id integer primary key autoincrement, "
            +"author text, "
            +"price real, "
            +"pages integer, "
            +"name text)";
    /**
     * constructor
     * @param context 上下文
     * @param name 数据库名称
     * @param factory 查询数据库时可以返回自定义cursor
     * @param version 数据库版本
     */
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        Toast.makeText(context,"database creates succeeded",Toast.LENGTH_SHORT).show();
    }

    /**
     * 如果创建表时发现表已经存在就会报错，因此在这里删除掉已经存在的表
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Book");
        onCreate(sqLiteDatabase);
    }
}
