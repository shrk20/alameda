import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class FoodPantryRunner extends JPanel {
    ArrayList<FoodItem> items = new ArrayList<>();
    JTextField nameField = new JTextField(10);
    JTextField qtyField = new JTextField(5);
    JCheckBox perishableBox = new JCheckBox("Perishable");
    JTextField expiryField = new JTextField(8);
    JTextField locationField = new JTextField(8);
    JButton addBtn = new JButton("Add Item");

    public FoodPantryRunner() {
        setLayout(new BorderLayout());
        JPanel input = new JPanel();
        input.add(new JLabel("Name:"));
        input.add(nameField);
        input.add(new JLabel("Qty:"));
        input.add(qtyField);
        input.add(perishableBox);
        input.add(new JLabel("Expiry:"));
        input.add(expiryField);
        input.add(new JLabel("Location:"));
        input.add(locationField);
        input.add(addBtn);
        add(input, BorderLayout.SOUTH);

        // When the button is clicked, add a new item
        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            int qty = 0;
            try {
                qty = Integer.parseInt(qtyField.getText());
            } catch (Exception ex) {}
            boolean perishable = perishableBox.isSelected();
            String expiry = expiryField.getText();
            String location = locationField.getText();
            items.add(new FoodItem(name, qty, perishable, expiry, location));
            nameField.setText("");
            qtyField.setText("");
            perishableBox.setSelected(false);
            expiryField.setText("");
            locationField.setText("");
            repaint();
        });


        // Add some starter items
        items.add(new FoodItem("Milk", 10, true, "2023-10-15", "Fridge"));
        items.add(new FoodItem("Canned Beans", 50, false, "2025-05-01", "Pantry"));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(40, 40, getWidth() - 80, getHeight() - 160);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Food Pantry Management System", 60, 80);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        int y = 120;
        g.drawString("Name", 60, y);
        g.drawString("Qty", 180, y);
        g.drawString("Perishable", 240, y);
        g.drawString("Expiry", 340, y);
        g.drawString("Location", 440, y);
        g.drawString("Remove", 540, y);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        y += 24;
        for (int i = 0; i < items.size(); i++) {
            FoodItem item = items.get(i);
            g.drawString(item.getName(), 60, y);
            g.drawString(String.valueOf(item.getQuantity()), 180, y);
            g.drawString(item.isPerishable() ? "Yes" : "No", 240, y);
            g.drawString(item.getExpiryDate() == null ? "N/A" : item.getExpiryDate(), 340, y);
            g.drawString(item.getLocation(), 440, y);
            // Draw a simple [Remove] button as text
            g.setColor(Color.RED);
            g.drawRect(530, y - 14, 70, 20);
            g.drawString("Remove", 540, y);
            g.setColor(Color.BLACK);
            y += 22;
        }
    }

    // Add mouse listener to handle remove button clicks
    {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int y = 144; // start of first row (120+24)
                for (int i = 0; i < items.size(); i++) {
                    int x = 530, bY = y - 14, w = 70, h = 20;
                    if (e.getX() >= x && e.getX() <= x + w && e.getY() >= bY && e.getY() <= bY + h) {
                        items.remove(i);
                        repaint();
                        break;
                    }
                    y += 22;
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Food Pantry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(new FoodPantryRunner());
        frame.setVisible(true);
    }

    public void setup() {
        items = new ArrayList<FoodItem>();
        FoodItem item1 = new FoodItem("Milk", 10, true, "2023-10-15", "Fridge");
        FoodItem item2 = new FoodItem("Canned Beans", 50, false, "2025-05-01", "Pantry");
        items.add(item1);
        items.add(item2);
    }

    public void displayItems(Graphics2D g2) {
        if (items == null) return;
        int margin = 40;
        int cardX = margin;
        int cardY = margin;
        int cardW = getWidth() - margin * 2;
        int cardH = getHeight() - margin * 2;

        int rowHeight = 56;
        int paddingX = 18;

        int[] colWidths = {420, 100, 140, 200, 220};
        int startY = 170;
        String[] headers = {"Name", "Qty", "Perishable", "Expiry Date", "Location"};

        
        int totalWidth = 0;
        for (int w : colWidths) {
            totalWidth += w;
        }

        
        int innerPadding = 20;
        int availableWidth = cardW - innerPadding * 2;
        if (totalWidth > availableWidth) {
            double scale = (double) availableWidth / totalWidth;
            totalWidth = 0;
            for (int i = 0; i < colWidths.length; i++) {
                colWidths[i] = Math.max(40, (int) (colWidths[i] * scale));
                totalWidth += colWidths[i];
            }
        }

        
        int startX = cardX + (cardW - totalWidth) / 2;

        
        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(startX - 10, startY - 34, totalWidth + 20, rowHeight, 10, 10);

        
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(new Color(60, 60, 60));
        int x = startX;
        for (int i = 0; i < headers.length; i++) {
            g2.drawString(headers[i], x + paddingX, startY - 10);
            x += colWidths[i];
        }

        
        Shape oldClip = g2.getClip();
        g2.setClip(cardX + 10, cardY + 80, cardW - 20, cardH - 90);

       
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        int y = startY + 14;
        for (int i = 0; i < items.size(); i++) {
            FoodItem item = items.get(i);
            
            if (i % 2 == 0) {
                g2.setColor(new Color(250, 250, 250));
            } else {
                g2.setColor(new Color(245, 248, 250));
            }
            g2.fillRect(startX - 10, y - 18, totalWidth + 20, rowHeight - 6);

            
            g2.setColor(Color.BLACK);
            x = startX;
            g2.drawString(item.getName(), x + paddingX, y + 10);
            x += colWidths[0];

            g2.drawString(String.valueOf(item.getQuantity()), x + paddingX, y + 10);
            x += colWidths[1];

            
            if (item.isPerishable()) {
                g2.setColor(new Color(200, 40, 40));
                g2.fillOval(x + paddingX, y - 2, 14, 14);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString("Yes", x + paddingX + 24, y + 10);
            } else {
                g2.setColor(new Color(40, 140, 40));
                g2.fillOval(x + paddingX, y - 2, 14, 14);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString("No", x + paddingX + 24, y + 10);
            }
            x += colWidths[2];

           
            if (item.isExpired()) {
                g2.setColor(new Color(180, 30, 30));
            } else {
                g2.setColor(new Color(60, 60, 60));
            }
            g2.drawString(item.getExpiryDate() == null ? "N/A" : item.getExpiryDate(), x + paddingX, y + 10);
            x += colWidths[3];

            g2.setColor(new Color(60, 60, 60));
            g2.drawString(item.getLocation(), x + paddingX, y + 10);

            y += rowHeight;
        }


        g2.setClip(oldClip);
    }
}

