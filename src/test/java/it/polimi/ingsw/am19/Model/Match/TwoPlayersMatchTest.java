package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.*;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the TwoPlayerMatch Class
 */
class TwoPlayersMatchTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }

    /**
     * testing the getter and setter for the current player attribute
     */
    @Test
    void testGetterAndSetterCurrPlayer() {
        Player p1 = new Player("Phil",TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        m.setCurrPlayer(p1);
        assertEquals(p1, m.getCurrPlayer());
    }

    /**
     * testing the getter for the number of players playing a match
     */
    @Test
    void testGetNumOfPlayers() {
        Player p1 = new Player("Phil",TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        assertEquals(2, m.getNumOfPlayers());
    }

    /**
     * testing the getter and setter for the alreadyPlayedHelperCards attribute
     */
    @Test
    void testGetAndSetForAlreadyPlayedHC() {
        Player p1 = new Player("Phil",TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        List<HelperCard> list = new ArrayList<>();

        list.add(p1.getHelperDeck().get(0));
        list.add(p2.getHelperDeck().get(1));

        m.setAlreadyPlayedCards(list);

        assertArrayEquals(list.toArray(), m.getAlreadyPlayedCards().toArray());

        m.resetAlreadyPlayedCards();
        assertEquals(0, m.getAlreadyPlayedCards().size());
    }

    /**
     * Tests assigning and removing from the list of available TowerColors the ones chosen for each Player
     */
    @Test
    void testInitializeTowers() {
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        assertEquals(1, m.getTowerColors().size());
        assertTrue(m.getTowerColors().contains(TowerColor.GREY));
        assertFalse(m.getTowerColors().contains(TowerColor.WHITE));
        assertFalse(m.getTowerColors().contains(TowerColor.BLACK));
    }

    /**
     * Tests assigning and removing from the list of available WizardFamilies the ones chosen for each Player
     */
    @Test
    void testInitializeWizards() {
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        assertEquals(2, m.getWizardFamilies().size());
        assertTrue(m.getWizardFamilies().contains(WizardFamily.WARRIOR));
        assertTrue(m.getWizardFamilies().contains(WizardFamily.WITCH));
        assertFalse(m.getWizardFamilies().contains(WizardFamily.SHAMAN));
        assertFalse(m.getWizardFamilies().contains(WizardFamily.KING));
    }

    /**
     * Tests the state of the Bag and GameBoards after the Match initialization
     */
    @Test
    void testInitializeGameBoards(){
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.initializeMatch();
        assertEquals(106,m.getBag().getTotNumOfStudents());
        assertEquals(2,m.getClouds().size());
        assertEquals(2, m.getGameBoards().size());
        for (Player player: m.getPlanningPhaseOrder()){
            GameBoard gameBoard = m.getGameBoards().get(player);
            assertEquals(7,gameBoard.getEntranceNumOfStud());
            assertEquals(8,gameBoard.getNumOfTowers());
            assertEquals(0,gameBoard.getDiningRoomNumOfStud());
        }
    }

    /**
     * Tests Clouds state after Match initialization and if they have a correct capacity
     */
    @Test
    void testInitializeClouds(){
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.initializeMatch();
        assertEquals(2,m.getClouds().size());
        for (Cloud cloud:m.getClouds()){
            assertEquals(3,cloud.getNumOfHostableStudents());
            assertEquals(0,cloud.getCurrNumOfStudents());
        }
    }

    /**
     * Tests the state of the archipelago of Islands after Match initialization
     */
    @Test
    void testInitializeIslands(){
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.initializeMatch();
        IslandManager islandManager = m.getIslandManager();
        MotherNature motherNature = m.getMotherNature();

        Island MNposition = motherNature.getCurrPosition();
        assertNotNull(MNposition);

        assertTrue(islandManager.getIslands().contains(MNposition));
        assertTrue(MNposition.isMotherNaturePresent());

        ListIterator<Island> iterator = islandManager.getIterator();
        Island islandOppositeMN = iterator.next();
        while (islandOppositeMN != MNposition){
            islandOppositeMN = iterator.next();
        }
       for (int i = 0; i < 6; i++) {
            islandOppositeMN = iterator.next();
       }
       assertEquals(0, islandOppositeMN.getTotStudents());
       assertEquals(0,MNposition.getTotStudents());

       Island island = iterator.next();
       for (int i = 0; i < 12; i++) {
           if (island != MNposition)
               assertFalse(island.isMotherNaturePresent());
           if(island != MNposition || island != islandOppositeMN)
               assertEquals(1, island.getTotStudents());
       }
    }

    /**
     * Tests trying to make the second Player use his last card although previously chosen by the first player
     */
    @Test
    void testActionPhaseOrder(){
        Player p1 = new Player("Phil",TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        p1.getHelperDeck().removeAll(new ArrayList<>(p1.getHelperDeck()));
        p2.getHelperDeck().removeAll(new ArrayList<>(p2.getHelperDeck()));

        HelperCard card1 = new HelperCard(WizardFamily.SHAMAN,1,1 );
        HelperCard card2 = new HelperCard(WizardFamily.KING,1,1 );
        p1.getHelperDeck().add(card1);
        p2.getHelperDeck().add(card2);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        try{
            m.useHelperCard(card2,p2);
        }catch (UnavailableCardException | IllegalCardOptionException e) {
            e.printStackTrace();
            fail();
        }

        try{
            m.useHelperCard(card1,p1);
        }catch (UnavailableCardException | IllegalCardOptionException e) {
            e.printStackTrace();
            fail();
        }

        System.out.println("Order of players in the next round:");
        for (Player p : m.getActionPhaseOrder()){
            System.out.println(p.getNickname());
        }
    }

    /**
     * Tests trying to make the second Player use a card tha was previously chosen by the first player, although having an unused card in his deck
     */
    @Test
    void testUseAlreadyUsedCard(){
        Player p1 = new Player("Phil",TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        p1.getHelperDeck().removeAll(new ArrayList<>(p1.getHelperDeck()));
        p2.getHelperDeck().removeAll(new ArrayList<>(p2.getHelperDeck()));

        HelperCard card1 = new HelperCard(WizardFamily.SHAMAN,1,1 );
        HelperCard card2 = new HelperCard(WizardFamily.KING,1,1 );
        HelperCard card3 = new HelperCard(WizardFamily.KING,2,1 );

        p1.getHelperDeck().add(card1);
        p2.getHelperDeck().add(card2);
        p2.getHelperDeck().add(card3);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        try{
            m.useHelperCard(card1,p1);
        }catch (UnavailableCardException | IllegalCardOptionException e) {
            e.printStackTrace();
            fail();
        }
        assertThrows(IllegalCardOptionException.class,
                () -> m.useHelperCard(card2,p2));
    }

    //TODO FURTHER TESTING OF THIS METHOD WILL BE NEEDED WHEN THE THREEPLAYERSMATCH WILL BE READY
    /**
     * Testing the sorting of the players for next turn's planning phase
     */
    @Test
    void testSortingOutPlanningPhase() {
        //create two players
        Player p1 = new Player("Phil", TowerColor.WHITE, WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        //create a match for two players
        AbstractMatch m = new TwoPlayersMatch();
        //initialize the match
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.initializeMatch();
        //the two players play a card each
        assertDoesNotThrow(() -> m.useHelperCard(p1.getHelperDeck().get(6), p1));
        assertDoesNotThrow(() -> m.useHelperCard(p2.getHelperDeck().get(0), p2));
        //check that the action phase order is as expected
        assertEquals(p1, m.getActionPhaseOrder().get(1));
        assertEquals(p2, m.getActionPhaseOrder().get(0));
        //sort out the next planning phase order
        m.updatePlanningPhaseOrder();
        //check that the next planning phase order is as expected
        assertEquals(p1, m.getPlanningPhaseOrder().get(1));
        assertEquals(p2, m.getPlanningPhaseOrder().get(0));
    }

    /**
     * testing the correct refill for the clouds
     */
    @Test
    void testRefillClouds() {
        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        try {
            m.refillClouds();
        } catch (EmptyBagException | TooManyStudentsException e) {
            fail();
        }

        for(Cloud c: m.getClouds()) {
            assertEquals(3, c.getCurrNumOfStudents());
        }
    }

    /**
     * testing the movement of a student
     */
    @Test
    void testMoveStudent() {
        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        Island island = m.getIslandManager().getIslands().get(0);
        GameBoard gameBoard = m.getGameBoards().get(p1);
        PieceColor student;

        //grab a student of a color, surely can be done in a better way, but i can't figure it out
        if(gameBoard.getEntrance().get(PieceColor.RED) > 0)
            student = PieceColor.RED;
        else if(gameBoard.getEntrance().get(PieceColor.BLUE) > 0)
            student = PieceColor.BLUE;
        else if(gameBoard.getEntrance().get(PieceColor.GREEN) > 0)
            student = PieceColor.GREEN;
        else if(gameBoard.getEntrance().get(PieceColor.YELLOW) > 0)
            student = PieceColor.YELLOW;
        else
            student = PieceColor.PINK;

        m.moveStudent(student, gameBoard, island);
        assertEquals(6, gameBoard.getEntranceNumOfStud());
        assertTrue(island.getTotStudents() > 0);
        assertTrue(island.getNumOfStudents().get(student) > 0);
    }

    /**
     * testing moving a student from the entrance to the dining room of a gameboard
     */
    @Test
    void testMoveStudentInsideGameboard() {
        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        GameBoard gameBoard = m.getGameBoards().get(p1);
        PieceColor student;

        if(gameBoard.getEntrance().get(PieceColor.RED) > 0)
            student = PieceColor.RED;
        else if(gameBoard.getEntrance().get(PieceColor.BLUE) > 0)
            student = PieceColor.BLUE;
        else if(gameBoard.getEntrance().get(PieceColor.GREEN) > 0)
            student = PieceColor.GREEN;
        else if(gameBoard.getEntrance().get(PieceColor.YELLOW) > 0)
            student = PieceColor.YELLOW;
        else
            student = PieceColor.PINK;

        m.setCurrPlayer(p1);

        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(student));
        assertEquals(1, gameBoard.getDiningRoomNumOfStud());
        assertEquals(1, gameBoard.getDiningRoom().get(student));
    }

    /**
     * testing the movement of mother nature with a valid number of steps
     */
    @Test
    void testMotherNatureMovement() {
        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        HelperCard helperCard = p1.getHelperDeck().get(9);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        assertDoesNotThrow(() ->  m.useHelperCard(helperCard, p1));

        MotherNature motherNature = m.getMotherNature();
        Island currPos = motherNature.getCurrPosition();
        assertTrue(currPos.isMotherNaturePresent());

        assertDoesNotThrow(() -> m.moveMotherNature(3, p1));

        Island nextPos = motherNature.getCurrPosition();
        assertFalse(currPos.isMotherNaturePresent());
        assertTrue(nextPos.isMotherNaturePresent());

    }

    /**
     * testing the movement of mother nature with an invalid number of steps
     */
    @Test
    void testMotherNatureMovementInvalid() {
        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        HelperCard helperCard = p1.getHelperDeck().get(9);

        AbstractMatch m = new TwoPlayersMatch();

        m.addPlayer(p1);
        m.addPlayer(p2);

        m.initializeMatch();

        assertDoesNotThrow(() ->  m.useHelperCard(helperCard, p1));

        MotherNature motherNature = m.getMotherNature();
        Island currPos = motherNature.getCurrPosition();
        assertTrue(currPos.isMotherNaturePresent());

        assertThrows(IllegalNumOfStepsException.class, () -> m.moveMotherNature(7, p1));

        assertTrue(currPos.isMotherNaturePresent());
    }
}