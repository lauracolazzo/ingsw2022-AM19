package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Map;

/**
 * class that allows to draw on the scene different type of objects used in multiple scenes
 */
public class Drawer {
    //--Students--
    /** Image containing the sprite of the red student */
    private final static Image redStudent = new Image("file:src/main/resources/Board/student_red.png");

    /** Image containing the sprite of the green student */
    private final static Image greenStudent = new Image("file:src/main/resources/Board/student_green.png");

    /** Image containing the sprite of the blue student */
    private final static Image blueStudent = new Image("file:src/main/resources/Board/student_blue.png");

    /** Image containing the sprite of the yellow student */
    private final static Image yellowStudent = new Image("file:src/main/resources/Board/student_yellow.png");

    /** Image containing the sprite of the pink student */
    private final static Image pinkStudent = new Image("file:src/main/resources/Board/student_pink.png");

    //--Professors--
    /** Image containing the sprite of the red professor */
    private final Image redProfessor = new Image("file:src/main/resources/Board/teacher_red.png");

    /** Image containing the sprite of the green professor */
    private final Image greenProfessor = new Image("file:src/main/resources/Board/teacher_green.png");

    /** Image containing the sprite of the blue professor */
    private final Image blueProfessor = new Image("file:src/main/resources/Board/teacher_blue.png");

    /** Image containing the sprite of the yellow professor */
    private final Image yellowProfessor = new Image("file:src/main/resources/Board/teacher_yellow.png");

    /** Image containing the sprite of the pink professor */
    private final Image pinkProfessor = new Image("file:src/main/resources/Board/teacher_pink.png");

    //--Various--
    /** Image containing the sprite of mother nature */
    private final Image motherNatureImg = new Image("file:src/main/resources/Board/mother_nature.png");

    /** Image containing the sprite of a no-entry-tile */
    private final Image noEntryTileImg = new Image("file:src/main/resources/Board/noEntryTile.png");

    //--Islands--
    /** Image containing the sprite of a single type 2 island */
    private final Image singleIslandType2 = new Image("file:src/main/resources/Board/island2.png");

    /** Image containing the sprite of group of two islands */
    private final Image islandGroup2 = new Image("file:src/main/resources/Board/islandGroup2.png");

    /** Image containing the sprite of group of three islands */
    private final Image islandGroup3 = new Image("file:src/main/resources/Board/islandGroup3.png");

    /** Image containing the sprite of group of four islands */
    private final Image islandGroup4 = new Image("file:src/main/resources/Board/islandGroup4.png");

    /** Image containing the sprite of group of five islands */
    private final Image islandGroup5 = new Image("file:src/main/resources/Board/islandGroup5.png");

    /** Image containing the sprite of group of six islands */
    private final Image islandGroup6 = new Image("file:src/main/resources/Board/islandGroup6.png");

    /** Image containing the sprite of group of seven islands */
    private final Image islandGroup7 = new Image("file:src/main/resources/Board/islandGroup7.png");

    /** Image containing the sprite of group of eight islands */
    private final Image islandGroup8 = new Image("file:src/main/resources/Board/islandGroup8.png");

    /**
     * Method to get the path of the image of a CharacterCard
     * @param c the character enum value
     * @return the path of the image of the CharacterCard corresponding to c
     */
    public String getCharacterImagePath(Character c){
        return "file:src/main/resources/it/polimi/ingsw/am19.View.GUI/CharacterCard/" + c + ".jpg";
    }

    /**
     * Method to get the price and description of a CharacterCard
     * @param c the CharacterCard
     * @return a string with the price and description
     */
    public String getCharacterDescription(AbstractCharacterCard c){
        return "Price: " + c.getPrice() + "\n" + c.getDescription();
    }

    /**
     * Method to populate a CharacterCard with students
     * @param card the CharacterCard
     * @param onCardGrid the GridPane where the students will be put on
     */
    public void setStudentOnCard(AbstractCharacterCard card, GridPane onCardGrid){
        Map<PieceColor,Integer> studentOnCard = card.getStudents();
        if(studentOnCard!=null){
            int r=0;
            int c=0;
            for (PieceColor color : studentOnCard.keySet()){
                for(int k=0; k<studentOnCard.get(color);k++){
                    onCardGrid.add(createStudent(color,20),c,r);
                    r++;
                    if(r==3){
                        c=2;
                        r=0;
                    }
                }
            }
        }
    }

    /**
     * method used to put student on the cloud
     * @param cloud the GridPane of the cloud we want to draw students on
     * @param cloudMap the cloud saved in cache corresponding to the gui's GridPane
     */
    public void populateCloud(GridPane cloud, Map<PieceColor, Integer> cloudMap) {
        int i = 0;
        int j = 0;
        for(PieceColor color : cloudMap.keySet()) {
            for(int k = 0; k < cloudMap.get(color); k++) {
                cloud.add(createStudent(color, 10), i, j);
                i++;
                if(i == 2) {
                    i = 0;
                    j++;
                }
            }
        }
    }

