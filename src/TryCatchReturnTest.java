import java.util.HashMap;
import java.util.Map;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/5/9 at 18:34.
 */

class TryCatchReturnTest {
  public static void main(String[] args) {
    System.out.println("TryCatchReturnTest.main(): " + test4());
  }

  private static int test() {
    int i = 0;
    try {
      i = 11;
      int a = i / 0;
      return i;
    } catch ( Exception e ) {
      i = 13;
      return i;
    } finally {
      i = 12;
//      System.out.println("TryCatchReturnTest.test(): ");
      return i;
    }

  }

  public static int test4() {
    int b = 20;
    try {
      System.out.println("try block");
      b = b / 0;
      return b += 80;
    } catch ( Exception e ) {
      System.out.println("catch block");
      return b += 15;
    } finally {
      System.out.println("finally block");
      if ( b > 25 ) {
        System.out.println("b>25, b = " + b);
      }
      b += 50;
    }
//    return 204;
  }

  public static Map<String, String> getMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("KEY", "INIT");

    try {
      map.put("KEY", "TRY");
      return map;
    } catch ( Exception e ) {
      map.put("KEY", "CATCH");
    } finally {
      map.put("KEY", "FINALLY");
    }

    return map;
  }

}
