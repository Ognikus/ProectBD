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
    private TableColumn<Games, String> CategColum;

    @FXML
    private TableColumn<Games, String> CategColum1;

    @FXML
    private TextField CategField;

    @FXML
    private TableColumn<Category, Integer> CategoryIdColumn;

    @FXML
    private TableColumn<Category, Integer> CategoryIdColumn1;

    @FXML
    private TextField CategoryIdField;

    @FXML
    private TableColumn<Games, String> CategoryNameColumn;

    @FXML
    private TableColumn<Games, String> CategoryNameColumn1;

    @FXML
    private TextField CategoryNameField;

    @FXML
    private TableView<Category> CategoryTable;

    @FXML
    private TableView<Category> CategoryTable1;

    @FXML
    private TableColumn<Games, Integer> CountColum;

    @FXML
    private TableColumn<Games, Integer> CountColum1;

    @FXML
    private TextField CountField;

    @FXML
    private TextField NameField;

    @FXML
    private TableColumn<Games, Integer> PriceColum;

    @FXML
    private TableColumn<Games, Integer> PriceColum1;

    @FXML
    private TextField PriceField;

    @FXML
    private TextField SearchCategoryField;

    @FXML
    private TextField SearchGameField;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddCategory;

    @FXML
    private Button btnAddCategoryBD;

    @FXML
    private Button btnAddGame;

    @FXML
    private Button btnCategotyPane;

    @FXML
    private Button btnDeletInfo;

    @FXML
    private Button btnDeletInfoCategoty;

    @FXML
    private Button btnExitProfile;

    @FXML
    private Button btnGamesPane;

    @FXML
    private Button btnRedactInfo;

    @FXML
    private Button btnRedactInfoCategory;

    @FXML
    private Button btnSearchGame;

    @FXML
    private Button btnSearshCatergory;

    @FXML
    private Button btnSearshGame;

    @FXML
    private TableView<Games> gamesTable;

    @FXML
    private TableView<Games> gamesTable1;

    @FXML
    private TableColumn<Games, String> nameGameColum;

    @FXML
    private TableColumn<Games, String> nameGameColum1;

    @FXML
    private Pane paneCategory;

    @FXML
    private Pane paneGames;

    @FXML
    private Pane paneSearch;

    @FXML
    private Pane paneSearshCategory;

    @FXML
    private Pane paneSearshGame;

    DataBaseHandler dbHandler = new DataBaseHandler();

    private final ObservableList<Games> dtgame = FXCollections.observableArrayList();
    private final ObservableList<Category> dtcategory = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        //Кнопки состояния панелей---------------------------------------------
        btnAddGame.setOnAction(actionEvent -> {
            paneGames.setVisible(true);
            paneCategory.setVisible(false);
            paneSearch.setVisible(false);
        });

        btnAddCategory.setOnAction(actionEvent -> {
            paneGames.setVisible(false);
            paneCategory.setVisible(true);
            paneSearch.setVisible(false);
        });

        btnSearchGame.setOnAction(actionEvent -> {
            paneGames.setVisible(false);
            paneCategory.setVisible(false);
            paneSearch.setVisible(true);
        });

        btnGamesPane.setOnAction(actionEvent -> {
            paneSearshCategory.setVisible(false);
            paneSearshGame.setVisible(true);
        });

        btnCategotyPane.setOnAction(actionEvent -> {
            paneSearshCategory.setVisible(true);
            paneSearshGame.setVisible(false);
        });
        //---------------------------------------------------------------------

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
            CategColum.setCellValueFactory(new PropertyValueFactory<>("GameCategory"));
            PriceColum.setCellValueFactory(new PropertyValueFactory<>("gamePrice"));
            CountColum.setCellValueFactory(new PropertyValueFactory<>("gameCount"));
            gamesTable.setItems(dtgame);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnRedactInfo.setOnAction(actionEvent -> {

        });

    }


    private void addInfAboutGames() throws  SQLException{
        ResultSet games = dbHandler.getGames();
        while (games.next()){
            Games game = new Games(
                    games.getString(1),
                    games.getString(2),
                    games.getString(3),
                    games.getString(4));
            dtgame.add(game);
        }
    }
}
