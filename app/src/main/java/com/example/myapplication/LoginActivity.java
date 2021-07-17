package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView textView_signIn;
    private TextView textView_find;
    private Button login_btn;
    private FirebaseAuth mAuth;
    private EditText textView_passwd;
    private EditText textView_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        textView_id = findViewById(R.id.textView_id);
        textView_passwd = findViewById(R.id.textView_passwd);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(textView_id.getText().toString(),
                        textView_passwd.getText().toString());
            }
        });

        // textview에 underline 긋는 방법 (setPaintFlags 사용하여 underline text flag 쓰기)
        textView_signIn = findViewById(R.id.textView_signIn);
        textView_find = findViewById(R.id.textView_find);

        textView_signIn.setPaintFlags(textView_signIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView_find.setPaintFlags(textView_find.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textView_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        textView_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void logIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                        }
                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

}
