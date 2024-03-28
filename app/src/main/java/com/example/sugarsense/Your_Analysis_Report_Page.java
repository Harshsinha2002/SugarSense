package com.example.sugarsense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Your_Analysis_Report_Page extends AppCompatActivity {

    TextView Date;
    TextView Patient_Name;
    TextView Patient_Age;
    TextView Patient_Height;
    TextView Patient_Weight;
    TextView Answer1, Answer2, Answer3, Answer4, Answer5, Answer6, Answer7, Answer8, Answer9, Answer10, Answer11, Answer12, Answer13, Answer14, Answer15;
    TextView Conclusion_Result_1, Conclusion_Result_2, Conclusion_Result_3;

    DatabaseReference db, db_profile_name;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_analysis_report_page);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Your Report");

        Date = findViewById(R.id.Date);
        Patient_Name = findViewById(R.id.Patient_Name);
        Patient_Age = findViewById(R.id.Patient_Age);
        Patient_Height = findViewById(R.id.Patient_Height);
        Patient_Weight = findViewById(R.id.Patient_Weight);


        Answer1 = findViewById(R.id.Answer1);
        Answer2 = findViewById(R.id.Answer2);
        Answer3 = findViewById(R.id.Answer3);
        Answer4 = findViewById(R.id.Answer4);
        Answer5 = findViewById(R.id.Answer5);
        Answer6 = findViewById(R.id.Answer6);
        Answer7 = findViewById(R.id.Answer7);
        Answer8 = findViewById(R.id.Answer8);
        Answer9 = findViewById(R.id.Answer9);
        Answer10 = findViewById(R.id.Answer10);
        Answer11 = findViewById(R.id.Answer11);
        Answer12 = findViewById(R.id.Answer12);
        Answer13 = findViewById(R.id.Answer13);
        Answer14 = findViewById(R.id.Answer14);
        Answer15 = findViewById(R.id.Answer15);


        Conclusion_Result_1 = findViewById(R.id.Conclusion_Result_1);
        Conclusion_Result_2 = findViewById(R.id.Conclusion_Result_2);
        Conclusion_Result_3 = findViewById(R.id.Conclusion_Result_3);

        getDataFromDatabase();

    }

    private void getDataFromDatabase()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            user_id = user.getEmail();
        }

        db_profile_name = FirebaseDatabase.getInstance().getReference("User Profile Name");
        db_profile_name.child(user_id.replace(".", "")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();
                        Patient_Name.setText("Patient Name : " + String.valueOf(dataSnapshot.getValue()));
                    }
                }
            }
        });

        db = FirebaseDatabase.getInstance().getReference("User Diabetes Risk Analysis Report");
        db.child(user_id.replace(".", "")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();

                        Patient_Age.setText("Age : " + String.valueOf(dataSnapshot.child("Ans1_personalQues_AGE_answer").getValue()) + "Years");
                        Patient_Weight.setText("Weight : " + String.valueOf(dataSnapshot.child("Ans2_personalQues_WEIGHT_answer").getValue()) + "Kg");
                        Patient_Height.setText("Height : " + String.valueOf(dataSnapshot.child("Ans3_personalQues_HEIGHT_answer").getValue()) + "Meter");


                        Answer1.setText(String.valueOf(dataSnapshot.child("Ans4_symptomsQues_URINATION_answer").getValue()));
                        Answer2.setText(String.valueOf(dataSnapshot.child("Ans5_symptomsQues_APPETITE_answer").getValue()));
                        Answer3.setText(String.valueOf(dataSnapshot.child("Ans6_symptomsQues_TIREDNESS_answer").getValue()));
                        Answer4.setText(String.valueOf(dataSnapshot.child("Ans7_symptomsQues_DARKPATCHES_answer").getValue()));
                        Answer5.setText(String.valueOf(dataSnapshot.child("Ans8_symptomsQues_WEIGHTLOSS_answer").getValue()));
                        Answer6.setText(String.valueOf(dataSnapshot.child("Ans9_symptomsQues_BLURRY_VISION_answer").getValue()));
                        Answer7.setText(String.valueOf(dataSnapshot.child("Ans10_familyHistoryQuestion_GRANDFATHER_answer").getValue()));
                        Answer8.setText(String.valueOf(dataSnapshot.child("Ans11_familyHistoryQuestion_GRANDMOTHER_answer").getValue()));
                        Answer9.setText(String.valueOf(dataSnapshot.child("Ans12_familyHistoryQuestion_FATHER_answer").getValue()));
                        Answer10.setText(String.valueOf(dataSnapshot.child("Ans13_familyHistoryQuestion_MOTHER_answer").getValue()));
                        Answer11.setText(String.valueOf(dataSnapshot.child("Ans14_reportsQues_HBA1C_Test_Done_answer").getValue()));
                        Answer12.setText(String.valueOf(dataSnapshot.child("Ans15_reportsQues_HBA1C_Result_answer").getValue()));
                        Answer13.setText(String.valueOf(dataSnapshot.child("Ans16_reportsQues_BloodSugar_Test_Done_answer").getValue()));
                        Answer14.setText(String.valueOf(dataSnapshot.child("Ans17_reportsQues_BloodSugar_Test_Fasting_Result_answer").getValue()));
                        Answer15.setText(String.valueOf(dataSnapshot.child("Ans18_reportsQues_BloodSugar_Test_PP_Result_answer").getValue()));


                        Conclusion_Result_1.setText(String.valueOf(dataSnapshot.child("BMI_TextView").getValue()));
                        Conclusion_Result_2.setText(String.valueOf(dataSnapshot.child("Symptoms_and_FamilyHistory_TextView").getValue()));
                        Conclusion_Result_3.setText(String.valueOf(dataSnapshot.child("Reports_Result_TextView").getValue()));
                        Date.setText("Date : " + String.valueOf(dataSnapshot.child("Date").getValue()));
                    }
                }
            }
        });
    }
}