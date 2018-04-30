package questions;

import java.util.Scanner;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/15 at 19:37.
 */

public class ShenXinFu1 {
  public static void main(String[] args) {
    int i = 0;
    if (i == 0){
       new ShenXinFu1().test();
      return;
    }
    Scanner scanner = new Scanner(System.in);
    String string = scanner.nextLine();
    String pattern = scanner.nextLine();
    if ( new ShenXinFu1().match(string, pattern) == 0 )
      System.out.println("match");
    else
      System.out.println("unmatch");
  }
  private int match(String str, String pattern) {
    pattern =  pattern.replaceAll("\\*", "[A-Z0-9a-z]{1,}")
            .replaceAll("\\?", "[A-Z0-9a-z]");
    return str.matches(pattern) ? 0 : -1;
  }
  private void test(){
    String str = "abc";
    String []pattern = new String[]{"abc", "a*", "a*c", "a*b", "ab?", "a?"};
    for ( int i = 0 ; i < pattern.length ; i++ ) {
      String s = pattern[i]
      .replaceAll("\\*", "[A-Z0-9a-z]{1,}")
      .replaceAll("\\?", "[A-Z0-9a-z]");
      System.out.println("questions.ShenXinFu1.test(): s = " + s);
      System.out.println("questions.ShenXinFu1.test() "+ pattern[i] + "匹配情况：" + str.matches(s));
    }
  }
}
