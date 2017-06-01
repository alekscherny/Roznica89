package ru.a89uchet.roznica89.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ua.cherny.kassir.R;
import ua.cherny.kassir.db.MetaDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 15.12.12
 * Time: 8:21
 * To change this template use File | Settings | File Templates.
 */
public class TaskListSimpleAdapter extends BaseAdapter {
    private ArrayList<HashMap<String,String>> dataList;
    Context mContext;
    private LayoutInflater mInflater;

    public TaskListSimpleAdapter(Context cont, ArrayList<HashMap<String,String>> mData){
        mContext=cont;
        dataList=mData;
        mInflater = LayoutInflater.from(cont);
    }

    @Override
    public int getCount() {
       return dataList.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int i) {
        return i;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.simpleitemtask, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.simpletaskname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(Integer.valueOf(dataList.get(i).get(MetaDatabase.Tasks.ISACTIVE))>0){
            convertView.setBackgroundColor(0xD000FF00);
        }
        holder.txtName.setText(dataList.get(i).get(MetaDatabase.Tasks.NAME));
        return convertView;  //To change body of implemented methods use File | Settings | File Templates.
    }

    static class ViewHolder {
        TextView txtName;
    }
}
