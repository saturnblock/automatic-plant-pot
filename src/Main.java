package one.microproject.rpi.hardware.gpio.sensors.impl;

import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import one.microproject.rpi.hardware.gpio.sensors.ADS1115;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ADS1115Impl implements ADS1115 {

    private static final Logger LOG = LoggerFactory.getLogger(ADS1115Impl.class);

    public static final int ADDRESS = 0x48;
    private static final int CONVERSION_REGISTER = 0x00;
    private static final int CONFIG_REGISTER     = 0x01;
    private static final int LO_THRESH_REGISTER  = 0x02;
    private static final int HI_THRESH_REGISTER  = 0x03;

    private static final int CONFIG_REGISTER_TEMPLATE = 0b1000000110000011;

    public enum GAIN {
        GAIN_6_144V(0b0000000000000000, 187.5/1_000_000),
        GAIN_4_096V(0b0000001000000000, 125.0/1_000_000),
        GAIN_2_048V(0b0000010000000000, 62.5/1_000_000),
        GAIN_1_024V(0b0000011000000000, 31.25/1_000_000),
        GAIN_0_512V(0b0000100000000000, 15.625/1_000_000),
        GAIN_0_256V(0b0000101000000000, 7.8125/1_000_000);
        private final int gain;
        private final double gainPerByte;
        GAIN(int gain, double gainPerByte) {
            this.gain = gain;
            this.gainPerByte = gainPerByte;
        }
        public int gain() {
            return gain;
        }
        public double gainPerByte() {
            return gainPerByte;
        }
    }

    private static final int A0_IN = 0b0100000000000000;
    private static final int A1_IN = 0b0101000000000000;
    private static final int A2_IN = 0b0110000000000000;
    private static final int A3_IN = 0b0111000000000000;

    private final int address;
    private final String deviceId;
    private final Context context;
    private final int i2cBus;
    private final I2C i2c;
    private final GAIN gain;

    public ADS1115Impl(Context pi4j) {
        this(pi4j, ADDRESS, GAIN.GAIN_4_096V, 1);
    }

    public ADS1115Impl(Context pi4j, int address) {
        this(pi4j, address, GAIN.GAIN_4_096V, 1);
    }

    public ADS1115Impl(Context pi4j, int address, GAIN gain, int i2cBus) {
        this.address = address;
        this.deviceId = "ADS1115";
        this.context = pi4j;
        this.i2cBus = i2cBus;
        this.gain = gain;
        I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");
        I2CConfig i2cConfig = I2C.newConfigBuilder(pi4j).id(deviceId).bus(i2cBus).device(address).build();
        i2c = i2CProvider.create(i2cConfig);
        LOG.info("ADS1115 Connected to i2c bus={} address={}. OK.", i2cBus, address);
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getI2CBus() {
        return i2cBus;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public GAIN getGain() {
        return gain;
    }

    @Override
    public double getAIn0() {
        return gain.gainPerByte * readIn(calculateConfig(A0_IN));
    }

    @Override
    public double getAIn1() {
        return  gain.gainPerByte * readIn(calculateConfig(A1_IN));
    }

    @Override
    public double getAIn2() {
        return  gain.gainPerByte * readIn(calculateConfig(A2_IN));
    }

    @Override
    public double getAIn3() {
        return  gain.gainPerByte * readIn(calculateConfig(A3_IN));
    }

    private int readIn(int config) {
        i2c.writeRegisterWord(CONFIG_REGISTER, config);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            LOG.error("Error: ", e);
        }
        int result = i2c.readRegisterWord(CONVERSION_REGISTER);
        LOG.debug("readIn: {}, raw {}", config, result);
        return result;
    }

    private int calculateConfig(int pinId) {
        return CONFIG_REGISTER_TEMPLATE | gain.gain | pinId;
    }

    @Override
    public void close() throws Exception {
        i2c.close();
    }

}
public class I2C {
}
//public class Ads1115_App implements Application {
//    public void singleRead(Ads1115 adc) {
//        System.out.println("Single read started ...");
//        //read analog value from all four channels
//        double aIn0 = adc.readValue(Ads1115.Channel.A0);
//        double aIn1 = adc.readValue(Ads1115.Channel.A1);
//        double aIn2 = adc.readValue(Ads1115.Channel.A2);
//        double aIn3 = adc.readValue(Ads1115.Channel.A3);
//        System.out.printf("Voltages: a0=%.3f V, a1=%.3f V, a2=%.3f V, a3=%.3f V%n", aIn0, aIn1, aIn2, aIn3);
//    }
//}


//public class ControlsGUI {
//    public void ToTheLeft(){
//
//    }
//    public void ToTheRight(){
//
//    }
//    public void ButtonPush(){
//
//    }
//}
//
//public class Encoder {
//    Encoder(){
//
//    }
//    ReadInput(/*from Encoder Pin x1, Enoder Pin x2 TBD*/){
//        if (Enoceder Pin 1 = 1 && Encoder Pin 2 = 0){
//            ToTheLeft();
//        }
//        else if (Encoder Pin 1 = 0 && Encoder Pin 2 = 1){
//            ToTheRight();
//        }
//        else if (Encoder Pin 1 = 1 && Encoder Pin 2 = 1){
//            ButtonPush();
//        }
//        else{
//            system.out.printf("Button not touched but function ReadInput was triggered");
//        }
//    }
//
//
//}

//import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        ADS1115Impl testads = new ADS1115Impl(null);
        testads.getAIn0();
        /*JFrame frame = new JFrame("Test");

        frame.setVisible(true);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/

    }
}