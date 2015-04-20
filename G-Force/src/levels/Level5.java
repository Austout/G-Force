package levels;

import gameSetup.LevelSelect;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.MOB;
import resources.Physics;
import resources.Platform;
import resources.Player;
import resources.Soldier;
import resources.Spike;

public class Level5 extends BasicGameState {
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	private ArrayList<Spike> spikes = new ArrayList<Spike>();
	public Level5(int ID){
		this.ID = ID;//ID = 7
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		Player.clearWaves();
		LevelSelect.setLevel(ID);
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		MOB.clearArray();
		
		platforms.add(new Platform(350,190,100,20));
		
		platforms.add(new Platform(150,90,100,20));
		MOBS.add(new Soldier(200,40,200,585));
		platforms.add(new Platform(150,290,100,20));
		MOBS.add(new Soldier(200,240,200,585));
		
		platforms.add(new Platform(550,90,100,20));
		MOBS.add(new Soldier(600,40,200,585));
		platforms.add(new Platform(550,290,100,20));
		MOBS.add(new Soldier(600,240,200,585));

		platforms.add(new Platform(50,20,100,20));
		MOBS.add(new Soldier(75,-30,200,585));
		platforms.add(new Platform(50,380,100,20));
		MOBS.add(new Soldier(75,330,200,585));
		
		platforms.add(new Platform(650,20,100,20));
		MOBS.add(new Soldier(700,-30,200,585));
		platforms.add(new Platform(650,380,100,20));
		MOBS.add(new Soldier(700,330,200,585));
		
		Player.load(400,100);
		Physics.setSpikes(spikes);
		Physics.setPlatforms(platforms);
		GeneralLevelMechanics.clear();
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
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

	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(MOBS.size() == 0){
			sbg.enterState(ID+1);
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
