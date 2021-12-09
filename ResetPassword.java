package footballApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResetPassword extends JFrame {
    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;


    private final JPasswordField new_Password;
    private final JPasswordField repeat_Password;
    private final JTextField forgotLoginField;


    public ResetPassword(){
        setSize(400, 500);
        setTitle("Reset Password");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        connection = Connect.ConnectDb();
        JLabel username = new JLabel("Username");
        setBounds(50, 30, 90, 30);
        add(username);

        JTextField login_ = new JTextField();
        setBounds(150, 70, 90, 30);
        add(login_);

        JLabel forgotLogin = new JLabel("Login:");
        forgotLogin.setBounds(50, 60, 100, 30);
        add(forgotLogin);

        JLabel password1 = new JLabel("New Password");
        password1.setBounds(35, 110, 120,30);
        add(password1);

        JLabel password2 = new JLabel("Repeat Password");
        password2.setBounds(30, 150, 120, 30);
        add(password2);

        new_Password = new JPasswordField();
        new_Password.setBounds(150, 100, 130, 30);
        add(new_Password);

        repeat_Password = new JPasswordField();
        repeat_Password.setBounds(150, 140, 130, 30);
        add(repeat_Password);

        JButton change = new JButton("Change");
        change.setBounds(100, 190, 100, 30);
        add(change);

        forgotLoginField = new JTextField();
        forgotLoginField.setBounds(150, 60, 130, 30);
        add(forgotLoginField);

        JButton goBack = new JButton("Back");
        goBack.setBounds(100, 230, 100, 30);
        add(goBack);

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password_1 = String.valueOf(new_Password.getPassword());

                String login=forgotLoginField.getText();


                if(!(String.valueOf(new_Password.getPassword()).equals(String.valueOf(repeat_Password.getPassword())))){
                    JOptionPane.showMessageDialog(null,"Passwords are not same! ");
                }

                try {
                    String sql = "UPDATE users SET password=? WHERE login=? ";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, password_1);
                    preparedStatement.setString(2, login);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Your password successfully changed!");

                }
                catch (Exception exc){
                    exc.printStackTrace();
                }
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                setVisible(false);

                MyProfile m = new MyProfile();
                m.setVisible(true);

            }
        });



    }
}


