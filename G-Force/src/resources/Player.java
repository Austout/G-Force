package resources;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Player {
	private static Vector2f position = new Vector2f();
	private static Vector2f size;
	private static float SPEED = 0.4f;
	private static float GRAVITY = 0.5f;
	private static float JUMP_HEIGHT = 250;
	private static float CurrentHeight = 0;
	private static int lastDirection = 1; //-1 = left, 1 = right, 0 = up
	private static Image playerRight, playerLeft, playerAttackRight,playerAttackLeft=null;
	private static ArrayList<ShockWave> waves = new ArrayList<ShockWave>();
	public static void load(){
		CurrentHeight = 0;
		lastDirection = 1;
		position = new Vector2f(30,0);
		size = new Vector2f(24,50);
		if(playerAttackLeft == null){
			try {
				playerAttackLeft = new Image("res/MainCharacter.png").getSubImage(24, 0, 24, 50).getFlippedCopy(true, false);
				playerAttackRight = new Image("res/MainCharacter.png").getSubImage(24, 0, 24, 50);
				playerLeft = new Image("res/MainCharacter.png").getSubImage(0, 0, 24, 50).getFlippedCopy(true, false);
				playerRight = new Image("res/MainCharacter.png").getSubImage(0, 0, 24, 50);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	private static float playerYSpeed = 0;
	private static int timeFromLastWave = 0;
	public static void processInput(Input input,int delta) {
		Physics.setWaves(waves);
		for(int i = 0; i < waves.size(); i++){
			waves.get(i).update(delta);
			if(waves.get(i).getPosition().x > 800 || waves.get(i).getPosition().x < 0 || waves.get(i).getPosition().y > 400){
				waves.remove(i);
				i--;
			}
		}
		Vector2f newPosition = new Vector2f(position);
		newPosition.y += GRAVITY;
		if(input.isKeyDown(Keyboard.KEY_A) || input.isKeyDown(Keyboard.KEY_LEFT)){
			lastDirection = -1;
			newPosition.x -= SPEED;
		}else if(input.isKeyDown(Keyboard.KEY_D) || input.isKeyDown(Keyboard.KEY_RIGHT)){
			newPosition.x += SPEED;
			lastDirection = 1;
		}
		if(!Physics.isPlayColliding(new Vector2f(newPosition.x, position.y), size)){
			position.x = newPosition.x;
		}
		newPosition.y += playerYSpeed;
		if(!Physics.isPlayColliding(new Vector2f(position.x, newPosition.y), size)){
			position.y = newPosition.y;
		}else if(input.isKeyDown(Keyboard.KEY_W) || input.isKeyDown(Keyboard.KEY_UP)){
			playerYSpeed = -0.9f;
		}
		CurrentHeight -= playerYSpeed;//minus because playerYSpeed is negative
		if(CurrentHeight > JUMP_HEIGHT){
			playerYSpeed = 0;
			CurrentHeight = 0;
		}
		timeFromLastWave += delta;
		if(input.isKeyDown(Keyboard.KEY_SPACE) && timeFromLastWave > 300){
			timeFromLastWave = 0;
			waves.add(new ShockWave(position.x + (size.x/2), position.y + (size.x/2), lastDirection));
			
		}
	}

	public static void render(Graphics g) {
		if(timeFromLastWave > 300 && lastDirection == -1){
			g.drawImage(playerLeft, position.x, position.y);
		}else if(timeFromLastWave > 300 && lastDirection == 1){
			g.drawImage(playerRight, position.x, position.y);
		}else if(lastDirection == -1){
			g.drawImage(playerAttackLeft, position.x, position.y);
		}else{
			g.drawImage(playerAttackRight, position.x, position.y);
		}
		for(ShockWave wave : waves){
			wave.render(g);
		}
	}
	public static void clearWaves(){
		waves = new ArrayList<ShockWave>();
	}
	public static Vector2f getPosition() {
		return position;
	}
	public static Vector2f getSize() {
		return size;
	}

	public static void load(int i, int j) {
		CurrentHeight = 0;
		lastDirection = 1;
		position = new Vector2f(i,j);
		size = new Vector2f(24,50);
		if(playerAttackLeft == null){
			try {
				playerAttackLeft = new Image("res/MainCharacter.png").getSubImage(24, 0, 24, 50).getFlippedCopy(true, false);
				playerAttackRight = new Image("res/MainCharacter.png").getSubImage(24, 0, 24, 50);
				playerLeft = new Image("res/MainCharacter.png").getSubImage(0, 0, 24, 50).getFlippedCopy(true, false);
				playerRight = new Image("res/MainCharacter.png").getSubImage(0, 0, 24, 50);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}	
	}
}
