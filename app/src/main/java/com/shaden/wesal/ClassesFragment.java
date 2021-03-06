package com.shaden.wesal;


import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassesFragment extends Fragment {


    FirebaseDatabase database;
    DatabaseReference ref;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;
    Classes classes;
    ImageButton addBtn;
    AddClassFragment addClassFragment;
    TextView noClasses, classInfo;
    ArrayList<Classes> allClasses;
    StudentsFragment studentsFragment;
    Typeface typeface;

    public ClassesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_classes, container, false);

        getActivity().setTitle(" قائمة الفصول");


        classes = new Classes();
        allClasses= new ArrayList<>();
        addClassFragment = new AddClassFragment();
        studentsFragment = new StudentsFragment();
        addBtn = (ImageButton)v.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, addClassFragment).commit();
            }
        });

        listView = (ListView) v.findViewById(R.id.classesList);
        noClasses = (TextView) v.findViewById(R.id.noClasses);
        classInfo = (TextView) v.findViewById(R.id.classInfo);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("classes");
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(), R.layout.class_info,R.id.classInfo,list);
        ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds:dataSnapshot.getChildren())
            {
                classes = ds.getValue(Classes.class);
                allClasses.add(classes);
                list.add("الفصل:   "+ classes.getName().toString()+"\n المعلمة:  "+classes.getTeacher().toString());
            }
            listView.setAdapter(adapter);
            if(list.isEmpty()){
                noClasses.setText("لا يوجد فصول حاليّا");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaffHomePage.setClassId(allClasses.get(position).getID());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame, studentsFragment ).commit();

            }
        });

        return v;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
