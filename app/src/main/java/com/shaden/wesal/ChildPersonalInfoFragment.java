package com.shaden.wesal;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChildPersonalInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChildPersonalInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildPersonalInfoFragment extends Fragment {


    TextView name, date, height, weight, blood,dateTitle,heightTitle,weightTitle,bloodTitle;
    Button cancel;

    FirebaseDatabase database;
    DatabaseReference ref;
    students student;
    childProfileFragment profileFragment;
    Typeface typeface;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChildPersonalInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildPersonalInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildPersonalInfoFragment newInstance(String param1, String param2) {
        ChildPersonalInfoFragment fragment = new ChildPersonalInfoFragment();
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

        View v = inflater.inflate(R.layout.fragment_child_personal_info, container, false);

        getActivity().setTitle(" قائمة الفصول");

        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");
        name = (TextView) v.findViewById(R.id.NameTxt);
        date = (TextView) v.findViewById(R.id.DateTxt);
        weight =(TextView) v.findViewById(R.id.WeightTxt);
        height =(TextView) v.findViewById(R.id.HeightTxt);
        blood=(TextView) v.findViewById(R.id.BloodTxt);
        dateTitle=(TextView) v.findViewById(R.id.DateTitle);
        heightTitle=(TextView) v.findViewById(R.id.HeightTitle);
        weightTitle=(TextView) v.findViewById(R.id.WeightTitle);
        bloodTitle=(TextView) v.findViewById(R.id.BloodTitle);

        name.setTypeface(typeface);
        dateTitle.setTypeface(typeface);
        heightTitle.setTypeface(typeface);
        weightTitle.setTypeface(typeface);
        bloodTitle.setTypeface(typeface);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("students").child(MotherHomePage.getChildId());
        student = new students();
        profileFragment = new childProfileFragment();

        cancel = (Button) v.findViewById(R.id.cancleProfileBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mother_main_frame, profileFragment);
                ft.commit();
            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students std = dataSnapshot.getValue(students.class);
                if(std.getGender().equals("boy"))
                    name.setText(std.getFirstname()+" "+ std.getMiddleName()+" "+std.getLastname());
                else
                    name.setText(std.getFirstname()+" "+std.getMiddleName()+" "+std.getLastname());
                date.setText(std.getDay()+"/"+std.getMonth()+"/"+std.getYear());
                weight.setText(String.valueOf(std.getWeight()));
                height.setText(String.valueOf(std.getHeight()));
                blood.setText(std.getBloodType());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





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
