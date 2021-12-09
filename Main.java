package footballApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;


public class Main {

    public static int sum=0;

    public static class LoginApp extends JFrame {

        Connection connection;

        ResultSet resultSet;

        PreparedStatement preparedStatement;

        private static JTextField loginField;
        private final JPasswordField passwordField;

        public static String getLogin(){
            return loginField.getText();
        }


        public LoginApp() {

            getMoney();
            connection = Connect.ConnectDb();

            setSize(400, 500);
            setTitle("Login");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setLayout(null);

            JLabel loginLabel = new JLabel("Enter login");
            loginLabel.setBounds(50, 30, 90, 30);
            add(loginLabel);

            JLabel passwordLabel = new JLabel("Enter password");
            passwordLabel.setBounds(50, 70, 90, 30);
            add(passwordLabel);

            loginField = new JTextField();
            loginField.setBounds(150, 30, 130, 30);
            add(loginField);

            passwordField = new JPasswordField();
            passwordField.setBounds(150, 70, 130, 30);
            add(passwordField);

            JButton loginButton = new JButton("Login");
            loginButton.setBounds(105, 120, 140, 30);
            add(loginButton);

            JButton forgotPassword = new JButton("Forgot password?");
            forgotPassword.setBounds(105, 160, 140, 30);
            add(forgotPassword);

            JButton signUp = new JButton("Sign Up");
            signUp.setBounds(105, 200, 140, 30);
            add(signUp);

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String query = "select * from users where login=? and password=?";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, loginField.getText());
                        preparedStatement.setString(2, String.valueOf(passwordField.getPassword()));

                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            if(loginField.getText().equals("admin")){
                                resultSet.close();
                                preparedStatement.close();
                                setVisible(false);
                                AdminApp amain = new AdminApp();
                                amain.setVisible(true);
                            }
                            else {
                                resultSet.close();
                                preparedStatement.close();
                                setVisible(false);
                                MainApp main = new MainApp();
                                main.setVisible(true);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect login and password");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            forgotPassword.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    ForgotPass f = new ForgotPass();
                    f.setVisible(true);
                }
            });
            signUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    SignUp s = new SignUp();
                    s.setVisible(true);
                }
            });
        }
        public static class MainApp extends JFrame {

            private void createMenu() {
                JMenuBar menu = new JMenuBar();
                JMenu fileMenu = new JMenu("Navigation");

                for (String fileItem : new String[]{"Main", "Leave", "My profile"}) {
                    JMenuItem item = new JMenuItem(fileItem);
                    item.setActionCommand(fileItem.toLowerCase());
                    item.addActionListener(new NewMenuListener());
                    fileMenu.add(item);
                }
                fileMenu.insertSeparator(1);

                menu.add(fileMenu);
                setJMenuBar(menu);
            }
            class NewMenuListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    String command = e.getActionCommand();
                    if ("leave".equals(command)) {
                        setVisible(false);
                        LoginApp loginApp = new LoginApp();
                        loginApp.setVisible(true);
                    }
                    if ("main".equals(command)) {
                        setVisible(false);
                        MainApp MainApp = new MainApp();
                        MainApp.setVisible(true);
                    }
                    if ("my profile".equals(command)) {
                        setVisible(false);
                        MyProfile MyProfile = new MyProfile();
                        MyProfile.setVisible(true);
                    }
                }
            }
            public MainApp() {
                setSize(400, 500);
                setTitle("Football App");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(null);
                setResizable(false);

                createMenu();

                JLabel league = new JLabel("Kazakhstan Premier League");
                league.setBounds(100, 10, 230, 40);
                add(league);

                JButton chooseMatch = new JButton("Choose Match");
                chooseMatch.setBounds(80, 50, 200, 30);
                add(chooseMatch);

                JButton leadersTable = new JButton("Leaders table");
                leadersTable.setBounds(80, 90, 200, 30);
                add(leadersTable);

                chooseMatch.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        Ticket ticket = new Ticket();
                        ticket.setVisible(true);
                    }
                });

                leadersTable.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        Leaders lead = new Leaders();
                        lead.setVisible(true);
                    }
                });

            }
        }

        public static void getMoney(){
            for (int j=0;j<2;j++) {
                new Thread(()->{
                    int a=0;
                    for (int i = 0; i < 100; i++){
                        a+= 1 + (int)(Math.random()*((350 - 1) + 1));
                    }
                    synchronized (Main.class){
                        sum=sum+a;
                    }
                }).start();
            }
        }

        public static class Leaders extends JFrame {

            Connection connection;

            public Leaders() {
                setSize(400, 500);
                setResizable(false);
                setLayout(null);
                setTitle("Leaders");

                connection=Connect.ConnectDb();

                JLabel leaderLabel = new JLabel("Leaders Table for KPL");
                leaderLabel.setBounds(150, 10, 230, 40);
                add(leaderLabel);

                JTextArea leaderArea = new JTextArea();
                leaderArea.setBounds(100, 80, 200, 250);
                add(leaderArea);

                JButton back = new JButton("Back");
                back.setBounds(150, 350, 100, 30);
                add(back);

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        MainApp main = new MainApp();
                        main.setVisible(true);
                    }
                });

                String query = "select * from teams order by wins DESC";
                ArrayList<String> teams=new ArrayList<>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {

                        String team = resultSet.getString("id") + ": " +resultSet.getString("wins") +"-" + resultSet.getString("draws") + "-" + resultSet.getString("loses") + " " + resultSet.getString("teamName") + "\n";
                        teams.add(team);
                    }
                    leaderArea.setText(teams.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }








            }
        }

        public static void main(String[] args) {
            Connection connection;
            connection=Connect.ConnectDb();
            LoginApp loginApp = new LoginApp();
            loginApp.setVisible(true);
            try {
                ServerSocket serverSocket = new ServerSocket(2000);
                while (true) {
                    Socket socket = serverSocket.accept();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream inputStream = new ObjectInputStream(((Socket) socket).getInputStream());
                    String message = (String) inputStream.readObject();
                    if (message.matches("[0-9]+")) BuyTicket.setCost(Integer.parseInt(message));
                    else {
                        Match.setMatchName(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
