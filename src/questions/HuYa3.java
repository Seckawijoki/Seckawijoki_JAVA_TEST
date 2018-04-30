package questions; /**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/16 at 20:30.
 */

import java.util.Scanner;

/**
ABDEC DBEAC

 */
public class HuYa3 {
  private static final HuYa3 ourInstance = new HuYa3();
  private static StringBuilder mString = new StringBuilder();
  static HuYa3 getInstance() {
    return ourInstance;
  }
  private HuYa3() {
  }
  public static void main(String[] args) {
    String preOrder;
    String inOrder;
    int i = 1;
    if (i == 0){
      preOrder = "ABDEC";
      inOrder = "DBEAC";
    } else {
      Scanner scanner = new Scanner(System.in);
      preOrder = scanner.next();
      inOrder = scanner.next();
    }
    char[] po = preOrder.toCharArray();
    char[] io = inOrder.toCharArray();
    postOrder(po, 0, po.length-1, io, 0, io.length-1);
    System.out.println(mString);
  }
  private static void postOrder(char[] preOrder, int preStart, int preEnd,
                                char[] inOrder, int inStart, int inEnd){
    if (inStart >= inEnd){
      mString.append(inOrder[inStart]);
      return;
    }
    int rootInInOrderPos = -1;
    for ( int i = 0 ; i < inOrder.length ; i++ ) {
      if (inOrder[i] == preOrder[preStart]){
        rootInInOrderPos = i;
        break;
      }
    }
//    System.out.println("questions.HuYa3.postOrder(): rootInInOrderPos = " + rootInInOrderPos);
    if (rootInInOrderPos < 0)return;
    postOrder(preOrder, preStart+1, preEnd, inOrder, inStart, rootInInOrderPos-1);
    postOrder(preOrder, preStart+1, preEnd, inOrder, rootInInOrderPos+1, inEnd);
    mString.append(inOrder[rootInInOrderPos]);
  }
}
