package footballApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Ticket extends JFrame{
    JComboBox teams;
    JButton next;
    JButton back;

    private final JLabel chooseMatch;

    public static String matchNameCommand;
    public Ticket(){
        setVisible(true);
        setSize(400, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        List<String> team = new ArrayList<>();
        String first = "KairatVSOral";
        String second = "AstanaVSTobyl";
        String third = "OrdabasyVSAktobe";
        String fourth = "TarazVSAtyrau";
        Collections.addAll(team, first, second, third, fourth);

        matchNameCommand=first;

        chooseMatch = new JLabel("Choose Match");
        chooseMatch.setBounds(30, 60, 100, 30);
        add(chooseMatch);

        teams = new JComboBox(team.toArray());
        teams.setBounds(30, 100, 200, 30);
        add(teams);

        next = new JButton("Next");
        next.setBounds(40, 300, 150, 30);
        add(next);

        back = new JButton("Back");
        back.setBounds(220, 300, 150, 30);
        add(back);

        teams.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED)
            {
                JComboBox box = (JComboBox)e.getSource();
                String item =(String)box.getSelectedItem();
                matchNameCommand=item;

            } else if (e.getStateChange() == ItemEvent.SELECTED){
                JComboBox box = (JComboBox)e.getSource();
                String item =(String)box.getSelectedItem();
                matchNameCommand=item;
            }
        });



        next.addActionListener(e -> {
            Match match = new Match();
            match.setVisible(true);
            try {
                Socket socket = new Socket("127.0.0.1", 2000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream.writeObject(matchNameCommand);
                socket.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }

            setVisible(false);

        });
        

        back.addActionListener(e -> {
            setVisible(false);
            Main.LoginApp.MainApp mainApp = new Main.LoginApp.MainApp();
            mainApp.setVisible(true);
        });
    }

}
