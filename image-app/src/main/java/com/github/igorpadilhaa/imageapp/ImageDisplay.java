package com.github.igorpadilhaa.imageapp;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.github.igorpadilhaa.imagetool.Image;
import com.github.igorpadilhaa.imagetool.filter.CommonFilters;
import com.github.igorpadilhaa.imagetool.filter.ImageFilter;

class ImageDisplay {

    protected static java.awt.Color BACKGROUND_COLOR = java.awt.Color.BLACK;
    
    private final ImagePanel imagePanel;
    private final Image displayImage;

    public ImageDisplay(Image displayImage) {
        this.imagePanel = new ImagePanel();
        this.displayImage = displayImage;
    }

    public void showUI() {
        JFrame frame = new JFrame("Image tool: display");
        frame.setLayout(new BorderLayout());
        frame.setBackground(BACKGROUND_COLOR);

        imagePanel.setImage(displayImage);
        frame.add(imagePanel);

        JComponent toolBar = createToolBar(imagePanel);
        frame.add(toolBar, BorderLayout.PAGE_START);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
    
    public JComponent createToolBar(ImagePanel imagePanel) {
        Box container = Box.createVerticalBox();
        container.add(filterMenu());
        
        return container;
    }

    private JComponent filterMenu() {
        Map<String, ImageFilter> filters = new HashMap<>();
        filters.put("None", (img) -> {});
        filters.put("Grayscale", CommonFilters.GRAYSCALE);
        filters.put("Sobel", CommonFilters.SOBEL);

        JComboBox<String> filterCombo = new JComboBox<>();

        filters.keySet().forEach(filterCombo::addItem);
        filterCombo.addActionListener(a -> {
            int filterIndex = filterCombo.getSelectedIndex();
            if (filterIndex == -1)
                throw new IllegalStateException("no filter selected to be applied");

            ImageFilter selectedFilter = filters.get(filterCombo.getItemAt(filterIndex));
            applyFilter(selectedFilter);
        });

        filterCombo.setSelectedItem("None");

        Box container = Box.createHorizontalBox();
        container.add(new JLabel("Filters: "));
        container.add(filterCombo);

        return container;
    }

    private void applyFilter(ImageFilter filter) {
        Image bufferImage = displayImage.copy();
        filter.apply(bufferImage);

        this.imagePanel.setImage(bufferImage);
    }

    private class ImagePanel extends JPanel {
        
        private Image image;

        ImagePanel() {
            this.image = null;
        }

        @Override
        public void paint(Graphics g) {
            paintComponent(g);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            Optional<Image> imageOptional = currentImage();
            if (imageOptional.isEmpty())
                return;

            Image drawingImage = imageOptional.get();

            BufferedImage buffImage = new BufferedImage(drawingImage.width(), drawingImage.height(), BufferedImage.TYPE_INT_ARGB);
            buffImage.setRGB(0, 0, drawingImage.width(), drawingImage.height(), drawingImage.pixels(), 0, drawingImage.width());

            int cx = this.getWidth() / 2;
            int cy = this.getHeight() / 2;

            int x = cx - drawingImage.width() / 2;
            int y = cy - drawingImage.height() / 2;

            g.drawImage(buffImage, x, y, null);
        }

        public Optional<Image> currentImage() {
            return Optional.ofNullable(image);
        }

        public void setImage(Image image) {
            Objects.requireNonNull(image);
            
            this.image = image;
            repaint();
        }
    }
}
