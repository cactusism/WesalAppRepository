package com.shaden.wesal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
//import android.widget.ProgressBar;
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

public class MainActivity extends AppCompatActivity {
    // Write a message to the database
    //ProgressBar progressBar;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;



    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        //LogIn
        findViewById( R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    public void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password= editTextPassword.getText().toString().trim();

        if(email.isEmpty()&&password.isEmpty()){
            editTextEmail.setError("حقل البريد الإلكتروني مطلوب");
            editTextPassword.setError("حقل كلمة المرور والبريد الإلكتروني مطلوب");
            editTextEmail.requestFocus();
            editTextPassword.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("حقل البريد الإلكتروني مطلوب");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("تأكدي أن البريد المدخل صحيح الصياغة");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("حقل كلمة المرور مطلوب");
            editTextPassword.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    FirebaseUser user =  mAuth.getCurrentUser();
                    final String userId = user.getUid();
                    final DatabaseReference mRef=  database.getReference().child("roles");
                  //mRef.child(userId).setValue("admin");

                    // Read from the database

                   mRef.child(userId).addValueEventListener(new ValueEventListener() {

                        Intent intentMother = new Intent (MainActivity.this, MotherHomePage.class);
                        Intent intentStaff = new Intent (MainActivity.this, StaffHomePage.class);
                        Intent intentAdmin = new Intent (MainActivity.this, AdminHomePage.class);
                       @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String value = dataSnapshot.getValue(String.class);
                            if(value.equals("mother")){
                            intentMother.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            System.out.print(mAuth.getCurrentUser().getUid());
                            startActivity(intentMother); }
                            else if (value.equals("staff")){
                               intentStaff.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               System.out.print(mAuth.getCurrentUser().getUid());
                               startActivity(intentStaff);
                            }
                            else if (value.equals("admin")){
                                intentAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                System.out.print(mAuth.getCurrentUser().getUid());
                                startActivity(intentAdmin);
                            }
                            else
                                editTextEmail.setError("حسابك يجري إصلاحه, راجعي المسؤولة");
                                editTextEmail.requestFocus();                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            mRef.child("logged").setValue("NOT");
                        }
                    });



                } else{
                    String error=task.getException().getMessage();
                    if(error.equals("There is no user record corresponding to this identifier. The user may have been deleted."))
                   error="لا يوجد مستخدم بهذه البيانات";
                 else if(task.getException().getMessage().equals("The password is invalid or the user does not have a password."))
                     error="كلمة المرور المدخلة غير صحيحة، الرجاء إعادة المحاولة";
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //updateUI(currentUser);
    }




}

