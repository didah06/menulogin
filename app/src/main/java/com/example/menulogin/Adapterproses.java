package com.example.menulogin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class Adapterproses extends BaseAdapter {
    private daftarkelas daftarkelas;
    private List<Data> listPlp;
    private LayoutInflater inflater;

    public Adapterproses(daftarkelas daftarkelas, List<Data> listPlp) {
        this.daftarkelas = daftarkelas;
        this.listPlp = listPlp;
    }


    @Override
    public int getCount() {
        return listPlp.size();
    }

    @Override
    public Object getItem(int position) {
        return listPlp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) daftarkelas.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        TextView id_plp = (TextView) convertView.findViewById(R.id.id_plp);

        Data data;
        data = listPlp.get(position);

        id_plp.setText(data.getId_plp());


        return convertView;
    }

}
