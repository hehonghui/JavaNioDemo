package com.nio.channel;  

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/** 
 * @Title: FileChannelDemo.java 
 * @Package com.nio.channel 
 * @Description:  FileChannel的使用，读、写双向，操作缓冲区Buffer
 * @author Mr.Simple bboyfeiyu@gmail.com 
 * @date Oct 4, 2013 3:09:11 PM 
 * @version V1.0 
 */
public class FileChannelDemo {

	/**
	 * 
	 * @Title: main 
	 * @Description: 
	 * @param args 
	 * @return void    
	 * @throws
	 */
	public static void main(String[] args) throws Exception{
		File file = new File("d:" + File.separator + "channel.txt");
		FileOutputStream fileOutputStream  = new FileOutputStream(file, true) ;
		// 获取文件通道
		FileChannel fileChannel = fileOutputStream.getChannel() ;
		String content = "Java NIO Demo.";
		// 构建缓冲区，Channel操作缓冲区
		ByteBuffer dataBuffer = ByteBuffer.allocate(content.getBytes().length) ;
		// 将内容放到缓冲区
		dataBuffer.put(content.getBytes()) ;
		// 重置缓冲区，使得Position回到0的位置，以读取所有数据
		dataBuffer.flip() ;
		// 将缓冲区的内容通过通道写入到文件中
		fileChannel.write(dataBuffer) ;

		// 输入流
		FileInputStream fis = new FileInputStream(file) ;
		fileChannel = fis.getChannel() ;
		ByteBuffer inputBuffer = ByteBuffer.allocate(fis.available()) ;
		byte[] data = new byte[fis.available()];
		fis.read(data) ;
		inputBuffer.put( data ) ;
		inputBuffer.flip() ;
		System.out.println("The Content is : " 
						+ new String(inputBuffer.array()));
	
		// 关闭通道和流
		fileChannel.close();
		fileOutputStream.close();
		fis.close() ;
	}
	
}
  