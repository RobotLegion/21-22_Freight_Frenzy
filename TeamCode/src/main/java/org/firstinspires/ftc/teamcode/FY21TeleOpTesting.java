package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "FY21TPrototype", group = "TeleOp")
public class FY21TeleOpTesting extends LinearOpMode {
    DcMotor topRight;
    DcMotor bottomRight;
    DcMotor topLeft;
    DcMotor bottomLeft;
    DcMotor carouselSpinner;
    DcMotor linearSlide;
    DcMotor spindle;

    double speed = 1;
    double linearSpeed = 0;
    double spindleSpeed = 0;
    double carouselSpeed = 0;
    double teamSpin = 1;
    double linearPosition = 1;
    long linearWait/* = unknown*/;
    long linearWait2 = (linearWait * 2);

    @Override
    public void runOpMode() throws InterruptedException {
        //hardware maps
        topRight = hardwareMap.dcMotor.get("TR"); // control hub port 0
        bottomRight = hardwareMap.dcMotor.get("BR"); //control hub port 1
        topLeft = hardwareMap.dcMotor.get("TL"); //control hub port 2
        bottomLeft = hardwareMap.dcMotor.get("BL"); //control hub port 3
        linearSlide = hardwareMap.dcMotor.get("LS"); //expansion hub port 0
        spindle = hardwareMap.dcMotor.get("SM"); //expansion hub port 1
        carouselSpinner = hardwareMap.dcMotor.get("CS"); //expansion hub port 2

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) { //when held, will slow the robot down for precise driving.
                speed = 0.25;
            }
            if (!gamepad1.right_bumper) { //when released, speed will be brought back to 1. Exclamation marks are "not" functions.
                speed = 1;
            }
            if (gamepad2.left_stick_button) {
                teamSpin = -1;
            }
            if (gamepad2.right_stick_button) {
                teamSpin = 1;
            }
            if (gamepad2.left_bumper) {
                spindleSpeed = -1;
            }
            if (gamepad2.right_bumper) {
                spindleSpeed = 1;
            }
            if (gamepad2.y) {
                if (linearPosition != 3){
                    if (linearPosition == 1){
                        linearPosition = 3;
                        linearSpeed = -1;
                        sleep (linearWait2);
                        linearSpeed = 0;
                    }else{
                        linearPosition = 3;
                        linearSpeed = -1;
                        sleep (linearWait);
                        linearSpeed = 0;
                    }
                }
            }
            if (gamepad2.b) {
                if (linearPosition != 2){
                    if (linearPosition == 1){
                        linearPosition = 2;
                        linearSpeed = -1;
                        sleep (linearWait);
                        linearSpeed = 0;
                    }else{
                        linearPosition = 2;
                        linearSpeed = 1;
                        sleep (linearWait);
                        linearSpeed = 0;
                    }
                }
            }
            if (gamepad2.a) {
                if (linearPosition != 1){
                    if (linearPosition == 3){
                        linearPosition = 1;
                        linearSpeed = 1;
                        sleep (linearWait2);
                        linearSpeed = 0;
                    }else{
                        linearPosition = 1;
                        linearSpeed = 1;
                        sleep (linearWait);
                        linearSpeed = 0;
                    }
                }
            }
            if (gamepad2.x) {
                carouselSpeed = teamSpin;
            }
            if (!gamepad2.x) {
                carouselSpeed = 0;
            }

            float gamepad1LeftY = -gamepad1.left_stick_x;        // Sets the gamepads left sticks y position to a float so that we can easily track the stick
            float gamepad1LeftX = gamepad1.left_stick_y;       // Sets the gamepads left sticks x position to a float so that we can easily track the stick
            float gamepad1RightX = gamepad1.right_stick_x;     // Sets the gamepads right sticks x position to a float so that we can easily track the stick

            // Mechanum formulas
            double TopRightSpeed = gamepad1LeftY + gamepad1LeftX + gamepad1RightX;     // Combines the inputs of the sticks to clip their output to a value between 1 and -1
            double TopLeftSpeed = -gamepad1LeftY + gamepad1LeftX + gamepad1RightX;     // Combines the inputs of the sticks to clip their output to a value between 1 and -1
            double BottomRightSpeed = gamepad1LeftY - gamepad1LeftX + gamepad1RightX;      // Combines the inputs of the sticks to clip their output to a value between 1 and -1
            double BottomLeftSpeed = -gamepad1LeftY - gamepad1LeftX + gamepad1RightX;      // Combines the inputs of the sticks to clip their output to a value between 1 and -1

            // sets speed
            double topLeftCorrectedSpeed = Range.clip(Math.pow(TopRightSpeed, 3), -speed, speed);    // Slows down the motor and sets its max/min speed to the double "speed"
            double topRightCorrectedSpeed = Range.clip(Math.pow(TopLeftSpeed, 3), -speed, speed);      // Slows down the motor and sets its max/min speed to the double "speed"
            double bottomLeftCorrectedSpeed = Range.clip(Math.pow(BottomRightSpeed, 3), -speed, speed);      // Slows down the motor and sets its max/min speed to the double "speed"
            double bottomRightCorrectedSpeed = Range.clip(Math.pow(BottomLeftSpeed, 3), -speed, speed);        // Slows down the motor and sets its max/min speed to the double "speed"

            topRight.setPower(topRightCorrectedSpeed);
            bottomRight.setPower(bottomRightCorrectedSpeed);
            topLeft.setPower(topLeftCorrectedSpeed);
            bottomLeft.setPower(bottomLeftCorrectedSpeed);
            linearSlide.setPower(linearSpeed);
            spindle.setPower(spindleSpeed);
            carouselSpinner.setPower(carouselSpeed);
        }
    }
}