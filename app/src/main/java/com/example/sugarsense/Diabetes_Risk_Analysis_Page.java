package com.example.sugarsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Diabetes_Risk_Analysis_Page extends AppCompatActivity {

    TextView QuestionBox_TextView, BMI_TextView, Symptoms_and_FamilyHistory_TextView, Reports_Result_TextView;
    LinearLayout QuestionBox_LinearLayout;
    LinearLayout Question_Answer_LinearLayout;
    EditText Answer_EditText;
    MaterialButton Yes_Button, No_Button, Maybe_Button, Submit_Button;
    int char_count = 0;
    int question_No = 1;
    int yes_count = 0;
    int no_count = 0;
    int maybe_count = 0;

    //personal question/answer section
    String Ques1_personalQues_AGE = "What would be your age (in years)??"; //question_No = 1
    int Ans1_personalQues_AGE_answer;
    String Ques2_personalQues_WEIGHT = "What would be your weight (in kg)??"; //question_No = 2
    Double Ans2_personalQues_WEIGHT_answer;
    String Ques3_personalQues_HEIGHT = "What would be your height (in meter)??"; //question_No = 3
    Double Ans3_personalQues_HEIGHT_answer;


    //Symptoms related questions
    String Ques4_symptomsQues_URINATION = "Are you experiencing FREQUENT URINATION ??"; //question_No = 4
    String Ans4_symptomsQues_URINATION_answer;
    String Ques5_symptomsQues_APPETITE = "Are you experiencing an increase in your APPETITE and THIRST ??"; //question_No = 5
    String Ans5_symptomsQues_APPETITE_answer;
    String Ques6_symptomsQues_TIREDNESS = "Are you experiencing frequent TIREDNESS or WEAKNESS in body ??"; //question_No = 6
    String Ans6_symptomsQues_TIREDNESS_answer;
    String Ques7_symptomsQues_DARKPATCHES = "Are you experiencing DARK PATCHES on YOUR NECK / ARMPITS ??"; //question_No = 7
    String Ans7_symptomsQues_DARKPATCHES_answer;
    String Ques8_symptomsQues_WEIGHTLOSS = "Are you experiencing UNEXPECTED WEIGHT LOSS ??"; //question_No = 8
    String Ans8_symptomsQues_WEIGHTLOSS_answer;
    String Ques9_symptomsQues_BLURRY_VISION = "Are you suffering from BLURRY VISION ??"; //question_No = 9
    String Ans9_symptomsQues_BLURRY_VISION_answer;


    //Family History Question/Answers
    String Ques10_familyHistoryQuestion_GRANDFATHER = "Did your GRANDFATHER have or had suffered from Type 1 or Type 2 diabetes ??"; //question_No = 10
    String Ans10_familyHistoryQuestion_GRANDFATHER_answer;
    String Ques11_familyHistoryQuestion_GRANDMOTHER = "Did your GRANDMOTHER have or had suffered from Type 1 or Type 2 diabetes ??"; //question_No = 11
    String Ans11_familyHistoryQuestion_GRANDMOTHER_answer;
    String Ques12_familyHistoryQuestion_FATHER = "Did your FATHER have or had suffered from Type 1 or Type 2 diabetes ??"; //question_No = 12
    String Ans12_familyHistoryQuestion_FATHER_answer;
    String Ques13_familyHistoryQuestion_MOTHER = "Did your MOTHER have or had suffered from Type 1 or Type 2 diabetes ??"; //question_No = 13
    String Ans13_familyHistoryQuestion_MOTHER_answer;


    //Reports related questions
    String Ques14_reportsQues_HBA1C_Test_Done = "Have you taken the HBA1C test in past 2-3 months ??"; //question_No = 14
    String Ans14_reportsQues_HBA1C_Test_Done_answer;
    String Ques15_reportsQues_HBA1C_Result = "Please provide the test result for that : "; //question_No = 15
    float Ans15_reportsQues_HBA1C_Result_answer;

    String Ques16_reportsQues_BloodSugar_Test_Done = "Have you taken the BLOOD SUGAR test in past 2-3 months ??"; //question_No = 16
    String Ans16_reportsQues_BloodSugar_Test_Done_answer;
    String Ques17_reportsQues_BloodSugar_Test_Fasting_Result = "Please provide the test result for FASTING BLOOD SUGAR : "; //question_No = 17
    float Ans17_reportsQues_BloodSugar_Test_Fasting_Result_answer;
    String Ques18_reportsQues_BloodSugar_Test_PP_Result = "Please provide the test result for PP BLOOD SUGAR : "; //question_No = 18
    float Ans18_reportsQues_BloodSugar_Test_PP_Result_answer;


    String Yellow = "#fee440";
    String Green = "#2c6e49";

    String user_id;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_risk_analysis_page);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Diabetes Risk Analysis");

        QuestionBox_TextView = findViewById(R.id.QuestionBox_TextView);
        QuestionBox_LinearLayout = findViewById(R.id.QuestionBox_LinearLayout);
        Question_Answer_LinearLayout = findViewById(R.id.Question_Answer_LinearLayout);
        Answer_EditText = findViewById(R.id.Answer_EditText);
        Yes_Button = findViewById(R.id.Yes_Button);
        No_Button = findViewById(R.id.No_Button);
        Maybe_Button = findViewById(R.id.Maybe_Button);
        Submit_Button = findViewById(R.id.Submit_Button);
        BMI_TextView = findViewById(R.id.BMI_TextView);
        Symptoms_and_FamilyHistory_TextView = findViewById(R.id.Symptoms_and_FamilyHistory_TextView);
        Reports_Result_TextView = findViewById(R.id.Reports_Result_TextView);



        Animation animation1 = AnimationUtils.loadAnimation(this, com.blogspot.atifsoftwares.animatoolib.R.anim.animate_diagonal_right_enter);
        QuestionBox_LinearLayout.startAnimation(animation1);
        QuestionBox_LinearLayout.setBackgroundDrawable(getDrawable(R.drawable.text_background));

        Question_1();

        Yes_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (question_No == 4)
                {
                    Ans4_symptomsQues_URINATION_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_5();
                }
                else if (question_No == 5)
                {
                    Ans5_symptomsQues_APPETITE_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_6();
                }
                else if (question_No == 6)
                {
                    Ans6_symptomsQues_TIREDNESS_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_7();
                }
                else if (question_No == 7)
                {
                    Ans7_symptomsQues_DARKPATCHES_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_8();
                }
                else if (question_No == 8)
                {
                    Ans8_symptomsQues_WEIGHTLOSS_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_9();
                }
                else if (question_No == 9)
                {
                    Ans9_symptomsQues_BLURRY_VISION_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_10();
                }
                else if (question_No == 10)
                {
                    Ans10_familyHistoryQuestion_GRANDFATHER_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_11();
                }
                else if (question_No == 11)
                {
                    Ans11_familyHistoryQuestion_GRANDMOTHER_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_12();
                }
                else if (question_No == 12)
                {
                    Ans12_familyHistoryQuestion_FATHER_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_13();
                }
                else if (question_No == 13)
                {
                    Ans13_familyHistoryQuestion_MOTHER_answer = "Yes";
                    question_No++;
                    yes_count++;
                    Question_14();
                }
                else if (question_No == 14)
                {
                    Ans14_reportsQues_HBA1C_Test_Done_answer = "Yes";
                    question_No++;
                    Question_15();
                }
                else if (question_No == 16)
                {
                    Ans16_reportsQues_BloodSugar_Test_Done_answer = "Yes";
                    question_No++;
                    Question_17();
                }
            }
        });


        No_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (question_No == 4)
                {
                    Ans4_symptomsQues_URINATION_answer = "No";
                    question_No++;
                    no_count++;
                    Question_5();
                }
                else if (question_No == 5)
                {
                    Ans5_symptomsQues_APPETITE_answer = "No";
                    question_No++;
                    no_count++;
                    Question_6();
                }
                else if (question_No == 6)
                {
                    Ans6_symptomsQues_TIREDNESS_answer = "No";
                    question_No++;
                    no_count++;
                    Question_7();
                }
                else if (question_No == 7)
                {
                    Ans7_symptomsQues_DARKPATCHES_answer = "No";
                    question_No++;
                    no_count++;
                    Question_8();
                }
                else if (question_No == 8)
                {
                    Ans8_symptomsQues_WEIGHTLOSS_answer = "No";
                    question_No++;
                    no_count++;
                    Question_9();
                }
                else if (question_No == 9)
                {
                    Ans9_symptomsQues_BLURRY_VISION_answer = "No";
                    question_No++;
                    no_count++;
                    Question_10();
                }
                else if (question_No == 10)
                {
                    Ans10_familyHistoryQuestion_GRANDFATHER_answer = "No";
                    question_No++;
                    no_count++;
                    Question_11();
                }
                else if (question_No == 11)
                {
                    Ans11_familyHistoryQuestion_GRANDMOTHER_answer = "No";
                    question_No++;
                    no_count++;
                    Question_12();
                }
                else if (question_No == 12)
                {
                    Ans12_familyHistoryQuestion_FATHER_answer = "No";
                    question_No++;
                    no_count++;
                    Question_13();
                }
                else if (question_No == 13)
                {
                    Ans13_familyHistoryQuestion_MOTHER_answer = "No";
                    question_No++;
                    no_count++;
                    Question_14();
                }
                else if (question_No == 14)
                {
                    Ans14_reportsQues_HBA1C_Test_Done_answer = "No";
                    question_No++;
                    question_No++;
                    Question_16();
                }
                else if (question_No == 16)
                {
                    Ans16_reportsQues_BloodSugar_Test_Done_answer = "No";
                    Result();
                }
            }
        });

        Maybe_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (question_No == 4)
                {
                    Ans4_symptomsQues_URINATION_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_5();
                }
                else if (question_No == 5)
                {
                    Ans5_symptomsQues_APPETITE_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_6();
                }
                else if (question_No == 6)
                {
                    Ans6_symptomsQues_TIREDNESS_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_7();
                }
                else if (question_No == 7)
                {
                    Ans7_symptomsQues_DARKPATCHES_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_8();
                }
                else if (question_No == 8)
                {
                    Ans8_symptomsQues_WEIGHTLOSS_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_9();
                }
                else if (question_No == 9)
                {
                    Ans9_symptomsQues_BLURRY_VISION_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_10();
                }
                else if (question_No == 10)
                {
                    Ans10_familyHistoryQuestion_GRANDFATHER_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_11();
                }
                else if (question_No == 11)
                {
                    Ans11_familyHistoryQuestion_GRANDMOTHER_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_12();
                }
                else if (question_No == 12)
                {
                    Ans12_familyHistoryQuestion_FATHER_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_13();
                }
                else if (question_No == 13)
                {
                    Ans13_familyHistoryQuestion_MOTHER_answer = "May Be";
                    question_No++;
                    maybe_count++;
                    Question_14();
                }
            }
        });

        Submit_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(question_No == 1)
                {
                    String input = Answer_EditText.getText().toString();
                    if(input.isEmpty())
                    {
                        Toast.makeText(Diabetes_Risk_Analysis_Page.this, "Field Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Ans1_personalQues_AGE_answer = Integer.parseInt(Answer_EditText.getText().toString());
                        question_No++;
                        Question_2();
                    }
                }


                else if (question_No == 2)
                {
                    String input = Answer_EditText.getText().toString();
                    if(input.isEmpty())
                    {
                        Toast.makeText(Diabetes_Risk_Analysis_Page.this, "Field Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Ans2_personalQues_WEIGHT_answer = Double.parseDouble(Answer_EditText.getText().toString());
                        question_No++;
                        Question_3();
                    }
                }


                else if (question_No == 3)
                {
                    String input = Answer_EditText.getText().toString();
                    if(input.isEmpty())
                    {
                        Toast.makeText(Diabetes_Risk_Analysis_Page.this, "Field Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Ans3_personalQues_HEIGHT_answer = Double.parseDouble(Answer_EditText.getText().toString());
                        question_No++;
                        Question_4();
                    }
                }


                else if (question_No == 15)
                {String input = Answer_EditText.getText().toString();
                    if(input.isEmpty())
                    {
                        Toast.makeText(Diabetes_Risk_Analysis_Page.this, "Field Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Ans15_reportsQues_HBA1C_Result_answer = Float.parseFloat(Answer_EditText.getText().toString());
                        Result();
                    }
                }


                else if (question_No == 17)
                {
                    String input = Answer_EditText.getText().toString();
                    if(input.isEmpty())
                    {
                        Toast.makeText(Diabetes_Risk_Analysis_Page.this, "Field Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Ans17_reportsQues_BloodSugar_Test_Fasting_Result_answer = Float.parseFloat(Answer_EditText.getText().toString());
                        question_No++;
                        Question_18();
                    }
                }
                else if (question_No == 18)
                {String input = Answer_EditText.getText().toString();
                    if(input.isEmpty())
                    {
                        Toast.makeText(Diabetes_Risk_Analysis_Page.this, "Field Left Empty", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Ans18_reportsQues_BloodSugar_Test_PP_Result_answer = Float.parseFloat(Answer_EditText.getText().toString());
                        Result();
                    }
                }
            }
        });

    }

    private void Question_1()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques1_personalQues_AGE, 1);
    }

    private void Question_2()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques2_personalQues_WEIGHT, 2);
    }

    private void Question_3()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques3_personalQues_HEIGHT, 3);
    }

    private void Question_4()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques4_symptomsQues_URINATION, 4);
    }

    private void Question_5()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques5_symptomsQues_APPETITE, 5);
    }

    private void Question_6()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques6_symptomsQues_TIREDNESS, 6);
    }

    private void Question_7()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques7_symptomsQues_DARKPATCHES, 7);
    }

    private void Question_8()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques8_symptomsQues_WEIGHTLOSS, 8);
    }

    private void Question_9()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques9_symptomsQues_BLURRY_VISION, 9);
    }

    private void Question_10()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques10_familyHistoryQuestion_GRANDFATHER, 10);
    }

    private void Question_11()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques11_familyHistoryQuestion_GRANDMOTHER, 11);
    }

    private void Question_12()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques12_familyHistoryQuestion_FATHER, 12);
    }

    private void Question_13()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques13_familyHistoryQuestion_MOTHER, 13);
    }

    private void Question_14()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques14_reportsQues_HBA1C_Test_Done, 14);
    }

    private void Question_15()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques15_reportsQues_HBA1C_Result, 15);
    }

    private void Question_16()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques16_reportsQues_BloodSugar_Test_Done, 16);
    }

    private void Question_17()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques17_reportsQues_BloodSugar_Test_Fasting_Result, 17);
    }

    private void Question_18()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);

        char_count = 0;
        printText(Ques18_reportsQues_BloodSugar_Test_PP_Result, 18);
    }

    private void Result()
    {
        Answer_EditText.setVisibility(View.GONE);
        Yes_Button.setVisibility(View.GONE);
        No_Button.setVisibility(View.GONE);
        Maybe_Button.setVisibility(View.GONE);
        Submit_Button.setVisibility(View.GONE);
        QuestionBox_LinearLayout.setVisibility(View.GONE);
        QuestionBox_TextView.setVisibility(View.GONE);
        Question_Answer_LinearLayout.setVisibility(View.GONE);

        double BMI = Ans2_personalQues_WEIGHT_answer/(Ans3_personalQues_HEIGHT_answer * Ans3_personalQues_HEIGHT_answer);

        double totalYesCount = yes_count + (maybe_count/2);
        double percentage = (totalYesCount/10) * 100;


        double round_Of_BMI = Math.round(BMI * 100.0)/100.0;
        double round_Of_Percentage = Math.round(percentage * 100.0)/100.0;

        BMI_Result(round_Of_BMI);
        Symptoms_and_FamilyHistory_Result(round_Of_Percentage, Ans1_personalQues_AGE_answer);

        if(Ans15_reportsQues_HBA1C_Result_answer != 0)
        {
            HBA1C_Test_Result();
        }

        if(Ans17_reportsQues_BloodSugar_Test_Fasting_Result_answer != 0 && Ans18_reportsQues_BloodSugar_Test_PP_Result_answer != 0)
        {
            float Fasting_Result = Ans17_reportsQues_BloodSugar_Test_Fasting_Result_answer;
            float PP_Result = Ans18_reportsQues_BloodSugar_Test_PP_Result_answer;
            BloodSugar_Test_Result(Fasting_Result, PP_Result);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        {
            if(user != null)
            {
              user_id = user.getEmail();
            }
        }

        String Age = Integer.toString(Ans1_personalQues_AGE_answer);
        String Weight = Double.toString(Ans2_personalQues_WEIGHT_answer);
        String Height = Double.toString(Ans3_personalQues_HEIGHT_answer);
        String BMI_Result = Double.toString(round_Of_BMI);
        String HBA1C_Result_answer = Float.toString(Ans15_reportsQues_HBA1C_Result_answer);
        String Fasting_Result = Float.toString(Ans17_reportsQues_BloodSugar_Test_Fasting_Result_answer);
        String PP_Result = Float.toString(Ans18_reportsQues_BloodSugar_Test_PP_Result_answer);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(date);

        HashMap<String, String> map = new HashMap<>();
        map.put("Ans1_personalQues_AGE_answer", Age);
        map.put("Ans2_personalQues_WEIGHT_answer", Weight);
        map.put("Ans3_personalQues_HEIGHT_answer", Height);
        map.put("BMI", BMI_Result);
        map.put("Ans4_symptomsQues_URINATION_answer", Ans4_symptomsQues_URINATION_answer);
        map.put("Ans5_symptomsQues_APPETITE_answer", Ans5_symptomsQues_APPETITE_answer);
        map.put("Ans6_symptomsQues_TIREDNESS_answer", Ans6_symptomsQues_TIREDNESS_answer);
        map.put("Ans7_symptomsQues_DARKPATCHES_answer", Ans7_symptomsQues_DARKPATCHES_answer);
        map.put("Ans8_symptomsQues_WEIGHTLOSS_answer", Ans8_symptomsQues_WEIGHTLOSS_answer);
        map.put("Ans9_symptomsQues_BLURRY_VISION_answer", Ans9_symptomsQues_BLURRY_VISION_answer);
        map.put("Ans10_familyHistoryQuestion_GRANDFATHER_answer", Ans10_familyHistoryQuestion_GRANDFATHER_answer);
        map.put("Ans11_familyHistoryQuestion_GRANDMOTHER_answer", Ans11_familyHistoryQuestion_GRANDMOTHER_answer);
        map.put("Ans12_familyHistoryQuestion_FATHER_answer", Ans12_familyHistoryQuestion_FATHER_answer);
        map.put("Ans13_familyHistoryQuestion_MOTHER_answer", Ans13_familyHistoryQuestion_MOTHER_answer);
        map.put("Ans14_reportsQues_HBA1C_Test_Done_answer", Ans14_reportsQues_HBA1C_Test_Done_answer);
        map.put("Ans15_reportsQues_HBA1C_Result_answer", HBA1C_Result_answer);
        map.put("Ans16_reportsQues_BloodSugar_Test_Done_answer", Ans16_reportsQues_BloodSugar_Test_Done_answer);
        map.put("Ans17_reportsQues_BloodSugar_Test_Fasting_Result_answer", Fasting_Result);
        map.put("Ans18_reportsQues_BloodSugar_Test_PP_Result_answer", PP_Result);
        map.put("BMI_TextView",BMI_TextView.getText().toString());
        map.put("Symptoms_and_FamilyHistory_TextView",Symptoms_and_FamilyHistory_TextView.getText().toString());
        map.put("Reports_Result_TextView", Reports_Result_TextView.getText().toString());
        map.put("Date",formattedDate);

        db = FirebaseDatabase.getInstance().getReference("User Diabetes Risk Analysis Report");
        db.child(user_id.replace(".","")).setValue(map);
    }

    private void BMI_Result(double round_Of_BMI)
    {
        BMI_TextView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(Diabetes_Risk_Analysis_Page.this, R.anim.fade_animation);
        if(round_Of_BMI < 18.40)
        {
            BMI_TextView.setTextColor(Color.parseColor(Yellow));
            BMI_TextView.setText("Your BMI is " + round_Of_BMI + ". It indicates you are UNDERWEIGHT");
            BMI_TextView.startAnimation(animation);
        }
        else if (round_Of_BMI >= 18.50 && round_Of_BMI <= 25.99)
        {
            BMI_TextView.setTextColor(Color.parseColor(Green));
            BMI_TextView.setText("Your BMI is " + round_Of_BMI + ". It indicates you have a NORMAL BODY WEIGHT");
            BMI_TextView.startAnimation(animation);
        }
        else if (round_Of_BMI > 25.99)
        {
            BMI_TextView.setTextColor(Color.RED);
            BMI_TextView.setText("Your BMI is " + round_Of_BMI + ". It indicates you are OVERWEIGHT");
            BMI_TextView.startAnimation(animation);
        }
    }

    private void Symptoms_and_FamilyHistory_Result(double round_Of_Percentage,int age)
    {
        Symptoms_and_FamilyHistory_TextView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(Diabetes_Risk_Analysis_Page.this, R.anim.fade_animation);

        if(age <= 35 && round_Of_Percentage <= 50.00)
        {
            Symptoms_and_FamilyHistory_TextView.setTextColor(Color.parseColor(Green));
            Symptoms_and_FamilyHistory_TextView.setText("Looking at your current symptoms and family history of diabetes you have " + round_Of_Percentage + "% chances of having the risk of Type 1 Diabetes");
            Symptoms_and_FamilyHistory_TextView.startAnimation(animation);
        }

        else if(age <= 35 && round_Of_Percentage > 50.00 && round_Of_Percentage <= 75.00)
        {
            Symptoms_and_FamilyHistory_TextView.setTextColor(Color.parseColor(Yellow));
            Symptoms_and_FamilyHistory_TextView.setText("Looking at your current symptoms and family history of diabetes you have " + round_Of_Percentage + "% chances of having the risk of Type 1 Diabetes");
            Symptoms_and_FamilyHistory_TextView.startAnimation(animation);
        }
        else if(age <= 35 && round_Of_Percentage > 75.00 && round_Of_Percentage <= 100.00)
        {
            Symptoms_and_FamilyHistory_TextView.setTextColor(Color.RED);
            Symptoms_and_FamilyHistory_TextView.setText("Looking at your current symptoms and family history of diabetes you have " + round_Of_Percentage + "% chances of having the risk of Type 1 Diabetes");
            Symptoms_and_FamilyHistory_TextView.startAnimation(animation);
        }

        else if(age > 35 && round_Of_Percentage <= 50.00)
        {
            Symptoms_and_FamilyHistory_TextView.setTextColor(Color.parseColor(Green));
            Symptoms_and_FamilyHistory_TextView.setText("Looking at your current symptoms and family history of diabetes you have " + round_Of_Percentage + "% chances of having the risk of Type 2 Diabetes");
            Symptoms_and_FamilyHistory_TextView.startAnimation(animation);
        }

        else if(age > 35 && round_Of_Percentage > 50.00 && round_Of_Percentage <= 75.00)
        {
            Symptoms_and_FamilyHistory_TextView.setTextColor(Color.parseColor(Yellow));
            Symptoms_and_FamilyHistory_TextView.setText("Looking at your current symptoms and family history of diabetes you have " + round_Of_Percentage + "% chances of having the risk of Type 2 Diabetes");
            Symptoms_and_FamilyHistory_TextView.startAnimation(animation);
        }

        else if(age > 35 && round_Of_Percentage > 75.00 && round_Of_Percentage <= 100.00)
        {
            Symptoms_and_FamilyHistory_TextView.setTextColor(Color.RED);
            Symptoms_and_FamilyHistory_TextView.setText("Looking at your current symptoms and family history of diabetes you have " + round_Of_Percentage + "% chances of having the risk of Type 2 Diabetes");
            Symptoms_and_FamilyHistory_TextView.startAnimation(animation);
        }
    }

    private  void HBA1C_Test_Result()
    {
        Reports_Result_TextView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(Diabetes_Risk_Analysis_Page.this, R.anim.fade_animation);

        if(Ans15_reportsQues_HBA1C_Result_answer <= 4.7 || Ans15_reportsQues_HBA1C_Result_answer > 10.0)
        {
            Reports_Result_TextView.setTextColor(Color.RED);
            Reports_Result_TextView.setText("Your HBA1C Report Result is ABNORMAL!!");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if(Ans15_reportsQues_HBA1C_Result_answer >= 4.8 && Ans15_reportsQues_HBA1C_Result_answer <= 5.8)
        {
            Reports_Result_TextView.setTextColor(Color.parseColor(Green));
            Reports_Result_TextView.setText("Your HBA1C Report Result is NORMAL!!");
            Reports_Result_TextView.startAnimation(animation);
        }
        else if (Ans15_reportsQues_HBA1C_Result_answer > 5.8 && Ans15_reportsQues_HBA1C_Result_answer < 6.4)
        {
            Reports_Result_TextView.setTextColor(Color.parseColor(Yellow));
            Reports_Result_TextView.setText("Your HBA1C Report Result is PREDIABETIC!!");
            Reports_Result_TextView.startAnimation(animation);
        }
        else if (Ans15_reportsQues_HBA1C_Result_answer > 6.4)
        {
            Reports_Result_TextView.setTextColor(Color.RED);
            Reports_Result_TextView.setText("Your HBA1C Report Result is DIABETIC!!");
            Reports_Result_TextView.startAnimation(animation);
        }
    }

    private  void BloodSugar_Test_Result(float fasting_analysis_result, float pp_analysis_result)
    {
        Reports_Result_TextView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(Diabetes_Risk_Analysis_Page.this, R.anim.fade_animation);

        if(fasting_analysis_result < 70 && pp_analysis_result < 140) // when both fasting and pp are low
        {
            Reports_Result_TextView.setTextColor(Color.RED);
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you are suffering from low blood sugar level and may have chances of HYPOGLYCEMIA.");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if (fasting_analysis_result < 70 && pp_analysis_result >= 140) // when just fasting is low
        {
            Reports_Result_TextView.setTextColor(Color.RED);
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you are suffering from low blood sugar level and may have chances of HYPOGLYCEMIA.");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if (fasting_analysis_result >= 70 && pp_analysis_result < 140) // when just pp is low
        {
            Reports_Result_TextView.setTextColor(Color.RED);
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you are suffering from low blood sugar level and may have chances of HYPOGLYCEMIA.");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if (fasting_analysis_result >= 70 && fasting_analysis_result <= 100 && pp_analysis_result >= 140 && pp_analysis_result <= 170) // when fasting and pp both are normal
        {
            Reports_Result_TextView.setTextColor(Color.parseColor(Green));
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you have a normal blood sugar level.");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if(fasting_analysis_result > 100 && fasting_analysis_result <= 125 && pp_analysis_result >= 140 && pp_analysis_result <= 170) // when fasting is prediabetic and pp is normal
        {
            Reports_Result_TextView.setTextColor(Color.parseColor(Yellow));
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you are having a border line blood sugar level and you are Prediabetic");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if(fasting_analysis_result >= 70 && fasting_analysis_result <= 100 && pp_analysis_result > 170 && pp_analysis_result <= 199) // when fasting is normal and pp is prediabetic
        {
            Reports_Result_TextView.setTextColor(Color.parseColor(Yellow));
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you are having a border line blood sugar level and you are Prediabetic");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if(fasting_analysis_result > 100 && fasting_analysis_result <= 125 && pp_analysis_result > 170 && pp_analysis_result <= 199) // when fasting and pp is prediabetic
        {
            Reports_Result_TextView.setTextColor(Color.parseColor(Yellow));
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you are having a border line blood sugar level and you are Prediabetic");
            Reports_Result_TextView.startAnimation(animation);
        }

        else if(fasting_analysis_result > 125 || pp_analysis_result > 199)
        {
            Reports_Result_TextView.setTextColor(Color.RED);
            Reports_Result_TextView.setText("Your Blood Sugar Report indicates you are having high blood sugar level and may have chances of HYPERGLYCEMIA");
            Reports_Result_TextView.startAnimation(animation);
        }

        else
        {
            Toast.makeText(this, "Maybe some uncertail result have been passed by you please refer to your readings!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void printText(String text, int ques_no)
    {
        if(char_count <= text.length())
        {
            String fetchText = text.substring(0, char_count);
            QuestionBox_TextView.setText(fetchText);

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    char_count++;
                    printText(text, ques_no);
                }
            }, 50);
        }

        else
        {
            if(ques_no == 1 || ques_no == 2 || ques_no == 3 || ques_no == 15 || ques_no == 17 || ques_no == 18)
            {
                Answer_EditText.setText("");
                Answer_EditText.setVisibility(View.VISIBLE);
                Submit_Button.setVisibility(View.VISIBLE);
            }

            else if(ques_no >= 4 && ques_no <= 13)
            {
                Yes_Button.setVisibility(View.VISIBLE);
                No_Button.setVisibility(View.VISIBLE);
                Maybe_Button.setVisibility(View.VISIBLE);
            }

            else if(ques_no == 14 || ques_no == 16)
            {
                Yes_Button.setVisibility(View.VISIBLE);
                No_Button.setVisibility(View.VISIBLE);
                Maybe_Button.setVisibility(View.GONE);
            }

            else if(ques_no == 0)
            {
                Yes_Button.setVisibility(View.GONE);
                No_Button.setVisibility(View.GONE);
                Maybe_Button.setVisibility(View.GONE);
                Answer_EditText.setVisibility(View.GONE);
                Submit_Button.setVisibility(View.GONE);
            }
        }
    }
}