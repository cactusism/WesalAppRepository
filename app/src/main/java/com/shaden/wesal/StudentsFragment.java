package com.shaden.wesal;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentsFragment extends Fragment implements View.OnClickListener {

    EditText firstName, middleName, lastName , nationalId, height, weight, bloodType,day,month,year;
    Button add, cancel;

    FirebaseDatabase database;
    DatabaseReference ref;
    students student;

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
        View v = inflater.inflate(R.layout.fragment_add_student, container, false);

        firstName= (EditText) v.findViewById(R.id.editTextFirstName);
        middleName= (EditText) v.findViewById(R.id.editTextMiddleName);
        lastName= (EditText) v.findViewById(R.id.editTextLastName);
        nationalId = (EditText) v.findViewById(R.id.editTextNationalId);
        height = (EditText) v.findViewById(R.id.editTextHight);
        weight = (EditText) v.findViewById(R.id.editTextWieght);
       // bloodType = (EditText) v.findViewById(R.id.editTextBloodType);

        Spinner bloodTypeSpinner = (Spinner) v.findViewById(R.id.bloodType);
        Spinner daySpinner = (Spinner) v.findViewById(R.id.day);
        Spinner monthSpinner = (Spinner) v.findViewById(R.id.month);
        Spinner yearSpinner = (Spinner) v.findViewById(R.id.year);

        String bloodType = bloodTypeSpinner.getSelectedItem().toString();
        String day = daySpinner.getSelectedItem().toString();
        String month = monthSpinner.getSelectedItem().toString();
        String year = yearSpinner.getSelectedItem().toString();

        //student.setBloodType("A");
        //student.setDay(day);
        //student.setMonth(month);
        //student.setYear(year);



        Button add_student_btn = (Button) v.findViewById(R.id.addStudentButton);
        add_student_btn.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("students");
        student = new students();

        // Inflate the layout for this fragment
        return v;
    }
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
        student.setFirstname(firstName.getText().toString());
        student.setMiddleName(middleName.getText().toString());
        student.setLastname(lastName.getText().toString());
        student.setNationalId(nationalId.getText().toString());
        student.setHeight(height.getText().toString());
        student.setWeight(weight.getText().toString());


    }
    @Override
    public void onClick(View v) {
        String firstNameVer = firstName.getText().toString();
        String middleNameVer = middleName.getText().toString();
        String lastNameVer = lastName.getText().toString();
        String nationalIdVer = nationalId.getText().toString();
        String heightVer = height.getText().toString();
        String weightVer = weight.getText().toString();


        if(firstNameVer.isEmpty()){
            firstName.setError("حقل اسم الطالب ممطلوب");
            firstName.requestFocus();
            return;
        }

        if(middleNameVer.isEmpty()){
            middleName.setError("حقل اسم الأب مطلوب");
            middleName.requestFocus();
            return;
        }
        if(lastNameVer.isEmpty()){
            lastName.setError("حقل اسم العائلة مطلوب");
            lastName.requestFocus();
            return;
        }
        if(nationalIdVer.isEmpty()){
            nationalId.setError("حقل السجل المدني مطلوب");
            nationalId.requestFocus();
            return;
        }
        if(nationalIdVer.length() <10 || nationalIdVer.length() > 10 || !(nationalIdVer.matches("[0-9]+"))){
            nationalId.setError("يجب أن يحتوي السجل المدني على ١٠ أرقام فقط");
            nationalId.requestFocus();
            return;
        }
        if(heightVer.isEmpty()){
            height.setError("حقل الطول مطلوب");
            height.requestFocus();
            return;
        }
        if(!(heightVer.matches("[0-9]+"))) {
            height.setError(" يجب أن يتكون الطول من أرقام فقط");
            height.requestFocus();
            return;
        }
        if(weightVer.isEmpty()){
            weight.setError("حقل الوزن مطلوب");
            weight.requestFocus();
        }
        if(!(weightVer.matches("[0-9]+"))){
            weight.setError(" يجب أن يتكون الوزن من أرقام فقط");
            weight.requestFocus();
            return;
        }


        getValues();
        ref.child("student03").setValue(student);
        Toast.makeText(getContext(),"تم إضافة الطالب",Toast.LENGTH_LONG).show();


    }


/*
    @Override
    public void onClick(View view) {

    }*/


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
