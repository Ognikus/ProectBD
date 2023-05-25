package com.example.testbd;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Games, String> CategColum;

    @FXML
    private TableColumn<Games, Integer> CountColum;

    @FXML
    private TableColumn<Games, String> NameColum;

    @FXML
    private TableColumn<Games, String> PriceColum;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Games> gameTable;

    @FXML
    private Label lbUserName;

    DataBaseHandler dbHandler = new DataBaseHandler();
    private final ObservableList<Games> dtgame = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        assert CategColum != null : "fx:id=\"CategColum\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert CountColum != null : "fx:id=\"CountColum\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert NameColum != null : "fx:id=\"NameColum\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert PriceColum != null : "fx:id=\"PriceColum\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert gameTable != null : "fx:id=\"gameTable\" was not injected: check your FXML file 'UserPage.fxml'.";
        assert lbUserName != null : "fx:id=\"lbUserName\" was not injected: check your FXML file 'UserPage.fxml'.";
        try {
            String name = String.valueOf(dbHandler.getUserName());
            lbUserName.setText(name);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        btnUpdate.setOnAction(actionEvent -> {
            try {
                gameTable.getItems().clear(); // Очистка текущих значений таблицы
                addInfAboutGames();
                NameColum.setCellValueFactory(new PropertyValueFactory<>("gameName"));
                CategColum.setCellValueFactory(new PropertyValueFactory<>("gameCategory"));
                PriceColum.setCellValueFactory(new PropertyValueFactory<>("gamePrice"));
                CountColum.setCellValueFactory(new PropertyValueFactory<>("gameCount"));
                gameTable.setItems(dtgame);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void addInfAboutGames() throws SQLException {
        ResultSet games = dbHandler.getGames();
        while (games.next()){
            Games game = new Games(
                    games.getString(2),
                    games.getString(3),
                    games.getString(4),
                    games.getString(5));
            dtgame.add(game);
        }
    }

}
