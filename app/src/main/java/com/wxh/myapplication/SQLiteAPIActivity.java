package com.wxh.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *  Android API操作SQLIte
 */
public class SQLiteAPIActivity extends AppCompatActivity  implements View.OnClickListener{

    /**
     *  声明变量
     */
    private Button btnInsert;
    private Button btnQuery;
    private Button btnUpdate;
    private Button btnDelete;
    private Context context;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBOpenHelper;
    private StringBuilder sb;
    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_api);

        // 实例化上下文对象
        context = SQLiteAPIActivity.this;
        // 实例化数据库工具类
        myDBOpenHelper = new MyDBOpenHelper(context,"my.db",null,1);
        // 绑定视图相关事件
        bindViews();
    }

    /**
     *  封装点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        // 实例化数据库
        db = myDBOpenHelper.getWritableDatabase();
        switch (v.getId()){
            case R.id.btn_insert:
                ContentValues values = new ContentValues();
                values.put("name","呵呵"+i);
                i++;
                // 参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据
                db.insert("person",null,values);
                Toast.makeText(context,"插入完毕",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                sb = new StringBuilder();
                // 参数依次是:表名，列名，select条件，select参数，group by条件，having条件
                // order by
                // 游标类似Java的结果集ResultSet
                Cursor cursor = db.query("person",null,null,null,null,null,null);
                // 游标移动到第一行
                if(cursor.moveToFirst()){
                    // 循环跳出条件时明月下一个行记录
                    do {
                        int personId = cursor.getInt(cursor.getColumnIndex("personid"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        sb.append("id:"+personId+",name:"+name+"\n");
                    }while (cursor.moveToNext());
                }
                // 关闭游标
                cursor.close();
                Toast.makeText(context,sb.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                ContentValues valuesUpdate = new ContentValues();
                valuesUpdate.put("name","嘻嘻");
                // 参数依次：表名、修改的值，修改的列名，where条件,如果不指定三四两个参数，会更改所有行
                db.update("person",valuesUpdate,"name = ?",new String[]{"呵呵2"});
                break;
            case R.id.btn_delete:
                // 参数依次是表名，以及where条件与约束
                db.delete("person","personid = ?",new String[]{"3"});
                break;
            default:
                break;
        }
    }

    private void bindViews(){
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnQuery = (Button) findViewById(R.id.btn_query);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        btnInsert.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    //*********************SQL语句操作数据库************************************
    /**
     *  插入数据
     * @param person
     */
    public void insert(Person person){
        // 可以写
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        db.execSQL("INSERT INTO person(name,phone) values(?,?)",
                new String[]{person.getName(),person.getPhone()});
    }

    /**
     *  修改数据
     * @param person
     */
    public void update(Person person){
        // 可以写
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        db.execSQL("UPDATE person SET name = ?,phone = ? WHERE personid = ?",
                new String[]{person.getName(),person.getPhone(),
                        String.valueOf(person.getPersonId())});
    }

    /**
     *  删除数据
     * @param personId
     */
    public void delete(Integer personId){
        // 可以写
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        db.execSQL("DELETE FROM person WHERE personid = ?",
                new String[]{String.valueOf(personId)});
    }

    /**
     *  查询数据
     * @param personId
     * @return  Person
     */
    public Person query(Integer personId){
        // 可以读
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE personid=?",
                new String[]{String.valueOf(personId)});
        // 游标移到第一行
        if(cursor.moveToFirst()){
            // 获取每列值
            Integer personId2 = cursor.getInt(cursor.getColumnIndex("personid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            return new Person(personId2,name,phone);
        }
        cursor.close();
        return null;
    }

    /**
     *  数据分页
     * @param pageIndex
     * @param pageSize
     * @return List
     */
    public List<Person> getScrollData(int pageIndex,int pageSize){
        List<Person> persons = new ArrayList<>();
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM person ORDER BY personid ASC LIMIT ?,?",
                new String[]{String.valueOf(pageIndex), String.valueOf(pageSize)});
        // 有数据就遍历
        while(cursor.moveToNext()){
            Integer personId = cursor.getInt(cursor.getColumnIndex("personid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            persons.add(new Person(personId,name,phone));
        }
        cursor.close();
        return persons;
    }

    /**
     *  查询总记录数
     * @return
     */
    public int getCount(){
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM person",null);
        int result  = 0;
        if(cursor.moveToFirst()){
            result = cursor.getInt(0);
        }
        cursor.close();
        return result;
    }

}
