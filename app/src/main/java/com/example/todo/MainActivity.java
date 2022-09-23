package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


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
    private Toolbar toolbar;
    TextView okay_text, cancel_text;
    public static final String TUTO_STRING="TUTORIAL_SHOW_DIALOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView=findViewById(R.id.recycleView);
        fab=findViewById(R.id.fab);
        toolbar=findViewById(R.id.toolbar);




        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean showDialog=preferences.getBoolean(TUTO_STRING,true);

        if (showDialog)
        {
            Dialog dialog = new Dialog(MainActivity.this);

            dialog.setContentView(R.layout.tutorial_dialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.setTitle("tutorial");
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

            okay_text = dialog.findViewById(R.id.ok);
            cancel_text = dialog.findViewById(R.id.dont_show_again);

            okay_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            cancel_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(TUTO_STRING,false);
                    editor.apply();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }




        myDB =new DBH(MainActivity.this);

        mList=new ArrayList<>();
        adapter=new TodoAdapter(myDB,MainActivity.this);


        setSupportActionBar(toolbar);

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