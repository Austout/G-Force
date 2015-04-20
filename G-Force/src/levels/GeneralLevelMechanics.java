package levels;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import resources.Bullet;
import resources.Button;
import resources.MOB;
import resources.Particles;
import resources.Physics;
import resources.Player;

public class GeneralLevelMechanics {
	private static ArrayList<Particles> particles = new ArrayList<Particles>();
	private static Button mainMenu = new Button(305, 300, 200,30,Color.cyan,Color.white, 0, "Main Menu",60);
	private static Button restart = new Button(305, 350, 200,30,Color.cyan,Color.white, 0, "Restart",60);
	private static Image cave = null;
	public static void addParticles(Particles particle){
		particles.add(particle);
	}
	public static void render(Graphics g){
		if(playerAlive){
			Player.render(g);
		}else{
			mainMenu.render(g);
			restart.render(g);
		}
		for(Particles particle : particles){
			particle.render(g);
		}
		for (Bullet bullet : MOB.getBullets()) {
			bullet.render(g);
		}
	}
	public static boolean playerAlive = true;
	public static void update(int delta, StateBasedGame sbg, GameContainer gc){
		if(playerAlive){
			Player.processInput(gc.getInput(), delta);
		}else{
			int stateID = mainMenu.checkMousePosition(gc.getInput().getMouseX(), gc.getInput().getMouseY());
			if(stateID != -1 && gc.getInput().isMouseButtonDown(0)){
				sbg.enterState(0);
			}
			stateID = restart.checkMousePosition(gc.getInput().getMouseX(), gc.getInput().getMouseY());
			if(stateID != -1 && gc.getInput().isMouseButtonDown(0)){
				sbg.enterState(sbg.getCurrentStateID());
			}
		}
		if (Player.getPosition().y > 400 && playerAlive) {
			killPlayer(Player.getPosition().x, Player.getPosition().y);
		}
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).update(delta);
			if(particles.get(i).end){
				particles.remove(i);
			}
		}
		ArrayList<Bullet> bullets = MOB.getBullets();
		for (int i = 0; i < MOB.getBullets().size(); i++) {
			if (Physics.isColliding(bullets.get(i).getPosition(), bullets.get(i).getSize(),Player.getPosition(), Player.getSize()) && playerAlive) {
				killPlayer(bullets.get(i).getPosition().x,bullets.get(i).getPosition().y);
			}
			bullets.get(i).update(delta);
			if (bullets.get(i).getPosition().x > 800 || bullets.get(i).getPosition().x < 0 || bullets.get(i).getPosition().y > 400
					|| bullets.get(i).getPosition().y < 0 || Physics.isPlayColliding(bullets.get(i).getPosition(), bullets.get(i).getSize())
					|| Physics.isCollidingMobWave(bullets.get(i).getPosition(), bullets.get(i).getSize()) != 2) {
				addParticles(new Particles(15,bullets.get(i).getPosition().x,bullets.get(i).getPosition().y, Color.red));
				MOB.removeBullet(i);
				bullets = MOB.getBullets();
				i--;
			}
		}
	}
	public static void killPlayer(float x, float y) {
		if(playerAlive){
			GeneralLevelMechanics.addParticles(new Particles(15,x,y, Color.red));
			playerAlive = false;		
		}
	}
	public static void clear() {
		if(cave == null){
			try {
				cave = new Image("res/CaveBackground.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		playerAlive = true;
		particles = new ArrayList<Particles>();
	}
	public static void renderBackground(Graphics g) {
		g.drawImage(cave, 0, 0);		
	}
}
