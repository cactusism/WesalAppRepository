package com.shaden.wesal;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentsFragment extends Fragment {


    FirebaseDatabase database;
    DatabaseReference ref;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    students student;
    ImageButton addBtn;
    AddStudentFragment addStudentFragment;
    StudentProfile studentProfile;
    StudentDetailsFragment studentDetailsFragment;
    TextView noStudents;
    ArrayList<students> allStudents;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StudentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentsFragment newInstance(String param1, String param2) {
        StudentsFragment fragment = new StudentsFragment();
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
        View v = inflater.inflate(R.layout.fragment_students, container, false);
        student = new students();
        addStudentFragment = new AddStudentFragment();
        studentProfile = new StudentProfile();
        studentDetailsFragment = new StudentDetailsFragment();
        addBtn = (ImageButton)v.findViewById(R.id.addstdBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, addStudentFragment).commit();
            }
        });


        listView = (ListView) v.findViewById(R.id.studentsList);
        noStudents = (TextView) v.findViewById(R.id.noStudents);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("students");
        list = new ArrayList<>();
        allStudents = new ArrayList<>();


        adapter = new ArrayAdapter<String>(getContext(), R.layout.onestudent,R.id.studentInfo,list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    student = ds.getValue(students.class);
                    if (student.getClassID().equals(StaffHomePage.getClassId())) {
                    allStudents.add(student);
                    list.add("الطالب:   " + student.getFirstname().toString() + "\n الفصل:  " + student.getClassName().toString());
                }}
                listView.setAdapter(adapter);
                if(list.isEmpty()){
                    noStudents.setText("لا يوجد طلاب حاليّا");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaffHomePage.setStudentId(allStudents.get(position).getStId());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, new StudentProfile()).commit();

            }
        });

        return v;
    }
        /*
        Button btnFragment = (Button) v.findViewById(R.id.addStudentButton2);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame, new AddStudentFragment());
                ft.commit();
            }
        });

        Button listStudentButton = (Button) v.findViewById(R.id.listStudentButton);
        listStudentButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame, new ListStudentFragment());
                ft.commit();
            }
        }); */




        // Inflate the layout for this fragment

//@Override
//public void onClick(View view) {
   // Intent intent = new Intent (getActivity().getContext(), addStudent.class);
   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //startActivity(intent);
     //   }
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
    public void getValues() {


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
