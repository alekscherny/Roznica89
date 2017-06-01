package ru.a89uchet.roznica89.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ua.cherny.kassir.R;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 13.12.12
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class OtborUniAdapter extends BaseAdapter {

    private ArrayList<OtborFields> otborList;
    Context mContext;
    private LayoutInflater mInflater;

    public OtborUniAdapter(Context cont, ArrayList<OtborFields> res){
        otborList=res;
        mContext=cont;
        mInflater = LayoutInflater.from(cont);
    }

    @Override
    public int getCount() {
        return otborList.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int i) {
        return otborList.get(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int i) {
        return i;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.simpleitemhoriz, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.field1);
            holder.txtValue = (TextView) convertView.findViewById(R.id.val1);
            holder.btnClear =(Button)convertView.findViewById(R.id.clearbtn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otborList.get(position).setValue_code("");
                otborList.get(position).setValue_str("");
                ActivityHelper.getAdapter().notifyDataSetChanged();
            }
        });

        holder.txtValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1=otborList.get(position).getValue_code();
                String data2=holder.txtValue.getText().toString();

                byte curType=otborList.get(position).getType_byte();
                ActivityHelper.startActivityUni(curType,data1,data2);
//                if( curName.equals("GOODS")){
//                    ActivityHelper.startActivity(GOODS,"");
//                } else if(curName.equals("NOMGRP")){
//                    ActivityHelper.startActivity(NOMGRPUPS,"");
//                } else if(curName.equals("dateBegin")){
//                    ActivityHelper.startActivity(DATE_BEGIN,holder.txtValue.getText().toString());
//                }else if(curName.equals("dateEnd")){
//                    ActivityHelper.startActivity(DATE_END,holder.txtValue.getText().toString());
//                }

            }
        });

        holder.txtName.setText(otborList.get(position).getRus_name());
        holder.txtValue.setText(otborList.get(position).getValue_str());

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtValue;
        Button btnClear;
    }
}
