import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/4/2 at 20:41.
 */

public class SynchronizedThread3 {
  class Bank {
    private AtomicInteger account = new AtomicInteger(100);

    public AtomicInteger getAccount() {
      return account;
    }

    public void save(int money) {
      account.addAndGet(money);
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
         bank.save(1);
//        System.out.println("NewThread.run(): bank.getAccount() = " + bank.getAccount());
        queue.add(bank.getAccount().get());
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
  private Queue<Integer> queue = new LinkedList();

  public static void main(String[] args) {
    final SynchronizedThread3 st = new SynchronizedThread3();
    st.useThread();
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(5000);
        } catch ( InterruptedException e ) {
          e.printStackTrace();
        }
        boolean flag = false;
        for ( int i = 1 ; !st.queue.isEmpty() ; ++i ) {
          if ( st.queue.poll() != i ) {
            flag = true;
            System.out.println("SynchronizedThread.main(): i = " + i);
            System.out.println("SynchronizedThread.main(): false");
            break;
          }
        }
        if ( !flag )
          System.out.println("SynchronizedThread.main(): true");
        else
          System.out.println("SynchronizedThread.main(): st.queue = " + st.queue);
      }
    }).start();
  }

}
