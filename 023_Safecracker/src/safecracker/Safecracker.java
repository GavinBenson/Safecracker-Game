/*
 * Safecracker.java
 */
package safecracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.net.URL;
import java.applet.*;

public class Safecracker extends JFrame
{

	int count = 0;

  ImagePanel safePanel = new ImagePanel(new ImageIcon("bank.gif").getImage());
  JPanel comboPanel = new JPanel();
  JPanel keyPanel = new JPanel();
  JPanel optionsPanel = new JPanel();
  JPanel buttonsPanel = new JPanel();
  JPanel resultsPanel = new JPanel();

  JTextField[] comboTextField = new JTextField[4];
  JButton[] keyButton = new JButton[9];
  ButtonGroup digitsButtonGroup = new ButtonGroup();
  JRadioButton twoDigitsRadioButton = new JRadioButton();
  JRadioButton threeDigitsRadioButton = new JRadioButton();
  JRadioButton fourDigitsRadioButton = new JRadioButton();
  JButton startStopButton = new JButton();
  JButton exitButton = new JButton();
  JScrollPane resultsPane = new JScrollPane();
  JTextArea resultsTextArea = new JTextArea();

  int numberDigits;
  String secretCombo;
  Random myRandom = new Random();
  int digitsEntered;
  String enteredCombo;

  AudioClip wrongSound;
  AudioClip correctSound;

  public static void main(String args[])
  {
    // create frame
    new Safecracker().show();
  }

