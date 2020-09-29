package org.quick.meduo.tools.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.tools.core.util.StrUtil;
import org.quick.meduo.tools.socket.aio.AioClient;
import org.quick.meduo.tools.socket.aio.AioSession;
import org.quick.meduo.tools.socket.aio.SimpleIoAction;

public class AioClientTest {
	public static void main(String[] args) {
		AioClient client = new AioClient(new InetSocketAddress("localhost", 8899), new SimpleIoAction() {
			
			@Override
			public void doAction(AioSession session, ByteBuffer data) {
				if(data.hasRemaining()) {
					Console.log(StrUtil.utf8Str(data));
					session.read();
				}
				Console.log("OK");
			}
		});
		
		client.write(ByteBuffer.wrap("Hello".getBytes()));
		client.read();
		
		client.close();
	}
}
