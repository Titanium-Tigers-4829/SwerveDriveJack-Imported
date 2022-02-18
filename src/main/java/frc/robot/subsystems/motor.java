// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class motor extends SubsystemBase {
  /** Creates a new motor. */
  private WPI_TalonFX motor1 = new WPI_TalonFX(0);
  private WPI_TalonFX motor2 = new WPI_TalonFX(1);
  public motor() {
    motor1.configFactoryDefault();
    motor2.configFactoryDefault();
  }

  public void spin(double speed1, double speed2){
    motor1.set(ControlMode.PercentOutput, speed1);
    motor2.set(ControlMode.PercentOutput, speed2);
    SmartDashboard.putNumber("Motor 1 speed", speed1);
    SmartDashboard.putNumber("Motor 2 speed", speed2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
