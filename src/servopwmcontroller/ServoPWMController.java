/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servopwmcontroller;

/**
 *
 * @author Rollie
 */
public class ServoPWMController {
    private static PWM pwm;
    private static Pins pins;
    private static Thread pwmThread;
    private static volatile Boolean EXIT = false;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        pins = new Pins(false);//initialize pins and set i2c mode to false
        pwm = new PWM(pins.pins[0], 2000);//set pin and set period in microseconds
        
        //creates new thread to run PWM
        runPWM();
        
        //Move servo to 90 degrees
        pwm.setPWM(1000);
        
        Thread.sleep(2000);
        
        //Move servo to 0 degrees
        pwm.setPWM(250);
        
        Thread.sleep(2000);
        
        //Move servo to 180 degrees
        pwm.setPWM(1750);
        
        Thread.sleep(2000);
        
        EXIT = true;//Stop PWM while loop
        pwmThread.join();//Join PWM thread
        System.exit(0);//Exit Program
    }
    
    /**
     * Creates a new thread to run the PWM
     */
    public static void runPWM(){
        pwmThread = new Thread() {
            @Override
            public void run() {
                while(!EXIT){
                    pwm.runPWM();//Runs the PWM loop
                }
            }
        };
        
        pwmThread.start();//Starts the PWM thread
    }
}
