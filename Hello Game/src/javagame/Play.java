package javagame;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState{
	Image portrait;
	Image worldMap;
	Image button;
	Image portrait2;
	long startTime = 0;
	long timeElapsed = 0;
	int tileSize = 32;
	float signX = 13 * tileSize;
	float signY = 3 * tileSize;
	int turns=1;
	int enemyTurn=0;
	TiledMap map;
	FontButton Back;
	FontButton End;
	FontButton Camera;
	FontButton Read;
	FontButton Attack;
	public int xpos = Mouse.getX();
	public int ypos = Mouse.getY();
	UnicodeFont theanodidot;
	int movement=6;
	int collisionLayer = 1;
	int cameraState=0;
	int camY=0;
	int camX=0;
	int enemyMovement=2;
	float enemyX;
	float enemyY;
	float soldierX;
	float soldierY;
	Unit soldier;
	Unit enemy1;
	
	String unitName="nothing";
	int unitHP=0;
	int unitDamage=0;
	int unitArmor=0;
	int unitMovement=0;
	int narration = 0;
	
	String message1 = "Midway along the journey of our life";
	String message2 = "I woke to find myself in a dark wood, ";
	String message3 = "For I had wandered off from the straight path.";
	
	public Play(int state){
		
	}
	
	public void init(final GameContainer gc, final StateBasedGame sbg) throws SlickException{
		try {
			map = new TiledMap(new FileInputStream("res/danteMap.tmx"), "res");
			soldier=new Unit(map, 1, "Dante",7,4,1,6, 49, 9);
	//		enemy1 =new Unit(map, 1, "Hero",7,3,1,2,1,7);
	//		enemyX = enemy1.getX();
	//		enemyY = enemy1.getY();
			soldierX = soldier.getX();
			soldierY = soldier.getY();
			
			theanodidot = new UnicodeFont("res/TheanoDidot-Regular.ttf", 22, false, false);
			
			End = new FontButton(gc, theanodidot, "End Turn", 600, 450, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                	timeElapsed=System.nanoTime();
	        			if(timeElapsed>=startTime+100000000 || startTime==0){
	        				startTime=System.nanoTime();
	        				soldier.reset();
	        		//		enemyMovement = enemy1.getMovement();
	        				movement = soldier.getMovement();
	        		//		if(enemy1.isAlive()){
	        					enemyTurn=0;
	        					
	        		//		}
	        			}
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
	        Back = new FontButton(gc, theanodidot, "Done", 600, 550, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                    sbg.enterState(0);
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
	        Camera = new FontButton(gc, theanodidot, "Camera", 600, 650, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                	timeElapsed=System.nanoTime();
	        			if(timeElapsed>=startTime+100000000){
		                	startTime=System.nanoTime();
		                	if(cameraState==0){
		                		cameraState=1;
		                	}	
		                	else{
		                		cameraState=0;
		                	}
	        			}
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
	        Read = new FontButton(gc, theanodidot, "Narration", 600, 750, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                	timeElapsed=System.nanoTime();
	        			if(timeElapsed>=startTime+100000000){
		                	startTime=System.nanoTime();
		                	narration++;
	        			}
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
	    /*    Attack = new FontButton(gc, theanodidot, "Sword Spin", 500, 450, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                	timeElapsed=System.nanoTime();
	        			if(timeElapsed>=startTime+100000000){
		                	if(enemy1.getX()+tileSize==soldier.getX() || enemy1.getX()-tileSize==soldier.getX()){
		                		if(enemy1.getY()==soldier.getY()){
		                			enemy1.damage(soldier.getDamage());
		                			if(enemy1.isAlive()){
		                				enemyTurn=1;
		                			}
		                		}
		                	}
		                	else if(enemy1.getY()+tileSize==soldier.getY() || enemy1.getY()-tileSize==soldier.getY()){
		                		if(enemy1.getY()==soldier.getY()){
		                			enemy1.damage(soldier.getDamage());
		                			if(enemy1.isAlive()){
		                				enemyTurn=1;
		                			}
		                		}
		                	}
	        			}
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        }; */
		} catch (FileNotFoundException e) {  
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		Image Portrait = new Image("res/FireEmblem_Cain_7233.PNG");
		Image Portrait2 = new Image("res/imgres.jpg");
		map.render(camX, camY);
		int xpos = Mouse.getX();
		int ypos = -(Mouse.getY()) + gc.getHeight();
		
		g.drawString("Movement left: " + movement, 480, 0);
		
		g.drawString("Cam: " + cameraState, 480, 25);
		
		g.drawString("Turn: " + turns, 480, 50);
		
		if(unitHP!=0){
			g.drawString("Name: "+unitName, 480, 75);
			g.drawString("HP: "+unitHP, 480, 90);
			g.drawString("Damage: "+unitDamage, 480, 105);
			g.drawString("Armor: "+unitArmor, 480, 120);
			g.drawString("Movement: "+unitMovement, 480, 135);
		}
		
		End.render(gc, g);
		Back.render(gc, g);
		Read.render(gc, g);
		Camera.render(gc,  g);;
		
		chat chatLog = new chat(g, 0, 400);
		
		chatLog.multiMessage(message1);
		chatLog.multiMessage("       " + message2);
		chatLog.multiMessage("       " + message3);
		/*
		if(enemy1.getX()+tileSize==soldier.getX() || enemy1.getX()-tileSize==soldier.getX()){
    		if(enemy1.getY()==soldier.getY())
    			Attack.render(gc, g);
    	}
    	else if(enemy1.getY()+tileSize==soldier.getY() || enemy1.getY()-tileSize==soldier.getY()){
    		if(enemy1.getX()==soldier.getX())
    			Attack.render(gc, g);
    	}
		*/
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		float soldierX = soldier.getX();
		float soldierY = soldier.getY();
		button = new Image("res/done-button-png-hi.png");
		Input input = gc.getInput();
		xpos = Mouse.getX();
		ypos = -(Mouse.getY()) + gc.getHeight();
		
	/*	if(input.isMouseButtonDown(0)){
			if(xpos>enemy1.getX()+camX && xpos<enemy1.getX()+camX+tileSize){
				if(ypos>enemy1.getY()-camY && ypos>enemy1.getY()-camY-tileSize){
					unitName=enemy1.getName();
					unitHP=enemy1.getHP();
					unitDamage=enemy1.getDamage();
					unitArmor=enemy1.getArmor();
					unitMovement=enemy1.getMovement();
				}
			}
		
			else if(xpos>soldier.getX()+camX && xpos<soldier.getX()+camX+tileSize){
				if(ypos>soldier.getY()-camY && ypos>soldier.getY()-camY-tileSize){
					unitName=soldier.getName();
					unitHP=soldier.getHP();
					unitDamage=soldier.getDamage();
					unitArmor=soldier.getArmor();
					unitMovement=soldier.getMovement();
				}
			}
			else{
				unitHP=0;
			}
		}
		*/
		/*
		if(enemyTurn==1){
			if(enemy1.getY()>soldier.getY() && enemy1.checkUp()==false)
				enemy1.moveUp(collisionLayer);
			if(enemy1.getX()>soldier.getX() && enemy1.checkLeft()==false)
				enemy1.moveLeft(collisionLayer);
			if(enemy1.getY()<soldier.getY() && enemy1.checkDown()==false)
				enemy1.moveDown(collisionLayer);
			else	
				enemy1.moveRight(collisionLayer);
			if(enemyMovement==0){
				if(enemy1.getX()+tileSize==soldier.getX() || enemy1.getX()-tileSize==soldier.getX()){
            		if(enemy1.getY()==soldier.getY())
            			soldier.damage(enemy1.getDamage());
            	}
            	else if(enemy1.getY()+tileSize==soldier.getY() || enemy1.getY()-tileSize==soldier.getY()){
            		if(enemy1.getX()==soldier.getX())
            			soldier.damage(enemy1.getDamage());
            	}
				enemyTurn=0;
				turns++;
				enemy1.reset();
			}
			enemyMovement--;
		}
		*/
		if(cameraState==1){
			timeElapsed=System.nanoTime();
			if(timeElapsed>=startTime+100000000|| startTime==0){
				if(input.isKeyDown(Input.KEY_UP)){
					startTime=System.nanoTime();
					camY -= tileSize;
				}
				else if(input.isKeyDown(Input.KEY_DOWN)){
					startTime=System.nanoTime();
					camY += tileSize;
				}
				else if(input.isKeyDown(Input.KEY_LEFT)){
					startTime=System.nanoTime();
					camX -= tileSize;
				}
				else if(input.isKeyDown(Input.KEY_RIGHT)){
					startTime=System.nanoTime();
					camX += tileSize;
				}
				
			}
		}	
		if(enemyTurn==0){
			if(cameraState==0){
				timeElapsed=System.nanoTime();
				if(timeElapsed>=startTime+100000000|| startTime==0){
					if(input.isKeyDown(Input.KEY_UP)){
						startTime=System.nanoTime();
						soldier.moveUp(collisionLayer);
						movement=soldier.getMovement();
					}
					else if(input.isKeyDown(Input.KEY_DOWN)){
						startTime=System.nanoTime();
						soldier.moveDown(collisionLayer);
						movement=soldier.getMovement();
					}
					else if(input.isKeyDown(Input.KEY_LEFT)){ 
						startTime=System.nanoTime();
						soldier.moveLeft(collisionLayer);
						movement=soldier.getMovement();
					}
					else if(input.isKeyDown(Input.KEY_RIGHT)){
						startTime=System.nanoTime();
						soldier.moveRight(collisionLayer);
						movement=soldier.getMovement();
					}
					
				}
			}
		}
		
		if(input.isKeyDown(Input.KEY_BACK)){ 
				sbg.enterState(0); 
		}	
	}
	
	public int getID(){
		return 1;
	}
}