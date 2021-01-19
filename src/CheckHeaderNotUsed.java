import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pengdapu on 2021/1/12 at 22:35.
 */

class CheckHeaderNotUsed {
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
        new CheckHeaderNotUsed().find();
    }

    private void find() {
        File file = new File(PATH_IWORLD);
        find(file);
        file = new File(PATH_OGRE_MAIN_EXT);
        find(file);
        file = new File(PATH_SCRIPT_SUPPORT);
        find(file);
    }

    private void find(File file){
        if ( file == null ) {
            return;
        }
        if (file.isDirectory()){
            File directory = file;
            File[] files = directory.listFiles();
            if (files.length <= 0){
                return;
            }
            final int count = files.length;
            for ( int i = 0 ; i < count ; ++i ) {
                find(files[i]);
            }
            return;
        }
        boolean notIncluded = checkIncludedButNotUsed(file);
        if (notIncluded){
            System.out.println(file.getName());
        }
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

    private boolean checkIncludedButNotUsed(File file){
        if ( file == null ) {
            return false;
        }
        if (file.isDirectory()){
            return false;
        }
        String encode;
        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            encode = inputStreamReader.getEncoding();
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
                stringBuilder.append(encode).append('\n');
                if ( hasHeader && headerLineNumber > 0 ) {
                    stringBuilder.append(String.format("%4d %s\n", headerLineNumber, headerLine));
                }
                if ( useClass && firstUseLineNumber > 0 ) {
                    stringBuilder.append(String.format("%4d %s\n", firstUseLineNumber, firstUseLine));
                }
                stringBuilder.append("===============\n");
                System.out.println(stringBuilder);
            }
            return hasHeader && !useClass;
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return false;
    }
}