  public Safecracker()
  {
    // frame constructor
    setTitle("Safecracker");
    setResizable(false);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent evt)
      {
        exitForm(evt);
      }
    });
    getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints gridConstraints;

    safePanel.setPreferredSize(new Dimension(330, 420));
    safePanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.gridheight = 3;
    getContentPane().add(safePanel, gridConstraints);

    comboPanel.setPreferredSize(new Dimension(160, 110));
    safePanel.setLayout(new GridBagLayout());
    comboPanel.setBackground(Color.YELLOW);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.insets = new Insets(110, 0, 0, 0);
    safePanel.add(comboPanel, gridConstraints);

    for (int i = 0; i < 4; i++)
    {
      comboTextField[i] = new JTextField();
      comboTextField[i].setPreferredSize(new Dimension(32, 48));
      comboTextField[i].setEditable(false);
      comboTextField[i].setBackground(Color.WHITE);
      comboTextField[i].setText("0");
      comboTextField[i].setHorizontalAlignment(SwingConstants.CENTER);
      comboTextField[i].setFont(new Font("Arial", Font.BOLD, 18));
      gridConstraints = new GridBagConstraints();
      gridConstraints.gridx = i;
      gridConstraints.gridy = 0;
      comboPanel.add(comboTextField[i], gridConstraints);
    }

    keyPanel.setPreferredSize(new Dimension(160, 100));
    keyPanel.setLayout(new GridBagLayout());
    keyPanel.setBackground(Color.YELLOW);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    safePanel.add(keyPanel, gridConstraints);

    for (int i = 0; i < 9; i++)
    {
      keyButton[i] = new JButton();
      keyButton[i].setText(String.valueOf(i + 1));
      gridConstraints = new GridBagConstraints();
      gridConstraints.gridx = i % 3;
      gridConstraints.gridy = i / 3;
      keyPanel.add(keyButton[i], gridConstraints);
      keyButton[i].addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          keyButtonActionPerformed(e);
        }
      });
    }
    
    

    optionsPanel.setPreferredSize(new Dimension(200, 100));
    optionsPanel.setBorder(BorderFactory.createTitledBorder("Options:"));
    optionsPanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 0;
    getContentPane().add(optionsPanel, gridConstraints);

    twoDigitsRadioButton.setText("Two Digits in Combination");
    twoDigitsRadioButton.setSelected(true);
    digitsButtonGroup.add(twoDigitsRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.anchor = GridBagConstraints.WEST;
    optionsPanel.add(twoDigitsRadioButton, gridConstraints);

    threeDigitsRadioButton.setText("Three Digits in Combination");
    digitsButtonGroup.add(threeDigitsRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.anchor = GridBagConstraints.WEST;
    optionsPanel.add(threeDigitsRadioButton, gridConstraints);

    fourDigitsRadioButton.setText("Four Digits in Combination");
    digitsButtonGroup.add(fourDigitsRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 2;
    gridConstraints.anchor = GridBagConstraints.WEST;
    optionsPanel.add(fourDigitsRadioButton, gridConstraints);

    buttonsPanel.setPreferredSize(new Dimension(200, 70));
    buttonsPanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 1;
    getContentPane().add(buttonsPanel, gridConstraints);

    startStopButton.setText("Start Game");
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    buttonsPanel.add(startStopButton, gridConstraints);
    startStopButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        startStopButtonActionPerformed(e);
      }
    });

    exitButton.setText("Exit");
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.insets = new Insets(10, 0, 0, 0);
    buttonsPanel.add(exitButton, gridConstraints);
    exitButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        exitButtonActionPerformed(e);
      }
    });

    resultsPanel.setPreferredSize(new Dimension(200, 250));
    resultsPanel.setBorder(BorderFactory.createTitledBorder("Results:"));
    resultsPanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 2;
    getContentPane().add(resultsPanel, gridConstraints);

    resultsTextArea.setEditable(false);
    resultsTextArea.setBackground(Color.WHITE);
    resultsPane.setPreferredSize(new Dimension(180, 220));
    resultsPane.setViewportView(resultsTextArea);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    resultsPanel.add(resultsPane, gridConstraints);

    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());

    setKeyButtons(false);
    try
    {
      wrongSound = Applet.newAudioClip(new URL("file:" + "uhoh.wav"));
      correctSound = Applet.newAudioClip(new URL("file:" + "tada.wav"));
    } catch (Exception ex)
    {
      System.out.println("Error loading sound files");
    }
  }

  private void keyButtonActionPerformed(ActionEvent e)
  {
    String n;
    int numberRight, positionRight;
    // determine which button was clicked
    // ActionCommand is text on button
    n = e.getActionCommand();
    // disable button since digits can't repeat
    keyButton[Integer.valueOf(n).intValue() - 1].setEnabled(false);
    // if first button in combo, clear out label boxes
    if (digitsEntered == 0)
    {
      comboTextField[0].setText("");
      comboTextField[1].setText("");
      comboTextField[2].setText("");
      comboTextField[3].setText("");
    }
    // add button to code
    enteredCombo += n;
    digitsEntered++;
    comboTextField[digitsEntered - 1].setText(n);
    // if all digits entered, check combo
    if (digitsEntered == numberDigits)
    {
      // reset combo buttons
      setKeyButtons(true);
      // check combination
      resultsTextArea.append("Entered: " + enteredCombo + "\n");
      if (enteredCombo.equals(secretCombo))
      {
    	count++;
    	correctSound.play();
    	JOptionPane.showMessageDialog(null, "Good job! You got it correct in " + count + " tries!");
        startStopButton.doClick();
        count = 0;
        
      } else
      {
    	count++;
        wrongSound.play();
        numberRight = 0;
        for (int i = 0; i < numberDigits; i++)
        {
          n = String.valueOf(enteredCombo.charAt(i));
          for (int j = 0; j < numberDigits; j++)
          {
            if (n.equals(String.valueOf(secretCombo.charAt(j))))
            {
              numberRight++;
            }
          }
        }
        // how many in correct position
        positionRight = 0;
        for (int i = 0; i < numberDigits; i++)
        {
          if (secretCombo.charAt(i) == enteredCombo.charAt(i))
          {
            positionRight++;
          }
        }
        resultsTextArea.append(String.valueOf(numberRight) + " digits correct" + "\n");
        resultsTextArea.append(String.valueOf(positionRight) + " in correct position" + "\n");
        resultsTextArea.append("Try again ..." + "\n\n");
        // clear combo to try again
        enteredCombo = "";
        digitsEntered = 0;
      }
    }
  }

  private void startStopButtonActionPerformed(ActionEvent e)
  {
    if (startStopButton.getText().equals("Start Game"))
    {
      startStopButton.setText("Stop Game");
      twoDigitsRadioButton.setEnabled(false);
      threeDigitsRadioButton.setEnabled(false);
      fourDigitsRadioButton.setEnabled(false);
      exitButton.setEnabled(false);
      setKeyButtons(true);
      resultsTextArea.setText("");
      // determine number of digits and set up labels
      if (twoDigitsRadioButton.isSelected())
      {
        numberDigits = 2;
      } else if (threeDigitsRadioButton.isSelected())
      {
        numberDigits = 3;
      } else
      {
        numberDigits = 4;
      }
      for (int i = 0; i < numberDigits; i++)
      {
        comboTextField[i].setVisible(true);
        comboTextField[i].setText("");
      }
      if (numberDigits != 4)
      {
        for (int i = numberDigits; i < 4; i++)
        {
          comboTextField[i].setVisible(false);
        }
      }
      // determine combination
      secretCombo = "";
      int j;
      boolean uniqueDigit;
      for (int i = 0; i < numberDigits; i++)
      {
        // select unique digit
        do
        {
          j = myRandom.nextInt(9) + 1;
          uniqueDigit = true;
          if (i != 0)
          {
            for (int k = 0; k < i; k++)
            {
              if (String.valueOf(secretCombo.charAt(k)).equals(String.valueOf(j)))
              {
                uniqueDigit = false;
              }
            }
          }
        } while (!uniqueDigit);
        secretCombo += String.valueOf(j);
      }
      enteredCombo = "";
      digitsEntered = 0;
    } else
    {
      if (enteredCombo.equals(secretCombo))
      {
        resultsTextArea.append("That's it!!" + "\n");
      } else
      {
        resultsTextArea.append("Game Stopped" + "\n");
      }
      resultsTextArea.append("Combination: " + secretCombo);
      startStopButton.setText("Start Game");
      twoDigitsRadioButton.setEnabled(true);
      threeDigitsRadioButton.setEnabled(true);
      fourDigitsRadioButton.setEnabled(true);
      exitButton.setEnabled(true);
      setKeyButtons(false);
    }
  }

  private void exitButtonActionPerformed(ActionEvent e)
  {
    System.exit(0);
  }

  private void setKeyButtons(boolean a)
  {
    for (int i = 0; i < 9; i++)
    {
      keyButton[i].setEnabled(a);
    }
  }

  private void exitForm(WindowEvent evt)
  {
    System.exit(0);
  }
}

class ImagePanel extends JPanel
{

  private Image img;

  public ImagePanel(Image img)
  {
    this.img = img;
  }

  public void paintComponent(Graphics g)
  {
    g.drawImage(img, 0, 0, null);
  }
}
