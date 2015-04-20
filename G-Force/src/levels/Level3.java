package levels;

import gameSetup.LevelSelect;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.MOB;
import resources.Particles;
import resources.Physics;
import resources.Platform;
import resources.Player;
import resources.Soldier;
import resources.Spike;

public class Level3 extends BasicGameState{
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	private ArrayList<Spike> spikes = new ArrayList<Spike>();
	public Level3(int ID){
		this.ID = ID;//ID = 5
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		Player.clearWaves();
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		MOB.clearArray();
		spikes.add(new Spike(300,320,200,60));

		
		MOBS.add(new Soldier(100,200,200,585));
		MOBS.add(new Soldier(200,200,200,585));
		
		MOBS.add(new Soldier(700,200,200,585));
		MOBS.add(new Soldier(600,200,200,585));
		
		MOBS.add(new Soldier(500,95,200,585));

		MOBS.add(new Soldier(300,95,200,585));

		platforms.add(new Platform(0,0,20,400));
		platforms.add(new Platform(780,0,20,400));
		
		platforms.add(new Platform(300,380,200,20));
		platforms.add(new Platform(280,250,20,160));
		platforms.add(new Platform(500,250,20,160));
		
		platforms.add(new Platform(0,250,300,20));
		platforms.add(new Platform(500,250,300,20));
		
		platforms.add(new Platform(450,150,100,20));
		platforms.add(new Platform(250,150,100,20));
		
		platforms.add(new Platform(20,50,20,20));//landing platform
		
		LevelSelect.setLevel(ID);

		Player.load();
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
		GeneralLevelMechanics.update(delta,sbg,gc);
		if(MOBS.size() == 0){
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
					GeneralLevelMechanics.addParticles(new Particles(15,MOBS.get(i).getPosition().x,MOBS.get(i).getPosition().y, Color.red));
					MOBS.remove(i);//loops through spike(s) and mobs to see if any of either are colliding then removes that mob
					i--;
				}
			}
			
		}
	}
	@Override
	public int getID() {
		return ID;
	}
}
