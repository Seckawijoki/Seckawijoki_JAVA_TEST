import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/4/26 at 23:42.
 */

public class ReferenceQueueTest {
  private ReferenceQueueTest() {
    EmployeeCache cache = EmployeeCache.getInstance();
    cache.getEmployee("1");
    cache.getEmployee("2");
    cache.getEmployee("3");
    System.gc();
    cache.getEmployee("1");
    cache.getEmployee("2");
    cache.getEmployee("3");
  }

  static class Employee {
    String id;

    Employee(String _id) {
      this.id = _id;
    }
  }

  static class EmployeeCache {
    static private EmployeeCache cache;
    private HashMap<String, EmployeeReference> mapEmployeeRefs;
    private ReferenceQueue<Employee> q;


    public static EmployeeCache getInstance() {
      if ( cache == null ) {
        cache = new EmployeeCache();
      }
      return cache;
    }

    public Employee getEmployee(String id) {
      Employee e = null;
      // 先从缓存中是查找该Employee实例的软引用，如果有，从软引用中取得。
      if ( mapEmployeeRefs.containsKey(id) ) {
        e = mapEmployeeRefs.get(id).get();
      }

      // 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
      // 并保存对这个新建实例的软引用
      if ( e == null ) {
        System.err.println("EmployeeCache.getEmployee(): null");
        e = new Employee(id);
        System.out.println("重新生成对象" + id);
        cacheEmployee(e);
      } else {
        System.out.println("从软引用中获取对象" + id);
      }
      return e;
    }

    private void cacheEmployee(Employee e) {
      CleanCache();
      EmployeeReference erf = new EmployeeReference(e, q);
      mapEmployeeRefs.put(e.id, erf);
    }

    // 清除那些所软引用的Employee对象已经被回收的EmployeeRef对象
    private void CleanCache() {
      EmployeeReference ref = null;
      while ( ( ref = (EmployeeReference) q.poll() ) != null ) {
        mapEmployeeRefs.remove(ref.key);
      }
    }

    private class EmployeeReference extends SoftReference<Employee> {
      private String key = "";

      private EmployeeReference(Employee e, ReferenceQueue<Employee> q) {
        super(e, q);
        key = e.id;
      }
    }

    private EmployeeCache() {
      mapEmployeeRefs = new HashMap();
      q = new ReferenceQueue();
    }
  }
}