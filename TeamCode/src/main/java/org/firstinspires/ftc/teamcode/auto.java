package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class auto extends LinearOpMode {
    // Declare variables

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized. Waiting for start...");
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;



        //stuff i think
        moveDrivetrain(frontLeft, backLeft, frontRight, backRight, 0.7,1500);
        stopDrivetrain(frontLeft, backLeft, frontRight, backRight);
        rotateDrivetrainLeft(frontLeft, backLeft, frontRight, backRight, 0.7, 300);
        stopDrivetrain(frontLeft, backLeft, frontRight, backRight);
    }

    private void moveDrivetrain(DcMotor frontLeft, DcMotor backLeft, DcMotor frontRight, DcMotor backRight, double power, int duration) throws InterruptedException {
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(-power);
        backRight.setPower(-power);
        sleep(duration);
    }

    private void stopDrivetrain(DcMotor frontLeft, DcMotor backLeft, DcMotor frontRight, DcMotor backRight) {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    private void rotateDrivetrainLeft(DcMotor frontLeft, DcMotor backLeft, DcMotor frontRight, DcMotor backRight, double power, int duration)  {
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(power);
        backRight.setPower(power);
        sleep(duration);
    }


}