package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "actually use this one its better")
public class betterdrive extends LinearOpMode {
    // variables


    @Override
    public void runOpMode() throws InterruptedException {
        // initialization
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        CRServo indexerLeft = hardwareMap.crservo.get("indexerLeft");
        CRServo indexerCenter = hardwareMap.crservo.get("indexerCenter");
        CRServo indexerRight = hardwareMap.crservo.get("indexerRight");
        DcMotor turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");

        DcMotor intake = hardwareMap.dcMotor.get("intake");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        indexerRight.setDirection(DcMotorSimple.Direction.REVERSE);
        indexerCenter.setDirection(DcMotorSimple.Direction.REVERSE);

        ShooterSubsystem shooterSubsystem = new ShooterSubsystem(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            // action area
            double x = -gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = -gamepad1.right_stick_x;
            double leftTrigger = gamepad2.left_trigger;
            double rightTrigger = gamepad2.right_trigger;

            // Math for drivetrain (IGNORE)
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontRightPower = (y + x + rx) / denominator;
            double rearRightPower = (y - x + rx) / denominator;
            double rearLeftPower = (y + x - rx) / denominator;
            double frontLeftPower = (y - x - rx) / denominator;

            if (gamepad1.x) {
                intake.setPower(0.5);
                indexerCenter.setPower(0.5);
                indexerLeft.setPower(0.5);
                indexerRight.setPower(0.5);
            } else if (gamepad1.y) {
                intake.setPower(-0.5);
                indexerCenter.setPower(-0.5);
                indexerLeft.setPower(-0.5);
                indexerRight.setPower(-0.5);
            } else {
                intake.setPower(0);
                indexerCenter.setPower(0);
                indexerLeft.setPower(0);
                indexerRight.setPower(0);
            }

            if (gamepad1.a) {
                shooterSubsystem.shoot();
            } else {
                shooterSubsystem.stop();
            }


            // Telling the motors to go at a certain power
            frontRight.setPower(frontRightPower);
            backRight.setPower(rearRightPower);
            backLeft.setPower(rearLeftPower);
            frontLeft.setPower(frontLeftPower);

            telemetry.addData("shooter RPM", shooterSubsystem.getRPM());
            telemetry.update();
        }
    }
}