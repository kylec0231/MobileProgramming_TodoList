package com.csce4623.ahnelson.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//Create HomeActivity and implement the OnClick listener
public class HomeActivity extends AppCompatActivity implements View.OnClickListener, MyAdapter.OnNoteListener {

    private static final String TAG = "HomeActivityTag";


    public static final int REQUEST_CODE_FOR_TODONOTE = 1001;
    public static final int REQUEST_CODE_FOR_UPDATETODONOTE = 1002;
    public static final String Extra_Text_TodoID = "com.csce4623.ahnelson.todolist.ExtraTextTodoID";
    public static final String Extra_Text_TodoTitle = "com.csce4623.ahnelson.todolist.ExtraTextTodoTitle";
    public static final String Extra_Text_TodoContent = "com.csce4623.ahnelson.todolist.ExtraTextTodoContent";
    public static final String Extra_Text_TodoDuedate = "com.csce4623.ahnelson.todolist.ExtraTextTodoDuedate";
    public static final String Extra_Text_TodobooleanCompletion = "com.csce4623.ahnelson.todolist.ExtraTextTodobooleanCompletion";

    private int countElements = 0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<TodoClass> TodoClassList = new ArrayList<> ();


    List<String> listStringTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFromContentProviderToTodoClassList();
        buildRecyclerView();
        initializeComponents();
    }

    public void loadFromContentProviderToTodoClassList(){
        TodoClassList.clear();
        //Create the projection for the query
        String[] projection = {
                ToDoProvider.TODO_TABLE_COL_ID,
                ToDoProvider.TODO_TABLE_COL_TITLE,
                ToDoProvider.TODO_TABLE_COL_CONTENT,
                ToDoProvider.TODO_TABLE_COL_DUEDATE,
                ToDoProvider.TODO_TABLE_COL_COMPLETION
        };

        Cursor myCursor = getContentResolver().query(ToDoProvider.CONTENT_URI,projection,null,null,null);

        if(myCursor != null & myCursor.getCount() > 0) {
            //Move the cursor to the beginning
            //myCursor.moveToFirst();

            while(myCursor.moveToNext()) {
                countElements ++;
                String TodoId = myCursor.getString(0);
                String TodoTitle = myCursor.getString(1);
                String TodoContent = myCursor.getString(2);
                String TodoDuedate = myCursor.getString(3);
                int ToDoCompletion = myCursor.getInt(4);
                //TodoDuedate = myCursor.getString(3);
//                String TodoDuedate = "not been implemented";

                TodoClassList.add(new TodoClass(TodoId, TodoTitle, TodoContent, TodoDuedate, ToDoCompletion));

//                System.out.println("what " + TodoId);
//                System.out.println("what " + TodoTitle);
//                System.out.println("what " + TodoContent);
            }


        } else{
            Toast.makeText(getApplicationContext(), "No Note to display!", Toast.LENGTH_LONG).show();

        }

    }

    public void buildRecyclerView(){
        recyclerView = findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(TodoClassList, this);
        recyclerView.setAdapter(mAdapter);


    }

    //Set the OnClick Listener for buttons
    void initializeComponents(){
        findViewById(R.id.btnNewToDoActivity).setOnClickListener(this);
//        findViewById(R.id.btnNewNote).setOnClickListener(this);
//        findViewById(R.id.btnDeleteNote).setOnClickListener(this);
        //findViewById(R.id.parent_layout).setOnClickListener(this);



    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            //If new Note, call createNewNote()
            case R.id.btnNewToDoActivity:
                createNewToDoActivity();
                break;
            //If new Note, call createNewNote()
//            case R.id.btnUpdate:
//                loadExistingToDoActivity();
//                break;
            //If new Note, call createNewNote()
//            case R.id.btnNewNote:
//                createNewNote();
//                break;
//            //If delete note, call deleteNewestNote()
//            case R.id.btnDeleteNote:
//                deleteNewestNote();
//                break;
            //This shouldn't happen
            default:
                break;
        }
    }

    //Create a new Activity
    void createNewToDoActivity() {

        Intent intent = new Intent(this, DisplayTodolistContentActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, REQUEST_CODE_FOR_TODONOTE);
    }

    //Create a new Activity
    void loadExistingToDoActivity() {

        Intent intent = new Intent(this, DisplayTodolistContentActivity.class);

        //EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, REQUEST_CODE_FOR_TODONOTE);
    }
    //Create a new note with the title "New Note" and content "Note Content"
    void createNewNote(){
        //Create a ContentValues object
        ContentValues myCV = new ContentValues();
        //Put key_value pairs based on the column names, and the values
        myCV.put(ToDoProvider.TODO_TABLE_COL_TITLE,"New Note");
        myCV.put(ToDoProvider.TODO_TABLE_COL_CONTENT,"Note Content");
        //Perform the insert function using the ContentProvider
        getContentResolver().insert(ToDoProvider.CONTENT_URI,myCV);
        //Set the projection for the columns to be returned
        String[] projection = {
                ToDoProvider.TODO_TABLE_COL_ID,
                ToDoProvider.TODO_TABLE_COL_TITLE,
                ToDoProvider.TODO_TABLE_COL_CONTENT};
        //Perform a query to get all rows in the DB
        Cursor myCursor = getContentResolver().query(ToDoProvider.CONTENT_URI,projection,null,null,null);
        //Create a toast message which states the number of rows currently in the database
        Toast.makeText(getApplicationContext(),Integer.toString(myCursor.getCount()),Toast.LENGTH_LONG).show();
    }

    //Delete the newest note placed into the database
    void deleteNewestNote(){
        //Create the projection for the query
        String[] projection = {
                ToDoProvider.TODO_TABLE_COL_ID,
                ToDoProvider.TODO_TABLE_COL_TITLE,
                ToDoProvider.TODO_TABLE_COL_CONTENT};

        //Perform the query, with ID Descending
        Cursor myCursor = getContentResolver().query(ToDoProvider.CONTENT_URI,projection,null,null,"_ID DESC");
        if(myCursor != null & myCursor.getCount() > 0) {
            //Move the cursor to the beginning
            myCursor.moveToFirst();
            //Get the ID (int) of the newest note (column 0)
            int newestId = myCursor.getInt(0);
            //Delete the note
            int didWork = getContentResolver().delete(Uri.parse(ToDoProvider.CONTENT_URI + "/" + newestId), null, null);
            //If deleted, didWork returns the number of rows deleted (should be 1)
            if (didWork == 1) {
                //If it didWork, then create a Toast Message saying that the note was deleted
                Toast.makeText(getApplicationContext(), "Deleted Note " + newestId, Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "No Note to delete!", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_CODE_FOR_TODONOTE:
                if (resultCode == Activity.RESULT_OK){
                    //get messages
                    String messageNoteTitle = data.getStringExtra("NoteTitleInfo");
                    String messageNoteContent = data.getStringExtra("NoteContentInfo");
                    String messageNoteDuedate = data.getStringExtra("DatePickerInfo");
                    int messageNoteCompletion = 0;
                    //Toast.makeText(getApplicationContext(), messageNoteTitle + " " + messageNoteContent , Toast.LENGTH_LONG).show();



                    //Create a ContentValues object
                    ContentValues myCV = new ContentValues();
                    //Put key_value pairs based on the column names, and the values
                    myCV.put(ToDoProvider.TODO_TABLE_COL_TITLE,messageNoteTitle);
                    myCV.put(ToDoProvider.TODO_TABLE_COL_CONTENT,messageNoteContent);
                    myCV.put(ToDoProvider.TODO_TABLE_COL_DUEDATE,messageNoteDuedate);
                    myCV.put(ToDoProvider.TODO_TABLE_COL_COMPLETION, messageNoteCompletion);

                    //Perform the insert function using the ContentProvider
                    getContentResolver().insert(ToDoProvider.CONTENT_URI,myCV);
                    //Set the projection for the columns to be returned
                    String[] projection = {
                            ToDoProvider.TODO_TABLE_COL_ID,
                            ToDoProvider.TODO_TABLE_COL_TITLE,
                            ToDoProvider.TODO_TABLE_COL_CONTENT,
                            ToDoProvider.TODO_TABLE_COL_DUEDATE,
                            ToDoProvider.TODO_TABLE_COL_COMPLETION
                    };
                    //Perform a query to get all rows in the DB
                    Cursor myCursor = getContentResolver().query(ToDoProvider.CONTENT_URI,projection,null,null,null);
                    //Create a toast message which states the number of rows currently in the database
                //    Toast.makeText(getApplicationContext(),Integer.toString(myCursor.getCount()),Toast.LENGTH_LONG).show();

                    countElements++;
                    TodoClassList.add(new TodoClass(Integer.toString(myCursor.getCount()), messageNoteTitle, messageNoteContent, messageNoteDuedate, messageNoteCompletion));

                    loadFromContentProviderToTodoClassList();

                    mAdapter.notifyDataSetChanged();
                    //do something with it
                }else{
                    Toast.makeText(getApplicationContext(), "Activity Canceled", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_CODE_FOR_UPDATETODONOTE:
                if(resultCode == Activity.RESULT_OK){

                    //get messages
                    String messageNoteId = data.getStringExtra("NoteId");
                    String messageNoteTitle = data.getStringExtra("NoteTitleUpdateInfo");
                    String messageNoteContent = data.getStringExtra("NoteContentUpdateInfo");
                    String messageNoteDuedate = data.getStringExtra("NoteDatePickerUpdateInfo");
                    int messageNotebooleanCompletion = data.getIntExtra("NotebooleanCompletion", -1);

                    Toast.makeText(getApplicationContext(), messageNoteTitle + " " + messageNoteContent
                            + " and completion value is: " + messageNotebooleanCompletion, Toast.LENGTH_LONG).show();

                    //Create a ContentValues object
                    ContentValues myCV = new ContentValues();
                    //Put key_value pairs based on the column names, and the values
                    myCV.put(ToDoProvider.TODO_TABLE_COL_TITLE,messageNoteTitle);
                    myCV.put(ToDoProvider.TODO_TABLE_COL_CONTENT,messageNoteContent);
                    myCV.put(ToDoProvider.TODO_TABLE_COL_DUEDATE,messageNoteDuedate);
                    myCV.put(ToDoProvider.TODO_TABLE_COL_COMPLETION, messageNotebooleanCompletion);
                    //Perform the insert function using the ContentProvider
                    //getContentResolver().insert(ToDoProvider.CONTENT_URI,myCV);
                    //Set the projection for the columns to be returned
                    String[] projection = {
                            ToDoProvider.TODO_TABLE_COL_ID,
                            ToDoProvider.TODO_TABLE_COL_TITLE,
                            ToDoProvider.TODO_TABLE_COL_CONTENT,
                            ToDoProvider.TODO_TABLE_COL_DUEDATE,
                            ToDoProvider.TODO_TABLE_COL_COMPLETION
                    };
                    //Perform a query to get all rows in the DB
                    getContentResolver().update(ToDoProvider.CONTENT_URI, myCV,ToDoProvider.TODO_TABLE_COL_ID+"=?", new String[] {messageNoteId} );
                    //       getContentResolver().update(ToDoProvider.CONTENT_URI, myCV,ToDoProvider.TODO_TABLE_COL_ID+"=?", new String[] {loadedTOdoID}); //id is the id of the row you wan to update

                    countElements++;

                    loadFromContentProviderToTodoClassList();
//                    for(int i = 0; i < TodoClassList.size(); i++)
//                    {
//                        if (TodoClassList.get(i).getmText1() == messageNoteId){
//                            TodoClassList.get(i).mText2 = messageNoteTitle;
//                            TodoClassList.get(i).mText3 = messageNoteContent;
//                        }
//                    }
//                    //TodoClassList.add(new TodoClass(messageNoteId, messageNoteTitle, messageNoteContent));
//

                    mAdapter.notifyDataSetChanged();



                }else if(resultCode == Activity.RESULT_FIRST_USER) {

                    String messageNoteId = data.getStringExtra("NoteId");

                    //Create the projection for the query
//                    String[] projection = {
//                            ToDoProvider.TODO_TABLE_COL_ID,
//                            ToDoProvider.TODO_TABLE_COL_TITLE,
//                            ToDoProvider.TODO_TABLE_COL_CONTENT};

                    String[] projection = {
                            ToDoProvider.TODO_TABLE_COL_ID,
                            ToDoProvider.TODO_TABLE_COL_TITLE,
                            ToDoProvider.TODO_TABLE_COL_CONTENT,
                            ToDoProvider.TODO_TABLE_COL_DUEDATE,
                            ToDoProvider.TODO_TABLE_COL_COMPLETION
                    };

                    //Perform the query, with ID Descending
                    Cursor myCursor = getContentResolver().query(ToDoProvider.CONTENT_URI,projection,null,null,"_ID DESC");
                    if(myCursor != null & myCursor.getCount() > 0) {
                        //Move the cursor to the beginning
                        myCursor.moveToFirst();
                        //Get the ID (int) of the newest note (column 0)
                        int newestId = myCursor.getInt(0);
                        //Delete the note
                        int didWork = getContentResolver().delete(Uri.parse(ToDoProvider.CONTENT_URI + "/" + messageNoteId), null, null);
                        //If deleted, didWork returns the number of rows deleted (should be 1)
                        if (didWork == 1) {
                            //If it didWork, then create a Toast Message saying that the note was deleted
                            Toast.makeText(getApplicationContext(), "Deleted Note " + messageNoteId, Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "No Note to delete!", Toast.LENGTH_LONG).show();

                    }
                    loadFromContentProviderToTodoClassList();
                    mAdapter.notifyDataSetChanged();


                }else {
                        Toast.makeText(getApplicationContext(), "Update Canceled", Toast.LENGTH_LONG).show();


                }
                break;

            default:
                Toast.makeText(getApplicationContext(), "abnormal call back detected", Toast.LENGTH_LONG).show();

                break;
        }

    }

    @Override
    public void onNoteClick(int position) {
        Toast.makeText(getApplicationContext(), "you clicked " + position + "th note", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onNoteClick method and you clicked " + position + "th note");

//        TodoClassList.get(position);
        Intent intent = new Intent(this, DisplayTodolistUpdateActivity.class );
        intent.putExtra(Extra_Text_TodoID, TodoClassList.get(position).getmTodoID());
        intent.putExtra(Extra_Text_TodoTitle, TodoClassList.get(position).getmTodoTitle());
        intent.putExtra(Extra_Text_TodoContent, TodoClassList.get(position).getmTodoContent());
        intent.putExtra(Extra_Text_TodoDuedate, TodoClassList.get(position).getmTodoDuedate());
        intent.putExtra(Extra_Text_TodobooleanCompletion, TodoClassList.get(position).getmToDoCompletion());

        startActivityForResult(intent, REQUEST_CODE_FOR_UPDATETODONOTE);
    }
}
