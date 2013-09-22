package com.squirrelapps.aigameframework;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Francesco Vadicamo on 9/21/13.
 */
public class BoardFragment extends Fragment implements Button.OnClickListener
{
    private static final String TAG = BoardFragment.class.getSimpleName();

    final int xDim = 8, yDim = 8;

    Board board = new SquareBoard(xDim, yDim);
//    float screen_w;
//    float screen_h;
//    int cell_size;

    Button[][] boardButtons = new Button[xDim][yDim];

    Set<Button> animatedButtons = new HashSet<Button>();
    int lastDistance = 1;
    Button lastButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(false);

        //MainActivity mainActivity = (MainActivity)getActivity();
        //this.mainActivityWeakRef = new WeakReference<MainActivity>(mainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView()");
        final View rootView = inflater.inflate(R.layout.f_board, container, false);

        Activity activity = getActivity();

//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        screen_w = dm.widthPixels; //(int) (dps * scale + 0.5f)
//        screen_h = dm.heightPixels;
//        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resId > 0) {
//            screen_h -= getResources().getDimensionPixelSize(resId);
//        }
//        //if(getActionBar().isShowing()) API >= 11
//        TypedValue tv = new TypedValue();
//        if(activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
//            screen_h -= getResources().getDimensionPixelSize(tv.resourceId);
//        }
//
//        cell_size = (int)(Math.min(screen_w/xDim, screen_h/yDim));
//
//        Log.d(TAG, "screen_w: " + screen_w + ", screen_h: " + screen_h + ", statusbar_h: " + getResources().getDimensionPixelSize(resId) + ", actionbar_h: " + getResources().getDimensionPixelSize(tv.resourceId) + " > cell_size: " + cell_size);

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

        GridLayout gridLayout = (GridLayout)rootView.findViewById(R.id.boardLayout);
        gridLayout.setUseDefaultMargins(false); //REMIND required for 0px gap between cells
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(xDim);
        gridLayout.setRowCount(yDim);
        //gridLayout.setBackgroundColor(Color.RED); //TODO rimuovere a fine debug
//        Configuration configuration = activity.getResources().getConfiguration();
//        if ((configuration.orientation == Configuration.ORIENTATION_PORTRAIT)) {
//            gridLayout.setColumnOrderPreserved(false);
//        } else {
//            gridLayout.setRowOrderPreserved(false);
//        }
//        GridLayout.Spec cellRow = GridLayout.spec(1);
//        GridLayout.Spec centerInAllColumns    = GridLayout.spec(0, 4, GridLayout.CENTER);

        //GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams(); //use default values
//        for (int y = 0; y < yDim; ++y){
//            //row.setBackgroundColor(Color.YELLOW); //TODO rimuovere a fine debug
//            for (int x = 0; x < xDim; ++x){
//                Button btn = new Button(activity);
//                btn.setTag(board.cells[x][y]);
//                btn.setText(/*board.cells[x][y].toString()*/"" + (y * yDim + x));
//                btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
//                btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
//                btn.setOnClickListener(this);
//                gridLayout.addView(btn, new GridLayout.LayoutParams());
//
//                boardButtons[x][y] = btn;
//            }
//        }

