package com.shaden.wesal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NotificationDialog extends AppCompatDialogFragment {
    private EditText notification;
    private EditText notificationSubject;
    private NotificationDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (NotificationDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement NotificationDialogListener");

        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("إضافة تنبيه")
                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("نشر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String notificationText = notification.getText().toString();
                        String notificationSubjectText = notificationSubject.getText().toString();
                        listener.applyTexts(notificationSubjectText, notificationText);

                    }
                });
        notification = view.findViewById(R.id.notEditText);
        notificationSubject = view.findViewById(R.id.notSubEditText);
        return builder.create();

    }
    public interface NotificationDialogListener {
        void applyTexts(String subject, String notification);
    }
}

