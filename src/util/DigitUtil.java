package util;

import java.nio.ByteBuffer;

/**
 * Created by seckawijoki on 18-5-4 at 下午5:34.
 */

public class DigitUtil {
  private DigitUtil(){

  }
  public static int byteArray2Int(byte[] b) {
    if (b == null || b.length < 4) return -1;
    return ByteBuffer.wrap(b).getInt();
//    return (b[0] << 24) | (b[1] << 16) | (b[2] << 8) | b[3];
  }
  public static byte[] int2byteArray(int i){
    byte[] bytes = new byte[4];
    return ByteBuffer.allocate(4).putInt(i).array();
  }
}
