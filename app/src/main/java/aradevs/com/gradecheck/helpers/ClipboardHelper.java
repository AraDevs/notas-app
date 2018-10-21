package aradevs.com.gradecheck.helpers;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ar4 on 21/10/2018.
 */
public class ClipboardHelper {
    public ClipboardHelper() {
    }

    public void copyToClipBoard(Activity activity, String label, CharSequence text) {
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(label, text);
        assert cm != null;
        cm.setPrimaryClip(clipData);
        Toast.makeText(activity.getApplicationContext(), "Copiado al portapapeles", Toast.LENGTH_SHORT).show();
    }
}
