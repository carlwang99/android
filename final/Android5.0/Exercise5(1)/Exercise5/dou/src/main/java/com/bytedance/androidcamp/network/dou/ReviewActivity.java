package com.bytedance.androidcamp.network.dou;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private EditText editReview;
    private LinearLayout linearLayout;
    private Display d;
    private Window win;
    private ImageView post;
    private WindowManager.LayoutParams lp;
    private RecyclerView review_rv;
    private List<ReviewItem> reviewItemList = new ArrayList<>();

    //对应review_Item的类
    private class ReviewItem{
        private String nickName;//昵称
        private String detail;//详细内容
        private String starNum;//点赞数量

        public ReviewItem(String nickName, String detail, String starNum){
            this.nickName = nickName;
            this.detail = detail;
            this.starNum = starNum;
        }

        public String getDetail() {
            return detail;
        }

        public String getNickName() {
            return nickName;
        }

        public String getStarNum() {
            return starNum;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setStarNum(String starNum) {
            this.starNum = starNum;
        }
    }

    public class ReviewItemViewHolder extends RecyclerView.ViewHolder {
        public TextView nickName;
        public TextView detail;
        public TextView starNum;
        public List<ReviewItem> reviewItemList;

        private int position;

        public ReviewItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), reviewItemList.get(position).getDetail(),Toast.LENGTH_SHORT).show();
                }
            });
            nickName = itemView.findViewById(R.id.nickname);
            detail = itemView.findViewById(R.id.detail);
            starNum = itemView.findViewById(R.id.starNum);
        }

        public void bind(List<ReviewItem> reviewItemList, ReviewItem itemList, int position) {
            nickName.setText(itemList.getNickName());
            detail.setText(itemList.getDetail());
            starNum.setText(itemList.getStarNum());
            this.position = position;
            this.reviewItemList = reviewItemList;
        }
    }

    public class ReviewItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<ReviewItem> reviewItemList;

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_view,parent,false);
            return new ReviewItemViewHolder(view);
        }


        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ((ReviewItemViewHolder)viewHolder).bind(reviewItemList, reviewItemList.get(i), i);
        }


        public int getItemCount() {
            return reviewItemList.size();
        }

        public void setList(List<ReviewItem> list){
            reviewItemList = list;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用于告诉Window页面切换需要使用动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_review);

        review_rv = findViewById(R.id.review_rv);
        review_rv.setLayoutManager(new LinearLayoutManager(this));

        initReviewList();

        final ReviewItemAdapter reviewItemAdapter = new ReviewItemAdapter();
        reviewItemAdapter.setList(reviewItemList);
        review_rv.setAdapter(reviewItemAdapter);

        reviewItemAdapter.notifyDataSetChanged();


        editReview = findViewById(R.id.editReview);
        linearLayout = findViewById(R.id.linear_layout);

        post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detail = editReview.getText().toString();
                if(detail.length()==0){
                    Toast.makeText(ReviewActivity.this, "空评论？？？", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReviewItem tmp1 = new ReviewItem("Carl",detail,"0");
                reviewItemList.add(0,tmp1);
                reviewItemAdapter.setList(reviewItemList);
                reviewItemAdapter.notifyDataSetChanged();
                Toast.makeText(ReviewActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                editReview.setText("");
            }
        });

        //用于设置窗口高度
        WindowManager m = getWindowManager();
        d = m.getDefaultDisplay();

        win = this.getWindow();
        lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int)(d.getHeight()*0.85);
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);

        setListenerToRootView();
    }

    private void initReviewList() {
        ReviewItem tmp1 = new ReviewItem("叶凡小哥哥","活得不如一只猫🐱","2.2w");
        reviewItemList.add(tmp1);
        ReviewItem tmp2 = new ReviewItem("凌水沟的鸭蛋","这个666啊","2179");
        reviewItemList.add(tmp2);
        ReviewItem tmp3 = new ReviewItem("叶凡小哥哥","我宁愿所有痛苦都留在我心里😘","1739");
        reviewItemList.add(tmp3);
        ReviewItem tmp4 = new ReviewItem("凌水沟的鸭蛋","也不愿忘记你的眼睛😊","469");
        reviewItemList.add(tmp4);
        ReviewItem tmp5 = new ReviewItem("叶凡小哥哥","Oh 越过谎言去拥抱你👀","23");
        reviewItemList.add(tmp5);
        ReviewItem tmp6 = new ReviewItem("凌水沟的鸭蛋","每当我找不到存在的意义😝","96");
        reviewItemList.add(tmp6);
    }

    private void setListenerToRootView() {
        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean mKeyboardUp = isKeyboardShown(rootView);
                if (mKeyboardUp) {
                    //键盘弹出
                    Rect r = new Rect();
                    //获取当前界面可视部分
                    ReviewActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                    //获取屏幕的高度
                    int screenHeight =  ReviewActivity.this.getWindow().getDecorView().getRootView().getHeight();
                    //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                    int heightDifference = screenHeight - r.bottom + linearLayout.getHeight()/2;
                    //要用父控件去设置属性
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
                    params.bottomMargin = heightDifference;
                    linearLayout.setLayoutParams(params);
                } else {
                    //键盘收起
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
                    params.bottomMargin = 0;
                    linearLayout.setLayoutParams(params);
                }
            }
        });
    }

    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

}
