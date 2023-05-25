package com.example.testbd;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginField;

    @FXML
    private TextField PasswordField;

    @FXML
    private Button RegistBTN;

    @FXML
    private Button VoityBTN;
    DataBaseHandler dbHandler = new DataBaseHandler();

    @FXML
    void initialize() {
        assert LoginField != null : "fx:id=\"LoginField\" was not injected: check your FXML file 'login.fxml'.";
        assert PasswordField != null : "fx:id=\"PasswordField\" was not injected: check your FXML file 'login.fxml'.";
        assert RegistBTN != null : "fx:id=\"RegistBTN\" was not injected: check your FXML file 'login.fxml'.";
        assert VoityBTN != null : "fx:id=\"VoityBTN\" was not injected: check your FXML file 'login.fxml'.";

        VoityBTN.setOnAction(actionEvent -> {
            String loginText = LoginField.getText().trim();
            String passText = PasswordField.getText().trim();

            if (!loginText.equals("") && !passText.equals("")) {
                if (loginText.equals("admin") && passText.equals("admin")) {
                    openNewWindow("hello-view.fxml");
                } else {
                    try {
                        if (checkUser(loginText, passText)) {
                           loginUser(loginText, passText);
                        } else {
                            System.out.println("Invalid login or password");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                System.out.println("Login and password are empty!");
            }
        });

        RegistBTN.setOnAction(actionEvent -> {
            DataBaseHandler dbHandler = new DataBaseHandler();;
            String userName = LoginField.getText();
            String password = PasswordField.getText();

            User user = new User(userName, password);
            try {
                dbHandler.insertUser(user);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            }
        });
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException {
        DataBaseHandler dbHandler = new DataBaseHandler();
        User user = new User(loginText, loginPassword);
        user.setName(loginText);
        user.setPassword(loginPassword);
        boolean userInserted = dbHandler.insertUser(user);

        if (userInserted) {
            openNewWindow("UserPage.fxml");
        }
    }

    public boolean checkUser(String loginText, String passText) throws SQLException {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getUser(loginText, passText);
        return resultSet.next(); // Если результат содержит хотя бы одну строку, значит пользователь существует
    }


    public void openNewWindow(String window) {
        try {
            // Загрузка файла FXML для нового окна
            FXMLLoader loader = new FXMLLoader(getClass().getResource(window));
            Parent root = loader.load();

            // Создание новой сцены и установка корневого узла
            Scene scene = new Scene(root);

            // Создание нового окна
            Stage stage = new Stage();
            stage.setTitle("Название нового окна");
            stage.setScene(scene);

            // Показать новое окно
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
