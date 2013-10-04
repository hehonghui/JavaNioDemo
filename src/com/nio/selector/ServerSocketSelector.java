package com.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @Title: FileLockDemo.java
 * @Package com.nio.filelock
 * @Description: 使用nio中的selector构建一个非阻塞的网络服务，用于构建高性能服务器，
 *               避免由于IO操作阻塞而导致的资源浪费,比如ServerSocket的accept()阻塞等,客户端编程 方式不变.
 *               使用ServerSocketChannel和SocketChannel实现.
 * @author Mr.Simple bboyfeiyu@gmail.com
 * @date Oct 4, 2013 3:23:21 PM
 * @version V1.0
 */
public class ServerSocketSelector {

	public static void main(String[] args) {
		try {
			startServerSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: startServerSocket
	 * @Description: 服务器绑定非阻塞serversocket的监听
	 * @return void
	 * @throws
	 */
	private static void startServerSocket() throws IOException {
		int[] ports = { 8000, 8001, 8002, 8003, 8004, 8005, 8006 };
		// 获得Selector
		Selector socketSelector = Selector.open();

		// 监听ports中的各个端口
		for (int i = 0; i < ports.length; i++) {
			// 打开服务器socket通道
			ServerSocketChannel serSocketChnl = ServerSocketChannel.open();
			// 配置为非阻塞模式
			serSocketChnl.configureBlocking(false);
			// 获取服务器socket
			ServerSocket serverSocket = serSocketChnl.socket();
			// 绑定端口
			InetSocketAddress address = new InetSocketAddress(ports[i]);
			// 绑定到服务器socket
			serverSocket.bind(address);
			// 注册selector
			serSocketChnl.register(socketSelector, SelectionKey.OP_ACCEPT);
			System.out.println("服务器正在监听 " + ports[i] + " 端口...");
		}
		// 处理请求
		dispatch(socketSelector);
	}

	/**
	 * 
	 * @Title: dispatch
	 * @Description:
	 * @param selector
	 * @return void
	 * @throws
	 */
	private static void dispatch(Selector selector) throws IOException {
		while (selector.select() > 0) {
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			while (iter.hasNext()) {
				SelectionKey item = iter.next();
				// 可接收状态
				if (item.isAcceptable()) {
					ServerSocketChannel channel = (ServerSocketChannel) item
							.channel();
					// 接收新连接
					SocketChannel client = channel.accept();
					client.configureBlocking(false);

					ByteBuffer data = ByteBuffer.allocateDirect(1024);
					data.put(("服务器时间为 : " + new Date()).getBytes());
					data.flip();
					client.write(data);
					client.close();
				}
			}
			selectionKeys.clear();
		}
	}
}
