package gameSetup;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import levels.*;

public class Initalization extends StateBasedGame{
	private static	AppGameContainer appc;
	private static final int MainMenu = 0;
	private static final int LevelSelect = 1;
	private static final int Options = 2;
	private static final int Level1 = 3;
	private static final int Level2 = 4;
	private static final int Level3 = 5;
	private static final int Level4 = 6;
	private static final int Level5 = 7;
	private static final int Level6 = 8;
	private static final int Level7 = 9;
	private static final int Level8 = 10;
	private static final int Level9 = 11;
	private static final int Level10 = 12;

	public Initalization(String name) {
		super(name);
		this.addState(new MainMenu(MainMenu));//State 0;
		this.addState(new LevelSelect(LevelSelect));//State 1;
		this.addState(new Options(Options));//State 2;
		this.addState(new Level1(Level1));//State 3;
		this.addState(new Level2(Level2));//State 4;
		this.addState(new Level3(Level3));//State 5;
		this.addState(new Level4(Level4));//State 6;
		this.addState(new Level5(Level5));//State 7;
		this.addState(new Level6(Level6));//State 8;
		this.addState(new Level7(Level7));//State 9;
		this.addState(new Level8(Level8));//State 10;
		this.addState(new Level9(Level9));//State 11;
		this.addState(new Level10(Level10));//State 12;

	}

	public static void main(String[] args) {
		try{
			appc = new AppGameContainer(new Initalization("G-Force"));
			appc.setDisplayMode(800,400, false);
			appc.setVSync(true);
			appc.setShowFPS(false);
			appc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MainMenu).init(gc, this);
		this.getState(LevelSelect).init(gc, this);
		this.getState(Options).init(gc, this);// Initializes the GameStates
		this.getState(Level1).init(gc, this);
		this.getState(Level2).init(gc, this);
		this.getState(Level3).init(gc, this);
		this.getState(Level4).init(gc, this);
		this.getState(Level5).init(gc, this);
		this.getState(Level6).init(gc, this);
		this.getState(Level7).init(gc, this);
		this.getState(Level8).init(gc, this);
		this.getState(Level9).init(gc, this);
		this.getState(Level10).init(gc, this);

		this.enterState(0);
	}

}
