// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.Utilities.Util;

public class Shooter extends PIDSubsystem {

  private final TalonFX m_motorLeft;
  private final TalonFX m_motorRight;
  /** Creates a new Motor. */
  public Shooter() {
    //m_controller = new PIDController(0.00055, 0.000001, 0.00001);
    super(new PIDController(0.00055, 0.000001, 0.00001));
    //m_controller.setTolerance(80.0);
    getController().setTolerance(80.0);

    setSetpoint(0.0);

    m_motorLeft = new TalonFX(RobotMap.CAN.SHOOTER_LEFT);
    m_motorRight = new TalonFX(RobotMap.CAN.SHOOTER_RIGHT);

    m_motorRight.setInverted(false);
    m_motorLeft.setInverted(true);

    m_motorRight.configClosedloopRamp(0.0);
    m_motorLeft.configClosedloopRamp(0.0);
    m_motorRight.configOpenloopRamp(0.0);
    m_motorLeft.configOpenloopRamp(0.0);
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  }

  public double getAverageSpeed() {
    return ((getRightSpeed() + getLeftSpeed()) / 2);
  }

  public double getRightSpeed() {
    return (m_motorRight.getSelectedSensorVelocity() / 2048d * 10d * 60d) * 2d;
  }

  public double getLeftSpeed() {
    return (m_motorLeft.getSelectedSensorVelocity() / 2048d * 10d * 60d) * 2d;
  }

  public void setSetpoint(double setpoint) {
    if (getController() != null) {
      getController().setSetpoint(Util.clip(setpoint, -2500, 2500));
    }
    super.setSetpoint(Util.clip(setpoint, -2500, 2500));
  }

  public void set(double speed) {
    speed = Util.clip(speed, -0.5, 0.5);
    m_motorLeft.set(ControlMode.PercentOutput, speed);
    m_motorRight.set(ControlMode.PercentOutput, speed);
  }
  
  public void stop() {
    set(0);
  }

  public void setWithPID(double setpoint) {
    set(m_controller.calculate(getAverageSpeed(), setpoint));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("shooter/speed", getAverageSpeed());
    System.out.println(getAverageSpeed());
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected double getMeasurement() {
    // TODO Auto-generated method stub
    return 0;
  }
}
