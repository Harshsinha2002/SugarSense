package com.example.sugarsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;

public class Diet_Plans_Page extends AppCompatActivity {

    Dialog dialog;
    MaterialCheckBox Vegetarian_Checkbox, Non_Vegetarian_Checkbox, Flexitarian_Checkbox;
    Button Dialog_Submit_btn;
    TextView text_box;
    String vegetarianTitle = "Vegetarian";
    String nonVegetarianTitle = "Non-Vegetarian";
    String flexitarianTitle = "Flexitarian";
    int char_count = 0;
    ImageView image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    int vegetarianChackState = 0;
    int nonvegetarianChackState = 0;
    int flexitarianChackState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plans_page);

        getWindow().setStatusBarColor(ContextCompat.getColor(Diet_Plans_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Dietary Advice");

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        text_box = findViewById(R.id.text_box);
        image = findViewById(R.id.image);

        dialog = new Dialog(Diet_Plans_Page.this);
        dialog.show();
        dialog.setContentView(R.layout.select_your_diet_dialogbox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_box_background));
        dialog.setCancelable(false);

        Vegetarian_Checkbox = dialog.findViewById(R.id.Vegetarian_Checkbox);
        Non_Vegetarian_Checkbox = dialog.findViewById(R.id.Non_Vegetarian_Checkbox);
        Flexitarian_Checkbox = dialog.findViewById(R.id.Flexitarian_Checkbox);
        Dialog_Submit_btn = dialog.findViewById(R.id.Dialog_Submit_btn);


        Dialog_Submit_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Vegetarian_Checkbox.isChecked() && Non_Vegetarian_Checkbox.isChecked() && Flexitarian_Checkbox.isChecked()
                        || Vegetarian_Checkbox.isChecked() && Non_Vegetarian_Checkbox.isChecked()
                        || Non_Vegetarian_Checkbox.isChecked() && Flexitarian_Checkbox.isChecked()
                        || Flexitarian_Checkbox.isChecked() && Vegetarian_Checkbox.isChecked())
                {
                    Toast.makeText(Diet_Plans_Page.this, "Please select just 1 diet type!!", Toast.LENGTH_SHORT).show();
                }

                   else if(Vegetarian_Checkbox.isChecked())
                    {
                        vegetarianChackState = 1;
                        dialog.dismiss();
                        Toast.makeText(Diet_Plans_Page.this, "Vegetarian Diet", Toast.LENGTH_SHORT).show();
                        setTitleText(vegetarianTitle);
                    }

                    else if(Non_Vegetarian_Checkbox.isChecked())
                    {
                        nonvegetarianChackState = 1;
                        dialog.dismiss();
                        Toast.makeText(Diet_Plans_Page.this, "Non Vegetarian Diet", Toast.LENGTH_SHORT).show();
                        setTitleText(nonVegetarianTitle);
                    }

                    else if(Flexitarian_Checkbox.isChecked())
                    {
                        flexitarianChackState = 1;
                        dialog.dismiss();
                        Toast.makeText(Diet_Plans_Page.this, "Flexitarian Diet", Toast.LENGTH_SHORT).show();
                        setTitleText(flexitarianTitle);
                    }

                    else
                {
                    Toast.makeText(Diet_Plans_Page.this, "Please select 1 diet type!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setTitleText(String text)
    {
        if(char_count <= text.length())
        {
            String fetchedText = text.substring(0, char_count);
            collapsingToolbarLayout.setTitle(fetchedText);

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count++;
                    setTitleText(text);
                }
            },200);
        }
        else
        {
            imagePerformAnimation();
        }
    }
    private void imagePerformAnimation()
    {
        if(vegetarianChackState == 1)
        {
            image.setImageDrawable(getDrawable(R.drawable.vegetarian_food_img));
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
            image.startAnimation(animation);
            text_box.setText(R.string.vegetarian_diet_text);
        }
        else if(nonvegetarianChackState == 1)
        {
            image.setImageDrawable(getDrawable(R.drawable.non_vegetarian_food_img));
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
            image.startAnimation(animation);
            text_box.setText(R.string.non_vegetarian_diet_text);
        }
        else if(flexitarianChackState == 1)
        {
            image.setImageDrawable(getDrawable(R.drawable.flexitarian_food_img));
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_animation);
            image.startAnimation(animation);
            text_box.setText(R.string.flexitarian_diet_text);
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Diet_Plans_Page.this, MainActivity.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateDiagonal(Diet_Plans_Page.this);
        finish();
    }

}