package com.example.improvedhouse;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Houses_list2 extends AppCompatActivity {

    ListView house_list2;
    DatabaseReference dbr;
    List<Houselist_Beenclass> Houselist2;
    private SearchView search;
    Activity activity;
    Houselist_Adapter2 mhouselistadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses_list2);

        Houselist2=new ArrayList<>();
        house_list2 = (ListView) findViewById(R.id.houseList);

        dbr= FirebaseDatabase.getInstance().getReference();
        Query query =dbr
                .child("Users").child(UserDetails.username).child("Houses");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Houselist2.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Houselist_Beenclass keyValue=dataSnapshot1.getValue(Houselist_Beenclass.class);
                    keyValue.setkey(dataSnapshot1.getKey());

                    Houselist2.add(keyValue);
                }
                mhouselistadapter=new Houselist_Adapter2(Houses_list2.this,Houselist2);
                house_list2.setAdapter(mhouselistadapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
