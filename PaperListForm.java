/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.papermanager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Main GUI window: lists all papers in a table and provides
 * Add / Edit / Delete / View Details / Open PDF actions.
 */
public class PaperListForm extends JFrame {

    private final PaperController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public PaperListForm(PaperController controller) {
        super("Research Paper Management");
        this.controller = controller;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        String[] columns = {"ID", "Title", "Author", "Year", "Category", "Has PDF"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 8, 0));
        JButton addBtn = new JButton("Add Paper");
        JButton editBtn = new JButton("Edit Paper");
        JButton deleteBtn = new JButton("Delete Paper");
        JButton viewBtn = new JButton("View Details");
        JButton openBtn = new JButton("Open PDF");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(openBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> onAdd());
        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());
        viewBtn.addActionListener(e -> onViewDetails());
        openBtn.addActionListener(e -> onOpenPdf());

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Paper> papers = controller.getAllPapers();
        for (Paper p : papers) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getTitle(), p.getAuthor(), p.getYear(),
                    p.getCategory(), p.hasPdf() ? "Yes" : "No"
            });
        }
    }

    private int getSelectedId() {
        int row = table.getSelectedRow();
        if (row == -1) return -1;
        return (int) tableModel.getValueAt(row, 0);
    }

    private void onAdd() {
        UploadPaperForm form = new UploadPaperForm(this, controller, null);
        form.setVisible(true);
        refreshTable();
    }

    private void onEdit() {
        int id = getSelectedId();
        if (id == -1) {
            showNoSelectionWarning();
            return;
        }
        Paper paper = controller.getPaperById(id);
        UploadPaperForm form = new UploadPaperForm(this, controller, paper);
        form.setVisible(true);
        refreshTable();
    }

    private void onDelete() {
        int id = getSelectedId();
        if (id == -1) {
            showNoSelectionWarning();
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this paper? This cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePaper(id);
            refreshTable();
        }
    }

    private void onViewDetails() {
        int id = getSelectedId();
        if (id == -1) {
            showNoSelectionWarning();
            return;
        }
        Paper paper = controller.getPaperById(id);
        PaperDetailsForm form = new PaperDetailsForm(this, paper);
        form.setVisible(true);
    }

    private void onOpenPdf() {
        int id = getSelectedId();
        if (id == -1) {
            showNoSelectionWarning();
            return;
        }
        boolean opened = controller.openPdf(id);
        if (!opened) {
            JOptionPane.showMessageDialog(this,
                    "No PDF is attached to this paper, or it could not be opened.",
                    "Cannot Open PDF", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showNoSelectionWarning() {
        JOptionPane.showMessageDialog(this,
                "Please select a paper from the list first.",
                "No Paper Selected", JOptionPane.WARNING_MESSAGE);
    }
}
