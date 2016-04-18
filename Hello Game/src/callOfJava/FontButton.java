package callOfJava;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

public class FontButton extends MouseOverArea {

    private final TrueTypeFont font, biggerFont;
    private final String text;
    private boolean lastMouseOver = false;
    private final StateBasedGame sbg;
    private final int stateID;
    private boolean isEnabled = true;
 
    public FontButton(GUIContext guic, UnicodeFont _font, String text, int x,
            int y, int width, int height, StateBasedGame sbg, int stateID)
            throws SlickException {
        super(guic, null, x, y, width, height);
        this.font = new TrueTypeFont(_font.getFont(), true);
        this.text = text;
        this.sbg = sbg;
        this.stateID = stateID;
        
        super.setNormalColor(Color.green);
        
        java.awt.Font baseFont = _font.getFont();
        this.biggerFont = new TrueTypeFont(new UnicodeFont(baseFont, baseFont.getSize()+4, baseFont.isBold(), baseFont.isItalic()).getFont(), true);
    }
 
    public void setIsEnabled(boolean b) {
        isEnabled = b;
    }
 
    public boolean isEnabled() {
        return isEnabled;
    }
 
    @Override
    public void render(GUIContext guic, Graphics g) {
        super.render(guic, g);
    	
    	TrueTypeFont f = this.font;
    	Color c = Color.gray;
    	int dx = 0, dy = 0;
    	
        if (isEnabled) {
            c = Color.orange;
            if (isMouseOver()) {
            	f = this.biggerFont;
                c = new Color(200, 50, 30);
                dx = -2;
                dy = -2;
            }
        }
        
        f.drawString(getX() + dx, getY() + dy, this.text, c);
    }
 
    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (sbg.getCurrentStateID() == stateID && isEnabled) {
            if (isMouseOver() && !lastMouseOver) {
                lastMouseOver = true;
            } else if (!isMouseOver()) {
                lastMouseOver = false;
            }
        }
        super.mouseMoved(oldx, oldy, newx, newy);
    }
 
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (isMouseOver() && sbg.getCurrentStateID() == stateID && isEnabled) {
            
        }
        super.mouseClicked(button, x, y, clickCount);
    }
}
