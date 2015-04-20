package gameSetup;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.Button;

public class LevelSelect extends BasicGameState {
	private final int ID;
	private static int CurrentLevel = 3;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	LevelSelect(int ID){
		this.ID = ID;
	}
	public static int getHighestLevel(){
		return CurrentLevel;
	}
	public static void setLevel(int CurrentLevel){
		LevelSelect.CurrentLevel = CurrentLevel;
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		buttons = new ArrayList<Button>();
		buttons.add(new Button(305, 300, 200,30,Color.cyan,Color.white, 0, "Main Menu",60));	
		buttons.add(new Button(200, 100, 50,50,Color.cyan,Color.white, 3, "1",20));	
		buttons.add(new Button(290, 100, 50,50,Color.cyan,Color.white, 4, "2",20));	
		buttons.add(new Button(380, 100, 50,50,Color.cyan,Color.white, 5, "3",20));	
		buttons.add(new Button(470, 100, 50,50,Color.cyan,Color.white, 6, "4",20));	
		buttons.add(new Button(560, 100, 50,50,Color.cyan,Color.white, 7, "5",20));	
		buttons.add(new Button(200, 200, 50,50,Color.cyan,Color.white, 8, "6",20));	
		buttons.add(new Button(290, 200, 50,50,Color.cyan,Color.white, 9, "7",20));	
		buttons.add(new Button(380, 200, 50,50,Color.cyan,Color.white, 10, "8",20));	
		buttons.add(new Button(470, 200, 50,50,Color.cyan,Color.white, 11, "9",20));	
		buttons.add(new Button(560, 200, 50,50,Color.cyan,Color.white, 12, "10",16));	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(Button button : buttons){
			button.render(g);
		}
	}
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1){
		pressAvalible = false;
	}
	boolean pressAvalible = false;
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(!input.isMouseButtonDown(0)){
			pressAvalible = true;
		}
		for(Button button : buttons){
			int stateID = button.checkMousePosition(input.getMouseX(), input.getMouseY());
			if(stateID != -1 && input.isMouseButtonDown(0) && pressAvalible){
				sbg.enterState(stateID);
			}
		}	
	}

	@Override
	public int getID() {
		return ID;
	}

	

}
