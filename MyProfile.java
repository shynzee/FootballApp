package footballApp ;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class MyProfile extends JFrame {

    private JLabel nameField;
    private JLabel moneyField;

    private JButton changeName;
    private JButton changePassword;
    private JButton addMoney;
    private JButton deleteAccount;
    private JButton back;

    private  JTextField changeNameField;
    private JTextField addMoneyField;

    Connection connection;

    ResultSet resultSet;

    PreparedStatement preparedStatement;

    public MyProfile() {

        connection = Connect.ConnectDb();
        setSize(400, 500);
        setTitle("MyProfile");
        setResizable(false);
        setVisible(true);
        setLayout(null);

        String userLogin = Main.LoginApp.getLogin();


        try {
            JLabel loginLabel = new JLabel("Your login");
            loginLabel.setBounds(30, 30, 100, 25);
            add(loginLabel);

            JLabel nameLabel = new JLabel("Your name");
            nameLabel.setBounds(30, 60, 140, 25);
            add(nameLabel);

            JLabel moneyLabel = new JLabel("Your money count");
            moneyLabel.setBounds(30, 90, 140, 25);
            add(moneyLabel);
            JLabel myTicketsLabel = new JLabel("My tickets");
            myTicketsLabel.setBounds(30, 210, 140, 25);
            add(myTicketsLabel);

            JLabel loginField = new JLabel();
            loginField.setBounds(160, 30, 140, 25);
            add(loginField);

            nameField = new JLabel();
            nameField.setBounds(160, 60, 140, 25);
            add(nameField);

            moneyField = new JLabel();
            moneyField.setBounds(160, 90, 140, 25);
            add(moneyField);


            changeName = new JButton("Change name");
            changeName.setBounds(30, 120, 140, 25);
            add(changeName);


            changePassword = new JButton("Change password");
            changePassword.setBounds(30, 180, 140, 25);
            add(changePassword);

            addMoney = new JButton("Add Money");
            addMoney.setBounds(30, 150, 140, 25);
            add(addMoney);

            changeNameField = new JTextField();
            changeNameField.setBounds(180, 120, 140, 25);
            add(changeNameField);

            addMoneyField = new JTextField();
            addMoneyField.setBounds(180, 150, 140, 25);
            add(addMoneyField);

            deleteAccount = new JButton("Delete Account");
            deleteAccount.setBounds(180, 180, 140, 25);
            add(deleteAccount);


            back = new JButton("Back");
            back.setBounds(95, 270, 140, 25);
            add(back);

            String query = "select * from users where login=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userLogin);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();


            String matchName = resultSet.getString("matches");
            String matchTicket = resultSet.getString("ticketsCount");

            ArrayList<String> a = new ArrayList<>();

            if(matchName != null && !matchName.equals("")){
                String[] matchNameArray = matchName.split(" ");
                String[] matchTicketArray = matchTicket.split(" ");
                for (int i = 0; i < matchNameArray.length; i++) {
                    a.add(matchNameArray[i] + " " + matchTicketArray[i]);
                }
            }
            JComboBox myTickets = new JComboBox(a.toArray());
            myTickets.setBounds(180, 210, 140, 30);
            add(myTickets);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                loginField.setText(resultSet.getString("login"));
                nameField.setText(resultSet.getString("name"));
                moneyField.setText(resultSet.getString("moneyCount"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }



        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ResetPassword resetPassword = new ResetPassword();
                resetPassword.setVisible(true);

            }
        });
        deleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql1 = "DELETE FROM users WHERE login=? ";
                try {

                    int result=JOptionPane.showConfirmDialog(null,
                            "Are you sure?","Delete Account",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if(result==JOptionPane.YES_OPTION) {
                        preparedStatement = connection.prepareStatement(sql1);
                        preparedStatement.setString(1, userLogin);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Your account delete");
                        setVisible(false);
                        Main.LoginApp m=new Main.LoginApp();
                        m.setVisible(true);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changeNameField.getText()!=null&& changeNameField.getText()!="") {
                    nameField.setText(changeNameField.getText());

                    String sql1 = "UPDATE users SET name=? WHERE login=? ";
                    try {
                        preparedStatement = connection.prepareStatement(sql1);
                        preparedStatement.setString(1, changeNameField.getText());
                        preparedStatement.setString(2, userLogin);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Your name successfully changed!");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Try again!");
                }
            }
        });
        addMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addMoneyField.getText()!=null&& addMoneyField.getText()!="") {
                    String sql1 = "UPDATE users SET moneyCount=? WHERE login=? ";
                    try {
                        String query = "select moneyCount from users where Login ='" + userLogin + "'" ;
                        Statement statement = connection.createStatement();
                        resultSet = statement.executeQuery(query);
                        resultSet.next();
                        String moneySum=Integer.toString(Integer.parseInt(resultSet.getString("moneyCount"))+Integer.parseInt(addMoneyField.getText()));

                        preparedStatement = connection.prepareStatement(sql1);
                        preparedStatement.setString(1, moneySum);
                        preparedStatement.setString(2, userLogin);
                        preparedStatement.executeUpdate();


                        moneyField.setText(moneySum);
                        JOptionPane.showMessageDialog(null, "Your money successfully add!");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Try again!");
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Main.LoginApp.MainApp log = new Main.LoginApp.MainApp();
                log.setVisible(true);
            }
        });
    }
}
