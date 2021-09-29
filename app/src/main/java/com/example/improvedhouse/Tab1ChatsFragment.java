package com.example.improvedhouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Tab1ChatsFragment extends Fragment {

    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> al1 = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    DatabaseReference dbr;
    Firebase reference1;
    String name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1chats, container, false);

        usersList = (ListView) rootView.findViewById(R.id.usersList);
        noUsersText = (TextView) rootView.findViewById(R.id.noUsersText);
        dbr= FirebaseDatabase.getInstance().getReference();
      //  reference1= FirebaseDatabase.getInstance().getReference();
        Firebase.setAndroidContext(getContext());
        reference1 = new Firebase("https://improvedhouse.firebaseio.com/Users/" + UserDetails.username+"/messages");

        Query query =dbr
                .child("Users");

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://improvedhouse.firebaseio.com/Users/"+UserDetails.username +"/messages.json";
        Log.d("Success", "I am here ");



        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);


        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                name=al.get(position);
                UserDetails.chatWith = name.substring(0,name.indexOf(" "));
                Log.d("chatwith",UserDetails.chatWith);
                reference1.child(UserDetails.chatWith).child("msg").setValue(" ");
               // reference1.child("Users").child(UserDetails.username).child("messages").child(UserDetails.chatWith).child("msg").setValue("read");
                startActivity(new Intent(getContext(), Chat.class));
            }
        });
        return rootView;
    }

    public void doOnSuccess(String s){

        try {

            al.clear();
            al1.clear();
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            Iterator j = obj.keys();
            String key = "";
            String temp;

            while(j.hasNext()){
              key = j.next().toString();
                temp=obj.getJSONObject(key).getString("time")+"/"+key+"  "+obj.getJSONObject(key).getString("msg");
                Log.d("message",temp);
                al1.add(temp);

            }
            Collections.sort(al1);
            for(int k=0;k<al1.size();k++) {

                temp=al1.get(k);
                al.add(temp.substring(temp.lastIndexOf("/")+1,temp.length()));
                totalUsers++;
                Log.d("message-name", al.get(k));

            }
//                while (i.hasNext()) {
//
//                    key = i.next().toString();
//                    if(!key.equals(UserDetails.username))
//
//                      //  al.add(key);
//                  //  Log.d("message-name",al1.get(k));
//
//                    totalUsers++;
//
//
//            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{

            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            Collections.reverse(al);
            usersList.setAdapter(new ArrayAdapter<String>(getView().getContext(), android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }

}