package com.shaden.wesal;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studentPAM extends Fragment {
    TextView studentName, txtProfile, txtPerformance, txtMsg;
    DatabaseReference ref;
    FirebaseDatabase database;
    ImageView profile, performance, chat, backBtn;
    ClassStudentsFragment classStudentsFragment;
    StudentPersonalInformationFragment studentPersonalInformationFragment;
    StaffChatFragment staffChatFragment;
    StudentPerformanceFragment studentPerformanceFragment;
    String motherId;
    Typeface typeface;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public studentPAM() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static studentPAM newInstance(String param1, String param2) {
        studentPAM fragment = new studentPAM();
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
        View v = inflater.inflate(R.layout.fragment_student_pam, container, false);

        getActivity().setTitle("ملف الطالب");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("students").child(StaffHomePage.getClassStudentId());
        classStudentsFragment = new ClassStudentsFragment();
        studentPerformanceFragment = new StudentPerformanceFragment();
        staffChatFragment = new StaffChatFragment();
        studentPersonalInformationFragment = new StudentPersonalInformationFragment();
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");

        txtProfile = (TextView) v.findViewById(R.id.txtAlbum);
        txtPerformance = (TextView) v.findViewById(R.id.txtAllstd);
        txtMsg = (TextView)v.findViewById(R.id.txtMsg);
        studentName = (TextView) v.findViewById(R.id.studentName2);
        profile = (ImageView) v.findViewById(R.id.album);
        performance = (ImageView) v.findViewById(R.id.allstd);
        chat = (ImageView) v.findViewById(R.id.star);
        backBtn = (ImageView) v.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClassFragment.isBackFromStar = false;
                MyClassFragment.isBackFromList = true;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, new MyClassFragment()).commit();
            }
        });

        studentName.setTypeface(typeface);
        txtMsg.setTypeface(typeface);
        txtProfile.setTypeface(typeface);
        txtPerformance.setTypeface(typeface);

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
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaffHomePage.setChatStudentId(StaffHomePage.getClassStudentId());


                Intent i = new Intent(getActivity(), MessageActivity.class );
                Bundle bundle = new Bundle();
                bundle.putString("userid", StaffHomePage.getClassStudentId());
                i.putExtras(bundle);
                startActivity(i);

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students std = dataSnapshot.getValue(students.class);
                studentName.setText(std.getFirstname()+" "+std.getLastname());
                setMotherId(std.getMotherId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    private void setMotherId(String motherId) {
        this.motherId = motherId;
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