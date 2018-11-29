package com.shaden.wesal;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    EditText staffName, emailEditText, passwordEditText, repeatPasswordEditText;
    DatabaseReference ref;
    TextView title;
    Typeface typeface;
    Button add, cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_staff);

        setTitle("تسجيل موظف");

        mAuth = FirebaseAuth.getInstance();
        ref = database.getReference("staff");
        typeface = Typeface.createFromAsset(this.getAssets(),"fonts/GE_SS_Two_Light.otf");
        staffName = (EditText) findViewById(R.id.staffName);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.passwordTextbox);
        repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordTextBox);
        title = (TextView) findViewById(R.id.titleSUStaff);

        add = (Button) findViewById(R.id.signUpBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);

        staffName.setTypeface(typeface);
        emailEditText.setTypeface(typeface);
        passwordEditText.setTypeface(typeface);
        repeatPasswordEditText.setTypeface(typeface);
        title.setTypeface(typeface);
        add.setTypeface(typeface);
        cancel.setTypeface(typeface);

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
        final String name = staffName.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();

        if(name.isEmpty()){
            staffName.setError("حقل الاسم مطلوب");
            staffName.requestFocus();
            return;
        }

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
                    staff staff = new staff(name);
                    staff.setId(userId);
                    staff.setAssigned("null");
                    ref.child(userId).setValue(staff);
                   // String id = ref.push().getKey();
                    //staff.setId(id);
                    //ref.child(id).setValue(staff);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
        }

        return true;
    }


}