    /**
     * method used to put student on the island
     * @param island the GridPane of the island we want to draw students on
     * @param reducedIsland the island saved in cache corresponding to the gui's GridPane
     */
    public void populateIsland(GridPane island, ReducedIsland reducedIsland) {
        Circle motherNature = new Circle(20);
        motherNature.setFill(new ImagePattern(motherNatureImg));

        for (PieceColor color : PieceColor.values()) {
            int num = reducedIsland.numOfStudents().get(color);
            if(num != 0) {
                switch (color) {
                    case RED -> {
                        island.add(createStudent(PieceColor.RED,10), 0, 0);
                        island.add(new Label("x" + num), 1, 0);
                    }
                    case GREEN -> {
                        island.add(createStudent(PieceColor.GREEN,10), 2, 0);
                        island.add(new Label("x" + num), 3, 0);
                    }
                    case BLUE -> {
                        island.add(createStudent(PieceColor.BLUE,10), 0, 1);
                        island.add(new Label("x" + num), 1, 1);
                    }
                    case YELLOW -> {
                        island.add(createStudent(PieceColor.YELLOW,10), 2, 1);
                        island.add(new Label("x" + num), 3, 1);
                    }
                    case PINK -> {
                        island.add(createStudent(PieceColor.PINK,10), 0, 2);
                        island.add(new Label("x" + num), 1, 2);
                    }
                }
            }
        }
        if(reducedIsland.presenceOfMotherNature())
            island.add(motherNature, 2,2);
        if(reducedIsland.noEntryTile()) {
            Circle noEntryTile = new Circle(15);
            noEntryTile.setFill(new ImagePattern(noEntryTileImg));
            island.add(noEntryTile, 2, 2);
        }
        if(reducedIsland.towerColor() != null)
            island.add(createTower(reducedIsland.towerColor()), 3, 2);
    }

    /**
     * method used to put player's student in their correct space in the gameBoard's entrance
     * @param entrance the GridPane where we want to draw the students on
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    public void populateEntrance(GridPane entrance, ReducedGameBoard gameBoard) {
        int i = 0;
        int j = 0;
        for(PieceColor color : PieceColor.values()) {
            for(int k = 0; k < gameBoard.entrance().get(color); k++) {
                entrance.add(createStudent(color,10), i, j);
                i++;
                if(i == 2) {
                    i = 0;
                    j++;
                }
            }
        }
    }

    /**
     * method used to put player's student in their correct space in the gameboard
     * @param redTable the GridPane of the table containing red students
     * @param greenTable the GridPane of the table containing green students
     * @param blueTable the GridPane of the table containing blue students
     * @param yellowTable the GridPane of the table containing yellow students
     * @param pinkTable the GridPane of the table containing pink students
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    public void populateDiningRoom(GridPane redTable, GridPane greenTable, GridPane blueTable, GridPane yellowTable, GridPane pinkTable,ReducedGameBoard gameBoard) {
        //red table
        for(int r = 0; r < gameBoard.diningRoom().get(PieceColor.RED); r++) {
            redTable.add(createStudent(PieceColor.RED,10), r, 0);
        }
        //green table
        for(int g = 0; g < gameBoard.diningRoom().get(PieceColor.GREEN); g++) {
            greenTable.add(createStudent(PieceColor.GREEN,10), g, 0);
        }
        //blue table
        for(int b = 0; b < gameBoard.diningRoom().get(PieceColor.BLUE); b++) {
            blueTable.add(createStudent(PieceColor.BLUE,10), b, 0);
        }
        //yellow table
        for(int y = 0; y < gameBoard.diningRoom().get(PieceColor.YELLOW); y++) {
            yellowTable.add(createStudent(PieceColor.YELLOW,10), y, 0);
        }
        //pink table
        for(int p = 0; p < gameBoard.diningRoom().get(PieceColor.PINK); p++) {
            pinkTable.add(createStudent(PieceColor.PINK,10), p, 0);
        }
    }

    /**
     * method used to put player's professors in their space in the gameboard
     * @param professorsTable the GridPane where we want to draw professors in
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    public void populateProfessors(GridPane professorsTable, ReducedGameBoard gameBoard) {
        for(int i = 0; i < gameBoard.professors().size(); i++) {
            switch (gameBoard.professors().get(i)) {
                case RED -> professorsTable.add(createProfessor(PieceColor.RED), 0, 1);
                case GREEN -> professorsTable.add(createProfessor(PieceColor.GREEN), 0, 0);
                case BLUE -> professorsTable.add(createProfessor(PieceColor.BLUE), 0, 4);
                case YELLOW -> professorsTable.add(createProfessor(PieceColor.YELLOW), 0, 2);
                case PINK -> professorsTable.add(createProfessor(PieceColor.PINK), 0, 3);
            }
        }
    }

    /**
     * method used to put player's towers in their space in the gameboard
     * @param towersField the GridPane where we want to draw the towers in
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    public void populateTowers(GridPane towersField, ReducedGameBoard gameBoard) {
        int i = 0;
        int j = 0;
        TowerColor color = gameBoard.TowerColor();
        for(int k = 0; k < gameBoard.numOfTowers(); k++) {
            towersField.add(createTower(color), i, j);
            i++;
            if(i == 2) {
                i = 0;
                j++;
            }
        }
    }

    /**
     * method to create a circle of radius 10 with the sprite of a student on it
     * @param pieceColor the color of the student
     * @param radius the radius of the circle of the student
     * @return a decorated circle
     */
    public Circle createStudent(PieceColor pieceColor, int radius) {
        StudentPiece student = new StudentPiece(radius); //create a circle of radius 10
        student.setColor(pieceColor);
        switch (pieceColor) {
            case RED -> {
                student.setFill(new ImagePattern(redStudent));
                student.setStroke(Color.DARKRED);
            }
            case GREEN -> {
                student.setFill(new ImagePattern(greenStudent));
                student.setStroke(Color.LIME);
            }
            case BLUE -> {
                student.setFill(new ImagePattern(blueStudent));
                student.setStroke(Color.DARKBLUE);
            }
            case YELLOW -> {
                student.setFill(new ImagePattern(yellowStudent));
                student.setStroke(Color.LEMONCHIFFON);
            }
            case PINK -> {
                student.setFill(new ImagePattern(pinkStudent));
                student.setStroke(Color.PEACHPUFF);
            }
        }
        return student;
    }

