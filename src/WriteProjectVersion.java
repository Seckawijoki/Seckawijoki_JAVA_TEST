import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class WriteProjectVersion {
    @Deprecated
    private static final int CHANGE_ANDROID_ANDROID_MANIFEST_XML = 0;
    private static final int CHANGE_CLIENT_MANAGER_CPP = 1;
    private static final int CHANGE_CLIENT_PREREQUISITES_H = 2;
    @Deprecated
    private static final int SWITCH_TO_BUILD_BLOCKARK_APK = 3;
    @Deprecated
    private static final int CHANGE_BUILD_GRADLE = 4;
    private static final int SWITCH_TO_BUILD_BLOCKARK_OBB = 5;
    private static final int CHANGE_BLOCKARK_OBB_ATTRIBUTE = 6;
    private static final int CHANGE_AS_APPPLAY_ROOT_BUILD_GRADLE = 7;
    private static final int SWITCH_TO_BUILD_LITE_VER_IN_CMAKE = 8;
    private static final String KEY_LINE_ANNOTATING_MARKS = "//";
    private static final String KEY_LINE_ANDROID_VERSION_CODE = "android:versionCode=";
    private static final String KEY_LINE_ANDROID_VERSION_NAME = "android:versionName=";
    private static final String KEY_LINE_VERSION_CODE = "versionCode";
    private static final String KEY_LINE_VERSION_NAME = "versionName";
    private static final String KEY_LINE_BUGLY_APP_VERSION = "BUGLY_APP_VERSION";
    private static final String KEY_LINE_BUGLY_ENABLE_DEBUG = "BUGLY_ENABLE_DEBUG";
    @Deprecated
    private static final String KEY_LINE_BUGLY_APP_VERSION_LOWER = "buglyAppVersion";
    @Deprecated
    private static final String KEY_LINE_BUGLY_ENABLE_DEBUG_LOWER = "buglyEnableDebug";
    private static final String KEY_LINE_S_CLIENT_VERSION = "static const char *sClientVersion";
    private static final String KEY_LINE_DEFINE_IWORLD_DEV_BUILD = "#define IWORLD_DEV_BUILD";
    @Deprecated
    private static final String KEY_LINE_DEPENDS_ON_NDK_BUILD = "compileTask -> compileTask.dependsOn ndkBuild";
    @Deprecated
    private static final String KEY_LINE_BUILD_ROOT_PATH = "def buildRootPath";
    @Deprecated
    private static final String KEY_LINE_NDK_BUILDING_DIR = "def ndkBuildingDir";
    @Deprecated
    private static final String KEY_LINE_ENGINE_ROOT_LOCAL = "\'ENGINE_ROOT_LOCAL";
    @Deprecated
    private static final String KEY_LINE_NDK_MODULE_PATH = "\'NDK_MODULE_PATH";
    @Deprecated
    private static final String KEY_LINE_APP_ABI = "\'APP_ABI";
    private static final String KEY_LINE_BUILD_BLOCKARK_OBB = "public static boolean mIsBuildApkObb";
    private static final String KEY_LINE_GET_MAIN_OBB_VERSION = "getMainObbVersion";
    private static final String KEY_LINE_GET_MAIN_OBB_FILE_SIZE = "getMainObbFileSize";
    private static final String KEY_LINE_GET_PATCH_OBB_VERSION = "getPatchObbVersion";
    private static final String KEY_LINE_SET_IS_LITE_VER = "set(IS_LITE_VERSION";
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static BufferedReader mReader;
    private static BufferedWriter mWriter;
    private static String mReadLine;
    private static File readFile;
    private static File writtenFile;
    private static String readCharset;
    private static String writeCharset;

    private static int[] splitVersionName(String versionName) throws IOException {
        if ( versionName == null || versionName.length() <= 0 ) return null;
        int[] versionDigits = new int[3];
        int firstIndex = versionName.indexOf('.');
        int lastIndex = versionName.lastIndexOf('.');
        versionDigits[0] = Integer.valueOf(versionName.substring(0, firstIndex));
        versionDigits[1] = Integer.valueOf(versionName.substring(firstIndex + 1, lastIndex - firstIndex + 1));
        versionDigits[2] = Integer.valueOf(versionName.substring(lastIndex + 1));
        return versionDigits;
    }

    private static void changeAsAppPlayRootBuildGradle(String versionName, boolean release) throws IOException {
        System.out.println("changeAsAppPlayRootBuildGradle(): versionName = " + versionName);
        if ( versionName == null || versionName.isEmpty() ) {
            System.out.println("changeAsAppPlayRootBuildGradle(): Empty version name");
            return;
        }
        int[] versionDigits = splitVersionName(versionName);
        if ( versionDigits == null || versionDigits.length < 3 ) {
            System.out.println("changeAsAppPlayRootBuildGradle(): Invalid version name: " + versionName);
            return;
        }
        int versionCode = versionDigits[0] * 65536 + versionDigits[1] * 256 + versionDigits[2];
        boolean hasWrittenVersionCode = false;
        boolean hasWrittenVersionName = false;
        outputStream = new FileOutputStream(writtenFile);
        mWriter = new BufferedWriter(new OutputStreamWriter(outputStream, writeCharset));
        while ( ( mReadLine = mReader.readLine() ) != null ) {
            if ( mReadLine.startsWith("<!-") ) {
                System.out.println("changeAsAppPlayRootBuildGradle(): mReadLine = " + mReadLine);
            }
            if ( hasWrittenVersionCode == false && mReadLine.contains(KEY_LINE_VERSION_CODE) ) {
                System.out.println("changeAsAppPlayRootBuildGradle(): Change versionCode");
                System.out.println("changeAsAppPlayRootBuildGradle(): oldLine = " + mReadLine);
                String newLine = KEY_LINE_VERSION_CODE + " = " + versionCode + '\n';
                System.out.println("changeAsAppPlayRootBuildGradle(): newLine = " + newLine);
                mWriter.write(newLine);
                hasWrittenVersionCode = true;
            } else if ( hasWrittenVersionName == false && mReadLine.contains(KEY_LINE_VERSION_NAME) ) {
                String newLine = KEY_LINE_VERSION_NAME + " = \"" + versionName + "\"\n";
                System.out.println("changeAsAppPlayRootBuildGradle(): Change versionName");
                System.out.println("changeAsAppPlayRootBuildGradle(): oldLine = " + mReadLine);
                System.out.println("changeAsAppPlayRootBuildGradle(): newLine = " + newLine);
                mWriter.write(newLine);
                hasWrittenVersionName = true;
            } else {
                mWriter.write(mReadLine);
                mWriter.write('\n');
            }
        }
        if ( hasWrittenVersionCode && hasWrittenVersionName )
            System.out.println("changeAsAppPlayRootBuildGradle(): Writing finished.");
        else {
            System.out.println("changeAsAppPlayRootBuildGradle(): Writing failed.");
            if ( hasWrittenVersionCode == false )
                System.out.println("changeAsAppPlayRootBuildGradle(): Writing versionCode failed.");
            if ( hasWrittenVersionName == false )
                System.out.println("changeAsAppPlayRootBuildGradle(): Writing versionName failed.");
        }
    }


    private static void changeBlockarkObbAttribute(String versionCode, String obbFileSize) throws IOException {
        outputStream = new FileOutputStream(writtenFile);
        mWriter = new BufferedWriter(new OutputStreamWriter(outputStream, writeCharset));
        System.out.println("changeBlockarkObbAttribute(): versionCode = " + versionCode);
        System.out.println("changeBlockarkObbAttribute(): obbFileSize = " + obbFileSize);
        boolean hasChangedMainObbVersion = false;
        boolean hasChangedMainObbFileSize = false;
        boolean hasChangedPatchObbVersion = false;
        while ( ( mReadLine = mReader.readLine() ) != null ) {
            if ( mReadLine.contains(KEY_LINE_ANNOTATING_MARKS) ) {
                mWriter.write(mReadLine);
                mWriter.write('\n');
                continue;
            }
            if ( hasChangedMainObbVersion == false && mReadLine.contains(KEY_LINE_GET_MAIN_OBB_VERSION) ) {
                mWriter.write(mReadLine);
                mWriter.write('\n');
                String newLine = "return " + versionCode + ";\n";
                System.out.println("changeBlockarkObbAttribute(): change getMainObbVersion()");
                System.out.println("changeBlockarkObbAttribute(): oldLine = " + ( mReadLine = mReader.readLine() ));
                System.out.println("changeBlockarkObbAttribute(): newLine = " + newLine);
                mWriter.write(newLine);
                hasChangedMainObbVersion = true;
            } else if ( hasChangedMainObbFileSize == false && mReadLine.contains(KEY_LINE_GET_MAIN_OBB_FILE_SIZE) ) {
                mWriter.write(mReadLine);
                mWriter.write('\n');
                String newLine = "return " + obbFileSize + "L;\n";
                System.out.println("changeBlockarkObbAttribute(): change getMainObbFileSize()");
                System.out.println("changeBlockarkObbAttribute(): oldLine = " + ( mReadLine = mReader.readLine() ));
                System.out.println("changeBlockarkObbAttribute(): newLine = " + newLine);
                mWriter.write(newLine);
                hasChangedMainObbFileSize = true;
            } else if ( hasChangedPatchObbVersion == false && mReadLine.contains(KEY_LINE_GET_PATCH_OBB_VERSION) ) {
                mWriter.write(mReadLine);
                mWriter.write('\n');
                String newLine = "return " + versionCode + ";\n";
                System.out.println("changeBlockarkObbAttribute(): change getPatchObbVersion()");
                System.out.println("changeBlockarkObbAttribute(): oldLine = " + ( mReadLine = mReader.readLine() ));
                System.out.println("changeBlockarkObbAttribute(): newLine = " + newLine);
                mWriter.write(newLine);
                hasChangedPatchObbVersion = true;
            } else {
                mWriter.write(mReadLine);
                mWriter.write('\n');
            }
        }
        if ( hasChangedMainObbVersion && hasChangedMainObbFileSize && hasChangedPatchObbVersion )
            System.out.println("changeBlockarkObbAttribute(): Writing finished.");
        else {
            if ( !hasChangedMainObbVersion )
                System.out.println("changeBlockarkObbAttribute(): Writing getMainObbVersion() failed.");
            if ( !hasChangedMainObbFileSize )
                System.out.println("changeBlockarkObbAttribute(): Writing getMainObbFileSize() failed.");
            if ( !hasChangedPatchObbVersion )
                System.out.println("changeBlockarkObbAttribute(): Writing getPatchObbVersion() failed.");
            System.out.println("changeBlockarkObbAttribute(): Writing failed.");
        }
    }

    private static void switchToBuildBlockarkObb(boolean buildObb) throws IOException {
        outputStream = new FileOutputStream(writtenFile);
        mWriter = new BufferedWriter(new OutputStreamWriter(outputStream, writeCharset));
        System.out.println("switchToBuildBlockarkObb(): buildObb = " + buildObb);
        boolean hasChanged = false;
        while ( ( mReadLine = mReader.readLine() ) != null ) {
            if ( hasChanged == false && mReadLine.contains(KEY_LINE_BUILD_BLOCKARK_OBB) ) {
                String newLine = KEY_LINE_BUILD_BLOCKARK_OBB + " = " + buildObb + ";\n";
                System.out.println("switchToBuildBlockarkObb(): change mIsBuildApkObb");
                System.out.println("switchToBuildBlockarkObb(): oldLine = " + mReadLine);
                System.out.println("switchToBuildBlockarkObb(): newLine = " + newLine);
                mWriter.write(newLine);
                hasChanged = true;
            } else {
                mWriter.write(mReadLine);
                mWriter.write('\n');
            }
        }
        if ( hasChanged )
            System.out.println("switchToBuildBlockarkObb(): Writing finished.");
        else
            System.out.println("switchToBuildBlockarkObb(): Writing failed.");
    }

    private static void close(Closeable closeable) {
        if ( closeable == null ) {
            return;
        }
        try {
            closeable.close();
        } catch ( IOException ignored ) {
        }
    }

    private static void changeClientPrerequisitesH(boolean annotate) throws IOException {
        boolean hasAnnotated = false;
        boolean needCopying = false;
        int annotationMarkIndex = 0;
        int macroDefinitionIndex = 0;
        File tempFile = new File("tempClientPrerequisitesH");
        FileWriter fileWriter = new FileWriter(tempFile);
        while ( ( mReadLine = mReader.readLine() ) != null ) {
            if ( hasAnnotated == false && ( macroDefinitionIndex = mReadLine.indexOf(KEY_LINE_DEFINE_IWORLD_DEV_BUILD) ) >= 0 ) {
                String newLine = "";
                annotationMarkIndex = mReadLine.indexOf(KEY_LINE_ANNOTATING_MARKS);

                System.out.println("changeClientPrerequisitesH(): annotationMarkIndex = " + annotationMarkIndex);
                System.out.println("changeClientPrerequisitesH(): macroDefinitionIndex = " + macroDefinitionIndex);
                hasAnnotated = annotationMarkIndex >= 0 && annotationMarkIndex < macroDefinitionIndex;
                needCopying = hasAnnotated != annotate;
                if ( needCopying ) {
                    if ( annotate ) {
                        newLine = KEY_LINE_ANNOTATING_MARKS + KEY_LINE_DEFINE_IWORLD_DEV_BUILD + '\n';
                    } else {
                        newLine = KEY_LINE_DEFINE_IWORLD_DEV_BUILD + '\n';
                    }
                } else {
                    System.out.println("changeClientPrerequisitesH(): no need to change");
                    break;
                }
                System.out.println("changeClientPrerequisitesH(): oldLine = " + mReadLine);
                System.out.println("changeClientPrerequisitesH(): newLine = " + newLine);
                fileWriter.write(newLine);
                hasAnnotated = true;
            } else {
                fileWriter.write(mReadLine);
                fileWriter.write('\n');
            }
        }

        fileWriter.flush();
        fileWriter.close();

        System.out.println("changeClientPrerequisitesH(): needCopying = " + needCopying);
        if ( needCopying ) {
            outputStream = new FileOutputStream(writtenFile);
            FileInputStream fileInputStream = new FileInputStream(tempFile);
            byte[] buffer = new byte[1024];
            int length = -1;
            while ( ( length = fileInputStream.read(buffer, 0, 1024) ) != -1 ) {
                System.out.println("changeClientPrerequisitesH(): length = " + length);
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            close(fileInputStream);
            close(outputStream);
        }
        tempFile.delete();

        if ( hasAnnotated ) {
            System.out.println("changeClientPrerequisitesH(): Writing finished.");
        }
    }

    private static void switchToBuildLiteVerInCmake(boolean lite) throws IOException {
        outputStream = new FileOutputStream(writtenFile);
        mWriter = new BufferedWriter(new OutputStreamWriter(outputStream, writeCharset));
        boolean hasWritten = false;
        String newLine = KEY_LINE_SET_IS_LITE_VER + ' ' + lite + ')' + '\n';
        while ( ( mReadLine = mReader.readLine() ) != null ) {
            if ( hasWritten == false && mReadLine.contains(KEY_LINE_SET_IS_LITE_VER) ) {
                System.out.println("changeClientManageCpp(): oldLine = " + mReadLine);
                System.out.println("changeClientManageCpp(): newLine = " + newLine);
                mWriter.write(newLine);
                hasWritten = true;
            } else {
                mWriter.write(mReadLine);
                mWriter.write('\n');
            }
        }
        if ( hasWritten ) {
            System.out.println("changeClientManageCpp(): Writing finished.");
        } else {
            mWriter.write(newLine);
            System.out.println("changeClientManageCpp(): add new line to end");
        }
    }

    private static void changeClientManageCpp(String versionName) throws IOException {
        outputStream = new FileOutputStream(writtenFile);
        mWriter = new BufferedWriter(new OutputStreamWriter(outputStream, writeCharset));
        if ( versionName == null || versionName.isEmpty() ) {
            System.out.println("changeClientManageCpp(): Empty version name");
            return;
        }
        boolean hasWritten = false;
        while ( ( mReadLine = mReader.readLine() ) != null ) {
            if ( hasWritten == false && mReadLine.contains(KEY_LINE_S_CLIENT_VERSION) ) {
                int leftIndex = mReadLine.indexOf('"');
                int rightIndex = mReadLine.lastIndexOf('"');
                System.out.println("changeClientManageCpp(): leftIndex = " + leftIndex);
                System.out.println("changeClientManageCpp(): rightIndex = " + rightIndex);
                String newLine = mReadLine.substring(0, leftIndex + 1) + versionName + mReadLine.substring(rightIndex) + '\n';
                System.out.println("changeClientManageCpp(): oldLine = " + mReadLine);
                System.out.println("changeClientManageCpp(): newLine = " + newLine);
                mWriter.write(newLine);
                hasWritten = true;
            } else {
                mWriter.write(mReadLine);
                mWriter.write('\n');
            }
        }
        if ( hasWritten )
            System.out.println("changeClientManageCpp(): Writing finished.");
        else
            System.out.println("changeClientManageCpp(): Writing failed.");
    }

    @Deprecated
    private static void changeAndroidManifestXml(String versionName, boolean release) throws IOException {
        outputStream = new FileOutputStream(writtenFile);
        mWriter = new BufferedWriter(new OutputStreamWriter(outputStream, writeCharset));
        System.out.println("changeAndroidManifestXml(): versionName = " + versionName);
        if ( versionName == null || versionName.isEmpty() ) {
            System.out.println("changeAndroidManifestXml(): Empty version name");
            return;
        }
        int[] versionDigits = splitVersionName(versionName);
        if ( versionDigits == null || versionDigits.length < 3 ) {
            System.out.println("changeAndroidManifestXml(): Invalid version name: " + versionName);
            return;
        }
        int versionCode = versionDigits[0] * 65536 + versionDigits[1] * 256 + versionDigits[2];
        boolean hasWrittenVersionCode = false;
        boolean hasWrittenVersionName = false;
        boolean hasWrittenBuglyAppVersion = false;
        boolean hasChangedBuglyEnableDebug = false;
        while ( ( mReadLine = mReader.readLine() ) != null ) {
            if ( mReadLine.startsWith("<!-") ) {
                System.out.println("changeAndroidManifestXml(): mReadLine = " + mReadLine);
            }
            if ( hasWrittenVersionCode == false && mReadLine.contains(KEY_LINE_ANDROID_VERSION_CODE) ) {
                int leftIndex = mReadLine.indexOf('"');
                int rightIndex = mReadLine.lastIndexOf('"');
                System.out.println("changeAndroidManifestXml(): Change android:versionCode");
                System.out.println("changeAndroidManifestXml(): leftIndex = " + leftIndex);
                System.out.println("changeAndroidManifestXml(): rightIndex = " + rightIndex);
                System.out.println("changeAndroidManifestXml(): oldLine = " + mReadLine);
                String newLine = mReadLine.substring(0, leftIndex + 1) + versionCode + mReadLine.substring(rightIndex) + '\n';
                System.out.println("changeAndroidManifestXml(): newLine = " + newLine);
                mWriter.write(newLine);
                hasWrittenVersionCode = true;
            } else if ( hasWrittenVersionName == false && mReadLine.contains(KEY_LINE_ANDROID_VERSION_NAME) ) {
                int leftIndex = mReadLine.indexOf('"');
                int rightIndex = mReadLine.lastIndexOf('"');
                String newLine = mReadLine.substring(0, leftIndex + 1) + versionName + mReadLine.substring(rightIndex) + '\n';
                System.out.println("changeAndroidManifestXml(): Change android:versionName");
                System.out.println("changeAndroidManifestXml(): leftIndex = " + leftIndex);
                System.out.println("changeAndroidManifestXml(): rightIndex = " + rightIndex);
                System.out.println("changeAndroidManifestXml(): oldLine = " + mReadLine);
                System.out.println("changeAndroidManifestXml(): newLine = " + newLine);
                mWriter.write(newLine);
                hasWrittenVersionName = true;
            } else if ( hasWrittenBuglyAppVersion == false && mReadLine.contains(KEY_LINE_BUGLY_APP_VERSION) ) {
                mWriter.write(mReadLine);
                mWriter.write('\n');
                mReadLine = mReader.readLine();
                int leftIndex = mReadLine.indexOf('"');
                int rightIndex = mReadLine.lastIndexOf('"');
                String newLine = mReadLine.substring(0, leftIndex + 1) + ( release ? versionName : "99.99.99" ) + mReadLine.substring(rightIndex) + '\n';
                System.out.println("changeAndroidManifestXml(): Change BUGLY_APP_VERSION");
                System.out.println("changeAndroidManifestXml(): release = " + release);
                System.out.println("changeAndroidManifestXml(): leftIndex = " + leftIndex);
                System.out.println("changeAndroidManifestXml(): rightIndex = " + rightIndex);
                System.out.println("changeAndroidManifestXml(): oldLine = " + mReadLine);
                System.out.println("changeAndroidManifestXml(): newLine = " + newLine);
                mWriter.write(newLine);
                hasWrittenBuglyAppVersion = true;
            } else if ( hasChangedBuglyEnableDebug == false && mReadLine.contains(KEY_LINE_BUGLY_ENABLE_DEBUG) ) {
                mWriter.write(mReadLine);
                mWriter.write('\n');
                mReadLine = mReader.readLine();
                int leftIndex = mReadLine.indexOf('"');
                int rightIndex = mReadLine.lastIndexOf('"');
                String newLine = mReadLine.substring(0, leftIndex + 1) + "false" + mReadLine.substring(rightIndex) + '\n';
                System.out.println("changeAndroidManifestXml(): Change BUGLY_ENABLE_DEBUG");
                System.out.println("changeAndroidManifestXml(): leftIndex = " + leftIndex);
                System.out.println("changeAndroidManifestXml(): rightIndex = " + rightIndex);
                System.out.println("changeAndroidManifestXml(): oldLine = " + mReadLine);
                System.out.println("changeAndroidManifestXml(): newLine = " + newLine);
                mWriter.write(newLine);
                hasChangedBuglyEnableDebug = true;
            } else {
                mWriter.write(mReadLine);
                mWriter.write('\n');
            }
        }
        if ( hasWrittenVersionCode && hasWrittenVersionName && hasWrittenBuglyAppVersion && hasChangedBuglyEnableDebug )
            System.out.println("changeAndroidManifestXml(): Writing finished.");
        else {
            System.out.println("changeAndroidManifestXml(): Writing failed.");
            if ( hasWrittenVersionCode == false )
                System.out.println("changeAndroidManifestXml(): Writing android:versionCode failed.");
            if ( hasWrittenVersionName == false )
                System.out.println("changeAndroidManifestXml(): Writing android:versionName failed.");
            if ( hasWrittenBuglyAppVersion == false )
                System.out.println("changeAndroidManifestXml(): Writing BUGLY_APP_VERSION failed.");
            if ( hasChangedBuglyEnableDebug == false )
                System.out.println("changeAndroidManifestXml(): Writing BUGLY_ENABLE_DEBUG failed.");
        }
    }

    private static String adjustVersionName(String versionName) {
        String[] versionDigits = versionName.split("\\.");
        System.out.println("Test.adjustVersionName(): versionDigits = " + Arrays.toString(versionDigits));
        StringBuilder stringBuilder = new StringBuilder();
        if ( versionDigits.length >= 3 ) {
            int i;
            for ( i = 0; i < versionDigits.length - 1 ; i++ )
                if ( versionDigits[i].length() > 0 ) {
                    stringBuilder.append(versionDigits[i]).append('.');
                }
            stringBuilder.append(versionDigits[i]);
        } else {
            stringBuilder.append(versionName);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
//      entrance(args);
        test();
    }

    public static void test() {
        entrance(
                new String[]{
                        "CMakeLists.txt",
                        "CMakeLists2.txt",
                        "8",
                        "true",
                }
        );
    }

    public static void entrance(String[] args) {
        if ( args == null ) {
            System.out.println("main(): args == null");
            return;
        }
        if ( args.length < 4 ) {
            System.out.println("main(): Length of arguments less than 4.");
            return;
        }
        readFile = new File(args[0]);
        writtenFile = new File(args[1]);
        int changeOption = Integer.valueOf(args[2]);
        String arg3 = args[3];
        switch ( changeOption ) {
            case CHANGE_ANDROID_ANDROID_MANIFEST_XML:
            case SWITCH_TO_BUILD_BLOCKARK_OBB:
            case CHANGE_BLOCKARK_OBB_ATTRIBUTE:
            case CHANGE_CLIENT_MANAGER_CPP:
            case SWITCH_TO_BUILD_LITE_VER_IN_CMAKE:
                readCharset = "UTF-8";
                writeCharset = "UTF-8";
                break;
            default:
            case CHANGE_CLIENT_PREREQUISITES_H:
            case CHANGE_BUILD_GRADLE:
            case CHANGE_AS_APPPLAY_ROOT_BUILD_GRADLE:
                readCharset = "GB2312";
                writeCharset = "GB2312";
                break;
        }
        System.out.println("main(): readFile = " + readFile);
        System.out.println("main(): writtenFile = " + writtenFile);
        System.out.println("main(): changeOption = " + changeOption);
        System.out.println("main(): readCharset = " + readCharset);
        System.out.println("main(): writeCharset = " + writeCharset);

        try {
            inputStream = new FileInputStream(readFile);
            mReader = new BufferedReader(new InputStreamReader(inputStream, readCharset));
            boolean release;
            switch ( changeOption ) {
                case CHANGE_ANDROID_ANDROID_MANIFEST_XML:
                    System.out.println("main(): deprecated option");
                    break;
                case CHANGE_CLIENT_MANAGER_CPP:
                    changeClientManageCpp(adjustVersionName(arg3));
                    break;
                case CHANGE_CLIENT_PREREQUISITES_H:
                    changeClientPrerequisitesH(Boolean.valueOf(arg3));
                    break;
                case SWITCH_TO_BUILD_BLOCKARK_APK:
                    System.out.println("main(): deprecated option");
                    break;
                case CHANGE_BUILD_GRADLE:
                    System.out.println("main(): deprecated option");
                    break;
                case SWITCH_TO_BUILD_BLOCKARK_OBB:
                    switchToBuildBlockarkObb(Boolean.valueOf(arg3));
                    break;
                case CHANGE_BLOCKARK_OBB_ATTRIBUTE:
                    if ( args.length < 5 ) {
                        System.out.println("main(): Length of arguments less than 5.");
                        return;
                    }
                    changeBlockarkObbAttribute(arg3, args[4]);
                    break;
                case CHANGE_AS_APPPLAY_ROOT_BUILD_GRADLE:
                    if ( args.length < 5 ) {
                        System.out.println("main(): Length of arguments less than 5.");
                        return;
                    }
                    release = false;
                    if ( args.length >= 5 ) {
                        release = Boolean.valueOf(args[4]);
                    }
                    changeAsAppPlayRootBuildGradle(adjustVersionName(arg3), release);
                    break;
                case SWITCH_TO_BUILD_LITE_VER_IN_CMAKE:
                    switchToBuildLiteVerInCmake(Boolean.valueOf(arg3));
                    break;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            close(mReader);
            close(mWriter);
        }
    }
}
