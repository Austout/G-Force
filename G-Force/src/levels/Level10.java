package levels;

import gameSetup.LevelSelect;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.MOB;
import resources.Physics;
import resources.Platform;
import resources.Player;
import resources.Spike;
import resources.Turret;

public class Level10 extends BasicGameState {
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	private ArrayList<Spike> spikes = new ArrayList<Spike>();
	private Image coin;
	public Level10(int ID){
		this.ID = ID;//ID = 12
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		Player.clearWaves();
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		MOB.clearArray();

		platforms.add(new Platform(-20,0,20,400));
		platforms.add(new Platform(800,0,20,400)); // walls		
		
		platforms.add(new Platform(750,300,50,20));
		MOBS.add(new Turret(750,250));
		
		platforms.add(new Platform(0,185,50,20));
		MOBS.add(new Turret(0,130));
		MOBS.add(new Turret(750,135));
		
		platforms.add(new Platform(0,50,50,20));
		MOBS.add(new Turret(0,-50));
		MOBS.add(new Turret(750,0));
		
		platforms.add(new Platform(50,380,100,20));//start platform
		platforms.add(new Platform(150,315,100,20,0.35f,150,600));
		
		platforms.add(new Platform(700,190,100,20));
		
		platforms.add(new Platform(600,250,20,20));
		platforms.add(new Platform(600,130,20,20));
		
		platforms.add(new Platform(700,50,100,20));
		
		
		
		LevelSelect.setLevel(ID);
		Player.load(100,300);
		Physics.setSpikes(spikes);
		Physics.setPlatforms(platforms);
		GeneralLevelMechanics.clear();
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		coin = new Image("res/Coin.png");
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		GeneralLevelMechanics.renderBackground(g);
		for(Platform platform : platforms){
			platform.render(g);
		}
		for(MOB mob : MOBS){
			mob.render(g);
		}
		for(Spike spike : spikes){
			spike.render(g);
		}		
		GeneralLevelMechanics.render(g);

		g.drawImage(coin, 740, 25);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for(Platform platform : platforms){
			platform.update(delta);
		}
		Physics.setPlatforms(platforms);
		if(Player.getPosition().x > 720 && Player.getPosition().y < 50){
			sbg.enterState(0);
		}
		for(int i = 0; i < MOBS.size(); i++){
			MOBS.get(i).update(delta);
			if(MOBS.get(i).getPosition().y > 400){
				MOBS.remove(i);
				i--;
			}
		}
		for(Spike spike : spikes){
			spike.update(delta);
			for(int i = 0; i < MOBS.size(); i++){
				if(Physics.isColliding(MOBS.get(i).getPosition(), MOBS.get(i).getSize(), spike.getPosition(), spike.getSize())){
					MOBS.remove(i);//loops through spike(s) and mobs to see if any of either are colliding then removes that mob
					i--;
				}
			}
			
		}
		GeneralLevelMechanics.update(delta,sbg,gc);
	}
	@Override
	public int getID() {
		return ID;
	}
}
