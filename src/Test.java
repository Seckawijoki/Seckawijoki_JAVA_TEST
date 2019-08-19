import com.sun.jndi.toolkit.url.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import classloader.Singleton;
import sun.rmi.runtime.Log;

/**
 * Created by Administrator on 2018/11/29 at 11:53.
 */

public class Test {
  public static void main(String[] args) throws Throwable {
//    String url = "https://community-test.miniworldbox.com/#/login?portrait=1&uin=205135751&ver=0.33.10&apiid=1&lang=0&country=CN&time=1552031261&auth=6b1435a341a095e1623f05fdfe4e0673&s2t=1552031234";
//    Map map = getQueryMap(url);
//    System.out.println("Test.main(): map.get(\\\"portrait\\\") = " + map.get("portrait"));
//    System.out.println("Test.main(): getQueryParameter(url, \\\"portrait\\\") = " + getQueryParameter(url, "portrait"));

//    Singleton singleton = Singleton.getSingleton();
    String qrCode = "{\\\"IsRoomOwner\\\":1,\\\"PW\\\":\\\"\\\",\\\"RoomUin\\\":639870912,\\\"Time\\\":1557737646,\\\"Type\\\":\\\"joinroom\\\"}";
    System.out.println("Test.main(): qrCode = " + qrCode);
    String qrCodeJson = qrCode.replaceAll("\"", "\\\\\"");
    System.out.println("Test.main(): qrCodeJson = " + qrCodeJson);
//    System.out.println("Test.main(): Singleton.class = " + Singleton.class);
//    System.out.println("Test.main(): singleton.getClass() = " + Singleton.getSingleton().getClass());
//    System.out.println("Test.main(): Class = " + Class.forName("classloader.Singleton"));
//    System.out.println("Test.main(): singleton.counter1 = " + singleton.counter1);
//    System.out.println("Test.main(): singleton.counter2 = " + singleton.counter2);

    String versionName = "0....37......5";
    String adjustment = adjustVersionName(versionName);
    System.out.println("Test.main(): adjustment = " + adjustment);

  }

  private static String adjustVersionName(String versionName){
    String[] versionDigits = versionName.split("\\.");
    System.out.println("Test.adjustVersionName(): versionDigits = " + Arrays.toString(versionDigits));
    StringBuilder stringBuilder = new StringBuilder();
    if (versionDigits.length >= 3) {
      int i;
      for ( i = 0 ; i < versionDigits.length - 1 ; i++ ) if (versionDigits[i].length() > 0) {
        stringBuilder.append(versionDigits[i]).append('.');
      }
      stringBuilder.append(versionDigits[i]);
    } else {
      stringBuilder.append(versionName);
    }
    return stringBuilder.toString();
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

  public static String getQueryParameter(String url, String key) {
    String query = url.substring(url.lastIndexOf("?") + 1);
    String[] params = query.split("&");
    for (String param : params) {
      String name = param.split("=")[0];
      if (name != null && name.equals(key)){
          return param.split("=")[1];
      }
    }
    return null;
  }

  public static Map<String, String> getQueryMap(String url) {
    String query = url.substring(url.lastIndexOf("?") + 1);
    System.out.println("Test.getQueryMap(): query = " + query);
    String[] params = query.split("&");
    System.out.println("Test.getQueryMap(): params = " + Arrays.toString(params));
    Map<String, String> map = new HashMap<String, String>();
    for (String param : params)
    {
      String name = param.split("=")[0];
      String value = param.split("=")[1];
      map.put(name, value);
    }
    return map;
  }

}
