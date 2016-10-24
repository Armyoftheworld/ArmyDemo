package dn.sindy.waterfalls;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SindyFalls extends ScrollView implements OnTouchListener {
    private LinearLayout firstColum, secondColum, thirdColum;
    private int firstHeight, secondHeight, thirdHeight;
    private int page;
    private SindyFalls scrollview;
    private int columWidth;
    private ImageLoader imageLoader;
    private LinearLayout mViewGroup;
    private static final int COUNT = 15;

    public SindyFalls(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollview = this;
        imageLoader = ImageLoader.getInstance();
        setOnTouchListener(this);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        //排列
        if (changed) {
            mViewGroup = (LinearLayout) getChildAt(0);
            firstColum = (LinearLayout) mViewGroup.findViewById(R.id.first_column);
            secondColum = (LinearLayout) mViewGroup.findViewById(R.id.second_column);
            thirdColum = (LinearLayout) mViewGroup.findViewById(R.id.third_column);
            columWidth = firstColum.getWidth();
            //加载图片
            LoadImages();
        }


    }


    private void LoadImages() {
        if (Utils.hasSDCard()) {
            int startIndex = page * COUNT;
            int endIndex = (page + 1) * COUNT;
            if (startIndex <= Images.imageUrls.length) {
                if (endIndex > Images.imageUrls.length) {
                    endIndex = Images.imageUrls.length;
                }
                for (int i = startIndex; i < endIndex; i++) {
                    //加载图片
                    ImageTask imageTask = new ImageTask();
                    ImageBean imageBean = new ImageBean();
                    imageBean.position = i;
                    imageBean.url = Images.imageUrls[i];
                    imageTask.execute(imageBean);
                }

            }
        }
        page++;
    }

    class ImageTask extends AsyncTask<ImageBean, Void, ArrayMap<String, Object>> {

        @Override
        protected ArrayMap<String, Object> doInBackground(ImageBean... params) {
            System.out.println("doInBackground position = " + params[0].position);
            return loadImages(params[0]);
        }

        //进行添加展示
        @Override
        protected void onPostExecute(ArrayMap<String, Object> map) {
            super.onPostExecute(map);
            Bitmap bitmap = (Bitmap) map.get("bitmap");
            int position = (int) map.get("position");
            System.out.println("onPostExecute position = " + position);
            double ratio = bitmap.getWidth() / (columWidth * 1.0f);
            int scaleHeight = (int) (bitmap.getHeight() / ratio);
            addImageToView(bitmap, scaleHeight, columWidth, position);
        }
    }

    private void addImageToView(Bitmap result, int scaleHeight, int columWidth, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columWidth, scaleHeight);
        ViewGroup view = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_item, null);
        ImageView iv = (ImageView) view.getChildAt(0);
        view.setLayoutParams(params);
        iv.setImageBitmap(result);
        TextView textView = (TextView) view.getChildAt(1);
        textView.setText("position == " + position);
        //接下来判断哪个View最小
        LinearLayout layout = findMinLinerlayout(columWidth, scaleHeight);
        layout.addView(view);
    }

    private LinearLayout findMinLinerlayout(int columWidth2, int scaleHeight) {
        if (firstHeight < secondHeight) {
            if (firstHeight < thirdHeight) {
                firstHeight += scaleHeight;
                return firstColum;
            } else {
                thirdHeight += scaleHeight;
                return thirdColum;
            }
        } else {
            if (secondHeight < thirdHeight) {
                secondHeight += scaleHeight;
                return secondColum;
            } else {
                thirdHeight += scaleHeight;
                return thirdColum;
            }
        }
    }

    private ArrayMap<String, Object> loadImages(ImageBean imageBean) {
        // TODO Auto-generated method stub
        //图片缓存  二级缓存   内存---sd----网上
        ArrayMap<String, Object> map = new ArrayMap<>(2);
        Bitmap bitmap = com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImageSync(imageBean.url);
        map.put("position", imageBean.position);
        if (bitmap != null) {
            map.put("bitmap", bitmap);
        }
//        else {
//            File file = new File(Utils.getImagePath(imageBean.url));
//            if (!file.exists()) {//sd当中不存在
//                //网上进行下载
//                Utils.downloadImage(imageBean.url, columWidth);
//            }
//            if (imageBean.url != null) {
//                bitmap = ImageLoader.decodeSampledBitmapFromResource(file.getPath(), columWidth);
//                if (bitmap != null) {
//                    map.put("bitmap", bitmap);
//                }
//            }
//        }
        return map;
    }

    int lastY = -1;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int scrollY = scrollview.getScrollY();
            if (scrollY == lastY) {//滑到了手机底部
                //加载下一页的图片
                LoadImages();
            } else {
                //
                lastY = scrollY;
                mHandler.sendEmptyMessageAtTime(0, 5);
            }

        }

        ;
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mHandler.sendEmptyMessageAtTime(0, 5);
        return false;
    }

}
