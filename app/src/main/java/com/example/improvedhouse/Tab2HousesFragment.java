package com.example.improvedhouse;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab2HousesFragment extends Fragment implements TextWatcher {

    ListView house_list;
    DatabaseReference dbr;
    List<Houselist_Beenclass> Houselist;
    private SearchView search;
    Activity activity;
    Houselist_Adapter  mhouselistadapter;
    EditText sbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2houses, container, false);

       // dbr= FirebaseDatabase.getInstance().getReference("Houses");
        Houselist=new ArrayList<>();
        house_list = (ListView) rootView.findViewById(R.id.houseList);

        dbr= FirebaseDatabase.getInstance().getReference("HousesList");
        Houselist=new ArrayList<>();
        house_list = (ListView) rootView.findViewById(R.id.houseList);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Houselist.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Houselist_Beenclass keyValue=dataSnapshot1.getValue(Houselist_Beenclass.class);

                    Houselist.add(keyValue);
                }
                mhouselistadapter=new Houselist_Adapter(getContext(),Houselist);
                house_list.setAdapter(mhouselistadapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sbar= (EditText) rootView.findViewById(R.id.sbar);
        sbar.addTextChangedListener(this);


        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mhouselistadapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}
