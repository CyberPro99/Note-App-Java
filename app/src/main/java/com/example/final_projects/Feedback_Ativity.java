package com.example.final_projects;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.hsalf.smileyrating.SmileyRating;

public class Feedback_Ativity extends AppCompatActivity {
    private SmileyRating smileyRating;
    Button button_send;
    TextInputEditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback__ativity);
        smileyRating = findViewById(R.id.smile_rating);
        button_send=findViewById(R.id.button_send);
        input=findViewById(R.id.input);
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //==========================================Simile Rating Code===================================================
        smileyRating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {

                if (SmileyRating.Type.GREAT == type) {
                    Toast.makeText(Feedback_Ativity.this, "رائع", Toast.LENGTH_SHORT).show();

                }else
                if (SmileyRating.Type.BAD == type) {
                    Toast.makeText(Feedback_Ativity.this, "سيء", Toast.LENGTH_SHORT).show();

                }else
                if (SmileyRating.Type.TERRIBLE == type) {
                    Toast.makeText(Feedback_Ativity.this, "مكروه", Toast.LENGTH_SHORT).show();

                }else
                if (SmileyRating.Type.GOOD == type) {
                    Toast.makeText(Feedback_Ativity.this, "جيد جدا", Toast.LENGTH_SHORT).show();

                }else
                if (SmileyRating.Type.OKAY == type) {
                    Toast.makeText(Feedback_Ativity.this, "جيد", Toast.LENGTH_SHORT).show();

                }
                int rating = type.getRating();

            }
        });
            }

    }
