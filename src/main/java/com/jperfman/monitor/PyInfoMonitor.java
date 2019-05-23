package com.jperfman.monitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import com.jperfman.exception.MonitorException;

public class PyInfoMonitor extends Thread implements IMonitor {
	private static final int SOCKET_TIMEOUT = 10000;
	private static final int BUFF_SIZE = 4096;
	private static final int CONN_TIMEOUT = 5000;

	private String monitorIP;
	private String monitorPort;
	
	public PyInfoMonitor(String monitorIP, String monitorPort) {
		this.monitorIP = monitorIP;
		this.monitorPort = monitorPort;
	}
	
	@Override
	public String monitor() throws MonitorException {
		// TODO Auto-generated method stub
		String monitorData = null;
		Socket socket = new Socket();
		
		try {
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(SOCKET_TIMEOUT);
			socket.setSendBufferSize(BUFF_SIZE);
			socket.setReceiveBufferSize(BUFF_SIZE);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			socket.connect(
					new InetSocketAddress(monitorIP, Integer
							.parseInt(monitorPort)), CONN_TIMEOUT);
			InputStream ins = socket.getInputStream();
			OutputStream ous = socket.getOutputStream();

			ous.write("1".getBytes());

			StringBuffer sb = new StringBuffer();
			byte[] buff = new byte[1024];
			while (ins.read(buff) > 0) {
				sb.append(new String(buff));
			}
			socket.close();
			ins.close();
			ous.close();
			//bugfix: add trim() to remove null box which appending the json string
			monitorData = sb.toString().trim();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new MonitorException("retrieve monitor info failed!!");
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MonitorException("retrieve monitor info failed!!");
		}
		
		return monitorData;
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return monitorIP;
	}

	@Override
	public String getPort() {
		// TODO Auto-generated method stub
		return monitorPort;
	}
	
	public static void main(String[] args) {
		PyInfoMonitor demo = new PyInfoMonitor("172.18.1.65", "18085");
		try {
			System.out.println(demo.monitor());
		} catch (MonitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
