/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.JoyStickConstants;
import frc.robot.commands.SwerveJoyStickDrive;
import frc.robot.subsystems.SwerveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here
  //SendableChooser<Command> chooser = new SendableChooser<>();

  public final SwerveSubsystem swerveSubsystem;

  private final Joystick driverJoystick = new Joystick(JoyStickConstants.joyStickPort);
  // private final motor _motor;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    swerveSubsystem = new SwerveSubsystem();
    swerveSubsystem.setDefaultCommand(new SwerveJoyStickDrive(
      swerveSubsystem, 
      () -> -driverJoystick.getRawAxis(1),
      () -> driverJoystick.getRawAxis(0),
      () -> driverJoystick.getRawAxis(2),
      () -> driverJoystick.getRawButton(2)));
    // _motor = new motor();
    // _motor.setDefaultCommand(new spin(_motor, ()->driverJoystick.getRawAxis(1), () -> driverJoystick.getRawAxis(3)));
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(driverJoystick, 1).whenPressed(() -> swerveSubsystem.zeroGyro());
    // new JoystickButton(driverJoystick, 2).whenPressed(()->swerveSubsystem.reset());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }


}
