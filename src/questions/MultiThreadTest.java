package questions;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/4/26 at 22:50.
 */

public class MultiThreadTest {
  private static long CURRENT_TIME = 0;
  public static void main(String[] args) {
    final Traveler traveler = new Traveler();
    Runnable r1 = new Runnable() {
      @Override
      public void run() {
        traveler.increase();
        CURRENT_TIME = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " | " + traveler.getCount());
      }
    };
    long start = System.currentTimeMillis();
    for ( int i = 0 ; i < 20 ; ++i ) {
      new Thread(r1).start();
    }
    System.out.println("MultiThreadTest.main(): " + (CURRENT_TIME-start));
  }
  private static class Traveler{
    private volatile static int count = 0;
    private synchronized static void increase(){
        count++;
    }
    private int getCount() {
      return count;
    }
  }
}
