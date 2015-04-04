package com.dd.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

abstract class CursorItemProxy {

    private Cursor mCursor;
    private int mIndex;

    public CursorItemProxy(@NonNull Cursor cursor, int index) {
        mCursor = cursor;
        mIndex = index;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public int getIndex() {
        return mIndex;
    }

}
