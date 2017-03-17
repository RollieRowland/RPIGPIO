
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servopwmcontroller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rollie
 */
public final class Pins {
    public final Pin[] pins = new Pin[26];
    private FileWriter ue;//unexport
    private FileWriter e;//export
    private final boolean i2c;
    private final String[] pinNames = 
        {"14", "15", "18", "23", "24", "25", "8", "7",
         "12", "16", "20", "21", "2", "3", "4", "17",
         "27", "22", "10", "9", "11", "5", "6", "13",
         "19", "26"};
    
    
    /**
     * 
     * @param i2c Sets whether the i2c pins can be used for GPIO manipulation;
     */
    Pins(boolean i2c){
        this.i2c = i2c;
        
        try{
            ue = new FileWriter("/sys/class/gpio/unexport");
            e = new FileWriter("/sys/class/gpio/export");
            
            initializePins();
        }
        catch(IOException ioEx){}
        
    }
    
    private void initializePins() throws IOException{
        int iterator = 0;
        
        if(i2c){
            for(String pin : pinNames){
                switch(pin){
                    case "2":
                        pins[iterator] = null;
                        break;
                    case "3":
                        pins[iterator] = null;
                        break;
                    default:
                        File check = new File("/sys/class/gpio/gpio" + pin);

                        if(check.exists()){
                            ue.write(pin);
                            ue.flush();
                        }
                        
                        e.write(pin);
                        e.flush();
                        
                        pins[iterator] = new Pin(pinNames[iterator], Pin.OUT);
                        break;
                }
                
                iterator++;
            }
            
        }
        else{
            for(String pin : pinNames){
                File check = new File("/sys/class/gpio/gpio" + pin);
                
                if(check.exists()){
                    ue.write(pin);
                    ue.flush();
                }
                
                e.write(pin);
                e.flush();
                
                pins[iterator] = new Pin(pinNames[iterator], Pin.OUT);
                
                iterator++;
            }
        }
    }
}
