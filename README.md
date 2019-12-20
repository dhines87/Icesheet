# Ice Sheet
A simple hockey league management client/server CRUD application.

This application was created as a PLAR (Prior Learning Accessment and Recognization) school project to showcase my knowledge in Client Server technologies.

The client is written in Angular.js and Typescript. It includes routing, parent/child components making REST calls to a JAVA EE server via RXJS observables.

The server is a Java EE application utilizing an EJB facade pattern and JPA based models in an EJB project. As well as JAX-RS as a WAR project. These two projects are bundled together in the same enterprise application arhive (EAR).

Gulp was used as a taskrunner to compile and bundle client side files to the Java EE application in a minimized state. (Ex: [icesheet.min.js](https://github.com/dhines87/IcesheetServer/blob/master/Icesheet-war/web/scripts/icesheet.min.js))

The application is deployed to Amazon's AWS public cloud as an EAR file on a Glassfish server running on Ubuntu.

Ice Sheet comes loaded with some sample data to get you started.

---
 
# How Ice Sheet Works

## Teams Screen

![](https://github.com/dhines87/Icesheet/blob/master/images/Teams.PNG)

This screen displays all the players of a team including their stats and sorts them by position. Toggle between teams by choosing a team in the team dropdown. You can create and edit teams and players from this screen. You cannot delete a team or players.

#### Create a Team

![](https://github.com/dhines87/Icesheet/blob/master/images/create_team_modal.PNG)

- Create a new team by clicking on the Create Team button. This will open a modal. Enter in a team name and click on the create button.

#### Edit a Team

![](https://github.com/dhines87/Icesheet/blob/master/images/edit_team_modal.PNG)

- Edit the currently selected team in the dropdown by clicking on the Edit Team button, which opens a modal. Edit the team name and click on the edit button.

#### Create a Player

![](https://github.com/dhines87/Icesheet/blob/master/images/create_player_modal.PNG)

- Create a player by clicking on the create player button. This will open a modal. After entering all the required information click on the create button. The player is assigned to the currently selected team in the dropdown. A player cannot switch teams.

#### Edit a Player

![](https://github.com/dhines87/Icesheet/blob/master/images/edit_player_modal.PNG)

- Edit a player by click on the desired player row in the players table, which will open a model. Edit the players information and click on the edit button.

## Stats Screen

![](https://github.com/dhines87/Icesheet/blob/master/images/stats.PNG)

This screen displays all the players stats in the league in a table. Some columns are sortable.

## Standings Screen

![](https://github.com/dhines87/Icesheet/blob/master/images/standings.PNG)

This screen displays the league standings. Some columns are sortable.

## Games Screen

![](https://github.com/dhines87/Icesheet/blob/master/images/games.PNG)

This screen displays each game that has been played in the league, along with the result.

#### Create a Game
- Create a game by click on the create game button. This will re-direct to the game screen.

#### Edit a Game
- Edit a game by clicking on the desired game row in the games table, which will re-direct to the game screen.

## Game Screen

![](https://github.com/dhines87/Icesheet/blob/master/images/edit_game.PNG)

This screen displays each game, along with each player on each team and the game summary.

#### Edit a Game
- After a game has been started from the game start mode or by navigating from the Games screen, the game will enter the edit mode. Each teams players will be displayed in a table. Clicking on a player row will open a new game event modal.

#### Start a Game

![](https://github.com/dhines87/Icesheet/blob/master/images/create_game.PNG)

- Clicking on the Create Game button on the Games screen will display the start mode for a game.
- The date has been preloaded with the current day. This date can be changed anytime.
- Choose the home and visitor team from each dropdown.
- Click on the Start Game button to save and officially start this game. Once the game is started the teams cannot be changed.

#### Add a Game Event

![](https://github.com/dhines87/Icesheet/blob/master/images/create_game_event.PNG)

- There are two type of game events: Goals and Penalties.
- When a new game event modal is opened by clicking on a player row, the game event will automatically set the chosen player.
- Choose between a Goal or Penalty.
- The game summary will automatically update after a game event is added.
  #### Goal Event:
  ![](https://github.com/dhines87/Icesheet/blob/master/images/create_goal.PNG)
  - Enter in the time of the goal between 0:00 and 20:00.
  - Choose the period (preset to the 1st).
  ![](https://github.com/dhines87/Icesheet/blob/master/images/create_goal_complete.PNG)
  - You can add a primary assist by clicking on the +Assist button, and choosing the player from the dropdown. Click on the +Assist button again to add a secondary assist.
  - Click on the save button to save the goal.
  #### Penalty Event:
  ![](https://github.com/dhines87/Icesheet/blob/master/images/create_penalty.PNG)
  - Enter in the time of the penalty between 0:00 and 20:00.
  - Choose the period (preset to the 1st).
  - Select the penalty type and minutes from each dropdown.
  - Click on the save button to save the penalty.
  
#### Edit a Game Event
![](https://github.com/dhines87/Icesheet/blob/master/images/edit_game_event.PNG)
- Click on a row from either the goal or penalty summary table to open an edit game event modal.

#### View Gamesheet
- Click on the view gamesheet button to download a pdf of the game summary.
- This functionality is handled by a servlet.
