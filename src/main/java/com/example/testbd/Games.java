package com.example.testbd;

public class Games {
    private Integer id;
    private String gamename;
    private String category_id;
    private Integer gameprice;
    private Integer gamecount;


    public Games(String id, String gamename, String category_id, String gameprice, String gamecount) {
        this.id = (id != null && !id.trim().isEmpty()) ? Integer.valueOf(id) : null;
        this.gamename = gamename;
        this.category_id = category_id;
        this.gameprice = (gameprice != null && !gameprice.trim().isEmpty()) ? Integer.valueOf(gameprice) : null;
        this.gamecount = (gamecount != null && !gamecount.trim().isEmpty()) ? Integer.valueOf(gamecount) : null;
    }

    public String getGameName() {
        return gamename;
    }

    public void setGameName(String gamename) {
        this.gamename = gamename;
    }

    public String  getGameCategory() {
        return category_id;
    }

    public void setGameCategory(String category_id) {
        this.category_id = category_id;
    }

    public Integer getGamePrice() {
        return gameprice;
    }

    public void setGamePrice(Integer gameprice) {
        this.gameprice = gameprice;
    }

    public Integer getGameCount() {
        return gamecount;
    }

    public void setGameCount(Integer gamecount) {
        this.gamecount = gamecount;
    }

    public int getGameId() {
        return id;
    }

    public void setGameId(Integer id) {
        this.id = id;
    }
}
