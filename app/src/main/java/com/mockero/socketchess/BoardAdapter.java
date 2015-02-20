package com.mockero.socketchess;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ddwiyaksha on 2/16/15.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{

    private Map<String, Integer> chessDrawable;
    private Map<String, Integer> chessPosition;


    public BoardAdapter() {
        initialize();
    }

    public void setPiece(String pieces) {

        String[] chessPieces = pieces.split(" ");
        for(String piece:chessPieces) {
            int position = calculatePosition(piece.charAt(1), piece.charAt(2));
            Log.e("Board", piece + " : " + position);
            chessPosition.put("" + piece.charAt(0), position);
        }
        notifyDataSetChanged();
    }

    public void initialize() {
        chessDrawable = new HashMap<>();
        chessDrawable.put("K", R.drawable.white_king);
        chessDrawable.put("Q", R.drawable.white_queen);
        chessDrawable.put("B", R.drawable.white_bishop);
        chessDrawable.put("N", R.drawable.white_knight);
        chessDrawable.put("R", R.drawable.white_rook);
        chessDrawable.put("k", R.drawable.black_king);
        chessDrawable.put("q", R.drawable.black_queen);
        chessDrawable.put("b", R.drawable.black_bishop);
        chessDrawable.put("n", R.drawable.black_knight);
        chessDrawable.put("r", R.drawable.black_rook);
        chessPosition = new HashMap<>();
    }

    public int calculatePosition(char xchar, char ychar) {
        int position = 0;
        int x = xchar - 97; // a ascii value 97
        int y = Integer.parseInt("" + ychar);
        position = x + ((8 - y) * 8);
        return position;
    }

    public String getKeyThatContainValue(int value) {
        for(Map.Entry<String, Integer> entry:chessPosition.entrySet()) {
            if(entry.getValue().intValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.item_cell, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int color = 0;
        int white = Color.parseColor("#FFCEA1");
        int black = Color.parseColor("#D58B4C");
        if(i % 2 == 0) {
            color = (i / 8 % 2 == 0) ? white : black;
        } else {
            color = (i / 8 % 2 == 0) ? black : white;
        }

        try {
            if (chessPosition.containsValue(i)) {
                String key = getKeyThatContainValue(i);
                int drawableRes = chessDrawable.get(key).intValue();
                viewHolder.cell.setImageResource(drawableRes);
            } else {
                viewHolder.cell.setImageBitmap(null);
            }
        } catch(Exception e) {
            Log.e("Adapter", "Something wrong with data : " + e.getMessage());
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
