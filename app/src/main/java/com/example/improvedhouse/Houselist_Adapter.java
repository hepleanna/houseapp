package com.example.improvedhouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joanna Ruth on 05-Jul-18.
 */

public class Houselist_Adapter extends BaseAdapter implements Filterable {

    Context c;
    private Activity activity;
    List<Houselist_Beenclass> HouselistBeenvalues;
    ArrayList<Houselist_Beenclass> originalarray,temparray;
    Houselist_Adapter  mhouselistadapter;
    DatabaseReference dbr;
    CustomFilter cs;

    public Houselist_Adapter(Context c, List<Houselist_Beenclass> HouselistBeenvalues){

        this.c=c;
       // this.activity=activity;
        this.HouselistBeenvalues=HouselistBeenvalues;

        this.originalarray = new ArrayList<Houselist_Beenclass>();
        this.originalarray.addAll(HouselistBeenvalues);
        this.temparray = new ArrayList<Houselist_Beenclass>();
        this.temparray.addAll(HouselistBeenvalues);

    }

    @Override
    public int getCount() {

        return this.originalarray.size();
    }

    @Override
    public Object getItem(int i) {

        return originalarray.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent){

//        LayoutInflater li=activity.getLayoutInflater();
//        View v=li.inflate(R.layout.houselist_card,null,true);

        View v = convertView;
        if (v == null) {
//            v = LayoutInflater.from().inflate(
//                    R.layout.houselist_card, parent, false);
                v=LayoutInflater.from(c).inflate(R.layout.houselist_card,parent,false);

        }


        TextView houseName=(TextView)v.findViewById(R.id.tv_houseName);
        TextView typeofhouse=(TextView)v.findViewById(R.id.tv_typeofhouse);
        TextView address=(TextView)v.findViewById(R.id.tv_address);
        TextView rentamount=(TextView)v.findViewById(R.id.tv_rentamount);
        TextView leaserentsale=(TextView)v.findViewById(R.id.tv_lease_rent_sale);
        TextView youremail=(TextView)v.findViewById(R.id.tv_youremail);
        ImageView imageview=(ImageView)v.findViewById(R.id.personImage);
        Button email =(Button)v.findViewById(R.id.btn_email);
        dbr= FirebaseDatabase.getInstance().getReference("HousesList");

        houseName.setText(/*"HOUSE NAME     :"+*/originalarray.get(position).getHousename());
        typeofhouse.setText(/*"TYPEOF HOUSE :"+*/originalarray.get(position).getHousetype());
        address.setText(/*"HOUSE ADDRESS     :"+*/originalarray.get(position).getHouseaddress());
        rentamount.setText(/*"HOUSE RENT AMOUNT :"+*/originalarray.get(position).getRentamount());
        leaserentsale.setText(/*"LEASE/RENT/SALE :"+*/originalarray.get(position).getLease_rent_sale());
        youremail.setText(/*"YOUR EMAIL:"+*/originalarray.get(position).getYouremail());

        String image=originalarray.get(position).getImage();

//        Glide.with(c)
//                .load(image))
//                .into(imageview);
        Glide.with(c).load(image).into(imageview);


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailId=originalarray.get(position).getYouremail();
                String split_first = emailId.substring(0,emailId.indexOf("@"));
                UserDetails.chatWith =  split_first;

                Intent i = new Intent(c, Chat.class);


                c.startActivity(i);

               // c.finish();
            }
        });



        return v;
    }

    @Override
    public Filter getFilter() {

        if(cs==null){
            cs=new CustomFilter();

        }

        return cs;

    }


    class CustomFilter extends Filter{

        protected FilterResults performFiltering(CharSequence constraint){

            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0)
            {

                constraint=constraint.toString().toUpperCase();
                ArrayList<Houselist_Beenclass> filters=new ArrayList<>();
                for(int i=0;i<temparray.size();i++)
                {
                    if(temparray.get(i).getHousetype().toUpperCase().contains(constraint))
                    {

                        Houselist_Beenclass singleRow =new Houselist_Beenclass(temparray.get(i).getHousename(),temparray.get(i).getHousetype(),
                                temparray.get(i).getHouseaddress(),temparray.get(i).getRentamount(),temparray.get(i).getLease_rent_sale(),
                                temparray.get(i).getYouremail(),temparray.get(i).getImage());

                        filters.add(singleRow);
                    }

                    else if(temparray.get(i).getHouseaddress().toUpperCase().contains(constraint)){
                        Houselist_Beenclass singleRow =new Houselist_Beenclass(temparray.get(i).getHousename(),temparray.get(i).getHousetype(),
                                temparray.get(i).getHouseaddress(),temparray.get(i).getRentamount(),temparray.get(i).getLease_rent_sale(),
                                temparray.get(i).getYouremail(),temparray.get(i).getImage());

                        filters .add(singleRow);
                    }

                    else if(temparray.get(i).getRentamount().toUpperCase().contains(constraint)){
                        Houselist_Beenclass singleRow =new Houselist_Beenclass(temparray.get(i).getHousename(),temparray.get(i).getHousetype(),
                                temparray.get(i).getHouseaddress(),temparray.get(i).getRentamount(),temparray.get(i).getLease_rent_sale(),
                                temparray.get(i).getYouremail(),temparray.get(i).getImage());
                        filters .add(singleRow);

                    }
                    else if(temparray.get(i).getLease_rent_sale().toUpperCase().contains(constraint)){
                        Houselist_Beenclass singleRow =new Houselist_Beenclass(temparray.get(i).getHousename(),temparray.get(i).getHousetype(),
                                temparray.get(i).getHouseaddress(),temparray.get(i).getRentamount(),temparray.get(i).getLease_rent_sale(),
                                temparray.get(i).getYouremail(),temparray.get(i).getImage());
                        filters .add(singleRow);

                    }






                }
                results.count=filters.size();
                results.values=filters;

            }
            else{

                results.count=temparray.size();
                results.values=temparray;
            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            originalarray=(ArrayList<Houselist_Beenclass>)filterResults.values;
            notifyDataSetChanged();


        }
    }


}
