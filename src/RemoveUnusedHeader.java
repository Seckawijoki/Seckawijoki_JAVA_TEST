import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import util.FileContentDeleter;
import util.FileSearcher;
import util.FileUtils;

/**
 * Created by pengdapu on 2021/1/12 at 22:35.
 */

class RemoveUnusedHeader implements FileSearcher.OnFileSearchListener {
    private static final String PATH_MINIGAME = "F:\\trunk\\env1\\client\\miniGame\\";
    private static final String PATH_IWORLD =  PATH_MINIGAME + "iworld";
    private static final String PATH_OGRE_MAIN_EXT =  PATH_MINIGAME + "OgreMainExt";
    private static final String PATH_SCRIPT_SUPPORT =  PATH_MINIGAME + "scriptsupport";
    private static final String KEYWORD_HEADER = "ClientManager.h";
    private static final String INCLUDE_PREFIX = "#include";
    private static final String[] KEY_CODES = {
            "g_ClientMgr",
            "ClientManager",
    };

    public static void main(String[] args) {
        new RemoveUnusedHeader().removeAll();
    }

    private boolean containKeyCodes(String s){
        if ( s == null ) {
            return false;
        }
        for ( String keyCode : KEY_CODES ) {
            if (s.contains(keyCode)){
                String trim = s.trim();
                if (!trim.contains(INCLUDE_PREFIX)) {
                    return true;
                }
            }
        }
        return false;
    }

    private FileSearcher.FileSelector mCppFileSelector = new FileSearcher.FileSelector() {
        @Override
        public boolean select(File file) {
            String filename = file.getName();
            return filename.endsWith(".cpp")
                    || filename.endsWith(".h")
//                    || filename.endsWith(".c")
                    || filename.endsWith(".hpp")
                    ;
        }
    };

    private void removeAll() {
        FileSearcher fileSearcher = FileUtils.newSearch(PATH_IWORLD)
                .setFileSelector(mCppFileSelector)
                .setOnFileSearchListener(this)
                .search();
        fileSearcher.search(PATH_OGRE_MAIN_EXT);
        fileSearcher.search(PATH_SCRIPT_SUPPORT);
    }

    @Override
    public void onSearch(File file) {
        if (!checkIncludedButNotUsed(file)){
            return;
        }
        boolean deleted = new FileContentDeleter(file).addKeywords(KEY_CODES).delete();
        if (deleted){
            System.out.println("RemoveUnusedHeader.onSearch(): " + file.getName());
        }
    }

    private boolean checkIncludedButNotUsed(File file){
        if ( file == null ) {
            return false;
        }
        if (file.isDirectory()){
            return false;
        }
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            boolean hasHeader = false;
            int headerLineNumber = 0;
            String headerLine = null;

            boolean useClass =  false;
            int firstUseLineNumber = 0;
            String firstUseLine = null;
            while ((line = bufferedReader.readLine()) != null){
//                String trim = line.trim();
                String trim = line;
                if (!hasHeader){
                    ++headerLineNumber;
                }
                if (trim.contains(KEYWORD_HEADER)){
                    headerLine = line;
                    hasHeader = true;
                }
                ++firstUseLineNumber;
                if (containKeyCodes(trim)){
                    firstUseLine = line;
                    useClass = true;
                    break;
                }
            }
            if (hasHeader || useClass) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(file.getName()).append('\n');
                if ( hasHeader && headerLineNumber > 0 ) {
                    stringBuilder.append(String.format("%4d %s\n", headerLineNumber, headerLine));
                }
                if ( useClass && firstUseLineNumber > 0 ) {
                    stringBuilder.append(String.format("%4d %s\n", firstUseLineNumber, firstUseLine));
                }
                stringBuilder.append("===============\n");
//                System.out.println(stringBuilder);
            }
            return hasHeader && !useClass;
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return false;
    }
}
