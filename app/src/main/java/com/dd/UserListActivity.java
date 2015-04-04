package com.dd;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import com.dd.data.UserProxy;
import com.dd.model.Database;
import com.dd.model.DatabaseConnection;
import com.dd.model.dao.UserDAO;

import java.util.List;

public class UserListActivity extends ListActivity {

    private ArrayAdapter<UserProxy> mAdapter;
    private UserDAO mUserDAO;

    public static void start(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, UserListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database database = DatabaseConnection.instance().open();
        mUserDAO = new UserDAO(database, getApplicationContext());

        List<UserProxy> userProxies = mUserDAO.selectAllUserProxy();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userProxies);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        cleanUpDatabase();
        super.onDestroy();
    }

    private void cleanUpDatabase() {
        if(mAdapter != null && mAdapter.getCount() != 0) {
            Cursor cursor = mAdapter.getItem(0).getCursor();
            mUserDAO.closeCursor(cursor);
        }

        DatabaseConnection.instance().close();
    }
}
