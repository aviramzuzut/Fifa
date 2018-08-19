package Ex1;

import Ex1.Ex1Backend.FifaWorldCupData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Matches {

	// String home;
	// String guest;
	// int homeScore;
	// int guestScore;
	// int numOfGames = fifa.getNumOfGames();

	static final int WIDTH = 400;
	static final int HEIGHT = 200;
	private FifaWorldCupData fifa;
	private ComboBox<String> chooseGame = new ComboBox<String>();
	
	public Matches(FifaWorldCupData fifa) {
		Stage matchWindow = new Stage();
		this.fifa = fifa;
		int numOfGames = fifa.getNumOfGames();
		matchWindow.setTitle("Recent Matches");

		
		Label matchScore = new Label();
		matchScore.setFont(new Font("Verdana", 50));
		//matchScore.setText("test");
		chooseGame.setValue("Choose game here");
		
		 for(int i = 0; i<numOfGames; i++) {
		
		 chooseGame.getItems().add(i,String.valueOf(i+1));
		 }
		 chooseGame.setOnAction((event) -> { 
			 try {
				Game myChoose = fifa.getGameAt(Integer.parseInt(chooseGame.getSelectionModel().getSelectedItem()));
				matchScore.setText(myChoose.getHome() + " " + myChoose.getHomeScore() + " - " + myChoose.getGuestScore() + " " + myChoose.getGuest());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 });

		VBox layout = new VBox();
		layout.setStyle("-fx-background-color: #8a000b");
		layout.getChildren().addAll(chooseGame, matchScore);
		layout.setPadding(new Insets(50, 50, 50, 50));
		layout.setSpacing(50);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, WIDTH, HEIGHT);
		matchWindow.setScene(scene);
		matchWindow.show();
	}

	//
	// public String setMatchScore(int index) throws IOException {
	//
	//
	//
	// return String.valueOf(fifa.getGameAt(index).getHome().getName()) + " " +
	// fifa.getGameAt(index).getHomeScore()
	// + " - "+ fifa.getGameAt(index).getGuestScore() +
	// String.valueOf(fifa.getGameAt(index).getGuest().getName());
	// }
}
