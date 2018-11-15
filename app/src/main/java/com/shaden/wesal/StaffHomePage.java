package com.shaden.wesal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StaffHomePage extends AppCompatActivity implements  NotificationDialog.NotificationDialogListener{

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private notifications not;
    private ClassesFragment classesFragment;
    private NotificationsFragment notificationsFragment;
    private StudentsFragment studentsFragment;
    private AddStudentFragment addstudentFragment;
    private MyClassFragment myClassFragment;

    private static String studentId;
    private static String classStudentId;
    private static String classId;

    public static String getChatStudentId() {
        return chatStudentId;
    }

    public static void setChatStudentId(String chatStudentId) {
        StaffHomePage.chatStudentId = chatStudentId;
    }

    private static String chatStudentId;


    private AddClassFragment addClassFragment;
    FirebaseDatabase database;
    DatabaseReference ref;




    @Override
    public void applyTexts(String subject, String notification) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        Calendar cal = Calendar.getInstance();
        not = new notifications();
        not.setSubject(subject);
        not.setBody(notification);
        not.setTime(dateFormat.format(cal.getTime()));
        //ref.push().setValue(not);
        String id = ref.push().getKey();
        not.setNotId(id);
        ref.child(id).setValue(not);
        Toast.makeText(this,"تم نشر التنبيه",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        ref=  database.getReference().child("notifications");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home_page);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        View view = mMainNav.findViewById(R.id.nav_notifications);
        view.performClick();
        mMainNav.setItemBackgroundResource(R.color.colorPink);

        classesFragment = new ClassesFragment();
        notificationsFragment = new NotificationsFragment();
        studentsFragment = new StudentsFragment();
        addstudentFragment = new AddStudentFragment();
        myClassFragment = new MyClassFragment();



        init();
        setFragment(notificationsFragment);

        mMainNav.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_classes:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(classesFragment);
                        return true;


                    case R.id.nav_notifications:
                        mMainNav.setItemBackgroundResource(R.color.colorPink);
                        setFragment(notificationsFragment);
                        return true;

                    /*case R.id.nav_students:
                        mMainNav.setItemBackgroundResource(R.color.colorBlue);
                        setFragment(studentsFragment);
                        return true; */

                    case R.id.nav_myClass:
                        mMainNav.setItemBackgroundResource(R.color.yellow);
                        setFragment(myClassFragment);
                        return true;

                        default:
                            return false;




                }
            }
        });

    }

    private void init(){
        ListStudentFragment fragment = new ListStudentFragment();
        //doFragmentTransaction(fragment, getString(R.string.), false, "");
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment, tag);
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
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

    public static String getStudentId () {return studentId;}
    public static void setStudentId(String id) { studentId=id;}


    public static String getClassStudentId () {return classStudentId;}
    public static void setClassStudentId(String id) { classStudentId=id;}

    public static String getClassId () {return classId;}
    public static void setClassId(String id) { classId=id;}

}
