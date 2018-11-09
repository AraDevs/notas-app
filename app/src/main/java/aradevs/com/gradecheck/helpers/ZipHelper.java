package aradevs.com.gradecheck.helpers;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Ar4 on 8/11/2018.
 */
public class ZipHelper {

    public void zip(String file, String destination, Activity a) throws IOException {
        FileOutputStream fos = new FileOutputStream(destination);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(file);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }
}
