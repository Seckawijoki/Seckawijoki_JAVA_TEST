import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by pengdapu on 2021/1/12 at 11:54.
 */

class CompareClientToLua {
    private static final String[] SECONDARY_PATHS = {
            "android\\ClientToLua.pkg",
            "ios\\ClientToLua.pkg",
            "win32\\ClientToLua.pkg",
            "linux\\ClientToLua.pkg",
    };
    private static final String BASE_PATH = "F:\\trunk\\env1\\client\\miniGame\\iworld\\platform\\";

    private ArrayList<String>[] aDifferentHeaders = new ArrayList[SECONDARY_PATHS.length];
    private ArrayList<String>[] aPlatformHeaders = new ArrayList[SECONDARY_PATHS.length];
    private ArrayList<String> allHeaders = new ArrayList<>();
    private ArrayList<String> commonHeaders = new ArrayList<>();

    public static void main(String[] __) throws IOException {
        new CompareClientToLua().compare();
    }

    private void compareOther(){
        final int length = SECONDARY_PATHS.length;
        for ( String header : allHeaders ) {
            boolean common = true;
            for ( int j = 0 ; j < length ; ++j ) {
                ArrayList<String> platformHeader = aPlatformHeaders[j];
                if ( !platformHeader.contains(header) ) {
                    common = false;
                    break;
                }
            }
            if ( common ) {
                commonHeaders.add(header);
            } else {
                for ( int j = 0 ; j < length ; ++j ) {
                    ArrayList<String> platformHeader = aPlatformHeaders[j];
                    ArrayList<String> differentHeader = aDifferentHeaders[j];
                    if ( platformHeader.contains(header) ) {
                        differentHeader.add(header);
                    }
                }
            }
        }
    }

    private Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };

    private void compare() throws IOException {
        final int length = SECONDARY_PATHS.length;
        allHeaders.clear();
        for ( int i = 0 ; i < length ; ++i ) {
            aPlatformHeaders[i] = new ArrayList<>();
            aDifferentHeaders[i] = new ArrayList<>();
            aDifferentHeaders[i].add("$pfile \"ClientToLuaCommon.pkg\"");
            ArrayList<String> platformHeader = aPlatformHeaders[i];
            String secondaryPath = SECONDARY_PATHS[i];
            String path = BASE_PATH + secondaryPath;
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                if (line.isEmpty()){
                    continue;
                }
                platformHeader.add(line);
                if (allHeaders.contains(line)){
                    continue;
                }
                allHeaders.add(line);
            }
        }
//        allHeaders.sort(comparator);
        compareOther();

        System.out.println("common");
        for ( String header : commonHeaders ) {
            System.out.println(header);
        }
        for ( int i = 0 ; i < length ; ++i ) {
            String secondaryPath = SECONDARY_PATHS[i];
            System.out.println("secondaryPath = " + secondaryPath);
            ArrayList<String> differentHeader = aDifferentHeaders[i];
            for ( String header : differentHeader ) {
                System.out.println(header);
            }
        }
    }
}
