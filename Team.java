package Ex1;

import java.util.Arrays;

import javafx.scene.paint.Color;

public class Team {

	// Constants

	public static final int SIZE_OF_TEAM = 54;
	// Team size = 3 * char + 6 * double = 54 (name = 3chars and each color got 3
	// doubles = 6 doubles total)

	// Variables

	private char[] name;
	private Color mainColor, secondaryColor;

	// Constructor

	public Team(char[] name, Color mainColor, Color secondaryColor) {
		this.name = name;
		this.mainColor = mainColor;
		this.secondaryColor = secondaryColor;
	}

	// Getters & Setters

	public char[] getName() {
		return name;
	}

	public void setName(char[] name) {
		this.name = name;
	}

	public Color getMainColor() {
		return mainColor;
	}

	public void setMainColor(Color mainColor) {
		this.mainColor = mainColor;
	}

	public Color getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(Color secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	@Override
	public String toString() {
		return "Team [name=" + Arrays.toString(name) + ", mainColor=" + mainColor + ", secondaryColor=" + secondaryColor
				+ "]";
	}

}
