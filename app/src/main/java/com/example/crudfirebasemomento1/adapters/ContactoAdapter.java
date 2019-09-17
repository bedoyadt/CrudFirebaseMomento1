package com.example.crudfirebasemomento1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.crudfirebasemomento1.R;
import com.example.crudfirebasemomento1.models.ClienteModel;

import java.util.ArrayList;

public class ContactoAdapter extends BaseAdapter {

    private final Context context;
    private ClienteModel model;
    private ArrayList<ClienteModel> list;

    public ContactoAdapter(Context context, ArrayList<ClienteModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_contacto, viewGroup, false);
        }
        TextView tv_iten_contacto = itemView.findViewById(R.id.tv_iten_contacto);
        model = list.get(i);
        tv_iten_contacto.setText(model.get_nombre());

        return itemView;
    }
}
