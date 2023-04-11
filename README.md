# Software Engineering I - Final Project  - A.Y. 2021/22
* <b>Prof:</b> Alessandro Margara - Politecnico di Milano
* <b>Group:</b>  AM19
* <b>Grade:</b> 30/30

### Members:
* #### Laura Colazzo ([@lauracolazzo](https://github.com/lauracolazzo)) <br>laura.colazzo@mail.polimi.it
* #### Dennis De Maria ([@dennydemaria](https://github.com/dennydemaria)) <br>dennis.demaria@mail.polimi.it
* #### Filippo Del Nero ([@FilippoDelNero](https://github.com/FilippoDelNero)) <br>filippogiovanni.delnero@mail.polimi.it
<br>

![](https://cf.geekdo-images.com/DzhJxVjMhGQadReXJmbIaQ__opengraph/img/qv98Tqw2-X1Mj-0J6OMlRoVC_Uk=/0x0:2988x1569/fit-in/1200x630/filters:strip_icc()/pic6253341.jpg)

## Description
The project was part of the final evaluation for Bachelor of Science in Computer Engineering course at Politecnico di Milano.

The specifications required to develop a Java application resembling an existing board game by Cranio Creations called [Eriantys](https://craniointernational.com/products/eriantys/) (full rules available [here](https://craniointernational.com/2021/wp-content/uploads/2021/06/Eriantys_rules_small.pdf)).

## Application design
The project is a client-server application that supports the complete game's rules for the two-player and three-player mode on both CLI e GUI. It also supports two advanced functionalities, including character cards implementation and persistence of the game state.

Regarding persistence, the functionality was implemented such that the storing procedure of the application state happens at the end of each round. It means that the first saving happens only once the login phase has ended and all players played their first round.
Information about an in progress match are stored in the same directory as the .jar file. When a match is completed, the stored match is automatically deleted.

In case a game is interrupted due to fatal errors, the server needs to be restarted before being able to resume the previous match or before starting a new one.

## Progress:


| Functionality          |State                                        |
|:-----------------------|:-------------------------------------------:|
| Basic rules            | 🟢 |
| Complete rules         | 🟢 |
| Communication Protocol | 🟢 |
| GUI                    | 🟢 |
| CLI                    | 🟢 |
| AF 1: Character cards  | 🟢 |
| AF 2: Persistence      | 🟢 |
| AF 3: -                | 🔴 |


🔴 Uninitiated
🟢 Completed
🟡 Work in progress

## Test coverage

| Package     | Class coverage | Method coverage | Line coverage |
|:------------|:--------------:|:---------------:|:-------------:|
| Model       |      100%      |       85%       |      88%      |
| Controller* |      75%       |       41%       |      20%      |

*Tests were made only where network was not involved

## Execution
### Server
To run the application in server mode you need to write the following command:
```
java -jar AM19.jar -s [port number]
```

### Client - CLI
To run the application as client in CLI mode you need to write the following command:
```
java -jar AM19.jar -cli 
```

### Client - GUI
To run the application as client in GUI mode you can double click on the .jar file or you can write the following command:
```
java -jar AM19.jar
```
