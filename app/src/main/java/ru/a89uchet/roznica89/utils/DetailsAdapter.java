package ru.a89uchet.roznica89.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import ru.a89uchet.roznica89.R;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 25.11.12
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class DetailsAdapter extends BaseExpandableListAdapter {
    private int[] colors = new int[] { 0xD0FF837C,0xD099FF87 };
    private ArrayList<Map<String,String>> mGroups;
    private ArrayList<ArrayList<Map<String,String>>> mChilds;
    private Context mContext;
    public static final byte DOCS = 11;

    public DetailsAdapter(Context context, ArrayList<Map<String,String>> groups, ArrayList<ArrayList<Map<String,String>>> childs) {
        mContext = context;
        mGroups = groups;
        mChilds = childs;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChilds.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.details_group, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText(mGroups.get(groupPosition).get("TOVAR"));

        TextView textGroupPlus = (TextView) convertView.findViewById(R.id.textGroupPlus);
        textGroupPlus.setText("+ "+mGroups.get(groupPosition).get("PRIHODQTY")+" ("+mGroups.get(groupPosition).get("PRIHODSUM")+")");

        TextView textGroupMinus = (TextView) convertView.findViewById(R.id.textGroupMinus);
        textGroupMinus.setText("- "+mGroups.get(groupPosition).get("RASHODQTY")+" ("+mGroups.get(groupPosition).get("RASHODSUM")+")");
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.details_child, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        textChild.setText(mChilds.get(groupPosition).get(childPosition).get("DATEDOC"));
        TextView textChildComment = (TextView) convertView.findViewById(R.id.textChildDoc);
        textChildComment.setText(mChilds.get(groupPosition).get(childPosition).get("COMMENT"));
        TextView textChildClient = (TextView) convertView.findViewById(R.id.textChildKlient);
        textChildClient.setText(mChilds.get(groupPosition).get(childPosition).get("KLIENT"));
        TextView textChildSclad = (TextView) convertView.findViewById(R.id.textChildSklad);
        textChildSclad.setText(mChilds.get(groupPosition).get(childPosition).get("SKLAD"));
        TextView textChildQty = (TextView) convertView.findViewById(R.id.textChildQty);
        textChildQty.setText(mChilds.get(groupPosition).get(childPosition).get("QTY"));
        TextView textChildPrice = (TextView) convertView.findViewById(R.id.textChildPrice);
        textChildPrice.setText(mChilds.get(groupPosition).get(childPosition).get("PRICE"));
        TextView textChildSum = (TextView) convertView.findViewById(R.id.textChildSum);
        textChildSum.setText(mChilds.get(groupPosition).get(childPosition).get("SUM"));

//        Button button = (Button)convertView.findViewById(R.id.buttonChild);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  Toast.makeText(mContext,"button is pressed",5000).show();
//            }
//        });
         if(mChilds.get(groupPosition).get(childPosition).get("PRIHRASH").equals("1")){
             convertView.setBackgroundColor(colors[1]);
         } else{
             convertView.setBackgroundColor(colors[0]);
         }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String doctype= mChilds.get(groupPosition).get(childPosition).get("DOCTYPE");
               String docid= mChilds.get(groupPosition).get(childPosition).get("DOCID");
                ActivityHelper.startActivity(DOCS,doctype+";"+docid);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
