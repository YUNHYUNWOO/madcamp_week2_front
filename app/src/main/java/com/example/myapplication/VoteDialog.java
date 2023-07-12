package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class VoteDialog extends Dialog implements View.OnClickListener{
    public VoteDialog(@NonNull Context context, ArrayList<VoteListItem> voteListItemArrayList, TextView textView) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        setContentView(R.layout.vote_dialog);

        RadioGroup radioGroup = findViewById(R.id.radio_group);
        RadioButton radioButton;
        for (VoteListItem i : voteListItemArrayList){
            String place_name = i.getPlace_name();

            radioButton = new RadioButton(getContext());
            radioButton.setText(place_name);

            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String check = ((RadioButton)radioGroup.getChildAt(i - 1)).getText().toString();
                textView.setText(check);
                dismiss();
            }
        });
    }

    public VoteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View view) {

    }
}