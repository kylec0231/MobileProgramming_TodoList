package com.csce4623.ahnelson.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<TodoClass> mTodoClassList = new ArrayList<>();
    private OnNoteListener mOnNoteListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public interface OnNoteListener{
        void onNoteClick (int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        //private TextView textView;
        private TextView mTextView1;
        private TextView mTextView2;
        private TextView mTextView3;
        private TextView mTextView4;

        OnNoteListener onNoteListener;

        public MyViewHolder(View v, OnNoteListener onNoteListener) {
            super(v);
            mTextView1 = v.findViewById(R.id.text_name1);
            mTextView2 = v.findViewById(R.id.text_name2);
            mTextView3 = v.findViewById(R.id.text_name3);
            mTextView4 = v.findViewById(R.id.text_name4);

            this.onNoteListener = onNoteListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<TodoClass> TodoClassList, OnNoteListener onNoteListener) {
        mTodoClassList = TodoClassList;
        this.mOnNoteListener = onNoteListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);

        MyViewHolder vh = new MyViewHolder(v, mOnNoteListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TodoClass currentTodoInstance = mTodoClassList.get(position);
        holder.mTextView1.setText(currentTodoInstance.getmTodoID());
        holder.mTextView2.setText(currentTodoInstance.getmTodoTitle());
        holder.mTextView3.setText(currentTodoInstance.getmTodoContent());
        holder.mTextView4.setText(currentTodoInstance.getmTodoDuedate());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTodoClassList.size();
    }
}