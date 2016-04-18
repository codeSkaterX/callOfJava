package callOfJava;

import org.newdawn.slick.Input;

import java.io.FileInputStream;

import org.newdawn.slick.tiled.TiledMap;

public class Unit {
	String name;
	int hp;
	int damage;
	int armor;
	int xPos;
	int yPos;
	int maxMovement;
	int movement;
	int timeElapsed=0;
	int startTime=0;
	int tileSize = 32;
	TiledMap map;
	int emptyID;
	int unitID;
	int unitCollisionID;
	int grave;
	int tempID;
	int freeID;
	int attacks=1;
	int collisionLayer;
	int team;
	
	public Unit(){}
	
	public Unit(TiledMap map, int collisionLayer, String title, int life, int attack, int defense, int movement, int x, int y, int team) {
		this.map = map;
		xPos  = x*tileSize;
		yPos  = y*tileSize;
		unitID=map.getTileId(xPos/32,yPos/32, map.getLayerIndex("Units"));
		emptyID=map.getTileId(0,0,map.getLayerIndex("Empty"));
		unitCollisionID=map.getTileId(1,0,map.getLayerIndex("Empty"));
		tempID=map.getTileId(2,0,map.getLayerIndex("Empty"));
		freeID=map.getTileId(2,0,map.getLayerIndex("Empty"));
		grave=map.getTileId(3,0,map.getLayerIndex("Empty"));
		name=title;
		damage=attack;
		hp    =life;
		armor =defense;
		this.team = team;
		this.collisionLayer=collisionLayer;
		
		maxMovement   = movement;
		this.movement = maxMovement;
	}
	
	public void reset(){
		movement=maxMovement;
		attacks=1;
	}
	
	public int getTeam(){
		return team;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHP(){
		return hp;
	}
	
	public int getAttacks(){
		return attacks;
	}
	
	public int getDamage(){
		attacks--;
		return damage;
	}
	
	public int getArmor(){
		return armor;
	}
	
	public int getMaxMovement(){
		return maxMovement;
	}
	
	public int getMovement(){
		return movement;
	}
	
	public void setMovement(int newMovement){
		movement = newMovement;
	}
	
	public void AI(){
		moveRight();
		movement--;
	}
	
	public int moveUp(){
		if(movement>=1){
			map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), emptyID);
			map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), tempID);
			yPos -= tileSize; 
			movement--; 
			if(yPos<0 || checkCollision()){
				yPos += tileSize;
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
				movement++;
			}
			else{
				tempID=map.getTileId(Math.round(xPos)/32, Math.round(yPos)/32, collisionLayer);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
				System.out.println("Moving up");
				return 1;
			}
		}
		return 0;
	}
	
	public int moveDown(){
		if(movement>=1){
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), emptyID);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), tempID);
				yPos += tileSize;
				movement--;
				if(yPos>(map.getHeight()-1)*tileSize || checkCollision()){
					yPos -= tileSize;
					map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
					map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
					movement++;
				}
				else{
					tempID=map.getTileId(Math.round(xPos)/32, Math.round(yPos)/32, collisionLayer);
					map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
					map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
					System.out.println("Moving down");
					return 1;
				}
		}
		return 0;
	}
	
	public int moveLeft(){
		if(movement>=1){
			map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), emptyID);
			map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), tempID);
			xPos -= tileSize;
			movement--;
			if(xPos<0 || checkCollision()){
				xPos += tileSize;
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
				movement++;
			}
			else{
				tempID=map.getTileId(Math.round(xPos)/32, Math.round(yPos)/32, collisionLayer);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
				System.out.println("Moving left");
				return 1;
			}
		}
		return 0;
	}
	
	public int moveRight(){
		if(movement>=1){
			map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), emptyID);
			map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), tempID);
			xPos += tileSize;
			movement--;
			if(xPos>(map.getWidth()-1)*tileSize || checkCollision()){
				xPos -= tileSize;
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
				movement++;
				
			}
			else{
				tempID=map.getTileId(Math.round(xPos)/32, Math.round(yPos)/32, collisionLayer);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), unitID);
				map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), unitCollisionID);
				System.out.println("Moving right");
				return 1;
			}
		}
		return 0;	
	}
	
	public void setX(int x){
		xPos = x;
	}
	
	public void setY(int y){
		yPos = y;
	}
	
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public void damage(int injury){
		hp = hp - ( injury - armor );
		System.out.println(name + " now has " + hp + " hp.");
		isAlive();
	}
	
	public boolean isAlive(){
		if(hp > 0)
			return true;
		map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Graves"), grave);
		map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("Units"), emptyID);
		map.setTileId(Math.round(xPos/32), Math.round(yPos/32), map.getLayerIndex("collision"), freeID);
		System.out.println(grave + " " + emptyID + " " + freeID);
		System.out.println(map.getTileId(Math.round(xPos)/32, Math.round(yPos)/32, collisionLayer));
		System.out.println("Grave set");
		return false;
	}
	
	public boolean checkUp(){
		int tileID = map.getTileId(Math.round(xPos)/32, Math.round(yPos+tileSize)/32, collisionLayer);
		if(yPos<0 || map.getTileProperty(tileID, "noPass", "0").equals("1")){
			return true;
		}
		return false;
	}
	
	public boolean checkDown(){
		int tileID = map.getTileId(Math.round(xPos)/32, Math.round(yPos-tileSize)/32, collisionLayer);
		if(yPos<0 || map.getTileProperty(tileID, "noPass", "0").equals("1")){
			return true;
		}
		return false;
	}
	
	public boolean checkLeft(){
		int tileID = map.getTileId(Math.round(xPos-tileSize)/32, Math.round(yPos)/32, collisionLayer);
		if(yPos<0 || map.getTileProperty(tileID, "noPass", "0").equals("1")){
			return true;
		}
		return false;
	}
	
	public boolean checkRight(){
		int tileID = map.getTileId(Math.round(xPos+tileSize)/32, Math.round(yPos)/32, collisionLayer);
		if(yPos<0 || map.getTileProperty(tileID, "noPass", "0").equals("1")){
			return true;
		}
		return false;
	}
	
	public boolean checkCollision(){
		int tileID = map.getTileId(Math.round(xPos)/32, Math.round(yPos)/32, collisionLayer);
		if(yPos<0 || map.getTileProperty(tileID, "noPass", "0").equals("1")){
			return true;
		}
		return false;
	}
	
	public boolean adjacentTop(Unit man){
		if(man.getY()-1 == getY())
			return true;
		return false;
	}
	
	public boolean adjacentBottom(Unit man){
		if(man.getY()+1 == getY()){
			System.out.println("ABOVE");
			return true;
		}
		return false;
	}
	
	public boolean adjacentLeft(Unit man){
		if(man.getX()-1 == getX())
			return true;
		return false;
	}
	
	public boolean adjacentRight(Unit man){
		if(man.getX()+1 == getX())
			return true;
		return false;
	}
}