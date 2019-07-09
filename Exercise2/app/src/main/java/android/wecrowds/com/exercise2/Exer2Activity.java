package android.wecrowds.com.exercise2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Exer2Activity extends AppCompatActivity {
    private RecyclerView rv_01;

    private List<ItemList> itemList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer2);

        InitItem();
        rv_01 = findViewById(R.id.rv_01);
        rv_01.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        ItemAdapter itemAdapter = new ItemAdapter();
        itemAdapter.setList(itemList);
        rv_01.setAdapter(itemAdapter);

        itemAdapter.notifyDataSetChanged();
    }

    private void InitItem() {
        ItemList tmp1 = new ItemList(1,"敬礼我的超级英雄",548583);
        itemList.add(tmp1);
        ItemList tmp2 = new ItemList(2,"我们不一样YOUNG",504189);
        itemList.add(tmp2);
        ItemList tmp3 = new ItemList(3,"珍\"eye\"每一天",486636);
        itemList.add(tmp3);
        ItemList tmp4 = new ItemList(4,"请平安出行",301982);
        itemList.add(tmp4);
        ItemList tmp5 = new ItemList(5,"现在是怀旧时间",301928);
        itemList.add(tmp5);
        ItemList tmp6 = new ItemList(6,"纸短情长",299192);
        itemList.add(tmp6);
        ItemList tmp7 = new ItemList(7,"我们一起学猫叫",291049);
        itemList.add(tmp7);
    }
}
