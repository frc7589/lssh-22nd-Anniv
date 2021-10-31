// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.DoubleSolenoid;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final XboxController con = new XboxController(0);
 
  private WPI_VictorSPX victorDriveLeft1 = new WPI_VictorSPX(6);
  private WPI_VictorSPX victorDriveLeft2 = new WPI_VictorSPX(2);
  private SpeedControllerGroup victorDriveLeft = new SpeedControllerGroup(victorDriveLeft2,victorDriveLeft1);
  private WPI_VictorSPX victorDriveRight1 = new WPI_VictorSPX(5);
  private WPI_VictorSPX victorDriveRight2 = new WPI_VictorSPX(0);
  private SpeedControllerGroup victorDriveRight = new SpeedControllerGroup(victorDriveRight2,victorDriveRight1);
  
  double  [] speed  ={0.4,0.6,0.8,1.0};
  int i = 0;

  public static DoubleSolenoid sol;
  public static DoubleSolenoid sol2;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    sol = new DoubleSolenoid(35, 0, 1);
    sol2 = new DoubleSolenoid(35 , 2, 3);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if(con.getAButtonPressed()){
      sol.set(Value.kForward);
    }

    if(con.getBButtonPressed()){
      sol.set(Value.kReverse);
    }

    if(con.getXButtonPressed()){
      sol2.set(Value.kForward);
    }

    if(con.getYButtonPressed()){
      sol2.set(Value.kReverse);
    }

    if(Math.abs(con.getY(Hand.kLeft))>0.1){
      victorDriveLeft.set(con.getY(Hand.kLeft)*-speed[i]);
    }
    else{
      victorDriveLeft.set(0);
    }
    if(Math.abs(con.getY(Hand.kRight))>0.1){
      victorDriveRight.set(con.getY(Hand.kRight)*speed[i]);
    }
    else{
      victorDriveRight.set(0);
    }
   
    if(con.getBumperPressed(Hand.kRight)){
      if(i<3){
        i++;
      }
    }
    if(con.getBumperPressed(Hand.kLeft)){
      if(i>0){
        i--;
      }
    }
  }
  

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
