package com.example.final_projects;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login_Sing_up_Activity extends AppCompatActivity {
    EditText email, pass;
    Button SingUp, Login;
    FirebaseAuth mAtu;
    ProgressDialog progressDialog;


    //=======================================Casting=====================================================
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__sing_up_);

        //=======================================code  is to make the Activity full screen=====================================================
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = findViewById(R.id.edit_email);
        pass = findViewById(R.id.edit_pass);
        SingUp = findViewById(R.id.Singup1);
        Login = findViewById(R.id.Login);
        mAtu = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("wait a moment");
        progressDialog.setCancelable(false);


        SingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Sing_up_Activity.this,login_detiles.class);
                startActivity(intent);
                finish();
            }
        });
        //=============================================LogIn button===========================
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get email and password and send it to firebase by the log in button
                String email_1 = email.getText() + "";
                String password = pass.getText() + "";
                // ?????? ???????? ?????????????? ?????????????????? ?????? ???????? ?????? ?????? ?????????????????? ???????????? ???????????? ?????????? ????????????
                if (isValidEmail(email_1) && password.length() >= 6) {
                    login(email_1, password);
                } else {
                    if (!isValidEmail(email_1))
                        email.setError("You do not have a registered e-mail");
                    else
                        pass.setError("Password does not match");
                }
            }
        });
    }

    //==============================logIn method==========================================================
    private void login(String email, String password) {
        progressDialog.show();

        mAtu.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Login_Sing_up_Activity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //=======================================TO ENSURE THAT THE EMAIL IS A corrected EMAIL OR NOT===================================================
    //TO ENSURE THAT THE EMAIL IS A corrected EMAIL OR NOT
    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //=================================================Show the progress bar onPause Call Method=================================================
    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

