package Ex1.Ex1Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import Ex1.Ex1Backend.FifaWorldCupData;
import Ex1.Game;
import Ex1.Team;
import javafx.scene.paint.Color;

public class FifaWorldCupFileManager implements FifaWorldCupData {

	// Constant

	private final static int INT_LENGTH = Integer.BYTES;
	private final static int NUM_OF_CHARS = 3; // Team name got 3 chars
	private final static int BEGINING_OF_FILE = 0;

	// Variables

	private RandomAccessFile teamsFile;
	private RandomAccessFile gamesFile;
	private Team host;
	private Team guest;
	private Game tempGame;
	private int numOfGames = 0;

	// Constructor

	public FifaWorldCupFileManager(String teamsFileAdd, String gamesFileAdd) throws FileNotFoundException {
		teamsFile = new RandomAccessFile(new File(teamsFileAdd), "rw");
		gamesFile = new RandomAccessFile(new File(gamesFileAdd), "rw");

	}

	// Functions

	@Override
	public void saveTeam(Team team) throws IOException {
		long endOfFile = teamsFile.length();
		if (endOfFile == BEGINING_OF_FILE) {
			teamsFile.writeInt(0); // If the file has no length, Number of teams is 0.
		}

		char[] teamName = team.getName();

		for (int i = 0; i < NUM_OF_CHARS; i++)
			teamsFile.writeChar(teamName[i]);

		teamsFile.writeDouble(team.getMainColor().getRed());
		teamsFile.writeDouble(team.getMainColor().getGreen());
		teamsFile.writeDouble(team.getMainColor().getBlue());
		teamsFile.writeDouble(team.getSecondaryColor().getRed());
		teamsFile.writeDouble(team.getSecondaryColor().getGreen());
		teamsFile.writeDouble(team.getSecondaryColor().getBlue());

		long cursor = teamsFile.getFilePointer(); // Saving current cursor spot

		teamsFile.seek(BEGINING_OF_FILE);
		int count = getNumOfTeams();
		count++;
		teamsFile.writeInt(count); // Updates the number of teams
		teamsFile.seek(cursor); // Takes cursor back to the end of the file.
	}

