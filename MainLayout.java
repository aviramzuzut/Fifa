package Ex1;

import java.io.IOException;

import Ex1.Ex1Backend.FifaWorldCupData;
import Ex1.Ex1Backend.FifaWorldCupFileManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainLayout extends BorderPane {

	FifaWorldCupData fifa = new FifaWorldCupFileManager(".\\res\\teams.bin", ".\\res\\games.bin");

	public MainLayout() throws IOException {

		//Window
		
		TeamPicker left = new TeamPicker();
		TeamPicker right = new TeamPicker();
	
		
		HBox bottomMenu = new HBox();
		Button newMatch = new Button("New Match");
		Button matches = new Button("Matches");
		bottomMenu.getChildren().addAll(newMatch, matches);

		this.setBottom(bottomMenu);
		this.setPadding(new Insets(0, 0, 10, 0));
		bottomMenu.setPadding(new Insets(20, 0, 20, 0));
		bottomMenu.setSpacing(30);
		bottomMenu.setAlignment(Pos.CENTER);

		HBox middleMenu = new HBox();
		this.setCenter(middleMenu);
		

		middleMenu.getChildren().addAll(left, right);
		middleMenu.setAlignment(Pos.CENTER);
		middleMenu.setSpacing(50);
		
		StackPane stackPane = new StackPane(new ImageView("file:.\\res\\logo.png"));
		stackPane.setStyle("-fx-background-color: #8a000b");
		middleMenu.setStyle("-fx-background-color: #0000f4");
		this.setTop(stackPane);
		
		//Actions
		
		matches.setOnAction(e->{
		try {
			@SuppressWarnings("unused")
			Matches matchesWindow = new Matches (fifa);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		});
		
		newMatch.setOnAction(e->{
			try {
				@SuppressWarnings("unused")
				NewMatch match = new NewMatch(left.getTeam(), right.getTeam(),fifa);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
}
