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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserPageController {

    @FXML
    private TableColumn<Games, String> CategColum;

    @FXML
    private TableColumn<Category, Integer> CategoryIdColumn;

    @FXML
    private TableColumn<Category, String> CategoryNameColumn;

    @FXML
    private TableView<Category> CategoryTable;

    @FXML
    private TableColumn<Games, Integer> CountColum;

    @FXML
    private TableColumn<Games, Integer> IDGameColum;

    @FXML
    private TableColumn<Games, Integer> PriceColum;

    @FXML
    private TextField SearchCategoryField;

    @FXML
    private TextField SearchGameField;

    @FXML
    private Label UserNameLabel;

    @FXML
    private Button btnCategotyPane;

    @FXML
    private Button btnExitProfile;

    @FXML
    private Button btnGamesPane;

    @FXML
    private Button btnSearchGame;

    @FXML
    private Button btnSearshCatergory;

    @FXML
    private Button btnSearshGame;

    @FXML
    private TableView<Games> gamesTable;

    @FXML
    private TableColumn<Games, String> nameGameColum;

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

        setUserNameLabel(); // Установка имени в UserNameLabel

        //Кнопки состояния панелей-----------------------------------------------------------------
        btnSearchGame.setOnAction(actionEvent -> {
            if (paneSearch.isVisible()) {
                paneSearch.setVisible(false);
            } else {
                paneSearch.setVisible(true);
            }
        });

        btnGamesPane.setOnAction(actionEvent -> {
            paneSearshGame.setVisible(true);
            paneSearshCategory.setVisible(false);
        });

        btnCategotyPane.setOnAction(actionEvent -> {
            paneSearshGame.setVisible(false);
            paneSearshCategory.setVisible(true);
        });

        btnExitProfile.setOnAction(actionEvent -> {
            LoginController lg = new LoginController();
            lg.openNewWindow((Stage) btnExitProfile.getScene().getWindow(), "login.fxml");;
        });
        //-----------------------------------------------------------------------------------------

        //Кнопки поиска----------------------------------------------------------------------------
        btnSearshGame.setOnAction(actionEvent -> {
            if(SearchGameField.getText().isEmpty()){
                dtgame.clear();
                UpdateGameTableColumn();
            }
            else {
                String gameName = SearchGameField.getText();
                searchGameByName(gameName);
            }
        });

        btnSearshCatergory.setOnAction(actionEvent -> {
            if(SearchCategoryField.getText().isEmpty()){
                dtcategory.clear();
                UpdateCategoryTableColumn();
            }
            else {
                String categoryName = SearchCategoryField.getText();
                searchCategoryByName(categoryName);
            }
        });
        //-----------------------------------------------------------------------------------------
    }
    //Методы обновления колонок--------------------------------------------------------------------
    private void UpdateGameTableColumn(){
        try {
            addInfAboutGames();
            IDGameColum.setCellValueFactory(new PropertyValueFactory<>("GameId"));
            nameGameColum.setCellValueFactory(new PropertyValueFactory<>("gameName"));
            CategColum.setCellValueFactory(new PropertyValueFactory<>("GameCategory"));
            PriceColum.setCellValueFactory(new PropertyValueFactory<>("gamePrice"));
            CountColum.setCellValueFactory(new PropertyValueFactory<>("gameCount"));
            gamesTable.setItems(dtgame);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void UpdateCategoryTableColumn(){
        try {
            addInfAboutCategory();
            CategoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryId"));
            CategoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
            CategoryTable.setItems(dtcategory);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //---------------------------------------------------------------------------------------------

    //Методы заполнения колонок--------------------------------------------------------------------
    private void addInfAboutGames() throws  SQLException{
        ResultSet games = dbHandler.getGames();
        while (games.next()){
            Games game = new Games(
                    games.getString(1),
                    games.getString(2),
                    games.getString(3),
                    games.getString(4),
                    games.getString(5));
            dtgame.add(game);
        }
    }

    private void addInfAboutCategory() throws SQLException{
        ResultSet categorys = dbHandler.getCategory();
        while (categorys.next()){
            Category category = new Category(
                    categorys.getString(1),
                    categorys.getString(2));
            dtcategory.add(category);
        }
    }
    //---------------------------------------------------------------------------------------------

    //Методы поиска по слову-----------------------------------------------------------------------
    private void searchGameByName(String gameName) {
        try {
            // Очищаем список игр перед выполнением поиска
            dtgame.clear();
            ResultSet games1 = dbHandler.getGamesSearch(gameName);

            // Получаем результаты запроса и добавляем их в список игр
            while (games1.next()) {
                Games game = new Games(
                        games1.getString(1),
                        games1.getString(2),
                        games1.getString(3),
                        games1.getString(4),
                        games1.getString(5)
                );
                dtgame.add(game);
            }

            // Обновляем таблицу с найденными играми
            gamesTable.setItems(dtgame);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска в базе данных", e);
        }
    }

    private void searchCategoryByName(String categoryName) {
        try {
            // Очищаем список игр перед выполнением поиска
            dtcategory.clear();
            ResultSet category1 = dbHandler.getCategorySearch(categoryName);

            // Получаем результаты запроса и добавляем их в список игр
            while (category1.next()) {
                Category category = new Category(
                        category1.getString(1),
                        category1.getString(2)
                );
                dtcategory.add(category);
            }

            // Обновляем таблицу с найденными играми
            CategoryTable.setItems(dtcategory);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска в базе данных", e);
        }
    }
    //---------------------------------------------------------------------------------------------

    //Установка имени пользователя-----------------------------------------------------------------
    private void setUserNameLabel() {
        UserNameLabel.setText(LoginController.UserName.getUserName());
    }
    //---------------------------------------------------------------------------------------------


}
