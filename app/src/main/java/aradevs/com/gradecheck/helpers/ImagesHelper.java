package aradevs.com.gradecheck.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import aradevs.com.gradecheck.R;

/**
 * Created by Ar4 on 8/11/2018.
 */
public class ImagesHelper {
    public static void setImage(String url, ImageView imageView, Context context) {
        Picasso.get().load(url)
                .noFade()
                .placeholder(context.getResources().getDrawable(R.drawable.loading))
                .error(context.getResources().getDrawable(R.drawable.noimage))
                .into(imageView);
    }
}
