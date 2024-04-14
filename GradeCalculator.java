
    import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

    public class GradeCalculatorUI extends JFrame {
        private ArrayList<String> names = new ArrayList<>();
        private ArrayList<Double> grades = new ArrayList<>();
        private JLabel averageLabel, highestLabel, lowestLabel;
        private JTable table;

        public GradeCalculatorUI() {
            setTitle("Student Grade Calculator");
            setSize(800, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.WHITE);

            JPanel inputPanel = new JPanel(new BorderLayout());
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            buttonPanel.setBackground(Color.WHITE);

            JButton enterGradeButton = createButton("Enter Grade");
            enterGradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    enterGrade();
                }
            });
            buttonPanel.add(enterGradeButton);

            JButton calculateButton = createButton("Calculate");
            calculateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    calculate();
                }
            });
            buttonPanel.add(calculateButton);

            inputPanel.add(buttonPanel, BorderLayout.NORTH);

            // Table to display student names and grades
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Student Name");
            model.addColumn("Grade");
            table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            inputPanel.add(scrollPane, BorderLayout.CENTER);

            mainPanel.add(inputPanel, BorderLayout.WEST);

            JPanel resultPanel = new JPanel(new GridLayout(3, 1));
            resultPanel.setBackground(Color.WHITE);
            averageLabel = createLabel("Average Grade: ");
            resultPanel.add(averageLabel);

            highestLabel = createLabel("Highest Grade: ");
            resultPanel.add(highestLabel);

            lowestLabel = createLabel("Lowest Grade: ");
            resultPanel.add(lowestLabel);

            mainPanel.add(resultPanel, BorderLayout.CENTER);

            add(mainPanel);
            setVisible(true);
        }

        private JButton createButton(String text) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.BLACK);
            button.setBackground(new Color(51, 153, 255));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return button;
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            return label;
        }

        private void enterGrade() {
            String name = JOptionPane.showInputDialog(null, "Enter the name of student:");
            if (name != null && !name.trim().isEmpty()) {
                names.add(name);
                String gradeStr = JOptionPane.showInputDialog(null, "Enter the grade of student:");
                try {
                    double grade = Double.parseDouble(gradeStr);
                    if (grade < 0 || grade > 100) {
                        JOptionPane.showMessageDialog(null, "Grade must be between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        grades.add(grade);
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(new Object[]{name, grade});
                        JOptionPane.showMessageDialog(null, "Grade entered successfully.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void calculate() {
            if (!grades.isEmpty()) {
                double sum = 0;
                for (double grade : grades) {
                    sum += grade;
                }
                double average = sum / grades.size();
                DecimalFormat df = new DecimalFormat("#.##");
                averageLabel.setText("Average Grade: " + df.format(average));

                double highest = Collections.max(grades);
                highestLabel.setText("Highest Grade: " + highest);

                double lowest = Collections.min(grades);
                lowestLabel.setText("Lowest Grade: " + lowest);
            } else {
                averageLabel.setText("Average Grade: ");
                highestLabel.setText("Highest Grade: ");
                lowestLabel.setText("Lowest Grade: ");
                JOptionPane.showMessageDialog(null, "No grades entered yet.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new GradeCalculatorUI();
                }
   });
}
    }


