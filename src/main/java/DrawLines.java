import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class DrawLines extends JPanel {

    private final static Color lineColor = new Color(44, 102, 230, 180);
    private List<City> cities = new ArrayList<City>();
    public void setCities(List<City> newValues) {
        cities = newValues;
    }
    public DrawLines() {
        setPreferredSize(new Dimension( 500, 500));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(lineColor);
        g.setStroke(new BasicStroke(2f));
        for (int i = 0; i < cities.size() - 1; i++) {
            final int x1 = cities.get(i).getX();
            final int y1 = cities.get(i).getY();
            final int x2 = cities.get(i + 1).getX();
            final int y2 = cities.get(i + 1).getY();
            g.drawLine(x1, y1, x2, y2);
        }
    }
}