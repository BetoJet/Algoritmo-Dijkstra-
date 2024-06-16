/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Java;

import java.awt.Point;
import java.util.*;
import javax.swing.JOptionPane;

public class Dijkstra {
    private Java gridPanel;

    public Dijkstra(Java gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void findShortestPath() {
        Point startNode = gridPanel.getStartNode();
        Point endNode = gridPanel.getEndNode();
        List<Point> weightedNodes = gridPanel.getWeightedNodes();
        List<Integer> nodeWeights = gridPanel.getNodeWeights();

        if (startNode == null || endNode == null) {
            JOptionPane.showMessageDialog(null, "Marca el nodo de fin o inicio", "ERROR EN NODOS", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        List<PointDistance> distances = new ArrayList<>();
        List<PointPrevious> previousNodes = new ArrayList<>();
        List<Point> visited = new ArrayList<>();

        for (int row = 0; row < gridPanel.getRows(); row++) {
            for (int col = 0; col < gridPanel.getCols(); col++) {
                Point point = new Point(col, row);
                distances.add(new PointDistance(point, Integer.MAX_VALUE));
                previousNodes.add(new PointPrevious(point, null));
            }
        }

        setDistance(distances, startNode, 0);
        pq.add(new Node(startNode, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Point currentNode = current.point;

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            if (currentNode.equals(endNode)) break;

            for (Point neighbor : getNeighbors(currentNode)) {
                if (!visited.contains(neighbor) && !gridPanel.getWallNodes().contains(neighbor)) {
                    int newDist = getDistance(distances, currentNode) + getWeight(neighbor, weightedNodes, nodeWeights);
                    if (newDist < getDistance(distances, neighbor)) {
                        setDistance(distances, neighbor, newDist);
                        setPrevious(previousNodes, neighbor, currentNode);
                        pq.add(new Node(neighbor, newDist));
                    }
                }
            }
        }

        Point step = endNode;
        while (step != null && getPrevious(previousNodes, step) != null) {
            gridPanel.markPath(step.y, step.x);
            step = getPrevious(previousNodes, step);
        }
    }

    private int getWeight(Point point, List<Point> weightedNodes, List<Integer> nodeWeights) {
        int index = weightedNodes.indexOf(point);
        return index != -1 ? nodeWeights.get(index) : 1;
    }

    private List<Point> getNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();
        int[] directions = {-1, 0, 1, 0, -1};
        for (int i = 0; i < 4; i++) {
            int newRow = point.y + directions[i];
            int newCol = point.x + directions[i + 1];
            if (newRow >= 0 && newRow < gridPanel.getRows() && newCol >= 0 && newCol < gridPanel.getCols()) {
                neighbors.add(new Point(newCol, newRow));
            }
        }
        return neighbors;
    }

    private int getDistance(List<PointDistance> distances, Point point) {
        for (PointDistance pd : distances) {
            if (pd.point.equals(point)) {
                return pd.distance;
            }
        }
        return Integer.MAX_VALUE;
    }

    private void setDistance(List<PointDistance> distances, Point point, int distance) {
        for (PointDistance pd : distances) {
            if (pd.point.equals(point)) {
                pd.distance = distance;
                return;
            }
        }
    }

    private Point getPrevious(List<PointPrevious> previousNodes, Point point) {
        for (PointPrevious pp : previousNodes) {
            if (pp.point.equals(point)) {
                return pp.previous;
            }
        }
        return null;
    }

    private void setPrevious(List<PointPrevious> previousNodes, Point point, Point previous) {
        for (PointPrevious pp : previousNodes) {
            if (pp.point.equals(point)) {
                pp.previous = previous;
                return;
            }
        }
    }

    private static class Node {
        Point point;
        int cost;

        Node(Point point, int cost) {
            this.point = point;
            this.cost = cost;
        }
    }

    private static class PointDistance {
        Point point;
        int distance;

        PointDistance(Point point, int distance) {
            this.point = point;
            this.distance = distance;
        }
    }

    private static class PointPrevious {
        Point point;
        Point previous;

        PointPrevious(Point point, Point previous) {
            this.point = point;
            this.previous = previous;
        }
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
