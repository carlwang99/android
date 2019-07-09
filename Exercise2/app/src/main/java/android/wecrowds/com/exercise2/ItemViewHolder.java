package android.wecrowds.com.exercise2;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView id;
    public TextView hotValue;
    public List<ItemList> itemL;

    private int position;

    public ItemViewHolder(@NonNull final View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), itemL.get(position).getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        title = itemView.findViewById(R.id.titleName);
        id = itemView.findViewById(R.id.itemID);
        hotValue = itemView.findViewById(R.id.hotValue);
    }

    public void bind(List<ItemList> itemL, ItemList itemList, int position) {
        title.setText(itemList.getTitle());
        hotValue.setText(itemList.getHotValue() + "");
        id.setText(itemList.getId() + ".");
        if(position>=3) {
            id.setTextColor(Color.parseColor("#99FFFFFF"));
        }
        this.position = position;
        this.itemL = itemL;
    }
}
