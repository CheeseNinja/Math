package matrix2x2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MatrixPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MatrixPanel() {
		components();

		setTitle("Matrix Transformation");
		setSize(600, 450);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void components() {
		initial11 = 1;
		initial12 = 0;
		initial21 = 0;
		initial22 = 1;
		main = new JPanel(new BorderLayout());
		center = new JPanel();
		north = new JPanel();
		east = new JPanel();
		west = new JPanel();

		list = new JTextArea(20, 20);
		list.setEditable(false);
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JButton calculate = new JButton("Calculate");
		calculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calculatingMatrix(masterMatrix);
				list.append(masterMatrix.get(0).toString() + "============\n");
			}

		});
		center.add(scroll);
		center.add(calculate);

		north.setPreferredSize(new Dimension(800, 30));
		north.setBackground(Color.BLACK);
		west.setPreferredSize(new Dimension(250, 500));
		west.setBackground(new Color(58, 89, 152));

		// north
		JLabel title = new JLabel("<html><font color = 'white'>2x2 Matrix</font></html>");
		title.setFont(new Font("Verdana", Font.BOLD, 14));
		north.add(title);

		// west
		JLabel subTitle = new JLabel("<html><font color = 'white'>Matrix User Input System</font></html>");
		subTitle.setFont(new Font("Arial", Font.BOLD, 12));

		JLabel initial1x2 = new JLabel("Enter inital 1x2 Vector: ");
		initial1x2.setForeground(new Color(77, 210, 255));
		initialVector = new JTextField(20);
		initialVector.setText("Ex: <1,0> , <0,1>");
		initialVector.setForeground(new Color(149, 149, 187));
		initialVector.setToolTipText("Ex: <1,0> , <0,1>");
		initialVector.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER
						&& initialVector.getText().replaceAll(" ", "").length() > 0) {
					try {
						String s = initialVector.getText();
						initial11 = Double.parseDouble(s.substring(s.indexOf("<") + 1, s.indexOf(",")));
						s = s.substring(s.indexOf(",") + 1);
						initial21 = Double.parseDouble(s.substring(0, s.indexOf(">")));
						s = s.substring(s.indexOf("<") + 1);
						initial12 = Double.parseDouble(s.substring(0, s.indexOf(",")));
						initial22 = Double.parseDouble(s.substring(s.indexOf(",") + 1, s.indexOf(">")));
					} catch (Exception e) {
					}
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}

		});

		west.add(subTitle);
		west.add(initial1x2);
		west.add(initialVector);
		west.add(linearTransformation());

		add(main);
		main.add(center, BorderLayout.CENTER);
		main.add(north, BorderLayout.NORTH);
		main.add(east, BorderLayout.EAST);
		main.add(west, BorderLayout.WEST);

	}

	private JPanel linearTransformation() {
		inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(225, 330));
		// Radio button
		translate = new JRadioButton("Translate");
		reflection = new JRadioButton("Reflection");
		rotation = new JRadioButton("Rotation");
		ButtonGroup linearButtons = new ButtonGroup();
		linearButtons.add(translate);
		linearButtons.add(reflection);
		linearButtons.add(rotation);
		JTextField condition = new JTextField(18);
		condition.setEditable(false);
		condition.setText("Enter values for transformation");

		inputPanel.add(translate);
		inputPanel.add(reflection);
		inputPanel.add(rotation);
		inputPanel.add(condition);
		// translate
		JLabel labelX = new JLabel("X: ");
		inputX = new JTextField(5);
		JLabel labelY = new JLabel("Y: ");
		inputY = new JTextField(7);
		inputPanel.add(labelX);
		inputPanel.add(inputX);
		inputPanel.add(labelY);
		inputPanel.add(inputY);
		// reflection
		JLabel equation = new JLabel("Linear Equation: ");
		JLabel yText = new JLabel("y=");
		inpEquation = new JTextField(3);
		JLabel xText = new JLabel("x");
		inputPanel.add(equation);
		inputPanel.add(yText);
		inputPanel.add(inpEquation);
		inputPanel.add(xText);
		// rotation
		JLabel degree = new JLabel("Degrees: ");
		inpDegree = new JTextField(5);
		inputPanel.add(degree);
		inputPanel.add(inpDegree);
		JButton add = new JButton("Add");
		inputPanel.add(add);
		// Action listener
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (translate.isSelected() && inputX.getText().length() > 0 && inputY.getText().length() > 0) {
					try {
						scaleX = Double.parseDouble(inputX.getText());
						scaleY = Double.parseDouble(inputY.getText());
						masterMatrix.add(new Matrix("Translate", scaleX, 0, scaleY, 0));
					}

					catch (Exception e) {
					}
				}
				// have some simple rad(), sqrt(), power(), etc.
				else if (reflection.isSelected() && inpEquation.getText().length() > 0) {
					try {
						degreeRotation = Math.atan(Double.parseDouble(inpEquation.getText()));
						reflectAngleX = (degreeRotation % (Math.PI)) * 2;
						reflectAngleY = reflectAngleX - (Math.PI / 2);
						Matrix m = new Matrix("Reflection", Math.cos(reflectAngleX), Math.cos(reflectAngleY),
								Math.sin(reflectAngleX), Math.sin(reflectAngleY));
						masterMatrix.add(m);
						setVisible(true);
					} catch (Exception e) {
					}
				} else if (rotation.isSelected() && inpDegree.getText().length() > 0) {
					try {
						rotationDegrees1 = Math.toRadians((Double.parseDouble(inpDegree.getText())) % 360);
						rotationDegrees2 = rotationDegrees1 + (Math.PI / 2);
						masterMatrix.add(new Matrix("Rotation", Math.cos(rotationDegrees1), Math.cos(rotationDegrees2),
								Math.sin(rotationDegrees1), Math.sin(rotationDegrees2)));
					} catch (Exception e) {
					}
				}
			}

		});
		JPanel userDefined = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.black);
				g.fillRect(10, 11, 4, 91);
				g.fillRect(10, 11, 12, 4);
				g.fillRect(10, 102, 12, 4);
				g.fillRect(190, 11, 4, 91);
				g.fillRect(182, 11, 12, 4);
				g.fillRect(182, 102, 12, 4);
			}
		};
		userDefined.setPreferredSize(new Dimension(205, 125));
		userDefined.setBackground(new Color(124, 149, 248));
		inputPanel.add(userDefined);

		usera11 = new JTextField(5);
		usera12 = new JTextField(5);
		usera21 = new JTextField(5);
		usera22 = new JTextField(5);

		userDefined.add(filler(205, 15));
		userDefined.add(usera11);
		userDefined.add(filler(25, 1));
		userDefined.add(usera12);
		userDefined.add(filler(205, 13));
		userDefined.add(usera21);
		userDefined.add(filler(25, 1));
		userDefined.add(usera22);
		JButton add2 = new JButton("Add");
		add2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				masterMatrix.add(new Matrix("User",Double.parseDouble(usera11.getText()),Double.parseDouble(usera12.getText()),
						Double.parseDouble(usera21.getText()),Double.parseDouble(usera22.getText())));
			}
			
		});
		userDefined.add(add2);
		return inputPanel;
	}

	@SuppressWarnings("rawtypes")
	private ArrayList calculatingMatrix(ArrayList<Matrix> list) {
		finalMatrix = new Matrix("", initial11, initial12, initial21, initial22);
		for (int i = 0; i < list.size(); i++) {
			String translation = list.get(i).getTranslation();
			switch (translation) {
			case "Translate":
				finalMatrix.seta11(finalMatrix.geta11() + list.get(i).geta11());
				finalMatrix.seta12(finalMatrix.geta12() + list.get(i).geta11());
				finalMatrix.seta21(finalMatrix.geta21() + list.get(i).geta21());
				finalMatrix.seta22(finalMatrix.geta22() + list.get(i).geta21());
				break;
			case "Reflection":
				doMatrix(finalMatrix, list.get(i));
				break;
			case "Rotation":
				doMatrix(finalMatrix, list.get(i));
				break;
			case "User":
				doMatrix(finalMatrix, list.get(i));
			}
		}
		list.clear();
		list.add(finalMatrix);
		return list;
	}

	private Matrix doMatrix(Matrix master, Matrix m) {
		double a11 = master.geta11(), a12 = master.geta12(), a21 = master.geta21(), a22 = master.geta22();
		master.seta11((a11 * m.geta11()) + (a21 * m.geta12()));
		master.seta12((a12 * m.geta11()) + (a22 * m.geta12()));
		master.seta21((a11 * m.geta21()) + (a21 * m.geta22()));
		master.seta22((a12 * m.geta21()) + (a22 * m.geta22()));
		return master;
	}

	private JPanel filler(int x, int y) {
		JPanel filler = new JPanel();
		filler.setPreferredSize(new Dimension(x, y));
		filler.setOpaque(false);
		return filler;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MatrixPanel();
			}
		});
	}

	private JPanel main, center, north, east, west, inputPanel;
	private JTextField initialVector, inputX, inputY, inpEquation, inpDegree, usera11, usera12, usera21, usera22;
	private JRadioButton translate, reflection, rotation;
	private double scaleX, scaleY, degreeRotation, reflectAngleX, reflectAngleY, rotationDegrees1, rotationDegrees2,
			initial11, initial12, initial21, initial22;
	private ArrayList<Matrix> masterMatrix = new ArrayList<Matrix>();
	public MatrixPanel m;
	private Matrix finalMatrix;
	private JTextArea list;

}
