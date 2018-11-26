package com.shaden.wesal;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignUpMother extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    DatabaseReference ref;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText emailEditText, passwordEditText, repeatPasswordEditText;
    ArrayList<String> list;
    ArrayList<students> studentsList;
    ArrayAdapter<String> adapter;
    students student;
    Spinner StudentSpinner;
    String selectedStudent;
    Typeface typeface;
    Button add, cancel;
    TextView title, childTxt;


    //String fullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_mother);

        setTitle("تسجيل أم");


        student = new students();
        mAuth = FirebaseAuth.getInstance();
        typeface = Typeface.createFromAsset(this.getAssets(),"fonts/GE_SS_Two_Light.otf");


        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.passwordTextbox);
        repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordTextBox);

        title = (TextView) findViewById(R.id.titleTxt);
        childTxt = (TextView) findViewById(R.id.childTxt);



        emailEditText.setTypeface(typeface);
        passwordEditText.setTypeface(typeface);
        repeatPasswordEditText.setTypeface(typeface);

        title.setTypeface(typeface);
        childTxt.setTypeface(typeface);

        add = (Button) findViewById(R.id.signUpBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);

        add.setTypeface(typeface);
        cancel.setTypeface(typeface);

        findViewById(R.id.signUpBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpMother.this, AdminHomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        StudentSpinner = (Spinner) findViewById(R.id.StudentsSpinner);
        ref = database.getReference("students");
        list = new ArrayList<>();
        studentsList = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(this, R.layout.activity_sign_up_mother,R.id.StudentsSpinner, list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(students.class);
                    studentsList.add(student);
                    if (student.getMotherId().equals("null")) {
                        list.add(student.getFirstname().toString() + " " + student.getMiddleName().toString() + " " + student.getLastname().toString() );
                    }
                }
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                StudentSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("حقل البريد الإلكتروني مطلوب");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("تأكدي أن البريد المدخل صحيح الصياغة");
            emailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("حقل كلمة المرور مطلوب");
            passwordEditText.requestFocus();
            return;
        }
        if (repeatPassword.isEmpty()) {
            repeatPasswordEditText.setError("حقل تأكيد كلمة المرور مطلوب");
            repeatPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordEditText.setError("كلمة المرور يجب أن تحوي 6 رموز على الأقل");
            passwordEditText.requestFocus();
            return;
        }
        if (!password.equals(repeatPassword)) {
            repeatPasswordEditText.setError("كلمتي المرور لا تتطابقان");
            repeatPasswordEditText.requestFocus();
            return;
        }
        if (StudentSpinner.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "يجب اختيار اسم الطفل", Toast.LENGTH_LONG).show();
        }

        selectedStudent = getStudent(StudentSpinner.getSelectedItem().toString());
        //selectedStudent = StudentSpinner.getSelectedItem().toString();
        //selectedStudent = selectedStudent.substring(selectedStudent.indexOf(',')+1);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    final String userId = user.getUid();
                    final DatabaseReference mRef = database.getReference().child("roles");
                    mRef.child(userId).setValue("mother");
                    ref.child(selectedStudent).child("motherId").setValue(userId);
                    openDialog();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void openDialog() {
        SignUpDialog dialog = new SignUpDialog();
        dialog.setEmailAndPassword(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
        dialog.show(getSupportFragmentManager(), "SignUp Dialog");
    }


    public String getStudent(String childName) {
        String firstName, middleName, lastName, fullName;

        for (int i = 0; i < studentsList.size(); i++) {
            firstName = studentsList.get(i).getFirstname();
            middleName = studentsList.get(i).getMiddleName();
            lastName = studentsList.get(i).getLastname();
            fullName = firstName + " " + middleName + " " + lastName;
            if (fullName.equals(childName))
                return studentsList.get(i).getStId();
        }
        return null;
    }
}