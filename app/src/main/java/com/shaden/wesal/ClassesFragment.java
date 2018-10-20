package com.shaden.wesal;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    public ClassesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_classes, container, false);
        classes = new Classes();
        listView = (ListView) v.findViewById(R.id.classesList);
    database = FirebaseDatabase.getInstance();
    ref = database.getReference("classes");
    list = new ArrayList<>();
    adapter = new ArrayAdapter<String>(getContext(), R.layout.class_info,R.id.classInfo,list);
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds:dataSnapshot.getChildren())
            {
                classes = ds.getValue(Classes.class);
                list.add(classes.getName().toString()+"  Teacher:"+classes.getTeacher().toString());
            }
            listView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

        return v;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
