package com.shaden.wesal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NotificationListAdapter extends ArrayAdapter<notifications>{
    Context mCtx;
    int resource;
    List<notifications> notificationsList;
    int index;

    public NotificationListAdapter (Context mCtx, int resource, List<notifications> notificationsList){
        super(mCtx, resource, notificationsList);
        this.mCtx = mCtx;
        this.resource = resource;
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource, null);

        TextView notSubject = view.findViewById(R.id.notSubject);
        TextView notBody = view.findViewById(R.id.notBody);
        TextView notDate = view.findViewById(R.id.notDate);

        notifications not = notificationsList.get(position);

        notSubject.setText(not.getSubject());
        notBody.setText(not.getBody());
        notDate.setText(not.getTime());

        return view;
    }


}
