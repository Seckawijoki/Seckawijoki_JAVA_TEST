import java.lang.reflect.Constructor;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/16 at 20:57.
 */

class SingletonBreaker {
  public static void main(String[] args) {
    String rootDir="E:/Intellij_Commercial_Project/JavaTest/out/production/JavaTest";
    //创建自定义文件类加载器
    FileClassLoader loader1 = new FileClassLoader(rootDir);
    FileClassLoader loader2 = new FileClassLoader(rootDir);
    try {
//      Class<?> object1=loader1.findClass("SingleInstance");
//      Class<?> object2=loader2.findClass("SingleInstance");
      Class<?> object1=loader1.loadClass("SingleInstance");
      Class<?> object2=loader2.loadClass("SingleInstance");
//      System.out.println("SingletonBreaker.main(): object1.hashCode() = " + object1.hashCode());
//      System.out.println("SingletonBreaker.main(): object2.hashCode() = " + object2.hashCode());
      Constructor constructor = SingleInstance.class.getDeclaredConstructor(null);
      constructor.setAccessible(true);
      SingleInstance singleInstance1 = (SingleInstance) constructor.newInstance();
      SingleInstance singleInstance2 = SingleInstance.getInstance();
      System.out.println("SingletonBreaker.main(): " + ( singleInstance1 == singleInstance2 ));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
