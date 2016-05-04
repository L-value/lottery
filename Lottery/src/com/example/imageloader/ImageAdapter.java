package com.example.imageloader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bean.Way;
import com.example.imageloader.ImageDownLoader.onImageLoaderListener;
import com.example.lottery.R;
import com.lottery.utils.AndroidTools;

/**   
*    
* 项目名称：ImageLoader   
* 类名称：ImageAdapter   
* 类描述： GridView的适配器类，主要是GridView滑动的时候取消下载任务，
* 静止的时候去下载当前显示的item的图片   
* 创建人：fuli   
* 创建时间：2015年7月26日 上午9:33:31   
* 修改人：fuli   
* 修改时间：2015年7月26日 上午9:33:31   
* 修改备注：   
* @version    
*    
*/
public class ImageAdapter  extends BaseAdapter implements OnScrollListener{

	/** 
     * 上下文对象的引用 
     */  
    private Context context;  
      
    /** 
     * Image Url的数组 
     */  
    private String [] imageThumbUrls;  
      
    /** 
     * GridView对象的应用 
     */  
    private ListView mListView;  
      
    /** 
     * Image 下载器 
     */  
    private ImageDownLoader mImageDownLoader;  
      
    /** 
     * 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。 
     * 参考http://blog.csdn.net/guolin_blog/article/details/9526203#comments 
     */  
    private boolean isFirstEnter = true;  
      
    /** 
     * 一屏中第一个item的位置 
     */  
    private int mFirstVisibleItem;  
      
    /** 
     * 一屏中所有item的个数 
     */  
    private int mVisibleItemCount;  
    private List<Way> ways;
      
      
    public ImageAdapter(Context context, ListView mGridView, String [] imageThumbUrls,List<Way> ways){  
        this.context = context;  
        this.mListView = mGridView;  
        this.imageThumbUrls = imageThumbUrls;
        this.ways = ways;
      //  mImageDownLoader = new ImageDownLoader(context);  
     //   mListView.setOnScrollListener(this);  
    }  
      
    @Override  
    public void onScrollStateChanged(AbsListView view, int scrollState) {  
        //仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务    
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){  
           // showImage(mFirstVisibleItem, mVisibleItemCount);  
        }else{  
           // cancelTask();  
        }  
          
    }  
  
  
    /** 
     * GridView滚动的时候调用的方法，刚开始显示GridView也会调用此方法 
     */  
    @Override  
    public void onScroll(AbsListView view, int firstVisibleItem,  
            int visibleItemCount, int totalItemCount) {  
        mFirstVisibleItem = firstVisibleItem;  
        mVisibleItemCount = visibleItemCount;  
        // 因此在这里为首次进入程序开启下载任务。   
        if(isFirstEnter && visibleItemCount > 0){  
           // showImage(mFirstVisibleItem, mVisibleItemCount);  
            isFirstEnter = false;  
        }  
    }  
      
  
    @Override  
    public int getCount() {  
        return ways.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return ways.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
    //	AndroidTools.showToastShort(context, "position"+ways.get(position).getDetail());
        ImageView imageView = null;
        TextView way,price,brought,detial;
        if (null == convertView)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.lottery_item, null);
		}
        imageView = (ImageView) convertView.findViewById(R.id.lottery);
        if (ways.get(position).getPlans_name().contains("A"))
		{
			imageView.setBackgroundResource(R.drawable.way_a);
		}else if (ways.get(position).getPlans_name().contains("B"))
		{
			imageView.setBackgroundResource(R.drawable.way_b);
		}else if (ways.get(position).getPlans_name().contains("C"))
		{
			imageView.setBackgroundResource(R.drawable.way_c);
		}
     //   way = (TextView) convertView.findViewById(R.id.way);
    //    way.setText(ways.get(position).getPlans_name()+"   "+ways.get(position).getPlans_salenum());
        brought = (TextView) convertView.findViewById(R.id.brought);
        brought.setText(ways.get(position).getPlans_storage()+"");
        detial = (TextView) convertView.findViewById(R.id.detial);
        detial.setText(ways.get(position).getDetail());
        price = (TextView) convertView.findViewById(R.id.price);
        price.setText(""+ways.get(position).getPlans_salenum()+"酬"+"   "+ways.get(position).getPlans_price()+"元/酬");
        /*******************************去掉下面这几行试试是什么效果****************************/  
     //   Bitmap bitmap = mImageDownLoader.showCacheBitmap(mImageUrl.replaceAll("[^\\w]", ""));  
     //   if(bitmap != null){  
     //       mImageView.setImageBitmap(bitmap);  
     //   }else{  
     //       mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));  
    //    }  
        /**********************************************************************************/  
          
          
        return convertView;  
    }  
      
    /** 
     * 显示当前屏幕的图片，先会去查找LruCache，LruCache没有就去sd卡或者手机目录查找，在没有就开启线程去下载 
     * @param firstVisibleItem 
     * @param visibleItemCount 
     */  
    private void showImage(int firstVisibleItem, int visibleItemCount){  
        Bitmap bitmap = null;  
        for(int i=firstVisibleItem; i<firstVisibleItem + visibleItemCount; i++){  
            String mImageUrl = imageThumbUrls[i];  
            final ImageView mImageView = (ImageView) mListView.findViewWithTag(mImageUrl);  
            bitmap = mImageDownLoader.downloadImage(mImageUrl, new onImageLoaderListener() {  
                  
                @Override  
                public void onImageLoader(Bitmap bitmap, String url) {  
                    if(mImageView != null && bitmap != null){  
                        mImageView.setImageBitmap(bitmap);  
                    }  
                      
                }  
            });  
              
            //if(bitmap != null){  
            //  mImageView.setImageBitmap(bitmap);  
            //}else{  
            //  mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_empty));  
            //}  
        }  
    }  
  
    /** 
     * 取消下载任务 
     */  
    public void cancelTask(){  
        mImageDownLoader.cancelTask();  
    }  
    public void addMoreData(List<Way> collection){
    	if (ways != null)
		{
			ways.addAll(collection);
		}
    }
  
}  