        for (int y = 0; y < yDim; ++y){
            //row.setBackgroundColor(Color.YELLOW); //TODO rimuovere a fine debug
            for (int x = 0; x < xDim; ++x){
                Button btn = new Button(getActivity());
//                btn.setWidth(20);
//                btn.setMinimumWidth(20);
//                btn.setMaxWidth(20);
//
//                btn.setHeight(20);
//                btn.setMinimumHeight(20);
//                btn.setMaxHeight(20);

                btn.setTag(board.cells[x][y]);
//                btn.setText(/*board.cells[x][y].toString()*/"" + (y * yDim + x));
////                btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
//                btn.setBackgroundResource(R.drawable.cell_gray_rounded);
//                btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
                setDefaultButtonProperties(btn, false);
                btn.setOnClickListener(this);

                GridLayout.Spec rowSpec = GridLayout.spec(x+1, GridLayout.CENTER);
                GridLayout.Spec colSpec = GridLayout.spec(y, GridLayout.CENTER);
                GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams(rowSpec, colSpec);
//                cellParams.setGravity(Gravity.CENTER);
                gridLayout.addView(btn, cellParams);

                boardButtons[x][y] = btn;
            }
        }

//        //TABLE
//        TableLayout table = (TableLayout)rootView.findViewById(R.id.tableLayout);
//
//        FrameLayout.LayoutParams pTable = new FrameLayout.LayoutParams(
//                TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT);
//        table.setBackgroundColor(Color.RED); //TODO rimuovere a fine debug
//        table.setLayoutParams(pTable);
//        table.setPadding(0, 0, 0, 0); //TODO se non lo si mette a zero va sottratto l'insets a screen_w e screen_h
//
////        //ROW TOP
////        TableRow rowTop = new TableRow(this);
////
////        TableLayout.LayoutParams pRowTop = new TableLayout.LayoutParams(
////                TableLayout.LayoutParams.MATCH_PARENT,
////                TableLayout.LayoutParams.WRAP_CONTENT);
////        pRowTop.weight = 1;
////
////        rowTop.setBackgroundColor(Color.BLUE);
////
////        TextView txt = new TextView(this);
////        txt.setText("Top Content");
////
////        rowTop.addView(txt, new TableRow.LayoutParams(
////                TableRow.LayoutParams.MATCH_PARENT,
////                TableRow.LayoutParams.WRAP_CONTENT));
////
////        table.addView(rowTop, pRowTop);
//
//        //CELL ROWs
//        TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
//                TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT,
////                cell_size*xDim,
////                cell_size,
//                1.0f);
//        TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
////                ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.MATCH_PARENT,
//                cell_size,
//                cell_size,
//                1.0f);
//        for (int y = 0; y < yDim; ++y){
//            TableRow row = new TableRow(activity);
//            //row.setBackgroundColor(Color.YELLOW); //TODO rimuovere a fine debug
//            for (int x = 0; x < xDim; ++x){
//                Button btn = new Button(activity);
//                btn.setTag(board.cells[x][y]);
//                btn.setText(/*board.cells[x][y].toString()*/"" + (y * yDim + x));
//                btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
//                btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
//                btn.setOnClickListener(this);
//                row.addView(btn, cellLp);
//
//                boardButtons[x][y] = btn;
//            }
//            table.addView(row, rowLp);
//        }
//
////        TableRow rowBottom = new TableRow(this);
////        rowBottom.setBackgroundColor(Color.GREEN);
////
////        TextView txtBottom = new TextView(this);
////        txtBottom.setText("Bottom Content");
////
////        TableLayout.LayoutParams pRowBottom = new
////                TableLayout.LayoutParams(
////                TableLayout.LayoutParams.WRAP_CONTENT,
////                TableLayout.LayoutParams.WRAP_CONTENT);
////
////        rowBottom.addView(txtBottom, new TableRow.LayoutParams(
////                TableRow.LayoutParams.MATCH_PARENT,
////                TableRow.LayoutParams.WRAP_CONTENT));
////
////        table.addView(rowBottom, pRowBottom);
//
//        //TODO in alternativa a quanto segue, ci si potrebbe registrare come GlobalLayoutListener e leggere la dim del layout o della table stessa!
        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                ViewGroup rl = (ViewGroup)rootView.findViewById(R.id.rootLayout);
                Log.v(TAG, "root layout: "+rl.getWidth()+", "+rl.getHeight());

                GridLayout gl = (GridLayout)rootView.findViewById(R.id.boardLayout);
                Log.v(TAG, "grid layout: "+gl.getWidth()+", "+gl.getHeight());
                fillBoardLayout(rl, gl);

                ViewTreeObserver obs = gl.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
            }
        });
