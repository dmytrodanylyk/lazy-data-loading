package com.dd;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import com.dd.data.LazyList;
import com.dd.data.User;
import com.dd.model.Database;
import com.dd.model.DatabaseConnection;
import com.dd.model.dao.UserDAO;

public class UserListActivity3 extends ListActivity {

    private ArrayAdapter<User> mAdapter;
    private LazyList<User> mUserLazyList;

    public static void start(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, UserListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database database = DatabaseConnection.instance().open();
        UserDAO mUserDAO = new UserDAO(database, getApplicationContext());

        mUserLazyList = mUserDAO.selectAllLazy();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mUserLazyList);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        cleanUpDatabase();
        super.onDestroy();
    }

    private void cleanUpDatabase() {
        if(mAdapter != null && mAdapter.getCount() != 0) {
            mUserLazyList.closeCursor();
        }

        DatabaseConnection.instance().close();
    }
}
