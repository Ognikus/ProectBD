package com.example.testbd;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class HelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Games, String> CategColum;

    @FXML
    private TextField CategField;

    @FXML
    private TableColumn<Games, Integer> CountColum;

    @FXML
    private TextField CountField;


    @FXML
    private TableColumn<User, String> nameColum;

    @FXML
    private TextField NameField;

    @FXML
    private Pane PaneUsers;

    @FXML
    private TableColumn<Games, Integer> PriceColum;

    @FXML
    private TextField PriceField;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnGames;

    @FXML
    private Button btnUsers;

    @FXML
    private TableColumn<Games, String> nameGameColum;

    @FXML
    private Pane paneGames;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableView<Games> gamesTable;

    DataBaseHandler dbHandler = new DataBaseHandler();
    private final ObservableList<User> data = FXCollections.observableArrayList();
    private final ObservableList<Games> dtgame = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        btnAdd.setOnAction(actionEvent -> {
            Games games = new Games(NameField.getText(), CategField.getText(), PriceField.getText(), CountField.getText());
            dtgame.add(games);
            try {
                dbHandler.insertGames(games);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при добавлении данных в список", e);
            }
        });
        try {
            addInfAboutGames();
            nameGameColum.setCellValueFactory(new PropertyValueFactory<>("gameName"));
            CategColum.setCellValueFactory(new PropertyValueFactory<>("gameCategory"));
            PriceColum.setCellValueFactory(new PropertyValueFactory<>("gamePrice"));
            CountColum.setCellValueFactory(new PropertyValueFactory<>("gameCount"));
            gamesTable.setItems(dtgame);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void addInfAboutGames() throws  SQLException{
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
