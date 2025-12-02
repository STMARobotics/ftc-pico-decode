package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class ShooterSubsystem extends SubsystemBase {

    private final DcMotorEx turretMotor;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");
        turretMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turretMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void shoot() {
        turretMotor.setVelocity(3200.0 * 28 / 60);
    }

    public void close() {
        turretMotor.setVelocity(3200.0 * 28 / 60);
    }

    public void stop() {
        turretMotor.setVelocity(0
        );
    }



    public double getRPM() {
        return turretMotor.getVelocity() * 60.0 / 28.0;
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