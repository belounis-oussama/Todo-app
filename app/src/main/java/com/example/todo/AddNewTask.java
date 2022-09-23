package com.example.todo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.Modeel.ToDoModel;
import com.example.todo.Utils.DBH;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.color.ColorRoles;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    //widgets
    private EditText mEditText;
    private Button mSave_btn;

    private DBH Mydb;

    public static AddNewTask newInstance()
    {
        return new AddNewTask();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_new_task , container ,false);
                return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mEditText= view.findViewById(R.id.text_edit);
        mSave_btn=view.findViewById(R.id.button_save);

        Mydb=new DBH(getActivity());

        boolean isUpdate=false;

        Bundle bundle=getArguments();

        if (bundle!=null)
        {
            isUpdate=true;
            String task=bundle.getString("task");
            mEditText.setText(task);


            if (task.length() >0)
            {
                mSave_btn.setEnabled(false);
            }
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(""))
                {
                    mSave_btn.setEnabled(false);
                    mSave_btn.setBackgroundColor(Color.GRAY);
                }else
                {
                    mSave_btn.setEnabled(true);
                    mSave_btn.setBackgroundColor(getResources().getColor(R.color.sec_color)); //you maybe need to change this
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        boolean finalIsUpdate = isUpdate;
        mSave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text= mEditText.getText().toString();
                if (finalIsUpdate)
                {
                    Mydb.updateTask(bundle.getInt("id"),text);
                }else
                {
                    ToDoModel item=new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    Mydb.insertTask(item);


                }
                dismiss();
            }
        });


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if (activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
