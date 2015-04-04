package com.dd.model.dao;


import android.content.Context;
import android.database.Cursor;
import com.dd.R;
import com.dd.data.User;
import com.dd.data.UserProxy;
import com.dd.model.Database;
import com.dd.model.utils.ArrayUtils;
import com.dd.model.utils.CursorParser;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Database mDatabase;
    private Context mContext;

    public UserDAO(Database database, Context context) {
        mDatabase = database;
        mContext = context;
    }

    public static String getCreateTable(Context context) {
        return context.getString(R.string.create_table_user);
    }

    public static String getDropTable(Context context) {
        return context.getString(R.string.drop_table_users);
    }

    public void deleteAll() {
        mDatabase.execSQL(mContext.getString(R.string.delete_all_users));
    }

    public void insert(List<User> userList) {
        mDatabase.beginTransaction();
        String sql = mContext.getString(R.string.insert_user);
        String[] bindArgs;
        for (User user : userList) {
            bindArgs = ArrayUtils.build(user.getAge(), user.getName(), user.getEmail());
            mDatabase.execSQL(sql, bindArgs);
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public void insert(User user) {
        String[] bindArgs = ArrayUtils.build(user.getName(), user.getAge());
        mDatabase.execSQL(mContext.getString(R.string.insert_user), bindArgs);
    }

    public List<User> selectAll() {
        Cursor cursor = mDatabase.rawQuery(mContext.getString(R.string.select_all_users), null);
        List<User> dataList = manageCursor(cursor);
        closeCursor(cursor);

        return dataList;
    }

    public List<UserProxy> selectAllUserProxy() {
        Cursor cursor = mDatabase.rawQuery(mContext.getString(R.string.select_all_users), null);
        return manageProxyCursor(cursor);
    }

    public void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    protected User cursorToData(Cursor cursor) {
        CursorParser parser = new CursorParser(cursor);

        User user = new User();
        user.setId(parser.readLong());
        user.setAge(parser.readInt());
        user.setName(parser.readString());
        user.setEmail(parser.readString());

        return user;
    }

    protected List<UserProxy> manageProxyCursor(Cursor cursor) {
        List<UserProxy> dataList = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dataList.add(new UserProxy(cursor, cursor.getPosition()));
                cursor.moveToNext();
            }
        }

        if(dataList.isEmpty()) {
            closeCursor(cursor);
        }

        return dataList;
    }

    protected List<User> manageCursor(Cursor cursor) {
        List<User> dataList = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToData(cursor);
                dataList.add(user);
                cursor.moveToNext();
            }
        }
        return dataList;
    }
}
