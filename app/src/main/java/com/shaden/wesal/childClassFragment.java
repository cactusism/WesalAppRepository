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
 * {@link childClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link childClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class childClassFragment extends Fragment {

    TextView className, txtAlbum, txtAllstd, txtStar;
    DatabaseReference ref;
    FirebaseDatabase database;
    ImageView album, allStd, star;
    Typeface typeface;
    MotherStarOfWeekFragment motherStarOfWeekFragment;
    MotherPhotoAlbumFragment motherPhotoAlbumFragment;
    ChildClassmatesFragment childClassmatesFragment;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public childClassFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment childClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static childClassFragment newInstance(String param1, String param2) {
        childClassFragment fragment = new childClassFragment();
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
        View v = inflater.inflate(R.layout.fragment_child_class, container, false);


        getActivity().setTitle("فصل الطالب");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("classes").child(MotherHomePage.childClass);
        childClassmatesFragment = new ChildClassmatesFragment();
        motherPhotoAlbumFragment= new MotherPhotoAlbumFragment();
        motherStarOfWeekFragment= new MotherStarOfWeekFragment();

        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");

        txtAlbum = (TextView) v.findViewById(R.id.txtAlbum);
        txtAllstd = (TextView) v.findViewById(R.id.txtAllstd);
        txtStar = (TextView)v.findViewById(R.id.txtStar);
        className = (TextView) v.findViewById(R.id.className);
        album = (ImageView) v.findViewById(R.id.album);
        allStd = (ImageView) v.findViewById(R.id.allstd);
        star = (ImageView) v.findViewById(R.id.star);

        txtAlbum.setTypeface(typeface);
        txtAllstd.setTypeface(typeface);
        txtStar.setTypeface(typeface);
        className.setTypeface(typeface);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Classes classes = dataSnapshot.getValue(Classes.class);
                className.setText(classes.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mother_main_frame, motherStarOfWeekFragment);
                fragmentTransaction.commit();
            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mother_main_frame, motherPhotoAlbumFragment);
                fragmentTransaction.commit();
            }
        });

        allStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mother_main_frame, childClassmatesFragment);
                fragmentTransaction.commit();
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
