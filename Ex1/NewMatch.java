package Ex1;

import java.io.IOException;

import com.sun.java.swing.plaf.windows.resources.windows;

import Ex1.Ex1Backend.FifaWorldCupData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewMatch {

	private FifaWorldCupData fifa;
	private TextField hostName = new TextField();
	private TextField guestName = new TextField();
	private TextField enterScoreHome = new TextField("Enter Score Here");
	private TextField enterScoreGuest = new TextField("Enter Score Here");
	private final int WIDTH = 400;
	private final int HEIGHT = 200;
	private int count = 0;
	private Stage window;


	public NewMatch(String team, String team2, FifaWorldCupData fifa) throws IOException {

		hostName.setText(team);
		guestName.setText(team2);
		this.fifa = fifa;
		this.window = new Stage();
		window.setTitle("Add New Game");
		BorderPane main = new BorderPane();
		main.setStyle(("-fx-background-color: #d00000"));
		GridPane gameTable = new GridPane();
		gameTable.setPadding(new Insets(20, 10, 10, 10));
		Label homelbl = new Label("HOME");
		Label guestlbl = new Label("GUEST");

		hostName.setDisable(true);
		guestName.setDisable(true);

		gameTable.addRow(0, homelbl, guestlbl);
		gameTable.addRow(1, hostName, guestName);
		gameTable.addRow(2, enterScoreHome, enterScoreGuest);
		gameTable.setAlignment(Pos.CENTER);
		gameTable.setVgap(15);
		gameTable.setHgap(15);
		main.setTop(gameTable);

		VBox bottom = new VBox();
		Button save = new Button("Save");
		bottom.getChildren().add(save);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(20, 20, 20, 20));
		main.setBottom(bottom);
		Scene scene = new Scene(main, WIDTH, HEIGHT);
		window.setScene(scene);
		window.show();
		save.setOnAction(e -> savingGameLogic());

	}

	private void savingGameLogic() {

		try {
			int homeScore = Integer.parseInt(enterScoreHome.getText());
			int guestScore = Integer.parseInt(enterScoreGuest.getText());
			Game game = new Game(hostName.getText().toCharArray(), guestName.getText().toCharArray(), homeScore,
					guestScore);
			fifa.saveGame(game);
			count++;
			fifa.setNumOfGames(count);
			//fifa.close();
			this.window.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e2) {

		}
	}
	public int getCount() {
		return count;
	}

}
