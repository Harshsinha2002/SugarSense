package com.example.sugarsense;

import static com.example.sugarsense.R.color.blue;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password_Page extends AppCompatActivity {

    String email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);


        TextInputEditText Email_editText = findViewById(R.id.Email_editText);
        Button Submit_btn = findViewById(R.id.Submit_btn);
        auth = FirebaseAuth.getInstance();

        getWindow().setStatusBarColor(ContextCompat.getColor(Forgot_Password_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitleColor(R.color.black);

        Submit_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = Email_editText.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(Forgot_Password_Page.this, "Enter Valid Email Address!!", Toast.LENGTH_SHORT).show();
                    Email_editText.setError("REQUIRED!");

                }
                else
                {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Forgot_Password_Page.this, "CHECK YOUR EMAIL", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Forgot_Password_Page.this, Login_Page.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Forgot_Password_Page.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}