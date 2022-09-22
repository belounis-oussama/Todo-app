package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;


import com.example.todo.Adapter.TodoAdapter;
import com.example.todo.Modeel.ToDoModel;
import com.example.todo.Utils.DBH;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DBH myDB;
    private List<ToDoModel> mList;
    private TodoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=findViewById(R.id.recycleView);
        fab=findViewById(R.id.fab);

        myDB =new DBH(MainActivity.this);

        mList=new ArrayList<>();
        adapter=new TodoAdapter(myDB,MainActivity.this);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mList=myDB.gettAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager() ,AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        mList=myDB.gettAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();

    }
}