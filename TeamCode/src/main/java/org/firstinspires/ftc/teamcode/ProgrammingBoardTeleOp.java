package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.os.SystemClock.sleep;

@TeleOp(name = "programming board TeleOp", group = "default")
public class ProgrammingBoardTeleOp extends LinearOpMode {
    private DigitalChannel touchSensor;
    private DcMotor motor;
    private double ticksPerRotation;
    private Servo servo;
    private AnalogInput pot;
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    public void init(HardwareMap hwMap) {
        touchSensor = hwMap.get(DigitalChannel.class, "touch_sensor");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
        motor = hwMap.get(DcMotor.class, "motor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ticksPerRotation = motor.getMotorType().getTicksPerRev();
        servo = hwMap.get(Servo.class, "servo");
        pot = hwMap.get(AnalogInput.class, "pot");

        colorSensor = hwMap.get(ColorSensor.class, "sensor_color_distance");
        distanceSensor = hwMap.get(DistanceSensor.class, "sensor_color_distance");
    }
    public boolean isTouchSensorPressed() {return!touchSensor.getState();
    }
    public void setMotorSpeed(double speed) {
        motor.setPower(speed);
    }

    //define motors and stuff
    DcMotor Large;
    DcMotor Small;
    DcMotor Rev;
    Servo Pass;
    ColorSensor RubberDuck;

    @Override
    public void runOpMode() throws InterruptedException {

        //hardware maps
            Large = hardwareMap.dcMotor.get("LM"); //port 3
            Small = hardwareMap.dcMotor.get("SM"); //port 0
            Rev = hardwareMap.dcMotor.get("HM"); //port 2
            Pass = hardwareMap.servo.get("S"); // medium port 3
            RubberDuck = hardwareMap.colorSensor.get("RD"); //small port 0

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                Large.setPower(-1.0);
                sleep(1000);
                Large.setPower(0);
            }
            if (gamepad1.y) {
                Pass.setPosition(90);
                sleep(1000);
                Pass.setPosition(0);
            }
            if (gamepad1.b) {
                Rev.setPower(0.5);
                sleep(1000);
                Rev.setPower(0);
            }
            if (gamepad1.a) {
                Small.setPower(0.5);
                sleep(1000);
                Small.setPower(0);
            }
        }

    }
}