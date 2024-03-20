package com.desktop.MoSchoolApp.dashboard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.desktop.MoSchoolApp.database.CourseData;
import com.desktop.MoSchoolApp.database.DBConnection;
import com.desktop.MoSchoolApp.database.StudentData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dashboardController implements Initializable {

	@FXML
	private Button addStudent_addBtn;

	@FXML
	private DatePicker addStudent_birthDate;

	@FXML
	private Button addStudent_clearBtn;

	@FXML
	private TableView<StudentData> addStudent_tableView;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_birthDate;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_course;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_firstName;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_gender;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_lastName;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_status;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_student;

	@FXML
	private TableColumn<StudentData, String> addStudent_col_year;

	@FXML
	private ComboBox<String> addStudent_course;

	@FXML
	private Button addStudent_deleteBtn;

	@FXML
	private TextField addStudent_firstName;

	@FXML
	private AnchorPane addStudent_form;

	@FXML
	private ComboBox<String> addStudent_gender;

	@FXML
	private ImageView addStudent_imageView;

	@FXML
	private TextField addStudent_lastName;

	@FXML
	private TextField addStudent_search;

	@FXML
	private ComboBox<String> addStudent_status;

	@FXML
	private TextField addStudent_student;

	@FXML
	private Button addStudent_updateBtn;

	@FXML
	private Button addStudent_uploadBtn;

	@FXML
	private ComboBox<String> addStudent_year;

	@FXML
	private Button addStudents_btn;

	@FXML
	private Button avlCourses_btn;

	@FXML
	private Button close;

	@FXML
	private Button course_addBtn;

	@FXML
	private Button course_clearBtn;

	@FXML
	private TableView<CourseData> course_tableView;

	@FXML
	private TableColumn<CourseData, String> course_col_course;

	@FXML
	private TableColumn<CourseData, String> course_col_degree;

	@FXML
	private TableColumn<CourseData, String> course_col_desc;

	@FXML
	private TextField course_course;

	@FXML
	private TextField course_degree;

	@FXML
	private Button course_deleteBtn;

	@FXML
	private TextField course_desc;

	@FXML
	private AnchorPane course_form;

	@FXML
	private Button course_updateBtn;

	@FXML
	private TextField gradeSearch;

	@FXML
	private TableView<?> gradeTableView;

	@FXML
	private TableColumn<?, ?> gradeTableView_col_course;

	@FXML
	private TableColumn<?, ?> gradeTableView_col_final;

	@FXML
	private TableColumn<?, ?> gradeTableView_col_firstSem;

	@FXML
	private TableColumn<?, ?> gradeTableView_col_secondSem;

	@FXML
	private TableColumn<?, ?> gradeTableView_col_student;

	@FXML
	private TableColumn<?, ?> gradeTableView_col_year;

	@FXML
	private Button grade_clearBtn;

	@FXML
	private Label grade_course;

	@FXML
	private TextField grade_firstSem;

	@FXML
	private AnchorPane grade_form;

	@FXML
	private TextField grade_secondSem;

	@FXML
	private TextField grade_student;

	@FXML
	private Button grade_updateBtn;

	@FXML
	private Label grade_year;

	@FXML
	private Button home_btn;

	@FXML
	private AnchorPane home_form;

	@FXML
	private Label home_totalEnrolled;

	@FXML
	private BarChart<?, ?> home_totalEnrolledChart;

	@FXML
	private Label home_totalFemale;

	@FXML
	private AreaChart<?, ?> home_totalFemaleChart;

	@FXML
	private Label home_totalMale;

	@FXML
	private LineChart<?, ?> home_totalMaleChart;

	@FXML
	private Button logout;

	@FXML
	private AnchorPane main_form;

	@FXML
	private Button maximize;

	@FXML
	private Button minimize;

	@FXML
	private Button studentsGrade_btn;

	@FXML
	private Label username;

	private double x;
	private double y;

	private Connection connection;
	private PreparedStatement ps;
	private ResultSet rs;
	private ObservableList<StudentData> studentListData;
	private ObservableList<CourseData> courseListData;

	private String[] years = { "First Year", "Second Year", "Third Year", "Fourth Year" };

	public Optional<ButtonType> showAlert(AlertType type, String title, String msg) {

		Alert alert;
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		return alert.showAndWait();

	}

	public void addStudentsUploadImg() {

		FileChooser open = new FileChooser();
		open.setTitle("Open image file");
		open.getExtensionFilters().add(new ExtensionFilter("Image filter", "*jpg", "*png"));

		File file = open.showOpenDialog(main_form.getScene().getWindow());

		if (file != null) {

			Image img = new Image(file.toString(), 120.0, 149.0, false, true);

			addStudent_imageView.setImage(img);

		}
	}

	public ObservableList<StudentData> addStudentGetDBDataList() {

		StudentData studentData;
		ObservableList<StudentData> observableStudentList = FXCollections.observableArrayList();

		String query = "SELECT * from student";
		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				studentData = new StudentData(rs.getInt("studentNum"), rs.getString("year"), rs.getString("course"),
						rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
						rs.getDate("birth"), rs.getString("status"), rs.getString("image"));

				observableStudentList.add(studentData);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return observableStudentList;
	}

	public void addStudentsDisplayData() {

		studentListData = addStudentGetDBDataList();

		addStudent_col_student.setCellValueFactory(new PropertyValueFactory<>("studentNum"));
		addStudent_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
		addStudent_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
		addStudent_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		addStudent_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		addStudent_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		addStudent_col_birthDate.setCellValueFactory(new PropertyValueFactory<>("birth"));
		addStudent_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

		addStudent_tableView.setItems(studentListData);

	}

	public void showSelectedStudent() {

		StudentData studentData = addStudent_tableView.getSelectionModel().getSelectedItem();
		int index = addStudent_tableView.getSelectionModel().getSelectedIndex();

		if (index - 1 < -1)
			return;

		addStudent_student.setText(String.valueOf(studentData.getStudentNum()));
		addStudent_firstName.setText(studentData.getFirstName());
		addStudent_lastName.setText(studentData.getLastName());

		String uri = "file:" + studentData.getImage();
		Image image = new Image(uri, 120., 149., false, true);
		addStudent_imageView.setImage(image);
	}

	public void addStudentsYearList() {
		List<String> yearList = Stream.of(years).collect(Collectors.toList());
		ObservableList<String> obsYearList = FXCollections.observableArrayList(yearList);
		addStudent_year.setItems(obsYearList);
	}

	public void addStudentsGenderList() {

		// List<String> yearList = Stream.of(years).collect(Collectors.toList());
		ObservableList<String> obsYearList = FXCollections.observableArrayList("Male", "Female");
		addStudent_gender.setItems(obsYearList);

	}

	public void addStudentsStatusList() {

		// List<String> yearList = Stream.of(years).collect(Collectors.toList());
		ObservableList<String> obsYearList = FXCollections.observableArrayList("Enrolled", "Not Enrolled", "Inactive");
		addStudent_status.setItems(obsYearList);

	}

	public void addStudentsCourseList() {

		String courseListQuery = "SELECT course from course";
		ObservableList<String> obsCourseList = FXCollections.observableArrayList();
		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}
		try {
			ps = connection.prepareStatement(courseListQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				obsCourseList.add(rs.getString("course"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addStudent_course.setItems(obsCourseList);

	}

	// available course page
	public ObservableList<CourseData> avlCoursesGetDBDataList() {

		CourseData courseData;
		ObservableList<CourseData> observableCourseList = FXCollections.observableArrayList();

		String query = "SELECT * from course";
		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				courseData = new CourseData(rs.getString("course"), rs.getString("description"),
						rs.getString("degree"));

				observableCourseList.add(courseData);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return observableCourseList;
	}

	public void avlCourseDisplayData() {

		courseListData = avlCoursesGetDBDataList();

		course_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
		course_col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
		course_col_degree.setCellValueFactory(new PropertyValueFactory<>("degree"));

		course_tableView.setItems(courseListData);

	}

	public void showSelectedCourse() {

		CourseData courseData = course_tableView.getSelectionModel().getSelectedItem();
		int index = course_tableView.getSelectionModel().getSelectedIndex();

		if (index - 1 < -1)
			return;

		course_course.setText(courseData.getCourse());
		course_desc.setText(courseData.getDescription());
		course_degree.setText(courseData.getDegree());

	}

	public void addCourseToDB() {

		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}

		String course = course_course.getText();
		String desc = course_desc.getText();
		String degree = course_degree.getText();

		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		courseMap.put(1, course);
		courseMap.put(2, desc);
		courseMap.put(3, degree);

		String insertQuery = "INSERT into course (course,description,degree) values (?,?,?)";

		if (course.isEmpty() || desc.isEmpty() || degree.isEmpty()) {
			showAlert(AlertType.ERROR, "Error Message", "Please fill the blank field");

		} else {

			// checking if course to be inserted is already exist
			String courseChek = "SELECT * from course where course=?";

			rs = executeSelectQuery(courseChek, course);

			if (checkDataDB(courseChek, course)) {
				showAlert(AlertType.ERROR, "Error Message", "Course: " + course + " is already exist!!!");
			} else {

				executeDMLQuery(insertQuery, courseMap);

				showAlert(AlertType.INFORMATION, "Information Message", "Course: " + course + " succesfully added!!!");

				// to immediely show the data
				avlCourseDisplayData();
				// to clear the data from field
				clearCourse();

			}

		}

	}

	public boolean checkDataDB(String query, String course) {

		rs = executeSelectQuery(query, course);

		try {
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public void clearCourse() {

		course_course.setText("");
		course_desc.setText("");
		course_degree.setText("");
	}

	public void updateCourse() {

		PreparedStatement ps;
		ResultSet rs;

		String course = course_course.getText();
		String desc = course_desc.getText();
		String degree = course_degree.getText();

		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}

		String updateQuery = "UPDATE course set description='" + desc + "' " + ", degree='" + degree
				+ "' where course='" + course + "'";

		if (course.isEmpty() || desc.isEmpty() || degree.isEmpty()) {

			showAlert(AlertType.ERROR, "Error Message", "Please fill the blank field");

		} else {
			try {

				Optional<ButtonType> showAlert = showAlert(AlertType.CONFIRMATION, "Confirmation Message",
						"Do you want to update?");

				if (showAlert.get() == ButtonType.OK) {

					ps = connection.prepareStatement(updateQuery);
					ps.executeUpdate();

					// to display data immediatly
					avlCourseDisplayData();
					clearCourse();

					showAlert(AlertType.INFORMATION, "Information Message",
							"Course: " + course_course.getText() + " succesfully update!!!");

				} else {
					return;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void deleteCourse() {

		String course = course_course.getText();
		String desc = course_desc.getText();
		String degree = course_degree.getText();

		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}

		String deleteCourse = "DELETE FROM course where course='" + course + "'";

		if (course.isEmpty() || desc.isEmpty() || degree.isEmpty()) {

			showAlert(AlertType.ERROR, "Error Message", "Please fill the blank field");

		} else {
			try {

				Optional<ButtonType> showAlert = showAlert(AlertType.CONFIRMATION, "Confirmation Message",
						"Do you want to DELETE?");

				if (showAlert.get() == ButtonType.OK) {

					ps = connection.prepareStatement(deleteCourse);
					ps.executeUpdate();

					// to display data immediatly
					avlCourseDisplayData();
					clearCourse();

					showAlert(AlertType.INFORMATION, "Information Message",
							"Course: " + course_course.getText() + " succesfully deleted!!!");

				} else {
					return;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public ResultSet executeSelectQuery(String query, String param) {

		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}

		try {
			ps = connection.prepareStatement(query);

			if (param != null) {
				ps.setString(1, param);
			}

			rs = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}

	public void executeDMLQuery(String query, Map<Integer, String> parameters) {

		if (connection == null) {
			connection = DBConnection.getDBConnection();
		}

		try {
			ps = connection.prepareStatement(query);

			if (parameters != null) {
				parameters.forEach((key, value) -> {
					try {

						ps.setString(key, value);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});
			}

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void logout() {

		try {

			Optional<ButtonType> btnAlert = showAlert(AlertType.CONFIRMATION, "Confirmation Message",
					"Do you want to logout?");
			if (btnAlert.get() == ButtonType.OK) {

				// hide dashboard page before going to login page

				logout.getScene().getWindow().hide();
				// link the login page
				Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));

				Stage stage = new Stage();
				Scene scene = new Scene(root);

				root.setOnMousePressed(event -> {

					x = event.getSceneX();
					y = event.getSceneY();

				});

				root.setOnMouseDragged(event -> {
					stage.setX(event.getScreenX() - x);
					stage.setY(event.getScreenY() - y);
					stage.setOpacity(0.8);
				});

				root.setOnMouseReleased(event -> {
					stage.setOpacity(1);
				});

				stage.initStyle(StageStyle.TRANSPARENT);

				stage.setScene(scene);
				stage.show();

			} else
				return;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void close() {
		System.exit(0);
		// javafx.application.Platform.exit();

	}

	public void minimize() {
		Stage stage = (Stage) main_form.getScene().getWindow();
		stage.setIconified(true);

	}

	public void maximize() {
		Stage stage = (Stage) main_form.getScene().getWindow();
		if (stage.isMaximized()) {
			stage.setMaximized(false);
		} else {
			stage.setMaximized(true);
		}

	}

	public void switchForm(ActionEvent event) {

		if (event.getSource() == home_btn) {
			home_form.setVisible(true);
			addStudent_form.setVisible(false);
			course_form.setVisible(false);
			grade_form.setVisible(false);

			home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#3f82ae,#26bf7d)");
			addStudents_btn.setStyle("-fx-background-color:TRANSPARENT");
			avlCourses_btn.setStyle("-fx-background-color:TRANSPARENT");
			studentsGrade_btn.setStyle("-fx-background-color:TRANSPARENT");

		} else if (event.getSource() == addStudents_btn) {
			home_form.setVisible(false);
			addStudent_form.setVisible(true);
			course_form.setVisible(false);
			grade_form.setVisible(false);

			addStudents_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#3f82ae,#26bf7d)");
			home_btn.setStyle("-fx-background-color:TRANSPARENT");
			avlCourses_btn.setStyle("-fx-background-color:TRANSPARENT");
			studentsGrade_btn.setStyle("-fx-background-color:TRANSPARENT");

			// to automatically populate the student list when add button is clicked

			addStudentsDisplayData();
			addStudentsYearList();
			addStudentsGenderList();
			addStudentsStatusList();
			addStudentsCourseList();

		} else if (event.getSource() == avlCourses_btn) {
			home_form.setVisible(false);
			addStudent_form.setVisible(false);
			course_form.setVisible(true);
			grade_form.setVisible(false);

			avlCourses_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#3f82ae,#26bf7d)");
			addStudents_btn.setStyle("-fx-background-color:TRANSPARENT");
			home_btn.setStyle("-fx-background-color:TRANSPARENT");
			studentsGrade_btn.setStyle("-fx-background-color:TRANSPARENT");

			avlCourseDisplayData();

		} else if (event.getSource() == studentsGrade_btn) {
			home_form.setVisible(false);
			addStudent_form.setVisible(false);
			course_form.setVisible(false);
			grade_form.setVisible(true);

			studentsGrade_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#3f82ae,#26bf7d)");
			addStudents_btn.setStyle("-fx-background-color:TRANSPARENT");
			avlCourses_btn.setStyle("-fx-background-color:TRANSPARENT");
			home_btn.setStyle("-fx-background-color:TRANSPARENT");

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		addStudentsDisplayData();
		addStudentsYearList();
		addStudentsGenderList();
		addStudentsStatusList();
		addStudentsCourseList();

		avlCourseDisplayData();

	}

}
