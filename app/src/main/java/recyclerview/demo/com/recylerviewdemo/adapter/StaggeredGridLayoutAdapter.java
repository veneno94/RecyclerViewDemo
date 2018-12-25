package recyclerview.demo.com.recylerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import recyclerview.demo.com.recylerviewdemo.R;

public class StaggeredGridLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> list;


    public StaggeredGridLayoutAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHolder itemHolder = new ItemHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false));
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final String str = list.get(position);
        if (holder instanceof ItemHolder) {


            final ItemHolder itemHolder = (ItemHolder) holder;

            itemHolder.title.setText(str);


        }

    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

}
