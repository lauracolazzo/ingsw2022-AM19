package it.polimi.ingsw.am19.View.GUI.Controllers;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyLoginInfoMessage;
import it.polimi.ingsw.am19.View.GUI.Gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * A class for managing login scene
 */
public class LoginController implements SceneController {
        /**black tower Image**/
        private final Image blackTower = new Image(getClass().getResource("/Towers/blackTower.png").toExternalForm());
        /**white tower Image**/
        private final Image whiteTower = new Image(getClass().getResource("/Towers/whiteTower.png").toExternalForm());
        /**grey tower Image**/
        private final Image greyTower = new Image(getClass().getResource("/Towers/greyTower.png").toExternalForm());
        /**card's back Image for king wizard family**/
        private final Image king = new Image(getClass().getResource("/HelperCard/king.png").toExternalForm());
        /**card's back Image for witch wizard family**/
        private final Image witch = new Image(getClass().getResource("/HelperCard/witch.png").toExternalForm());
        /**card's back Image for warrior wizard family**/
        private final Image warrior = new Image(getClass().getResource("/HelperCard/warrior.png").toExternalForm());
        /**card's back Image for shaman wizard family**/
        private final Image shaman = new Image(getClass().getResource("/HelperCard/shaman.png").toExternalForm());

        /**black tower ImageView**/
        private final ImageView blackImageView = new ImageView(blackTower);
        /**white tower ImageView**/
        private final ImageView whiteTowerImageView = new ImageView(whiteTower);
        /**grey tower ImageView**/
        private final ImageView greyTowerImageView = new ImageView(greyTower);
        /**card's back ImageView for witch wizard family**/
        private final ImageView witchImageView = new ImageView(witch);
        /**card's back ImageView for king wizard family**/
        private final ImageView kingImageView = new ImageView(king);
        /**card's back ImageView for warrior wizard family**/
        private final ImageView warriorImageView = new ImageView(warrior);
        /**card's back ImageView for shaman wizard family**/
        private final ImageView shamanImageView = new ImageView(shaman);

        /**
         * gui's reference
         */
        private Gui gui;

        /**
         * sets warning label text content and makes it hidden,
         * then it sets keyboard event listeners that act like submit button was pressed
         */
        public void initialize(){
                warningLabel.setVisible(false);
                warningLabel.setText("Please fill out all fields");

                usernameField.setOnKeyPressed(this::enterKeyPressed);
                towerColorField.setOnKeyPressed(this::enterKeyPressed);
                wizardFamilyField.setOnKeyPressed(this::enterKeyPressed);
        }
        @FXML
        private Label warningLabel;

        @FXML
        private Button submitButton;

        @FXML
        private MenuButton towerColorField;

        @FXML
        private TextField usernameField;

        @FXML
        private MenuButton wizardFamilyField;

        /**
         * after pressing submitButton it manages all menus and fields content
         * @param event the event of pressing submitButton
         */
        @FXML
        void sendUserData(ActionEvent event) {
                sendUserData();
        }

        private TowerColor getTowerColor(String towerColor){
               return switch (towerColor.toLowerCase()){
                        case "white" ->  TowerColor.WHITE;
                        case "black"->  TowerColor.BLACK;
                        case "grey"->  TowerColor.GREY;
                        default -> null;
                };
        }

        private WizardFamily getWizardFamily(String wizardFamily){
                return switch (wizardFamily.toLowerCase()){
                        case "king" ->  WizardFamily.KING;
                        case "witch"->  WizardFamily.WITCH;
                        case "shaman"->  WizardFamily.SHAMAN;
                        case "warrior"->  WizardFamily.WARRIOR;
                        default -> null;
                };
        }

        public void setOptions(ArrayList<TowerColor> towerColors, ArrayList<WizardFamily> wizardFamilies){
                //if there's no option, display it as already selected
                if (towerColors.size() == 1)
                        towerColorField.setText(towerColors.get(0).toString().toLowerCase());

                //menus' options setup
                for (TowerColor color: towerColors)
                        switch (color) {
                                case BLACK -> towerColorField.getItems().add(
                                        new MenuItem(color.toString().toLowerCase(), blackImageView));
                                case WHITE -> towerColorField.getItems().add(
                                        new MenuItem(color.toString().toLowerCase(), whiteTowerImageView));
                                case GREY -> towerColorField.getItems().add(
                                        new MenuItem(color.toString().toLowerCase(), greyTowerImageView));
                        }

                for (WizardFamily wizardFamily: wizardFamilies)
                        switch (wizardFamily) {
                                case WARRIOR -> addCardToMenuOptions(wizardFamily,warriorImageView);
                                case WITCH -> addCardToMenuOptions(wizardFamily,witchImageView);
                                case KING -> addCardToMenuOptions(wizardFamily,kingImageView);
                                case SHAMAN -> addCardToMenuOptions(wizardFamily,shamanImageView);
                        }

                //event handlers for clicking on MenuButtons events
                EventHandler<ActionEvent> clickOnTowerMenuItem = (e) ->
                                towerColorField.setText(((MenuItem)e.getSource()).getText());

                EventHandler<ActionEvent> clickOnWizardFamMenuItem = (e) ->
                                wizardFamilyField.setText(((MenuItem)e.getSource()).getText());

                //event handlers for clicking on MenuItems events
                for (MenuItem item: towerColorField.getItems())
                        item.setOnAction(clickOnTowerMenuItem);

                for (MenuItem item: wizardFamilyField.getItems())
                        item.setOnAction(clickOnWizardFamMenuItem);

        }

        /**
         * sets gui's reference
         * @param gui id the gui's reference
         */
        @Override
        public void setGui(Gui gui) {
                this.gui = gui;
        }

        @Override
        public void showGenericMsg(GenericMessage msg) {
        }

        private boolean checkInputValidity() {
                return usernameField.getText() != null && !usernameField.getText().equals("")
                        && !towerColorField.getText().equals("Tower color") //default option still set
                        && !wizardFamilyField.getText().equals("Wizard family"); //default option still set
        }

        private void sendUserData(){
                if (!checkInputValidity())
                        warningLabel.setVisible(true);
                else{
                        gui.setNickname(usernameField.getText());
                        gui.getMyClient().sendMessage(new ReplyLoginInfoMessage(
                                usernameField.getText(),
                                getTowerColor(towerColorField.getText()),
                                getWizardFamily(wizardFamilyField.getText())));
                }
        }

        private void enterKeyPressed(KeyEvent e){
                if( e.getCode() == KeyCode.ENTER )
                        sendUserData();
        }

        private void addCardToMenuOptions(WizardFamily wizardFamily,ImageView iv){
                iv.setFitWidth(98.8);
                iv.setFitHeight(149.8);
                wizardFamilyField.getItems().add(new MenuItem(wizardFamily.toString().toLowerCase(),iv));
        }
}