    /**
     * method to create a circle of radius 15 with the sprite of a professor on it
     * @param pieceColor the color of the professor
     * @return a decorated circle
     */
    public Circle createProfessor(PieceColor pieceColor) {
        Circle professor = new Circle(15); //create a circle of radius 15
        switch (pieceColor) {
            case RED -> professor.setFill(new ImagePattern(redProfessor));
            case GREEN -> professor.setFill(new ImagePattern(greenProfessor));
            case BLUE -> professor.setFill(new ImagePattern(blueProfessor));
            case YELLOW -> professor.setFill(new ImagePattern(yellowProfessor));
            case PINK -> professor.setFill(new ImagePattern(pinkProfessor));
        }
        return professor;
    }

    /**
     * method to create a circle of radius 10 with the color of a tower
     * @param towerColor the color of the tower
     * @return a colored circle
     */
    public Circle createTower(TowerColor towerColor) {
        Color color;
        Color strokeColor;

        if(towerColor == TowerColor.BLACK) {
            color = Color.BLACK;
            strokeColor = Color.DARKGRAY;
        }
        else if(towerColor == TowerColor.WHITE) {
            color = Color.WHITE;
            strokeColor = Color.LIGHTGRAY;
        }
        else {
            color = Color.GREY;
            strokeColor = Color.DARKSLATEGREY;
        }

        Circle tower = new Circle(10, color);
        tower.setStroke(strokeColor);

        return tower;
    }

    /**
     * Method to get a PieceColor starting from a string
     * @param s the string supposedly containing the color
     * @return the PieceColor
     */
    public PieceColor getColor (String s){
        return switch (s){
            case "GREEN"-> PieceColor.GREEN;
            case "BLUE"-> PieceColor.BLUE;
            case "PINK"->PieceColor.PINK;
            case "RED"-> PieceColor.RED;
            case "YELLOW"-> PieceColor.YELLOW;
            default -> null;
        };
    }

    /**
     * method to get a path of student's PieceColor from its PieceColor
     * @param color the PieceColor of the student
     * @return the image of the correct sprite
     */
    public Image getStudentPath(PieceColor color){
        return switch (color){
            case BLUE -> blueStudent;
            case PINK -> pinkStudent;
            case RED -> redStudent;
            case YELLOW -> yellowStudent;
            case GREEN -> greenStudent;
        };
    }

    /**
     * Method to get the correct image for the number of island composing a group
     * @param numOfIsland the number of island of said group
     * @return the image of numOfIsland number of island
     */
    public Image getIslandImage(int numOfIsland) {
        return switch (numOfIsland) {
            case 2 -> islandGroup2;
            case 3 -> islandGroup3;
            case 4 -> islandGroup4;
            case 5 -> islandGroup5;
            case 6 -> islandGroup6;
            case 7 -> islandGroup7;
            case 8 -> islandGroup8;
            default -> singleIslandType2;
        };
    }

}
