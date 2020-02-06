package com.csce4623.ahnelson.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class DisplayTodolistContentActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "DisplayTodolistContentActivityTag";


    private Button buttonChannel1;
    private Button buttonChannel2;
    private NotificationHelper mNotificationHelper;
    private Calendar testC;

    Button btn_setDateTime;
    EditText et_setDateTime;


    EditText tvNoteTitle ;
    EditText etNoteContent ;
    EditText etDatePicker ;



    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    String hourFinal0, minuteFinal0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_todolist_content);
        setContentView(R.layout.note_activity);
        Toast.makeText(getApplicationContext(),"open new activity",Toast.LENGTH_LONG).show();

        tvNoteTitle = (EditText) findViewById(R.id.tvNoteTitle);
        etNoteContent = (EditText) findViewById(R.id.etNoteContent);
        etDatePicker = (EditText) findViewById(R.id.etDatePicker);

        final String messagetvNoteTitle1 = tvNoteTitle.getText().toString();
        final String messageetNoteContent1 = etNoteContent.getText().toString();
        final String messageetDatePicker1 = etDatePicker.getText().toString();

        btn_setDateTime = (Button) findViewById(R.id.btnDateTimePicker);
        et_setDateTime = (EditText) findViewById(R.id.etDatePicker);


        buttonChannel1 = (Button) findViewById(R.id.btnSave);
        mNotificationHelper = new NotificationHelper(this);

//        buttonChannel1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//
//                sendOnChannel1("hello", "testonly");
//            }
//        });

        btn_setDateTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                Date newDate = c.getTime();

                DatePickerDialog datePickerDialog = new DatePickerDialog(DisplayTodolistContentActivity.this, DisplayTodolistContentActivity.this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(newDate.getTime() - (newDate.getTime()%(24*60*60*1000)));
                datePickerDialog.show();

            }
        });
        initializeComponents();

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
        findViewById(R.id.btnSave).setOnClickListener(this);
        //findViewById(R.id.btnDateTimePicker).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            //If new Note, call createNewNote()
            case R.id.btnSave:
                saveCurrentInformation();
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
    void saveCurrentInformation(){

//        EditText tvNoteTitle = (EditText) findViewById(R.id.tvNoteTitle);
//        EditText etNoteContent = (EditText) findViewById(R.id.etNoteContent);
//        EditText etDatePicker = (EditText) findViewById(R.id.etDatePicker);

        tvNoteTitle = (EditText) findViewById(R.id.tvNoteTitle);
        etNoteContent = (EditText) findViewById(R.id.etNoteContent);
        etDatePicker = (EditText) findViewById(R.id.etDatePicker);

        String messagetvNoteTitle = tvNoteTitle.getText().toString();
        String messageetNoteContent = etNoteContent.getText().toString();
        String messageetDatePicker = etDatePicker.getText().toString();


        messagetvNoteTitle = tvNoteTitle.getText().toString();
        messageetNoteContent = etNoteContent.getText().toString();
        messageetDatePicker = etDatePicker.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("NoteTitleInfo",messagetvNoteTitle);
        intent.putExtra("NoteContentInfo", messageetNoteContent);
        intent.putExtra("DatePickerInfo",messageetDatePicker);

        setResult(Activity.RESULT_OK, intent);

        sendOnChannel1(messagetvNoteTitle, messageetNoteContent);


//                Toast.makeText(getApplicationContext(), "check"+ messageetNoteContent + " " + messageetNoteContent,Toast.LENGTH_LONG).show();


        finish();
//
//        //Create a ContentValues object
//        ContentValues myCV = new ContentValues();
//        //Put key_value pairs based on the column names, and the values
//        myCV.put(ToDoProvider.TODO_TABLE_COL_TITLE,"New Note");
//        myCV.put(ToDoProvider.TODO_TABLE_COL_CONTENT,"Note Content");
//        //Perform the insert function using the ContentProvider
//        getContentResolver().insert(ToDoProvider.CONTENT_URI,myCV);
//        //Set the projection for the columns to be returned
//        String[] projection = {
//                ToDoProvider.TODO_TABLE_COL_ID,
//                ToDoProvider.TODO_TABLE_COL_TITLE,
//                ToDoProvider.TODO_TABLE_COL_CONTENT};
//        //Perform a query to get all rows in the DB
//        Cursor myCursor = getContentResolver().query(ToDoProvider.CONTENT_URI,projection,null,null,null);
//        //Create a toast message which states the number of rows currently in the database
//        Toast.makeText(getApplicationContext(),Integer.toString(myCursor.getCount()),Toast.LENGTH_LONG).show();


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(DisplayTodolistContentActivity.this
                , DisplayTodolistContentActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

        ////////////////////////



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        hourFinal = hourOfDay;
        minuteFinal = minute;


        if(hourFinal < 10)
            hourFinal0 = "0" + String.valueOf(hourFinal);
        else
            hourFinal0 = String.valueOf(hourFinal);

        if(minuteFinal < 10)
            minuteFinal0 = "0" + String.valueOf(minuteFinal);
        else
            minuteFinal0 = String.valueOf(minuteFinal);

        et_setDateTime.setText(monthFinal + "/" + dayFinal + "/" + yearFinal + "/ " + hourFinal0 + ":" + minuteFinal0);
///////////////

//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        c.set(Calendar.MINUTE, minute);
//        c.set(Calendar.SECOND, 0);
//
//        updateTimeText(c);
//        startAlarm(c);

    }
    public void updateTimeText(Calendar c){

        String timeText = "";

        timeText += c.getTime().toString();
        et_setDateTime.setText(timeText);
///////////////////


        //et_setDateTime.setText(monthFinal + "/" + dayFinal + "/" + yearFinal + "/ " + hourFinal0 + ":" + minuteFinal0);

    }
    public void startAlarm(Calendar c){

    }

    public void sendOnChannel1(String title, String message){
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title, message);
        mNotificationHelper.getManager().notify(1, nb.build());

    }
}
