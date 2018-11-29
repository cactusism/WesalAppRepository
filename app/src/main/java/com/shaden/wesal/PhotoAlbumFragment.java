package com.shaden.wesal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotoAlbumFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotoAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoAlbumFragment extends Fragment {
    private Button btnUpload, btnSelect;
    private ImageView imageView;
    private EditText txtImgTitle;
    private ProgressBar progressBar;
    private TextView showImgs;

    private final int PICK_IMAGE_REQUEST = 71;

    private Uri filePath;


    private FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseReference database;
    private StorageTask uploadTask;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PhotoAlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoAlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoAlbumFragment newInstance(String param1, String param2) {
        PhotoAlbumFragment fragment = new PhotoAlbumFragment();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            Picasso.with(getContext()).load(filePath).into(imageView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photo_album, container, false);

        getActivity().setTitle("ألبوم الصور");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance().getReference("images/"+StaffHomePage.getClassId());

        showImgs =(TextView) v.findViewById(R.id.text_view_show_imgs);
        btnUpload = (Button)v.findViewById(R.id.uploadBtn);
        btnSelect = (Button)v.findViewById(R.id.selectBtn);
        imageView = (ImageView)v.findViewById(R.id.imgView);
        txtImgTitle = (EditText) v.findViewById(R.id.edit_text_img_title);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"اختيار صورة"),PICK_IMAGE_REQUEST);
            }
        });



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getContext(),"رفع الصورة تحت التنفيذ",Toast.LENGTH_SHORT).show();
                }else {
                    if (filePath != null) {

                        StorageReference ref = storageRef.child("images/" + StaffHomePage.getClassId() + "/" + UUID.randomUUID().toString());
                        uploadTask = ref.putFile(filePath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot task) {
                                        Task<Uri> urlTask = task.getStorage().getDownloadUrl();
                                        while (!urlTask.isSuccessful());
                                        Uri downloadUrl = urlTask.getResult();
                                        final String sdownloadurl = String.valueOf(downloadUrl);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setProgress(0);
                                            }
                                        }, 5000);
                                        Toast.makeText(getContext(), "تم رفع الصورة", Toast.LENGTH_LONG).show();
                                        Upload upload = new Upload(txtImgTitle.getText().toString().trim(), sdownloadurl);
                                        String uploadId = database.push().getKey();
                                        database.child(uploadId).setValue(upload);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "فشل رفع الصورة" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        progressBar.setProgress((int) progress);
                                    }
                                });

                    } else
                        Toast.makeText(getContext(), "لم يتم اختيار صورة", Toast.LENGTH_SHORT).show();
            } // end else not in progress
        }
        });

        showImgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ViewAlbumFragment());
                fragmentTransaction.commit();

            }
        });

        return v;
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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
