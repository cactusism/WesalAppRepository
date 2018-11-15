package com.shaden.wesal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class studentsListAdapter extends ArrayAdapter<students> {
    Context mCtx;
    int resource;
    List<students> studentsList;
    int index;

    public studentsListAdapter (Context mCtx, int resource, List<students> studentsList){
        super(mCtx, resource, studentsList);
        this.mCtx = mCtx;
        this.resource = resource;
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource, null);
/*
        TextView stName = view.findViewById(R.id.stdNameBox);
        TextView stClass = view.findViewById(R.id.stClass);

        students student = studentsList.get(position);

        stName.setText(student.getFirstname()+" "+student.getLastname());
        stClass.setText("فصل الزهور");
*/
        return view;
    }

}
