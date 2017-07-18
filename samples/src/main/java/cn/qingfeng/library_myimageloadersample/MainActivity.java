package cn.qingfeng.library_myimageloadersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.qingfeng.library_myimageloadersample.util.DensityUtils;
import cn.qingfeng.library_myimageloadersample.util.Images;
import cn.qingfeng.myimageloaderlibrary.ImageLoader;
import cn.qingfeng.myimageloaderlibrary.config.ImageLoaderConfig;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ImageLoaderConfig config = new ImageLoaderConfig.Builder(this)
                .setDefaultDrawable(R.mipmap.default_image)
                .setErrorDrawable(R.mipmap.error_image)
                .build();
        ImageLoader.getInstance(this).init(config);


        initUi();
    }


    private void initUi() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager
                .VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<Integer> mHeights = new ArrayList<Integer>();

        public MyAdapter() {

            for (int i = 0; i < Images.imageThumbUrls.length; i++) {
                mHeights.add((int) (DensityUtils.dp2px(MainActivity.this, 150) + Math.random() * DensityUtils.dp2px
                        (MainActivity.this, 100)));
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.item_recyclerview, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            ImageView imageView = holder.imageView;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height = mHeights.get(position);
            imageView.setLayoutParams(layoutParams);

            String path = Images.imageThumbUrls[position];
            ImageLoader.getInstance(MainActivity.this).displayImage(imageView, path);


        }

        @Override
        public int getItemCount() {
            return Images.imageThumbUrls.length;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
