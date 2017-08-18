package MyTool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.sql.PseudoColumnUsage;
import java.util.Scanner;


/**
 * 
 * @author XQ
 *字节流
 *		FileInputStream
 *		FileOutputStream
 *字符流
 *		FileReader
 *		FileWriter
 *
 *缓存字节流
 *		BufferedInputStream  （推荐）
 *		BufferedOutputStream  （推荐）
 *缓冲字符流
 *		BufferedReader（了解）
 *		BufferedWriter（了解）
 *
 *转换流
 *		InputStreamReader
 *		OutputStreamWriter	
 *
 *内存流
 *		ByteArrayInputStream 内存输入流
 *		ByteArrayOutputStream 内存输出流
 *
 *数据处理流
 *		DataOutputStream
 *		DataInputStream
 *
 *序列化流
 *		ObjectOutputStream 
 *		ObjectInputStream
 *
 *字节输出流
 *		PrintStream
 *
 *随机访问文件流
 *		RandomAccessFile
 *
 *方法：
 *readFile文件字节输入流读取文件内容
 *writeFile用字节流向文档中写入数据
 *readFileByChars用字符流读取文档数据并输出
 *writeFileByChars通过字符流向文档中写入数据
 *copyDocument复制文件
 *readFileWithBuffer缓存字节流文件读取
 *writeFileWithBuffer缓存字节流写入
 *copyDocumentWithBuffer缓存字节流进行文件复制
 *copyDocumentByLineWithBuffer通过缓存字符流的按行复制，不推荐使用
 *StringCover对字符串进行编码
 *changeStream转换输入流--》将字节输入流转换成字符输入流
 *copyDocumentWithCoverStream转换流复制文件,将一种编码格式的文件转换为另一种格式编码的文件
 *memoryInputStream内存输入流
 *memoryOutputStream内存输出流
 *writeDataStream数据流写入
 *readDataStream数据流读取
 *writeObjectToFile序列化写入
 *readObjectFromFile序列化读取
 *printStreamUse字节输出流
 *reSetPrintStream重定向输出流
 *reSetInStream重定向输入流
 *useRandomRead随机访问文件流
 */

public class UseIO {

