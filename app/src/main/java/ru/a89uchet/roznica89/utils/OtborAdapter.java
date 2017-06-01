package ru.a89uchet.roznica89.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ru.a89uchet.roznica89.R;
import ru.a89uchet.roznica89.db.MetaDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 04.12.12
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class OtborAdapter extends BaseAdapter {
    public static final byte DATE_BEGIN = 1;
    public static final byte DATE_END = 2;
    public static final byte GOODS = 3;
    public static final byte NOMGRPUPS = 4;

   private ArrayList<OtborFields> otborList;
   Context mContext;
    private LayoutInflater mInflater;

    public OtborAdapter(Context cont, ArrayList<OtborFields> res){
        otborList=res;
        mContext=cont;
        mInflater = LayoutInflater.from(cont);
        initFields();
    }
    private void initFields(){
        OtborFields d1=new OtborFields();
        d1.setName("dateBegin");
        d1.setRus_name("Дата с:");
        d1.setData_type("DATE");

        OtborFields d2=new OtborFields();
        d2.setName("dateEnd");
        d2.setRus_name("Дата по:");
        d2.setData_type("DATE");

        OtborFields tovar=new OtborFields();
        tovar.setName("GOODS");
        tovar.setRus_name("Товар");
        tovar.setData_type(MetaDatabase.Goods.CODE);

        OtborFields grp=new OtborFields();
        grp.setName("NOMGRP");
        grp.setRus_name("Группа товара");
        grp.setData_type(MetaDatabase.Goods.NOMGRP);

        otborList.add(d1);
        otborList.add(d2);
        otborList.add(tovar);
        otborList.add(grp);
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
                String curName=otborList.get(position).getName();
                if( curName.equals("GOODS")){
                    ActivityHelper.startActivity(GOODS,"");
                } else if(curName.equals("NOMGRP")){
                    ActivityHelper.startActivity(NOMGRPUPS,"");
                } else if(curName.equals("dateBegin")){
                    ActivityHelper.startActivity(DATE_BEGIN,holder.txtValue.getText().toString());
                }else if(curName.equals("dateEnd")){
                    ActivityHelper.startActivity(DATE_END,holder.txtValue.getText().toString());
                }

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
