/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Auto Red", group="Linear Opmode")
public class Auto_Red extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot();
        robot.init(hardwareMap, telemetry);

        waitForStart();
        runtime.reset();

        robot.drive.encoderDrive(0.6,0.75,0.75,3);

        robot.drive.drive(0,0.5,0,0.6);
        safeWait(0.6);

//        robot.drive.drive(0,0,-0.2,0.2);
//        safeWait(0.2);

        robot.drive.encoderDrive(0.5,-0.2,0.2,2);

        //如果安装了摄像头，直接在此调用摄像头初始化程序
        robot.detector.start();

        //调整此参数以保证全素运行
        safeWait(3);

        robot.detector.stop();

        telemetry.addData("case: ", robot.detector.stableResult);
        telemetry.update();

        robot.drive.encoderDrive(0.8,1.8, 1.8, 4);
        robot.stop();

        robot.launch.launch(2.2*360);

        safeWait(3);

        //控制伺服将环推到飞轮上
        robot.launch.push();
//        robot.inTake.in(5*360,5);
        //等待动作完成
        safeWait(1);
        robot.launch.push();
        safeWait(1);
        robot.launch.push();
        safeWait(1);
        robot.launch.stop();

        if (robot.detector.stableResult ==0){
            robot.drive.drive(0,0,0.8,0.6);
            safeWait(0.6);

            robot.drive.drive(0.5,0,0,0.7);
            safeWait(0.7);

            releaseGoal();

            robot.intake.kick();
        }

        if (robot.detector.stableResult == 1){

            robot.drive.drive(0.5,0,0,0.9);
            safeWait(0.9);

            releaseGoal();

            robot.intake.kick();
        }

        if (robot.detector.stableResult == 2){
            robot.drive.drive(1,0,0,1);
            safeWait(1);

            robot.drive.drive(-0.5,0,0,0.5);
            safeWait(0.5);

            robot.drive.drive(0,0,0.6, 0.5);
            safeWait(0.5);

            robot.drive.drive(0.5,0,0,0.8);
            safeWait(0.8);

            releaseGoal();

            robot.drive.drive(0,0,-0.5,0.5);
            safeWait(0.5);

            robot.drive.encoderDrive(0.8,-2,-2,5);

            robot.intake.kick();
        }

        /*

        double forward = 0.0;
        double backward = 0.0;
        switch (robot.detector.stableResult){
            case 1://中
                forward = 3;
                backward = 1;
                break;
            case 2://远
                forward = 4;
                backward = 1.5;
                break;
            default://近
                forward = 2;
                backward = 0.5;
        }

        //向前直行3秒，2秒后自动伸出手臂
        robot.drive.drive(1,0,0,forward);
        safeWait(forward);
        // 平移回到放置摇摇乐的位置
        if(robot.detector.stableResult==0 ||robot.detector.stableResult==2 ){
            robot.drive.drive(0,1,0,1);
            safeWait(1);
        }

         */

        robot.stop();
    }

    public void releaseGoal(){
        robot.arm.rotate(0.8);
        safeWait(0.8);
        robot.arm.stop();

        safeWait(1);

        robot.arm.open();

        safeWait(2);
    }


    public void safeWait(double seconds){
        double initTime = runtime.seconds();
        while (opModeIsActive() && (runtime.seconds() < initTime+ seconds)) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
