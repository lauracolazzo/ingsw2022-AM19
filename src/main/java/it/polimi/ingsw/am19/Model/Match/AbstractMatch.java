package it.polimi.ingsw.am19.Model.Match;
import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.*;
import it.polimi.ingsw.am19.Model.Utilities.Observable;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.*;

/**
 * Abstract Class that implements all methods shared by different kinds of Matches
 */
public abstract class AbstractMatch extends Observable implements Match {
    /**
     * Number of Players expected
     */
    int numOfPlayers;

    /**
     * Takes a reference to the current player
     */
    Player currPlayer;

    /**
     * Saves all players in the planning phase order
     */
    List<Player> planningPhaseOrder;

    /**
     * Saves all players in the action phase order
     */
    List<Player> actionPhaseOrder;

    /**
     * Maps each Player to his GameBoard
     */
    Map<Player, GameBoard> gameBoards;

    /**
     * Is a List of Clouds
     */
    List<Cloud> clouds;

    /**
     * Saves a reference to the IslandManager
     */
    IslandManager islandManager;

    /**
     * Saves Bag's instance
     */
    Bag bag;

    /**
     * Saves a reference to MotherNature instance
     */
    MotherNature motherNature;

    /**
     *  Saves a reference to the ProfessorManager
     */
    ProfessorManager professorManager;

    /**
     * Is a List of the available WizardFamilies
     */
    final List<WizardFamily> wizardFamilies;

    /**
     * Is a List of the available TowerColors
     */
    final List<TowerColor> towerColors;

    /**
     * Saves the already played HelperCards
     */
    List<HelperCard> alreadyPlayedCards ;

    /**
     * Builds a new Match with the specified number of Players
     * @param num represents the number of Players that will play tha game
     */
    public AbstractMatch(int num) {
        this.numOfPlayers = num;
        this.currPlayer = null;
        this.planningPhaseOrder = new ArrayList<>();
        this.actionPhaseOrder = new ArrayList<>();
        this.gameBoards = new HashMap<>();
        this.clouds = new ArrayList<>();
        this.professorManager = new ProfessorManager();
        this.islandManager = new IslandManager(professorManager);
        this.bag = Bag.getBagInstance();
        this.motherNature = MotherNature.getInstance();
        this.wizardFamilies = new ArrayList<>(Arrays.asList(WizardFamily.values()));
        this.towerColors = new ArrayList<>(Arrays.asList(TowerColor.values()));
        this.alreadyPlayedCards = new ArrayList<>();
    }

    /**
     * Initializes all the game components in order to get ready for the firs planning phase
     */
    @Override
    public abstract void initializeMatch();

    /**
     * Adds a  player to the game
     * @param player represents the Player to add
     */
    @Override
    public void addPlayer(Player player) {
        //TODO THE CONTROLLER SHOULD CHECK THE NICKNAMES TO AVOID DOUBLES
        if(planningPhaseOrder.size() < numOfPlayers) {
            planningPhaseOrder.add(player);
        }
    }

    /**
     * Returns the current player
     * @return the current player
     */
    @Override
    public Player getCurrPlayer(){
        return currPlayer;
    }

    /**
     * Updates the current player
     * @param player the newest current player to set
     */
    @Override
    public void setCurrPlayer(Player player) {
        this.currPlayer = player;
    }

    /**
     * Reorders the order of the player for the next planning phase
     */
    @Override
    public void updatePlanningPhaseOrder(){
        int firstPlayerIndex = planningPhaseOrder.lastIndexOf(actionPhaseOrder.get(0));
        int whereToInsert = 0;
        for(int i = firstPlayerIndex; i < planningPhaseOrder.size(); i++) {
            Player playerToShift = planningPhaseOrder.remove(i);
            planningPhaseOrder.add(whereToInsert, playerToShift);
            whereToInsert++;
        }
    }

    /**
     * Refills all the Clouds
     * @throws EmptyBagException when there are no students available to put on the Clouds
     * @throws TooManyStudentsException when trying to add more students than the actual Cloud capacity
     */
    @Override
    public void refillClouds() throws EmptyBagException, TooManyStudentsException {
        for (Cloud cloud: clouds){
            for (int i = 0; i < cloud.getNumOfHostableStudents(); i++){
                PieceColor color = bag.drawStudent();
                cloud.addStudent(color);
            }
        }
    }

