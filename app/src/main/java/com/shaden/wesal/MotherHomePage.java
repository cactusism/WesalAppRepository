package com.shaden.wesal;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shaden.wesal.chatNotifications.Token;


public class MotherHomePage extends AppCompatActivity {
    private MotherNotificationsFragment motherNotificationsFragment;
    DatabaseReference ref;
    FirebaseDatabase database;
    Context context = this;
    int counter =0;
    long numOfNotificationBefore =0;
    long numOfNotificationAfter = 0;
    String notificationTitle;
    static String childId;
    FirebaseUser fUser;


    public static String getChildId() {
        return childId;
    }

    public static void setChildId(String childId) {
        MotherHomePage.childId = childId;
    }

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    childProfileFragment childProfileFragment;
    childClassFragment childClassFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        motherNotificationsFragment = new MotherNotificationsFragment();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_home_page);
        childClassFragment = new childClassFragment();
        childProfileFragment = new childProfileFragment();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        updateToken(FirebaseInstanceId.getInstance().getToken());




        mMainFrame = (FrameLayout) findViewById(R.id.mother_main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.mother_main_nav);

        View view = mMainNav.findViewById(R.id.nav_notifications);
        view.performClick();
        mMainNav.setItemBackgroundResource(R.color.colorPink);

        setFragment(motherNotificationsFragment);

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

       database = FirebaseDatabase.getInstance();
       ref=  database.getReference().child("notifications");
           ref.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   //set notification title:
                   notifications not = null;
                   for (DataSnapshot ds:dataSnapshot.getChildren())
                   {
                       not = ds.getValue(notifications.class);
                   }
                   if (not != null)
                   notificationTitle = not.subject;


                   numOfNotificationAfter = dataSnapshot.getChildrenCount();
                   if(counter !=0 && numOfNotificationAfter > numOfNotificationBefore) {
                       NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                               .setDefaults(NotificationCompat.DEFAULT_ALL)
                               .setSmallIcon(R.drawable.notification)
                               .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.newwesal))
                               .setContentTitle("تم نشر إعلان جديد")
                               .setContentText(notificationTitle);
                       NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                       notificationManager.notify(1, notificationBuilder.build());
                   }
                   counter++;
                   numOfNotificationBefore = dataSnapshot.getChildrenCount();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

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

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fUser.getUid()).setValue(token1);
    }
}
