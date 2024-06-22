package com.festivalcompany;

import com.festivalcompany.service.AuthService;
import com.festivalcompany.view.CompanyListView;
import com.festivalcompany.view.CompanyDetailsView;
import com.festivalcompany.service.FirestoreService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class FestivalCompanyManagementApp extends Application {

    private CompanyListView companyListView;
    private CompanyDetailsView companyDetailsView;
    private FirestoreService firestoreService;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Festival Company Management");

        // Create login form
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        TextField username = new TextField();
        username.setPromptText("Username");
        GridPane.setConstraints(username, 0, 0);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        GridPane.setConstraints(password, 0, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 1);

        grid.getChildren().addAll(username, password, loginButton);

        Scene loginScene = new Scene(grid, 300, 200);

        loginButton.setOnAction(e -> {
            if (AuthService.authenticate(username.getText(), password.getText())) {
                launchMainApplication(primaryStage);
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void launchMainApplication(Stage primaryStage) {
        firestoreService = new FirestoreService();
        companyListView = new CompanyListView(firestoreService);
        companyDetailsView = new CompanyDetailsView(firestoreService);

        BorderPane root = new BorderPane();
        root.setLeft(companyListView);
        root.setCenter(companyDetailsView);

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Festival Company Management - Main Application");

        companyListView.setOnCompanySelected(company -> companyDetailsView.displayCompany(company));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}