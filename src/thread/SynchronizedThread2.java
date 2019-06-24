package thread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/4/2 at 20:41.
 */

public class SynchronizedThread2 {
  static class Bank{
    //使用ThreadLocal类管理共享变量account
    private static ThreadLocal<Integer> account = new ThreadLocal<Integer>(){
      @Override
      protected Integer initialValue(){
        return 0;
      }
    };
    public void save(int money){
      account.set(account.get()+money);
    }
    public int getAccount(){
      return account.get();
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
        System.out.println(Thread.currentThread().getName()+ " | " + bank.getAccount());
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
  private Queue<Integer> queue = new LinkedList();

  public static void main(String[] args) {
    final SynchronizedThread2 st = new SynchronizedThread2();
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
            System.out.println("thread.SynchronizedThread.main(): i = " + i);
            System.out.println("thread.SynchronizedThread.main(): false");
            break;
          }
        }
        if ( !flag )
          System.out.println("thread.SynchronizedThread.main(): true");
        else
          System.out.println("thread.SynchronizedThread.main(): st.queue = " + st.queue);
      }
    }).start();
  }

}
