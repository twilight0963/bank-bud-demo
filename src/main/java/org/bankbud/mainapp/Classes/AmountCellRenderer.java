package org.bankbud.mainapp.Classes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AmountCellRenderer extends DefaultTableCellRenderer {
    //Color changer for the net amt, green for addition, red for loss..
    @Override
    public void setValue(Object value) {
        if (value instanceof Number number) {
            double amount = number.doubleValue();
            setForeground(amount >= 0 ? Color.GREEN : Color.RED);
            setText(String.format("%.2f", amount)); // Format to 2 decimal places
        } else {
            setForeground(Color.WHITE);
            super.setValue(value);
        }
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String amtStr = value.toString();
            if (amtStr.startsWith("+")) {
                cell.setForeground(Color.GREEN);
            } else if (amtStr.startsWith("-")) {
                cell.setForeground(Color.RED);
            } else {
                cell.setForeground(Color.WHITE); // Default color
            }

        return cell;
    }
}
