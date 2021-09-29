package com.example.improvedhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.auth.FirebaseAuth;

public class Tab3ProfileFragment extends Fragment {


    private FirebaseAuth firebaseAuth;
    private TextView buttonLogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3profile, container, false);
        TextView buttonLogout = rootView.findViewById(R.id.buttonLogout);
        TextView addHouse = rootView.findViewById(R.id.addHouse);
        TextView myHouse = rootView.findViewById(R.id.myHouse);
        TextView Name = rootView.findViewById(R.id.Name);

        Name.setText(UserDetails.username);

        firebaseAuth= FirebaseAuth.getInstance();


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getContext(),Login.class));
            }
        });

        addHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // getActivity().finish();
                startActivity(new Intent(getContext(),HouseRegistration.class));
            }
        });

        myHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //getActivity().finish();
                startActivity(new Intent(getContext(),Houses_list2.class));
            }
        });

        return rootView;
    }
}
