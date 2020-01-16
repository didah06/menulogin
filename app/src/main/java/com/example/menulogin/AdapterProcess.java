package com.example.menulogin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class AdapterProcess extends RecyclerView.Adapter<AdapterProcess.ViewProcessHolder> {
    Context context;
    private ArrayList<ModelData> item;



    public AdapterProcess(daftarkelas daftarkelas, List<Data> item) {
        this.context = daftarkelas;
    }
    @NonNull
    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(ViewProcessHolder holder, final int position) {
        final ModelData data = item.get(position);
        holder.id_plp.setText(data.getId_plp());
        holder.id_plp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.get(position).getId_plp();

                Intent in = new Intent(context, InputAbsensi.class);
                in.putExtra(item.get(position).getId_plp(), id);
                context.startActivity(in);
                Toast.makeText(context, "clicked" +id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {
        TextView   id_plp;


        public ViewProcessHolder(final View itemView) {

            super(itemView);
            id_plp = (TextView) itemView.findViewById(R.id.id_plp);

        }
    }
}
