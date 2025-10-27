package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class ShooterSubsystem extends SubsystemBase {

    private final DcMotorEx turretMotor;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        turretMotor = hardwareMap.get(DcMotorEx.class, "turret1");
        turretMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void shoot() {
        turretMotor.setVelocity(-5000*360*60, AngleUnit.DEGREES);
    }

    public void stop() {
        turretMotor.setVelocity(0);
    }

    public void reverse() {
        turretMotor.setVelocity(3500*360*60, AngleUnit.DEGREES);
    }

    public boolean isStalling() {
        return turretMotor.getCurrent(CurrentUnit.AMPS) > 9.2;
    }

    /**
     * This method is called repeatedly by the scheduler. It reads the
     * current state and sets the motor power accordingly.
     */
    @Override
    public void periodic() {

    }
}