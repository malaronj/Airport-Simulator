/** Name: Kyle Marr, Malaron Jeyakumar, Ronald Kwok
 * Date: December 11, 2023
 * Description:	A program that turns multiples picture of a arriving plane into an animation.
 */

// Imports
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TakeoffAnimation extends JPanel {
	private final static String IMAGE_NAME = "takeoff-"; // base image name
	protected ImageIcon images[]; // array of images
	private final int TOTAL_IMAGES = 48;  // number of images
	private int currentImage = 0; // current image index
	private final int ANIMATION_DELAY = 60; // millisecond delay
	private int width; // image width
	private int height; // image height
	private boolean active = false; 

	private Timer animationTimer; // Timer drives animation

	// constructor initializes Animation by loading images
	public TakeoffAnimation() {
		images = new ImageIcon[ TOTAL_IMAGES ];

		// load 48 images
		for ( int count = 0; count < images.length; count++ )
			images[ count ] = new ImageIcon( "takeoff/" + IMAGE_NAME + (count+1) + ".png" );

		// this example assumes all images have the same width and height
		width = images[ 0 ].getIconWidth();   // get icon width
		height = images[ 0 ].getIconHeight(); // get icon height

	} // end Animation constructor

	// display current image 
	public void paintComponent( Graphics g ) {
		super.paintComponent( g ); // call superclass paintComponent

		images[ currentImage ].paintIcon( this, g, 0, 0);

		// set next image to be drawn only if timer is running
		if ( animationTimer.isRunning() )  
			currentImage = ( currentImage + 1 ) % TOTAL_IMAGES;
	} // end method paintComponent

	// get the a boolean that checks if the animation is active
	public boolean getActive() {
		return active;
	} // end method getActive

	// set the active boolean to true or false
	public void setActive(boolean active) {
		this.active = active;
	} // end method setActive

	// start animation, or restart if window is redisplayed
	public void startAnimation() {
		setActive(true);
		if ( animationTimer == null )  {
			currentImage = 0; // display first image

			// create timer
			animationTimer =  new Timer( ANIMATION_DELAY, new TimerHandler() );

			animationTimer.start(); // start timer
		} // end if
		else { // animationTimer already exists, restart animation
			if ( ! animationTimer.isRunning() )
				animationTimer.restart();
		} // end else
	} // end method startAnimation

	// stop animation timer
	public void stopAnimation() {
		setActive(true);
		animationTimer.stop();
	} // end method stopAnimation

	// return preferred size of animation, called by window.pack()
	public Dimension getPreferredSize() {
		return new Dimension( width+2, height +2);
	} // end method getPreferredSize

	// inner class to handle action events from Timer
	private class TimerHandler implements ActionListener {
		// respond to Timer's event
		public void actionPerformed( ActionEvent actionEvent ) {
			repaint(); // repaint animator
		} // end method actionPerformed
	} // end class TimerHandler
} // end class Animation