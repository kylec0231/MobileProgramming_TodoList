package com.csce4623.ahnelson.todolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class DisplayTodolistUpdateActivity extends AppCompatActivity implements View.OnClickListener , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private static final String TAG = "DisplayTodolistUpdateActivityTag";
    public static final String Extra_Text_Title = "com.csce4623.ahnelson.todolist.DisplayTodolistUpdateActivity.Title";
    public static final String Extra_Text_Content = "com.csce4623.ahnelson.todolist.DisplayTodolistUpdateActivity.Content";
    public static final int AlarmPendingIntent = 10001;
    private Calendar c;

    private Button buttonChannel1;
//    private Button buttonChannel2;
    private NotificationHelper mNotificationHelper;

    String loadedTodoID;
    String loadedTodoTitle;
    String loadedTodoContent;
    String loadedTodoDuedate;
    int loadedTodoCompletion;

    EditText textViewTodoTitle;
    EditText textViewTodoContent;
    EditText textViewTodoDatepicker;

    Button btn_setDateTimeUpdate;
//     EditText et_setDateTimeUpdate;
    CheckBox checkBoxUpdate;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    int booleanCompletion;
    String hourFinal0, minuteFinal0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_todolist_content);
        setContentView(R.layout.note_update_activity);



        Toast.makeText(getApplicationContext(),"open update activity",Toast.LENGTH_LONG).show();


        buttonChannel1 = (Button) findViewById(R.id.btnUpdate);
        mNotificationHelper = new NotificationHelper(this);

        initializeComponents();


        btn_setDateTimeUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                Date newDate = c.getTime();

                DatePickerDialog datePickerDialog = new DatePickerDialog(DisplayTodolistUpdateActivity.this, DisplayTodolistUpdateActivity.this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(newDate.getTime() - (newDate.getTime()%(24*60*60*1000)));
                datePickerDialog.show();

            }
        });

        checkBoxUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (checkBoxUpdate.isChecked()){
                    booleanCompletion = 1;
                }
                else
                {
                    booleanCompletion = 0;
                }
                Toast.makeText(getApplicationContext(),"booleanCompletion is: " + booleanCompletion,Toast.LENGTH_LONG).show();

            }
        });


//        // Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();
////        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
////        String message = intent.getStringExtra(HomeActivity.EXTRA_MESSAGE);
//
//        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(message);
    }
    void initializeComponents(){
        findViewById(R.id.btnUpdate).setOnClickListener(this);
        findViewById(R.id.btnDone).setOnClickListener(this);

        Intent intent = getIntent();
        loadedTodoID = intent.getStringExtra(HomeActivity.Extra_Text_TodoID);
        loadedTodoTitle = intent.getStringExtra(HomeActivity.Extra_Text_TodoTitle);
        loadedTodoContent = intent.getStringExtra(HomeActivity.Extra_Text_TodoContent);
        loadedTodoDuedate = intent.getStringExtra(HomeActivity.Extra_Text_TodoDuedate);
        loadedTodoCompletion = intent.getIntExtra(HomeActivity.Extra_Text_TodobooleanCompletion, -1);


        textViewTodoTitle = (EditText) findViewById(R.id.tvNoteTitleUpdate);
        textViewTodoContent = (EditText) findViewById(R.id.etNoteContentUpdate);
        textViewTodoDatepicker = (EditText) findViewById(R.id.etDatePickerUpdate);

        btn_setDateTimeUpdate = (Button) findViewById(R.id.btnDateTimePickerUpdate);
        //et_setDateTimeUpdate = (EditText) findViewById(R.id.etDatePickerUpdate);
        checkBoxUpdate = (CheckBox) findViewById(R.id.checkBoxUpdate);

        textViewTodoTitle.setText(loadedTodoTitle);
        textViewTodoContent.setText(loadedTodoContent);
        textViewTodoDatepicker.setText(loadedTodoDuedate);
        if(loadedTodoCompletion == 1)
            checkBoxUpdate.setChecked(true);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            //If new Note, call createNewNote()
            case R.id.btnUpdate:
                updateCurrentInformation();
                break;
            case R.id.btnDone:
                DeleteCurrentInformation();
                break;
            //This shouldn't happen
            default:
                break;
        }
    }

    //Create a function to save current information
//    void saveCurrentInformation(){
//
//
//
//    }

    //Create a new note with the title "New Note" and content "Note Content"
    void updateCurrentInformation(){



        startAlarm(c);
        String messageNoteTitle = textViewTodoTitle.getText().toString();
        String messageNoteContent = textViewTodoContent.getText().toString();
        String messageDatePicker = textViewTodoDatepicker.getText().toString();
        int messagebooleanCompletion = booleanCompletion;

        Intent intent = new Intent();
        intent.putExtra("NoteId", loadedTodoID);
        intent.putExtra("NoteTitleUpdateInfo",messageNoteTitle);
        intent.putExtra("NoteContentUpdateInfo", messageNoteContent);
        intent.putExtra("NoteDatePickerUpdateInfo",messageDatePicker);
        intent.putExtra("NotebooleanCompletion", messagebooleanCompletion);

        setResult(Activity.RESULT_OK, intent);
 //       sendOnChannel1(messageNoteTitle, messageNoteContent);
 //       sendOnChannel1withAlarm(messageNoteTitle, messageNoteContent, messageDatePicker);


        finish();


    }
    void DeleteCurrentInformation(){
        Intent intent = new Intent();
        intent.putExtra("NoteId", loadedTodoID);

        setResult(Activity.RESULT_FIRST_USER, intent);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(DisplayTodolistUpdateActivity.this
                , DisplayTodolistUpdateActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;


        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourFinal);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

      //  startAlarm(c);

        if(hourFinal < 10)
            hourFinal0 = "0" + String.valueOf(hourFinal);
        else
            hourFinal0 = String.valueOf(hourFinal);

        if(minuteFinal < 10)
            minuteFinal0 = "0" + String.valueOf(minuteFinal);
        else
            minuteFinal0 = String.valueOf(minuteFinal);

        textViewTodoDatepicker.setText(monthFinal + "/" + dayFinal + "/" + yearFinal + " " + hourFinal0 + ":" + minuteFinal0);

    }

    public void sendOnChannel1(String title, String message){
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title, message);
        mNotificationHelper.getManager().notify(1, nb.build());

    }


    public void sendOnChannel1withAlarm(String title, String message, String deadLineDate){


//        Intent intentAlarm = new Intent(this, MyBroadcastReceiver.class)

        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title, message);
        mNotificationHelper.getManager().notify(1, nb.build());

    }

    public void startAlarm(Calendar c) {

        String messageNoteTitle = textViewTodoTitle.getText().toString();
        String messageNoteContent = textViewTodoContent.getText().toString();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.putExtra(Extra_Text_Title, messageNoteTitle);
        intent.putExtra(Extra_Text_Content, messageNoteContent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmPendingIntent, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);


    }
}
