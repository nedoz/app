package com.example.project;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnDragListener {
    RecyclerView items;
    ImageView deleteImage;
    NavigationView navigationView;
    ArrayList dataList = new ArrayList();
    String selectedItem;
    ArrayList arrayList = new ArrayList();
    ItemModel item;
    int itemId;
    CoordinatorLayout coordinatorLayout;
    String gridItem;
    TextView tvSaveChanges;
    Toolbar toolbar;
    File gridFile, listFile;
    static Adapter adapter;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private MyDragShadowBuilder.MyDragEventListener mDragListener;
    private static final String TAG = "MainActivity";

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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);


        mDragListener = new MyDragShadowBuilder.MyDragEventListener();


        deleteImage.setOnDragListener(mDragListener);

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
        final NavigationAdapter adapter1 = new NavigationAdapter(this, 0, dataList);
        // mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_item,dataList));
        mDrawerList.setAdapter(adapter1);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int img = 0;
                selectedItem = (String) dataList.get(position);

                if (selectedItem != null) {
                    switch (selectedItem) {
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
                    if (Adapter.name == "") {
                        dataList.remove(position);
                    } else {
                        dataList.set(position, Adapter.name);

                    }
                    adapter1.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();


                }
            }
        });


        arrayList.add(new ItemModel("1", R.drawable.ic_baseline_looks_1));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel("2", R.drawable.ic_baseline_looks_2));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel("3", R.drawable.ic_baseline_looks_3));
        arrayList.add(new ItemModel(""));
        arrayList.add(new ItemModel(""));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
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

    static class MyDragShadowBuilder extends View.DragShadowBuilder {
        // The drag shadow image, defined as a drawable thing
        private Drawable shadow;

        public MyDragShadowBuilder(View v) {
            super(v);

            int color = ((ColorDrawable) v.getBackground()).getColor();

            shadow = new ColorDrawable(getDarkerColor(color));
        }

        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            int width, height;

            width = getView().getWidth() / 2;

            height = getView().getHeight() / 2;

            shadow.setBounds(0, 0, width, height);

            size.set(width, height);

            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            // Draws the ColorDrawable in the Canvas passed in from the system
            shadow.draw(canvas);
        }

        static class MyDragEventListener implements View.OnDragListener {

            public boolean onDrag(View view, DragEvent event) {
                final int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            view.setBackgroundColor(Color.parseColor("#FFC4E4FF"));
                            return true;
                        }
                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        // When dragged item entered the receiver view area
                        view.setBackgroundColor(Color.parseColor("#FFB7FFD6"));
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        // Ignore the event
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        // When dragged object exit the receiver object
                        view.setBackgroundColor(Color.parseColor("#FFFFBCBC"));
                        // Return true to indicate the dragged object exited the receiver view
                        return true;
                    case DragEvent.ACTION_DROP:
                        // Get the dragged data
                        ClipData.Item item = event.getClipData().getItemAt(0);
                        String dragData = (String) item.getText();
                        Log.d(TAG, "onDrag: " + dragData);
                        try {
                            adapter.emptyText(Integer.parseInt(dragData));
                        } catch (Exception e) {
                            Log.e(TAG, "onDrag: " + e.getMessage());
                        }

                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        // Remove the background color from view
                        view.setBackgroundColor(Color.TRANSPARENT);
                        if (event.getResult()) {
                            Log.d(TAG, "onDrag: The drop was handled.");
                        } else {
                            Log.d(TAG, "onDrag: The drop did not work.");
                        }

                        // Return true to indicate the drag ended
                        return true;
                    default:
                        Log.e("Drag and Drop example", "Unknown action type received.");
                        break;
                }

                return false;
            }


        }

        public int getDarkerColor(int color) {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            //hsv[2] = 0.8f *hsv[2];
            hsv[2] = 0.7f * hsv[2]; // more darker
            return Color.HSVToColor(hsv);
        }

        static class MyLongClickListener implements View.OnLongClickListener {
            @Override
            public boolean onLongClick(View view) {
                // Get the background color of view
                int color = ((ColorDrawable) view.getBackground()).getColor();
                String colorString = String.valueOf(color);

            /*
                ClipData.Item
                    Description of a single item in a ClippedData.
                    The types than an individual item can currently contain are: Text, Intent and Uri.
            */
                // Create a new ClipData
                // Create a ne ClipData.Item from the TextView objects tag
                ClipData.Item item = new ClipData.Item(colorString);

            /*
                ClipData
                    Representation of a clipped data on the clipboard.

                    ClippedData is a complex type containing one or Item instances, each of which
                    can hold one or more representations of an item of data. For display to the
                    user, it also has a label and iconic representation.

                    A ClipData contains a ClipDescription, which describes important meta-data
                    about the clip. In particular, its getDescription().getMimeType(int) must
                    return correct MIME type(s) describing the data in the clip. For help in
                    correctly constructing a clip with the correct MIME type, use
                    newPlainText(CharSequence, CharSequence), newUri(ContentResolver,
                    CharSequence, Uri), and newIntent(CharSequence, Intent).

                    Each Item instance can be one of three main classes of data: a simple
                    CharSequence of text, a single Intent object, or a Uri.
            */
                // Create a new ClipData using the tag as a label, the plain text MIME type,
                // and the already created item.
                ClipData dragData = new ClipData(
                        colorString,
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item
                );

                // Instantiates the drag shadow builder
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(view);

                // Starts the drag
                view.startDrag(
                        dragData, //  the data to be drag
                        myShadow, // the drag shadow builder
                        null, // no need to use local data
                        0 // flags
                );
                return false;
            }

        }


    }
}