	/**
	 * 使用随机访问文件流 ：向文件写入数据
	 * @param file
	 */
	public static void useRandomWrite(File file) {
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile("raf.txt", "rw");
			randomAccessFile.write("刘老师教育我们：做人要善良！！".getBytes());
			
			randomAccessFile.writeBoolean(true);
			randomAccessFile.writeUTF("不识庐山真面目，只缘身在此山中。");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			closeIO(randomAccessFile);
		}
	}
	
	/**
	 * 使用随机访问文件流：读取文件当中的内容
	 */
	public static void useRandomRead(File file) {
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");//可读可写操作
			System.out.println("文件指针位置："+randomAccessFile.getFilePointer());
			randomAccessFile.seek(10);

			byte[]buf = new byte[128];

			int hasRead = 0;
			while ((hasRead = randomAccessFile.read(buf))!=-1) {
				String msg = new String(buf,0,hasRead);
				System.out.println(msg);
			}
			System.out.println("文件指针位置："+randomAccessFile.getFilePointer());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(randomAccessFile);
		}
	}
	
	/**
	 * 字节输出流
	 * @param file
	 * 将控制台的数据打印到本地
	 */
	public static void printStreamUse(File file) {
		PrintStream printStream = null;
		try {
			printStream = new PrintStream(file);
			printStream.append("追加的内容").append(",\n");
			printStream.println("打印内容");
			printStream.write("写入的内容".getBytes());
			printStream.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	/**
	 * 重定向输入流
	 * @param file
	 */
	public static void reSetInStream(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			System.setIn(fileInputStream);//把标准输入流重定向为文件输出流
			//把原来接受键盘输入重定向为接受文件的内容
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNext()) {
				String next = scanner.next();
				System.out.println(next);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 重定向输出流
	 * @param file
	 */
	public static void reSetPrintStream(File file) {
		PrintStream stream = null;
		try {
			stream =new PrintStream(file);
			//重定向输出流
			System.setOut(stream);
			System.out.println("落霞与孤鹜齐飞，");
			System.out.println("秋水共长天一色。");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 序列化写入
	 * 对象转为字节序列的过程
	 * @param file
	 * 注意：
	 * 		1.先序列后反序列，期间步骤必须一致
	 * 		2.只有实现了Serializable接口的类才能被序列
	 * 		3.对象中被transient修饰的属性不会被序列
	 * 用途：
	 * 		1.把对象转成字节序列，保存到硬盘当中
	 * 		2.传递字节序列
	 */
	public static void writeObjectToFile(File file) {
		ObjectOutputStream objectOutputStream = null;
		PersonUseObjectStream p = new PersonUseObjectStream("呼啦啦", 10, 9000);
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
			objectOutputStream.writeObject(p);
			objectOutputStream.writeObject("单身狗敲代码");
			objectOutputStream.writeInt(27);
			objectOutputStream.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeIO(objectOutputStream);
		}
	}
	
	/**
	 * 序列化读取
	 * 字节序列恢复为对象
	 * @param file
	 */
	public static void readObjectFromFile(File file) {
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(file));
			Object a = objectInputStream.readObject();
			Object b = objectInputStream.readObject();
			int readInt = objectInputStream.readInt(); 
			System.out.println(a.toString());
			System.out.println(b.toString());
			System.out.println(readInt);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeIO(objectInputStream);
		}
		
	}
	
	
	/**
	 * 数据流写入，需配合数据流读取使用
	 * 作用：可以作为文件加密
	 * 特点： 既能够保存数据本身，又能够保存数据类型（基本数据类型+String）
	 * 注意：读取顺序必须和存入顺序一致
	 * @param file
	 */
	public static void writeDataStream(File file) {
		DataOutputStream dataOutputStream = null;
		try {
			//写入一个文件到数据流中
			dataOutputStream = new DataOutputStream(new FileOutputStream(file));
			//将数字写入数据流中
			dataOutputStream.writeInt(12345);
			//将字符串写入数据流中
			dataOutputStream.writeUTF("上山打老虎");
			dataOutputStream.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
		} finally {
			closeIO(dataOutputStream);
		}
		
	}
	/**
	 * 数据流读取
	 * @param file
	 */
	public static void readDataStream(File file) {
		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(new FileInputStream(file));
			int readInt = dataInputStream.readInt();
			String readString = dataInputStream.readUTF();
			System.out.println(""+readInt+"\n"+readString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件字节输入流读取文件内容的步骤：
	 * 1.创建流对象
	 * 2.创建一个缓存字节的容器数组
	 * 3.定义一个变量，保存实际读取的字节数
	 * 4.循环读取数据
	 * 5.操作保存数据的数组
	 * 6.关闭流
	 * */

	public static void readFile(File file) {
		//创建一个流对象
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			//创建一个缓存字节的容器数组
			byte[] data = new byte[1024];
			//定义一个变量，保存实际读取的字节数
			int hasRead = 0;
			//循环读取数据
			while ((hasRead = fileInputStream.read(data))!=-1) {
				//操作保存数据的数组
				String string = new String(data, 0, hasRead);
				System.out.println(string);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(fileInputStream);
		}
	} 

	/**
	 *向文件当中写入数据的步骤
	 * 1.选择流：创建流对象
	 * 2.准备数据源，把数据源转换成字节数组类型
	 * 3.通过流向文件当中写入数据
	 * 4.刷新流
	 * 5.关闭流
	 * */
	
	/**
	 * 用字节流向文档中写入数据
	 * @param file  将要写入的地址
	 * @param string  将要写入的数据
	 * @param whether  是否覆盖
	 */
	public static void writeFile(File file, String string, Boolean whether) {
		//创建字节流对象
		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(file, whether);
			//获取数据，并将其转化为字节
			byte[] data = string.getBytes();
			//通过流向文件中写入数据
			fileOutputStream.write(data, 0, data.length);
			//刷新流
			fileOutputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(fileOutputStream);
		}

	}

	/**
	 * 用字符流读取文档数据并输出
	 * @param file
	 */
	public static void readFileByChars(File file) {
		FileReader fileReader = null;
		
		try {
			fileReader = new FileReader(file);
			char[] data = new char[1024];
			int hasRead = 0;
			while ((hasRead = fileReader.read(data))!=-1) {
				String string = new String(data, 0, hasRead);
				System.out.println(string);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(fileReader);
		}
	}
	
	/**
	 * 通过字符流向文档中写入数据
	 * @param file
	 * @param string
	 * @param whether
	 */
	public static void writeFileByChars(File file,String string,boolean whether) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file, whether);
			//使用字符输出流写入到文档中，与字节流不同的是，此处能直接写，而不用转化为byte数组
			fileWriter.write(string);
			//刷新流
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(fileWriter);
		}
	}
	
	/**
	 * 复制文件
	 * @param fromFile 传入的文件地址
	 * @param toFile 复制到何处
	 */
	public static void copyDocument(File fromFile,File toFile) {
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		
		try {
			fileInputStream = new FileInputStream(fromFile);
			fileOutputStream = new FileOutputStream(toFile);
			
			byte[]data = new byte[1024];
			int hasRead = 0;
			while ((hasRead = fileInputStream.read(data))!=-1) {
				fileOutputStream.write(data, 0, hasRead);
			}
			fileOutputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(new Closeable[] {fileInputStream,fileOutputStream});
		}
	}
	
	/**
	 * 缓存字节流文件读取
	 * @param file
	 */
	public static void readFileWithBuffer(File file) {
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] data = new byte[1024];
			int hasRead = 0;
			while ((hasRead = bufferedInputStream.read(data))!=-1) {
				String string = new String(data, 0, hasRead);
				System.out.println(string);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(bufferedInputStream);
		}
	}
	
	/**
	 * 缓存字节流写入
	 * @param file 地址
	 * @param string 写入的数据
	 * @param whether 是否覆盖
	 */
	public static void writeFileWithBuffer(File file,String string,boolean whether) {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file,whether));
			byte[]data = string.getBytes();
			bufferedOutputStream.write(data);
			bufferedOutputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 缓存字节流进行文件复制
	 * @param fromFile 需要复制的文件
	 * @param toFile 复制到的文件
	 */
	public static void copyDocumentWithBuffer(File fromFile,File toFile) {
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;

		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(fromFile));
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(toFile));
			byte[]data = new byte[1024];
			int hasRead = 0;
			while ((hasRead = bufferedInputStream.read(data))!=-1) {
				bufferedOutputStream.write(data, 0, hasRead);
			}
			
			bufferedOutputStream.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(new Closeable[] {bufferedInputStream,bufferedOutputStream});
		}
	}
	
	/**
	 * 通过缓存字符流的按行复制，不推荐使用
	 * @param fromFile
	 * @param toFile
	 */
	public static void copyDocumentByLineWithBuffer(File fromFile,File toFile) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(fromFile));
			bufferedWriter = new BufferedWriter(new FileWriter(toFile));
			
			String line = "";
			while (true) {
				line = bufferedReader.readLine();
				if (line==null) {
					break;
				}
				bufferedWriter.write(line);
				bufferedWriter.newLine();//换行
			}
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(new Closeable[] {bufferedReader,bufferedWriter});
		}
	}
	
	/**
	 * 对字符串进行编码
	 * @param string 需要进行编码的字符串
	 * @param charsetName 需要转换成那种编码
	 * @return
	 */
	public static String StringCover(String string,String charsetName) {
		//使用默认格式进行编码解码,用于展示学习
		String resultStr = null;
		if (charsetName == null) {
			//字符串编码的过程，使用编译器默认的格式进行编码
			byte[] data = string.getBytes();
			//解码
			resultStr = new String(data, 0, data.length);
		
		}else {
			//使用特定格式进行编码
			try {
				byte[] data = string.getBytes(charsetName);
				//得到编码后的字符串（解码）
				resultStr = new String(data, 0, data.length);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return resultStr;
	}
	
	/**
	 * 转换输入流--》将字节输入流转换成字符输入流
	 * 作用：为了防止文件使用字符输入流处理时出现乱码问题
	 * @param file
	 */
	public static void changeStream(File file) {
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = "";
			while ((line=bufferedReader.readLine())!=null) {
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(new Closeable[] {fileInputStream,inputStreamReader,bufferedReader});
		}
		
	}
	
	/**
	 * 转换流复制文件,将一种编码格式的文件转换为另一种格式编码的文件
	 * @param fromFile 转那个位置
	 * @param toFile 转到哪个位置
	 * @param charsetName 转为什么格式
	 */
	public static void copyDocumentWithCoverStream(File fromFile,File toFile,String charsetName) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fromFile),charsetName));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toFile),charsetName));
			String line = "";
			while ((line = bufferedReader.readLine())!=null) {
				bufferedWriter.write(line);
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 内存输入流，感觉没卵用
	 * @param string
	 * 内存输入流的操作和文件输入流的操作是完全相同
	 * 唯一不同的是内存输入流操作的是字节数据，而文件输入流操作的是文件
	 */
	public static void memoryInputStream(String string) {
		byte[] msgdata = string.getBytes();//将要操作的字符串转化为byte数据
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(msgdata);

		byte[] data = new byte[128];
		
		int hasRead = 0;
		try {
			while ((hasRead = byteArrayInputStream.read(data))!=-1) {
				String resultStr = new  String(data, 0, hasRead);
				System.out.println(resultStr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 内存输出流
	 * 作用：在循环当中把所有的数据存放到统一的容器中，然后再循环结束之后把容器中所有的数据一次性取出
	 * 注意：数据量不要过大，过大造成内存溢出
	 * @param strings
	 */
	public static void memoryOutputStream(String[] strings) {
		//此处以字符串作例，若是文件按照注释中的处理
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		for(String s:strings) {
			try {
				byteArrayOutputStream.write(s.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		byte[]data = byteArrayOutputStream.toByteArray();
		System.out.println(new String(data, 0, data.length));
		
		
		//直接打开注释即可，文件处理实例
//		File file = new File("src\\MyTool\\UseDate.java");
//		FileInputStream fileInputStream = null;
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		try {
//			fileInputStream = new FileInputStream(file);
//			byte[] fileData = new byte[512];
//			int hasRead = 0;
//			while ( (hasRead = fileInputStream.read(fileData))!=-1) {
//				byteArrayOutputStream.write(fileData, 0, hasRead);
//			}
//			byte[] outData = byteArrayOutputStream.toByteArray();
//			byteArrayOutputStream.flush();
//			System.out.println(new String(outData));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	//关闭相关流
	private static void closeIO(Closeable...IOs) {
		for (Closeable closeable : IOs) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
