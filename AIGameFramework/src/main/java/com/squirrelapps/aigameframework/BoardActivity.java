package com.squirrelapps.aigameframework;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoardActivity extends Activity  implements Button.OnClickListener
{
    private static final String TAG = BoardActivity.class.getSimpleName();

    final int xDim = 8, yDim = 8;

    Board board = new SquareBoard(xDim, yDim);
    float screen_w;
    float screen_h;
    int cell_size;

    Button[][] boardButtons = new Button[xDim][yDim];

    Set<Button> animatedButtons = new HashSet<Button>();
    int lastDistance = 1;
    Button lastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_board);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screen_w = dm.widthPixels; //(int) (dps * scale + 0.5f)
        screen_h = dm.heightPixels;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            screen_h -= getResources().getDimensionPixelSize(resId);
        }
        //if(getActionBar().isShowing()) API >= 11
        TypedValue tv = new TypedValue();
        if(getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            screen_h -= getResources().getDimensionPixelSize(tv.resourceId);
        }

        cell_size = (int)(Math.min(screen_w/xDim, screen_h/yDim));

        Log.d(TAG, "screen_w: " + screen_w + ", screen_h: " + screen_h + ", statusbar_h: " + getResources().getDimensionPixelSize(resId) + ", actionbar_h: " + getResources().getDimensionPixelSize(tv.resourceId) + " > cell_size: " + cell_size);

//        GridView gridview = (GridView) findViewById(R.id.gridView);
////        gridview.setMinimumWidth(cell_size * xDim);
////        gridview.setMinimumHeight(cell_size * yDim);
//        gridview.setAdapter(new ImageAdapter(this));
//        gridview.setNumColumns(xDim);
//        gridview.setColumnWidth(cell_size);
//
//        gridview.setOnItemClickListener(new GridView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Toast.makeText(MainActivity.this, "" + /*position*/ (Cell)v.getTag(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //TABLE
        TableLayout table = (TableLayout)findViewById(R.id.tableLayout);

        FrameLayout.LayoutParams pTable = new FrameLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        table.setBackgroundColor(Color.RED); //TODO rimuovere a fine debug
        table.setLayoutParams(pTable);
        table.setPadding(0, 0, 0, 0); //TODO se non lo si mette a zero va sottratto l'insets a screen_w e screen_h

//        //ROW TOP
//        TableRow rowTop = new TableRow(this);
//
//        TableLayout.LayoutParams pRowTop = new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT);
//        pRowTop.weight = 1;
//
//        rowTop.setBackgroundColor(Color.BLUE);
//
//        TextView txt = new TextView(this);
//        txt.setText("Top Content");
//
//        rowTop.addView(txt, new TableRow.LayoutParams(
//                TableRow.LayoutParams.MATCH_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//
//        table.addView(rowTop, pRowTop);

        //CELL ROWs
        TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
//                cell_size*xDim,
//                cell_size,
                1.0f);
        TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,
                cell_size,
                cell_size,
                1.0f);
        for (int y = 0; y < yDim; ++y){
            TableRow row = new TableRow(this);
            //row.setBackgroundColor(Color.YELLOW); //TODO rimuovere a fine debug
            for (int x = 0; x < xDim; ++x){
                Button btn = new Button(this);
                btn.setTag(board.cells[x][y]);
                btn.setText(/*board.cells[x][y].toString()*/"" + (y * yDim + x));
                btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
                btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
                btn.setOnClickListener(this);
                row.addView(btn, cellLp);

                boardButtons[x][y] = btn;
            }
            table.addView(row, rowLp);
        }

//        TableRow rowBottom = new TableRow(this);
//        rowBottom.setBackgroundColor(Color.GREEN);
//
//        TextView txtBottom = new TextView(this);
//        txtBottom.setText("Bottom Content");
//
//        TableLayout.LayoutParams pRowBottom = new
//                TableLayout.LayoutParams(
//                TableLayout.LayoutParams.WRAP_CONTENT,
//                TableLayout.LayoutParams.WRAP_CONTENT);
//
//        rowBottom.addView(txtBottom, new TableRow.LayoutParams(
//                TableRow.LayoutParams.MATCH_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//
//        table.addView(rowBottom, pRowBottom);

        //TODO in alternativa a quanto segue, ci si potrebbe registrare come GlobalLayoutListener e leggere la dim del layout o della table stessa!
//        table.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
//        {
//            @Override
//            public void onGlobalLayout()
//            {
//
//            }
//        });
//        //oppure
//        table.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
//        {
//            @Override
//            public boolean onPreDraw()
//            {
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.board, menu);
        return true;
    }

    @Override
    public void onClick(View view)
    {
        if(view instanceof Button){
            Cell cell = (Cell)view.getTag();

            synchronized(BoardActivity.this){
                Button button = (Button)view;
                if(button == lastButton){
                    lastDistance = (lastDistance+1) % Math.max(Math.max(cell.x + 1, xDim - (cell.x /*+ 1*/)), Math.max(cell.y + 1, yDim - (cell.y /*+ 1*/)));
                }else{
                    lastButton = button;
                    lastDistance = 1;
                }

                Toast.makeText(BoardActivity.this, "" + cell + " (distance " + lastDistance + ")", Toast.LENGTH_SHORT).show();

                stopAnimation();

                Set<Cell> neighbors = board.borderNeighbors(cell, lastDistance);
                Log.v(TAG, neighbors.size() + " neighbors of " + cell + " at distance "+lastDistance+" found: "+ Arrays.toString(neighbors.toArray()));
                for(Cell c : neighbors){
                    Button btn = boardButtons[c.x][c.y];

                    btn.setBackgroundResource(R.drawable.cell_neighbors_anim);
                    // Get the background, which has been compiled to an AnimationDrawable object.
                    AnimationDrawable frameAnimation = (AnimationDrawable)btn.getBackground();
                    // Start the animation (looped playback by default).
                    frameAnimation.start();

                    animatedButtons.add(btn);
                }
            }
        }
    }

    private synchronized void stopAnimation()
    {
        for(Button btn : animatedButtons){
            AnimationDrawable frameAnimation = (AnimationDrawable)btn.getBackground();
            // Stop the animation (looped playback by default).
            frameAnimation.stop();
            Cell cell = (Cell)btn.getTag();
            int x = cell.x;
            int y = cell.y;

            btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
            btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
        }
        animatedButtons.clear();
    }

    class ImageAdapter extends BaseAdapter
    {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return xDim*yDim;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(cell_size, cell_size));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            int x = position%xDim;
            int y = position/xDim;

            imageView.setTag(board.cells[x][y]);
            //imageView.setImageResource((x%2 == 0 && y%2 != 0)||(x%2 != 0 && y%2 == 0)?R.drawable.squareboard_even :R.drawable.squareboard_odd);
            imageView.setImageResource((x+y)%2 == 0?R.drawable.squareboard_even :R.drawable.squareboard_odd);
            return imageView;
        }
    }
}
