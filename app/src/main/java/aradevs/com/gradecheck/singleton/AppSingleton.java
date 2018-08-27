package aradevs.com.gradecheck.singleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ar4 on 25/08/2018.
 */

public class AppSingleton {


    // declaring useful variables
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private static AppSingleton singleton;
    private AppSingleton(Context context) {
        requestQueue = getRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {

                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new AppSingleton(context);
        }
        return singleton;
    }

    private RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void addToRequestQueue(Request req, Context context) {
        getRequestQueue(context).add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
