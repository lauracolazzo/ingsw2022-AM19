package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyPlayCharacterCardMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CharacterCardController implements SceneController {
    private Gui gui;
    private Drawer drawer;
    private List<AbstractCharacterCard> characterCards = new ArrayList<>();
    private AbstractCharacterCard cardChosen = null;

    public void setCharacterCards(List<AbstractCharacterCard> characterCards) {
        this.characterCards = characterCards;
        setLayout();
    }

    @Override
    public void setGui(Gui gui) {
        drawer = new Drawer();
        this.gui = gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {

    }

    @FXML
    private ImageView character1;

    @FXML
    private ImageView character2;

    @FXML
    private ImageView character3;

    @FXML
    private TextArea description1;

    @FXML
    private TextArea description2;

    @FXML
    private TextArea description3;

    @FXML
    private Label coinLabel;

    @FXML
    private GridPane onCardGrid1;

    @FXML
    private GridPane onCardGrid2;

    @FXML
    private GridPane onCardGrid3;


    @FXML
    void negativeReplyMessage(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),null));
    }

    @FXML
    void onMouseEnteredEvent(MouseEvent event) {
        event.getPickResult().getIntersectedNode().setCursor(Cursor.HAND);
    }

    @FXML
    void onMouseExitedEvent(MouseEvent event) {

    }

    @FXML
    void useCharacter1(MouseEvent event) {
        cardChosen = characterCards.get(0);
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(0).getId()));
    }

    @FXML
    void useCharacter2(MouseEvent event) {
        cardChosen = characterCards.get(1);
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(1).getId()));
    }

    @FXML
    void useCharacter3(MouseEvent event) {
        cardChosen = characterCards.get(2);
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(2).getId()));
    }

    private void setLayout(){
        character1.setImage(new Image(getImagePath(characterCards.get(0).getId())));
        description1.setText(getDescription(characterCards.get(0)));
        setStudentOnCard(characterCards.get(0),onCardGrid1);
        character2.setImage(new Image(getImagePath(characterCards.get(1).getId())));
        description2.setText(getDescription(characterCards.get(1)));
        setStudentOnCard(characterCards.get(1),onCardGrid2);
        character3.setImage(new Image(getImagePath(characterCards.get(2).getId())));
        description3.setText(getDescription(characterCards.get(2)));
        setStudentOnCard(characterCards.get(2),onCardGrid3);
        coinLabel.setText("You have:\n" + gui.getCache().getGameBoards().get(0).coins()+ " coins");
    }

    private void setStudentOnCard(AbstractCharacterCard card,GridPane onCardGrid){
        Map<PieceColor,Integer> studentOnCard = card.getStudents();
        if(studentOnCard!=null){
            int r=0;
            int c=0;
            for (PieceColor color : studentOnCard.keySet()){
                for(int k=0; k<studentOnCard.get(color);k++){
                    onCardGrid.add(drawer.createStudent(color, 20), c,r);
                    r++;
                    if(r==3){
                        c=2;
                        r=0;
                    }
                }
            }
        }

    }

    private String getImagePath(Character c){
        String path = "file:src/main/resources/it/polimi/ingsw/am19.View.GUI/CharacterCard/" + c + ".jpg";
        return path;
    }

    private String getDescription(AbstractCharacterCard c){
        String description = "Price: " + c.getPrice() + "\n" + c.getDescription();
        return description;
    }

    public AbstractCharacterCard getCardChosen() {
        return cardChosen;
    }
}

