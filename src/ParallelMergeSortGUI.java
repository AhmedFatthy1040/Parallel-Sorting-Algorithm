import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSortGUI extends JFrame {
    private JTextField inputField;
    private DefaultListModel<Integer> listModel;
    private JList<Integer> arrayJList;
    private JTextArea resultArea;
    private JLabel statusLabel;
    private JLabel arrayCountLabel;
    private JProgressBar progressBar;

    public ParallelMergeSortGUI() {
        initializeUI();
        setupEventHandlers();
        setUITheme();
    }

    private void initializeUI() {
        setTitle("🚀 Parallel Merge Sort - Advanced Sorting Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255));

        JPanel topPanel = new JPanel(new BorderLayout());
        createHeaderPanel(topPanel);
        createInputPanel(topPanel);
        
        createContentPanel(mainPanel);
        createFooterPanel(mainPanel);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);
    }

    private void createHeaderPanel(JPanel topPanel) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Parallel Merge Sort Algorithm");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("High-Performance Concurrent Sorting with Fork/Join Framework");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(220, 230, 240));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.setOpaque(false);
        titleContainer.add(titleLabel, BorderLayout.CENTER);
        titleContainer.add(subtitleLabel, BorderLayout.SOUTH);

        headerPanel.add(titleContainer, BorderLayout.CENTER);
        topPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createInputPanel(JPanel topPanel) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        inputPanel.setBackground(new Color(250, 252, 255));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Input Controls",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));

        inputField = new JTextField(15);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        inputField.setToolTipText("Enter an integer value to add to the array");

        JButton addButton = createStyledButton("➕ Add Number", new Color(34, 139, 34), Color.WHITE);
        JButton sortButton = createStyledButton("🔄 Sort Array", new Color(70, 130, 180), Color.WHITE);
        JButton clearButton = createStyledButton("🗑️ Clear All", new Color(220, 20, 60), Color.WHITE);
        JButton randomButton = createStyledButton("🎲 Add Random", new Color(255, 140, 0), Color.WHITE);

        inputPanel.add(new JLabel("Number:"));
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(sortButton);
        inputPanel.add(clearButton);
        inputPanel.add(randomButton);

        addButton.addActionListener(event -> addNumberToList());
        sortButton.addActionListener(event -> performSort());
        clearButton.addActionListener(event -> clearArray());
        randomButton.addActionListener(event -> addRandomNumbers());

        topPanel.add(inputPanel, BorderLayout.SOUTH);
    }

    private void createContentPanel(JPanel mainPanel) {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 248, 255));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Current Array",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));

        listModel = new DefaultListModel<>();
        arrayJList = new JList<>(listModel);
        arrayJList.setFont(new Font("Consolas", Font.PLAIN, 14));
        arrayJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        arrayJList.setBackground(new Color(255, 255, 255));
        arrayJList.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane listScrollPane = new JScrollPane(arrayJList);
        listScrollPane.setPreferredSize(new Dimension(250, 300));
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        arrayCountLabel = new JLabel("Elements: 0");
        arrayCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        arrayCountLabel.setForeground(new Color(70, 130, 180));
        arrayCountLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        leftPanel.add(arrayCountLabel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Sorting Results & Performance",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setBackground(new Color(248, 248, 255));
        resultArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        rightPanel.add(resultScrollPane, BorderLayout.CENTER);

        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void createFooterPanel(JPanel mainPanel) {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(70, 130, 180));
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        statusLabel = new JLabel("Ready to sort • Add numbers using the input field above");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);

        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        progressBar.setStringPainted(true);
        progressBar.setString("Sorting...");

        footerPanel.add(statusLabel, BorderLayout.CENTER);
        footerPanel.add(progressBar, BorderLayout.EAST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Color originalBg = bgColor;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg);
            }
        });
        
        return button;
    }

    private void setupEventHandlers() {
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addNumberToList();
                }
            }
        });
    }

    private void setUITheme() {
    }

    private void addNumberToList() {
        try {
            String inputText = inputField.getText().trim();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a number.", 
                    "Input Required", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int number = Integer.parseInt(inputText);
            listModel.addElement(number);
            inputField.setText("");
            updateArrayCount();
            updateStatus("Number " + number + " added to array");
            
            inputField.requestFocus();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid input. Please enter a valid integer.\nExample: 42, -15, 1000", 
                "Invalid Number Format", 
                JOptionPane.ERROR_MESSAGE);
            inputField.selectAll();
            inputField.requestFocus();
        }
    }

    private void addRandomNumbers() {
        try {
            String input = JOptionPane.showInputDialog(this, 
                "How many random numbers to add? (1-100)", 
                "Add Random Numbers", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                int count = Integer.parseInt(input.trim());
                if (count < 1 || count > 100) {
                    throw new NumberFormatException("Count must be between 1 and 100");
                }
                
                java.util.Random random = new java.util.Random();
                for (int i = 0; i < count; i++) {
                    int randomNum = random.nextInt(1000);
                    listModel.addElement(randomNum);
                }
                
                updateArrayCount();
                updateStatus(count + " random numbers added to array");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid number between 1 and 100.", 
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearArray() {
        if (listModel.getSize() > 0) {
            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to clear all " + listModel.getSize() + " numbers?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
                listModel.clear();
                resultArea.setText("");
                updateArrayCount();
                updateStatus("Array cleared");
            }
        } else {
            updateStatus("Array is already empty");
        }
    }

    private void updateArrayCount() {
        arrayCountLabel.setText("Elements: " + listModel.getSize());
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void performSort() {
        if (listModel.getSize() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please add some numbers to the array first.\n\nTip: Use the input field above or click 'Add Random' for quick testing.", 
                "Empty Array", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        progressBar.setVisible(true);
        statusLabel.setText("Sorting " + listModel.getSize() + " elements...");
        
        int[] array = new int[listModel.getSize()];
        for (int i = 0; i < listModel.getSize(); i++) {
            array[i] = listModel.getElementAt(i);
        }

        SwingWorker<int[], Void> worker = new SwingWorker<int[], Void>() {
            private long sortingTime;
            private int[] originalArray;
            
            @Override
            protected int[] doInBackground() throws Exception {
                originalArray = array.clone();
                long startTime = System.nanoTime();
                
                try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
                    int[] result = forkJoinPool.invoke(new MergeSortTask(array));
                    sortingTime = System.nanoTime() - startTime;
                    return result;
                }
            }
            
            @Override
            protected void done() {
                try {
                    int[] sortedArray = get();
                    displayResults(originalArray, sortedArray, sortingTime);
                    progressBar.setVisible(false);
                    updateStatus("Sorting completed successfully in " + 
                               String.format("%.3f", sortingTime / 1_000_000.0) + " ms");
                } catch (Exception ex) {
                    progressBar.setVisible(false);
                    updateStatus("Error occurred during sorting");
                    JOptionPane.showMessageDialog(ParallelMergeSortGUI.this, 
                        "Error during sorting: " + ex.getMessage(), 
                        "Sorting Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        worker.execute();
    }

    private void displayResults(int[] original, int[] sorted, long timeNanos) {
        StringBuilder result = new StringBuilder();
        
        result.append("🔍 SORTING ANALYSIS REPORT\n");
        result.append("═".repeat(50)).append("\n\n");
        
        result.append("📊 Array Information:\n");
        result.append("   • Size: ").append(original.length).append(" elements\n");
        result.append("   • Algorithm: Parallel Merge Sort (Fork/Join)\n");
        result.append("   • Time Complexity: O(n log n)\n\n");
        
        result.append("⏱️ Performance Metrics:\n");
        result.append("   • Sorting Time: ").append(String.format("%.3f ms", timeNanos / 1_000_000.0)).append("\n");
        result.append("   • Throughput: ").append(String.format("%.0f elements/ms", 
                                                      original.length / (timeNanos / 1_000_000.0))).append("\n\n");
        
        result.append("📋 Original Array:\n");
        if (original.length <= 50) {
            result.append("   ").append(Arrays.toString(original)).append("\n\n");
        } else {
            result.append("   [").append(original[0]);
            for (int i = 1; i < Math.min(20, original.length); i++) {
                result.append(", ").append(original[i]);
            }
            result.append(", ... (").append(original.length - 20).append(" more), ");
            for (int i = Math.max(20, original.length - 5); i < original.length; i++) {
                result.append(original[i]);
                if (i < original.length - 1) result.append(", ");
            }
            result.append("]\n\n");
        }
        
        result.append("✅ Sorted Array:\n");
        if (sorted.length <= 50) {
            result.append("   ").append(Arrays.toString(sorted)).append("\n\n");
        } else {
            result.append("   [").append(sorted[0]);
            for (int i = 1; i < Math.min(20, sorted.length); i++) {
                result.append(", ").append(sorted[i]);
            }
            result.append(", ... (").append(sorted.length - 20).append(" more), ");
            for (int i = Math.max(20, sorted.length - 5); i < sorted.length; i++) {
                result.append(sorted[i]);
                if (i < sorted.length - 1) result.append(", ");
            }
            result.append("]\n\n");
        }
        
        boolean isCorrect = true;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] < sorted[i-1]) {
                isCorrect = false;
                break;
            }
        }
        
        result.append("✨ Verification:\n");
        result.append("   • Sorting Status: ").append(isCorrect ? "✅ CORRECT" : "❌ ERROR").append("\n");
        result.append("   • Elements Preserved: ").append(original.length == sorted.length ? "✅ YES" : "❌ NO").append("\n");
        
        if (original.length >= 1000) {
            result.append("\n🚀 Performance Note: Excellent! Parallel processing shows benefits for large datasets.");
        } else if (original.length >= 100) {
            result.append("\n💡 Performance Note: Good performance. Try larger datasets to see parallel benefits.");
        }
        
        resultArea.setText(result.toString());
        resultArea.setCaretPosition(0);
    }
}
