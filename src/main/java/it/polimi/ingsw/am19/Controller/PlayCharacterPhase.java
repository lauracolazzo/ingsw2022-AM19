package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.CharacterCards.*;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;

public class PlayCharacterPhase extends AbstractPhase implements Phase{
    private final String currPlayer;
    private AbstractCharacterCard  card;
    public PlayCharacterPhase(MatchController matchController) {
        super(matchController);
        this.currPlayer = matchController.getCurrPlayer();
    }

    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            switch (msg.getMessageType()){
                case PLAY_CHARACTER_CARD -> {
                    ReplyPlayCharacterCardMessage message = (ReplyPlayCharacterCardMessage) msg;
                    playCharacterCard(message);
                }

                case REPLY_CHARACTER_PARAMETER -> {
                    ReplyCharacterParameterMessage message = (ReplyCharacterParameterMessage) msg;
                    activateCardEffect(message);
                }
            }
        }
    }

    @Override
    public void performPhase(String currPlayer) {

    }

    @Override
    public void initPhase() {

    }

    private void askParameters(){
        boolean requireColor = false, requireIsland = false, requireListColor = false;
        if (card.isRequiringIsland())
            requireIsland = true;
        if (card.isRequiringPieceColor())
            requireColor = true;
        if (card.isRequiringPieceColorList())
            requireListColor = true;
        matchController.sendMessage(currPlayer,
                new AskCharacterParameterMessage(requireColor,requireIsland, requireListColor));
        System.out.println("parameters sent");
    }


    private void playCharacterCard(ReplyPlayCharacterCardMessage message){
        //this.card = message.getCardToUse();
        if (message.getCardToUse() == null)//the client doesn't want to play a card. Let's go back to action phase
            goBackToPrevPhase();
        else{
            card = getCharacterById(message.getCardToUse());
            askForParameters();
        }
    }

    private void goBackToPrevPhase(){
        Phase prevPhase = matchController.getRoundsManager().getPrevPhase();
        matchController.getRoundsManager().changePhase(prevPhase);
        switch (((ActionPhase)prevPhase).getCurrStep()){
            case MOVE_STUD ->
                matchController.sendMessage(currPlayer,new AskEntranceMoveMessage());
            case MOVE_MN ->
                matchController.sendMessage(currPlayer,new AskMotherNatureStepMessage());
            case TAKE_STUD ->
                matchController.sendMessage(currPlayer, new AskCloudMessage(model.getNonEmptyClouds()));
        }
    }

    private void askForParameters(){
        if (inputController.checkIsCharacterAvailable(card) &&
                inputController.checkAffordability(card))
            askParameters();
        else
            card = null;
    }

    private AbstractCharacterCard getCharacterById(Character id){
       for (AbstractCharacterCard characterCard : ((ExpertMatchDecorator)model).getCharacterCards()){
           if (characterCard.getId() == id)
               return characterCard;
       }
       return null;
    }

    private void activateCardEffect(ReplyCharacterParameterMessage message){
        PieceColor color = message.getColor();
        Integer islandIndex = message.getIsland();
        Island island = null;
        if (islandIndex!=null)
            island = model.getIslandManager().getIslands().get(islandIndex);
        List<PieceColor> colorList = message.getColorList();

        /*if (inputController.checkIsInArchipelago(islandIndex) &&
                inputController.checkValidColor(color) &&
                inputController.checkValidColor(colorList)
                ) {
         */
            try {
                ((ExpertMatchDecorator)model).playCard(card,color,island,colorList);
                ((ActionPhase)matchController.getRoundsManager().getPrevPhase()).setCardPlayed(true);
                goBackToPrevPhase();
            } catch (InsufficientCoinException e) {
                Phase prevPhase = matchController.getRoundsManager().getPrevPhase();
                matchController.getRoundsManager().changePhase(prevPhase);
            } catch (NoSuchColorException | TooManyStudentsException e) {
                matchController.sendMessage(currPlayer,new ErrorMessage("server","Invalid parameters"));
            }
        //}
    }
}
