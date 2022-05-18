package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchControllerTest {
    @Test
    public void twoPlayersNoExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(2,false);
        assertEquals(2,c.getModel().getNumOfPlayers());
        assertFalse(c.getModel() instanceof ExpertMatchDecorator);
    }

    @Test
    public void threePlayersNoExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(3,false);
        assertEquals(3,c.getModel().getNumOfPlayers());
        assertFalse(c.getModel() instanceof ExpertMatchDecorator);
    }

    @Test
    public void twoPlayersExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(2,true);
        assertEquals(2,c.getModel().getNumOfPlayers());
        assertTrue(c.getModel() instanceof ExpertMatchDecorator);
    }

    @Test
    public void threePlayersExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(3,true);
        assertEquals(3,c.getModel().getNumOfPlayers());
        assertTrue(c.getModel() instanceof ExpertMatchDecorator);
    }

    @Test
    public void addPlayer(){
        MatchController c = new MatchController();
        c.createNewMatch(2,false);
        assertTrue(c.getModel().getPlanningPhaseOrder().isEmpty());

        c.addPlayer("Laura", TowerColor.WHITE, WizardFamily.WITCH);
        assertEquals(1,c.getModel().getPlanningPhaseOrder().size());
    }
}


