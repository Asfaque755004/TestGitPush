package com.desktop.MoSchoolApp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private double x=0;
    private double y=0;
    @Override
    public void start(Stage stage) throws IOException {
    	Parent root=FXMLLoader.load(getClass().getResource("login/login.fxml"));
    	
    	root.setOnMousePressed( event->{
    		x=event.getSceneX();
    		y=event.getSceneY();    		
    	});
    	
    	root.setOnMouseDragged(event->{
    		stage.setX(event.getSceneX()-x);
    		stage.setY(event.getSceneY()-y);
    		stage.setOpacity(.8);
    	});
    	
    	root.setOnMouseReleased(event->{
    		stage.setOpacity(1);
    	});
    	
    	stage.initStyle(StageStyle.TRANSPARENT);   
       
        scene=new Scene(root);
        Image icon=new Image("/images/sms.jpg");
        stage.getIcons().add(icon);    
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}