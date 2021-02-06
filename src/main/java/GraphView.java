import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class GraphView extends JPanel {
    private final static int pointWidth = 4;
    private final int frameSize = 1000;
    private final static Color gridColor = new Color(219, 180, 45, 161);
    private final static Color pointColor = new Color(231, 19, 19, 200);
    private static final Stroke graphStroke = new BasicStroke(2f);
    private ArrayList<City> cities = new ArrayList<City>();
    private Pair<Integer,Integer> X_YGrids;

    public GraphView() {
        setPreferredSize(new Dimension( frameSize, frameSize));
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
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int length = cities.size();
        final int width = frameSize;

        // create hatch marks and grid lines for y axis.
        for (int i = 1; i < X_YGrids.getValue() + 1; i++) {
            final int x1 = 0;
            final int x2 = frameSize;
            final int y = i*(frameSize/ X_YGrids.getValue());
            g.setColor(gridColor);
            g.drawLine(x1, y, x2, y);
        }

        // and for x axis
        for (int i = 1; i < X_YGrids.getKey() + 1; i++) {
            final int y1 = 0;
            final int y2 = frameSize;
            final int x = i*(frameSize/ X_YGrids.getKey());
            g.setColor(gridColor);
            g.drawLine(x, y1, x, y2);
        }

        final Stroke oldStroke = g.getStroke();
//        g.setColor(lineColor);
        g.setStroke(graphStroke);

        g.setStroke(oldStroke);
        g.setColor(pointColor);
        for (City value : cities) {
            final int x = value.getX() - pointWidth / 2;
            final int y = value.getY() - pointWidth / 2;
            g.fillOval(x, y, pointWidth, pointWidth);
        }

    }
}


