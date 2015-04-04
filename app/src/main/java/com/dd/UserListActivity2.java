package com.dd;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import com.dd.data.User;
import com.dd.data.UserProxy;
import com.dd.model.Database;
import com.dd.model.DatabaseConnection;
import com.dd.model.dao.UserDAO;

import java.util.List;

public class UserListActivity2 extends ListActivity {

    public static void start(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, UserListActivity2.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database database = DatabaseConnection.instance().open();
        final UserDAO userDAO = new UserDAO(database, getApplicationContext());

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userList = userDAO.selectAll();
                deliverResultUI(userList);
            }
        }).start();

    }

    private void deliverResultUI(final List<User> userList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter adapter = new ArrayAdapter<>(UserListActivity2.this, android.R.layout.simple_list_item_1, userList);
                setListAdapter(adapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        DatabaseConnection.instance().close();
        super.onDestroy();
    }
}
