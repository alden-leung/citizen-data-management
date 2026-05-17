package prog2.finalgroup;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class Gui extends JFrame {

    private List<Citizen> citizens = new ArrayList<>();

    private DefaultTableModel tableModel;
    private JTable citizenTable;

    private JLabel lblTotal;
    private JLabel lblMale;
    private JLabel lblFemale;
    private JLabel lblResidents;
    private JLabel lblSeniors;
    private JLabel lblAvgAge;
    private JLabel lblYoungest;
    private JLabel lblOldest;
    private JLabel lblStatus;

    private final Color COLOR_BG_MAIN      = new Color(187, 188, 195);    // Soft professional light gray
    private final Color COLOR_BG_PANEL     = new Color(204, 204, 204);    // Clean white for panels/tables
    private final Color COLOR_TEXT_PRIMARY = new Color(0, 0, 0);       // Dark gray text for readability
    private final Color COLOR_TEXT_MUTED   = new Color(31, 50, 94);    // Muted gray for secondary text
    private final Color COLOR_ACCENT       = new Color(99, 99, 99);    // Subtle borders and header backgrounds
    private final Color COLOR_BTN_HOVER    = new Color(114, 143, 66);    // Slightly darker gray for button hover
    private final Color COLOR_SELECTION    = new Color(57, 53, 54);    // Very soft blue-gray for table selection

    private final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 20);
    private final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);

    public Gui() {
        setTitle("Citizen Registry - CS 122 Final Project 02");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        getContentPane().setBackground(COLOR_BG_MAIN);
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        add(createTopPanel(),    BorderLayout.NORTH);
        add(createTablePanel(),  BorderLayout.CENTER);
        add(createStatsPanel(),  BorderLayout.EAST);
        add(createStatusBar(),   BorderLayout.SOUTH);

        loadData();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(COLOR_BG_MAIN);

        JLabel title = new JLabel("Citizen Registry");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_TEXT_PRIMARY);

        JButton btnLoad = new JButton("Load Data");
        btnLoad.setFont(FONT_BODY);
        btnLoad.setBackground(COLOR_ACCENT);
        btnLoad.setForeground(COLOR_TEXT_PRIMARY);
        btnLoad.setFocusPainted(false);
        btnLoad.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));


        btnLoad.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnLoad.setBackground(COLOR_BTN_HOVER); }
            public void mouseExited(MouseEvent e) { btnLoad.setBackground(COLOR_ACCENT); }
        });

        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        panel.add(title);
        panel.add(btnLoad);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 10));
        panel.setBackground(COLOR_BG_MAIN);

        JLabel heading = new JLabel("All Citizen Records");
        heading.setFont(FONT_HEADING);
        heading.setForeground(COLOR_TEXT_MUTED);
        panel.add(heading, BorderLayout.NORTH);

        String[] columns = {"Full Name", "Email", "Address", "Age", "District", "Resident", "Gender"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        citizenTable = new JTable(tableModel);
        citizenTable.setRowHeight(28);
        citizenTable.setFont(FONT_BODY);
        citizenTable.setBackground(COLOR_BG_PANEL);
        citizenTable.setForeground(COLOR_TEXT_PRIMARY);
        citizenTable.setGridColor(COLOR_ACCENT);
        citizenTable.setSelectionBackground(COLOR_SELECTION);
        citizenTable.setSelectionForeground(COLOR_TEXT_PRIMARY);

        JTableHeader header = citizenTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(COLOR_ACCENT);
        header.setForeground(COLOR_TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(citizenTable);
        scrollPane.getViewport().setBackground(COLOR_BG_PANEL);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_ACCENT));

        panel.add(scrollPane, BorderLayout.CENTER);

        citizenTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showSelectedCitizenDetails();
                }
            }
        });

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 1, 5, 5));
        panel.setPreferredSize(new Dimension(240, 0));
        panel.setBackground(COLOR_BG_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_ACCENT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel heading = new JLabel("Statistics");
        heading.setFont(FONT_HEADING);
        heading.setForeground(COLOR_TEXT_PRIMARY);
        heading.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_ACCENT));

        lblTotal     = createStyledLabel("Total: -");
        lblMale      = createStyledLabel("Male: -");
        lblFemale    = createStyledLabel("Female: -");
        lblResidents = createStyledLabel("Residents: -");
        lblSeniors   = createStyledLabel("Seniors (60+): -");
        lblAvgAge    = createStyledLabel("Avg Age: -");
        lblYoungest  = createStyledLabel("Youngest: -");
        lblOldest    = createStyledLabel("Oldest: -");

        panel.add(heading);
        panel.add(lblTotal);
        panel.add(lblMale);
        panel.add(lblFemale);
        panel.add(lblResidents);
        panel.add(lblSeniors);
        panel.add(lblAvgAge);
        panel.add(lblYoungest);
        panel.add(lblOldest);

        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_BODY);
        label.setForeground(COLOR_TEXT_MUTED);
        return label;
    }

    private JPanel createStatusBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(COLOR_BG_MAIN);

        lblStatus = new JLabel("Ready.");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(COLOR_TEXT_MUTED);

        panel.add(lblStatus);
        return panel;
    }

    private void loadData() {
        citizens = MyProgramUtility.loadCitizens("res/data.csv");

        if (citizens.isEmpty()) {
            UIManager.put("OptionPane.background", COLOR_BG_PANEL);
            UIManager.put("Panel.background", COLOR_BG_PANEL);
            UIManager.put("Label.foreground", COLOR_TEXT_PRIMARY);
            JOptionPane.showMessageDialog(this,
                    "No data loaded. Check that res/data.csv exists.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        populateTable();
        updateStats();
        lblStatus.setText("Loaded " + citizens.size() + " citizens.");
    }

    private void populateTable() {
        tableModel.setRowCount(0);

        for (Citizen c : citizens) {
            tableModel.addRow(new Object[]{
                    c.getFullName(),
                    c.getEmail(),
                    c.getAddress(),
                    c.getAge(),
                    c.getDistrict(),
                    c.isResident() ? "Resident" : "Non-Resident",
                    c.getGender() == 'M' ? "Male" : "Female"
            });
        }
    }

    private void updateStats() {
        Citizen youngest = CitizenStatistics.getYoungestCitizen(citizens);
        Citizen oldest   = CitizenStatistics.getOldestCitizen(citizens);

        lblTotal    .setText("Total: "         + citizens.size());
        lblMale     .setText("Male: "          + CitizenStatistics.countMales(citizens));
        lblFemale   .setText("Female: "        + CitizenStatistics.countFemales(citizens));
        lblResidents.setText("Residents: "     + CitizenStatistics.countResidents(citizens));
        lblSeniors  .setText("Seniors (60+): " + CitizenStatistics.countSeniorCitizens(citizens));
        lblAvgAge   .setText("Avg Age: "       + String.format("%.1f", CitizenStatistics.getAverageAge(citizens)));
        lblYoungest .setText("Youngest: "      + (youngest != null ? youngest.getFullName() + " (" + youngest.getAge() + ")" : "-"));
        lblOldest   .setText("Oldest: "        + (oldest   != null ? oldest.getFullName()   + " (" + oldest.getAge()   + ")" : "-"));
    }

    private void showSelectedCitizenDetails() {
        int row = citizenTable.getSelectedRow();
        if (row < 0 || row >= citizens.size()) return;

        Citizen c = citizens.get(row);

        String details =
                "Name:\t" + c.getFullName()  + "\n" +
                        "Email:\t" + c.getEmail()     + "\n" +
                        "Address:\t" + c.getAddress()   + "\n" +
                        "Age:\t" + c.getAge()       + "\n" +
                        "Gender:\t" + (c.getGender() == 'M' ? "Male" : "Female") + "\n" +
                        "District:\t" + c.getDistrict()  + "\n" +
                        "Status:\t" + (c.isResident() ? "Resident" : "Non-Resident");

        Color originalPanelColor = UIManager.getColor("Panel.background");
        Color originalLabelColor = UIManager.getColor("Label.foreground");

        UIManager.put("OptionPane.background", COLOR_BG_PANEL);
        UIManager.put("Panel.background", COLOR_BG_PANEL);
        UIManager.put("Label.foreground", COLOR_TEXT_PRIMARY);

        JOptionPane.showMessageDialog(this, details, "Citizen Details", JOptionPane.INFORMATION_MESSAGE);

        UIManager.put("Panel.background", originalPanelColor);
        UIManager.put("Label.foreground", originalLabelColor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }
}