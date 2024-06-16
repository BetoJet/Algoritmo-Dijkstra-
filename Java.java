package Java;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Java extends JPanel {

    private int rows;
    private int cols;
    private int nodeSize;
    private List<Point> visitedNodes = new ArrayList<>();
    private List<Point> pathNodes = new ArrayList<>();
    private List<Point> wallNodes = new ArrayList<>();
    private Point startNode = null;
    private Point endNode = null;
    private List<Point> weightedNodes = new ArrayList<>();
    private List<Integer> nodeWeights = new ArrayList<>();

    public Java(int rows, int cols, int nodeSize) {
        this.rows = rows;
        this.cols = cols;
        this.nodeSize = nodeSize;
        setPreferredSize(new Dimension(cols * nodeSize, rows * nodeSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * nodeSize;
                int y = row * nodeSize;
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, nodeSize, nodeSize);
                Point point = new Point(col, row);

                if (wallNodes.contains(point)) {
                    g.setColor(Color.magenta);
                    g.fillRect(x, y, nodeSize, nodeSize);
                } else if (visitedNodes.contains(point)) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, nodeSize, nodeSize);
                } else if (pathNodes.contains(point)) {
                    g.setColor(Color.PINK);
                    g.fillRect(x, y, nodeSize, nodeSize);
                } else if (startNode != null && startNode.equals(point)) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, nodeSize, nodeSize);
                } else if (endNode != null && endNode.equals(point)) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, nodeSize, nodeSize);
                }
            }
        }
    }

    public void markWall(int row, int col) {
        wallNodes.add(new Point(col, row));
        repaint();
    }

    public void markStart(int row, int col) {
        startNode = new Point(col, row);
        repaint();
    }

    public void markEnd(int row, int col) {
        endNode = new Point(col, row);
        repaint();
    }

    public void clearNode(int row, int col) {
        Point point = new Point(col, row);
        wallNodes.remove(point);
        visitedNodes.remove(point);
        pathNodes.remove(point);
        int idx = weightedNodes.indexOf(point);
        if (idx != -1) {
            weightedNodes.remove(idx);
            nodeWeights.remove(idx);
        }
        if (startNode != null && startNode.equals(point)) startNode = null;
        if (endNode != null && endNode.equals(point)) endNode = null;
        repaint();
    }

    public void markVisited(int row, int col) {
        visitedNodes.add(new Point(col, row));
        repaint();
    }

    public void markPath(int row, int col) {
        pathNodes.add(new Point(col, row));
        repaint();
    }

    public void setNodeWeight(int row, int col, int weight) {
        weightedNodes.add(new Point(col, row));
        nodeWeights.add(weight);
        repaint();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<Point> getVisitedNodes() {
        return visitedNodes;
    }

    public List<Point> getPathNodes() {
        return pathNodes;
    }

    public List<Point> getWallNodes() {
        return wallNodes;
    }

    public Point getStartNode() {
        return startNode;
    }

    public Point getEndNode() {
        return endNode;
    }

    public List<Point> getWeightedNodes() {
        return weightedNodes;
    }

    public List<Integer> getNodeWeights() {
        return nodeWeights;
    }

    public void clearAll() {
        wallNodes.clear();
        visitedNodes.clear();
        pathNodes.clear();
        nodeWeights.clear();
        startNode = null;
        endNode = null;
        repaint();
    }
    
    
    
     public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PanelGrafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelGrafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelGrafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelGrafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelGrafico().setVisible(true);
            }
        });
        
    }
}
