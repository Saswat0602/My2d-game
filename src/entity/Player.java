package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler KeyH;
	
	public final int screenX;
	public final int screenY;
	
	public int hasKey = 0;
	
	public Player(GamePanel gp , KeyHandler keyH) {
		this.gp=gp;
		this.KeyH = keyH;
		
		screenX = gp.ScreenWidth/2 - (gp.tileSize/2);
		screenY = gp.ScreenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		
		solidArea.x=8;
		solidArea.y=16;
		
		solidAreaDefaultX =  solidArea.x;
		solidAreaDefaultY =  solidArea.y;	
		
		solidArea.width=32;
		solidArea.height=32;
		
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed=4;
		direction = "up";
	}
	
	public void getPlayerImage() {
		
		up1 = setup("boy-up-1");
		up2 = setup("boy-up-2");
		down1 = setup("boy-down-1");
		down2 = setup("boy-down-2");
		left1 = setup("boy-left-1");
		left2 = setup("boy-left-2");
		right1 = setup("boy-right-1");
		right2 = setup("boy-right-2");
		
		
		
			}
	
	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			image = uTool.scaledImage(image, gp.tileSize,gp.tileSize);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
		}
	
	
	
	public void update() {
		
		if(KeyH.upPressed == true || KeyH.downPressed == true || 
				KeyH.leftPressed == true || KeyH.rightPressed == true) {
			
			
			if(KeyH.upPressed == true) {
				direction = "up";
			
			}
			else if(KeyH.downPressed == true) {
				direction = "down";
			}
			else if(KeyH.leftPressed == true) {
				direction = "left";
			}
			else if(KeyH.rightPressed == true) {
				direction = "right";
			}
			
			//collision checker
			collisionOn = false;
			gp.cChecker.checkTiles(this);
			
			//collison check for objects
		 int objIndex =	gp.cChecker.checkObject(this, true);
		 pickUpObject(objIndex);
			
			
			//if collision is false player can move
			
			if(collisionOn == false) {
				switch(direction) {
				
				case "up":worldY-=speed;
					break;
				case "down":worldY+=speed;
					break;
				case "left":worldX-= speed ;	
					break;
				case "right":worldX+= speed ;						
					break;
				
				
				}
			}
			
			
			
			spriteCounter++;
			if(spriteCounter >10) {
				if(spriteNum ==1) {
					spriteNum = 2;
				}
				else if(spriteNum ==2) {
					spriteNum = 1;
				}
				spriteCounter=0;
			}
			
		}
			
	}
	
	public void pickUpObject(int i) {
		if(i !=999) {
			
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i]=null;
				gp.ui.showMessage("You Got a Key!!");
				System.out.println("Key: "+hasKey);
				break;
				
			case "Door":
				if(hasKey >0) {
					gp.playSE(3);
					gp.obj[i]= null;
					hasKey--;
					gp.ui.showMessage("door open!!");
				}
				else {
					gp.ui.showMessage("You need a Key!!");

				}
				break;
				
			case "Boots":
				gp.playSE(2);
				speed +=2;
				gp.obj[i]= null;
				gp.ui.showMessage("Power Ups!!");

				break;
			case "Chest":
				gp.ui.gameFinished=true;
				gp.stopMusic();
				gp.playSE(4);
				gp.ui.showMessage("game finished !!");

				break;
					
			}
			
				
			
		}
	}

	public void draw(Graphics g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y,gp.tileSize,gp.tileSize);
		
		BufferedImage image  = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;		
			}
			if(spriteNum == 2) {
				image = up2;		
			}
			break;
			
		case "down":
			if(spriteNum == 1) {
				image = down1;		
			}
			if(spriteNum == 2) {
				image = down2;		
			}
			break;
			
		case "left":
			if(spriteNum == 1) {
				image = left1;		
			}
			if(spriteNum == 2) {
				image = left2;		
			}
	    	break;
	    	
		case "right":
			if(spriteNum == 1) {
				image = right1;		
			}
			if(spriteNum == 2) {
				image = right2;		
			}
					break;
					
		}
		
		g2.drawImage(image, screenX, screenY, null);
			
	}

}









































