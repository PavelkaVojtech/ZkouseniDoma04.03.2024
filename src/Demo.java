import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo extends JFrame{
    private JPanel panelMain;
    private JTextArea textArea1;
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu jMenu = new JMenu("File");
    private JMenuItem jMenuItem = new JMenuItem("Open");
    private JMenuItem jMenuItem2 = new JMenuItem("Save");
    private JMenuItem jMenuItem3 = new JMenuItem("Save as");

    private File file;

    public Demo(){
        initComponents();
    }

    public void initComponents(){
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Demo");
        setSize(400, 300);
        jMenuBar.add(jMenu);
        jMenu.add(jMenuItem);
        jMenu.add(jMenuItem2);
        jMenu.add(jMenuItem3);
        setJMenuBar(jMenuBar);

        jMenuItem.addActionListener(e -> otevriSoubor());
        jMenuItem2.addActionListener(e -> ulozSoubor(file));
        jMenuItem3.addActionListener(e -> ulozSouborJako());
    }

    public void otevriSoubor(){
        JFileChooser jFileChooser = new JFileChooser("..");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("textové soubory", "txt"));
        int result = jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            file = jFileChooser.getSelectedFile();
            cteniZeSouboru(file);
        }
        else {
            JOptionPane.showMessageDialog(this, "soubor nenalezen");
        }
    }

    public void cteniZeSouboru(File textak){
        try (
                FileReader fileReader = new FileReader(textak);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                Scanner scaner = new Scanner(bufferedReader);
                )
        {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while (scaner.hasNextLine()){
                line = scaner.nextLine();
                textArea1.setText(line);
                stringBuilder.append(line).append("\n");
            }
            textArea1.setText(String.valueOf(stringBuilder));
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void ulozSoubor(File file){
        try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));)
        {
            printWriter.write(textArea1.getText());
            JOptionPane.showMessageDialog(this, "Hotovo");
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(this, "Nastal problém: " + e.getLocalizedMessage());
        }
    }

    public void ulozSouborJako(){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("text", "txt"));
        int result = jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            file = jFileChooser.getSelectedFile();
            file = new File(file.getAbsolutePath() + ".txt");
            ulozSoubor(file);
        }
        else {
            JOptionPane.showMessageDialog(this, "soubor nenalezen");
        }
    }
}
