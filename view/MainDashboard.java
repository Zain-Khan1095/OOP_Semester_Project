package view;

import db.InventoryManager;
import model.Medicine;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class MainDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private InventoryManager manager;
    private JLabel lblStatus;

    // Minimalist Color Palette
    private final Color ACCENT_BLUE = new Color(0, 102, 204);
    private final Color TEXT_DARK = new Color(51, 51, 51);

    public MainDashboard() {
        manager = new InventoryManager();
        setupWindow();
        initUI();
        refreshData();
    }

    private void setupWindow() {
        setTitle("Pharmacy Inventory - Simple Edition");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // --- 1. MINIMAL TOOLBAR ---
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JButton btnAdd = createSimpleButton("Add Medicine", ACCENT_BLUE);
        JButton btnSell = createSimpleButton("Process Sale", new Color(34, 139, 34));
        JButton btnDelete = createSimpleButton("Remove", new Color(204, 0, 0));
        JButton btnRefresh = createSimpleButton("Refresh", Color.GRAY);

        toolbar.add(btnAdd);
        toolbar.add(btnSell);
        toolbar.add(btnDelete);
        toolbar.add(btnRefresh);
        add(toolbar, BorderLayout.NORTH);

        // --- 2. CLEAN DATA TABLE ---
        String[] columns = {"ID", "Medicine Name", "Price", "Stock", "Expiry", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        styleSimpleTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 15, 10, 15));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. SIMPLE FOOTER ---
        lblStatus = new JLabel(" Total Assets: 0 PKR");
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblStatus.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(lblStatus, BorderLayout.SOUTH);

        // Listeners
        btnAdd.addActionListener(e -> handleAdd());
        btnSell.addActionListener(e -> handleSell());
        btnDelete.addActionListener(e -> handleDelete());
        btnRefresh.addActionListener(e -> refreshData());
    }

    private JButton createSimpleButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(color);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

   private void styleSimpleTable() {
    table.setRowHeight(35);
    table.setGridColor(new Color(245, 245, 245));
    table.getTableHeader().setBackground(Color.WHITE);
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
    
    // Set a professional selection color
    table.setSelectionBackground(new Color(0, 102, 204)); 
    table.setSelectionForeground(Color.WHITE); // Ensures text is white when selected

    table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean isSelected, boolean hasFocus, int r, int c) {
            JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, isSelected, hasFocus, r, c);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            
            // 1. Handle Selection State FIRST (The Fix)
            if (isSelected) {
                l.setBackground(t.getSelectionBackground());
                l.setForeground(t.getSelectionForeground()); // Makes text White
            } else {
                // 2. Handle Normal State with Highlighting
                l.setBackground(Color.WHITE); 
                if ("LOW".equals(v)) {
                    l.setForeground(new Color(204, 0, 0)); // Red for Low
                    l.setText("● LOW");
                } else {
                    l.setForeground(new Color(34, 139, 34)); // Green for OK
                    l.setText("● OK");
                }
            }
            return l;
        }
    });
}

    private void handleAdd() {
        JTextField name = new JTextField();
        JTextField price = new JTextField();
        JTextField stock = new JTextField();
        JTextField expiry = new JTextField();
        Object[] fields = { "Name:", name, "Price:", price, "Stock:", stock, "Expiry:", expiry };
        
        if (JOptionPane.showConfirmDialog(this, fields, "Add Item", 2) == 0) {
            try {
                manager.addMedicine(new Medicine(name.getText(), Double.parseDouble(price.getText()), 
                        Integer.parseInt(stock.getText()), expiry.getText()));
                refreshData();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Check inputs."); }
        }
    }

    private void handleSell() {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a medicine from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Get current data from table
    int id = (int) tableModel.getValueAt(row, 0);
    String name = (String) tableModel.getValueAt(row, 1);
    double unitPrice = Double.parseDouble(tableModel.getValueAt(row, 2).toString());
    int currentStock = (int) tableModel.getValueAt(row, 3);

    // 1. Ask for Quantity
    String input = JOptionPane.showInputDialog(this, 
            "Selling: " + name + "\nAvailable Stock: " + currentStock + "\nEnter Quantity to Sell:", "Process Sale", 
            JOptionPane.QUESTION_MESSAGE);

    if (input != null && !input.isEmpty()) {
        try {
            int qtyToSell = Integer.parseInt(input);

            // 2. Validations
            if (qtyToSell <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.");
                return;
            }
            if (qtyToSell > currentStock) {
                JOptionPane.showMessageDialog(this, "Insufficient stock! Only " + currentStock + " available.");
                return;
            }

            // 3. Logic & Confirmation
            double totalBill = qtyToSell * unitPrice;
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Confirm Sale:\n" + qtyToSell + "x " + name + "\nTotal: " + String.format("%.2f", totalBill) + " PKR", 
                    "Receipt Summary", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Update database
                manager.updateStock(id, currentStock - qtyToSell);
                refreshData();
                
                // Professional Success Message
                JOptionPane.showMessageDialog(this, "Sale Processed!\nTotal Bill: " + totalBill + " PKR");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid whole number for quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private void handleDelete() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "Delete ID " + id + "?", "Confirm", 0) == 0) {
                manager.deleteMedicine(id);
                refreshData();
            }
        }
    }

    public void refreshData() {
    tableModel.setRowCount(0); // Clear the table
    
    // Get the array instead of a List
    Medicine[] inventory = manager.getAllMedicinesArray();
    
    double totalValue = 0;
    int itemsCount = 0;

    for (int i = 0; i < inventory.length; i++) {
        Medicine m = inventory[i];
        
        // Very important: Skip the empty slots in the array
        if (m == null) continue; 

        String status = (m.getStock() <= 5) ? "LOW" : "OK";
        
        tableModel.addRow(new Object[]{
            m.getId(), 
            m.getName(), 
            m.getPrice(), 
            m.getStock(), 
            m.getExpiryDate(), 
            status
        });
        
        totalValue += (m.getPrice() * m.getStock());
        itemsCount++;
    }
    
    lblStatus.setText(String.format(" Total Assets: %.0f PKR | Items: %d", totalValue, itemsCount));
}
}