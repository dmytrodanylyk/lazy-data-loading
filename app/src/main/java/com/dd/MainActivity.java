package com.dd;

import android.app.Activity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.view.View;
import com.dd.data.User;
import com.dd.model.Database;
import com.dd.model.DatabaseConnection;
import com.dd.model.DatabaseHelper;
import com.dd.model.dao.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private UserDAO mUserDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseConnection.initializeInstance(new DatabaseHelper(this));

        Database database = DatabaseConnection.instance().open();
        mUserDAO = new UserDAO(database, getApplicationContext());

        initView();

    }

    @Override
    protected void onDestroy() {
        DatabaseConnection.instance().close();
        super.onDestroy();
    }

    private void initView() {
        findViewById(R.id.btnDeleteAllUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllUsers();
            }
        });

        findViewById(R.id.btnInsertUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUserList();
            }
        });

        findViewById(R.id.btnStartListActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListActivity.start(MainActivity.this);
            }
        });

        findViewById(R.id.btnStartListActivity2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListActivity2.start(MainActivity.this);
            }
        });
    }

    private void deleteAllUsers() {
        mUserDAO.deleteAll();
    }

    public void insertUserList() {
        mUserDAO.insert(generateDummyUserList(10000));
    }

    private List<User> generateDummyUserList(int itemsCount) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < itemsCount; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("Jon Doe " + i);
            user.setEmail("jondoe" + i + "@gmail.com");
            userList.add(user);
        }
        return userList;
    }
}
