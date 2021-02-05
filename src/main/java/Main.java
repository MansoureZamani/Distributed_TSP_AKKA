import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createInitialFrame();
            }
        });

    }

    private static void createInitialFrame(){
        JFrame frame = new JFrame("TSP initialization");
        JTextField cityNumbers = new JTextField(15);
        JTextField sectionNumbers = new JTextField(15);
        JPanel moreComplexPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        moreComplexPanel.add(new JLabel("Enter Number of Cities:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        moreComplexPanel.add(cityNumbers, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        moreComplexPanel.add(new JLabel("Enter Number of Sections:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        moreComplexPanel.add(sectionNumbers, gbc);

        int result = JOptionPane.showConfirmDialog(frame, moreComplexPanel,
                                                   "Calculate TSP", JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            calculateTSPResult(Integer.parseInt(cityNumbers.getText()),
                               Integer.parseInt(sectionNumbers.getText()));
        }
    }

    private static void calculateTSPResult(int cityNumbers, int sectionNumbers){
        Management manger = new Management(cityNumbers,sectionNumbers);
        List<City> cities = manger.initialRandomCities();
        Map<String,List<City>> zoning = manger.partitionCities(cities);
        GraphView graphView = new GraphView();
        DrawLines drawLines = new DrawLines();
        Pair<Integer, Integer> bestDivisor = manger.findBestDivisors();
        JFrame frame = new JFrame("TSP Solver");
        graphView.setX_YGrids(bestDivisor);
        graphView.setCities(cities);

        Graphics graphics = createAndShowGui(graphView,drawLines,frame);
        Map<String,ActorRef> actors = new HashMap<>();
        ActorSystem system = ActorSystem.create("actor-system");
        ActorRef masterRef = system.actorOf(
                MasterActor.props(graphics, drawLines, frame,sectionNumbers), "master");
        for (Map.Entry<String, List<City>> entry : zoning.entrySet()) {
            ActorRef readingActorRef = system.actorOf(
                    CalculationActor.props(masterRef, graphics, drawLines,
                                           frame), entry.getKey());
            actors.put(entry.getKey(), readingActorRef);
            readingActorRef.tell(new Pair(entry.getValue(),masterRef),ActorRef.noSender());
        }
    }

    private static Graphics createAndShowGui(GraphView graphView , DrawLines drawLines, JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(graphView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame.getGraphics();
    }
}
