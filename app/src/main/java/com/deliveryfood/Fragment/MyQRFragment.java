package com.deliveryfood.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.deliveryfood.MainActivity;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lombok.SneakyThrows;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyQRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyQRFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imgMyQR;
    private static int REQUEST_CODE = 100;
    OutputStream outputStream;

    public MyQRFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyQRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyQRFragment newInstance(String param1, String param2) {
        MyQRFragment fragment = new MyQRFragment();
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

    MainActivity mainActivity;
    BitmapDrawable drawable;
    Bitmap bitmap;

    @SneakyThrows
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_q_r, container, false);
        imgMyQR = view.findViewById(R.id.imgMyQR);
        mainActivity = (MainActivity) getActivity();

//        Picasso.get().load("https://res.cloudinary.com/dtri2001/image/upload/v1663591342/IMG/Milk%20tea/Avt_l1rwgo.jpg").into(imgMyQR);
        Picasso.get().load(mainActivity.getQRCode()).into(imgMyQR);

        ImageButton shareBTN = view.findViewById(R.id.shareBTN);
        shareBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareQRCode();
            }
        });

        ImageButton saveBTN = view.findViewById(R.id.saveBTN);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveIMG();
                } else {
                    askPermission();
                }
            }
        });
        return view;
    }



    private void saveIMG() {

        File dir = new File(Environment.getExternalStorageDirectory(), "SaveImage");
        if (!dir.exists()) {
            dir.mkdir();
        }
        BitmapDrawable drawable = (BitmapDrawable) imgMyQR.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(getContext(), "Successfuly Saved", Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void askPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

    }

    private void shareQRCode() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        drawable = (BitmapDrawable) imgMyQR.getDrawable();
        bitmap = drawable.getBitmap();
        File file = new File(getContext().getExternalCacheDir() + "/" + "Beautiful Picture" + ".png");
        Intent intent;

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(intent, "Share image Via: "));
    }


}