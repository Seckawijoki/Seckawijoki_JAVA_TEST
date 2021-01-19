package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pengdapu on 2021/1/19 at 16:20.
 */

public class FileContentDeleter {
    private List<String> mKeywordList = new ArrayList<>(4);
    private File mTargetFile;
    public FileContentDeleter(){}
    public FileContentDeleter(String path){
        this(new File(path));
    }

    public FileContentDeleter(File targetFile) {
        mTargetFile = targetFile;
    }

    public FileContentDeleter addKeyword(String keyword){
        mKeywordList.add(keyword);
        return this;
    }

    public FileContentDeleter addKeywords(String[] keywords){
        mKeywordList.addAll(Arrays.asList(keywords));
        return this;
    }

    private boolean hasKeyword(String line){
        if ( line == null ) {
            return false;
        }
        for ( String keyword : mKeywordList ) {
            if (line.contains(keyword)){
                return true;
            }
        }
        return false;
    }

    public boolean delete(){
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        StringBuilder stringBuilder = new StringBuilder();
        String encode;
        try {
            inputStream = new FileInputStream(mTargetFile);
            inputStreamReader = new InputStreamReader(inputStream);
            encode = inputStreamReader.getEncoding();
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                if (hasKeyword(line)){
                    continue;
                }
                stringBuilder.append(line).append('\n');
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(bufferedReader);
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(mTargetFile);
            outputStream.write(stringBuilder.toString().getBytes(encode));
            outputStream.flush();
        } catch ( IOException e ) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(outputStream);
        }

        System.out.println(encode + "--------" + mTargetFile.getName());
        return true;
    }
}
