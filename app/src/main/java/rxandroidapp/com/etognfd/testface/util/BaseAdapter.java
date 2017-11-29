package rxandroidapp.com.etognfd.testface.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import rxandroidapp.com.etognfd.testface.MainActivity;
import rxandroidapp.com.etognfd.testface.R;
import rxandroidapp.com.etognfd.testface.retrofit.api.bean.BaseBean;
import rxandroidapp.com.etognfd.testface.vo.RotateBean;

/**
 * Created by 14537 on 2017/11/24.
 */

public class BaseAdapter extends PagerAdapter {
    private OnItemClickListener itemClickListener;
    private Context mContext;
    private List<RotateBean> mData;
    private LayoutInflater inflater;
//    private Animation animOut;
    public BaseAdapter(Context context, List<RotateBean> data) {
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(mContext);
//        animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_cicle_out);
//        animOut.setFillAfter(true);
    }

    public void setData(List<RotateBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int newPosition = position % mData.size();
        View convertView = inflater.inflate(R.layout.item_vp, container, false);
//        convertView.startAnimation(animOut);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick( "url",newPosition);
                }
            }
        });
        imageView.setImageResource(mData.get(newPosition).getImgId());
        container.addView(convertView);
        return convertView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


    //点击事件接口
    public interface OnItemClickListener {
        void onItemClick(String str, int position);

    }

    //设置点击事件的方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

