package browse.by.voice.example;

/**
 * Created by psantwani on 11/13/2015.
 */
import java.util.TimerTask;
import java.util.Date;

public class ScheduledTask extends TimerTask {

    Date now; // to display current time

    // Add your task here
    public void run() {
        now = new Date(); // initialize date
            if((FragmentInstruction.mTutorialHandler.mToolTip.mDescription.equals("Clicking on the left section of the bar will open menu."))){
                FragmentInstruction.mTutorialHandler.cleanUp();
                System.out.println("Application Terminates");
            }
        };
}