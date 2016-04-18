package callOfJava;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

public class wordButton {
	public wordButton(GUIContext guic, int x, int y, StateBasedGame sbg){
		
	}


public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
	g.drawString("HI", 500, 500);
}

}