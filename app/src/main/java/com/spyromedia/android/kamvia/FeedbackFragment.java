package com.spyromedia.android.kamvia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FeedbackFragment extends Fragment {
    TextInputEditText email, emailbody;
    MaterialButton sendbtn;
    String email_text, email_body_text;
    String user_id;
    String format;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
//        email = v.findViewById(R.id.emailtext);
        emailbody = v.findViewById(R.id.emailbody_text);
        sendbtn = v.findViewById(R.id.emailsent);
        user_id = Globals.currentUser.USER_ID;
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        format = s.format(new Date());
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                email_text = email.getText().toString();
                email_body_text = emailbody.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String subject = "Kamvia Feedback of USER:-" + user_id + " at " + format;
                String uriText = "mailto:" + Uri.encode("rahulrajan.rr18@gmail.com") +
                        "?subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(email_body_text);
                Uri uri = Uri.parse(uriText);
                intent.setData(uri);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        return v;
    }
}