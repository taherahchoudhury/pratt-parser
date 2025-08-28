package com.example.prattparsergui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrattController implements Initializable {

    private final ParserExecute parserExecute = new ParserExecute();

    // All components contained in the selectPane
    @FXML
    private BorderPane selectPane;
    @FXML
    private Label labelExp;
    @FXML
    private ListView<String> listOfExpressions;
    @FXML
    private TextField textField;
    @FXML
    private Label labelError;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Button btnSelect;

    String[] expressions = {"10+10-5/2", "(2*2)/2", "(20+20)/5*2", "4*2+(2-1)","2+3+4","(15*4)+2/1-(5*2)-7", "(500*200)/40"};

    String currentExpression = "";

    // All components contained in the parserPane
    @FXML
    private BorderPane parserPane;

    @FXML
    private Label labelResult;

    @FXML
    private Text lblCurrentExp;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnTable;

    @FXML
    private Button btnBranch;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnExecute;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnFinish;

    @FXML
    private Button btnResult;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnReset;

    @FXML
    private TableView<ParsingStatus> tblResult;

    @FXML
    private TableColumn<ParsingStatus, String> tcTokens;

    @FXML
    private TableColumn<ParsingStatus, String> tcToken;

    @FXML
    private TableColumn<ParsingStatus, String> tcOp;

    @FXML
    private TableView<Operator> tblOperators;

    @FXML
    private TableColumn<Operator, String> tcOperators;

    @FXML
    private TableColumn<Operator, Integer> tcPrecedence;

    ObservableList<Operator> operators = FXCollections.observableArrayList(
            new Operator(1,"+"),
            new Operator(1,"-"),
            new Operator(2,"*"),
            new Operator(2,"/")
    );

    ObservableList<ParsingStatus> statusList = FXCollections.observableArrayList();


    ArrayList<String> parsingResultArray = new ArrayList<>();

    int count = 0;

    boolean hasStart = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpOperatorTable();
        setUpExpressionsList();
    }

    private void setUpTableView() {
        statusList.addAll(parserExecute.getStatusList());
        //set up columns for the table view
        tcTokens.setCellValueFactory(new PropertyValueFactory<>("tokens"));
        tcToken.setCellValueFactory(new PropertyValueFactory<>("token"));
        tcOp.setCellValueFactory(new PropertyValueFactory<>("operator"));
        //load data
        tblResult.setItems(statusList);
    }

    private void setUpOperatorTable() {
        //set up columns in the Operator table
        tcOperators.setCellValueFactory(new PropertyValueFactory<>("operator"));
        tcPrecedence.setCellValueFactory(new PropertyValueFactory<>("precedence"));
        //load data
        tblOperators.setItems(operators);
    }

    private void setUpExpressionsList() {
        //populate list with expressions
        listOfExpressions.getItems().addAll(expressions);
        //display selected expression as label
        listOfExpressions.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            btnSelect.setDisable(false);
            currentExpression = listOfExpressions.getSelectionModel().getSelectedItem();
            labelExp.setText(currentExpression);
        });
    }

    /*
    These are the on action methods used for the different button clicks and key presses.
    */
    public void onBtnTableClick(ActionEvent event) {
        displayTableView();
    }

    public void onBtnBranchClick(ActionEvent event) {
        displayBranchView();
    }

    private void displayTableView() {
        // display table view
        txtResult.setVisible(false);
        tblResult.setVisible(true);
        // disable
        btnTable.setDisable(true);
        btnBranch.setDisable(false);
        // hide navigation buttons
        setVisible(false);
    }

    private void displayBranchView() {
        tblResult.setVisible(false);
        txtResult.setVisible(true);
        btnBranch.setDisable(true);
        btnTable.setDisable(false);

        // show navigation buttons
        setVisible(true);

    }

    private void setVisible(boolean bool) {
        btnReset.setVisible(bool);
        btnNext.setVisible(bool);
        btnPrevious.setVisible(bool);
        btnFinish.setVisible(bool);
    }

    public void onTextFieldClick(MouseEvent event) {
        listOfExpressions.getSelectionModel().clearSelection();
        btnSelect.setDisable(true);
    }
    public void onBtnSelectClick(ActionEvent event) {
        displayParserPane();
    }

    public void onEnter(ActionEvent event) {
        currentExpression = textField.getText();
        String output = parserExecute.isValidExpression(currentExpression);
        if (Objects.equals(output, "valid")) {
            displayParserPane();
        } else {
            labelError.setVisible(true);
            labelError.setText(output);
        }
    }

    /*
    When start button is clicked the pratt parser is executed on the given expression.
    The result is then displayed on a label.
     */
    public void onBtnExecuteClick(ActionEvent event) {
        if (!hasStart) {
            startBranchView();
            startTableView();
            hasStart = true;
        }
    }

    private void startTableView() {
        clearTableView();
        setUpTableView();
        btnExecute.setDisable(true);
    }

    private void clearTableView() {
        // clear any data previously loaded
        statusList.clear();
        tblResult.getItems().clear();
        tblResult.refresh();
    }

    private void setDisable(boolean bool) {
        btnFinish.setDisable(bool);
        btnNext.setDisable(bool);
        btnPrevious.setDisable(bool);
        btnReset.setDisable(bool);
    }

    private void startBranchView() {
        btnTable.setDisable(false);
        clearTableView();
        count = 1;
        // enable navigation buttons
        setDisable(false);
        // execute algorithm
        executeParser();
    }

    private void executeParser() {
        // set the result label with the answer and enable the button to see parsed expression
        parserExecute.clearStatusList();
        parsingResultArray = parserExecute.getParsingResult(currentExpression);
        txtResult.setVisible(true);
        txtResult.setText(parsingResultArray.get(0));
        lblCurrentExp.setText("Result: " + currentExpression + " = " + parserExecute.getResultValue());
        btnResult.setDisable(false);
    }

    public void onBtnResultClick(ActionEvent event) {
        TextArea textArea = new TextArea();
        textArea.setText(parserExecute.getResultString());
        textArea.setEditable(false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Parsed Expression");
        alert.setHeaderText("Here is the final parsed expression");
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void onBtnNextClick(ActionEvent event) {
        if (!parsingResultArray.isEmpty()) {
            if (count < parsingResultArray.size()) {
                count++;
                setTextArea(count);
            }
        }
        if (count == parsingResultArray.size()) {
            btnFinish.setDisable(true);
            btnNext.setDisable(true);
        } else {
            btnFinish.setDisable(false);
        }
        btnPrevious.setDisable(false);
    }

    public void onBtnPreviousClick(ActionEvent event) {
        if (!parsingResultArray.isEmpty()) {
            if (count > 0) {
                count--;
                setTextArea(count);
            }
            btnNext.setDisable(false);
        }
        // cannot click the finish button if it is already finished
        btnFinish.setDisable(btnNext.isDisable());

        if (count == 0) {
            btnPrevious.setDisable(true);
        }
    }

    public void onBtnFinishClick(ActionEvent event) {
        if (!parsingResultArray.isEmpty()) {
            setTextArea(parsingResultArray.size());
        }
        count = parsingResultArray.size();
        btnFinish.setDisable(true);
        btnNext.setDisable(true);
        btnPrevious.setDisable(false);
    }

    private void setTextArea(int count) {
        txtResult.clear();
        for (int i = 0; i < count; i++) {
            txtResult.appendText(parsingResultArray.get(i));
        }
    }

    public void onBtnBackClick(ActionEvent event) {
        displaySelectPane();
    }

    public void onBtnResetClick(ActionEvent event) {
        hasStart = false;
        parsingResultArray.clear();
        txtResult.clear();

        // clear any data previously loaded
        statusList.clear();
        tblResult.getItems().clear();

        btnExecute.setDisable(false);
        btnTable.setDisable(true);
        setDisable(true);
    }

    /*
    The display methods switch from different panes
     */
    private void displayParserPane() {
        setVisible(true);
        btnExecute.setDisable(false);

        tblResult.setVisible(false);
        txtResult.setVisible(true);

        btnTable.setDisable(true);
        btnBranch.setDisable(true);
        btnResult.setDisable(true);

        selectPane.setVisible(false);
        parserPane.setVisible(true);

        lblCurrentExp.setText("Current expression: " + currentExpression + " ");
        txtResult.clear();
    }

    private void displaySelectPane() {
        count = 0;
        hasStart = false;
        textField.clear();
        parsingResultArray.clear();

        tblResult.getItems().clear();
        statusList.clear();

        selectPane.setVisible(true);
        parserPane.setVisible(false);
        labelResult.setVisible(false);

        setDisable(true);
    }
}

