/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/16 at 20:57.
 */

public class SingleInstance {
  private static class SingletonHolder {
    private static final SingleInstance INSTANCE = new SingleInstance();
  }
  public static SingleInstance getInstance() {
    return SingletonHolder.INSTANCE;
  }
  private SingleInstance() {
    synchronized ( SingleInstance.class ) {
      if (SingletonHolder.INSTANCE != null){
        throw new RuntimeException();
      }
    }
  }
}