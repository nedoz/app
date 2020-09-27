package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnDragListener

{
    private ListView mDrawerList ;
    RecyclerView items;
    ImageView deleteImage;
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayList dataList = new ArrayList();
    String selectedItem;
    ArrayList arrayList = new ArrayList();
   ItemModel item;
    int itemId ;
    CoordinatorLayout coordinatorLayout;
    String gridItem ;
    TextView tvSaveChanges;
    Toolbar toolbar;
    File gridFile,listFile;
    Adapter adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        mDrawerList = findViewById(R.id.lv_items);
        deleteImage = findViewById(R.id.delete_icon);
        adapter = new Adapter(arrayList);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        coordinatorLayout = findViewById(R.id.coordinator);
        tvSaveChanges = findViewById(R.id.tv_save_changes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setHomeButtonEnabled(true);

          mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,
        R.string.drawer_close);


         // coordinatorLayout.setOnDragListener(this);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        items = findViewById(R.id.ContentGridView);

         dataList.add("4");
         dataList.add("5");
         dataList.add("6");

tvSaveChanges.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
final NavigationAdapter adapter1 = new NavigationAdapter(this,0,dataList);
     // mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_item,dataList));
        mDrawerList.setAdapter(adapter1);
mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int img = 0;
        selectedItem = (String) dataList.get(position);

      if (selectedItem != null)
      { switch (selectedItem) {
                case "1":
                    img = R.drawable.ic_baseline_looks_1;
                    break;
                case "2":
                    img = R.drawable.ic_baseline_looks_2;
                    break;
                case "3":
                    img = R.drawable.ic_baseline_looks_3;
                    break;
                case "4":
                    img = R.drawable.ic_baseline_looks_4;
                    break;
                case "5":
                    img = R.drawable.ic_baseline_looks_5;
                    break;
                case "6":
                    img = R.drawable.ic_baseline_looks_6;
                    break;
            }
            item = new ItemModel(selectedItem, img);

            arrayList.set(Adapter.id, item);
            adapter.notifyDataSetChanged();
          if(Adapter.name == "") {
              dataList.remove(position);
          }
          else
          {
          dataList.set(position,Adapter.name);

          }
            adapter1.notifyDataSetChanged();
            adapter1.notifyDataSetChanged();


      }
    }
});



        arrayList.add(new ItemModel("1",R.drawable.ic_baseline_looks_1));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel("2",R.drawable.ic_baseline_looks_2));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel("3",R.drawable.ic_baseline_looks_3));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel(""));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        ItemTouchHelper.Callback callback = new TouchHelper(adapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(items);
        items.setLayoutManager(layoutManager);
        items.setAdapter(adapter);
    }




    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onDrag(View v, DragEvent event) {
        Rect draggedImageRect = new Rect();
        Rect trashRect = new Rect();

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
            case DragEvent.ACTION_DRAG_EXITED:
            case DragEvent.ACTION_DRAG_LOCATION:
            case DragEvent.ACTION_DRAG_ENDED:
            case DragEvent.ACTION_DRAG_STARTED:
                return true;
            case DragEvent.ACTION_DROP:
                float oldX = v.getX();
                float oldY = v.getY();
                v.setX(event.getX());
                v.setY(event.getY());
                v.getHitRect(draggedImageRect);
                deleteImage.getHitRect(trashRect); //change trashImage with your variable

                /*
                 * More magic happens here. We need to make sure, that only the drop to the trash will delete the dragged item.
                 */
                if (Rect.intersects(draggedImageRect, trashRect)) {
                    try {
                        int pos = Integer.parseInt(event.getClipData().getItemAt(0).getText().toString());

                        arrayList.remove(pos);
                        adapter.notifyItemRemoved(pos);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    v.setX(oldX);
                    v.setY(oldY);
                    return true;
                }
                    default:
                        return false;
                }
        }

    }


