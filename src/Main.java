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

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Test");

        frame.setVisible(true);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}