/** Name: Kyle Marr, Malaron Jeyakumar, Ronald Kwok
 * Date: December 11, 2023
 * Description:	A program that simulates an airport queue system.
 */

// Imports
import javax.swing.JFrame;

public class AirportSimulationTest {

	public static void main(String[] args) {
		// Instantiates the AirportSimulation object
		AirportSimulation airport = new AirportSimulation();

		airport.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		airport.setSize( 430, 540 ); // set frame size
		airport.setLocationRelativeTo(null); // makes the frame appear in the center of the screen
		airport.setVisible( true ); // display frame


	}  

}