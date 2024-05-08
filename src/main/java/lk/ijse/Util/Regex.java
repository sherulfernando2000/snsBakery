package lk.ijse.Util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Regex {
    public static boolean isTextFieldValid(lk.ijse.Util.TextField textField, String text){
        String filed = "";

        switch (textField){
            case CID:
                filed = "^([C][0-9]{3,10})$";
                break;
            case NAME:
                filed = "^[A-Za-z]{3,}(?:\\s[A-Za-z]{3,})?$";   /*"^[A-z|\\\\s]{3,}$"*/   /*"^[A-Za-z]+(?:\\s[A-Za-z]+)?$"*/
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case PHONENO:
                filed = "^\\d{10}$";
                break;
            case ADDRESS:
                filed = "^([A-z0-9]|[-/,.@+]|\\\\s){4,}$";
                break;
            case SID:
                filed = "^([S][0-9]{3,10})$";
                break;
            case IID:
                filed = "^([I][0-9]{3,10})$";
                break;
            case PRICE:
                filed = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case QTY:
                filed = "^\\d+$";
                break;
            case EID:
                filed = "^([E][0-9]{3,10})$";
                break;
            case PID:
                filed = "^([P][0-9]{3,10})$";
                break;
            case PASSWORD:
                filed = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
                break;



        }

       /* ID : "^([A-Z0-9])$"
        LANKA_ID : "^([0-9]{9}[x|X|v|V]|[0-9]{12})$"
        NAME : "^[A-z|\\s]{3,}$"
        EMAIL : "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$"
        ADDRESS : "^([A-z0-9]|[-/,.@+]|\\s){4,}$"
        PHONE : "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$"
        DOUBLE : "^([0-9]){1,}[.]([0-9]){1,}$"
        INVOICE : "^([0-9]){1,}$"
        NONE_CHARACTER : "^[\\W]{1,}$"
        INT : "^\\d+$"*/

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(lk.ijse.Util.TextField location, TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-border-color: green;");
            //textField.setStyle("-fx-");
            return true;
        }else {
            textField.setStyle("-fx-border-color: red;");
            return false;
        }
    }

   /* public static void setTextColor(TextField textField, boolean isValid) {
        if (isValid) {
            textField.setStyle("-fx-text-inner-color: green;");
        } else {
            textField.setStyle("-fx-text-inner-color: red;");
        }
    }*/


}
