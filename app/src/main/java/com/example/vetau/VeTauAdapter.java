package com.example.vetau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VeTauAdapter extends ArrayAdapter<Vetau> {
    private Context context;
    private List<Vetau> arrVeTau;
    public VeTauAdapter(@NonNull Context context, int resource, @NonNull List<Vetau> objects) {
        super(context, resource, objects);
        this.context=context;
        this.arrVeTau=objects;
    }

    @Nullable
    @Override
    public Vetau getItem(int position) {
        return arrVeTau.get(position);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            // convertView = LayoutInflater.from(context).inflate(R.layout.collab, parent, false);
            convertView = LayoutInflater.from(context).inflate(R.layout.vetau, parent, false);
            viewHolder.tv_ga_den = convertView.findViewById(R.id.tv_ga_den);
            viewHolder.tv_ga_di = convertView.findViewById(R.id.tv_ga_di);
            viewHolder.tv_gia_ve = convertView.findViewById(R.id.tv_gia_ve);
            viewHolder.tv_loai_ve = convertView.findViewById(R.id.tv_loai_ve);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Vetau vetau = arrVeTau.get(position);

        viewHolder.tv_ga_den.setText(vetau.getGaDen());
        viewHolder.tv_ga_di.setText(vetau.getGaDi());

        if (vetau.getLoaiVe()==1){
            viewHolder.tv_loai_ve.setText("Khu hoi");
            viewHolder.tv_gia_ve.setText(""+(vetau.getDonGia()*2*0.95));
        }
        else {
            viewHolder.tv_loai_ve.setText("mot chieu");
            viewHolder.tv_gia_ve.setText( ""+vetau.getDonGia());
        }


        return convertView;
    }

    class ViewHolder {
        TextView tv_ga_den;
        TextView tv_ga_di;
        TextView tv_gia_ve;
        TextView tv_loai_ve;
    }


}
