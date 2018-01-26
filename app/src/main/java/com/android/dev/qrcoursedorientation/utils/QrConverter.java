package com.android.dev.qrcoursedorientation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Guillaume Colletaz on 16/01/2018.
 */

public class QrConverter {

    public static int white = 0xFFFFFFFF;
    public static int black = 0xFF000000;

    public static Bitmap TextToImageEncode(String Value, int width) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    width, width, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        black : white;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public static void saveImage(Context context, String type,int num, Bitmap myBitmap, String folderName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(
                String.valueOf(Environment.getExternalStorageDirectory()+ "/QrCode/"+folderName));
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        String fileName;
        if(!Objects.equals(type, "checkpoint")){
            fileName = type;
        }else {
            fileName = type + "_num_" + num;
        }

        try {
            File f = new File(wallpaperDirectory, fileName + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

    static public void zipFolder(String srcFolder, String destZipFile){
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        try {
            fileWriter = new FileOutputStream(destZipFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        zip = new ZipOutputStream(fileWriter);
        try {
            addFolderToZip("", srcFolder, zip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            zip.flush();
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void addFileToZip(String path, String srcFile,
                                     ZipOutputStream zip) throws Exception {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    static private void addFolderToZip(String path, String srcFolder,
                                       ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/"
                        + fileName, zip);
            }
        }
    }

}
