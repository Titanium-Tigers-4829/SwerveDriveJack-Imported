// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ModuleConstants;

public class SwerveModule extends SubsystemBase {

  private final WPI_TalonFX driveMotor;
  private final WPI_TalonFX turnMotor;
  private final CANCoder canCoder;

  private final int moduleNum;
  /** Creates a new SwerveModule. */
  public SwerveModule(int driveMotorID, int turnMotorID, boolean driveMotorReversed, boolean turnMotorReversed, int num, int canCoderID) {

    // Making the motor objects
    driveMotor = new WPI_TalonFX(driveMotorID);
    turnMotor = new WPI_TalonFX(turnMotorID);

    moduleNum = num;

    canCoder = new CANCoder(canCoderID);

    // Reversing them if necessary
    driveMotor.setInverted(driveMotorReversed);
    turnMotor.setInverted(turnMotorReversed);

    // Setting up the encoders
    driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    // Setting up PID for turnMotor
    turnMotor.config_kP(0, 1.0);

    // TODO: Setup Absolute Encoder stuff to straighten wheels when robot boots up
    // Question: How will the robot know where the wheels currently are (a prerequisite to being able to move the wheels to where we want them to be) when it boots up?
    // I thought that that is why we needed to use pins to make sure all the wheels are perfectly aligned before the match. I could be wrong though.
  }

  /**
   * Gets the angle in Radians of the current turn motor
   */
  public double getTurnAngle() {
    // return (turnMotor.getSelectedSensorPosition() % 1024) * ModuleConstants.ticksToRadians;
    return canCoder.getPosition() % 360;
  }

  /**
   * Gets the velocity in Radians per second for the current turn motor
   */
  public double getTurnVelocity() {
    // return (turnMotor.getSelectedSensorVelocity()) * ModuleConstants.ticksToRadians;
    return canCoder.getVelocity();
  }

  // TODO: Check if this works
  /**
   * Gets the velocity in Meters per second of the current drive motor
   */
  public double getDriveVelocity() {
    // First gets the motor shaft units per 100ms, then multiplies by 10 to get it in units per second,
    // next it divides it by ticks and motor shaft units to get the rps,
    // next it multiplies that by the circumference of the wheel to get inches per second
    // finally it converts it to meters per second
    return ((((turnMotor.getSelectedSensorVelocity()) * 10) / (2048 * 7.13) ) * 4 * Math.PI * 0.0254);
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurnAngle()));
  }

  public void reset(){
    turnMotor.setSelectedSensorPosition(0);
  }

  public void stopModule() {
    driveMotor.set(0);
    turnMotor.set(0);
  }

  public void turnTo(double degree){
    degree = (degree * 2048);
    SmartDashboard.putNumber("Module " + String.valueOf(moduleNum) + " turnTo", degree);
    turnMotor.set(ControlMode.MotionMagic, degree);
  }

  public void setDesiredState(SwerveModuleState state) {
    if (Math.abs(state.speedMetersPerSecond) < 0.01){
      stopModule();
      return;
    }
    // Optimizes angle so the wheel won't ever have to move more than 90 degrees
    // state = SwerveModuleState.optimize(state, getState().angle);
    // Sets the drive motor's speed from 0.0 to 1.0
    driveMotor.set(ControlMode.PercentOutput, state.speedMetersPerSecond / ModuleConstants.physicalMaxSpeedMetersPerSecond);
    turnTo(state.angle.getDegrees());
    SmartDashboard.putNumber("Module " + String.valueOf(moduleNum) + " angle", state.angle.getDegrees());

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Module " + String.valueOf(moduleNum) + " deg", getTurnAngle());
    SmartDashboard.putNumber("Module " + String.valueOf(moduleNum) + " integrated", turnMotor.getSelectedSensorPosition());

  }
}
