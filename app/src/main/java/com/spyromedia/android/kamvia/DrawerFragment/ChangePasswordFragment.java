package com.spyromedia.android.kamvia.DrawerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spyromedia.android.kamvia.CheckInt;
import com.spyromedia.android.kamvia.R;

public class ChangePasswordFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chang_password,container,false);

//        Button test = view.findViewById(R.id.test);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), CheckInt.class);
//                startActivity(intent);
//            }
//        });

        return  view;
    }
}
