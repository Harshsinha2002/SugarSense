package com.example.sugarsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph_Generation_Page extends AppCompatActivity {

    int Fasting_Test1, Fasting_Test2, Fasting_Test3, Fasting_Test4, Fasting_Test5, Fasting_Test6, Fasting_Test7;
    int PP_Test1, PP_Test2, PP_Test3, PP_Test4, PP_Test5, PP_Test6, PP_Test7;
    String low_blood_sugar = "Your blood sugar analysis displays you are having a low blood sugar level and may have chances of HYPOGLYCEMIA. \n \n You should consult our doctor and follow our suggested Precautions, Remedies and Diet Plans.";
    String normal_blood_sugar = "Your blood sugar analysis displays you are having a normal blood sugar level.\n \n You should follow our suggested Precautions and Diet Plans for more good health.";
    String prediabetes_blood_sugar = "Your blood sugar analysis displays you are having a border line blood Diabetes.\n \n You should follow our suggested Precautions and Diet Plans for good a health and avoid the health risks of diabetes.";
    String high_blood_sugar = "Your blood sugar analysis displays you are having a high blood sugar level and may have chances of HYPERGLYCEMIA..\n \nYou should consult our doctor and follow our suggested Precautions, Remedies and Diet Plans.";
    int char_count = 0;
    TextView print_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_generation_page);
        getWindow().setStatusBarColor(ContextCompat.getColor(Graph_Generation_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Graph");

        print_txt = findViewById(R.id.print_txt);
        GraphView graphView = findViewById(R.id.idGraphView);
        Fasting_Test1 = getIntent().getIntExtra("Fasting_Test1", 0);
        Fasting_Test2 = getIntent().getIntExtra("Fasting_Test2", 0);
        Fasting_Test3 = getIntent().getIntExtra("Fasting_Test3", 0);
        Fasting_Test4 = getIntent().getIntExtra("Fasting_Test4", 0);
        Fasting_Test5 = getIntent().getIntExtra("Fasting_Test5", 0);
        Fasting_Test6 = getIntent().getIntExtra("Fasting_Test6", 0);
        Fasting_Test7 = getIntent().getIntExtra("Fasting_Test7", 0);

        PP_Test1 = getIntent().getIntExtra("PP_Test1", 0);
        PP_Test2 = getIntent().getIntExtra("PP_Test2", 0);
        PP_Test3 = getIntent().getIntExtra("PP_Test3", 0);
        PP_Test4 = getIntent().getIntExtra("PP_Test4", 0);
        PP_Test5 = getIntent().getIntExtra("PP_Test5", 0);
        PP_Test6 = getIntent().getIntExtra("PP_Test6", 0);
        PP_Test7 = getIntent().getIntExtra("PP_Test7", 0);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, Fasting_Test1),
                new DataPoint(2, Fasting_Test2),
                new DataPoint(4, Fasting_Test3),
                new DataPoint(5, Fasting_Test4),
                new DataPoint(6, Fasting_Test5),
                new DataPoint(7, Fasting_Test6),
                new DataPoint(8, Fasting_Test7),
        });

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, PP_Test1),
                new DataPoint(2, PP_Test2),
                new DataPoint(4, PP_Test3),
                new DataPoint(5, PP_Test4),
                new DataPoint(6, PP_Test5),
                new DataPoint(7, PP_Test6),
                new DataPoint(8, PP_Test7),
        });

        series.setColor(Color.GREEN);
        series2.setColor(Color.RED);
        graphView.addSeries(series);
        graphView.addSeries(series2);

        int fasting_analysis_result = (Fasting_Test1 + Fasting_Test2 + Fasting_Test3 + Fasting_Test4 + Fasting_Test5 + Fasting_Test6 + Fasting_Test7) / 7;
        int pp_analysis_result = (PP_Test1 + PP_Test2 + PP_Test3 + PP_Test4 + PP_Test5 + PP_Test6 + PP_Test7) / 7;

        resultAnalysisReport(fasting_analysis_result, pp_analysis_result);
    }

    private void resultAnalysisReport(int fasting_analysis_result,int pp_analysis_result)
    {
        if(fasting_analysis_result < 70 && pp_analysis_result < 140) // when both fasting and pp are low
        {
            print_txt.setTextColor(Color.RED);
            printResult(low_blood_sugar);
        }

        else if (fasting_analysis_result < 70 && pp_analysis_result >= 140) // when just fasting is low
        {
            print_txt.setTextColor(Color.RED);
            printResult(low_blood_sugar);
        }

        else if (fasting_analysis_result >= 70 && pp_analysis_result < 140) // when just pp is low
        {
            print_txt.setTextColor(Color.RED);
            printResult(low_blood_sugar);
        }

        else if (fasting_analysis_result >= 70 && fasting_analysis_result <= 100 && pp_analysis_result >= 140 && pp_analysis_result <= 170) // when fasting and pp both are normal
        {
            print_txt.setTextColor(Color.GREEN);
            printResult(normal_blood_sugar);
        }

        else if(fasting_analysis_result > 100 && fasting_analysis_result <= 125 && pp_analysis_result >= 140 && pp_analysis_result <= 170) // when fasting is prediabetic and pp is normal
        {
            print_txt.setTextColor(Color.parseColor("#fca311"));
            printResult(prediabetes_blood_sugar);
        }

        else if(fasting_analysis_result >= 70 && fasting_analysis_result <= 100 && pp_analysis_result > 170 && pp_analysis_result <= 199) // when fasting is normal and pp is prediabetic
        {
            print_txt.setTextColor(Color.parseColor("#fca311"));
            printResult(prediabetes_blood_sugar);
        }

        else if(fasting_analysis_result > 100 && fasting_analysis_result <= 125 && pp_analysis_result > 170 && pp_analysis_result <= 199) // when fasting and pp is prediabetic
        {
            print_txt.setTextColor(Color.parseColor("#fca311"));
            printResult(prediabetes_blood_sugar);
        }

        else if(fasting_analysis_result > 125 || pp_analysis_result > 199)
        {
            print_txt.setTextColor(Color.RED);
            printResult(high_blood_sugar);
        }

        else
        {
            Toast.makeText(this, "Maybe some uncertail result have been passed by you please refer to your readings!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void printResult(String passedText)
    {
        if(char_count <= passedText.length())
        {
            String fetched_Text = passedText.substring(0,char_count);
            print_txt.setText(fetched_Text);

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count ++;
                    printResult(passedText);
                }
            }, 70);
        }
    }
}