import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;
import java.awt.Color;
import java.util.Random;

// ------------------------------------------------------------------
// BlackPanther
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// Probably the first bot you will learn about.
// Moves in a seesaw motion, and spins the gun around at each end.
// ------------------------------------------------------------------
public class BlackPanther extends Bot {

    private final Random random = new Random();

    // The main method starts our bot
    public static void main(String[] args) {
        new BlackPanther().start();
    }

    // Constructor, which loads the bot config file
    BlackPanther() {
        super(BotInfo.fromFile("BlackPanther.json"));
        // Set the colors for the bot
        setBodyColor(Color.ORANGE);
        setGunColor(Color.ORANGE);
        setRadarColor(Color.RED);
        setScanColor(Color.RED);
        setBulletColor(Color.RED);
    
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Repeat while the bot is running
        while (isRunning()) {
            //forward(200);
            turnGunRight(360);
            moveRandomly();

        }
    }

    private void moveRandomly() {
        int moveDistance = random.nextInt(200) + 100;
        int turnAngle = random.nextInt(90) - 45;
        if (random.nextBoolean()) {
            forward(moveDistance);
        } else {
            back(moveDistance);
        }
        turnRight(turnAngle);
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        fire(1);
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
       turnLeft(90 - bearing);
       moveRandomly();
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        // Calculate the bearing to the direction of the bullet
        back(50);

        // Turn 90 degrees to the bullet direction based on the bearing
       turnRight(90);
       moveRandomly();
    }
}
