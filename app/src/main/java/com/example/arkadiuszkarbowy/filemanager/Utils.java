package com.example.arkadiuszkarbowy.filemanager;

import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * Created by arkadiuszkarbowy on 23/10/15.
 */
public class Utils {
    public static String getMimeType(String url)
    {
        String parts[]=url.split("\\.");
        String extension=parts[parts.length-1];
        String type = null;
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String parseFileName(String path) {
        String filename;
        int index = path.lastIndexOf(File.separator) + 1;
        if (index != 0)
            filename = path.substring(index);
        else
            filename = File.separator;

        return filename;
    }
}
