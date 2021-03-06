package com.shaden.wesal;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditStarOfWeekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditStarOfWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditStarOfWeekFragment extends Fragment {

    ArrayList<students> studentsList;
    ArrayAdapter<students> studentsAdapter;
    Spinner studentsSpinner;
    FirebaseDatabase database;
    DatabaseReference ref, studentsRef, classRef;
    FirebaseUser fuser;
    Button edit, cancel;
    Classes classes, newClass;
    students student;
    String staffId, classId, starOfWeek;
    Typeface typeface;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditStarOfWeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditStarOfWeekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditStarOfWeekFragment newInstance(String param1, String param2) {
        EditStarOfWeekFragment fragment = new EditStarOfWeekFragment();
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
        View v =  inflater.inflate(R.layout.fragment_edit_star_of_week, container, false);
        getActivity().setTitle("نجم الأسبوع");
        studentsList = new ArrayList<>();
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");
        studentsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, studentsList);
        studentsSpinner = (Spinner) v.findViewById(R.id.studentsSpinner);
        edit = (Button) v.findViewById(R.id.editBtn);
        cancel = (Button) v.findViewById(R.id.cancelBtn);
        ref = FirebaseDatabase.getInstance().getReference("classes");
        studentsRef = FirebaseDatabase.getInstance().getReference("students");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        staffId = fuser.getUid();
        newClass = new Classes();

        edit.setTypeface(typeface);
        cancel.setTypeface(typeface);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClassFragment.isBackFromStar = true;
                MyClassFragment.isBackFromList = false;

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new MyClassFragment());
                fragmentTransaction.commit();
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    classes = ds.getValue(Classes.class);
                    if(classes.getTeacherID().equals(staffId)){
                        classId = classes.getID();
                        if(!classes.getStarOfTheWeekId().equals("")){
                            starOfWeek = classes.getStarOfTheWeekId();
                        }
                    }

                }
                studentsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            student = ds.getValue(students.class);
                            if(student.getClassID().equals(classId)){
                                if(!student.getStId().equals(starOfWeek)){
                                    studentsList.add(student);
                                }
                            }
                        }
                        studentsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        studentsSpinner.setAdapter(studentsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                classRef = FirebaseDatabase.getInstance().getReference("classes").child(classId);
                classRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Classes cls = dataSnapshot.getValue(Classes.class);
                        newClass.setName(cls.getName());
                        newClass.setStarOfTheWeekId(cls.getStarOfTheWeekId());
                        newClass.setStarOfTheWeekName(cls.getStarOfTheWeekName());
                        newClass.setTeacher(cls.getTeacher());
                        newClass.setTeacherID(cls.getTeacherID());
                        newClass.setID(cls.getID());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClassFragment.isBackFromStar = true;
                MyClassFragment.isBackFromList = false;
                students std = (students) studentsSpinner.getSelectedItem();
                newClass.setStarOfTheWeekId(std.getStId());
                newClass.setStarOfTheWeekName(std.getFirstname()+" "+ std.getLastname());
                classRef.setValue(newClass);
                Toast.makeText(getContext(),"تم تعديل نجم الأسبوع",Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, new MyClassFragment()).commit();
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
