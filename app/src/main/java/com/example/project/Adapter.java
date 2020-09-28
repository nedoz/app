package com.example.project;

import android.content.ClipData;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class Adapter extends RecyclerView.Adapter<Adapter.holder> implements ItemTouchHelperAdapter, View.OnLongClickListener {
    public Adapter(ArrayList<ItemModel> items) {
        this.items = items;
        // this.onProductClickListener = onProductClickListener;
    }

    ItemTouchHelper mTouchHelper;
    //  OnProductClickListener onProductClickListener;
    static int id = 0;
    static String name = null;
    ArrayList<ItemModel> items = new ArrayList();

    // private onClickItemListener itemListener;
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder, final int position) {
        holder.itemText.setText(items.get(position).getItemName());
        holder.itemImage.setImageResource(items.get(position).getItemImage());
        holder.itemView.setTag(position);
        holder.itemView.setOnLongClickListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        Collections.swap(items, fromPosition, toPosition);
        notifyDataSetChanged();
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.mTouchHelper = touchHelper;
    }

    @Override
    public void onItemSwiped(int position) {

        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onLongClick(View v) {
        v.startDrag(ClipData.newPlainText("label", v.getTag().toString()), new View.DragShadowBuilder(v), null, 0);
        return true;
    }

    public void emptyText(int position) {
        items.set(position, new ItemModel(""));
        notifyItemChanged(position);
    }


    public class holder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
        ImageView itemImage;
        ImageView bg;
        TextView itemText;
        GestureDetector gestureDetector;

        public holder(@NonNull View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.content);
            itemImage = itemView.findViewById(R.id.item_image);
            itemText = itemView.findViewById(R.id.tv_content);
//            gestureDetector = new GestureDetector(itemView.getContext(), this);
//            itemView.setOnTouchListener(this);
            itemView.setOnLongClickListener(new MainActivity.MyDragShadowBuilder.MyLongClickListener());
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            id = getAdapterPosition();
            name = items.get(getAdapterPosition()).getItemName();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            id = getAdapterPosition();
            name = items.get(id).getItemName();
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mTouchHelper.startDrag(this);
            //  v.startDrag(ClipData.newPlainText("label", v.getTag().toString()), new View.DragShadowBuilder(v), null, 0);

        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }


    }

}