package aTerolGroup_OOP_FinalProject;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableListGUI extends JFrame {
    private JTable table;

    public TableListGUI() {
        initializeUI();
        getTables();
    }

    private void initializeUI() {
        setTitle("Table List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();
    }

        void getTables() {
        Object[][] data=null;
        String[] columnNames= {"Table Name"};
        DefaultTableModel displayModel = new DefaultTableModel(data,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable row editing for all cells
            }
        };
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setModel(displayModel);
        try (Connection conn = AMS_DB_connector.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            // Check if data is matched, used on updating/deleting the specified row,
            ResultSet tables = metaData.getTables(null, null, "%", null);
            while (tables.next()) {
                String tableName = tables.getString("ams_acc");
                String list[] = {tableName};
                DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
                tblModel.addRow(list);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: "+ ex.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TableListGUI());
    }
}

