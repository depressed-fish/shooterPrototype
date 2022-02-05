// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.CustomButtons.DoubleShooter;
import frc.robot.CustomButtons.SpeedChange;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Shooter m_shooter = new Shooter();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final Joystick m_joystick;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_joystick = new Joystick(0);

    // Configure the button bindings
    configureButtonBindings();

  }

  /**
   * method to turn motor on
   */
  private void configureButtonBindings() {
    //code  
    JoystickButton runShooter = new JoystickButton(m_joystick, 3);
    JoystickButton runShooterTwo = new JoystickButton(m_joystick, 4);

    JoystickButton increase = new JoystickButton(m_joystick, 1);
    JoystickButton decrease = new JoystickButton(m_joystick, 2);

    DoubleShooter shootButtons = new DoubleShooter(runShooter, runShooterTwo);
    

    shootButtons.whenHeld(new RunCommand(() -> { m_shooter.set(Constants.rates.speed); }), true);
    shootButtons.whenReleased(new RunCommand(() -> { m_shooter.stop(); }), true);
    
    increase.whenPressed(new InstantCommand(() -> { Constants.rates.speed += 0.05;}));
    decrease.whenPressed(new InstantCommand(() -> { Constants.rates.speed -= 0.05;}));
    
    //m_shooter.set(3000 * 0.0001); // Run at resting speed

    // m_shooter.setDefaultCommand(new RunCommand(() -> {
    //   if (m_shooter.isEnabled()) {
    //     m_shooter.disable();
    //   }
    //   m_shooter.set(3000 * 0.0001); // Run at resting speed
    // }, m_shooter));

    //runShooter.whileHeld(new RunCommand(() -> { m_shooter.set(0.5); }), true);
    //runShooter.whenReleased(new RunCommand(() -> { m_shooter.stop(); }), true);

    
    // runShooter.whileHeld(new RunCommand(() -> { m_shooter.setWithPID(10); }), true);
    // runShooter.whenReleased(new RunCommand(() -> { m_shooter.stop(); }), true);

  }

  /**
   * auto bad
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
