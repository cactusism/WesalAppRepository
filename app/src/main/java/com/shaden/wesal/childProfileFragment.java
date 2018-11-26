package com.shaden.wesal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link childProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link childProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class childProfileFragment extends Fragment {

    /*TextView studentName;
    DatabaseReference ref, childrenRef;
    FirebaseDatabase database;
    ImageView profile,back, performance, chat;
    ClassStudentsFragment classStudentsFragment;
    StudentPersonalInformationFragment studentPersonalInformationFragment;
    StaffChatFragment staffChatFragment;
    StudentPerformanceFragment studentPerformanceFragment;
    FirebaseUser fuser;
    String motherId, childId;
    students student;*/
    FirebaseUser fuser;
    FirebaseDatabase database;
    String motherId, name;
    DatabaseReference childrenRef;
    students student;
    TextView sName;
    ImageView chat, performance;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public childProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment childProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static childProfileFragment newInstance(String param1, String param2) {
        childProfileFragment fragment = new childProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_child_profile, container, false);

        database = FirebaseDatabase.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        sName = (TextView) v.findViewById(R.id.studentName1);
        motherId = fuser.getUid();
        childrenRef = database.getReference().child("students");
        childrenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot: dataSnapshot.getChildren()){
                    student = snapshot.getValue(students.class);
                    if(student.getMotherId().equals(motherId)){
                        //MotherHomePage.setChildId(student.getStId());
                        name = student.getFirstname()+" "+student.getLastname();
                        sName.setText(name);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        chat = (ImageView) v.findViewById(R.id.chat);
        performance = (ImageView) v.findViewById(R.id.performance);


        /*database = FirebaseDatabase.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        motherId = fuser.getUid();
        childrenRef = database.getReference().child("students");

        childrenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot: dataSnapshot.getChildren()){
                    student = snapshot.getValue(students.class);
                    if(student.getMotherId().equals(motherId))
                        MotherHomePage.setChildId(student.getStId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref = database.getReference().child("students").child(MotherHomePage.getChildId());
        classStudentsFragment = new ClassStudentsFragment();
        studentPerformanceFragment = new StudentPerformanceFragment();
        staffChatFragment = new StaffChatFragment();
        studentPersonalInformationFragment = new StudentPersonalInformationFragment();

        studentName = (TextView) v.findViewById(R.id.studentName);
        profile = (ImageView) v.findViewById(R.id.profile);
        back = (ImageView) v.findViewById(R.id.backBtn);
        performance = (ImageView) v.findViewById(R.id.performance);
        chat = (ImageView) v.findViewById(R.id.chat);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, classStudentsFragment);
                fragmentTransaction.commit();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, studentPersonalInformationFragment);
                fragmentTransaction.commit();
            }
        });

        performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, studentPerformanceFragment);
                fragmentTransaction.commit();
            }
        });*/

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChildMessageActivity.class );
                startActivity(i);
                /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, staffChatFragment);
                fragmentTransaction.commit(); */
            }
        });

        performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mother_main_frame, new PerformanceChildFragment());
                fragmentTransaction.commit();
            }
        });

/*
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students std = dataSnapshot.getValue(students.class);
                studentName.setText(std.getFirstname()+" "+std.getLastname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
}
