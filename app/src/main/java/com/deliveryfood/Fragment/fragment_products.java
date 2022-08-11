package com.deliveryfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.deliveryfood.R;

public class fragment_products extends Fragment {
    @Nullable
    View result;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.fragment_products,container,false);
        return result;
    }
}
