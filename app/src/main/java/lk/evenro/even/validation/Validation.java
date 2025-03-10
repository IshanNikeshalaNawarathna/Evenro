package lk.evenro.even.validation;

public class Validation {

    public static boolean isEmailValid(String email){
        return email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
    public static boolean isPasswordValid(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");
    }

    public static boolean isDouble(String text){
        return text.matches("^\\d+(\\.\\d{2})?$");
    }

    public static boolean isInteger(String text){
        return text.matches("^\\d+$");
    }

}
