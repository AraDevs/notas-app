package aradevs.com.gradecheck.helpers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Ar4 on 8/11/2018.
 */
public class ZipHelper {

    public void zip(InputStream file, String destination, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(destination);
        ZipOutputStream zipOut = new ZipOutputStream(fos);/*
        File fileToZip = new File(Objects.requireNonNull(file.getPath()));
        //FileInputStream fis = new FileInputStream(fileToZip);

        InputStream fis = a.getContentResolver().openInputStream(file);*/
        ZipEntry zipEntry = new ZipEntry(name);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = file.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        file.close();
        fos.close();
    }
}
