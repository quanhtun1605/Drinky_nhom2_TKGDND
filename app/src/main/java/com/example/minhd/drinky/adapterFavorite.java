package com.example.minhd.drinky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by minhd on 17/11/21.
 */

public class adapterFavorite  extends RecyclerView.Adapter<adapterFavorite.CommonHolder>{

    private ICom mInter;

    public adapterFavorite(ICom iCom){
        mInter = iCom ;
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item, parent, false);
        return new CommonHolder(view) ;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        Item item = mInter.getItem(position) ;
        holder.tvName.setText(item.getName());
        holder.tvLoc.setText(item.getLoc());
        holder.img.setImageResource(item.getImg());
    }

    @Override
    public int getItemCount() {
        return mInter.getCount();
    }

    static class CommonHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvLoc ;
        private ImageView img;

        public CommonHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name) ;
            tvLoc = itemView.findViewById(R.id.loc) ;
            img = itemView.findViewById(R.id.img);

        }
    }

    public interface ICom {
        int getCount();
        Item getItem(int position) ;
    }

}
