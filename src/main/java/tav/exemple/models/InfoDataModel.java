package tav.exemple.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InfoDataModel {

    private final StringProperty countComs;

    public InfoDataModel(String countComs){
        this.countComs = new SimpleStringProperty(countComs);
    }
    public String getCountComs() {
        return countComs.get();
    }
    public void  setCountComs(String countComs){

        this.countComs.set(countComs);
//        System.out.println(countComs);
    }


    public StringProperty countComsProperty() {
        return this.countComs;
    }

}
