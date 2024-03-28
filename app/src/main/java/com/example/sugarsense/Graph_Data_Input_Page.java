package com.example.sugarsense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Graph_Data_Input_Page extends AppCompatActivity {

    TextInputEditText Fasting_Test1;
    TextInputEditText Fasting_Test2;
    TextInputEditText Fasting_Test3;
    TextInputEditText Fasting_Test4;
    TextInputEditText Fasting_Test5;
    TextInputEditText Fasting_Test6;
    TextInputEditText Fasting_Test7;
    TextInputEditText PP_Test1;
    TextInputEditText PP_Test2;
    TextInputEditText PP_Test3;
    TextInputEditText PP_Test4;
    TextInputEditText PP_Test5;
    TextInputEditText PP_Test6;
    TextInputEditText PP_Test7;

    DatabaseReference db;

    String User_id;

    MaterialButton Save_Data_btn;
    MaterialButton Delete_Data_btn;
    MaterialButton Generate_Graph_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_data_input_page);

        getWindow().setStatusBarColor(ContextCompat.getColor(Graph_Data_Input_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Graph Analysis");

        Fasting_Test1 = findViewById(R.id.Fasting_Test1);
        Fasting_Test2 = findViewById(R.id.Fasting_Test2);
        Fasting_Test3 = findViewById(R.id.Fasting_Test3);
        Fasting_Test4 = findViewById(R.id.Fasting_Test4);
        Fasting_Test5 = findViewById(R.id.Fasting_Test5);
        Fasting_Test6 = findViewById(R.id.Fasting_Test6);
        Fasting_Test7 = findViewById(R.id.Fasting_Test7);

        PP_Test1 = findViewById(R.id.PP_Test1);
        PP_Test2 = findViewById(R.id.PP_Test2);
        PP_Test3 = findViewById(R.id.PP_Test3);
        PP_Test4 = findViewById(R.id.PP_Test4);
        PP_Test5 = findViewById(R.id.PP_Test5);
        PP_Test6 = findViewById(R.id.PP_Test6);
        PP_Test7 = findViewById(R.id.PP_Test7);

        Save_Data_btn = findViewById(R.id.Save_Data_btn);
        Delete_Data_btn = findViewById(R.id.Delete_Data_btn);
        Generate_Graph_btn = findViewById(R.id.Generate_Graph_btn);

        checkPreviousEnteredData();

        Save_Data_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Graph_Data_Input_Page.this, "Data Saved and Updated!!",Toast.LENGTH_SHORT).show();
                saveDataToDatabase();
            }
        });

        Delete_Data_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Dialog dialog = new Dialog(Graph_Data_Input_Page.this);
                dialog.setContentView(R.layout.delete_data_dialog_box);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_box_background));
                dialog.setCancelable(true);
                dialog.show();

                Button Dialog_Cancelbtn, Dialog_Deletebtn;
                Dialog_Cancelbtn = dialog.findViewById(R.id.Dialog_Cancelbtn);
                Dialog_Deletebtn = dialog.findViewById(R.id.Dialog_Deletebtn);

                Dialog_Cancelbtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });

                Dialog_Deletebtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(Graph_Data_Input_Page.this, "Data Deleted Sucessfully!!", Toast.LENGTH_SHORT).show();
                        db = FirebaseDatabase.getInstance().getReference("User Graph Analysis Data");
                        db.child(User_id.replace(".","")).removeValue();
                        dialog.dismiss();

                        Fasting_Test1.setText("");
                        Fasting_Test2.setText("");
                        Fasting_Test3.setText("");
                        Fasting_Test4.setText("");
                        Fasting_Test5.setText("");
                        Fasting_Test6.setText("");
                        Fasting_Test7.setText("");

                        PP_Test1.setText("");
                        PP_Test2.setText("");
                        PP_Test3.setText("");
                        PP_Test4.setText("");
                        PP_Test5.setText("");
                        PP_Test6.setText("");
                        PP_Test7.setText("");
                    }
                });
            }
        });

        Generate_Graph_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();

                if (Fasting_Test1.getText().toString().isEmpty() || Fasting_Test2.getText().toString().isEmpty() || Fasting_Test3.getText().toString().isEmpty() || Fasting_Test4.getText().toString().isEmpty() || Fasting_Test5.getText().toString().isEmpty() || Fasting_Test6.getText().toString().isEmpty() || Fasting_Test7.getText().toString().isEmpty() || PP_Test1.getText().toString().isEmpty() || PP_Test2.getText().toString().isEmpty() || PP_Test3.getText().toString().isEmpty() || PP_Test4.getText().toString().isEmpty() || PP_Test5.getText().toString().isEmpty() || PP_Test6.getText().toString().isEmpty() || PP_Test7.getText().toString().isEmpty()) {
                    Toast.makeText(Graph_Data_Input_Page.this, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Graph_Data_Input_Page.this, Graph_Generation_Page.class);
                    intent.putExtra("Fasting_Test1", Integer.parseInt(Fasting_Test1.getText().toString()));
                    intent.putExtra("Fasting_Test2", Integer.parseInt(Fasting_Test2.getText().toString()));
                    intent.putExtra("Fasting_Test3", Integer.parseInt(Fasting_Test3.getText().toString()));
                    intent.putExtra("Fasting_Test4", Integer.parseInt(Fasting_Test4.getText().toString()));
                    intent.putExtra("Fasting_Test5", Integer.parseInt(Fasting_Test5.getText().toString()));
                    intent.putExtra("Fasting_Test6", Integer.parseInt(Fasting_Test6.getText().toString()));
                    intent.putExtra("Fasting_Test7", Integer.parseInt(Fasting_Test7.getText().toString()));


                    intent.putExtra("PP_Test1", Integer.parseInt(PP_Test1.getText().toString()));
                    intent.putExtra("PP_Test2", Integer.parseInt(PP_Test2.getText().toString()));
                    intent.putExtra("PP_Test3", Integer.parseInt(PP_Test3.getText().toString()));
                    intent.putExtra("PP_Test4", Integer.parseInt(PP_Test4.getText().toString()));
                    intent.putExtra("PP_Test5", Integer.parseInt(PP_Test5.getText().toString()));
                    intent.putExtra("PP_Test6", Integer.parseInt(PP_Test6.getText().toString()));
                    intent.putExtra("PP_Test7", Integer.parseInt(PP_Test7.getText().toString()));

                    startActivity(intent);
                    Animatoo.INSTANCE.animateFade(Graph_Data_Input_Page.this);
                }
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Graph_Data_Input_Page.this, MainActivity.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateDiagonal(Graph_Data_Input_Page.this);
        finish();
    }

    public void checkPreviousEnteredData()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            User_id = user.getEmail();
        }
        db = FirebaseDatabase.getInstance().getReference("User Graph Analysis Data");
        db.child(User_id.replace(".","")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();
                        String Fasting_Test1_Data = String.valueOf(dataSnapshot.child("Fasting_Test1").getValue());
                        String Fasting_Test2_Data = String.valueOf(dataSnapshot.child("Fasting_Test2").getValue());
                        String Fasting_Test3_Data = String.valueOf(dataSnapshot.child("Fasting_Test3").getValue());
                        String Fasting_Test4_Data = String.valueOf(dataSnapshot.child("Fasting_Test4").getValue());
                        String Fasting_Test5_Data = String.valueOf(dataSnapshot.child("Fasting_Test5").getValue());
                        String Fasting_Test6_Data = String.valueOf(dataSnapshot.child("Fasting_Test6").getValue());
                        String Fasting_Test7_Data = String.valueOf(dataSnapshot.child("Fasting_Test7").getValue());

                        String PP_Test1_Data = String.valueOf(dataSnapshot.child("PP_Test1").getValue());
                        String PP_Test2_Data = String.valueOf(dataSnapshot.child("PP_Test2").getValue());
                        String PP_Test3_Data = String.valueOf(dataSnapshot.child("PP_Test3").getValue());
                        String PP_Test4_Data = String.valueOf(dataSnapshot.child("PP_Test4").getValue());
                        String PP_Test5_Data = String.valueOf(dataSnapshot.child("PP_Test5").getValue());
                        String PP_Test6_Data = String.valueOf(dataSnapshot.child("PP_Test6").getValue());
                        String PP_Test7_Data = String.valueOf(dataSnapshot.child("PP_Test7").getValue());

                        Fasting_Test1.setText(Fasting_Test1_Data);
                        Fasting_Test2.setText(Fasting_Test2_Data);
                        Fasting_Test3.setText(Fasting_Test3_Data);
                        Fasting_Test4.setText(Fasting_Test4_Data);
                        Fasting_Test5.setText(Fasting_Test5_Data);
                        Fasting_Test6.setText(Fasting_Test6_Data);
                        Fasting_Test7.setText(Fasting_Test7_Data);

                        PP_Test1.setText(PP_Test1_Data);
                        PP_Test2.setText(PP_Test2_Data);
                        PP_Test3.setText(PP_Test3_Data);
                        PP_Test4.setText(PP_Test4_Data);
                        PP_Test5.setText(PP_Test5_Data);
                        PP_Test6.setText(PP_Test6_Data);
                        PP_Test7.setText(PP_Test7_Data);
                    }
                }
            }
        });

    }

    public  void  saveDataToDatabase()
    {
        String Fasting_Test1_Data = Fasting_Test1.getText().toString();
        String Fasting_Test2_Data = Fasting_Test2.getText().toString();
        String Fasting_Test3_Data = Fasting_Test3.getText().toString();
        String Fasting_Test4_Data = Fasting_Test4.getText().toString();
        String Fasting_Test5_Data = Fasting_Test5.getText().toString();
        String Fasting_Test6_Data = Fasting_Test6.getText().toString();
        String Fasting_Test7_Data = Fasting_Test7.getText().toString();

        String PP_Test1_Data = PP_Test1.getText().toString();
        String PP_Test2_Data = PP_Test2.getText().toString();
        String PP_Test3_Data = PP_Test3.getText().toString();
        String PP_Test4_Data = PP_Test4.getText().toString();
        String PP_Test5_Data = PP_Test5.getText().toString();
        String PP_Test6_Data = PP_Test6.getText().toString();
        String PP_Test7_Data = PP_Test7.getText().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put("Fasting_Test1", Fasting_Test1_Data);
        map.put("Fasting_Test2", Fasting_Test2_Data);
        map.put("Fasting_Test3", Fasting_Test3_Data);
        map.put("Fasting_Test4", Fasting_Test4_Data);
        map.put("Fasting_Test5", Fasting_Test5_Data);
        map.put("Fasting_Test6", Fasting_Test6_Data);
        map.put("Fasting_Test7", Fasting_Test7_Data);


        map.put("PP_Test1", PP_Test1_Data);
        map.put("PP_Test2", PP_Test2_Data);
        map.put("PP_Test3", PP_Test3_Data);
        map.put("PP_Test4", PP_Test4_Data);
        map.put("PP_Test5", PP_Test5_Data);
        map.put("PP_Test6", PP_Test6_Data);
        map.put("PP_Test7", PP_Test7_Data);

        db = FirebaseDatabase.getInstance().getReference("User Graph Analysis Data");
        db.child(User_id.replace(".","")).setValue(map);
    }
}