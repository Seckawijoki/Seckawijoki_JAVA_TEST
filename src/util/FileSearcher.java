package util;

import java.io.File;

/**
 * Created by pengdapu on 2021/1/19 at 15:48.
 */

public class FileSearcher {
    private OnFileSearchListener mOnFileSearchListener;
    private FileSelector mFileSelector;
    private FileExcluder mFileExcluder;
    private File mEntranceFile;
    public FileSearcher(String path) {
        this(new File(path));
    }

    public FileSearcher(File file) {
        mEntranceFile = file;
    }

    public FileSearcher setOnFileSearchListener(OnFileSearchListener onFileSearchListener) {
        mOnFileSearchListener = onFileSearchListener;
        return this;
    }

    public FileSearcher setFileSelector(FileSelector fileSelector) {
        mFileSelector = fileSelector;
        return this;
    }

    public FileSearcher setFileExcluder(FileExcluder fileExcluder) {
        mFileExcluder = fileExcluder;
        return this;
    }

    public FileSearcher search(){
        _search(mEntranceFile);
        return this;
    }

    public FileSearcher search(String path){
        mEntranceFile = new File(path);
        _search(mEntranceFile);
        return this;
    }

    public FileSearcher search(File file){
        mEntranceFile = file;
        _search(mEntranceFile);
        return this;
    }

    private void _search(File file){
        if ( file == null ) {
            return;
        }
        if (!file.exists()){
            return;
        }
        if (file.isDirectory()){
            File directory = file;
            File[] files = directory.listFiles();
            for ( File f : files ) {
                _search(f);
            }
            return;
        }
        if (mFileExcluder != null && mFileExcluder.exclude(file)){
            return;
        }
        if (mFileSelector != null && !mFileSelector.select(file)){
            return;
        }
        if ( mOnFileSearchListener != null ) {
            mOnFileSearchListener.onSearch(file);
        }
    }

    public interface OnFileSearchListener{
        void onSearch(File file);
    }
    public interface FileSelector{
        boolean select(File file);
    }
    public interface FileExcluder{
        boolean exclude(File file);
    }
}
