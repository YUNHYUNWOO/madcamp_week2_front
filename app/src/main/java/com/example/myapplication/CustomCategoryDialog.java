package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomCategoryDialog extends Dialog implements View.OnClickListener{
    private TextView category;
    public CustomCategoryDialog(@NonNull Context context, TextView category) {
        super(context);

        this.category = category;
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        setContentView(R.layout.category_dialog);

        View Item1 = findViewById(R.id.item1);
        View Item2 = findViewById(R.id.item2);
        View Item3 = findViewById(R.id.item3);
        View Item4 = findViewById(R.id.item4);

        Item1.setOnClickListener(this);
        Item2.setOnClickListener(this);
        Item3.setOnClickListener(this);
        Item4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.item1){
            category.setText("음식점");
            dismiss();
        } else if(view.getId() == R.id.item2){
            category.setText("카페");
            dismiss();
        } else if(view.getId() == R.id.item3){
            category.setText("피시방");
            dismiss();
        } else if(view.getId() == R.id.item4){
            category.setText("숙박");
            dismiss();
        }
    }
}
