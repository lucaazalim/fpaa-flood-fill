package br.pucminas.fpaa.floodfill;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DrawingFrame extends JFrame {

    private BufferedImage canvas;
    private Graphics2D g2d;
    private int currentX, currentY, oldX, oldY;

    public DrawingFrame(int width, int height) {
        setTitle("Flood Fill Drawing Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize canvas
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = canvas.createGraphics();
        // RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint the canvas background white
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 1000, 1000);

        // Set drawing color and stroke
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        // Create drawing panel
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);
            }
        };

        drawingPanel.setPreferredSize(new Dimension(width, height));
        drawingPanel.setBackground(Color.WHITE);

        // Add mouse listeners for drawing
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                if (g2d != null) {
                    g2d.drawLine(oldX, oldY, currentX, currentY);
                    drawingPanel.repaint();
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton paintButton = new JButton("Paint");

        paintButton.addActionListener(_ -> {
            new ImageFloodFill(canvas).execute();
            drawingPanel.repaint();
        });

        buttonPanel.add(paintButton);

        // Create reset button
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(_ -> {
            // Clear the canvas and paint it white again
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // Reset drawing color to black
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));

            // Repaint the drawing panel
            drawingPanel.repaint();
        });

        buttonPanel.add(resetButton);

        // Create mandala generator button
        JButton mandalaButton = new JButton("Generate Mandala");

        mandalaButton.addActionListener(_ -> {
            // Generate a random mandala
            MandalaGenerator generator = new MandalaGenerator();
            BufferedImage mandalaImage = generator.generateMandala(width, height);

            // Clear the canvas first
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // Draw the mandala image onto the canvas
            g2d.drawImage(mandalaImage, 0, 0, null);

            // Reset drawing color to black
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));

            // Repaint the drawing panel
            drawingPanel.repaint();
        });

        buttonPanel.add(mandalaButton);

        // Add components to frame
        add(new JScrollPane(drawingPanel), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
