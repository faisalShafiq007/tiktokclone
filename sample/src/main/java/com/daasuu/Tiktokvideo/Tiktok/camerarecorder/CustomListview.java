package com.daasuu.Tiktokvideo.Tiktok.camerarecorder;

import PackageTiktok.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Custom class generated for viewholder and all that crap
public class CustomListview extends ArrayAdapter<String> {
    private String[] places;
    private Integer[] imageid;
    private Activity context;
    public CustomListview(Activity context,Integer[] imageid,String[] places ) {
        super(context, R.layout.main_recycle,places);
        this.context=context;
        this.imageid=imageid;
        this.places=places;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.main_recycle,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{

            viewHolder= (ViewHolder) r.getTag();
        }
        viewHolder.imgw.setImageResource(imageid[position]);
        viewHolder.tvtw1.setText(places[position]);
        return r;

    }
    class ViewHolder{

        TextView tvtw1;
        ImageView imgw;
        ViewHolder(View v){
            tvtw1=(TextView) v.findViewById(R.id.tvtextview);
            imgw=(ImageView) v.findViewById(R.id.tvimageview);
        }
    }
}