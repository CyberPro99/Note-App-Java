package com.example.final_projects;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.allyants.notifyme.NotifyMe;
import com.example.final_projects.DBHelper.DBHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class New_Task extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Button time_btn;
    public Button submit, View_Task;
    DBHelper DB;
    public EditText editName, editDis;
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;

    //======================================Casting===========================================
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__task);
        DB = new DBHelper(this);
        time_btn = findViewById(R.id.time_picker_actions);
        submit = findViewById(R.id.Submit_Button_new_task);
        editName = findViewById(R.id.edit_name_new_task);
        editDis = findViewById(R.id.edit_dis_new_task);
        View_Task = findViewById(R.id.View_task);

        //============================== code  is to make the Activity full screen==============================================================
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //=================================Listener For insert data into SQLite Database Table (UserDetails)==========================================
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskNameText = editName.getText().toString();
                String taskDesText = editDis.getText().toString();
                String task_time_text = time_btn.getText().toString();

                Boolean CheckInsertData = DB.InsertUserData(taskNameText, taskDesText);
                if (CheckInsertData) {
                    Toasty.success(New_Task.this, "Successful Add The Task", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(New_Task.this, "An error occurred while entering", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //=================================Listener For View data into SQLite Database Table (UserDetails)==========================================
        View_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getData();
                if (res.getCount() == 0) {
                    Toast.makeText(New_Task.this, "No Data Found ", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("Task Name :").append(res.getString(0)).append("\n");
                    buffer.append("Description :").append(res.getString(1)).append("\n \n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(New_Task.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        //================================get Current date and set it to DataPicker=================================================
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                New_Task.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH)// Initial day selection
        );

        //===============================get Current Time and set it to DataPicker==================================================
        tpd = TimePickerDialog.newInstance(
                New_Task.this,
                now.get(Calendar.HOUR_OF_DAY), // Initial Hour selection
                now.get(Calendar.MINUTE),// Initial Minute selection
                false
        );

        //=================================Set Time Button Listener=============================================
        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
    }

    //====================================DatePickerDialog==========================================
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");
    }

    //===================================TimePickerDialog===========================================
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(Calendar.MINUTE, minute);

        //==============================Notification Customize================
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(editName.getText().toString())
                .content(editDis.getText().toString())
                .color(13, 112, 242, 0)
                .time(now)
                .large_icon(R.mipmap.ic_launcher_foreground)
                .small_icon(R.drawable.notfication)
                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")//RRULE for frequency of notification
                .addAction(new Intent(),"List Task") //The action will call the intent when pressed
                .build();
    }
}