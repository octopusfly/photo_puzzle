package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/*
 * Update log:
 * 2017-02-03:
 *      Created by Zhang Yufei.
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.Controller;

/**
 * The main frame for this program.
 *
 * @author Zhang Yufei
 * @version 2017-02-03
 */
public class MainFrame extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * The buttons used for setting source picture, element photo directory and
     * the save directory.
     */
    private JButton srcButton, photoButton, saveButton;

    /**
     * The text fields used for recording source picture, element photo
     * directory and the save directory.
     */
    private JTextField srcField, photoField, saveField;

    /**
     * The scroll bar used for change the size of photo.
     */
    private JSlider sizeSlider;

    /**
     * Record the photoSize;
     */
    private JTextField sizeField;

    /**
     * The buttons used for parameters setting and resetting and generate the
     * result;
     */
    private JButton generateButton, setButton, resetButton;

    /**
     * The text fields recording the width and height of the element photo;
     */
    private JTextField widthField, heightField;

    /**
     * Listeners for buttons.
     */
    private FileListener fileListener;

    /**
     * Listeners for buttons.
     */
    private ParameterListener parameterListener;

    /**
     * listener for size slider.
     */
    private ChangeListener sizeListener;
    
    /**
     * Used for display the current state of the program.
     */
    private JTextField stateField;

    public MainFrame() {

        init();
        addComponent();

        setTitle("照片拼图");
        setSize(400, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Initial the components.
     */
    private void init() {
        fileListener = new FileListener();
        parameterListener = new ParameterListener();
        sizeListener = new SizeListener();

        srcField = new JTextField(20);
        photoField = new JTextField(20);
        saveField = new JTextField(20);
        srcField.setEditable(false);
        photoField.setEditable(false);
        saveField.setEditable(false);
        srcField.setBackground(Color.white);
        photoField.setBackground(Color.white);
        saveField.setBackground(Color.white);
        srcField.setForeground(Color.red);
        photoField.setForeground(Color.red);
        saveField.setForeground(Color.red);

        srcButton = new JButton("浏览");
        photoButton = new JButton("浏览");
        saveButton = new JButton("浏览");
        srcButton.addActionListener(fileListener);
        photoButton.addActionListener(fileListener);
        saveButton.addActionListener(fileListener);

        sizeSlider = new JSlider(1, 10, 5);
        sizeField = new JTextField(5);
        sizeField.setEditable(false);
        sizeField.setForeground(Color.red);
        sizeField.setText("5");

        setButton = new JButton("保存参数");
        resetButton = new JButton("重置参数");
        generateButton = new JButton("生成图像");

        setButton.addActionListener(parameterListener);
        resetButton.addActionListener(parameterListener);

        widthField = new JTextField(5);
        heightField = new JTextField(5);
        widthField.setText("50");
        heightField.setText("50");
        widthField.setForeground(Color.red);
        heightField.setForeground(Color.red);

        sizeSlider.addChangeListener(sizeListener);

        generateButton.setEnabled(false);

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stateField.setText("当前状态：正在合成...");
                Thread thread = new Thread() {
                    public void run() {
                        Controller.setParameter(srcField.getText(), photoField.getText(),
                                saveField.getText(),
                                Integer.parseInt(widthField.getText()),
                                Integer.parseInt(heightField.getText()),
                                Integer.parseInt(sizeField.getText()));
                        setButton.setEnabled(false);
                        resetButton.setEnabled(false);
                        Controller.startup();
                        Controller.delete();
                        setButton.setEnabled(true);
                        resetButton.setEnabled(true);
                        generateButton.setEnabled(false);
                        stateField.setText("当前状态：无操作");
                        JOptionPane.showMessageDialog(null, "合成完成");
                    }
                };
                
                thread.start();
            }
        });
        
        stateField = new JTextField(10);
        stateField.setText("当前状态：无操作");
        stateField.setForeground(Color.red);
        stateField.setEditable(false);
    }

    /**
     * add components into frame.
     */
    private void addComponent() {
        setLayout(new BorderLayout(5, 5));

        JPanel p_center = new JPanel();
        p_center.setLayout(new GridLayout(5, 1, 5, 5));

        JPanel p_center_1 = new JPanel();
        p_center_1.setLayout(new FlowLayout(5));
        p_center_1.add(new JLabel("原始图片："));
        p_center_1.add(srcField);
        p_center_1.add(srcButton);
        p_center.add(p_center_1);

        JPanel p_center_2 = new JPanel();
        p_center_2.setLayout(new FlowLayout(5));
        p_center_2.add(new JLabel("拼图图片："));
        p_center_2.add(photoField);
        p_center_2.add(photoButton);
        p_center.add(p_center_2);

        JPanel p_center_3 = new JPanel();
        p_center_3.setLayout(new FlowLayout(5));
        p_center_3.add(new JLabel("存储路径："));
        p_center_3.add(saveField);
        p_center_3.add(saveButton);
        p_center.add(p_center_3);

        JPanel p_center_4 = new JPanel();
        p_center_4.setLayout(new FlowLayout(5));
        p_center_4.add(new JLabel("图片大小："));
        p_center_4.add(sizeField);
        p_center_4.add(sizeSlider);
        p_center.add(p_center_4);

        JPanel p_center_5 = new JPanel();
        p_center_5.setLayout(new GridLayout(1, 4, 5, 5));
        p_center_5.add(new JLabel("拼图宽度："));
        p_center_5.add(widthField);
        p_center_5.add(new JLabel("  拼图高度:"));
        p_center_5.add(heightField);
        p_center.add(p_center_5);

        p_center.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        JPanel outer = new JPanel();
        outer.add(p_center);
        add(outer, BorderLayout.CENTER);

        JPanel p_south = new JPanel();
        FlowLayout layout = new FlowLayout(5);
        layout.setAlignment(FlowLayout.CENTER);
        p_south.setLayout(layout);

        p_south.add(setButton);
        p_south.add(resetButton);
        p_south.add(generateButton);

        add(p_south, BorderLayout.SOUTH);
        
        add(stateField, BorderLayout.NORTH);
    }

    /**
     * Check if the input parameters are correct.
     * 
     * @return <code>true</code> if the input is correct, or <code>false</code>
     */
    private boolean check() {
        return true;
    }

    /**
     * Listener for buttons.
     */
    class FileListener implements ActionListener {
        private JFileChooser chooser;

        public FileListener() {
            chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }

        public void actionPerformed(ActionEvent event) {
            JButton button = (JButton) event.getSource();
            JTextField result = null;
            if (button == srcButton) {
                result = srcField;
            } else if (button == photoButton) {
                result = photoField;
            } else if (button == saveButton) {
                result = saveField;
            }

            chooser.showOpenDialog(MainFrame.this);
            File file = chooser.getSelectedFile();
            if (file != null) {
                result.setText(file.getAbsolutePath());
            }
        }
    }

    /**
     * Listener for buttons.
     */
    class ParameterListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton button = (JButton) event.getSource();
            if (button == setButton) {
                srcButton.setEnabled(false);
                photoButton.setEnabled(false);
                saveButton.setEnabled(false);
                sizeSlider.setEnabled(false);
                widthField.setEditable(false);
                heightField.setEditable(false);
                generateButton.setEnabled(true);

                if (check()) {
                    JOptionPane.showMessageDialog(MainFrame.this, "设定成功");
                    stateField.setText("当前状态：参数设定完成");
                }
            } else if (button == resetButton) {
                srcButton.setEnabled(true);
                photoButton.setEnabled(true);
                saveButton.setEnabled(true);
                sizeSlider.setEnabled(true);

                widthField.setEditable(true);
                heightField.setEditable(true);
                
                generateButton.setEnabled(false);
                stateField.setText("当前状态：无操作");
            }
        }
    }

    class SizeListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            int x = sizeSlider.getValue();
            sizeField.setText("" + x);
        }
    }
}
