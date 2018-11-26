package com.shaden.wesal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationDialog extends AppCompatDialogFragment {
    private EditText notification;
    private EditText notificationSubject;
    private TextView whatsnew;
    private NotificationDialogListener listener;
    Typeface typeface;

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
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");
        builder.setView(view)
                .setTitle("إضافة إعلان")
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

                        if(notificationSubjectText.isEmpty()){
                            notification.setText("بدون عنوان");

                        }

                        if(notificationText.isEmpty()){
                            Toast.makeText(getContext(),"لم يتم نشر الإعلان لأن حقل محتوى الإعلان مطلوب",Toast.LENGTH_LONG).show();
                        }
                        else {
                            listener.applyTexts(notificationSubjectText, notificationText);
                        }

                    }
                });
        notification = view.findViewById(R.id.notEditText);
        notificationSubject = view.findViewById(R.id.notSubEditText);
        whatsnew = view.findViewById(R.id.whatsnew);
        whatsnew.setTypeface(typeface);
       // notificationSubject.setTypeface(typeface);
       // notification.setTypeface(typeface);
        return builder.create();

    }
    public interface NotificationDialogListener {
        void applyTexts(String subject, String notification);
    }
}

