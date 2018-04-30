import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/4/2 at 20:41.
 */

public class SynchronizedThread {
  class Bank {

    //    private int account = 0;
    private volatile int account = 0;
    public int getAccount() {
      return account;
    }
    public void save0(int money) {
      account += money;
    }
    /**
     * 用同步方法实现
     * @param money
     */
    public synchronized void save(int money) {
      account += money;
    }

    /**
     * 用同步代码块实现
     *
     * @param money
     */
    public void save1(int money) {
      synchronized (this) {
        account += money;
      }
    }
  }

  class NewThread implements Runnable {
    private Bank bank;
    public NewThread(Bank bank) {
      this.bank = bank;
    }
    @Override
    public void run() {
      for (int i = 0; i < 100; i++) {
//         bank.save1(10);
         bank.save(10);
//        bank.save0(1);
        queue.add(bank.getAccount());
      }
    }


  }

  /**
   * 建立线程，调用内部类
   */
  public void useThread() {
    Bank bank = new Bank();
    NewThread new_thread = new NewThread(bank);
    Thread []threads = new Thread[50];
    for ( int i = 0 ; i < threads.length ; i++ ) {
      threads[i] = new Thread(new_thread);
      threads[i].start();
    }
  }
  private Queue<Integer> queue = new LinkedList<>();

  public static void main(String[] args) {
    SynchronizedThread st = new SynchronizedThread();
    st.useThread();
    new Thread(()->{
      try {
        Thread.sleep(5000);
      } catch ( InterruptedException e ) {
        e.printStackTrace();
      }
      boolean flag = false;
      for ( int i = 1 ; !st.queue.isEmpty() ; ++i ) {
        if (st.queue.poll() != i){
          flag = true;
          System.out.println("SynchronizedThread.main(): i = " + i);
          System.out.println("SynchronizedThread.main(): false");
          break;
        }
      }
      if (!flag)
      System.out.println("SynchronizedThread.main(): true");
      else
        System.out.println("SynchronizedThread.main(): st.queue = " + st.queue);
    }).start();
  }

}
