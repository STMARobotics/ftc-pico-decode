package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Main Drive (USE THIS ONE)")
public class DRIVE extends LinearOpMode {
    // variables

    private DcMotorEx turretMotor = null;
    private CRServo indexerLeft = null;
    private CRServo indexerCenter = null;
    private CRServo indexerRight = null;
    private DcMotor intake = null;


    private enum Launchstate {
        Idle,
        Spinup,
        Launch,
        Launching,
    }

    private Launchstate launchstate;

    ElapsedTime feederTimer = new ElapsedTime();

    void launch(boolean shotRequested) {
        switch (launchstate) {
            case Idle:
                if (shotRequested) {
                    launchstate = Launchstate.Spinup;
                } else {
                    turretMotor.setVelocity(0); // Keep motor off when idle
                }
                break;
            case Spinup:
                if (!shotRequested) { // if button released during spinup, go back to idle.
                    launchstate = Launchstate.Idle;
                    turretMotor.setVelocity(0);
                    break;
                }
                turretMotor.setVelocity(1900);
                if (turretMotor.getVelocity() > 1700.) {
                    launchstate = Launchstate.Launch;
                }
                break;
            case Launch:
                indexerLeft.setPower(1);
                indexerRight.setPower(1);
                indexerCenter.setPower(1);
                feederTimer.reset();
                launchstate = Launchstate.Launching;
                break;
            case Launching:
                if (feederTimer.seconds() > 0.2) {
                    launchstate = Launchstate.Idle;
                    indexerLeft.setPower(0);
                    indexerRight.setPower(0);
                    indexerCenter.setPower(0);
                    turretMotor.setVelocity(0);
                }
                break;
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // initialization
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        indexerLeft = hardwareMap.crservo.get("indexerLeft");
        indexerCenter = hardwareMap.crservo.get("indexerCenter");
        indexerRight = hardwareMap.crservo.get("indexerRight");
        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");
        turretMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //turretMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));
        launchstate = Launchstate.Idle;

        intake = hardwareMap.dcMotor.get("intake");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        indexerRight.setDirection(DcMotorSimple.Direction.REVERSE);
        indexerCenter.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            // action area
            double x = -gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = -gamepad1.right_stick_x;
            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;

            // Math for drivetrain (IGNORE)
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontRightPower = (y + x + rx) / denominator;
            double rearRightPower = (y - x + rx) / denominator;
            double rearLeftPower = (y + x - rx) / denominator;
            double frontLeftPower = (y - x - rx) / denominator;

            // Intake control with triggers
            if (leftTrigger > 0.1) {
                intake.setPower(leftTrigger);
                indexerCenter.setPower(leftTrigger);
                indexerLeft.setPower(leftTrigger);
                indexerRight.setPower(leftTrigger);
            } else if (rightTrigger > 0.1) {
                intake.setPower(-rightTrigger); // Reverse
                indexerCenter.setPower(-rightTrigger);
                indexerLeft.setPower(-rightTrigger);
                indexerRight.setPower(-rightTrigger);
            } else if (launchstate == Launchstate.Idle) { // Don't run intake logic if we are in the middle of a shot
                intake.setPower(0);
                indexerCenter.setPower(0);
                indexerLeft.setPower(0);
                indexerRight.setPower(0);
            }

            // Shooting controlled by 'B' button
            launch(gamepad1.b);


            // Telling the motors to go at a certain power
            frontRight.setPower(frontRightPower);
            backRight.setPower(rearRightPower);
            backLeft.setPower(rearLeftPower);
            frontLeft.setPower(frontLeftPower);

            telemetry.addData("Shooter Velocity", turretMotor.getVelocity());
            telemetry.addData("Launch State", launchstate);
            telemetry.update();

        }
    }
}
