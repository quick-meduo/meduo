package org.quick.meduo.tools.socket;

import java.nio.ByteBuffer;

import org.quick.meduo.tools.core.date.DateUtil;
import org.quick.meduo.tools.core.io.BufferUtil;
import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.tools.core.util.StrUtil;
import org.quick.meduo.tools.log.StaticLog;
import org.quick.meduo.tools.socket.aio.AioServer;
import org.quick.meduo.tools.socket.aio.AioSession;
import org.quick.meduo.tools.socket.aio.SimpleIoAction;

public class AioServerTest {
	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
        AioServer aioServer = new AioServer(8899);
		aioServer.setIoAction(new SimpleIoAction() {
			
			@Override
			public void accept(AioSession session) {
				StaticLog.debug("【客户端】：{} 连接。", session.getRemoteAddress());
				session.write(BufferUtil.createUtf8("=== Welcome to Hutool socket server. ==="));
			}
			
			@Override
			public void doAction(AioSession session, ByteBuffer data) {
				Console.log(data);
				
				if(false == data.hasRemaining()) {
					StringBuilder response = StrUtil.builder()//
							.append("HTTP/1.1 200 OK\r\n")//
							.append("Date: ").append(DateUtil.formatHttpDate(DateUtil.date())).append("\r\n")//
							.append("Content-Type: text/html; charset=UTF-8\r\n")//
							.append("\r\n")
							.append("Hello Hutool socket");//
					session.writeAndClose(BufferUtil.createUtf8(response));
				}else {
					session.read();
				}
			}
		}).start(true);
	}
}
