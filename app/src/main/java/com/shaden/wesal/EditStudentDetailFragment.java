package com.shaden.wesal;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditStudentDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditStudentDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditStudentDetailFragment extends Fragment implements View.OnClickListener {

    TextView std,title;
    EditText name;
    FirebaseDatabase database;
    DatabaseReference ref;
    students student;
    Button cancel, add;
    StudentsFragment studentsFragment;
    StudentProfile studentProfile;
    String motherId, className;



    EditText firstName, middleName, lastName , heightText, weightText; //nationalId,
    Spinner bloodTypeSpinner,daySpinner, monthSpinner,yearSpinner;//genderSpinner;
    String bloodType,day,month,year,gender,nationalId,gndr,classID;
    double height,weight;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private StudentProfile.OnFragmentInteractionListener mListener;

    public EditStudentDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentProfile newInstance(String param1, String param2) {
        StudentProfile fragment = new StudentProfile();
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
        View v = inflater.inflate(R.layout.fragment_edit_student_detail, container, false);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("students").child(StaffHomePage.getStudentId());
        student = new students();
        studentsFragment = new StudentsFragment();
        studentProfile = new StudentProfile();

        cancel = (Button) v.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame, new StudentsFragment());
                ft.commit();
            }
        });
        add = (Button) v.findViewById(R.id.editBtn);
        add.setOnClickListener(this);

        title = (TextView)v.findViewById(R.id.titleText);

        firstName= (EditText) v.findViewById(R.id.editTextFirstName);
        middleName= (EditText) v.findViewById(R.id.editTextMiddleName);
        lastName= (EditText) v.findViewById(R.id.editTextLastName);
       // nationalId = (EditText) v.findViewById(R.id.editTextNationalId);
        heightText = (EditText) v.findViewById(R.id.editTextHight);
        weightText = (EditText) v.findViewById(R.id.editTextWieght);


        bloodTypeSpinner = (Spinner) v.findViewById(R.id.bloodType);
        daySpinner = (Spinner) v.findViewById(R.id.day);
        monthSpinner = (Spinner) v.findViewById(R.id.month);
        yearSpinner = (Spinner) v.findViewById(R.id.year);
       // genderSpinner = (Spinner) v.findViewById(R.id.gender);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students std = dataSnapshot.getValue(students.class);
                firstName.setText(std.getFirstname());
                middleName.setText(std.getMiddleName());
                lastName.setText(std.getLastname());
               //nationalId.setText(std.getNationalId());
                nationalId = std.getNationalId();
                gndr = std.getGender();
                motherId = std.getMotherId();
                className = std.getClassName();
                classID=std.getClassID();
               /* if(std.getGender().equals("boy"))
                    genderSpinner.setSelection(0);
                else
                    genderSpinner.setSelection(1);*/
                daySpinner.setSelection(Integer.parseInt(std.getDay())-1);
                monthSpinner.setSelection(Integer.parseInt(std.getMonth())-1);
                int year = Integer.parseInt(std.getYear().substring(3))-1;
                yearSpinner.setSelection(year);

                weightText.setText(String.valueOf(std.getWeight()));
                heightText.setText(String.valueOf(std.getHeight()));
                int bloodT=1;
                switch (std.getBloodType()){
                    case "A": bloodT = 0; break;
                    case "B": bloodT=1; break;
                    case "O": bloodT=2; break;
                    case"AB": bloodT=3; break;
                }
                bloodTypeSpinner.setSelection(bloodT);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        std = (TextView)v.findViewById(R.id.std);



        return v ;
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
        if (context instanceof StudentProfile.OnFragmentInteractionListener) {
            mListener = (StudentProfile.OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getValues()
    {
        student.setFirstname(firstName.getText().toString());
        student.setMiddleName(middleName.getText().toString());
        student.setLastname(lastName.getText().toString());
       student.setNationalId(nationalId);
        student.setHeight(height);
        student.setWeight(weight);
        student.setBloodType(bloodType);
        student.setDay(day);
        student.setMonth(month);
        student.setYear(year);
        student.setGender(gndr);
        student.setMotherId(motherId);
        student.setClassName(className);
        student.setClassID(classID);
    }

    @Override
    public void onClick(View v) {


        String firstNameVer = firstName.getText().toString();
        String middleNameVer = middleName.getText().toString();
        String lastNameVer = lastName.getText().toString();
        //String nationalIdVer = nationalId.getText().toString();
        String heightVer = heightText.getText().toString();
        String weightVer = weightText.getText().toString();

        bloodType = bloodTypeSpinner.getSelectedItem().toString();
        day = daySpinner.getSelectedItem().toString();
        month = monthSpinner.getSelectedItem().toString();
        year = yearSpinner.getSelectedItem().toString();
        /*gender = genderSpinner.getSelectedItem().toString();

        if (gender == "ولد") {
            gender = "boy";
            title.setText("معلومات الطالبة");
        }
        else
            gender = "girl"; */


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
      /*  if(nationalIdVer.isEmpty()){
            nationalId.setError("حقل السجل المدني مطلوب");
            nationalId.requestFocus();
            return;
        }
        if(nationalIdVer.length() <10 || nationalIdVer.length() > 10 || !(nationalIdVer.matches("[0-9]+"))){
            nationalId.setError("يجب أن يحتوي السجل المدني على ١٠ أرقام فقط");
            nationalId.requestFocus();
            return;
        }*/
        if(heightVer.isEmpty()){
            heightText.setError("حقل الطول مطلوب");
            heightText.requestFocus();
            return;
        }

        if(weightVer.isEmpty()){
            weightText.setError("حقل الوزن مطلوب");
            weightText.requestFocus();
            return;
        }

        try {
            height = Double.parseDouble(heightVer);
        } catch (Exception e){
            heightText.setError(" يجب أن يتكون الطول من أرقام فقط");
            heightText.requestFocus();
            return;
        }
        try {
            weight = Double.parseDouble(weightVer);
        } catch (Exception e){
            weightText.setError(" يجب أن يتكون الوزن من أرقام فقط");
            weightText.requestFocus();
            return;
        }

        getValues();
        student.setStId(StaffHomePage.getStudentId());
        ref.setValue(student);
        Toast.makeText(getContext(),"تم تعديل بيانات الطالب",Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frame, studentProfile).commit();



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
