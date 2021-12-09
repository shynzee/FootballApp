package footballApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;

public class AdminApp extends JFrame {

    public void createMenu() {
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("Navigation");

        for (String fileItem : new String[]{"Main", "Exit"}) {
            JMenuItem item = new JMenuItem(fileItem);
            item.setActionCommand(fileItem.toLowerCase());
            item.addActionListener(new AdminApp.NewMenuListener());
            fileMenu.add(item);
        }
        fileMenu.insertSeparator(1);
        menu.add(fileMenu);
        setJMenuBar(menu);
    }

    class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("main".equals(command)) {
                setVisible(false);
                AdminApp aApp = new AdminApp();
                aApp.setVisible(true);
            }
            if ("exit".equals(command)) {
                setVisible(false);
                Main.LoginApp m = new Main.LoginApp();
                m.setVisible(true);

            }

        }
    }


    public AdminApp() {
        createMenu();

        setSize(400, 500);
        setTitle("Admin Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);


        JButton editTeamsButton = new JButton("Edit Teams");
        editTeamsButton.setBounds(80, 90, 200, 30);
        add(editTeamsButton);

        JButton editUserButton = new JButton("Edit Users");
        editUserButton.setBounds(80, 130, 200, 30);
        add(editUserButton);

        JButton back = new JButton("Back");
        back.setBounds(80, 170, 200, 30);
        add(back);

        back.addActionListener(e -> {
            setVisible(false);
            Main.LoginApp log = new Main.LoginApp();
            log.setVisible(true);
        });


        editTeamsButton.addActionListener(e -> {
            setVisible(false);
            AdminEditTeam aTeam = new AdminEditTeam();
            aTeam.setVisible(true);
        });

        editUserButton.addActionListener(e -> {
            setVisible(false);
            AdminEditUser aUser = new AdminEditUser();
            aUser.setVisible(true);
        });

    }
}

     class AdminEditMatch extends JFrame {
        public void createMenu() {
            JMenuBar menu = new JMenuBar();
            JMenu fileMenu = new JMenu("Navigation");

            for (String fileItem : new String[]{"Main", "Exit"}) {
                JMenuItem item = new JMenuItem(fileItem);
                item.setActionCommand(fileItem.toLowerCase());
                item.addActionListener(new AdminEditMatch.NewMenuListener());
                fileMenu.add(item);
            }
            fileMenu.insertSeparator(1);
            menu.add(fileMenu);
            setJMenuBar(menu);
        }

        class NewMenuListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if ("main".equals(command)) {
                    setVisible(false);
                    AdminApp aApp = new AdminApp();
                    aApp.setVisible(true);
                }
                if ("exit".equals(command)) {
                    setVisible(false);
                    Main.LoginApp m=new Main.LoginApp();
                    m.setVisible(true);

                }

            }
        }


    }

     class AdminEditTeam extends JFrame {
        Connection connection;

        ResultSet resultSet;

        PreparedStatement preparedStatement;

         private final JComboBox chooseTeamBox;

        private final JTextArea showTeamInfo;

         private JButton back;

        ArrayList<String> teams=new ArrayList<>();
        String choose;

        public void createMenu() {
            JMenuBar menu = new JMenuBar();
            JMenu fileMenu = new JMenu("Navigation");

            for (String fileItem : new String[]{"Main", "Exit"}) {
                JMenuItem item = new JMenuItem(fileItem);
                item.setActionCommand(fileItem.toLowerCase());
                item.addActionListener(new  AdminEditTeam.NewMenuListener());
                fileMenu.add(item);
            }
            fileMenu.insertSeparator(1);

            menu.add(fileMenu);
            setJMenuBar(menu);
        }

        class NewMenuListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if ("main".equals(command)) {
                    setVisible(false);
                    AdminApp aApp = new AdminApp();
                    aApp.setVisible(true);
                }
                if ("exit".equals(command)) {
                    setVisible(false);
                    Main.LoginApp m=new Main.LoginApp();
                    m.setVisible(true);
                }
            }
        }

        public AdminEditTeam() {
            connection = Connect.ConnectDb();
            createMenu();

            setSize(400, 500);
            setLayout(null);
            setTitle("Edit Team");
            setResizable(false);

            JButton back = new JButton("Back");
            back.setBounds(75, 180, 100, 30);
            add(back);


            JLabel chooseTeamLabel = new JLabel("Choose Team");
            chooseTeamLabel.setBounds(50, 30, 100, 30);
            add(chooseTeamLabel);

            showTeamInfo = new JTextArea();
            showTeamInfo.setBounds(75, 250, 200, 250);
            add(showTeamInfo);

            JButton addTeamButton = new JButton("Add team");
            addTeamButton.setBounds(75,100,100,30);
            add(addTeamButton);

            JButton deleteTeamButton = new JButton("Delete team");
            deleteTeamButton.setBounds(75,140,100,30);
            add(deleteTeamButton);

            String query = "select * from teams";
            try {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {

                    String team = resultSet.getString("id") + " " + resultSet.getString("teamName");
                    teams.add(team);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            chooseTeamBox = new JComboBox(teams.toArray());
            chooseTeamBox.setBounds(170, 30, 120, 30);
            add(chooseTeamBox);

            chooseTeamBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    showTeamInfo.setText("");
                    JComboBox box = (JComboBox)e.getSource();
                    String item =(String)box.getSelectedItem();
                    showTeamInfo.setText(item);
                    if (item!=null) {
                        String[] i = item.split(" ");
                        choose = i[0];
                    }

                } else if (e.getStateChange() == ItemEvent.SELECTED) {
                    showTeamInfo.setText("");
                    JComboBox box = (JComboBox)e.getSource();
                    String item =(String)box.getSelectedItem();

                    if (item!=null) {
                        String[] i = item.split(" ");
                        choose = i[0];
                    }
                    String query12 = "select * from teams";

                    String temp="";
                    try {
                        Statement statement = connection.createStatement();
                        resultSet = statement.executeQuery(query12);
                        while (resultSet.next()) {

                            if(resultSet.getString("id").equals(choose)){
                                temp="ID: "+resultSet.getString("id")+"\nTeam Name: "+resultSet.getString("teamName")+"\nWins: "+resultSet.getString("wins")+"\nDraws: "+resultSet.getString("draws")+"\nLoses: "+resultSet.getString("loses")+"\nScore: "+resultSet.getString("score");
                            }
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    showTeamInfo.setText(temp);

                }
            });

            deleteTeamButton.addActionListener(e -> {
                String query1 = "DELETE from teams where id=?";
                try {
                    preparedStatement = connection.prepareStatement(query1);
                    preparedStatement.setString(1, choose);
                    preparedStatement.executeUpdate();

                    String query2 = "select * from teams";
                    try {

                        Statement statement = connection.createStatement();
                        resultSet = statement.executeQuery(query2);
                        teams.clear();
                        while (resultSet.next()) {
                            String team = resultSet.getString("id") + " " + resultSet.getString("teamName");
                            teams.add(team);
                        }

                        chooseTeamBox.removeAllItems();
                        for (String s : teams) {
                            chooseTeamBox.insertItemAt(s, chooseTeamBox.getItemCount());
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            });

            addTeamButton.addActionListener(e -> {
                setVisible(false);
                AddTeam a=new AddTeam();
                a.setVisible(true);
            });

            back.addActionListener(e -> {
                setVisible(false);
                AdminApp a = new AdminApp();
                a.setVisible(true);
            });


        }
    }

     class AddTeam extends JFrame{
        Connection connection;

        ResultSet resultSet;

        PreparedStatement preparedStatement;


         private final JTextField nameField;
        private final JTextField gamesField;
        private final JTextField winsField;
        private final JTextField drawsField;
        private final JTextField losesField;
        private final JTextField scoreField;


         public void createMenu() {
            JMenuBar menu = new JMenuBar();
            JMenu fileMenu = new JMenu("Navigation");

            for (String fileItem : new String[]{"Main", "Exit"}) {
                JMenuItem item = new JMenuItem(fileItem);
                item.setActionCommand(fileItem.toLowerCase());
                item.addActionListener(new AddTeam.NewMenuListener());
                fileMenu.add(item);
            }
            fileMenu.insertSeparator(1);

            menu.add(fileMenu);
            setJMenuBar(menu);
        }



         class NewMenuListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if ("main".equals(command)) {
                    setVisible(false);
                    AdminApp aApp = new AdminApp();
                    aApp.setVisible(true);
                }
                if ("exit".equals(command)) {
                    setVisible(false);
                    Main.LoginApp m=new Main.LoginApp();
                    m.setVisible(true);

                }

            }
        }
        public AddTeam() {

            setTitle("Add Team");
            setSize(400, 500);
            setResizable(false);
            setVisible(true);
            setLayout(null);
            connection = Connect.ConnectDb();
            createMenu();
            JLabel teamName = new JLabel("Team Name: ");
            teamName.setBounds(30, 50, 100, 30);
            add(teamName);

            nameField = new JTextField();
            nameField.setBounds(130, 50, 100, 30);
            add(nameField);

            JLabel gamesPlayed = new JLabel("Games played: ");
            gamesPlayed.setBounds(30, 90, 100, 30);
            add(gamesPlayed);

            gamesField = new JTextField();
            gamesField.setBounds(130, 90, 100, 30);
            add(gamesField);

            JLabel teamWins = new JLabel("Wins: ");
            teamWins.setBounds(30, 130, 100, 30);
            add(teamWins);

            winsField = new JTextField();
            winsField.setBounds(130, 130, 100, 30);
            add(winsField);

            JLabel teamDraws = new JLabel("Draws:");
            teamDraws.setBounds(30,170, 100, 30);
            add(teamDraws);


            drawsField = new JTextField();
            drawsField.setBounds(130,170, 100, 30);
            add(drawsField);

            JLabel teamLoses = new JLabel("Loses:");
            teamLoses.setBounds(30,210,100,30);
            add(teamLoses);

            losesField=new JTextField();
            losesField.setBounds(130,210,100,30);
            add(losesField);

            JLabel teamScore = new JLabel("Score:");
            teamScore.setBounds(30,250,100,30);
            add(teamScore);

            scoreField=new JTextField();
            scoreField.setBounds(130,250,100,30);
            add(scoreField);

            JButton addButton = new JButton("Add");
            addButton.setBounds(130,300,80,30);
            add(addButton);

            JButton back = new JButton("Back");
            back.setBounds(130, 340, 80, 30);
            add(back);

            addButton.addActionListener(e -> {
                if ((nameField.getText() != "" && nameField.getText() != null) && (winsField.getText() != "" && winsField.getText() != null) && (drawsField.getText() != "" && drawsField.getText() != null) && (gamesField.getText() != "" && gamesField.getText() != null) && (scoreField.getText() != "" && scoreField.getText() != null) && (scoreField.getText() != "" && scoreField.getText() != null)) {
                    try {
                        String query = "insert into teams(teamName, gamesPlayed, wins, draws, loses, score) values (?,?,?,?,?,?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, nameField.getText());
                        preparedStatement.setString(2, gamesField.getText());
                        preparedStatement.setString(3, winsField.getText());
                        preparedStatement.setString(4, drawsField.getText());
                        preparedStatement.setString(5, losesField.getText());
                        preparedStatement.setString(6, scoreField.getText());
                        preparedStatement.execute();
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    }
                      add();
                } else {
                    JOptionPane.showMessageDialog(null, "Try again!");
                }
            });

            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    AdminEditTeam ae = new AdminEditTeam();
                    ae.setVisible(true);
                }
            });


        }


        public void add() {
            JOptionPane.showMessageDialog(null, "New team added!");
        }
    }

    class AdminEditUser extends JFrame implements Add{

        ArrayList<String> users = new ArrayList<>();

        Connection connection;

        ResultSet resultSet;

        PreparedStatement preparedStatement;

        private final JTextField userLoginField;
        private final JTextField userPassword;

        private final JComboBox usersBox;

        private JButton back;

        String choose;

        public void createMenu() {
            JMenuBar menu = new JMenuBar();
            JMenu fileMenu = new JMenu("Navigation");

            for (String fileItem : new String[]{"Main", "Exit"}) {
                JMenuItem item = new JMenuItem(fileItem);
                item.setActionCommand(fileItem.toLowerCase());
                item.addActionListener(new AdminEditUser.NewMenuListener());
                fileMenu.add(item);
            }
            fileMenu.insertSeparator(1);

            menu.add(fileMenu);
            setJMenuBar(menu);
        }

        @Override
        public void add() {
            JOptionPane.showMessageDialog(null, "Team added");
        }

        class NewMenuListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if ("main".equals(command)) {
                    setVisible(false);
                    AdminApp aApp = new AdminApp();
                    aApp.setVisible(true);
                }
                if ("exit".equals(command)) {
                    setVisible(false);
                    Main.LoginApp m = new Main.LoginApp();
                    m.setVisible(true);

                }

            }
        }

            public AdminEditUser() {
                connection = Connect.ConnectDb();
                createMenu();

                setSize(400, 500);
                setLayout(null);
                setTitle("Edit User");
                setResizable(false);

                JLabel usersLabel = new JLabel("Choose user: ");
                usersLabel.setBounds(50, 30, 90, 30);
                add(usersLabel);

                JButton deleteUser = new JButton("Delete user");
                deleteUser.setBounds(50, 90, 120, 30);
                add(deleteUser);
                userLoginField = new JTextField();
                userLoginField.setBounds(50, 60, 90, 30);
                add(userLoginField);
                JButton renameLogin = new JButton("Change login");
                renameLogin.setBounds(150, 60, 120, 30);
                add(renameLogin);
                userPassword = new JTextField();
                userPassword.setBounds(50, 120, 90, 30);
                add(userPassword);
                JButton changePassword = new JButton("Change pas");
                changePassword.setBounds(150, 120, 120, 30);
                add(changePassword);


                String query = "select * from users";
                try {

                    Statement statement = connection.createStatement();
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        if (resultSet.getString("login").equals("admin")) {
                            continue;
                        }
                        String user = resultSet.getString("id") + " " + resultSet.getString("password") + " " + resultSet.getString("login");
                        users.add(user);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                usersBox = new JComboBox(users.toArray());
                usersBox.setBounds(150, 30, 120, 30);
                add(usersBox);
                usersBox.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.DESELECTED) //edit: bracket was missing
                    {
                        JComboBox box = (JComboBox) e.getSource();

                        String item = (String) box.getSelectedItem();

                        if(item!=null) {
                            String[] i = item.split(" ");
                            choose = i[0];
                        }

                    } else if (e.getStateChange() == ItemEvent.SELECTED) {
                        JComboBox box = (JComboBox) e.getSource();
                        String item = (String) box.getSelectedItem();
                        if(item!=null) {
                            String[] i = item.split(" ");
                            choose = i[0];
                        }
                    }
                });
                deleteUser.addActionListener(e -> {
                    String query1 = "DELETE from users where id=?";
                    try {
                        preparedStatement = connection.prepareStatement(query1);
                        preparedStatement.setString(1, choose);
                        preparedStatement.executeUpdate();

                        String query23 = "select * from users";
                        try {

                            Statement statement = connection.createStatement();
                            resultSet = statement.executeQuery(query23);
                            users.clear();
                            while (resultSet.next()) {
                                if (resultSet.getString("login").equals("admin")) {
                                    continue;
                                }
                                String user = resultSet.getString("id") + " " + resultSet.getString("password") + " " + resultSet.getString("login");
                                users.add(user);
                            }
                            usersBox.removeAllItems();
                            for (String s : users) {
                                usersBox.insertItemAt(s, usersBox.getItemCount());
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                changePassword.addActionListener(e -> {
                    String query1 = "UPDATE users SET password=? WHERE id=?";
                    try {
                        preparedStatement = connection.prepareStatement(query1);
                        preparedStatement.setString(1, userPassword.getText());
                        preparedStatement.setString(2, choose);
                        preparedStatement.executeUpdate();
                        String query22 = "select * from users";
                        try {

                            Statement statement = connection.createStatement();
                            resultSet = statement.executeQuery(query22);
                            users.clear();
                            while (resultSet.next()) {
                                if (resultSet.getString("login").equals("admin")) {
                                    continue;
                                }
                                String user = resultSet.getString("id") + " " + resultSet.getString("password") + " " + resultSet.getString("login");
                                users.add(user);
                            }

                            usersBox.removeAllItems();
                            for (String s : users) {
                                usersBox.insertItemAt(s, usersBox.getItemCount());
                            }

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });


                renameLogin.addActionListener(e -> {
                    String query1 = "UPDATE users SET login=? WHERE id=?";
                    try {
                        preparedStatement = connection.prepareStatement(query1);
                        preparedStatement.setString(1, userLoginField.getText());
                        preparedStatement.setString(2, choose);
                        preparedStatement.executeUpdate();

                        String query2 = "select * from users";
                        try {

                            Statement statement = connection.createStatement();
                            resultSet = statement.executeQuery(query2);
                            users.clear();
                            while (resultSet.next()) {
                                if (resultSet.getString("login").equals("admin")) {
                                    continue;
                                }
                                String user = resultSet.getString("id") + " " + resultSet.getString("password") + " " + resultSet.getString("login");
                                users.add(user);
                            }
                            usersBox.removeAllItems();
                            for (String s : users) {
                                usersBox.insertItemAt(s, usersBox.getItemCount());
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }


