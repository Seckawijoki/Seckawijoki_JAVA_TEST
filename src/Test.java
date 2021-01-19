import com.sun.jndi.toolkit.url.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
//    String qrCode = "{\\\"IsRoomOwner\\\":1,\\\"PW\\\":\\\"\\\",\\\"RoomUin\\\":639870912,\\\"Time\\\":1557737646,\\\"Type\\\":\\\"joinroom\\\"}";
//    System.out.println("Test.main(): qrCode = " + qrCode);
//    String qrCodeJson = qrCode.replaceAll("\"", "\\\\\"");
//    System.out.println("Test.main(): qrCodeJson = " + qrCodeJson);
//    System.out.println("Test.main(): Singleton.class = " + Singleton.class);
//    System.out.println("Test.main(): singleton.getClass() = " + Singleton.getSingleton().getClass());
//    System.out.println("Test.main(): Class = " + Class.forName("classloader.Singleton"));
//    System.out.println("Test.main(): singleton.counter1 = " + singleton.counter1);
//    System.out.println("Test.main(): singleton.counter2 = " + singleton.counter2);

//    String versionName = "0....37......5";
//    String adjustment = adjustVersionName(versionName);
//    System.out.println("Test.main(): adjustment = " + adjustment);

    final int count = 22;
    for ( int i = 1 ; i <= count ; ++i ) {
      String template = "[" + i + "] = " + i + ",";
      System.out.println(template);
    }
//    for ( int i = 1 ; i <= count ; ++i ) {
//      System.out.println("<Button name=\"$parentPartTypeBtn" + i + "\" inherits=\"TemplateARCustomSkinLibPartTypeBtn\"  id=\"" + i + "\"/>");
//    }
//    for ( int i = 1 ; i <= count * 6 ; ++i ) {
//      System.out.println("<Button name=\"$parentIncrease" + i + "\" inherits=\"TemplateARIncreaseBtn\" id=\"" + i + "\"/>");
//    }
//    for ( int i = 81 ; i <= 115 ; ++i ) {
//      System.out.println("<FontString name=\"$parentData" + i + "\" fonttype=\"BlackFont18\" textcolor=\"#ffff0000\" justifyV=\"middle\" abs_x=\"160\" abs_y=\"40\"/>");
//    }
//    long now = System.currentTimeMillis();
//    float nowf = now;
//    Thread.sleep(30);
//    long now2 = System.currentTimeMillis();
//    float nowf2 = now;
//    nowf = 1582804395185L;
//    nowf2 = 1582804395215L;
//
//    System.out.println("Test.main(): now = " + now);
//    System.out.println("Test.main(): nowf = " + nowf);
//    System.out.println("Test.main(): now2 = " + now2);
//    System.out.println("Test.main(): nowf2 = " + nowf2);
//    System.out.println("Test.main(): (now2 - now) = " + (now2 - now));
//    System.out.println("Test.main(): (nowf2 - nowf) = " + (nowf2 - nowf));
//    System.out.println("Test.main(): Float.floatToIntBits(nowf ) = " + Integer.toBinaryString(Float.floatToIntBits(nowf)));
//    System.out.println("Test.main(): Float.floatToIntBits(nowf2) = " + Integer.toBinaryString(Float.floatToIntBits(nowf2)));
//
//    String data = "content://com.android.externalstorage.documents/document/primary:ar_skeleton_points/20191225_101556.txt";
//    URI uri = URI.create(data);
//    System.out.println("Test.main(): uri = " + uri);
//
//    String row = "0,0,-0.019762825,0.74253386,-1.8900167,-0.099893115,0.71932936,-1.9369936,-0.40668377,0.80424345,-2.351348,-0.18093708,0.061816078,-0.9900964,-0.90298384,1.2104932,-1.9350978,0.24751647,1.240751,-3.8709679,0.17303929,0.2232039,-1.4105259,-0.90323913,1.2108353,-1.9356447,-0.16637677,0.106807694,-2.1065397,0.12789042,-0.60849416,-2.1305819,0.036918595,-0.729555,-1.2863394,-0.11953819,0.040874254,-0.6758403,-0.09597163,-0.19979455,-0.561238,-0.113944486,-0.26250893,-0.45347148,-0.038218565,0.009360792,-0.60359436,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.6904297,0.76904297,0.73535156,0.57421875,0.76904297,0.26586914,0.63964844,0.8053247,-0.038505785,0.5915822,0.0,-0.0042439066,0.9974884,0.07070327,0.0,-0.59281886,-0.059449703,0.80313855,0.0,-0.09324136,0.0101516545,-0.17460302,1.0,-0.8120321,-0.92911756,-1.2972839";
//    String[] split = row.split(",");
//    System.out.println("Test.main(): split.length = " + split.length);
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
