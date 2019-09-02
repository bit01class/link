package com.bit.sock01.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EchoHandler extends TextWebSocketHandler{
	/* 쳇팅서버 */
//	List<WebSocketSession> list=new ArrayList<WebSocketSession>();
	static int cnt;
	Map<String,WebSocketSession> map=new HashMap<String,WebSocketSession>();
	Map<String,String> users=new HashMap<String,String>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Connect...");
		cnt++;
		map.put(session.getId(), session);
		users.put(session.getId(), "user"+cnt);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
		// 클라이언트에 전달
		TextMessage msg=null;
		msg=new TextMessage(users.get(session.getId())
								+">>>"+message.getPayload());
		Set<String> keys = map.keySet();
		Iterator<String> ite = keys.iterator();
		while(ite.hasNext()) {
			map.get(ite.next()).sendMessage(msg);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("Close...");
		map.remove(session.getId());
		users.remove(session.getId());
	}
}
