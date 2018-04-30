package questions;

import java.util.Scanner;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/16 at 20:21.
 */

/**
 2 1 2 2 3
 */
public class HuYa2 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[]p = new int[n];
    int[]q =  new int[n];
    for ( int i = 0 ; i < p.length ; i++ ) {
      p[i] = scanner.nextInt();
    }
    for ( int i = 0 ; i < q.length ; i++ ) {
      q[i] = scanner.nextInt();
    }
    int sumP = 0;
    int sumQ = 1;
    for ( int i = 0 ; i < q.length ; i++ ) {
      sumQ *= q[i];
    }
    for ( int i = 0 ; i < p.length ; i++ ) {
      int item = p[i];
      for ( int j = 0 ; j < p.length ; ++j ) {
        if (i == j)continue;
        item *= q[j];
      }
      sumP += item;
    }
    int yue = yue(sumP, sumQ);
    System.out.println((sumP/yue) + " " + (sumQ/yue));
  }
  private static int yue(int x, int y){
    if (y == 0)return x;
    return yue(y, x%y);
  }
}
