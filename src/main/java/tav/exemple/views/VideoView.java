package tav.exemple.views;


import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/** Класс интерфейса */
public class VideoView {

    private Parent view;

    private TextArea inputTextArea;
    private ChoiceBox choiceNumberPort;
    private Button updateComPort;
    private Button receivData;
    private Button stopData;
    private Label statePort;
    private Text countData;
    private Text readBytes;
    private Text infoBytes;

    public VideoView() {
        view = createView();
    }

    public ChoiceBox getChoiceNumberPort() {
        return choiceNumberPort;
    }
    public Button getReceivData() {
        return receivData;
    }
    public Button getStopDataData() {
        return stopData;
    }
    public Button getUpdateComPort() {
        return updateComPort;
    }

    public Label getStatePort() {
        return statePort;
    }

    public Text getCountData() {
        return countData;
    }

    public Text getReadBytes() {
        return readBytes;
    }

    public Text getInfoBytes() {
        return infoBytes;
    }


    public TextArea getInputTextArea() {
        return inputTextArea;
    }

    private SplitPane createView() {

        SplitPane splitPane = new SplitPane();
        splitPane.setPrefWidth(800);
        splitPane.setPrefHeight(400);

        VBox leftControl  = new VBox(20);
        leftControl.setPadding(new Insets(15));
        leftControl.setAlignment(Pos.TOP_CENTER);

        VBox rightControl = new VBox(20);
        rightControl.setMinWidth(Region.USE_PREF_SIZE);
        rightControl.setPrefWidth(500);
        rightControl.setPadding(new Insets(15));
        rightControl.setAlignment(Pos.TOP_CENTER);

        leftControl.getChildren().add(createControlPanel());

        rightControl.getChildren().add(createVideo());

        splitPane.getItems().addAll(leftControl, rightControl);

        return splitPane;
    }

    private Node createControlPanel() {
        VBox vBox = new VBox(15);
        HBox hBox1 = new HBox(15);
        HBox hBox2 = new HBox(15);
        HBox hBox3 = new HBox(15);

        choiceNumberPort = new ChoiceBox();
        choiceNumberPort.setPrefWidth(100);

        statePort = new Label();
        countData = new Text("Посылки:");
        readBytes = new Text("Считано байт:");
        infoBytes = new Text("Иформационные байты:");

        updateComPort = new Button("Обновить порты");
        receivData = new Button("Принять данные");
        stopData = new Button("Закрыть порт");

        hBox1.getChildren().addAll(updateComPort, choiceNumberPort);
        hBox1.setAlignment(Pos.BASELINE_LEFT);
        hBox2.getChildren().addAll(receivData, statePort);
        hBox3.getChildren().addAll(stopData);
        vBox.getChildren().addAll(hBox1,hBox2, hBox3,countData, readBytes, infoBytes);
        return vBox;
    }
    /** Метод создать поле для видео */
    private Node createVideo() {
        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.TOP_CENTER);

        Label titleTextArea = new Label("Получаемые данные");
        titleTextArea.setStyle("-fx-font-weight: 500; -fx-padding: 4px; -fx-font-size: 13pt; -fx-alignment: center;");

        inputTextArea= new TextArea("input");
        inputTextArea.setPrefWidth(400);
        inputTextArea.setPrefHeight(60);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(inputTextArea);

        vBox.getChildren().addAll(titleTextArea, inputTextArea);
        return vBox;
    }

    public Parent getView() {
        return view;
    }
}

