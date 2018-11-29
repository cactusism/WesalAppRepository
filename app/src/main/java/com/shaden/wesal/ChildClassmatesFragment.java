package com.shaden.wesal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
 * {@link ChildClassmatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChildClassmatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildClassmatesFragment extends Fragment {

    FirebaseUser fuser;
    String motherId;
    DatabaseReference ref, classStdRef;
    students student;
    String classId;
    TextView noStudents;


    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChildClassmatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildClassmatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildClassmatesFragment newInstance(String param1, String param2) {
        ChildClassmatesFragment fragment = new ChildClassmatesFragment();
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
        View v = inflater.inflate(R.layout.fragment_child_classmates, container, false);
        getActivity().setTitle("طلاب الفصل");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        motherId = fuser.getUid();
        ref = FirebaseDatabase.getInstance().getReference("students");
        classStdRef = FirebaseDatabase.getInstance().getReference("students");
        noStudents = (TextView) v.findViewById(R.id.noStudents);
        list = new ArrayList<>();
        listView = (ListView) v.findViewById(R.id.studentsList);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.onestudent,R.id.studentInfo,list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    student = ds.getValue(students.class);
                    if (student.getMotherId().equals(motherId)){
                        classId = student.getClassID();
                    }
                }
                classStdRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            student = ds.getValue(students.class);
                            if(student.getClassID().equals(classId) && !student.getMotherId().equals(motherId)){
                                list.add(student.getFirstname()+" "+student.getLastname());
                            }
                        }
                       listView.setAdapter(adapter);
                       if(list.isEmpty()){
                           noStudents.setText("لا يوجد طلاب حاليا");
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
