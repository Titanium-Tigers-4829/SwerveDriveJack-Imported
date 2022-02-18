// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.motor;

public class spin extends CommandBase {
  /** Creates a new spin. */
  private motor _motor;
  private DoubleSupplier _speed, _speed2;
  public spin(motor Motor, DoubleSupplier speed, DoubleSupplier speed2) {
    // Use addRequirements() here to declare subsystem dependencies.
    _motor = Motor;
    _speed = speed;
    _speed2 = speed2;
    addRequirements(Motor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    _motor.spin(_speed.getAsDouble(), _speed2.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    _motor.spin(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
