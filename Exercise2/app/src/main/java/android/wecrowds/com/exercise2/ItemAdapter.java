package android.wecrowds.com.exercise2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemList> itemList;

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ItemViewHolder(view);
    }


    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((ItemViewHolder)viewHolder).bind(itemList, itemList.get(i), i);
    }


    public int getItemCount() {
        return itemList.size();
    }

    public void setList(List<ItemList> list){
        itemList = list;
    }
}
