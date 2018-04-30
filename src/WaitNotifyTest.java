/**
 * Created by 瑶琴频曲羽衣魂 on 2018/4/28 at 11:17.
 */

public class WaitNotifyTest {
  private String[] flag = {"true"};

  class NotifyThread extends Thread {
    public NotifyThread(String name) {
      super(name);
    }

    public void run() {
      try {
        sleep(3000);//推迟3秒钟通知
      } catch ( InterruptedException e ) {
        e.printStackTrace();
      }
      synchronized ( flag ) {
        flag[0] = "false";
        System.out.println(getName() + " notifies.");
        flag.notifyAll();
      }
    }
  }
  class WaitThread extends Thread {
    public WaitThread(String name) {
      super(name);
    }

    public void run() {
      synchronized ( flag ) {
        while ( flag[0] != "false" ) {
          long waitTime = System.currentTimeMillis();
          try {
            System.out.println(getName() + " begins waiting!");
            flag.wait();
          } catch ( InterruptedException e ) {
            e.printStackTrace();
          }
          waitTime = System.currentTimeMillis() - waitTime;
          System.out.println("wait time :" + waitTime);
        }
        System.out.println(getName() + " ends waiting!");
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Main Thread Run!");
    WaitNotifyTest test = new WaitNotifyTest();
    NotifyThread notifyThread = test.new NotifyThread("notify01");
    WaitThread waitThread01 = test.new WaitThread("waiter01");
    WaitThread waitThread02 = test.new WaitThread("waiter02");
    WaitThread waitThread03 = test.new WaitThread("waiter03");
    notifyThread.start();
    waitThread01.start();
    waitThread02.start();
    waitThread03.start();
  }
}
