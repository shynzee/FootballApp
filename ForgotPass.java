package footballApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ForgotPass extends JFrame {
    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;

    private final JTextField TextUsername;
    private final JTextField TextSecretPass;
    private final JTextField TextAnswer;
    private final JTextField TextYourPass;


    public ForgotPass(){
        connection = Connect.ConnectDb();
        setSize(500,400);
        setTitle("Forgot Password");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel username = new JLabel("Login");
        username.setBounds(50,20,100,20);
        add(username);

        JLabel secret = new JLabel("Secret Question");
        secret.setBounds(50,50,100,20);
        add(secret);

        JLabel answer = new JLabel("Answer");
        answer.setBounds(50,80,100,20);
        add(answer);

        JLabel password = new JLabel("Your password");
        password.setBounds(50,110,100,20);
        add(password);

        TextUsername = new JTextField();
        TextUsername.setBounds(170,20,200,20);
        add(TextUsername);

        TextSecretPass = new JTextField();
        TextSecretPass.setBounds(170,50,200,20);
        add(TextSecretPass);

        TextAnswer = new JTextField();
        TextAnswer.setBounds(170,80,200,20);
        add(TextAnswer);

        TextYourPass = new JTextField();
        TextYourPass.setBounds(170,110,200,20);
        add(TextYourPass);

        JButton search = new JButton("Search");
        search.setBounds(380,20,90,20);
        add(search);

        JButton retrieve = new JButton("Retrieve");
        retrieve.setBounds(380,80,90,20);
        add(retrieve);

        JButton back = new JButton("Back");
        back.setBounds(380,110,90,20);
        add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                setVisible(false);

                Main.LoginApp m = new Main.LoginApp();
                m.setVisible(true);

            }
        });

        retrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Retrieve();
            }
        });


        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Search();
            }
        });
    }

    public void Search(){
        try {
            String text = TextUsername.getText();
            String query = "select * from users where Login ='" + text + "'" ;
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            if (resultSet.next()){
                JOptionPane.showMessageDialog(null,"It is login true");
                String secretP = resultSet.getString("secret");
                System.out.println(secretP);
                TextSecretPass.setText(secretP);
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Retrieve(){
        try{
            String a2 = TextAnswer.getText();
            String query = "select * from users where answer = '" + a2 + "'";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()){
                String ans = resultSet.getString("answer");
                String pas = resultSet.getString("password");

                if (a2.equalsIgnoreCase(ans)){
                    JOptionPane.showMessageDialog(null, "Correct");
                    TextYourPass.setText(pas);
                }else {
                    JOptionPane.showMessageDialog(null,"Incorrect answer");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}



