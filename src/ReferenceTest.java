

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;


public class ReferenceTest {
  public static void main(String[] args) {
//    strongReference();
    softReference();
//    weakReference();
//    weakReference2();
//    phantomReference();
//    new ReferenceQueueTest();
  }

  private static void strongReference() {
    Object o = new Object();
    System.out.println("ReferenceTest.strongReference(): o = " + o);
    o = null;
    System.gc();
    System.out.println("ReferenceTest.strongReference(): o = " + o);
  }

  //-Xms10M -Xmx10M -Xmn5M -XX:+PrintGCDetails
  private static void softReference() {
    SoftReference<byte[]> softReference = new SoftReference(new byte[1024*1024*4]);
    System.out.println("ReferenceTest.softReference(): " + softReference.get().length);
    System.gc();
    System.out.println("ReferenceTest.softReference(): " + softReference.get().length);
  }

  //-Xms10M -Xmx10M -Xmn5M -XX:+PrintGCDetails
  private static void weakReference() {
    ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
    WeakReference<Object> weakReference = new WeakReference(new Object(), referenceQueue);
    System.out.println("ReferenceTest.weakReference(): " + weakReference.get());
    System.gc();
    System.out.println("ReferenceTest.weakReference(): " + weakReference.get());
  }

  private static void weakReference2() {
    Object o = new Object();
    ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
    WeakReference<Object> weakReference = new WeakReference(o, referenceQueue);
    System.out.println("ReferenceTest.weakReference(): " + weakReference.get());
    System.gc();
    System.out.println("ReferenceTest.weakReference(): " + weakReference.get());
  }

  private static void phantomReference() {
    ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
    PhantomReference<Object> phantomReference = new PhantomReference(new Object(), referenceQueue);
    System.out.println("ReferenceTest.phantomReference(): " + phantomReference.get());
    System.gc();
    System.out.println("ReferenceTest.phantomReference(): " + phantomReference.get());
  }

}
