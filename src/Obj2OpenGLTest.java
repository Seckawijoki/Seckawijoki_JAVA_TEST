import com.sun.prism.impl.VertexBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Vector;

import org.obj2openjl.v3.Obj2OpenJL;
import org.obj2openjl.v3.model.OpenGLModelData;
import org.obj2openjl.v3.model.RawOpenGLModel;

import sun.rmi.runtime.Log;

/**
 * Created by Administrator on 2019/4/3 at 17:26.
 */

public class Obj2OpenGLTest {
  private static FloatBuffer VertexBuffer;
  private static FloatBuffer NormalBuffer;
  private static FloatBuffer TextureBuffer;
  private static int VertexCount;
  private static final String FORMAT = "%.4f";
  public static void main(String[] args) {
    try {
      File file = new File("luomo02.obj");
//      loadObjFirst(file);
//      outputOpenGLBuffer("first.txt");
//      loadObjSecond(file);
//      outputOpenGLBuffer("second.txt");
      loadObjFromTxt();
      outputOpenGLBuffer("third.txt");
    } catch ( Throwable e ) {
      System.out.println("Obj2OpenGLTest.main(): " + e);
      e.printStackTrace();
    }
  }

  private static void outputOpenGLBuffer(String filename) throws IOException {
    File file = new File(filename);
    FileWriter fileWriter = new FileWriter(file);
    write(fileWriter, VertexBuffer);
    write(fileWriter, NormalBuffer);
    write(fileWriter, TextureBuffer);
  }

  private static void write(FileWriter fileWriter, FloatBuffer floatBuffer) throws IOException {
    int index = 0;
    floatBuffer.position(0);
    while ( floatBuffer.hasRemaining() ) {
      fileWriter.write(String.format(FORMAT, floatBuffer.get()));
      fileWriter.write(++index % 3 != 0 ? " " : "\n");
    }
    fileWriter.write("\n");
  }

