package Ex1;

import java.io.FileNotFoundException;
import java.io.IOException;

import Ex1.Ex1Backend.FifaWorldCupData;
import Ex1.Ex1Backend.FifaWorldCupFileManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class TeamPicker extends BorderPane {

	private Team nowTeam = null;
	private boolean firstRun = true;
	
	FifaWorldCupData fifa = new FifaWorldCupFileManager(".\\res\\teams.bin", ".\\res\\games.bin");

	public TeamPicker() throws FileNotFoundException, IOException {

		setPadding(new Insets(20, 20, 20, 20));
		Button next = new Button(">");
		Button previous = new Button("<");

		ImageView flagPic = new ImageView("file:.\\res\\photos\\arg.png");
		flagPic.setFitHeight(200);
		flagPic.setFitWidth(400);
		Label teamName = new Label("ARG");

		Color top = new Color(0.5, 0.5, 0.5, 0.5);
		Circle topCircle = new Circle(10, 10, 7, top);
		Color bottom = new Color(0.8, 0.8, 0.8, 0.8);
		Circle bottomCircle = new Circle(10, 10, 7, bottom);
		Label firstColor = new Label("First Color:  ");
		Label secondColor = new Label("Second Color:  ");
		firstColor.setFont(new Font("Cambria", 15));
		secondColor.setFont(new Font("Cambria", 15));

		GridPane colors = new GridPane();

		colors.addRow(0, firstColor, topCircle);
		colors.addRow(1, secondColor, bottomCircle);
		colors.setAlignment(Pos.CENTER);
		teamName.setContentDisplay(ContentDisplay.BOTTOM);
		teamName.setFont(new Font("Cambria", 30));

		StackPane lbl = new StackPane(teamName);
		lbl.setPadding(new Insets(5, 5, 5, 5));

		StackPane leftPane = new StackPane(previous);
		leftPane.setPadding(new Insets(20, 20, 20, 20));

		StackPane rightPane = new StackPane(next);
		rightPane.setPadding(new Insets(20, 20, 20, 20));

		setTop(lbl);
		setCenter(flagPic);
		setLeft(leftPane);
		setRight(rightPane);
		setBottom(colors);

		next.setOnAction(e -> {

			if (firstRun) {
				try {
					fifa.nextTeam();
				} catch (IOException e1) {
					System.out.println("Error no teams in file");
				}
				firstRun = false;
			}
			try {
				nowTeam = fifa.nextTeam();
			} catch (IOException e1) {
				System.out.println("Error no teams in file");
			}
			String name = new String(nowTeam.getName());
			teamName.setText(name.toUpperCase());
			flagPic.setImage(new Image("file:.\\res\\photos\\" + name + ".png"));
			topCircle.setFill(Color.color(Math.random(), Math.random(), Math.random()));
			bottomCircle.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		});

		previous.setOnAction(e -> {
			try {
				nowTeam = fifa.previousTeam();
				String name = new String(nowTeam.getName());
				teamName.setText(name.toUpperCase());
				flagPic.setImage(new Image("file:.\\res\\photos\\" + name + ".png"));
				topCircle.setFill(Color.color(Math.random(), Math.random(), Math.random()));
				bottomCircle.setFill(Color.color(Math.random(), Math.random(), Math.random()));

			} catch (IOException e2) {
				System.out.println("Error no teams in file");
			}
		});

	}

	public String getTeam() {
		if(nowTeam == null) {
			return "ARG";
		}else {
		return (String.valueOf(nowTeam.getName()).toUpperCase());
	}
	}
	
}
