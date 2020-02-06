package com.csce4623.ahnelson.todolist;

public class TodoClass {
    public String mTodoId;
    public String mTodoTitle;
    public String mTodoContent;
    public String mTodoDuedate;
    public int mToDoCompletion;


    public TodoClass (String todoId, String todoTitle, String todoContent, String todoDuedate, int todoCompletion)
    {
        mTodoId = todoId;
        mTodoTitle = todoTitle;
        mTodoContent = todoContent;
        mTodoDuedate = todoDuedate;
        mToDoCompletion = todoCompletion;

    }

    public String getmTodoID ()
    {
        return mTodoId;
    }
    public String getmTodoTitle ()
    {
        return mTodoTitle;
    }
    public String getmTodoContent () { return mTodoContent; }
    public String getmTodoDuedate () { return mTodoDuedate; }
    public int getmToDoCompletion() { return mToDoCompletion; }

}
