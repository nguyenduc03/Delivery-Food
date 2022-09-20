package com.deliveryfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.deliveryfood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NonUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NonUserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button profile_btn_dn;
    Button profile_btn_dk;
    View view;


    public NonUserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NonUserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NonUserProfileFragment newInstance(String param1, String param2) {
        NonUserProfileFragment fragment = new NonUserProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_non_user_profile, container, false);
        anhXa();
        ChuyenHuong();
        return view;
    }

    private void ChuyenHuong() {
        profile_btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);

                fragmentTransaction.replace(R.id.fragmentContainerView, new RegisterFragment());
                fragmentTransaction.addToBackStack("Fragment Detail");
                fragmentTransaction.commit();
            }
        });
        profile_btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);

                fragmentTransaction.replace(R.id.fragmentContainerView, new fragment_login());
                fragmentTransaction.addToBackStack("Fragment Detail");
                fragmentTransaction.commit();
            }
        });

    }

    private void anhXa() {
        profile_btn_dk = view.findViewById(R.id.profile_btn_dk);
        profile_btn_dn = view.findViewById(R.id.profile_btn_dn);

    }

}