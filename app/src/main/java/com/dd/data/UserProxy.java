package com.dd.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

public class UserProxy extends CursorItemProxy {

    private User mUser;

    public UserProxy(@NonNull Cursor cursor, int index) {
        super(cursor, index);
        mUser = new User();
    }

    public String getName() {
        if (mUser.getName() == null) {
            Cursor cursor = getCursor();
            cursor.moveToPosition(getIndex());
            int columnIndex = cursor.getColumnIndex("name");
            mUser.setName(cursor.getString(columnIndex));
        }

        return mUser.getName();
    }

    public String getEmail() {
        // TODO add parsing data from cursor
        return mUser.getEmail();
    }

    public int getAge() {
        // TODO add parsing data from cursor
        return mUser.getAge();
    }

    public long getId() {
        // TODO add parsing data from cursor
        return mUser.getId();
    }

    @Override
    public String toString() {
        return "Name: " + getName();
    }
}
