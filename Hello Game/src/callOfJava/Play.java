package callOfJava;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	int turns=1;
	int enemyTurn=0;
	TiledMap map;
	FontButton Back;
	FontButton End;
	FontButton Camera;
	FontButton Attack;
	FontButton Victory;
	
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
	Unit enemy2;
	Unit enemy3;
	
	Unit boss;
	
	int counter;
	
	String unitName="nothing";
	int unitHP=0;
	int unitDamage=0;
	int unitArmor=0;
	int unitMovement=0;
	int unitMovementLeft=0;
	List<Unit> allUnits;
	List<Unit> enemies;
	List<Unit> heroes;
	
	Unit temp;
	Unit show;
	
	chat console;
	
	public Play(int state){
	}
	
	public void init(final GameContainer gc, final StateBasedGame sbg) throws SlickException{
		try {
			map = new TiledMap(new FileInputStream("res/Map.tmx"), "res");
			allUnits = new ArrayList<>();
			enemies = new ArrayList<>();
			heroes = new ArrayList<>();
			soldier=new Unit(map, 1, "Guard",7,4,1,6,13,7,1);
			enemy1 =new Unit(map, 1, "Hero",7,3,1,2,1,5,2);
			enemy2 =new Unit(map, 1, "Hero",7,3,1,2,1,7,2);
			enemy3 =new Unit(map, 1, "Hero",7,3,1,2,1,9,2);
			boss   =new Unit(map, 1, "Prince Charles",3,1,2,2,0,6,2);
			allUnits.add(soldier);
			allUnits.add(enemy1);
			allUnits.add(enemy2);
			allUnits.add(enemy3);
			allUnits.add(boss);
			enemies.add(enemy1);
			enemies.add(enemy2);
			enemies.add(enemy3);
			enemies.add(boss);
			heroes.add(soldier);
		
			enemyX = enemy1.getX();
			enemyY = enemy1.getY();
			soldierX = soldier.getX();
			soldierY = soldier.getY();
			temp=soldier;
			
			theanodidot = new UnicodeFont("res/TheanoDidot-Regular.ttf", 22, false, false);
			
			End = new FontButton(gc, theanodidot, "End Turn", 500, 190, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                	timeElapsed=System.nanoTime();
	                	if(timeElapsed>=startTime+10000000 || startTime==0){
	                		for(Unit selection : allUnits){
	        					selection.reset();
	        				}
	                		enemyTurn=1;
	        				startTime=System.nanoTime();
	        				System.out.println("Ending turn ... ");
	        			}
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
	        Back = new FontButton(gc, theanodidot, "Done", 500, 275, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                    sbg.enterState(0);
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
	        Camera = new FontButton(gc, theanodidot, "Camera", 500, 365, 100, 60, sbg, 1) {
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
	        
	        Attack = new FontButton(gc, theanodidot, "Sword Spin", 500, 450, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                	timeElapsed=System.nanoTime();
	        			if(timeElapsed>=startTime+100000000){
	        				startTime=System.nanoTime();
				            if(temp.getAttacks()>0){
				            	for(int i=0; i<enemies.size(); i++){	
		        					if(enemies.get(i).getY()==temp.getY()){
				                		if(enemies.get(i).getX()+tileSize==temp.getX() || enemies.get(i).getX()-tileSize==temp.getX()){
				                			enemies.get(i).damage(temp.getDamage());
				                			if(enemies.get(i).isAlive() == false){
				                				enemies.remove(i);
				                			}
				                		}
		        					}
		        					else{
			        					if(enemies.get(i).getX()==temp.getX()){
					                		if(enemies.get(i).getX()+tileSize==temp.getX() || enemies.get(i).getX()-tileSize==temp.getX()){
					                			enemies.get(i).damage(temp.getDamage());
					                			if(enemies.get(i).isAlive() == false){
					                				enemies.remove(i);
					                			}
					                		}
			        					}
		        					}
		        				}
				            }
				            else{
				            	System.out.println(temp.getAttacks());
				            }
	        			}
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
	        Victory = new FontButton(gc, theanodidot, "Accept victory?", 500, 365, 100, 60, sbg, 1) {
	            @Override
	            public void mouseClicked(int button, int x, int y, int clickCount) {
	                if (isMouseOver() && sbg.getCurrentStateID() == Play.this.getID() && isEnabled()) {
	                	timeElapsed=System.nanoTime();
	        			if(timeElapsed>=startTime+100000000){
		                	startTime=System.nanoTime();
		                	if(enemies.size() == 0)
		                		System.exit(0);
	        			}
	                }
	                super.mouseClicked(button, x, y, clickCount);
	            }
	        };
	        
		} catch (FileNotFoundException e) {  
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		map.render(camX, camY);
		int xpos = Mouse.getX();
		int ypos = -(Mouse.getY()) + gc.getHeight();
		
	/*	if(parent.insideRectangle(xpos, ypos, tileSize, tileSize, Math.round(soldierX), Math.round(soldierY))){
			g.drawString("Soldier Health: "+soldierHealth, 480, 75);
		}
		else{showHealthSoldier=0;}
		
		if(parent.insideRectangle(xpos, ypos, tileSize, tileSize, Math.round(enemyX), Math.round(enemyY))){
			g.drawString("Soldier Health: "+soldierHealth, 480, 75);
		}
		else{showHealthEnemy=0;}*/
		
		g.drawString("Cam: " + cameraState, 480, 0);
		
		g.drawString("Turn: " + turns, 480, 25);
		
	/*	if(enemyTurn==1)
		g.drawString("ENEMY TURN: TRUE", 480, 50);
		else
		g.drawString("ENEMY TURN: FALSE", 480, 50);*/
		
		if(unitHP>0){
			g.drawString("Name: "+show.name, 480, 75);
			g.drawString("HP: "+show.hp, 480, 90);
			g.drawString("Damage: "+show.damage, 480, 105);
			g.drawString("Armor: "+show.armor, 480, 120);
			g.drawString("Movement: "+show.maxMovement, 480, 135);
			g.drawString("Movement left: " + show.movement, 480, 150);
		}
		
/*		g.drawString(xpos+" > "+soldier.getX(), 480, 135);
		g.drawString(xpos+" < "+(soldier.getX()+tileSize), 480, 150);
		g.drawString(ypos+" > "+soldier.getY(), 480, 165);
		g.drawString(ypos+" > "+(soldier.getY()-tileSize), 480, 180);*/
		
//		g.setColor(Color.black);
//		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
//
//		g.setColor(Color.black);
		
		End.render(gc, g);
		Back.render(gc, g);
		Camera.render(gc, g);
		
		if(enemies.size() == 0)
			Victory.render(gc, g);
		
		for(Unit enemy : enemies){
			for(Unit hero : heroes){
				if(enemy.getX()+tileSize==hero.getX() || enemy.getX()-tileSize==hero.getX()){
		    		if(enemy1.getY()==soldier.getY())
		    			Attack.render(gc, g);
		    	}
		    	else if(enemy.getY()+tileSize==hero.getY() || enemy.getY()-tileSize==hero.getY()){
		    		if(enemy.getX()==hero.getX())
		    			Attack.render(gc, g);
		    	}
			}
		}
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		button = new Image("res/done-button-png-hi.png");
		Input input = gc.getInput();
		xpos = Mouse.getX();
		ypos = -(Mouse.getY()) + gc.getHeight();
		
		if(input.isMouseButtonDown(0)){
			timeElapsed=System.nanoTime();
			if(timeElapsed>=startTime+100000000|| startTime==0){
				startTime=System.nanoTime();
				counter = 0;
				for(Unit selection: allUnits){
			//		System.out.println(selection.getName() + " is the name, the x is " + selection.getX() + " and the y is " + selection.getY());
					if(xpos>=selection.getX()+camX && xpos<=selection.getX()+camX+tileSize){
						if(ypos>=selection.getY()-camY && ypos<=selection.getY()-camY+tileSize){
							unitName=selection.getName();
							unitHP=selection.getHP();
							unitDamage=selection.getDamage();
							unitArmor=selection.getArmor();
							unitMovementLeft=selection.getMovement();
							unitMovement=selection.getMaxMovement();
							show = selection;
							counter = 1;			
							if(selection.getTeam() == 1){
								temp = selection;
							}
						}
					}
			/*		else if(allUnits.size() == allUnits.lastIndexOf(selection) + 1 && counter == 0){
						unitHP = 0;
					}*/
				}
			}
		}
		
		if(enemyTurn==1){
			if(enemy1.getHP()>0)
				enemy1.AI();
			/*	while(enemy1.movement > 0){
                //	if(enemy1.getY()>soldier.getY() && enemy1.checkUp()==false)
				//		enemy1.moveUp(collisionLayer);
				//	if(enemy1.getX()>soldier.getX() && enemy1.checkLeft()==false)
				//		enemy1.moveLeft(collisionLayer);
				//	if(enemy1.getY()<soldier.getY() && enemy1.checkDown()==false)
				//		enemy1.moveDown(collisionLayer);
				//	else	
				enemy1.moveRight(collisionLayer);
					if(enemy1.getX()+tileSize==soldier.getX() || enemy1.getX()-tileSize==soldier.getX()){
	            		if(enemy1.getY()==soldier.getY())
	            			soldier.damage(enemy1.getDamage());
	            	}
	            	else if(enemy1.getY()+tileSize==soldier.getY() || enemy1.getY()-tileSize==soldier.getY()){
	            		if(enemy1.getX()==soldier.getX())
	            			soldier.damage(enemy1.getDamage());
	            	}
				
			}*/
			enemyTurn=0;
			turns++;
		}
		if(cameraState==1){
			if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_RIGHT)){
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
		}	
	
		if(enemyTurn==0){
			if(cameraState==0){
				if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_RIGHT)){
					timeElapsed=System.nanoTime();	
					if(timeElapsed>=startTime+100000000|| startTime==0){
						if(input.isKeyDown(Input.KEY_UP)){
							System.out.println(allUnits.indexOf(temp));
							startTime=System.nanoTime();
							temp.moveUp();
							movement=temp.getMovement();
						}
						else if(input.isKeyDown(Input.KEY_DOWN)){
							System.out.println(allUnits.indexOf(temp));
							startTime=System.nanoTime();
							temp.moveDown();
							movement=temp.getMovement();
						}
						else if(input.isKeyDown(Input.KEY_LEFT)){ 
							System.out.println(allUnits.indexOf(temp));
							startTime=System.nanoTime();
							temp.moveLeft();
							movement=temp.getMovement();
						}
						else if(input.isKeyDown(Input.KEY_RIGHT)){
							System.out.println(allUnits.indexOf(temp));
							startTime=System.nanoTime();
							temp.moveRight();
							movement=temp.getMovement();
						}
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
