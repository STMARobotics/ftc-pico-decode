package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

@TeleOp(name = "Basic Mecanum Drive", group = "Linear OpMode")
public class DRIVE extends LinearOpMode {
    // variables

    @Override
    public void runOpMode() throws InterruptedException {
        // initialization
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor turret1 = hardwareMap.dcMotor.get("turret1"); // You gotta make sure this is in ur robot config


        waitForStart();

        while (opModeIsActive()) {
            // action area
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            double t = gamepad1.right_trigger;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontRightPower = (y + x + rx) / denominator;
            double rearRightPower = (y - x + rx) / denominator;
            double rearLeftPower = (y + x - rx) / denominator;
            double frontLeftPower = (y - x - rx) / denominator;

            frontRight.setPower(frontRightPower);
            backRight.setPower(rearRightPower);
            backLeft.setPower(rearLeftPower);
            frontLeft.setPower(frontLeftPower);
            turret1.setPower(t);
        }
    }
}
