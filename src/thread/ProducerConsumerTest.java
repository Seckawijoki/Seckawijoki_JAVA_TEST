package thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/4/28 at 10:31.
 */

public class ProducerConsumerTest {
  private static final int PRODUCER_SLEEP_TIME = 2777;
  private static final int CONSUMER_SLEEP_TIME = 8888;
  public static void main(String[] args) {
    Queue<Integer> queue = new LinkedList();
    ProducerConsumerTest test = new ProducerConsumerTest();
    final int size = 10;
    Producer producer = test.new Producer(queue, size, "Producer");
    Consumer consumer1 = test.new Consumer(queue, size, "Consumer1");
    Consumer consumer2 = test.new Consumer(queue, size, "Consumer2");
    Consumer consumer3 = test.new Consumer(queue, size, "Consumer3");
    producer.start();
    consumer1.start();
    consumer2.start();
    consumer3.start();
  }
  /**
   * Producer Thread will keep producing values for Consumer
   * to consumer. It will use wait() method when Queue is full
   * and use notify() method to send notification to Consumer
   * Thread.
   * @author WINDOWS 8
   * */
  class Producer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;
    public Producer(Queue<Integer> queue, int maxSize, String name) {
      super(name);
      this.queue = queue;
      this.maxSize = maxSize;
    }
    @Override public void run() {
      while (true) {
        synchronized (queue) {
          while (queue.size() >= maxSize) {
            try {
              System.out .println("###" + getName() + "'s Queue is full.### Queue contains " + queue);
              queue.wait();
            } catch (Exception ex) {
              ex.printStackTrace();
            }
          }
          Random random = new Random();
          int i = random.nextInt(100);
          queue.add(i);
          System.out.println(getName() + " produced value : " + i + ". Queue contains  " + queue);
          queue.notifyAll();
        }
        try {
          Thread.sleep(PRODUCER_SLEEP_TIME);
        } catch ( InterruptedException e ) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   * Consumer Thread will consumer values form shared queue.
   * It will also use wait() method to wait if queue is
   * empty. It will also use notify method to send * notification to producer thread after consuming values
   * from queue.
   * @author WINDOWS 8
   **/
  class Consumer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;
    public Consumer(Queue<Integer> queue, int maxSize, String name){
      super(name);
      this.queue = queue;
      this.maxSize = maxSize;
    }
    @Override
    public void run() {
      while (true) {
        try {
          Thread.sleep(CONSUMER_SLEEP_TIME);
        } catch ( InterruptedException e ) {
          e.printStackTrace();
        }
        synchronized (queue) {
          while (queue.isEmpty()) {
            System.out.println("Queue is empty," + getName() + " is waiting" + " for producer thread to put something in queue");
            try {
              queue.wait();
            } catch (Exception ex) {
              ex.printStackTrace();
            }
          }
          System.out.println(getName() + " consumed value : " + queue.remove() + ". Queue contains " + queue);
          queue.notifyAll();
        }
      }
    }
  }
}
