package tav.exemple;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import tav.exemple.controllers.ComPortRead;
import tav.exemple.controllers.NumberPorts;
import tav.exemple.views.VideoView;

/**
 * Главный класс приложения videoInterfece
 * @author Тумазов Андрей
 */
public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage)  {

        VideoView view = new VideoView();
        NumberPorts numberPorts = new NumberPorts(view);
        ComPortRead CcmPort = new ComPortRead(view);

        primaryStage.setTitle("Видео интрефейс");
        primaryStage.setScene(new Scene(view.getView()));
        primaryStage.show();
    }
    /** Стартовый метод приложения */
    public static void main(String[] args) {
        launch(args);
    }

}

