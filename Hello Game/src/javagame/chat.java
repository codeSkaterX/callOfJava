package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class chat {
	int xPos;
	int yPos;
	String[] messageList = new String[3];
	Graphics g;
	
	public chat(){
	}
	
	public chat(Graphics g, int x, int y){
		xPos = x;
		yPos = y;
		this.g = g;
		messageList[2] = "";
		messageList[1] = "";
		messageList[0] = "";
	}
	
	
	public void multiMessage(String message){
		messageList[2] = messageList[1];
		messageList[1] = messageList[0];
		messageList[0] = message;
		
		g.setColor(Color.black);
		g.fillRect(xPos, yPos, xPos + 600, yPos + 100);
		g.setColor(Color.white);
		
		g.drawString(messageList[0], xPos, yPos + 100);
		g.drawString(messageList[1], xPos, yPos + 50);
		g.drawString(messageList[2], xPos, yPos);
	}

}