//        //oppure
//        table.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
//        {
//            @Override
//            public boolean onPreDraw()
//            {
//                return false;
//            }
//        });

        return rootView;
    }

    private void fillBoardLayout(ViewGroup rootLayout, GridLayout gridLayout)
    {
        int cell_size = (int)(Math.min(/*gridLayout*/rootLayout.getWidth()/xDim, /*gridLayout*/rootLayout.getHeight()/yDim));
        Log.v(TAG, "cell_size: "+cell_size);

//        for (int y = 0; y < yDim; ++y){
//            //row.setBackgroundColor(Color.YELLOW); //TODO rimuovere a fine debug
//            for (int x = 0; x < xDim; ++x){
//                Button btn = new Button(getActivity());
//                btn.setWidth(cell_size);
//                btn.setHeight(cell_size);
//                btn.setTag(board.cells[x][y]);
//                btn.setText(/*board.cells[x][y].toString()*/"" + (y * yDim + x));
//                btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
//                btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
//                btn.setOnClickListener(this);
//
//                GridLayout.Spec rowSpec = GridLayout.spec(x+1, GridLayout.CENTER);
//                GridLayout.Spec colSpec = GridLayout.spec(y, GridLayout.CENTER);
//                GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams(rowSpec, colSpec);
////                cellParams.setGravity(Gravity.CENTER);
//                gridLayout.addView(btn, cellParams);
//
//                boardButtons[x][y] = btn;
//            }
//        }

        Button btn;
        for(int i = 0; i < gridLayout.getChildCount(); i++)
        {
            btn = (Button)gridLayout.getChildAt(i);
            btn.setWidth(cell_size);
            btn.setMinimumWidth(cell_size);
            btn.setMaxWidth(cell_size);

            btn.setHeight(cell_size);
            btn.setMinimumHeight(cell_size);
            btn.setMaxHeight(cell_size);
        }
    }

    protected void setDefaultButtonProperties(Button btn, boolean alternate)
    {
        Cell cell = (Cell)btn.getTag();
        int x = cell.x;
        int y = cell.y;

        if(alternate){
            btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
            btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
        }else{
            btn.setBackgroundResource(R.drawable.cell_green_dark);
            btn.setTextColor(Color.WHITE);
        }

        btn.setText(/*board.cells[x][y].toString()*/"" + (y * yDim + x));
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.d(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onResume()
     */
    @Override
    public void onResume()
    {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onPause()
     */
    @Override
    public void onPause()
    {
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

//    /* (non-Javadoc)
//     * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
//     */
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        //super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.f_board, menu);
//    }
//
//    /* (non-Javadoc)
//     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        return true;
//		/*
//		// respond to menu item selection
//		switch(item.getItemId()){
//			case R.id.action_xxx:
//				Intent xxx = new Intent(getActivity(), XXX.class);
//				startActivity(xxx);
//				return true;
//				// break;
//
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//		*/
//    }


    @Override
    public void onClick(View view)
    {
        if(view instanceof Button){
            Cell cell = (Cell)view.getTag();

            synchronized(BoardFragment.this){
                Button button = (Button)view;
                if(button == lastButton){
                    lastDistance = (lastDistance+1) % Math.max(Math.max(cell.x + 1, xDim - (cell.x /*+ 1*/)), Math.max(cell.y + 1, yDim - (cell.y /*+ 1*/)));
                }else{
                    lastButton = button;
                    lastDistance = 1;
                }

                Toast.makeText(getActivity(), "" + cell + " (distance " + lastDistance + ")", Toast.LENGTH_SHORT).show();

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

            setDefaultButtonProperties(btn, false);
        }
        animatedButtons.clear();
    }

//    class ImageAdapter extends BaseAdapter
//    {
//        private Context mContext;
//
//        public ImageAdapter(Context c)
//        {
//            mContext = c;
//        }
//
//        public int getCount()
//        {
//            return xDim * yDim;
//        }
//
//        public Object getItem(int position)
//        {
//            return null;
//        }
//
//        public long getItemId(int position)
//        {
//            return 0;
//        }
//
//        // create a new ImageView for each item referenced by the Adapter
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//            ImageView imageView;
//            if (convertView == null) {  // if it's not recycled, initialize some attributes
//                imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new GridView.LayoutParams(cell_size, cell_size));
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////                imageView.setPadding(8, 8, 8, 8);
//            } else {
//                imageView = (ImageView) convertView;
//            }
//
//            int x = position%xDim;
//            int y = position/xDim;
//
//            imageView.setTag(board.cells[x][y]);
//            //imageView.setImageResource((x%2 == 0 && y%2 != 0)||(x%2 != 0 && y%2 == 0)?R.drawable.squareboard_even :R.drawable.squareboard_odd);
//            imageView.setImageResource((x+y)%2 == 0?R.drawable.squareboard_even :R.drawable.squareboard_odd);
//            return imageView;
//        }
//    }
}
