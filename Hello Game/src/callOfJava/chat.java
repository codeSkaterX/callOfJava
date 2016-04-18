package callOfJava;

public class chat {
	int xPos;
	int yPos;
	String[] messageList = new String[3];

	public chat(){}
	
	public chat(int x, int y){
		xPos = x;
		yPos = y;
	}
	
	public void multiMessage(String message){
		messageList[3]=messageList[2];
		messageList[2]=messageList[1];
		messageList[1]=messageList[0];
	}

}
