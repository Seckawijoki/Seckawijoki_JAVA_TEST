import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;

class ReplaceCodeAndHeader {
    private static final String PATH = "F:\\trunk\\env1\\client\\miniGame\\iworld";
    private static final String KEYWORD_HEADER = "ClientManager.h";
    private static final String KEYWORD_CODE = "g_ClientMgr.get_lua_const()";
//    private static final String KEYWORD_CODE = "g_ClientMgr.getLuaInterface()";
    private static final String OLD_CODE = "g_ClientMgr";
    private static final String REPLACE_HEADER = "LuaInterface.h";
    private static final String REPLACE_CODE = "LuaInterface::getSingleton().get_lua_const()";
//    private static final String REPLACE_CODE = "LuaInterface::getSingleton()";
    private static final String TEMP_OUTPUT_PATH = "tempReplaceFile";
    private static final String READ_CHARSET = "ASCII";
    private static final String WRITE_CHARSET = "UTF-8";
    public static void main(String[] args) {
        new ReplaceCodeAndHeader().find();
    }

    private void find() {
        File file = new File(PATH);
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
        if (!file.getName().endsWith(".cpp")){
            return;
        }
        boolean hasReplaced = replace(file);
        if (hasReplaced){
            System.out.println(file.getName());
        }
    }

    private boolean replace(File file){
        if ( file == null ) {
            return false;
        }
        if (file.isDirectory()){
            return false;
        }
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, READ_CHARSET);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            boolean hasOldClass = false;
            boolean hasHeader = false;
            boolean hasCode =  false;
            boolean hasTargetHeader = false;
            while ((line = bufferedReader.readLine()) != null){
                if (line.contains(REPLACE_HEADER)){
                    hasTargetHeader = true;
                }
                if (line.contains(KEYWORD_HEADER)){
                    hasHeader = true;
                }
                if (line.contains(OLD_CODE)){
                    hasOldClass = true;
                }
                if (line.contains(KEYWORD_CODE)){
                    hasCode = true;
                }
            }
            if (!hasCode || !hasHeader){
                return false;
            }
            bufferedReader.close();
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, READ_CHARSET);
            bufferedReader = new BufferedReader(inputStreamReader);
            ArrayList<String> lines = new ArrayList<>(2048);
            while ((line = bufferedReader.readLine()) != null){
//                if (line.contains(KEYWORD_HEADER) && !hasTargetHeader){
//                    if ( hasOldClass ) {
//                        line +=  "\r\n#include \"" + REPLACE_HEADER + "\"";
//                    } else {
//                        line = line.replace(KEYWORD_HEADER, REPLACE_HEADER);
//                    }
//                }
                line = line.replace(KEYWORD_HEADER, REPLACE_HEADER);
                line = line.replace(KEYWORD_CODE, REPLACE_CODE);
                lines.add(line);
            }
            bufferedReader.close();

            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, WRITE_CHARSET);
            for ( String s : lines ) {
                outputStreamWriter.write(s);
                outputStreamWriter.write('\r');
                outputStreamWriter.write('\n');
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();
            return true;
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( bufferedReader != null ) {
                    bufferedReader.close();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            try {
                if ( outputStreamWriter != null ) {
                    outputStreamWriter.close();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        return false;
    }
}