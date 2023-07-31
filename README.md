<h1>Проект по программированию на Java с использованием Scene Builder. Магазин игр с интерфейсом для юзера и админа</h1>
<p><h2>Работа выполнена с помощью графического инструмента Java javaFX, с использованием SceneBuilder и базой данных PostgreSQL (Pg Admin)</h2></p>
<p><h3>Код для создания таблиц</h3></p>

<p><h3>-- Создание таблицы categoryGames</h3></p>
<p>CREATE TABLE categoryGames (</p>
<p> id INT PRIMARY KEY,</p>
<p>  categoryname VARCHAR(255)</p>
<p>);</p>

<p><h3>-- Создание таблицы games</h3></p>
<p>CREATE TABLE games (</p>
<p>  id INT PRIMARY KEY,</p>
<p>  gamename VARCHAR(255),</p>
<p>  gamecategory_id INT,</p>
<p>  gameprice INT,</p>
<p>  gamecount INT,</p>
<p>  FOREIGN KEY (gamecategory_id) REFERENCES categoryGames(id)</p>
<p>);</p>
  
<p><h3>-- Создание таблицы users</h3></p>
<p>CREATE TABLE users (</p>
<p>  id INT PRIMARY KEY,</p>
<p>  login VARCHAR(255),</p>
<p>  password VARCHAR(255)</p>
<p>);</p>
