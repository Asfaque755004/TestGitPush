package com.desktop.MoSchoolApp.login;

import java.io.IOException;

import com.desktop.MoSchoolApp.database.DBConnection;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController  {
	
    @FXML
    private FontAwesomeIcon login_close;	
		
	@FXML
    private Button login_btn;

	 @FXML
	 private Button login_close_btn;

    @FXML
    private AnchorPane login_main_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;
    
    private double x;
    private double y;

    public void close() {
    	System.exit(0);
    }
    
    public void setBgCloseIcon() {
    	
    	Tooltip tt=new Tooltip("Close");
    	    	
    	login_close_btn.setTooltip(tt);
    	
    }
    
    public void remBgCloseIcon() {
    	
    	login_close_btn.setStyle("-fx-background-color:#fff;");
    	
    }
    
	public void loginAdmin() {
		
		String uname=login_username.getText();
		String upwd=login_password.getText();
		
		//checking if login form fields are empty
		if(uname.isEmpty() || upwd.isEmpty()) {
			
			showAlert(AlertType.ERROR, "Error Message", "Username or Password is not filled.Please enter both");
			
		}else if (DBConnection.checkAuthentication(uname, upwd)) {
			
			//Authentication is successful, show the dashboard to the admin user
			showAlert(AlertType.INFORMATION, "Information Message", "Successfully Loggedin!");
			
			//To hide the login form
			login_btn.getScene().getWindow().hide();
			
			//Link to DASHbOARD
			try {
				Parent root=FXMLLoader.load(getClass().getResource("/com/desktop/MoSchoolApp/dashboard/Dashboard.fxml"));
				
				Stage stage=new Stage();
				
				root.setOnMousePressed( event ->{
					
					x=event.getSceneX();
					y=event.getSceneY();
					
				});
				
				root.setOnMouseDragged( event ->{
					stage.setX(event.getScreenX()-x);
					stage.setY(event.getScreenY()-y);
					stage.setOpacity(0.8);
				});
				
				root.setOnMouseReleased(event->{
					stage.setOpacity(1);
				});
				
				stage.initStyle(StageStyle.TRANSPARENT);
				
				Scene scene=new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			//show the wrong username/pwd entered 
			showAlert(AlertType.ERROR, "Error Message", "Wrong Username or Password.Please enter correct");
		}		
		
	}	
	
	public void showAlert(AlertType type,String title,String msg) {
		
		Alert alert;		
		alert=new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
		
	}	

}
