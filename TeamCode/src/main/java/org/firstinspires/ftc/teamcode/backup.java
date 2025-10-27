package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.ShooterSubsystem;

@TeleOp(name = "bakcup (BAREBONES)")
public class backup extends LinearOpMode {
    // variables

    @Override
    public void runOpMode() throws InterruptedException {
        // initialization
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor turret1 = hardwareMap.dcMotor.get("turret1");


        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        ShooterSubsystem shooterSubsystem = new ShooterSubsystem(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            // action area
            double x = -gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            double t = gamepad1.right_trigger;


            // Math for drivetrain (IGNORE)
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontRightPower = (y + x + rx) / denominator;
            double rearRightPower = (y - x + rx) / denominator;
            double rearLeftPower = (y + x - rx) / denominator;
            double frontLeftPower = (y - x - rx) / denominator;

            turret1.setPower(t);

            // Telling the motors to go at a certain power
            frontRight.setPower(frontRightPower);
            backRight.setPower(rearRightPower);
            backLeft.setPower(rearLeftPower);
            frontLeft.setPower(frontLeftPower);
        }
    }
}
