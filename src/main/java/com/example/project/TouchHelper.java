package com.example.project;

import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TouchHelper extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public TouchHelper(ItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG)
        {
            viewHolder.itemView.setBackground(ContextCompat.getDrawable(viewHolder.itemView.getContext(),
                    R.drawable.bg_selected_item));
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
    final int dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
    final int swipeFlag = ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
      mAdapter.onItemSwiped(viewHolder.getAdapterPosition());
     if(direction == ItemTouchHelper.DOWN)
     {
         mAdapter.onItemSwiped(viewHolder.getAdapterPosition());
     }



    }
}
