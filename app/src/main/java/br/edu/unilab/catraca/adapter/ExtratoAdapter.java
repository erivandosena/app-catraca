package br.edu.unilab.catraca.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.unilab.catraca.R;
import br.edu.unilab.catraca.resource.Extrato;


/**
 * Created by erivando on 27/12/2016.
 */

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.MyViewHolder> {

    private List<Extrato> extratoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView descricao, data, local, valor;

        public MyViewHolder(View view) {
            super(view);
            descricao=(TextView) view.findViewById(R.id.descricao);
            data=(TextView) view.findViewById(R.id.data);
            local=(TextView) view.findViewById(R.id.local);
            valor=(TextView) view.findViewById(R.id.valor);
        }
    }


    public ExtratoAdapter(List<Extrato> extratoList) {
        this.extratoList=extratoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_extrato_linha, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Extrato extrato=extratoList.get(position);
        holder.descricao.setText(extrato.getDescricao());
        holder.data.setText(extrato.getData());
        holder.local.setText(extrato.getLocal());
        holder.valor.setText(extrato.getValor());
    }

    @Override
    public int getItemCount() {
        return extratoList.size();
    }
}