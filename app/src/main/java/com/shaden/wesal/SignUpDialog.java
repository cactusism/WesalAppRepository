package com.shaden.wesal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class SignUpDialog extends AppCompatDialogFragment {
    String email = "", password="";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("تمّ تسجيل المستخدم بنجاح");
        builder.setMessage("البريد الإلكتروني:"+ email+"\n" +"كلمة المرور:"+password);
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent (getContext(), AdminHomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return builder.create();

    }

    public void setEmailAndPassword (String e, String p){
        email = e;
        password = p;
    }
}
