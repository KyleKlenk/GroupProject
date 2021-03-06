package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class InputDialog extends Dialog {

    static protected TextField createDoubleInputField(){
        TextField doubleField = new TextField();
        doubleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    doubleField.setText(oldValue);
                }
            }
        });
        return doubleField;
    }

    static protected HBox createTimeBox(Spinner<Integer> hour, Spinner<Integer> minute, ToggleGroup groupAMPM) {
        HBox timeBox = new HBox();
        hour.setPrefWidth(70);
        minute.setPrefWidth(70);
        RadioButton am = new RadioButton("AM");
        am.setToggleGroup(groupAMPM);
        am.setSelected(true);
        RadioButton pm = new RadioButton("PM");
        pm.setToggleGroup(groupAMPM);
        timeBox.getChildren().addAll(hour, minute, am, pm);
        return timeBox;
    }
}
