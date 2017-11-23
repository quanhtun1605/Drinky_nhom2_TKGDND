package com.example.minhd.drinky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by minhd on 17/11/23.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.CommonHolder> {

    private ICom mInter;

    public ProfileAdapter(ICom iCom) {
        mInter = iCom;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_profile, parent, false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonHolder commonHolder, int i) {
        ItemProfile item = mInter.getItem(i) ;
        commonHolder.img.setImageResource(item.getImg());
        commonHolder.tvReview.setText(item.getReview());
    }


    @Override
    public int getItemCount() {
        return mInter.getCount();
    }

    static class CommonHolder extends RecyclerView.ViewHolder {

        private TextView tvReview;
        private ImageView img;

        public CommonHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_tra) ;
            tvReview = itemView.findViewById(R.id.rv);
        }
    }

    public interface ICom {
        int getCount();

        ItemProfile getItem(int position);
    }

}