    /**
     * Moves a student of a specified PieceColor from a place to another
     * @param color represents the PieceColor of the student to move
     * @param from represents the place where the movement starts
     * @param to represents the final destination of the movement
     */
    //TODO Handle Exception -> idea: if the destination is full -> rollback
    @Override
    public void moveStudent(PieceColor color, MoveStudent from, MoveStudent to) {
        try {
            from.removeStudent(color);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }
        try {
            to.addStudent(color);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to allow the current player to move a student from his game-board's entrance to the dining room
     * @param color the student's color you want to move
     * @throws NoSuchColorException when we pass an unexpected color
     * @throws InsufficientCoinException when we try to add the 11th student of a color
     * @throws TooManyStudentsException ?
     */
    //TODO PERCHÈ TIRA LA INSUFFICIENT_COIN_EXCEPTION?
    public void moveStudentToDiningRoom(PieceColor color) throws NoSuchColorException, InsufficientCoinException, TooManyStudentsException {
        GameBoard currPlayerGameBoard = gameBoards.get(currPlayer);
        currPlayerGameBoard.moveStudentToDiningRoom(color);
    }

    /**
     * Makes a player play a new HelperCard
     * @param card is the card to play
     * @throws UnavailableCardException when the specified card is not available in the Player's deck
     * @throws IllegalCardOptionException when a card is chosen but it has been played by another Player and could have been replaced by another unused card
     */
    @Override
    public void useHelperCard(HelperCard card) throws UnavailableCardException, IllegalCardOptionException {
        if (alreadyPlayedCards.contains(card)){
            for (HelperCard helperCard: currPlayer.getHelperDeck()) {
                if (!alreadyPlayedCards.contains(helperCard))
                    throw new IllegalCardOptionException("Cannot play a card that has already been played by another player");
            }
        }
        currPlayer.useHelperCard(card);
        alreadyPlayedCards.add(card);

        int target = card.getNextRoundOrder();
        ListIterator<Player> iterator = actionPhaseOrder.listIterator();
        if(!actionPhaseOrder.isEmpty()) {
            int index = actionPhaseOrder.size() - 1;
            while (iterator.hasNext()) {
                Player currPlayer = iterator.next();
                if (currPlayer.getCurrentCard().getNextRoundOrder() <= target) {
                    index = iterator.nextIndex();
                }
            }
            actionPhaseOrder.add(index, currPlayer);
        }
        else
            actionPhaseOrder.add(0,currPlayer);

    }

    /**
     * Removes all the HelperCards from the List of the already used ones in the previous turn
     */
    public void resetAlreadyPlayedCards(){
        int size = alreadyPlayedCards.size();
        if (size > 0) {
            alreadyPlayedCards.subList(0, size).clear();
        }
    }

    /**
     * Moves Mother Nature
     * @param steps the numbers of step you want to move Mother Nature of
     * @throws IllegalNumOfStepsException the number of steps in either < 0 or > than what allowed by the card
     */
    public void moveMotherNature(int steps) throws IllegalNumOfStepsException {
        int maxNumOfSteps = currPlayer.getCurrentCard().getMaxNumOfSteps();
        if(steps > maxNumOfSteps)
            throw new IllegalNumOfStepsException("Trying to move MotherNature further than what's allowed which is" + maxNumOfSteps);
        else
            motherNature.move(steps);
    }

    /**
     * set the color's tower for a player
     * @param towerColor the color chosen by a player
     * @param player the player choosing said color
     */
    public void setTowerColors(TowerColor towerColor, Player player) {
        player.setTowerColor(towerColor);
        this.towerColors.remove(towerColor);
    }

    /**
     * set the wizard family for a player
     * @param wizardFamily the wizard chosen by a player
     * @param player the player choosing said wizard
     */
    public void setWizardFamily(WizardFamily wizardFamily,Player player){
        player.setWizardFamily(wizardFamily);
        this.wizardFamilies.remove(wizardFamily);
    }

    /**
     * setter for the alreadyPlayedCards attribute
     * @param alreadyPlayedCards a list containing the helper cards played during the current turn
     */
    public void setAlreadyPlayedCards(List<HelperCard> alreadyPlayedCards) {
        this.alreadyPlayedCards = alreadyPlayedCards;
    }

    /**
     * getter for number of players
     * @return the number of player currently playing
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * getter for planningPhaseOrder
     * @return the players in the order in which they'll play the next planning phase
     */
    public List<Player> getPlanningPhaseOrder() {
        return planningPhaseOrder;
    }

    /**
     * getter for actionPhaseOrder
     * @return the players in the order in which they'll play the next action phase
     */
    public List<Player> getActionPhaseOrder() {
        return actionPhaseOrder;
    }

    /**
     * getter for gameBoards attribute
     * @return a map that link each player to its game-board
     */
    public Map<Player, GameBoard> getGameBoards() {
        return gameBoards;
    }

    /**
     * getter for the clouds attribute
     * @return the list containing all the clouds in the game
     */
    public List<Cloud> getClouds() {
        return clouds;
    }

    /**
     * getter for the islandManager attribute
     * @return a reference to the islandManager
     */
    public IslandManager getIslandManager() {
        return islandManager;
    }

    /**
     * getter for the bag attribute
     * @return a reference to the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * getter for the motherNature attribute
     * @return a reference to the motherNature
     */
    public MotherNature getMotherNature() {
        return motherNature;
    }

    /**
     * getter for the professorManager attribute
     * @return a reference to the professorManager
     */
    public ProfessorManager getProfessorManager() {
        return professorManager;
    }

    /**
     * getter for the wizardFamilies attribute
     * @return a list containing the remaining wizard families in the game
     */
    public List<WizardFamily> getWizardFamilies(){
        return this.wizardFamilies;
    }

    /**
     * getter for the towerColors attribute
     * @return a list containing the remaining tower's colors in the game
     */
    public List<TowerColor> getTowerColors() {
        return this.towerColors;
    }

    /**
     * getter for the alreadyPlayedCards attribute
     * @return the list containing the cards played in a given turn
     */
    public List<HelperCard> getAlreadyPlayedCards() {
        return this.alreadyPlayedCards;
    }
}
