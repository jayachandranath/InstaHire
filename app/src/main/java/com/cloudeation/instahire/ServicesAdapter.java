package com.cloudeation.instahire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServicesAdapter extends ArrayAdapter<ServicesList> {
    private Context context;
    private int resourceId;
    private List<ServicesList>  servicesLists;

    public ServicesAdapter(@NonNull Context context, int resource, @NonNull List<ServicesList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
        this.servicesLists = objects;
    }

    @Override
    public int getCount() {
        return servicesLists.size();

    }

    @Nullable
    @Override
    public ServicesList getItem(int position) {

        return servicesLists.get(position);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try{
            if (view==null){

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(resourceId,parent,false);

                ServicesList servicelist = servicesLists.get(position);
                if (servicelist!=null){
                    TextView name = view.findViewById(R.id.textView4);
                    ImageView imageView = view.findViewById(R.id.imageView2);
                    name.setText(servicelist.getName());
                    imageView.setImageResource(servicelist.getImage());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try{
            if (view==null){

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(resourceId,parent,false);

                ServicesList servicelist = servicesLists.get(position);
                if (servicelist!=null){
                    TextView name = view.findViewById(R.id.textView4);
                    ImageView imageView = view.findViewById(R.id.imageView2);
                    name.setText(servicelist.getName());
                    imageView.setImageResource(servicelist.getImage());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return view;    }
}
