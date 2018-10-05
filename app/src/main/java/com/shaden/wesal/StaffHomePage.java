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
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class StaffHomePage extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ClassesFragment classesFragment;
    private NotificationsFragment notificationsFragment;
    private StudentsFragment studentsFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home_page);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        classesFragment = new ClassesFragment();
        notificationsFragment = new NotificationsFragment();
        studentsFragment = new StudentsFragment();

        setFragment(notificationsFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_classes :
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(classesFragment);
                        return true;


                    case R.id.nav_notifications:
                        mMainNav.setItemBackgroundResource(R.color.colorPink);
                        setFragment(notificationsFragment);
                        return true;

                    case R.id.nav_students:
                        mMainNav.setItemBackgroundResource(R.color.colorBlue);
                        setFragment(studentsFragment);
                        return true;

                        default:
                            return false;




                }
            }
        });

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

}
