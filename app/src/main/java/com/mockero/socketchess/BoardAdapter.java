package com.mockero.socketchess;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by ddwiyaksha on 2/16/15.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.item_cell, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int color = Color.LTGRAY;
        if(i % 2 == 0) {
            color = (i / 8 % 2 == 0) ? Color.WHITE : Color.DKGRAY;
        } else {
            color = (i / 8 % 2 == 0) ? Color.DKGRAY : Color.WHITE;
        }

        viewHolder.cell.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return 64;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton cell;

        public ViewHolder(View itemView) {
            super(itemView);
            cell = (ImageButton) itemView.findViewById(R.id.cell);
        }
    }
}
