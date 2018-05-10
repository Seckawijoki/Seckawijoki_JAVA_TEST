import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.net.Socket;

import constants.ServerPath;
import util.DigitUtil;

/**
 * Created by seckawijoki on 18-5-4 at 上午9:55.
 */

public class Server {
  private Socket socket;
  private ServerSocket serverSocket;
  private Writer writer;
  private Reader reader;
  private InputStream inputStream;
  private OutputStream outputStream;
  private char[] chars = new char[8];
  private byte[] bytes = new byte[8];

  private Server() throws IOException {
    System.out.println("Server(): ");
    serverSocket = new ServerSocket(ServerPath.PORT);
    socket = serverSocket.accept();
    inputStream = socket.getInputStream();
    reader = new InputStreamReader(inputStream);
    inputStreamReading();
//    readIncrementRepeatedly();
  }

  private void inputStreamReading() throws IOException {
    byte []bytes = new byte[4];
    int length;
    while (true){
      length = inputStream.read(bytes);
      if (length == -1)break;
      int i = DigitUtil.byteArray2Int(bytes);
      System.out.println("Server.inputStreamReading(): i = " + i);
      outputStream.write(DigitUtil.int2byteArray(++i));
      System.out.println("Server.inputStreamReading(): i = " + i);
    }
  }

  private void readIncrementRepeatedly() throws IOException {
    int i;
    while (true) {
      i = reader.read();
      System.out.println("Server.sendIncrementRepeatedly(): reads i = " + i);
      if (i == -1) break;
      writer.write(++i);
      System.out.println("Server.sendIncrementRepeatedly(): writes i = " + i);
    }
  }

  private void readClient() throws IOException {
//    outputStream = socket.getOutputStream();
//    writer = new OutputStreamWriter(outputStream);
//    writer = new BufferedWriter(writer);
//    PrintStream printStream = new PrintStream(outputStream, true);
    BufferedReader bufferedReader;
    String line;
    int length;
    while (true) {
//      bufferedReader = new BufferedReader(reader);
      if ((length = reader.read(chars)) != -1) {
        System.out.println("Server.readClient(): " + new String(chars));
        System.out.println("Server.readClient(): length = " + length);
        
      }
      /*
      if ((line = bufferedReader.readLine()) != null) {
        System.out.println("readClient(): line = " + line);
//        writer.write(line);
      }
      */
    }
  }

  public static void main(String[] args) {
    Server server = null;
    try {
      server = new Server();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (server == null) return;
      try {
        if (server.inputStream != null) {
          server.inputStream.close();
        }
        if (server.outputStream != null) {
          server.outputStream.close();
        }
        if (server.reader != null) {
          server.reader.close();
        }
        if (server.writer != null) {
          server.writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
