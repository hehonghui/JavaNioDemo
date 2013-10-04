package com.nio.buffer;  

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/** 
 * @Title: BufferDemo.java 
 * @Package com.nio.buffer 
 * @Description:  
 * @author Mr.Simple bboyfeiyu@gmail.com 
 * @date Oct 4, 2013 2:55:03 PM 
 * @version V1.0 
 */
public class BufferDemo {
	
	public static void main(String[] args) {
		intBuffer() ;
		byteBuffer() ;
	}
	
	/**
	 * 
	 * @Title: intBuffer 
	 * @Description:  
	 * @return void    
	 * @throws
	 */
	private static void intBuffer() {
		// 分配大小为10的缓冲区
		IntBuffer intBuffer = IntBuffer.allocate(10) ;
		System.out.print("1、写入数据之前的position、limit和capacity：") ;
		System.out.println("position = " + intBuffer.position() 
				+ "，limit = " + intBuffer.limit() 
				+ "，capacty = " + intBuffer.capacity()) ;
		int[] arr = {1,3,5,7,9};
		int temp = 11 ;
		intBuffer.put(temp) ;
		intBuffer.put(arr) ;
		System.out.print("2、写入数据之后的position、limit和capacity：") ;
		System.out.println("position = " + intBuffer.position() 
				+ "，limit = " + intBuffer.limit() 
				+ "，capacty = " + intBuffer.capacity()) ;
		
		// 重置缓冲区, position会重置为0， limit会等于原来position的值
		intBuffer.flip() ;
		
		System.out.print("3、准备输出数据时的position、limit和capacity：") ;
		System.out.println("position = " + intBuffer.position() 
				+ "，limit = " + intBuffer.limit() 
				+ "，capacty = " + intBuffer.capacity()) ;
		System.out.println("缓冲区中的内容：") ;
		while ( intBuffer.hasRemaining() ){
			int buf = intBuffer.get() ;
			System.out.print( buf + ", ");
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @Title: charBuffer 
	 * @Description:  
	 * @return void    
	 * @throws
	 */
	private static void byteBuffer() {
		ByteBuffer buf = ByteBuffer.allocate(10) ;
		byte[] data = {2,5,7,8};
		buf.put( data ) ;
		buf.flip() ;
		
		while (buf.hasRemaining()) {
			System.out.print( buf.get() + ", ");
			
		}
	}

}
  