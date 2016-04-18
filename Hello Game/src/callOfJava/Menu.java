package callOfJava;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState{
	
	public String mouse = "No input yet.";
	
	public int xpos = Mouse.getX();
	public int ypos = Mouse.getY();
	
	Image soldier;
	int faceX = 200;
	int faceY = 200;
	
	Image playNow;
	Image exitGame;
	
	public Menu(int state){
		
	}
	
	public static boolean insideRectangle(int recX, int recY, int recLength, int recWidth, int pointX, int pointY){
		if( (recX < pointX && pointX < recLength) && (recY < pointY && pointY < recWidth)){
			return true;
		}
		return false;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		playNow=new Image("res/start.png");
		exitGame=new Image("res/exit.png");
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.drawString(mouse, 100, 50);
//		g.drawString(Integer.toString(), 200, 200);
//		g.drawString("Would you like to play Call of Java?", 50, 50);
		playNow.draw(100,100);
		exitGame.draw(100,200);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_UP)){ faceY -= 1; }
		if(input.isKeyDown(Input.KEY_DOWN)){ faceY += 1;}
		if(input.isKeyDown(Input.KEY_LEFT)){ faceX -= 1;}
		if(input.isKeyDown(Input.KEY_RIGHT)){ faceX += 1;}
		
		xpos = Mouse.getX();
		ypos = -(Mouse.getY()) + gc.getHeight();
		
		if(insideRectangle(100, 100, 100+playNow.getWidth(), 100+playNow.getHeight(), xpos, ypos))
			if(input.isMouseButtonDown(0)){
				sbg.enterState(1);
			}
		
		if(insideRectangle(100, 200, 100+exitGame.getWidth(), 200+exitGame.getHeight(), xpos, ypos))
			if(input.isMouseButtonDown(0)){
				System.exit(0);
			}
		
		mouse = xpos + ", " + ypos;
	}
	
	public int getID(){
		return 0;
	}
}
