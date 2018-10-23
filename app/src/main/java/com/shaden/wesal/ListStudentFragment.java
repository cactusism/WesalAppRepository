package com.shaden.wesal;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListStudentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SwipeMenuListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    List<students> studentsList;
    studentsListAdapter adapter;
    students student;
    TextView noStudents;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListStudentFragment() {
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

        database = FirebaseDatabase.getInstance();
        ref=  database.getReference().child("students");
        View v = inflater.inflate(R.layout.fragment_list_student, container, false);
        student = new students();
        listView = (SwipeMenuListView) v.findViewById(R.id.studentsList);
        studentsList = new ArrayList<>();
        noStudents = (TextView) v.findViewById(R.id.noStudents);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    student = ds.getValue(students.class);
                    studentsList.add( student);
                }
                adapter = new studentsListAdapter(getContext(), R.layout.onestudent,studentsList);
                listView.setAdapter(adapter);

                if(studentsList.isEmpty()){
                    noStudents.setText("لا يوجد طلاب حاليّا");
                }


                SwipeMenuCreator creator = new SwipeMenuCreator() {

                    @Override
                    public void create(SwipeMenu menu) {
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getContext());
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0x00,
                                0x66, 0xFF)));
                        // set item width
                        deleteItem.setWidth(230);
                        // set a icon
                        deleteItem.setIcon(R.drawable.information);
                        // add to menu
                        menu.addMenuItem(deleteItem);
                    }
                };
                listView.setMenuCreator(creator);

                listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.main_frame, new StudentInfoFragment());
                        ft.commit();

                        // false : close the menu; true : not close the menu
                        return false;
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
