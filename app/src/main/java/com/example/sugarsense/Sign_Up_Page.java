package com.example.sugarsense;

import static android.content.ContentValues.TAG;

import static com.example.sugarsense.R.color.blue;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Sign_Up_Page extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String email,password;
    String full_name;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mAuth = FirebaseAuth.getInstance();
        TextInputEditText Email_editText = findViewById(R.id.Email_editText);
        TextInputEditText Password_editText = findViewById(R.id.Password_editText);
        TextInputEditText Full_Name_edittxt = findViewById(R.id.Full_Name_edittxt);
        Button CreateAccount_btn = findViewById(R.id.CreateAccount_btn);

        getWindow().setStatusBarColor(ContextCompat.getColor(Sign_Up_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitleColor(R.color.black);

        CreateAccount_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = Email_editText.getText().toString().trim();
                password = Password_editText.getText().toString().trim();
                full_name = Full_Name_edittxt.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Email_editText.setError("");
                }

                else if(TextUtils.isEmpty(password))
                {
                    Password_editText.setError("");
                }

                else if(TextUtils.isEmpty(full_name))
                {
                    Full_Name_edittxt.setError("");
                }

                else if(password.length()<6)
                {
                    Password_editText.setError("");
                    Toast.makeText(Sign_Up_Page.this, "Password must contain 6 letters!!", Toast.LENGTH_LONG).show();
                }

                else
                {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        db = FirebaseDatabase.getInstance().getReference("User Profile Name");
                                        db.child(email.replace(".","")).setValue(full_name);

                                        Intent intent = new Intent(Sign_Up_Page.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else
                                    {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Sign_Up_Page.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }
}