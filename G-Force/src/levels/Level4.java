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
import resources.Soldier;
import resources.Spike;


public class Level4 extends BasicGameState {
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	private ArrayList<Spike> spikes = new ArrayList<Spike>();
	private Image coin;
	public Level4(int ID){
		this.ID = ID;//ID = 6
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		Player.clearWaves();
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		MOB.clearArray();
		
		spikes.add(new Spike(320,320,180,60));

		
		MOBS.add(new Soldier(200,200,200,585));
		
		MOBS.add(new Soldier(600,200,200,585));

		platforms.add(new Platform(0,0,20,400));
		platforms.add(new Platform(780,0,20,400));
		
		platforms.add(new Platform(100,0,20,300));
		platforms.add(new Platform(70,280,80,20));
		platforms.add(new Platform(70,180,80,20));
		
		platforms.add(new Platform(300,100,20,300));
		platforms.add(new Platform(270,100,80,20));

		platforms.add(new Platform(500,100,20,300));
		platforms.add(new Platform(470,100,80,20));
		
		platforms.add(new Platform(700,0,20,300));
		platforms.add(new Platform(670,280,80,20));
		platforms.add(new Platform(670,180,80,20));
		
		platforms.add(new Platform(20,75,20,20));//landing platform
		platforms.add(new Platform(760,75,20,20));//exit platform
		
		platforms.add(new Platform(0,380,800,20));

		LevelSelect.setLevel(ID);
		Player.load();
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
		if(Player.getPosition().x > 720 && Player.getPosition().y < 75){
			sbg.enterState(ID+1);
		}
		for(MOB mob : MOBS){
			mob.update(delta);
		}
		for(Spike spike : spikes){
			spike.update(delta);
			if(Physics.isColliding(Player.getPosition(), Player.getSize(), spike.getPosition(), spike.getSize())){
				GeneralLevelMechanics.killPlayer(Player.getPosition().x, Player.getPosition().y);
			}
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
