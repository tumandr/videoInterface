package tav.exemple.controllers;

import jssc.SerialPortList;
import tav.exemple.views.VideoView;

/**
 * Класс Контроллер обновить порты
 */
public class NumberPorts {

    public NumberPorts(VideoView view) {
        numberPort (view);
        view.getUpdateComPort().setOnAction(event -> {
            view.getChoiceNumberPort().getItems().clear();
            numberPort (view);
        });
    }

    public void numberPort (VideoView view){
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            String port = portNames [i];
            view.getChoiceNumberPort().setValue(port);
            view.getChoiceNumberPort().getItems().add(port);
        }
    }

}
