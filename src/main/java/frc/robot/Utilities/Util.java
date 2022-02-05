package frc.robot.Utilities;

public class Util {

    public static double clip(double input, double min, double max) {
        return Math.max(min, Math.min(input, max));
      }
    
}
