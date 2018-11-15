


package com.shaden.wesal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VisitingMotherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VisitingMotherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisitingMotherFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private FirebaseAuth mAuth;
    DatabaseReference ref;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    MotherHomePage motherHomePage;
    ArrayList<String> list;
    ArrayList<schedule> daysList;
    ArrayAdapter<String> adapter;
    schedule schedule;
    Spinner WeekDaySpinner;
    String selectedDay;
    String classId;
    String studentMotherId;
    TextView existDay;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VisitingMotherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisitingMotherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisitingMotherFragment newInstance(String param1, String param2) {
        VisitingMotherFragment fragment = new VisitingMotherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_visiting_mother, container, false);

        Button book_btn = (Button) v.findViewById(R.id.book2);
        book_btn.setOnClickListener(this);
        WeekDaySpinner = (Spinner) v.findViewById(R.id.weekDaySpinner);
        existDay = (TextView) v.findViewById(R.id.existDay);

      /* DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();

        Query query1 = reference1.child("schedules").orderByChild("day06").equalTo(studentMotherId);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    // for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        existDay.setText("عذراً لديك حجز سابق");
                        WeekDaySpinner.setVisibility(View.INVISIBLE);

                    // }
                }
                else
                { */
        schedule = new schedule();
        mAuth = FirebaseAuth.getInstance();


       /* Button btnFragment = (Button) v.findViewById(R.id.cancel);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame, new N);
                ft.commit();
            }
        });*/
        ref = database.getReference("schedules");
        list = new ArrayList<>();
        daysList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);


        studentMotherId = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("students").orderByChild("motherId").equalTo(studentMotherId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        classId = ds.child("classID").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    schedule = ds.getValue(schedule.class);
                    daysList.add(schedule);
                    if (schedule.getClassId().equals(classId)) {
                        if (schedule.getDay01().equals("null"))
                            list.add("الأسبوع الثالث - يوم الأحد");
                        if (schedule.getDay02().equals("null"))
                            list.add("الأسبوع الثالث - يوم الخميس");
                        if (schedule.getDay03().equals("null"))
                            list.add("الأسبوع الرابع - يوم الأحد");
                        if (schedule.getDay04().equals("null"))
                            list.add("الأسبوع الرابع - يوم الخميس");
                        if (schedule.getDay05().equals("null"))
                            list.add("الأسبوع الخامس - يوم الأحد");
                        if (schedule.getDay06().equals("null"))
                            list.add("الأسبوع الخامس - يوم الخميس");
                        if (schedule.getDay07().equals("null"))
                            list.add("الأسبوع السادس - يوم الأحد");
                        if (schedule.getDay08().equals("null"))
                            list.add("الأسبوع السادس - يوم الخميس");
                        if (schedule.getDay09().equals("null"))
                            list.add("الأسبوع السابع - يوم الأحد");
                        if (schedule.getDay10().equals("null"))
                            list.add("الأسبوع السابع - يوم الخميس");
                        if (schedule.getDay11().equals("null"))
                            list.add("الأسبوع الثامن - يوم الأحد");
                        if (schedule.getDay12().equals("null"))
                            list.add("الأسبوع الثامن - يوم الخميس");
                        if (schedule.getDay13().equals("null"))
                            list.add("الأسبوع التاسع - يوم الأحد");
                        if (schedule.getDay14().equals("null"))
                            list.add("الأسبوع التاسع - يوم الخميس");
                        if (schedule.getDay15().equals("null"))
                            list.add("الأسبوع العاشر - يوم الأحد");
                        if (schedule.getDay16().equals("null"))
                            list.add("الأسبوع العاشر - يوم الخميس");
                        if (schedule.getDay17().equals("null"))
                            list.add("الأسبوع الحادي عشر - يوم الأحد");
                        if (schedule.getDay18().equals("null"))
                            list.add("الأسبوع الحادي عشر - يوم الخميس");
                        if (schedule.getDay19().equals("null"))
                            list.add("الأسبوع الثاني عشر - يوم الأحد");
                        if (schedule.getDay20().equals("null"))
                            list.add("الأسبوع الثاني عشر - يوم الخميس");
                    }
                }
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                WeekDaySpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return v;

    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void onClick(View v) {

        selectedDay = (String) WeekDaySpinner.getSelectedItem().toString();
        String day;
        if (selectedDay == "الأسبوع الثالث - يوم الأحد")
            day = "day01";
        else if (selectedDay == "الأسبوع الثالث - يوم الخميس")
            day = "day02";
        else if (selectedDay == "الأسبوع الرابع - يوم الأحد")
            day = "day03";
        else if (selectedDay == "الأسبوع الرابع - يوم الخميس")
            day = "day04";
        else if (selectedDay == "الأسبوع الخامس - يوم الأحد")
            day = "day05";
        else if (selectedDay == "الأسبوع الخامس - يوم الخميس")
            day = "day06";
        else if (selectedDay == "الأسبوع السادس - يوم الأحد")
            day = "day07";
        else if (selectedDay == "الأسبوع السادس - يوم الخميس")
            day = "day08";
        else if (selectedDay == "الأسبوع السابع - يوم الأحد")
            day = "day09";
        else if (selectedDay == "الأسبوع السابع - يوم الخميس")
            day = "day10";
        else if (selectedDay == "الأسبوع الثامن - يوم الأحد")
            day = "day11";
        else if (selectedDay == "الأسبوع الثامن - يوم الخميس")
            day = "day12";
        else if (selectedDay == "الأسبوع التاسع - يوم الأحد")
            day = "day13";
        else if (selectedDay == "الأسبوع التاسع - يوم الخميس")
            day = "day14";
        else if (selectedDay == "الأسبوع العاشر - يوم الأحد")
            day = "day15";
        else if (selectedDay == "الأسبوع العاشر - يوم الخميس")
            day = "day16";
        else if (selectedDay == "الأسبوع الحادي عشر - يوم الأحد")
            day = "day17";
        else if (selectedDay == "الأسبوع الحادي عشر - يوم الخميس")
            day = "day18";
        else if (selectedDay == "الأسبوع الثاني عشر - يوم الأحد")
            day = "day19";
        else
            day = "day20";

        String motherId = mAuth.getCurrentUser().getUid();
        ref.child(schedule.getID()).child(day).setValue(motherId);
        Toast.makeText(getContext(),"تم حجز الموعد",Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.main_frame,new MotherHomePage()).commit();


    }
}