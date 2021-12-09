package footballApp;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.*;

public class Match extends JFrame {

    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;

    public static String matchName="Game";
    public static void setMatchName(String s){
        matchName=s;
    }
    public static String allMatchNames;
    public static String allTickets;

    public Match() {
        connection = Connect.ConnectDb();

        setSize(600,600);
        setTitle("Match");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel chooseSector = new JLabel("Choose sector");
        chooseSector.setBounds(200, 30, 100, 30);
        add(chooseSector);

        JButton nSector = new JButton("N SECTOR");
        nSector.setBounds(120, 60, 240, 30);
        add(nSector);

        JButton wSector = new JButton("W SECTOR");
        wSector.setBounds(70, 100, 100, 250);
        add(wSector);

        JButton eSector = new JButton("E SECTOR");
        eSector.setBounds(320, 100, 100, 250);
        add(eSector);

        JButton sSector = new JButton("S SECTOR");
        sSector.setBounds(120, 370, 240, 30);
        add(sSector);

        JButton NE = new JButton("NE");
        NE.setBounds(360, 60, 50, 30);
        add(NE);

        JButton SE = new JButton("SE");
        SE.setBounds(360, 370, 50, 30);
        add(SE);

        JButton NW = new JButton("NW");
        NW.setBounds(65, 60, 55, 30);
        add(NW);

        JButton SW = new JButton("SW");
        SW.setBounds(65, 370, 55, 30);
        add(SW);

        JButton back = new JButton("Back");
        back.setBounds(190, 420, 100, 30);
        add(back);



        nSector.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2300");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });

        sSector.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2300");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });

        eSector.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2500");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });

        wSector.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2500");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });

        NE.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2000");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });

        SE.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2000");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });

        NW.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2000");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });

        SW.addActionListener(e -> {
            BuyTicket b=new BuyTicket();
            b.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject("2000");
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

        });


        back.addActionListener(e -> {
            setVisible(false);
            Main.LoginApp.MainApp ma = new Main.LoginApp.MainApp();
            ma.setVisible(true);
        });








    }
}

class BuyTicket extends JFrame{
    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;


    private static final int countTickets=100;
    private static int ticketCost=0;

    public static void setCost(int i){
        ticketCost=i;
    }

    public BuyTicket(){
        connection = Connect.ConnectDb();

        setSize(500,400);
        setTitle("Buy ticket");
        setLayout(null);

        JLabel ticketNumberLabel = new JLabel("Ticket Number");
        ticketNumberLabel.setBounds(30, 30, 100, 30);

        JLabel ticketNumber = new JLabel("№:" + Math.random());
        ticketNumber.setBounds(30,70,200,30);
        add(ticketNumber);

        JButton buyTicketButton = new JButton("Buy ticket");
        buyTicketButton.setBounds(30,100,100,30);
        add(buyTicketButton);

        buyTicketButton.addActionListener(e -> {

            int sum=0;
            String sql="SELECT matches, ticketsCount FROM users";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                ArrayList<String> a=new ArrayList<>();


                while (resultSet.next()){
                    if(resultSet.getString(1)==null || resultSet.getString(2)==null) continue;
                    String b[] =(resultSet.getString(1)+" "+resultSet.getString(2)).split(" ");
                    String c[]=resultSet.getString(2).split(" ");
                    for (int i=0;i<b.length;i++){
                        if(b[i].equals("AstanaVSTobyl")){
                            sum+=Integer.parseInt(c[i]);
                        }
                    }
                }



            }catch (Exception ece){
                ece.printStackTrace();
            }

            if(sum>=100){
                JOptionPane.showMessageDialog(null, "No available tickets for match: " + Match.matchName);

            }


else {
                String query = "select * from users where login ='" + Main.LoginApp.getLogin() + "'";
                Statement statement;
                try {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(query);
                    resultSet.next();

                    int money = Integer.parseInt(resultSet.getString("moneyCount"));
                    if (money < ticketCost) {
                        JOptionPane.showMessageDialog(null, "Not enough money");
                    } else {
                        String sql1 = "UPDATE users SET matches=?,ticketsCount=?,moneyCount=? WHERE login=? ";
                        try {

                            preparedStatement = connection.prepareStatement(sql1);

                            if (resultSet.getString("matches") == null || resultSet.getString("matches").equals("")) {
                                Match.allMatchNames = Match.matchName;
                                preparedStatement.setString(1, Match.matchName);
                            } else {
                                Match.allMatchNames = resultSet.getString("matches");
                                String[] matchNamesIndexes = Match.allMatchNames.split(" ");

                                int matchIndex = 555;
                                for (int i = 0; i < matchNamesIndexes.length; i++) {
                                    if (Objects.equals(Match.matchName, matchNamesIndexes[i])) {
                                        matchIndex = i;
                                        break;
                                    }
                                }
                                if (matchIndex == 555) {
                                    preparedStatement.setString(1, Match.allMatchNames + " " + Match.matchName);
                                } else {
                                    preparedStatement.setString(1, Match.allMatchNames);
                                }
                            }

                            if (resultSet.getString("ticketsCount") == null || resultSet.getString("ticketsCount").equals("")) {
                                preparedStatement.setString(2, "1");
                            } else {
                                Match.allTickets = resultSet.getString("ticketsCount");
                                String[] matchIndexes = Match.allMatchNames.split(" ");
                                int index = 555;
                                for (int i = 0; i < matchIndexes.length; i++) {
                                    if (Objects.equals(Match.matchName, matchIndexes[i])) {
                                        index = i;
                                        break;
                                    }
                                }

                                if (index == 555) {
                                    preparedStatement.setString(2, Match.allTickets + " " + "1");
                                } else {
                                    String[] matchTicketsIndexes = Match.allTickets.split(" ");
                                    for (int i = 0; i < matchTicketsIndexes.length; i++) {
                                        if (i == index) {
                                            matchTicketsIndexes[i] = Integer.toString(Integer.parseInt(matchTicketsIndexes[i]) + 1);
                                        }
                                    }
                                    Match.allTickets = String.join(" ", matchTicketsIndexes);
                                    preparedStatement.setString(2, Match.allTickets);
                                }


                            }

                            preparedStatement.setString(3, Integer.toString(Integer.parseInt(resultSet.getString("moneyCount")) - ticketCost));
                            preparedStatement.setString(4, Main.LoginApp.getLogin());
                            preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Your successfully bought a ticket!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }


                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        });

    }


}
