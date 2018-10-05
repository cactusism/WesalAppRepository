package com.shaden.wesal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpStaff extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText emailEditText, passwordEditText, repeatPasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_staff);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.passwordTextbox);
        repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordTextBox);
        findViewById(R.id.signUpBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SignUpStaff.this, AdminHomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("حقل البريد الإلكتروني مطلوب");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("تأكدي أن البريد المدخل صحيح الصياغة");
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEditText.setError("حقل كلمة المرور مطلوب");
            passwordEditText.requestFocus();
            return;
        }
        if(repeatPassword.isEmpty()){
            repeatPasswordEditText.setError("حقل تأكيد كلمة المرور مطلوب");
            repeatPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 6){
            passwordEditText.setError("كلمة المرور يجب أن تحوي 6 رموز على الأقل");
            passwordEditText.requestFocus();
            return;
        }
        if (!password.equals(repeatPassword)){
            repeatPasswordEditText.setError("كلمتي المرور لا تتطابقان");
            repeatPasswordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user =  mAuth.getCurrentUser();
                    final String userId = user.getUid();
                    final DatabaseReference mRef=  database.getReference().child("roles");
                    mRef.child(userId).setValue("staff");
                    openDialog();
                }
                else{
                    String error=task.getException().getMessage();
                    if(error.equals("The email address is already in use by another account."))
                        error="البريد الإلكتروني المُدخل مسجّل مسبقًا";
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    public void openDialog(){
        SignUpDialog dialog = new SignUpDialog();
        dialog.setEmailAndPassword(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
        dialog.show(getSupportFragmentManager(), "SignUp Dialog");
    }
}
