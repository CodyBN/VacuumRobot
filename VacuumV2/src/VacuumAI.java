import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Window;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import java.awt.Panel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class VacuumAI {
	/**
	 * Cody Noack and Zachary Upshaw
	 */
	final int maxTurns = 1000;
	private int turn = 0;
	public Timer timer = new Timer();
	private int score = 0;
	private JLabel scores;
	private boolean cleaned;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VacuumAI window = new VacuumAI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public VacuumAI() {
	
		initialize();
	
		
		
	}


	/**
	 * Initialize the contents of the frame.
	 * Cody Noack
	 */
	private void initialize() {
		frame = new JFrame("Vacuum");
		frame.setBounds(100, 100, 667, 636);
		frame.getContentPane();
		frame.getContentPane().setLayout(null);
		Environment ePanel = new Environment();
		ePanel.setBounds(190, 120, 400, 400);
		ePanel.setBackground(Color.LIGHT_GRAY);
		ePanel.agent.populate();

		ePanel.setVisible(false);
		
		frame.getContentPane().add(ePanel);

		
		Panel panel = new Panel();
		panel.setBounds(0, 233, 150, 110);
		frame.getContentPane().add(panel);
		
		this.scores = new JLabel(Integer.toString(score));
		
		scores.setVerticalAlignment(SwingConstants.TOP);
		scores.setFont(new Font("Tahoma", Font.BOLD, 32));
		scores.setBounds(12, 10, 573, 110);
		scores.paintImmediately(scores.getVisibleRect());
		scores.setVisible(false);
		frame.getContentPane().add(scores);
		
		java.awt.Button button = new java.awt.Button("Generate Map");
		button.setBounds(29, 10, 96, 24);
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				ePanel.setVisible(true);
				scores.setVisible(true);
			}
		});
		panel.setLayout(null);
		panel.add(button);
		
		java.awt.Button button_1 = new java.awt.Button("Run Agent");
		button_1.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				runAI(ePanel);
			}
		});
		button_1.setBounds(29, 66, 96, 24);
		panel.add(button_1);

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	
	/**
	 * runs AI and tells gui how to handle 
	 * Agent's decisions
	 * @param e
	 */
	private void runAI(Environment e)
	{
		
		//creates a timer so that we can see the animation of the agent.
		TimerTask tt = new TimerTask()
		{
			
			public void run()
			{
				/**
				 * Zachary Upshaw
				 */
				scores.setText(Integer.toString(score));
				if(turn >= maxTurns )
				{
					timer.cancel();
					cleaned = e.buildingCleaned();//checks if building is cleaned or not.
					scores.setFont(new Font("Tahoma", Font.PLAIN, 16));
					scores.setText("Max amount of steps reached.");
					System.out.println("Max amount of steps");
					if(e.agent.isLobby() ==true){
						System.out.println("stopped in lobby");
						System.out.println("Performance meassure:" + score);
						scores.setText("<html><br>"+scores.getText() + "\n" + 
								"Stopped inside the lobby" 
								+ "<br>" + "Performance meassure: " + score + "</html>");
						if(cleaned) {
							scores.setText("<html>"+scores.getText() + "<br>" + 
									"Stopped oustide of the lobby (-1000 points)" 
									+ "<br>" + "Performance meassure: " + score + "<br> building is cleaned" +"<br> Number of steps: "+ turn +  "</html>");
						}

						else {
							scores.setText("<html>"+scores.getText() + "<br>" + 
									"Stopped oustide of the lobby (-1000 points)" 
									+ "<br>" + "Performance meassure: " + score + "<br> building is not cleaned" +"<br> Number of steps: "+ turn + "</html>");
						}
					}
					else{
						
						System.out.println("stopped outside of lobby (-1000 points)");					
						score = score - 1000;
						System.out.println("Performance meassure:" + score);

						
						if(cleaned) {
							scores.setText("<html>"+scores.getText() + "<br>" + 
									"Stopped oustide of the lobby (-1000 points)" 
									+ "<br>" + "Performance meassure: " + score + "<br> building is cleaned" +"<br> Number of steps: "+ turn +  "</html>");
						}

						else {
							scores.setText("<html>"+scores.getText() + "<br>" + 
									"Stopped oustide of the lobby (-1000 points)" 
									+ "<br>" + "Performance meassure: " + score + "<br> building is not cleaned" +"<br> Number of steps: "+ turn + "</html>");
						}
							
					}
						
				}
			
				/**
				 * Cody Noack
				 */
				//Decides where the vacuum will move next
				if(e.agent.determineAction() == 4){
					System.out.println("sucks dirt (+100 points)");
					score = score +100;
					turn++;
				}
				else if(e.agent.determineAction()== 0)
				{
					
				   e.moveRight();
				   turn++;
				   System.out.println("Moved right (-10 points)");
				   score = score -10;
				}
				else if(e.agent.determineAction()== 1)
				{
					e.moveLeft();
					turn++;
					System.out.println("Moved left (-10 points)");
					score = score -10;
				}
				else if(e.agent.determineAction()== 2)
				{
					e.moveUp();
					turn++;
					System.out.println("Moved up (-10 points)");
					
					score = score -10;
				}
				else if(e.agent.determineAction()== 3)
				{
					e.moveDown();
					turn++;
					System.out.println("Moved down (-10 points)");
					score = score -10;
				}
			}
			
		};
		timer.schedule(tt, 1000,100);
	}
}
