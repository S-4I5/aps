package GUI.view;

import GUI.Waveform;

import program.Controller;
import statistics.ClientStatistics;
import statistics.Statistics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AutoModeGraphs {
    private void createStepModeFrame() {
        final Controller controller = new Controller();
    }



    public void start() {
        JFrame currentFrame = new JFrame() {
        };
        Statistics.getInstance();
        Statistics.countOfDevices = 4;
        Statistics.countOfClients = 10;
        Statistics.workTime = 100;
        Statistics.sizeOfBuffer = 3;
        Statistics.minimum = 0.1;
        Statistics.maximum = 0.2;
        Statistics.lambda = 5;

        ArrayList<Double> err = new ArrayList<>();
        ArrayList<Double> time = new ArrayList<>();
        ArrayList<Double> stress = new ArrayList<>();

        System.out.println("buffer");
        for (int j = 10; j <= 100 ; j+=10) {
            Statistics.getInstance();
            Statistics.sizeOfBuffer = j;
            Controller controller = new Controller();
            controller.auto();

            System.out.println(controller);

            int i = 0;

            double avgError = 0;
            double timeInSystem = 0;
            double deviceWork = 0;

            for (ClientStatistics clientStat : controller.getStatistics().getClientsStats()) {
                //
                avgError += (double) clientStat.getCanceledOrdersCount() / clientStat.getGeneratedOrdersCount();

                //
                timeInSystem += clientStat.getTotalTime() / clientStat.getGeneratedOrdersCount();

                //
                if (i < Controller.statisticsPub.getDevicesCount()) {
                    deviceWork +=
                            Controller.statisticsPub.getDevicesWorkTime().get(i) / controller.getCurrentTime();
                }
                i++;
            }
            System.out.println(j);
            System.out.println(avgError/controller.getClients().size());
            System.out.println(timeInSystem/controller.getClients().size());
            System.out.println(deviceWork/controller.getDevices().size());

            err.add(avgError/controller.getClients().size()*10);
            time.add(timeInSystem/controller.getClients().size());
            stress.add(deviceWork/controller.getDevices().size());
        }

        Statistics.sizeOfBuffer = 3;

        System.out.println("clients");
        for (int j = 10; j <= 100 ; j+=10) {
            Statistics.getInstance();
            Statistics.countOfClients = j;
            Controller controller = new Controller();
            controller.auto();

            System.out.println(controller);

            int i = 0;

            double avgError = 0;
            double timeInSystem = 0;
            double deviceWork = 0;

            for (ClientStatistics clientStat : controller.getStatistics().getClientsStats()) {
                //
                avgError += (double) clientStat.getCanceledOrdersCount() / clientStat.getGeneratedOrdersCount();

                //
                timeInSystem += clientStat.getTotalTime() / clientStat.getGeneratedOrdersCount();

                //
                if (i < Controller.statisticsPub.getDevicesCount()) {
                    deviceWork +=
                            Controller.statisticsPub.getDevicesWorkTime().get(i) / controller.getCurrentTime();
                }
                i++;
            }
            System.out.println(j);
            System.out.println(avgError/controller.getClients().size());
            System.out.println(timeInSystem/controller.getClients().size());
            System.out.println(deviceWork/controller.getDevices().size());
        }

        Statistics.countOfClients = 10;

        System.out.println("devices");
        for (int j = 10; j <= 100 ; j+=10) {
            Statistics.getInstance();
            Statistics.countOfDevices = j;
            Controller controller = new Controller();
            controller.auto();

            System.out.println(controller);

            int i = 0;

            double avgError = 0;
            double timeInSystem = 0;
            double deviceWork = 0;

            for (ClientStatistics clientStat : controller.getStatistics().getClientsStats()) {
                //
                avgError += (double) clientStat.getCanceledOrdersCount() / clientStat.getGeneratedOrdersCount();

                //
                timeInSystem += clientStat.getTotalTime() / clientStat.getGeneratedOrdersCount();

                //
                if (i < Controller.statisticsPub.getDevicesCount()) {
                    deviceWork +=
                            Controller.statisticsPub.getDevicesWorkTime().get(i) / controller.getCurrentTime();
                }
                i++;
            }
            System.out.println(j);
            System.out.println(avgError/controller.getClients().size());
            System.out.println(timeInSystem/controller.getClients().size());
            System.out.println(deviceWork/controller.getDevices().size());

        }

        currentFrame.setVisible(true);
        currentFrame.setTitle("Параметры симуляции");
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentFrame.setSize(240, 580);
        currentFrame.setLocation(900, 250);
        currentFrame.setLayout(null);
    }

}
