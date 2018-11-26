package com.shaden.wesal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import java.util.Collection;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment implements NotificationDialog.NotificationDialogListener {

    SwipeMenuListView listView;
    List<notifications> notsList;
    NotificationListAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference ref;
    notifications not;
    TextView noNots;
    Typeface typeface;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    @Override
    public void applyTexts(String subject, String notification) {

    }

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView addBTN;


    private OnFragmentInteractionListener mListener;

    public NotificationsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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

        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        database = FirebaseDatabase.getInstance();
        ref=  database.getReference().child("notifications");

        getActivity().setTitle("إعلانات المدرسة");

        typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/GE_SS_Two_Light.otf");
        not = new notifications();
        listView = (SwipeMenuListView) v.findViewById(R.id.notsList);
        notsList = new ArrayList<>();
        noNots = (TextView) v.findViewById(R.id.noNots);
        noNots.setTypeface(typeface);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notsList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                        not = ds.getValue(notifications.class);
                        notsList.add(0, not);
                }
                adapter = new NotificationListAdapter(getContext(), R.layout.not_info,notsList);
                listView.setAdapter(adapter);
                if(notsList.isEmpty()){
                    noNots.setText("لا يوجد تنبيهات حاليّا");
                }


                SwipeMenuCreator creator = new SwipeMenuCreator() {

                    @Override
                    public void create(SwipeMenu menu) {
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getContext());
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                                0x00, 0x00)));
                        // set item width
                        deleteItem.setWidth(230);
                        // set a icon
                        deleteItem.setIcon(R.drawable.rubbish);
                        // add to menu
                        menu.addMenuItem(deleteItem);
                    }
                };
                listView.setMenuCreator(creator);

                listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("هل أنت متأكد من حذف التنبيه؟");
                        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("notifications").child(notsList.get(position).getNotId());
                                ref.removeValue();
                                //notifyDataSetChanged();
                                Toast.makeText(getContext(),"تم حذف التنبيه بنجاح",Toast.LENGTH_LONG).show();

                            }
                        });
                        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        // false : close the menu; true : not close the menu
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addBTN = (ImageView) v.findViewById(R.id.addNotificationBTN);
        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.main_frame, addNotFragment ).commit();
            }

        });
        // Inflate the layout for this fragment

        return v;
    }

    public void openDialog(){
        NotificationDialog ND = new NotificationDialog();
        ND.show(getFragmentManager(), "ND");
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
