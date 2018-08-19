package Ex1;

public class Game {

	// Constant
	public final int NUM_OF_CHARS = 3;
	public final static int SIZE_OF_GAME = 20; // each game takes 20 bytes of file's length

	// Variables

	private char[] guest;
	private char[] home;
	private int homeScore;
	private int guestScore;

	// Constructor

	public Game(char[] home,char[] guest , int homeScore, int guestScore) {
		this.guest = guest;
		this.home = home;
		this.setHomeScore(homeScore);
		this.setGuestScore(guestScore);
	}

	// Getters & Setters

	public char[] getGuest() {
		return guest;
	}

	public void setGuest(char[] guest) {
		this.guest = guest;
	}

	public char[] getHome() {
		return home;
	}

	public void setHome(char[] home) {
		this.home = home;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getGuestScore() {
		return guestScore;
	}

	public void setGuestScore(int guestScore) {
		this.guestScore = guestScore;
	}

}
