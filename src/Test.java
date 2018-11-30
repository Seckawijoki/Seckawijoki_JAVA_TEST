import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.rmi.runtime.Log;

/**
 * Created by Administrator on 2018/11/29 at 11:53.
 */

public class Test {
  public static void main(String[] args) {
    String filepath = "aa/bbb/ccc/ddd/eee.txt";
    File file = new File(filepath);
    System.out.println("Test.main(): file = " + file);
    System.out.println("Test.main(): file.exists() = " + file.exists());
    System.out.println("Test.main(): file.canWrite() = " + file.canWrite());
    System.out.println("Test.main(): file.getName() = " + file.getName());
    boolean result = createFile(filepath);
    System.out.println("Test.main(): result = " + result);
    saveBitmap(new byte[]{ (byte) 135 }, filepath);
  }
  public static boolean createFile(String filepath) {
    File file = new File(filepath);
    if (file.exists()) {
      return true;
    }
    File parentDir = file.getParentFile();
    if (!parentDir.exists() && !parentDir.mkdirs()) {
      return false;
    }
    try {
      return file.createNewFile();
    } catch (IOException e) {
      return false;
    }
  }
  public static void saveBitmap(byte[] bytes,String savePath){
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(savePath);
      BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
      bos.write(bytes);
      bos.flush();
      bos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
