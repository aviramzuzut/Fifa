package Ex1.Ex1Backend;

import java.io.IOException;

import Ex1.Game;
import Ex1.Team;

public interface FifaWorldCupData {

	void saveTeam(Team team) throws IOException;

	void saveTeam(Team team, int index) throws IOException;

	Team nextTeam() throws IOException;

	Team previousTeam() throws IOException;

	void saveGame(Game game) throws IOException;

	void saveGame(Game game, int index) throws IOException;

	Game getGameAt(int index) throws IOException;

	int getNumOfTeams();

	int getNumOfGames();
	
	void close() throws IOException;
	
	public void setNumOfGames(int numOfGames);
	
}
