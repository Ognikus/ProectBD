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

        VoityBTN.setOnAction(actionEvent -> {
            String loginText = LoginField.getText().trim();
            String passText = PasswordField.getText().trim();

            if (!loginText.equals("") && !passText.equals("")) {
                if (loginText.equals("admin") && passText.equals("admin")) {
                    openNewWindow((Stage) RegistBTN.getScene().getWindow(), "hello-view.fxml");
                } else {
                    try {
                        if (checkUser(loginText, passText)) {
                           loginUser(loginText, passText);
                        } else {
                            System.out.println("Неверный логин или пароль");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                System.out.println("Логин или пароль пусты!");
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
        boolean userExists = checkUser(loginText, loginPassword);
        User user = new User(loginText, loginPassword);
        user.setName(loginText);
        UserName.setUserName(loginText);

        if (userExists) {
            openNewWindow((Stage) RegistBTN.getScene().getWindow(), "UserPage.fxml");
        } else {
            System.out.println("Неверный логин или пароль");
        }
    }


    public boolean checkUser(String loginText, String passText) throws SQLException {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getUser(loginText, passText);
        return resultSet.next(); // Если результат содержит хотя бы одну строку, значит пользователь существует
    }


    public void openNewWindow(Stage currentStage, String window) {
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

            // Закрытие текущего окна
            currentStage.close();

            // Показать новое окно
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class UserName {
        private static String userName;

        public static String getUserName() {
            return userName;
        }

        public static void setUserName(String name) {
            userName = name;
        }
    }


}
