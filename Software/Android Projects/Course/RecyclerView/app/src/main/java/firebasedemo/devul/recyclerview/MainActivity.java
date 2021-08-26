package firebasedemo.devul.recyclerview;

import android.graphics.Canvas;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MyClass> lst = new ArrayList<>();
    RecyclerView recyclerView;
    ProductAdapter myAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        lst.add(new MyClass("1", "a", R.drawable.ic_android_black_24dp, 0.0, (float) 5.0 , "hello"));
        lst.add(new MyClass("1", "a", R.drawable.ic_android_black_24dp, 0.0, (float) 5.0 , "hello"));
        lst.add(new MyClass("1", "a", R.drawable.ic_android_black_24dp, 0.0, (float) 5.0 , "hello"));
        lst.add(new MyClass("1", "a", R.drawable.ic_android_black_24dp, 0.0, (float) 5.0 , "hello"));
        lst.add(new MyClass("1", "a", R.drawable.ic_android_black_24dp, 0.0, (float) 5.0 , "hello"));

        myAdpter = new ProductAdapter(lst, MainActivity.this);

        recyclerView.setAdapter(myAdpter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final  MyClass no = lst.get(position);

                Button vw = (Button) view.findViewById(R.id.viewbtn);

                vw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Clicked from vw", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}