import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

import constants.ServerPath;
import util.DigitUtil;

/**
 * Created by seckawijoki on 18-5-4 at 上午10:07.
 */

public class Client {
  private final Socket client;
  private Writer writer;
  private Reader reader;
  private InputStream inputStream;
  private OutputStream outputStream;
  private char[] chars;
  private byte[] bytes;
  private Client() throws IOException {
    System.out.println("Client(): ");
    client = new Socket(ServerPath.PATH, ServerPath.PORT);
    outputStream = client.getOutputStream();
    writer = new OutputStreamWriter(outputStream);
//    writer = new BufferedWriter(writer);
    inputStream = client.getInputStream();
    reader = new InputStreamReader(inputStream);
//    reader = new BufferedReader(reader);
//    sendToServer();
//    writeKeyboard();
//    sendIncrementRepeatedly();
    outputStreamWriting();
  }

  private void outputStreamWriting() throws IOException {
    int i = 0;
    byte[] bytes = new byte[4];
    int length;
    while (true){
      outputStream.write(DigitUtil.int2byteArray(++i));
      System.out.println("Client.outputStreamWriting(): i = " + i);
      length = inputStream.read(bytes);
      if (length == -1)break;
      i = DigitUtil.byteArray2Int(bytes);
      System.out.println("Client.outputStreamWriting(): i = " + i);
    }
  }

  private void sendToServer() throws IOException{
    InputStreamReader reader = new InputStreamReader(System.in);
    BufferedReader bufferedReader = new BufferedReader(reader);
    String line;
    while ((line = bufferedReader.readLine()) != null){
      System.out.println("Keyboard: line = " + line);
      writer.write(line);
      writer.flush();
    }
  }

  private void sendIncrementRepeatedly() throws IOException {
    int i = 0;
    while (true){
      writer.write(++i);
      System.out.println("Client.sendIncrementRepeatedly(): writes i = " + i);
      i = reader.read();
      System.out.println("Client.sendIncrementRepeatedly(): reads i = " + i);
      if (i == -1)break;
    }
  }

  private void writeKeyboard() throws IOException {
    InputStreamReader reader = new InputStreamReader(System.in);
    BufferedReader bufferedReader = new BufferedReader(reader);
    String line;
    while (true){
      System.out.print("Please input: ");
      if ((line = bufferedReader.readLine()) != null) {
        System.out.println("Keyboard: line = " + line);
        writer.write(line);
        writer.flush();
      }

    }
  }
  public static void main(String[] args){
    try {
      new Client();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
