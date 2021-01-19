package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by pengdapu on 2021/1/19 at 16:27.
 */

public class IOUtils {
    public static void close(InputStream inputStream){
        if ( inputStream == null ) {
            return;
        }
        try {
            inputStream.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
    public static void close(OutputStream outputStream){
        if ( outputStream == null ) {
            return;
        }
        try {
            outputStream.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
    public static void close(Reader reader){
        if ( reader == null ) {
            return;
        }
        try {
            reader.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
    public static void close(Writer writer){
        if ( writer == null ) {
            return;
        }
        try {
            writer.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
