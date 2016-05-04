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
* ��Ŀ���ƣ�ImageLoader   
* �����ƣ�ImageAdapter   
* �������� GridView���������࣬��Ҫ��GridView������ʱ��ȡ����������
* ��ֹ��ʱ��ȥ���ص�ǰ��ʾ��item��ͼƬ   
* �����ˣ�fuli   
* ����ʱ�䣺2015��7��26�� ����9:33:31   
* �޸��ˣ�fuli   
* �޸�ʱ�䣺2015��7��26�� ����9:33:31   
* �޸ı�ע��   
* @version    
*    
*/
public class ImageAdapter  extends BaseAdapter implements OnScrollListener{

	/** 
     * �����Ķ�������� 
     */  
    private Context context;  
      
    /** 
     * Image Url������ 
     */  
    private String [] imageThumbUrls;  
      
    /** 
     * GridView�����Ӧ�� 
     */  
    private ListView mListView;  
      
    /** 
     * Image ������ 
     */  
    private ImageDownLoader mImageDownLoader;  
      
    /** 
     * ��¼�Ƿ�մ򿪳������ڽ��������򲻹�����Ļ����������ͼƬ�����⡣ 
     * �ο�http://blog.csdn.net/guolin_blog/article/details/9526203#comments 
     */  
    private boolean isFirstEnter = true;  
      
    /** 
     * һ���е�һ��item��λ�� 
     */  
    private int mFirstVisibleItem;  
      
    /** 
     * һ��������item�ĸ��� 
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
        //����GridView��ֹʱ��ȥ����ͼƬ��GridView����ʱȡ�������������ص�����    
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){  
           // showImage(mFirstVisibleItem, mVisibleItemCount);  
        }else{  
           // cancelTask();  
        }  
          
    }  
  
  
    /** 
     * GridView������ʱ����õķ������տ�ʼ��ʾGridViewҲ����ô˷��� 
     */  
    @Override  
    public void onScroll(AbsListView view, int firstVisibleItem,  
            int visibleItemCount, int totalItemCount) {  
        mFirstVisibleItem = firstVisibleItem;  
        mVisibleItemCount = visibleItemCount;  
        // ���������Ϊ�״ν����������������   
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
        price.setText(""+ways.get(position).getPlans_salenum()+"��"+"   "+ways.get(position).getPlans_price()+"Ԫ/��");
        /*******************************ȥ�������⼸��������ʲôЧ��****************************/  
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
     * ��ʾ��ǰ��Ļ��ͼƬ���Ȼ�ȥ����LruCache��LruCacheû�о�ȥsd�������ֻ�Ŀ¼���ң���û�оͿ����߳�ȥ���� 
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
     * ȡ���������� 
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

