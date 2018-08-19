package Ex1;

import Ex1.Ex1Backend.FifaWorldCupData;
import Ex1.Ex1Backend.FifaWorldCupFileManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GeneratingTeamsUsingPhotos extends Application{

	final static int WIDTH = 1200;
	final static int HEIGHT = 600;
	
	public static void main(String[] args) throws IOException {

		
		
		FifaWorldCupData fifa = new FifaWorldCupFileManager(".\\res\\teams.bin", ".\\res\\games.bin");

		if (fifa.getNumOfTeams() == 0) {

			File dir = new File(".\\res\\photos");
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) { // checks if the file is empty
				for (File child : directoryListing) {
					fifa.saveTeam(new Team(child.getName().replaceAll(".png", "").toCharArray(),
							Color.color(Math.random(), Math.random(), Math.random()),
							Color.color(Math.random(), Math.random(), Math.random())));
				}
			}
		}

		launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		MainLayout theMainLayout = new MainLayout();
		FifaWorldCupData fifa= new FifaWorldCupFileManager(".\\res\\teams.bin", ".\\res\\games.bin");
		Scene scene = new Scene(theMainLayout,WIDTH , HEIGHT);
		stage.setOnCloseRequest(e -> {
			try {
				((FifaWorldCupFileManager) fifa).close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.setScene(scene);
		stage.show();
				
	}
}