package util;

import java.io.File;

/**
 * Created by pengdapu on 2021/1/19 at 15:34.
 */

public class FileUtils {
    public static FileSearcher newSearch(String path){
        return new FileSearcher(path);
    }
    public static FileSearcher newSearch(File file){
        return new FileSearcher(file);
    }
    private FileUtils(){}
}
