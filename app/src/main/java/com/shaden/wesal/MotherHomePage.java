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

import com.google.firebase.auth.FirebaseAuth;


public class MotherHomePage extends AppCompatActivity {


    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    MotherNotificationsFragment motherNotificationsFragment;
    ChildProfileFragment childProfileFragment;
    ChildClassFragment childClassFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_home_page);

        motherNotificationsFragment = new MotherNotificationsFragment();
        childClassFragment = new ChildClassFragment();
        childProfileFragment = new ChildProfileFragment();



        mMainFrame = (FrameLayout) findViewById(R.id.mother_main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.mother_main_nav);

        View view = mMainNav.findViewById(R.id.nav_notifications);
        view.performClick();
        mMainNav.setItemBackgroundResource(R.color.colorPink);

        mMainNav.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_child :
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(childProfileFragment);
                        return true;


                    case R.id.nav_notifications:
                        mMainNav.setItemBackgroundResource(R.color.colorPink);
                        setFragment(motherNotificationsFragment);
                        return true;

                    case R.id.nav_child_class:
                        mMainNav.setItemBackgroundResource(R.color.colorBlue);
                        setFragment(childClassFragment);
                        return true;

                    default:
                        return false;




                }
            }
        });

    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mother_main_frame, fragment);
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
