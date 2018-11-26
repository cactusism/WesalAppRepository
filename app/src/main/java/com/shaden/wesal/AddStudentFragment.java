package com.shaden.wesal;

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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddStudentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStudentFragment extends Fragment implements View.OnClickListener {
    EditText firstName, middleName, lastName , nationalId, heightText, weightText;
    Spinner bloodTypeSpinner,daySpinner, monthSpinner,yearSpinner, genderSpinner;
    String bloodType,day,month,year,gender;
    double height,weight;
    Button add, cancel;
    StudentsFragment studentsFragment;
    Classes selectedClass;
    FirebaseDatabase database;
    DatabaseReference ref, classesRef;
    students student;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStudentFragment newInstance(String param1, String param2) {
        AddStudentFragment fragment = new AddStudentFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_student, container, false);

        getActivity().setTitle("إضافة طالب");


        firstName= (EditText) v.findViewById(R.id.editTextFirstName);
        middleName= (EditText) v.findViewById(R.id.editTextMiddleName);
        lastName= (EditText) v.findViewById(R.id.editTextLastName);
        nationalId = (EditText) v.findViewById(R.id.editTextNationalId);
        heightText = (EditText) v.findViewById(R.id.editTextHight);
        weightText = (EditText) v.findViewById(R.id.editTextWieght);


        bloodTypeSpinner = (Spinner) v.findViewById(R.id.bloodType);
        daySpinner = (Spinner) v.findViewById(R.id.day);
        monthSpinner = (Spinner) v.findViewById(R.id.month);
        yearSpinner = (Spinner) v.findViewById(R.id.year);
        genderSpinner = (Spinner) v.findViewById(R.id.gender);

        studentsFragment = new StudentsFragment();

        Button add_student_btn = (Button) v.findViewById(R.id.editBtn);
        add_student_btn.setOnClickListener(this);

        Button btnFragment = (Button) v.findViewById(R.id.cancelBtn);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame, new StudentsFragment());
                ft.commit();
            }
        });

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("students");
        classesRef=database.getReference("classes");
        student = new students();

        classesRef.child(StaffHomePage.getClassId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               selectedClass = dataSnapshot.getValue(Classes.class);
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
    public void getValues()
    {
        student.setFirstname(firstName.getText().toString());
        student.setMiddleName(middleName.getText().toString());
        student.setLastname(lastName.getText().toString());
        student.setNationalId(nationalId.getText().toString());
        student.setHeight(height);
        student.setWeight(weight);
        student.setBloodType(bloodType);
        student.setDay(day);
        student.setMonth(month);
        student.setYear(year);
        student.setGender(gender);
        student.setMotherId("null");
        student.setPerformance("");
        student.setClassID(selectedClass.getID());
        student.setClassName(selectedClass.getName());
        student.setFullName();

    }
    //@Override
    public void onClick(View v) {
        String firstNameVer = firstName.getText().toString();
        String middleNameVer = middleName.getText().toString();
        String lastNameVer = lastName.getText().toString();
        String nationalIdVer = nationalId.getText().toString();
        String heightVer = heightText.getText().toString();
        String weightVer = weightText.getText().toString();

        bloodType = bloodTypeSpinner.getSelectedItem().toString();
        day = daySpinner.getSelectedItem().toString();
        month = monthSpinner.getSelectedItem().toString();
        year = yearSpinner.getSelectedItem().toString();
        gender = genderSpinner.getSelectedItem().toString();

        if (gender == "ولد")
            gender = "boy";
        else
            gender = "girl";


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
            nationalId.setError("يجب أن يتكون السجل المدني من ١٠ أرقام فقط");
            nationalId.requestFocus();
            return;
        }
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
        String id = ref.push().getKey();
        student.setStId(id);
        ref.child(id).setValue(student);
        Toast.makeText(getContext(),"تم إضافة الطالب",Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frame, studentsFragment).commit();


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
