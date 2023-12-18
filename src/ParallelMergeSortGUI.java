import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSortGUI extends JFrame {
    private JTextField inputField;
    private DefaultListModel<Integer> listModel;
    private JList<Integer> arrayJList;
    private JTextArea resultArea;

    public ParallelMergeSortGUI() {
        setTitle("Parallel Merge Sort GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        arrayJList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(arrayJList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputField = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton sortButton = new JButton("Sort");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNumberToList();
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSort();
            }
        });

        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(sortButton);

        mainPanel.add(listScrollPane, BorderLayout.WEST);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultArea, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void addNumberToList() {
        try {
            String inputText = inputField.getText();
            int number = Integer.parseInt(inputText);
            listModel.addElement(number);
            inputField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int[] mergeSort(int[] array) {
        if (array.length <= 1) {
            return array;
        }

        int midpoint = array.length / 2;

        int[] left = Arrays.copyOfRange(array, 0, midpoint);
        int[] right = Arrays.copyOfRange(array, midpoint, array.length);

        int[] sortedLeft = mergeSort(left);
        int[] sortedRight = mergeSort(right);

        return merge(sortedLeft, sortedRight);
    }

    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];

        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    private void performSort() {
        int[] array = new int[listModel.getSize()];
        for (int i = 0; i < listModel.getSize(); i++) {
            array[i] = listModel.getElementAt(i);
        }

        int[] sortedArray = mergeSort(array);

        resultArea.setText("Original Array: " + Arrays.toString(array) + "\n"
                + "Sorted Array: " + Arrays.toString(sortedArray));
    }
}
