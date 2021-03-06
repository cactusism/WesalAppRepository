package com.shaden.wesal;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * {@link MotherStarOfWeekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MotherStarOfWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotherStarOfWeekFragment extends Fragment {
    FirebaseUser fuser;
    String motherId, classId;
    DatabaseReference stdRef, clsRef;
    students student;
    Classes classes;
    TextView starOfWeekTxt,starTitle;
    Button back;
    Typeface typeface;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MotherStarOfWeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MotherStarOfWeekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MotherStarOfWeekFragment newInstance(String param1, String param2) {
        MotherStarOfWeekFragment fragment = new MotherStarOfWeekFragment();
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
        View v = inflater.inflate(R.layout.fragment_mother_star_of_week, container, false);
        getActivity().setTitle("نجم الأسبوع");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        motherId = fuser.getUid();
        starOfWeekTxt = (TextView) v.findViewById(R.id.starOfWeek);
        starTitle = (TextView) v.findViewById(R.id.starTitle);
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");
        starTitle.setTypeface(typeface);
        starOfWeekTxt.setTypeface(typeface);

        stdRef = FirebaseDatabase.getInstance().getReference("students");
        back = (Button) v.findViewById(R.id.back);
        back.setTypeface(typeface);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mother_main_frame, new childClassFragment()).commit();
            }
        });

        stdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    student = ds.getValue(students.class);
                    if(student.getMotherId().equals(motherId)){
                        classId = student.getClassID();
                    }

                }
                clsRef = FirebaseDatabase.getInstance().getReference("classes").child(classId);
                clsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        classes = dataSnapshot.getValue(Classes.class);
                        if(classes.getStarOfTheWeekId().equals("")){
                            starOfWeekTxt.setText("لا يوجد نجم حاليا");
                        }
                        else{
                            starOfWeekTxt.setText(classes.getStarOfTheWeekName());
                        }
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
