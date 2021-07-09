package com.example.madcamp2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddGroup extends DialogFragment {
    private Context mCon;
    private GridAdapter adapter;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.145:80";

    public AddGroup(Context context, GridAdapter adapter) {
        this.adapter = adapter;
        this.mCon = context;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.add_group, container, false);


        EditText et_date = rootView.findViewById(R.id.et_link);
        EditText et_todo = rootView.findViewById(R.id.et_todo);
        EditText et_headcount = rootView.findViewById(R.id.et_headcount);
        Button btn_cancel = rootView.findViewById(R.id.bt_link_cancel);
        Button btn_submit = rootView.findViewById(R.id.bt_link_submit);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                retrofitInterface = retrofit.create(RetrofitInterface.class);
                HashMap<String, String> map = new HashMap<>();
                map.put("time", et_date.getText().toString());
                map.put("place", et_todo.getText().toString());
                map.put("headcount", et_headcount.getText().toString());
                Call<GroupItem> call = retrofitInterface.executeGroupAdd(map);
                call.enqueue(new Callback<GroupItem>() {
                    @Override
                    public void onResponse(Call<GroupItem> call, Response<GroupItem> response) {
                        if (response.code() == 200) {
                            Toast.makeText(mCon, "add group successfully", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(mCon, "Already registered", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<GroupItem> call, Throwable t) {
                        Toast.makeText(mCon, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                dismiss();
            }
        });

        return rootView;
    }
}
