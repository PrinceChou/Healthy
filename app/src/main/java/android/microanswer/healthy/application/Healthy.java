package android.microanswer.healthy.application;

import android.app.Application;
import android.content.res.Configuration;
import android.microanswer.healthy.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 应用程序类
 * Created by Micro on 2016/6/21.
 */

public class Healthy extends Application {
    public static ImageLoaderConfiguration ilc = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (ilc == null) {
            ilc = new ImageLoaderConfiguration.Builder(this).threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY)
                    .defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).showImageOnFail(R.mipmap.ic_img_faill)
                            .displayer(new FadeInBitmapDisplayer(200)).showImageOnLoading(R.mipmap.loading).build())
                    .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(1000).build();
        }
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(ilc);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


    public static void noUseMethod(Object... obj){

    }
}
