package MyTool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

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
 *
 *
 *
 *
 *
 *readFile文件字节输入流读取文件内容
 *writeFile用字节流向文档中写入数据
 *readFileByChars用字符流读取文档数据并输出
 *writeFileByChars通过字符流向文档中写入数据
 *copyDocument复制文件
 *readFileWithBuffer
 *writeFileWithBuffer
 *copyDocumentWithBuffer
 *copyDocumentByLineWithBuffer
 *StringCover
 *changeStream
 *copyDocumentWithCoverStream
 */

public class UseIO {

	
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
	
	
	public static void memoryInputStream() {
		
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
