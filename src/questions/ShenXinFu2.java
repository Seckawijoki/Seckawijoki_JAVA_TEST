package questions;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/15 at 20:13.
 */

public class ShenXinFu2 {
  private static final ShenXinFu2 INSTANCE = new ShenXinFu2();
  private int mSize;
  private Stack<String> stringStack = new Stack<>();
  public static void main(String[] args) {
    int i = 1;
    if ( i == 0 ) {
      INSTANCE.test();
      return;
    }
    Scanner scanner = new Scanner(System.in);
    String str = scanner.nextLine();
    char[] c = str.toCharArray();
    Stack<Character> stack = new Stack<>();
    for ( int j = 0 ; j < c.length ; j++ ) {
      stack.add(c[c.length - j - 1]);
    }
    INSTANCE.mSize = c.length;
    INSTANCE.allPopSeq(stack, new Stack<>(), new Stack<>());
  }

  private void printStack(Stack<Character> stack) {
    for ( Character next : stack ) {
      System.out.print(next);
    }
    System.out.print('\n');
  }

  private void allPopSeq(Stack<Character> inStack,
                         Stack<Character> stack,
                         Stack<Character> outStack) {
    if ( inStack.size() == 0 && stack.size() == 0 && outStack.size() == mSize ) {
      printStack(outStack);
      return;
    }
//    System.out.println("questions.ShenXinFu2.allPopSeq(): " + inStack + " | "  + stack + " | "  + outStack );
    if ( !inStack.empty() ) {//入栈
      char v = inStack.peek();
      stack.push(v);
      inStack.pop();
      allPopSeq(inStack, stack, outStack);
      stack.pop();
      inStack.push(v);//回溯恢复
    }
    if ( !stack.empty() ) {//出栈
      char v = stack.peek();
      stack.pop();
      outStack.push(v);
      allPopSeq(inStack, stack, outStack);
      outStack.pop();
      stack.push(v);//回溯恢复
    }
  }
  private void test() {
    Stack<Character> stack = new Stack<>();
    stack.add('c');
    stack.add('b');
    stack.add('a');
    INSTANCE.mSize = stack.size();
    System.out.println("questions.ShenXinFu2.test(): stack = " + stack);
    INSTANCE.allPopSeq(stack, new Stack<>(), new Stack<Character>());
  }

}
