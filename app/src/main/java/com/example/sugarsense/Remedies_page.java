package com.example.sugarsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.sugarsense.ui.remedies.Remedies_Fragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class Remedies_page extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;
    TextView Header_text_box;
    TextView text_box;
    int char_count = 0;
    String pageType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedies_page);

        getWindow().setStatusBarColor(ContextCompat.getColor(Remedies_page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        text_box = findViewById(R.id.text_box);
        Header_text_box = findViewById(R.id.Header_text_box);
        image = findViewById(R.id.image);

        pageType = getIntent().getStringExtra("Remedy");

        if(pageType.equalsIgnoreCase("Homeopathic"))
        {
            setTitle("Homeopathic Remedies");
            coordinatorLayout.setBackgroundColor(Color.parseColor("#e6ccb2"));
            appBarLayout.setBackgroundColor(Color.parseColor("#e6ccb2"));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.homeopathic_remedies_img);
            printHeaderText("Homeopathic");
        }

        else if(pageType.equalsIgnoreCase("Ayurvedic"))
        {
            setTitle("Ayurvedic Remedies");
            coordinatorLayout.setBackgroundColor(Color.parseColor("#a7c957"));
            appBarLayout.setBackgroundColor(Color.parseColor("#a7c957"));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.ayurvedic_remedies_img);
            printHeaderText("Ayurvedic");
        }

        else if(pageType.equalsIgnoreCase("Allopathic"))
        {
            setTitle("Allopathic Remedies");
            coordinatorLayout.setBackgroundColor(Color.parseColor("#ddb892"));
            appBarLayout.setBackgroundColor(Color.parseColor("#ddb892"));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.allopathic_remedies_img);
            printHeaderText("Allopathic");
        }

        else if(pageType.equalsIgnoreCase("Life style changes"))
        {
            setTitle("Life Style Changes");
            coordinatorLayout.setBackgroundColor(Color.parseColor("#495057"));
            appBarLayout.setBackgroundColor(Color.parseColor("#495057"));
            collapsingToolbarLayout.setBackgroundResource(R.drawable.life_style_changes_img);
            printHeaderText("Life Style Changes");
        }
    }

    private void printHeaderText(String text)
    {
        if(char_count <= text.length())
        {
            String fetchedText = text.substring(0, char_count);
            Header_text_box.setText(fetchedText);

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count++;
                    printHeaderText(text);
                }
            },200);
        }
        else
        {
            if(pageType.equalsIgnoreCase("Homeopathic"))
            {
                text_box.setText(R.string.homeopathic_remedy_description);
            }
            else if(pageType.equalsIgnoreCase("Ayurvedic"))
            {
                text_box.setText(R.string.ayurvedic_remedy_description);
            }
            else if(pageType.equalsIgnoreCase("Allopathic"))
            {
                text_box.setText(R.string.allopethic_remedies_description);
            }
            else if(pageType.equalsIgnoreCase("Life style changes"))
            {
                text_box.setText(R.string.life_style_changes_remediees_description);
            }
        }
    }
}