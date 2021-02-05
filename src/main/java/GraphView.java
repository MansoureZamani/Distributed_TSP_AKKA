import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class GraphView extends JPanel {
    private final static int pointWidth = 4;
    private final static Color lineColor = new Color(44, 102, 230, 180);
    private final static Color gridColor = new Color(219, 180, 45, 161);
    private final static Color pointColor = new Color(231, 19, 19, 200);
    private static final Stroke graphStroke = new BasicStroke(2f);
    private ArrayList<City> cities = new ArrayList<City>();
    private Pair<Integer,Integer> X_YGrids;
    private Graphics2D graphics2D;

    public GraphView() {
        setPreferredSize(new Dimension( 500, 500));
    }

    public void setCities(List<City> newValues) {
        cities.addAll(newValues);
    }
    public void setX_YGrids(Pair<Integer, Integer> X_YGrids) {
        this.X_YGrids = X_YGrids;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (!(graphics instanceof Graphics2D)) {
            graphics.drawString("Graphics is not Graphics2D, unable to render", 0, 0);
            return;
        }
        final Graphics2D g = (Graphics2D) graphics;
        graphics2D = g;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int length = cities.size();
        final int width = 500;

        // create hatch marks and grid lines for y axis.
        for (int i = 1; i < X_YGrids.getKey() + 1; i++) {
            final int x1 = 0;
            final int x2 = 500;
            final int y = i*(500/ X_YGrids.getKey());
            g.setColor(gridColor);
            g.drawLine(x1, y, x2, y);
        }

        // and for x axis
        for (int i = 1; i < X_YGrids.getValue() + 1; i++) {
            final int y1 = 0;
            final int y2 = 500;
            final int x = i*(500/ X_YGrids.getValue());
            g.setColor(gridColor);
            g.drawLine(x, y1, x, y2);
        }

        final Stroke oldStroke = g.getStroke();
//        g.setColor(lineColor);
        g.setStroke(graphStroke);

        boolean drawDots = width > (length * pointWidth);
        if (drawDots) {
            g.setStroke(oldStroke);
            g.setColor(pointColor);
            for (City value : cities) {
                final int x = value.getX() - pointWidth / 2;
                final int y = value.getY() - pointWidth / 2;
                g.fillOval(x, y, pointWidth, pointWidth);
            }
        }
    }

    public void drawLines(Graphics2D g, List<City> cities) {
        g.setColor(lineColor);
        g.setStroke(new BasicStroke(2f));
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < cities.size() - 1; i++) {
                    final int x1 = cities.get(i).getX();
                    final int y1 = cities.get(i).getY();
                    final int x2 = cities.get(i + 1).getX();
                    final int y2 = cities.get(i + 1).getY();
                    g.drawLine(x1, y1, x2, y2);
                    System.out.println("draw line "+ i);
                }
                return null;
            }
        };

    }
    public Graphics2D getGraphics2D() {
        return graphics2D;
    }
}


