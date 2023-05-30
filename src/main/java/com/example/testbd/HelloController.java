package com.example.testbd;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private TableColumn<Games, String> CategColum;

    @FXML
    private TableColumn<Games, String> CategColum1;

    @FXML
    private TextField CategField;

    @FXML
    private TextField CategoryIDLabel;

    @FXML
    private TableColumn<Category, Integer> CategoryIdColumn;

    @FXML
    private TableColumn<Category, Integer> CategoryIdColumn1;

    @FXML
    private TextField CategoryIdField;

    @FXML
    private TableColumn<Category, String> CategoryNameColumn;

    @FXML
    private TableColumn<Category, String> CategoryNameColumn1;

    @FXML
    private TextField CategoryNameField;

    @FXML
    private TableView<Category> CategoryTable;

    @FXML
    private TableView<Category> CategoryTable1;

    @FXML
    private TableColumn<Games, String> CountColum;

    @FXML
    private TableColumn<Games, String> CountColum1;

    @FXML
    private TextField CountField;

    @FXML
    private TextField GamesIDLabel;

    @FXML
    private TableColumn<Games, Integer> IDGameColum;

    @FXML
    private TableColumn<Games, Integer> IDGameColum1;

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
    private Button btnSearshGameTB;

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
    private final ObservableList<Games> dtgame1 = FXCollections.observableArrayList();
    private final ObservableList<Category> dtcategory = FXCollections.observableArrayList();
    private final ObservableList<Category> dtcategory1 = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        //Вызываемые функции---------------------------------------------------
        UpdateGameTableColumn();
        UpdateCategoryTableColumn();
        //---------------------------------------------------------------------

        gamesTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Games selectedGame = gamesTable.getSelectionModel().getSelectedItem();
                if (selectedGame != null) {
                    // Здесь вы можете установить значения выбранной игры в текстовые поля
                    GamesIDLabel.setText(String.valueOf((selectedGame.getGameId())));
                    NameField.setText(selectedGame.getGameName());
                    CategField.setText(selectedGame.getGameCategory());
                    PriceField.setText(String.valueOf(selectedGame.getGamePrice()));
                    CountField.setText(String.valueOf(selectedGame.getGameCount()));
                }
            }
        });

        CategoryTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Category selectedCategory = CategoryTable.getSelectionModel().getSelectedItem();
                if (selectedCategory != null) {
                    // Здесь вы можете установить значения выбранной игры в текстовые поля
                    CategoryIDLabel.setText(String.valueOf((selectedCategory.getCategoryId())));
                    CategoryIdField.setText(String.valueOf((selectedCategory.getCategoryId())));
                    CategoryNameField.setText(selectedCategory.getCategoryName());
                }
            }
        });

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

        btnExitProfile.setOnAction(actionEvent -> {
            LoginController lg = new LoginController();
            lg.openNewWindow((Stage) btnExitProfile.getScene().getWindow(), "login.fxml");;

        });
        //---------------------------------------------------------------------

        //Кнопки с играми------------------------------------------------------
        btnAdd.setOnAction(actionEvent -> {
            Games games = new Games(" ",NameField.getText(), CategField.getText(), PriceField.getText(), CountField.getText());
            dtgame.clear();
            try {
                dbHandler.insertGames(games);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при добавлении данных в список", e);
            }
            UpdateGameTableColumn();
        });

        btnSearshGameTB.setOnAction(actionEvent -> {
            if (SearchGameField.getText().isEmpty()) {
                dtgame1.clear();
                UpdateGameTableColumn1();
            } else {
                String gameName = SearchGameField.getText();
                searchGameByName(gameName);
            }
        });

        btnRedactInfo.setOnAction(actionEvent -> {
            Games selectedGame = gamesTable.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                // Получите обновленные значения из текстовых полей
                String id = GamesIDLabel.getText();
                String updatedName = NameField.getText();
                String updatedCategory = CategField.getText();
                String updatedPrice = PriceField.getText();
                String updatedCount = CountField.getText();

                // Обновите выбранную игру
                selectedGame.setGameId(Integer.valueOf(id));
                selectedGame.setGameName(updatedName);
                selectedGame.setGameCategory(updatedCategory);
                selectedGame.setGamePrice(Integer.valueOf(updatedPrice));
                selectedGame.setGameCount(Integer.valueOf(updatedCount));


                // Здесь вызовите метод для обновления игры в базе данных
                try {
                    dbHandler.updateGames(selectedGame);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при обновлении данных в базе данных", e);
                }

                // Очистите текстовые поля после обновления
                dtgame.clear();
                GamesIDLabel.clear();
                NameField.clear();
                CategField.clear();
                PriceField.clear();
                CountField.clear();
                UpdateGameTableColumn();
            } else {
                System.out.println("Игра не найдена");
            }

        });

        btnDeletInfo.setOnAction(actionEvent -> {
            try {
                int gameIdToDelete = Integer.parseInt(GamesIDLabel.getText()); // Идентификатор игры, которую нужно удалить
                dbHandler.deleteGame(gameIdToDelete);
                System.out.println("Запись успешно удалена");
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при удалении записи из базы данных", e);
            }
            dtgame.clear();
            GamesIDLabel.clear();
            NameField.clear();
            CategField.clear();
            PriceField.clear();
            CountField.clear();
            UpdateGameTableColumn();
        });
        //---------------------------------------------------------------------

        //Кнопки с категориями-------------------------------------------------
        btnAddCategoryBD.setOnAction(actionEvent -> {
            Category category = new Category(CategoryIdField.getText(), CategoryNameField.getText());
            dtcategory.clear();
            try {
                dbHandler.insertCategory(category);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при добавлении данных в список", e);
            }
            UpdateCategoryTableColumn();
        });

        btnRedactInfoCategory.setOnAction(actionEvent -> {
            Category selectedCategory = CategoryTable.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                // Получите обновленные значения из текстовых полей
                String id = CategoryIDLabel.getText();
                String updateId = CategoryIdField.getText();
                String updatedName = CategoryNameField.getText();

                // Обновите выбранную игру
                selectedCategory.setCategoryId(Integer.valueOf(updateId));
                selectedCategory.setCategoryName(updatedName);



                // Здесь вызовите метод для обновления игры в базе данных
                try {
                    dbHandler.updateCategory(selectedCategory);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при обновлении данных в базе данных", e);
                }

                // Очистите текстовые поля после обновления
                dtcategory.clear();
                CategoryIDLabel.clear();
                CategoryIdField.clear();
                CategoryNameField.clear();
                UpdateCategoryTableColumn();
            } else {
                System.out.println("Категория не найдена");
            }
        });

        btnDeletInfoCategoty.setOnAction(actionEvent -> {
            try {
                int catIdToDelete = Integer.parseInt(CategoryIDLabel.getText()); // Идентификатор категории, которую нужно удалить
                dbHandler.deleteCategory(catIdToDelete);
                System.out.println("Запись успешно удалена");
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при удалении записи из базы данных", e);
            }
            dtcategory.clear();
            CategoryIDLabel.clear();
            CategoryIdField.clear();
            CategoryNameField.clear();
            UpdateCategoryTableColumn();
        });

        btnSearshCatergory.setOnAction(actionEvent -> {
            if(SearchCategoryField.getText().isEmpty()){
                dtcategory1.clear();
                UpdateCategoryTableColumn1();
            }
            else {
                String categoryName = SearchCategoryField.getText();
                searchCategoryByName(categoryName);
            }
        });
        //---------------------------------------------------------------------
    }

    private void searchCategoryByName(String categoryName) {
        try {
            // Очищаем список игр перед выполнением поиска
            dtcategory1.clear();
            ResultSet category1 = dbHandler.getCategorySearch(categoryName);

            // Получаем результаты запроса и добавляем их в список игр
            while (category1.next()) {
                Category category = new Category(
                        category1.getString(1),
                        category1.getString(2)
                );
                dtcategory1.add(category);
            }

            // Обновляем таблицу с найденными играми
            CategoryTable1.setItems(dtcategory1);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска в базе данных", e);
        }
    }

    private void searchGameByName(String gameName) {
        try {
            // Очищаем список игр перед выполнением поиска
            dtgame1.clear();
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
                dtgame1.add(game);
            }

            // Обновляем таблицу с найденными играми
            gamesTable.setItems(dtgame1);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска в базе данных", e);
        }
    }

    private void UpdateGameTableColumn1(){
        try {
            addInfAboutGames1();
            IDGameColum1.setCellValueFactory(new PropertyValueFactory<>("gameId"));
            nameGameColum1.setCellValueFactory(new PropertyValueFactory<>("gameName"));
            CategColum1.setCellValueFactory(new PropertyValueFactory<>("GameCategory"));
            PriceColum1.setCellValueFactory(new PropertyValueFactory<>("gamePrice"));
            CountColum1.setCellValueFactory(new PropertyValueFactory<>("gameCount"));
            gamesTable1.setItems(dtgame1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void UpdateGameTableColumn(){
        try {
            addInfAboutGames();
            IDGameColum.setCellValueFactory(new PropertyValueFactory<>("gameId"));
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

    private void UpdateCategoryTableColumn1(){
        try {
            addInfAboutCategory1();
            CategoryIdColumn1.setCellValueFactory(new PropertyValueFactory<>("CategoryId"));
            CategoryNameColumn1.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
            CategoryTable1.setItems(dtcategory1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    private void addInfAboutCategory1() throws SQLException{
        ResultSet categorys = dbHandler.getCategory();
        while (categorys.next()){
            Category category = new Category(
                    categorys.getString(1),
                    categorys.getString(2));
            dtcategory1.add(category);
        }
    }

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

    private void addInfAboutGames1() throws  SQLException{
        ResultSet games = dbHandler.getGames();
        while (games.next()){
            Games game = new Games(
                    games.getString(1),
                    games.getString(2),
                    games.getString(3),
                    games.getString(4),
                    games.getString(5));
            dtgame1.add(game);
        }
    }
}
