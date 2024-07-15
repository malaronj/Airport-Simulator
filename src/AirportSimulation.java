/** 
 * A program that simulates an airport queue system.
 * @author Kyle Marr, Malaron Jeyakumar, Ronald Kwok
 * @version December 11, 2023
 */

// Imports
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Timer;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AirportSimulation extends JFrame {

	// create private variables
	private File landingsFile = new File("arrivals.txt");;
	private File takeoffsFile = new File("takeoffs.txt");;
	private Font listFont;

	private JTextArea listOne;
	private JTextArea listTwo;

	private JButton startButton;
	private JButton exitButton;

	// Creates two integer linked lists
	private Queue<Integer> landing = new LinkedList<Integer>();
	private Queue<Integer> takeoff = new LinkedList<Integer>();

	private Scanner fileOne;
	private Scanner fileTwo;

	private Timer timer;
	private int timerDuration;
	private int fileNumbersOne;
	private int fileNumbersTwo;

	private JScrollPane scrollPaneOne;
	private JScrollPane scrollPaneTwo;

	private boolean listOneUpdate;
	private boolean listTwoUpdate;

	private JLabel title;
	private JLabel start;
	private JLabel incoming;
	private JLabel outgoing;
	private JLabel arrivingFlight;
	private JLabel takeoffFlight;

	private JTextField arrivingNumber;
	private JTextField takeoffNumber;

	private ArrivingAnimation animation1 = new ArrivingAnimation(); 
	private TakeoffAnimation animation2 = new TakeoffAnimation(); 

	private Container container;

	/** 
	 * zero-argument constructor
	 */
	public AirportSimulation(){

		ActionEventListener handler = new ActionEventListener();
		timerDuration = 600;
		timer = new Timer(timerDuration, new ActionEventListener());
		GuiCreator(handler);

	}

	/**
	 * creates the GUI for the Simulation
	 * @param handler The action event listener
	 */
	private void GuiCreator(ActionEventListener handler){

		// Sets the layout of the entire frame
		setLayout(null);

		// Creates the container
		container = getContentPane();
		container.setBackground(Color.LIGHT_GRAY);

		// Creates the font for the lists
		listFont = new Font("Arial", Font.PLAIN, 20);


		// Checks if the file exists before making a scanner object using the file
		try {
			if (landingsFile.exists()) {
				fileOne = new Scanner(landingsFile);
			} else {
				System.out.println("File " + landingsFile.getName() + " does not exist.");
			}

			if (takeoffsFile.exists()) {
				fileTwo = new Scanner(takeoffsFile);
			} else {
				System.out.println("File " + takeoffsFile.getName() + " does not exist.");
			}
		} catch (IOException ioException) {
			System.err.println("Java Exception: " + ioException);
			System.out.println("Sorry, unable to open the files for reading.");
		}


		// Runs until all the flights contained within the files are in the landing and takeoff queues
		while (fileOne.hasNext()) {
			fileNumbersOne = fileOne.nextInt();
			landing.add(fileNumbersOne);
		}
		while (fileTwo.hasNext()) {
			fileNumbersTwo = fileTwo.nextInt();
			takeoff.add(fileNumbersTwo);
		}

		listOne = new JTextArea("", 10, 3);
		listTwo = new JTextArea("", 10, 3);

		textAreaCreator(listOneUpdate = true, listTwoUpdate = true);

		fileOne.close();
		fileTwo.close();

		// Set preferences
		listOne.setFont(listFont);
		listTwo.setFont(listFont);
		listOne.setFocusable(false);
		listTwo.setFocusable(false);

		// Create the labels
		title = new JLabel("Airport Simulator (PAY)");
		title.setFont(new Font("Arial", Font.BOLD, 18));

		start = new JLabel("Press 'START' to begin Simulation.");
		start.setFont(new Font("Arial", Font.BOLD, 12));
		incoming = new JLabel("Arrivals");
		outgoing = new JLabel("Takeoffs");
		arrivingFlight = new JLabel("Arriving Flight");
		takeoffFlight = new JLabel("Takeoff Flight");

		// Creates the buttons
		startButton = new JButton("Start");
		exitButton = new JButton("Exit");
		startButton.setFocusable(false);
		exitButton.setFocusable(false);

		// Creates the text fields
		arrivingNumber = new JTextField(6);
		takeoffNumber = new JTextField(6);

		// Adds a action listener to the buttons and text fields
		startButton.addActionListener(handler);
		exitButton.addActionListener(handler);
		arrivingNumber.addActionListener(handler);
		takeoffNumber.addActionListener(handler);

		// Creates the scroll panes and also sets the horizontal scroll bar to always showing
		scrollPaneOne = new JScrollPane(listOne);
		scrollPaneTwo = new JScrollPane(listTwo);
		scrollPaneOne.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneTwo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// setting the dimenensions and coordinates of the objects
		title.setBounds(38, 20, 250, 20);
		start.setBounds(40, 40, 200, 20);

		incoming.setBounds(30, 60, 75, 20);
		outgoing.setBounds(190, 60, 75, 20);

		arrivingFlight.setBounds(20, 400, 100, 20);
		takeoffFlight.setBounds(175, 400, 150, 20);

		startButton.setBounds(104, 130, 70, 30);
		exitButton.setBounds(300, 450, 70, 30);

		arrivingNumber.setBounds(105, 400, 60, 20);
		takeoffNumber.setBounds(260, 400, 60, 20);

		scrollPaneOne.setBounds(20, 90, 75, 300);
		scrollPaneTwo.setBounds(180, 90, 75, 300);

		// adding objects to frame
		add(title);
		add(start);

		add(incoming);
		add(outgoing);

		add(arrivingFlight);
		add(takeoffFlight);

		add(startButton);
		add(exitButton);

		add(arrivingNumber);
		add(takeoffNumber);

		add(scrollPaneOne);
		add(scrollPaneTwo);
	}

	/**
	 * Creates the basic JTextArea lists for the GUI
	 * @param listOneUpdate Checks if list one is needed to be updated
	 * @param listTwoUpdate Checks if list two is needed to be updated
	 */
	private void textAreaCreator(boolean listOneUpdate, boolean listTwoUpdate){
		if (listOneUpdate == true){
			// Iterates through the queue and appends it to the list
			for (Object planeLandings: landing){
				listOne.append(String.valueOf(planeLandings) + " \n");
			}
		}

		if (listTwoUpdate == true){
			// Iterates through the queue and appends it to the list
			for (Object planeTakeoffs: takeoff){
				listTwo.append(String.valueOf(planeTakeoffs) + " \n");
			}
		}

		// Sets both update booleans to false
		listOneUpdate = false;
		listTwoUpdate = false;
	}
	/**
	 * Updates the queues for landing and takeoff
	 * @param queue The queue that is being updated
	 */
	private void updateQueues(Queue queue){
		// Removes the first item within the queue
		queue.poll();

		// Clears the entire list
		if (queue == landing){
			listOne.selectAll();
			listOne.replaceSelection("");
			// Replaces the entire list with the updated queue
			textAreaCreator(listOneUpdate = true, listTwoUpdate = false);
		}
		else if (queue == takeoff){
			listTwo.selectAll();
			listTwo.replaceSelection("");
			// Replaces the entire list with the updated queue
			textAreaCreator(listOneUpdate = false, listTwoUpdate = true);
		}
	}

	/**
	 * The ActionListener for the buttons
	 */
	private class ActionEventListener implements ActionListener {

		// The number of time cycles
		int timeCycles = 0;
		// The number of arrivals that have happened
		int minimumArrivals = 0;

		/** 
		 * Listens for any actions
		 * @param The action from the textboxes or the timers
		 */
		@Override
		public void actionPerformed( ActionEvent event ) {

			// Exits the system when pressed
			if (event.getSource() == exitButton) {
				System.exit(0);
			}

			// Starts the timer when pressed
			if (event.getSource() == startButton){
				timer.start();
			}

			// Checks for events coming from the timer
			if (event.getSource() == timer) {

				// Increases the number of time cycles by one
				timeCycles++;
				// countdown for takeoff/landing
				if (timeCycles == 1){
					// if plane is landing
					if (!listOne.getText().trim().equals("") && minimumArrivals <= 2) {
						start.setText("Flight " + landing.peek() + " is next to land in 3");
						if (animation2.getActive() == true) {
							animation2.stopAnimation();
						}
						animation1.setBounds(310, 210, 50, 50);
						add(animation1);
						animation1.startAnimation();
					}
					// if plane is taking off
					if (!listTwo.getText().trim().equals("") && minimumArrivals == 2) {
						start.setText("Flight " + takeoff.peek() + " is next to take off in 3");
						if (animation1.getActive() == true) {
							animation1.stopAnimation();
						}
						animation2.setBounds(310, 210, 50, 50);
						add(animation2);
						animation2.startAnimation();
					}
				}
				if (timeCycles == 2){
					// if plane is landing
					if (!listOne.getText().trim().equals("") && minimumArrivals <= 2)
						start.setText("Flight " + landing.peek() + " is next to land in 2");
					// if plane is taking off
					if (!listTwo.getText().trim().equals("") && minimumArrivals == 2)
						start.setText("Flight " + takeoff.peek() + " is next to take off in 2");
				}
				if (timeCycles == 3){
					// if plane is landing
					if (!listOne.getText().trim().equals("") && minimumArrivals <= 2)
						start.setText("Flight " + landing.peek() + " is next to land in 1");
					// if plane is taking off
					if (!listTwo.getText().trim().equals("") && minimumArrivals == 2)
						start.setText("Flight " + takeoff.peek() + " is next to take off in 1");
				}
				// Checks if list one is not empty, if 4 time cycles have passed, and if there has been less than 2 arrivals
				if (!listOne.getText().trim().equals("") && timeCycles == 4 && minimumArrivals < 2){

					start.setText("                  Landing Now.                  ");
					updateQueues(landing);
					minimumArrivals++;
					timeCycles = -1;
				}
				// if landing queue is empty
				if (listOne.getText().trim().equals("")) {
					// if takeoff queue is empty
					if (listTwo.getText().trim().equals("")) {
						animation1.stopAnimation();
						animation2.stopAnimation();
						timer.stop();
					}
					// if only landing queue is empty
					else
						minimumArrivals = 2;

				}
				// if only takeoff is empty
				if (listTwo.getText().trim().equals("")) {
					minimumArrivals = 0;
				}
				// plane takes off
				if (!listTwo.getText().trim().equals("") && timeCycles == 4 && minimumArrivals == 2){
					start.setText("                  Taking Off Now.                  ");
					updateQueues(takeoff);

					minimumArrivals = 0;
					timeCycles = -1;

				}

			}

			// user pressed Enter in LandingTextField
			if ( event.getSource() == arrivingNumber ){
				try {
					int newLanding = Integer.parseInt(arrivingNumber.getText());
					// if user number is from 1 to 9999
					if (newLanding < 10000 && newLanding > 0){
						landing.add(newLanding);

						// Clears the entire list
						listOne.selectAll();
						listOne.replaceSelection("");
						// Replaces the entire list with the updated queue
						textAreaCreator(listOneUpdate = true, listTwoUpdate = false);
						arrivingNumber.setText("");
					}
					// if user number is not from 1 to 9999
					else{
						JOptionPane.showMessageDialog( null, "Please enter a valid flight number!" );
					}
				}
				catch(NumberFormatException numberFormatException) {
					JOptionPane.showMessageDialog( null, "Please enter a valid flight number!" );
				}
			}
			// user pressed Enter in LandingTextField
			if ( event.getSource() == takeoffNumber ){
				try {
					int newTakeoff = Integer.parseInt(takeoffNumber.getText());
					// if user number is from 1 to 9999
					if (newTakeoff < 10000 && newTakeoff > 0){
						takeoff.add(newTakeoff);
						listTwo.selectAll();
						listTwo.replaceSelection("");
						textAreaCreator(listOneUpdate = false, listTwoUpdate = true);
						takeoffNumber.setText("");
					}
					// if user number is not from 1 to 9999
					else{
						JOptionPane.showMessageDialog( null, "Please enter a valid flight number!" );
					}
				}
				catch(NumberFormatException numberFormatException) {
					JOptionPane.showMessageDialog( null, "Please enter a valid flight number!" );
				}
			}
		}
	}
}