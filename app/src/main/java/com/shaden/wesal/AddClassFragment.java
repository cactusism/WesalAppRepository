package com.shaden.wesal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClassFragment extends Fragment implements View.OnClickListener {

    EditText name;
    FirebaseDatabase database;
    DatabaseReference ref, staffRef;
    Classes classes;
    Button addClassBtn, cancelClassBtn;
    ClassesFragment classesFragment;
    Spinner teachersSpinner;
    staff stf, selectedStf;
    ArrayList<staff> staffList;
    ArrayAdapter<staff> stfAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClassFragment newInstance(String param1, String param2) {
        AddClassFragment fragment = new AddClassFragment();
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

        View v = inflater.inflate(R.layout.fragment_add_class, container, false);

        getActivity().setTitle("إضافة فصل");


        name= (EditText) v.findViewById(R.id.name_text);
        addClassBtn = (Button) v.findViewById(R.id.add_class_btn);
        cancelClassBtn = (Button) v.findViewById(R.id.cancelBtn);
        teachersSpinner = (Spinner) v.findViewById(R.id.teachersSpinner);
        classesFragment = new ClassesFragment();
        staffList = new ArrayList<>();
        stfAdapter = new ArrayAdapter<staff>(getContext(), android.R.layout.simple_spinner_item, staffList);
        stf = new staff();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("classes");
        staffRef = database.getReference("staff");
        classes = new Classes();

        staffRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    stf = ds.getValue(staff.class);
                   if(stf.getAssigned().equals("null"))
                    staffList.add(stf);

                }
                stfAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                teachersSpinner.setAdapter(stfAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedStf = (staff) teachersSpinner.getSelectedItem();
                String nameVer = name.getText().toString();

                if(nameVer.isEmpty()){
                    name.setError("حقل اسم الفصل ممطلوب");
                    name.requestFocus();
                    return;
                }




                getValues();
                String id = ref.push().getKey();
                classes.setID(id);
                classes.setStarOfTheWeekId("");
                classes.setStarOfTheWeekName("");
                ref.child(id).setValue(classes);



                Toast.makeText(getContext(),"تم إضافة الفصل",Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, classesFragment).commit();



            }
        });



        cancelClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, classesFragment).commit();
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

    public void getValues()
    {
        classes.setName(name.getText().toString());
        classes.setTeacherID(selectedStf.getId());
        classes.setTeacher(selectedStf.getName());
    }

    @Override
    public void onClick(View v) {
        String nameVer = name.getText().toString();

        if (nameVer.isEmpty()) {
            name.setError("حقل اسم الفصل ممطلوب");
            name.requestFocus();
            return;
        }

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
