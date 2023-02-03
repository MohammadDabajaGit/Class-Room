package sample.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Model.DataSource;
import sample.Model.User;

import java.util.regex.Pattern;

public class LoginRegisterController {
    @FXML
    public TextField registerName;
    public TextField registerEmail;
    public PasswordField registerPassword;

    public GridPane registerPane;

    public Label emailError;
    public Label passwordError;
    public Label nameError;
    public Label registrationConfirm;
    public RadioButton registerStudent;
    public RadioButton registerTeacher;
    public TextField loginEmail;
    public PasswordField loginPassword;
    public RadioButton loginStudent;
    public RadioButton loginTeacher;
    public RadioButton loginAdmin;
    public GridPane inputPane;
    public GridPane loginPane;

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidName(String name) {
        String nameRegex = "(?=.{3,})";

        Pattern pat = Pattern.compile(nameRegex);
        if (name == null)
            return false;
        return pat.matcher(name).lookingAt() ;
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "(?=.{8,})";

        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null)
            return false;
        return pat.matcher(password).lookingAt() ;
    }

    public void register(ActionEvent actionEvent) {
        String name = registerName.getText();
        String email = registerEmail.getText();
        String password = registerPassword.getText();
        String role = null;


        //set style to normal
        registerEmail.setStyle("");
        registerPane.getChildren().removeAll(emailError);

        registerPassword.setStyle("");
        registerPane.getChildren().removeAll(passwordError);

        registerName.setStyle("");
        registerPane.getChildren().removeAll(nameError);

        registerPane.setStyle("");
        registerPane.getChildren().removeAll(registrationConfirm);

        // all fields are valid
        boolean valid = true;

        // check if name is valid
        if(!isValidName(name)) {
            registerName.setText("");
            registerName.setStyle("-fx-border-color: red; -fx-border-radius: 3;");
            nameError = new Label("the name should be 3+ letters");
            nameError.setTextFill(Color.RED);
            registerPane.add(nameError,1,1,2,1);
            valid = false;
        }

        // check if pass is valid
        if(!isValidPassword(password)) {
            registerPassword.setText("");
            registerPassword.setStyle("-fx-border-color: red; -fx-border-radius: 3;");
            passwordError = new Label("the password should be 8+ letters");
            passwordError.setTextFill(Color.RED);
            registerPane.add(passwordError,1,5,2,1);
            valid = false;
        }

        // check email is valid
        if(!isValidEmail(email)) {
            registerEmail.setText("");
            registerEmail.setStyle("-fx-border-color: red; -fx-border-radius: 3;");
            emailError = new Label("the email is invalid");
            emailError.setTextFill(Color.RED);
            registerPane.add(emailError,1,3,2,1);
            valid = false;
        }

        //check if resister as student or teacher
        if(registerStudent.isSelected()) {
            role = "student";
        }

        if(registerTeacher.isSelected()) {
            role = "teacher";
        }

        if(valid) {
            valid = DataSource.getInstance().insertUser(name,email,password,role);
            if (!valid) {
                registerEmail.setText("");
                registerEmail.setStyle("-fx-border-color: red; -fx-border-radius: 3;");
                emailError = new Label("the email is already registered");
                emailError.setTextFill(Color.RED);
                registerPane.add(emailError,1,3,2,1);
            }
            else {
                registerPane.setStyle("-fx-border-color: green; -fx-border-radius: 3;");
                registrationConfirm = new Label("Registration Completed");
                registrationConfirm.setTextFill(Color.GREEN);
                registerPane.add(registrationConfirm,1,7,2,1);
            }
        }
    }

    public void login(ActionEvent actionEvent) {
        String email = loginEmail.getText();
        String password = loginPassword.getText();
        String role = "";

        //set style to normal
        registerEmail.setStyle("");
        loginPassword.setStyle("");
        loginPane.setStyle("");
        registerPane.getChildren().removeAll(emailError,passwordError,nameError,registrationConfirm);


        if(loginStudent.isSelected()) {
            role = "student";
        }
        if (loginTeacher.isSelected()) {
            role = "teacher";
        }
        if (loginAdmin.isSelected()) {
            role = "admin";
        }
        ObservableList<User> users =  DataSource.getInstance().queryUser("","",email,"","",role);
        if(users.isEmpty()) {
            loginEmail.setText("");
            loginEmail.setStyle("-fx-border-color: red; -fx-border-radius: 3;");
            emailError = new Label("email not found, please register first");
            emailError.setTextFill(Color.RED);
            loginPane.add(emailError,1,3,3,1);
        }
        else {
            users = DataSource.getInstance().queryUser("","",email,password,"",role);
            if(users.isEmpty()) {
                loginPassword.setText("");
                loginPassword.setStyle("-fx-border-color: red; -fx-border-radius: 3;");
                passwordError = new Label("wrong password");
                passwordError.setTextFill(Color.RED);
                loginPane.add(emailError,1,3,3,1);
            }
            else {
                users = DataSource.getInstance().queryUser("","",email,password,"true",role);
                if(users.isEmpty()) {
                    loginPassword.setText("");
                    loginEmail.setText("");
                    loginPane.setStyle("-fx-border-color: red; -fx-border-radius: 3;");
                    passwordError = new Label("your account is not active");
                    passwordError.setTextFill(Color.RED);
                    loginPane.add(passwordError,1,3,3,1);
                }
                else {
                    User user = users.get(0);
                    Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    try {
                        if (role.equals("student")) {

                        }
                        if (role.equals("teacher")) {
                            Parent root = FXMLLoader.load(getClass().getResource("../View/teacher.fxml"));
                            window.setTitle("Teacher Page");
                            window.setScene(new Scene(root, 800, 700));
                            window.show();
                        }
                        if (role.equals("admin")) {
//                            Admin admin = DataSource.getInstance().queryAdmin("",user.getEmail(),"").get(0);
//                            FXMLLoader fxmlLoader = new FXMLLoader();
//                            fxmlLoader.setLocation(getClass().getResource("../View/admin.fxml"));
//                            Parent root = fxmlLoader.load();
//                            window.setScene(new Scene(root));
//                            AdminController adminController = fxmlLoader.getController();
//                            adminController.admin = admin;
//                            window.show();

                            Parent root = FXMLLoader.load(getClass().getResource("../View/admin.fxml"));
                            window.setTitle("Admin Page");
                            window.setScene(new Scene(root, 800, 700));
                            window.show();
                        }
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
