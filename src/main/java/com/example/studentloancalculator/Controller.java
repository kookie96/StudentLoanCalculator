package com.example.studentloancalculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Variables
    // Use myListView.setItems(observableList) to set data in the List View component
    // for the data type below
    ObservableList<String> observableList = FXCollections.observableArrayList("1", "2", "3", "4",
            "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");

    String [] years = {"1", "2", "3", "4",
            "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    String[] rates = {"3.00", "3.50", "4.00", "4.50", "5.00", "5.50", "6.00", "6.50", "7.00"};

    double annualPayment;
    double interestRate;
    float loanAmount;
    int currentYear;

    //Components in FXML File
    @FXML
    private Label errorMessage;

    @FXML
    private TextField userLoanAmount;

    @FXML
    private ComboBox<String> myComboBox = new ComboBox<>();

    @FXML
    private Button calculatePayment;

    @FXML
    private CheckBox noMissedPay;

    @FXML
    private CheckBox autoWithdrawal;

    @FXML
    private RadioButton isDeferment;

    @FXML
    private RadioButton isNotDeferment;

    @FXML
    private ToggleGroup tgDeferment;

    @FXML
    private Label finalCalculation;

    @FXML
    private ListView<String> myListView = new ListView<>();

    //Initializing List View and storing selected year
    //Also initializing Combo Box and storing selected interest rate
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myListView.getItems().addAll(years);
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentYear = Integer.parseInt(myListView.getSelectionModel().getSelectedItem());
            }
        });

        myComboBox.getItems().addAll(rates);
        myComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                interestRate = Float.parseFloat(myComboBox.getSelectionModel().getSelectedItem());
            }
        });
    }

    //Calculating the annual payment for the user by clicking the "calculate" button
    @FXML
    protected void onCalculateButtonClick() {
        try {
            loanAmount = Float.parseFloat(userLoanAmount.getText());

            if (noMissedPay.isSelected()) {
                interestRate -= 0.25;
            }

            if (autoWithdrawal.isSelected()) {
                interestRate -= 0.25;
            }

            if (isDeferment.isSelected()) {
                annualPayment = 0;
            } else if (isNotDeferment.isSelected()) {
                annualPayment = ((interestRate / 100) * loanAmount) /
                        (1 - Math.pow(1 + (interestRate / 100), -(currentYear)));
            }

            //Displaying the annual payment and rounding the annual
            //payment to 2 decimal places using the Decimal Format class
            DecimalFormat df = new DecimalFormat("#.##");
            finalCalculation.setText("$" + df.format(annualPayment));

            //** Another way to round final annual payment to 2 decimal places
            /*
            finalCalculation.setText(String.format("$%.2f",annualPayment));
            */
        } catch (NumberFormatException nfe) {
            //Catch exception if user doesn't input a number
            errorMessage.setText("WARNING! Enter a valid amount.");

        } catch (Exception e) {
            //Catch any other exception that might be thrown
            errorMessage.setText(e.getMessage());
        }
    }
    @FXML
    protected void hideErrorMessage(MouseEvent event) {
        errorMessage.setText("");
    }

}