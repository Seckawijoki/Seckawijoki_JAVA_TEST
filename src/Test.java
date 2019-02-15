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
    String str1 = new StringBuilder().append("计算机").append("系统").toString();
    String str2 = new StringBuilder().append("ja").append("va").toString();
    System.out.println("Test.main(): " + (str1.intern() == str1));
    System.out.println("Test.main(): " + (str2.intern() == str2));
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
