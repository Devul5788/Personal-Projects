package firebasedemo.devul.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<MyClass> mylist;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,price;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title1);
            price = (TextView) view.findViewById(R.id.price);
            img = (ImageView) view.findViewById(R.id.list_image);
        }
    }


    public ProductAdapter(List<MyClass> moviesList,Context c) {
        this.mylist= moviesList;
        this.context=c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyClass my = mylist.get(position);
        holder.title.setText(my.getName());
        holder.price.setText("₹" + my.getPrice());
        Glide.with(context)
                .load(my.getThumb())
                .into(holder.img);
       holder.img.setImageResource(my.getImg());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
}