	@Override
	public void saveTeam(Team team, int index) throws IOException {

		long endOfFile = teamsFile.length();
		int maxIndex = getNumOfTeams();
		if (endOfFile == BEGINING_OF_FILE && index == 0) {
			teamsFile.writeInt(0); // If the file has no length, Number of teams is 0.
			writeTeamInIndex(team);
			try {
				if (index > maxIndex) {

				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Index is out of bounds");
				// teamsFile.close();
			}
		} else {
			teamsFile.seek(INT_LENGTH + ((index - 1) * Team.SIZE_OF_TEAM)); // Beginning of the file has int, and after
																			// that are
			// teams.
			if (index == maxIndex) {
				writeTeamInIndex(team);
			} else {
				int numOfRotations = maxIndex - index;
				Team temp = new Team(null, null, null);
				char tempChars[] = new char[NUM_OF_CHARS]; // Read current team of file
				for (int i = 0; i < numOfRotations; i++) {
					for (int j = 0; j < NUM_OF_CHARS; j++) {
						tempChars[j] = teamsFile.readChar();
					}
					temp.setName(tempChars);
					Color mainColor = Color.color(teamsFile.readDouble(), teamsFile.readDouble(),
							teamsFile.readDouble());
					temp.setMainColor(mainColor);

					Color secondaryColor = Color.color(teamsFile.readDouble(), teamsFile.readDouble(),
							teamsFile.readDouble());
					temp.setSecondaryColor(secondaryColor);
					writeTeamInIndex(team);
					team = temp;
				}
			}
		}
		long cursor = teamsFile.getFilePointer(); // Saving current cursor spot
		teamsFile.seek(BEGINING_OF_FILE);
		int count = getNumOfTeams();
		count++;
		teamsFile.writeInt(count); // Updates the number of teams
		teamsFile.seek(cursor); // Takes cursor back to the end of the file.
	}

	@Override
	public Team nextTeam() throws IOException {
		long cursor = teamsFile.getFilePointer();
		long endOfFile = teamsFile.length();

		if (endOfFile == 0) // If the file has no length, Number of teams is 0.
		{
			return null;
		}
		if (cursor == BEGINING_OF_FILE || cursor == endOfFile)
			teamsFile.seek(INT_LENGTH);
		return readTeam();
	}

	@Override
	public Team previousTeam() throws IOException {

		long cursor = teamsFile.getFilePointer();
		long endOfFile = teamsFile.length();

		if (cursor == INT_LENGTH +  Team.SIZE_OF_TEAM || cursor == BEGINING_OF_FILE) { //if the cursor is at the int (number of teams) or at the begining
			cursor = endOfFile - (Team.SIZE_OF_TEAM);
			teamsFile.seek(cursor);

		} else {
		
			cursor -= (2 * Team.SIZE_OF_TEAM);
			teamsFile.seek(cursor);
		}
		return readTeam();
	}

	@Override
	public void saveGame(Game game) throws IOException {

		if (gamesFile.length() == 0) {
			gamesFile.writeInt(0); // If the file has no length, Number of games is 0.

		} else {
			gamesFile.seek(gamesFile.length());
		}
		char[] FirstTeamName = game.getHome();
		char[] SecondTeamName = game.getGuest();

		for (int i = 0; i < NUM_OF_CHARS; i++)
			teamsFile.writeChar(FirstTeamName[i]);

		for (int i = 0; i < NUM_OF_CHARS; i++)
			teamsFile.writeChar(SecondTeamName[i]);

		gamesFile.writeInt(game.getHomeScore());
		gamesFile.writeInt(game.getGuestScore());

		gamesFile.seek(BEGINING_OF_FILE);
		long cursor = teamsFile.getFilePointer(); // Saving current cursor spot
		int count = getNumOfGames();

		this.setNumOfGames(count++);
		gamesFile.writeInt(count);
		gamesFile.seek(cursor);
	}

	@Override
	public void saveGame(Game game, int index) throws IOException {
		long endOfFile = gamesFile.length();
		int maxIndex = getNumOfGames();
		if (endOfFile == BEGINING_OF_FILE) {
			gamesFile.writeInt(0); // If the file has no length, Number of games is 0.
		}
		if (index > maxIndex)
			throw new IOException("The max index value that can be entered is: " + maxIndex);
		else { // then it can be.
			gamesFile.seek(INT_LENGTH + ((index - 1) * Game.SIZE_OF_GAME)); // Beginning of the file has int, and after
																			// that are
			Game temp = new Game(null, null, 0, 0); // teams.
			char[] temp1 = new char[NUM_OF_CHARS];
			char[] temp2 = new char[NUM_OF_CHARS];
			for (int i = 0; i < index; i++) {
				for (int j = 0; j < NUM_OF_CHARS; j++)
					temp1[j] = gamesFile.readChar();
				for (int k = 0; k < NUM_OF_CHARS; k++)
					temp2[k] = gamesFile.readChar();
				temp.setHomeScore(gamesFile.readInt());
				temp.setGuestScore(gamesFile.readInt());
				writeGameInIndex(game);
				game = temp;
			}
			long cursor = teamsFile.getFilePointer(); // Saving current cursor spot
			gamesFile.seek(BEGINING_OF_FILE);
			int count = getNumOfGames();
			this.setNumOfGames(count++);
			gamesFile.writeInt(count); // Updates the number of games
			gamesFile.seek(cursor); // Takes cursor back to the end of the file.
		}
	}

	@Override
	public Game getGameAt(int index) throws IOException {
		if (gamesFile.length() == 0) {
			return null;
		}
		tempGame = null;
		host = null;
		guest = null;
		long cursor = gamesFile.getFilePointer();
		gamesFile.seek(INT_LENGTH + index * Game.SIZE_OF_GAME);
		char team1[] = new char[NUM_OF_CHARS];
		char team2[] = new char[NUM_OF_CHARS];

		for (int i = 0; i < NUM_OF_CHARS; i++) {
			team1[i] = gamesFile.readChar();
		}
		for (int i = 0; i < NUM_OF_CHARS; i++) {
			team2[i] = gamesFile.readChar();
		}
		int homeScore  = gamesFile.readInt();
		int guestScore  = gamesFile.readInt();

		
		tempGame = new Game(team1, team2, homeScore, guestScore);
		gamesFile.seek(cursor);
		return tempGame;
	}

	@Override
	public int getNumOfTeams() {
		int count = 0;
		long cursor;

		try {
			cursor = teamsFile.getFilePointer();
			teamsFile.seek(0);
			count = teamsFile.readInt();
			teamsFile.seek(cursor);
		} catch (IOException e) {
			System.out.println("Teams are not loaded yet");
		}

		return count;
	}

	@Override
	public int getNumOfGames() {
		int count = 0;
		long cursor;
		try {
			cursor = gamesFile.getFilePointer();
			gamesFile.seek(0);
			//count = teamsFile.readInt();
			count = this.numOfGames;
			gamesFile.seek(cursor); 
		} catch (IOException e) {

			System.out.println("There are no games!");
		}
		return count;
	}

	public void close() throws IOException {
		teamsFile.close();
		gamesFile.close();
	}

	public void writeTeamInIndex(Team team) throws IOException {
		char[] teamName = team.getName();
		for (int i = 0; i < NUM_OF_CHARS; i++)
			teamsFile.writeChar(teamName[i]);

		teamsFile.writeDouble(team.getMainColor().getRed());
		teamsFile.writeDouble(team.getMainColor().getGreen());
		teamsFile.writeDouble(team.getMainColor().getBlue());
		teamsFile.writeDouble(team.getSecondaryColor().getRed());
		teamsFile.writeDouble(team.getSecondaryColor().getGreen());
		teamsFile.writeDouble(team.getSecondaryColor().getBlue());
	}

	public void writeGameInIndex(Game game) throws IOException {
		char[] FirstTeamName = game.getHome();
		char[] SecondTeamName = game.getGuest();

		for (int i = 0; i < NUM_OF_CHARS; i++)
			teamsFile.writeChar(FirstTeamName[i]);

		for (int i = 0; i < NUM_OF_CHARS; i++)
			teamsFile.writeChar(SecondTeamName[i]);

		gamesFile.writeInt(game.getHomeScore());
		gamesFile.writeInt(game.getGuestScore());
	}

	private Team readTeam() throws IOException {
		Team temp = new Team(null, null, null);
		char name[] = new char[NUM_OF_CHARS];
		for (int i = 0; i < name.length; i++)
			name[i] = teamsFile.readChar();
		temp.setName(name);

		Color mainColor = Color.color(teamsFile.readDouble(), teamsFile.readDouble(), teamsFile.readDouble());
		temp.setMainColor(mainColor);

		Color secondaryColor = Color.color(teamsFile.readDouble(), teamsFile.readDouble(), teamsFile.readDouble());
		temp.setSecondaryColor(secondaryColor);
		return temp;
	}
	public void setNumOfGames(int numOfGames) {
		this.numOfGames = numOfGames;
	}
	
	
}
