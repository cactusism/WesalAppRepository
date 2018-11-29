package com.shaden.wesal;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.style.TypefaceSpan;
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
 * {@link PerformanceChildFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerformanceChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerformanceChildFragment extends Fragment {

    TextView performance,title;
    DatabaseReference ref;
    students student;
    Button cancelBtn;
    Typeface typeface;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PerformanceChildFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerformanceChildFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerformanceChildFragment newInstance(String param1, String param2) {
        PerformanceChildFragment fragment = new PerformanceChildFragment();
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

        View v = inflater.inflate(R.layout.fragment_performance_child, container, false);

        getActivity().setTitle("الأداء الأكاديمي");

        title = (TextView)v.findViewById(R.id.myChTitle);
        performance = (TextView) v.findViewById(R.id.allstd);
        cancelBtn = (Button)v.findViewById(R.id.cancelBtn);
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");
        title.setTypeface(typeface);
        performance.setTypeface(typeface);
        cancelBtn.setTypeface(typeface);


        ref =  FirebaseDatabase.getInstance().getReference().child("students").child(MotherHomePage.getChildId());
        student = new students();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students std = dataSnapshot.getValue(students.class);
                if(std.getPerformance().equals("")){
                    performance.setText("عذرا لا يوجد أداء أكاديمي لطفلك حتى الآن");
                }
                else{
                    performance.setText(std.getPerformance());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mother_main_frame, new childProfileFragment());
                ft.commit();
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