  private static void loadObjSecond(File file) throws IOException {
    InputStream is = new FileInputStream(file);
    float[] vertexes;
    float[] normals;
    float[] texCoords;

    Vector<Float> vertexVector = new Vector<>();
    Vector<Float> normalVector = new Vector<>();
    Vector<Float> textureVector = new Vector<>();
    Vector<String> faces = new Vector<>();

    BufferedReader reader = null;
    InputStreamReader isr = new InputStreamReader(is);
    reader = new BufferedReader(isr);

    // read file until EOF
    String line;
    while ( ( line = reader.readLine() ) != null ) {
      line = line.trim();
      String[] parts = line.split("[ ]+");
      if ( parts[0] == null || parts[0].equals("") ) {
        continue;
      }
      float f;
      switch ( parts[0] ) {
        case "v":
          // vertexVector
          vertexVector.add(f = Float.valueOf(parts[1]));
          vertexVector.add(f = Float.valueOf(parts[2]));
          vertexVector.add(f = Float.valueOf(parts[3]));
          vertexVector.add(parts.length >= 5 ? Float.valueOf(parts[4]) : null);
          break;
        case "vt":
          // textureVector
//          System.out.println("read" + Arrays.toString(parts));
          textureVector.add(f = Float.valueOf(parts[1]));
          textureVector.add(f = Float.valueOf(parts[2]));
          textureVector.add(f = Float.valueOf(parts[3]));
          break;
        case "vn":
          // normalVector
          normalVector.add(f = Float.valueOf(parts[1]));
          normalVector.add(f = Float.valueOf(parts[2]));
          normalVector.add(f = Float.valueOf(parts[3]));
          break;
        case "f":
          // faces: vertex/texture/normal
          faces.add(parts[1]);
          faces.add(parts[2]);
          faces.add(parts[3]);
          break;
      }
    }
    if ( reader != null ) {
      reader.close();
    }

    int faceNumber = faces.size();
//    vertexes = new float[vertexVector.size()];
//    normals = new float[normalVector.size()];
//    texCoords = new float[textureVector.size()];
    vertexes = new float[faceNumber*3];
    normals = new float[faceNumber*3];
    texCoords = new float[faceNumber*3];

    System.out.println("Obj2OpenGLTest.loadObjSecond(): vertexVector.size() = " + vertexVector.size());
    System.out.println("Obj2OpenGLTest.loadObjSecond(): textureVector.size() = " + textureVector.size());
    System.out.println("Obj2OpenGLTest.loadObjSecond(): normalVector.size() = " + normalVector.size());
    System.out.println("Obj2OpenGLTest.loadObjSecond(): vertexes.length = " + vertexes.length);
    System.out.println("Obj2OpenGLTest.loadObjSecond(): texCoords.length = " + texCoords.length);
    System.out.println("Obj2OpenGLTest.loadObjSecond(): normals.length = " + normals.length);

    int positionIndex = 0;
    int normalIndex = 0;
    int textureIndex = 0;
    int index = 0;
    for ( String face : faces ) {
      String[] parts = face.split("/");
//      System.out.println("faces = " + Arrays.toString(parts));

      float value;
      try {
        value = vertexVector.get(3 * (Integer.valueOf(parts[0])-1) + index + 1);
      } catch ( Throwable e ){
        value = 0;
      }
      vertexes[positionIndex++] = value;

      try {
        value = textureVector.get(3 * (Integer.valueOf(parts[1])-1) + index + 1);
      } catch ( Throwable e ){
        value = 0;
      }
      texCoords[textureIndex++] = value;

      try {
        value = normalVector.get(3 * (Integer.valueOf(parts[2])-1) + index + 1);
      } catch ( Throwable e ){
        value = 0;
      }
      normals[normalIndex++] = value;


//      vertexes[positionIndex++] = vertexVector.get((3 * Integer.valueOf(parts[0]) + index) % vertexVector.size());
//      texCoords[normalIndex++] = textureVector.get((3 * Integer.valueOf(parts[1]) + index) % textureVector.size());
//      normals[textureIndex++] = normalVector.get((3 * Integer.valueOf(parts[2]) + index) % normalVector.size());
      index = (++index) % 3;
    }
    VertexBuffer = ByteBuffer.allocateDirect(vertexes.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    NormalBuffer = ByteBuffer.allocateDirect(normals.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    TextureBuffer = ByteBuffer.allocateDirect(texCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

    VertexBuffer.put(vertexes);
    NormalBuffer.put(normals);
    TextureBuffer.put(texCoords);
  }

  private static void loadObjFirst(File file) throws FileNotFoundException {
    InputStream it = new FileInputStream(file);
    RawOpenGLModel openGLModel = new Obj2OpenJL().convert(it);
    OpenGLModelData openGLModelData = openGLModel.normalize().center().getDataForGLDrawArrays();

    //float[] appleVertices = Cube.cubeVerts;
    float[] Vertices = openGLModelData.getVertices();
    VertexCount = Vertices.length / 3;
    VertexBuffer = ByteBuffer.allocateDirect(Vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    VertexBuffer.put(Vertices);
    float[] Normals = openGLModelData.getNormals();
    NormalBuffer = ByteBuffer.allocateDirect(Normals.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    NormalBuffer.put(Normals);
    float[] TexCoords = openGLModelData.getTextureCoordinates();
    TextureBuffer = ByteBuffer.allocateDirect(TexCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    TextureBuffer.put(TexCoords);

    VertexBuffer.position(0);//从0开始读
    NormalBuffer.position(0);
    TextureBuffer.position(0);
  }

  private static void loadObjFromTxt(){
    int luomosimpleOBJNumVerts = 3066;
    try {
      VertexBuffer = readFromTxtInputStream(new FileInputStream(new File("vertexes.txt")));
      NormalBuffer = readFromTxtInputStream(new FileInputStream(new File("normals.txt")));
      TextureBuffer = readFromTxtInputStream(new FileInputStream(new File("texcoords.txt")));
    } catch ( IOException e ) {

    }
  }

  private static FloatBuffer readFromTxtInputStream(InputStream inputStream) throws IOException {
    int luomosimpleOBJNumVerts = 3066;
    float[] data = new float[luomosimpleOBJNumVerts * 3];
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String line;
    int i = 0;
    while ( (line = bufferedReader.readLine()) != null ){
      String[] strings = line.split(",");
      System.out.println("Obj2OpenGLTest.readFromTxtInputStream(): strings = " + Arrays.toString(strings));
      if (strings.length >= 1) {
        data[i++] = Float.parseFloat(strings[0]);
      }
      if (strings.length >= 2) {
        data[i++] = Float.parseFloat(strings[1]);
      }
      if (strings.length >= 3) {
        data[i++] = Float.parseFloat(strings[2]);
      }
    }
    FloatBuffer floatBuffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(data);
    floatBuffer.position(0);
    return floatBuffer;
  }
}
