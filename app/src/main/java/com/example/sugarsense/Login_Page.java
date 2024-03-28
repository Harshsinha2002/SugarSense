package com.example.sugarsense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login_Page extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String email, password;
    int views = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        TextView SignUp_textView = findViewById(R.id.SignUp_textView);
        TextView ForgotPassword_textView = findViewById(R.id.ForgotPassword_textView);
        MaterialButton Login_btn = findViewById(R.id.Login_btn);
        TextInputEditText Email_editText = findViewById(R.id.Email_editText);
        TextInputEditText Password_editText = findViewById(R.id.Password_editText);
        CardView Login_CardView = findViewById(R.id.Login_CardView);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Login_Page.this, R.color.blue));

        Animation animationSlideIn = AnimationUtils.loadAnimation(Login_Page.this, R.anim.slide_in_animation);
        Login_CardView.startAnimation(animationSlideIn);

        performAnimationEmail_editText();

        SignUp_textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Login_Page.this, Sign_Up_Page.class);
                startActivity(intent);
            }
        });

        ForgotPassword_textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Login_Page.this, Forgot_Password_Page.class);
                startActivity(intent);
            }
        });

        Login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = Email_editText.getText().toString().trim();
                password = Password_editText.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Email_editText.setError("");
                }

                else if (TextUtils.isEmpty(password))
                {
                    Password_editText.setError("");
                }

                else
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(Login_Page.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login_Page.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Login_Page.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void performAnimationLogin_CardView()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animationMoveRight = AnimationUtils.loadAnimation(Login_Page.this, R.anim.slide_in_animation);
                CardView Login_CardView = findViewById(R.id.Login_CardView);
                Login_CardView.setVisibility(View.VISIBLE);
                Login_CardView.startAnimation(animationMoveRight);
                performAnimationEmail_editText();
            }
        }, 1500);
    }

    private void performAnimationEmail_editText()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animationMoveRight = AnimationUtils.loadAnimation(Login_Page.this, R.anim.move_right_to_left_animation);
                TextInputEditText Email_editText = findViewById(R.id.Email_editText);
                Email_editText.setVisibility(View.VISIBLE);
                Email_editText.startAnimation(animationMoveRight);
                performAnimationPassword_editText();
            }
        }, 1000);
    }

    private void performAnimationPassword_editText()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animationMoveRight = AnimationUtils.loadAnimation(Login_Page.this, R.anim.move_right_to_left_animation);
                TextInputEditText Password_editText = findViewById(R.id.Password_editText);
                Password_editText.setVisibility(View.VISIBLE);
                Password_editText.startAnimation(animationMoveRight);
                performAnimationSignUp_textView();
            }
        }, 1000);
    }

    private void performAnimationSignUp_textView()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animationMoveRight = AnimationUtils.loadAnimation(Login_Page.this, R.anim.move_right_to_left_animation);
                TextView SignUp_textView = findViewById(R.id.SignUp_textView);
                SignUp_textView.setVisibility(View.VISIBLE);
                SignUp_textView.startAnimation(animationMoveRight);
                performAnimationForgotPassword_textView();
            }
        }, 1000);
    }

    private void performAnimationForgotPassword_textView()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animationMoveRight = AnimationUtils.loadAnimation(Login_Page.this, R.anim.move_right_to_left_animation);
                TextView ForgotPassword_textView = findViewById(R.id.ForgotPassword_textView);
                ForgotPassword_textView.setVisibility(View.VISIBLE);
                ForgotPassword_textView.startAnimation(animationMoveRight);
                performAnimationLogin_btn();
            }
        }, 1000);
    }

    private void performAnimationLogin_btn()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Animation animationMoveRight = AnimationUtils.loadAnimation(Login_Page.this, R.anim.slide_in_animation);
                MaterialButton Login_btn = findViewById(R.id.Login_btn);
                Login_btn.setVisibility(View.VISIBLE);
                Login_btn.startAnimation(animationMoveRight);
            }
        }, 200);
    }
}