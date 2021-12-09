package footballApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class SignUp extends JFrame implements Add{
    Connection connection;

    ResultSet resultSet;

    PreparedStatement preparedStatement;

    private final JTextField nameField;
    private final JLabel moneyCountField;
    private final JTextField login;
    private final JPasswordField password;
    private JLabel SIGNUP;

    private final JTextField secretField;
    private final JTextField answerField;

    private JButton back;

    public  SignUp(){
        connection = Connect.ConnectDb();
        setSize(400, 600);
        setTitle("SignUp");
        setResizable(false);
        setVisible(true);
        setLayout(null);


        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(30, 50, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 50, 100, 30);
        add(nameField);

        JLabel moneyCountLabel = new JLabel("Money: ");
        moneyCountLabel.setBounds(30, 90, 100, 30);
        add(moneyCountLabel);

        moneyCountField = new JLabel();
        moneyCountField.setBounds(130, 90, 100, 30);
        add(moneyCountField);
        moneyCountField.setText(Integer.toString(Main.sum));

        JLabel loginLabel = new JLabel("New login");
        loginLabel.setBounds(30, 130, 100, 30);
        add(loginLabel);

        login = new JTextField();
        login.setBounds(130, 130, 100, 30);
        add(login);

        JLabel passwordLabel = new JLabel("New password");
        passwordLabel.setBounds(30,170, 100, 30);
        add(passwordLabel);


        password = new JPasswordField();
        password.setBounds(130,170, 100, 30);
        add(password);

        JLabel secretLabel = new JLabel("Enter Secret '?'");
        secretLabel.setBounds(30,210,100,30);
        add(secretLabel);

        secretField=new JTextField();
        secretField.setBounds(130,210,100,30);
        add(secretField);
        JLabel answerLabel = new JLabel("Secret Answer");
        answerLabel.setBounds(30,250,100,30);
        add(answerLabel);
        answerField=new JTextField();
        answerField.setBounds(130,250,100,30);
        add(answerField);


        JButton createAcc = new JButton("SignUp");
        createAcc.setBounds(130,300,80,30);
        add(createAcc);

        back = new JButton("Back");
        back.setBounds(130, 340, 80, 30);
        add(back);


        createAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(login.getText().equalsIgnoreCase("admin")){
                    JOptionPane.showMessageDialog(null, "You can't create account");
                }
                if ((login.getText() != "" && login.getText() != null) && (String.valueOf(password.getPassword()) != "" && String.valueOf(password.getPassword()) != null) && (nameField.getText() != "" && nameField.getText() != null) && (moneyCountField.getText() != "" && moneyCountField.getText() != null) && (secretField.getText() != "" && secretField.getText() != null) && (answerField.getText() != "" && answerField.getText() != null)) {
                    try {
                        String query = "insert into users(login, password, name, moneyCount, secret, answer) values (?,?,?,?,?,?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, login.getText());
                        preparedStatement.setString(2, String.valueOf(password.getPassword()));
                        preparedStatement.setString(3, nameField.getText());
                        preparedStatement.setString(4, moneyCountField.getText());
                        preparedStatement.setString(5, secretField.getText());
                        preparedStatement.setString(6, answerField.getText());
                        preparedStatement.execute();
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    }
                   add();
                } else {
                    JOptionPane.showMessageDialog(null, "Try again!");
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.sum=0;
                setVisible(false);
                Main.LoginApp log = new Main.LoginApp();
                log.setVisible(true);
            }
        });
    }

    @Override
    public void add() {
        JOptionPane.showMessageDialog(null, "New account created!");
    }
}


