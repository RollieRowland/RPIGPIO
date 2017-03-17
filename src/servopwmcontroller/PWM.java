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
public class PWM {
    private int dutyCycle;
    private final int period;
    private final Pin pin;
    
    PWM(Pin pin, int period){
        this.period = period;
        this.pin = pin;//defaults to output
        this.dutyCycle = 0;
    }
    
    public void runPWM(){
        pin.setHigh();

        delay(dutyCycle);

        pin.setLow();

        delay(period - dutyCycle);
    }
    
    public void setPWM(int dutyCycle){
        this.dutyCycle = dutyCycle;
    }
    
    private void delay(int microseconds){
        long startTime = System.nanoTime();
        
        while((System.nanoTime() - startTime) > microseconds * 1000){}
    }
}
