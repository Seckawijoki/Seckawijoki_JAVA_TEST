package questions;

import java.util.Scanner;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/16 at 19:36.
 */

public class HuYa1 {
  public static void main(String[] args) {
    int i = 0;
    if ( i == 0 ) {
      new HuYa1().test();
      return;
    }
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int m = scanner.nextInt();
    int[][] matrix = new int[n][m];
    for ( int j = 0 ; j < n ; ++j ) {
      for ( int k = 0 ; k < m ; ++k ) {
        matrix[j][k] = scanner.nextInt();
      }
    }
    new HuYa1().ni(matrix);
  }

  private void ni(int[][] matrix) {
    int startX = 0, startY = 0, endX = matrix.length - 1, endY = matrix[0].length - 1;
    boolean[][] output = new boolean[matrix.length][matrix[0].length];
    StringBuilder s = new StringBuilder();
    int count = matrix.length * matrix[0].length;
    int x = startX, y = startY;
    while ( x < endX && y < endY && count > 0) {
      for ( y = startY, x = startX; x <= endX ; ++x, --count ){
        s.append(matrix[x][y]).append(' ');
      System.out.println("questions.HuYa1.ni(): x = " + x + " | y = " + y);
      }
      ++startY;
      for ( --x, y = startY; y < endY ; ++y, --count ){
        s.append(matrix[x][y]).append(' ');
        System.out.println("questions.HuYa1.ni(): x = " + x + " | y = " + y);
      }
      --endX;
      for ( ; x >= startX ; --x, --count ){
        s.append(matrix[x][y]).append(' ');
        System.out.println("questions.HuYa1.ni(): x = " + x + " | y = " + y);
      }
      --endY;
      for ( ++x, y = endY; y >= startY ; --y, --count ){
        s.append(matrix[x][y]).append(' ');
        System.out.println("questions.HuYa1.ni(): x = " + x + " | y = " + y);
      }
      ++startX;
    }
    s.deleteCharAt(s.length() - 1);
    System.out.println(s.toString());
  }

  private void test() {
    int[][] matrix = new int[][]{ { 1, 8, 7 }, { 2, 9, 6 }, { 3, 4, 5 } };
    ni(matrix);
    int[][] matrix2 = new int[][]{ { 1, 8, 7, 6 }, { 2, 3, 4, 5 } };
    ni(matrix2);
  }
}
