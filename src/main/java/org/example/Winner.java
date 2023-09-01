package org.example;

import org.example.database.GsonParser;

public class Winner {
    public String hasComputerLose() {

        if (new Move().getComputerMove().isEmpty()) {
            return "Congratulations! You won! The computer can't find the next move.";
        } else {
            return "";
        }

    }

    public String userHasGivenUp(String textFromTextField) {

        if (textFromTextField.equals("I give up")) {
            return "Computer won!";
        } else {
            return "";
        }
    }

    public String wrongCity(String inputFromUser) {
        if (!new GsonParser().getCityNames().contains(inputFromUser)) {
            return "Wrong city";
        } else return "";
    }
}
