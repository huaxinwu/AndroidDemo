package com.wxh.myapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
/**
 *  自定义一个内容提供器
 */
public class MyContentProvider extends ContentProvider {

    /**
     * 初始化一些常量
     */
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    /**
     *  数据库工具类
     */
    private DBOpenHlper dbOpenHlper;

    // 为了方便直接使用UriMatcher,这里addURI,下面再调用Matcher进行匹配
    static {
        //  三个参数：授权、表名、表中id为1的数据
        matcher.addURI("com.wxh.providers.myprovider","test",1);
    }

    /**
     *  实现父类构造器
     */
    public MyContentProvider() {
        super();
    }

    /**
     *  创建数据库
     * @return
     */
    @Override
    public boolean onCreate() {
        // 创建一个数据库文件，四个参数：上下文对象、文件名、游标工厂对象、版本号
        dbOpenHlper = new DBOpenHlper(this.getContext(),"test.db",null,1);
        return true;
    }

    /**
     *  获取Uri类型
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     *  插入数据
     * @param uri
     * @param values
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (matcher.match(uri)){
            case 1:
                SQLiteDatabase db = dbOpenHlper.getReadableDatabase();
                long rowId = db.insert("test",null,values);
                // 插入成功
                if(rowId > 0 ){
                    // 在前面已有的Uri后面追加ID
                    Uri nameUri = ContentUris.withAppendedId(uri,rowId);
                    // 通知数据已经发生改变
                    getContext().getContentResolver().notifyChange(nameUri,null);
                    // 结果返回uri
                    return nameUri;
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     *  删除数据
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     *  更新数据
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return  0;
    }

    /**
     *  查询数据
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }


}
