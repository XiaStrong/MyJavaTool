package MyTool;

import java.io.File;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//测试内存输入流
//		UseIO.memoryInputStream("你快乐吗？");
		
		//测试内存输出流
//		String[] strings = new String[]{"你的内心孤单吗？","有些时候"};
//		UseIO.memoryOutputStream(strings);
		
		//测试数据流输入输出
//		File file = new File("DataStream.txt");
//		UseIO.writeDataStream(file);
//		UseIO.readDataStream(file);
		
		//序列化流测试
//		File file = new File("ObjectStream.txt");
//		UseIO.writeObjectToFile(file);
//		UseIO.readObjectFromFile(file);
		
		//字节输出流
		File file = new File("BaseTest.txt");
//		UseIO.printStreamUse(file);
//		UseIO.reSetPrintStream(file);
		UseIO.reSetInStream(file);
	}

}
