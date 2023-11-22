package GUI.view.fx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;
import program.Controller;
import statistics.ClientStatistics;
import statistics.Statistics;

import java.util.ArrayList;

public class AutoModeController {

    final NumberAxis rejRate = new NumberAxis();
    final NumberAxis timeInSYS = new NumberAxis();
    final NumberAxis avgUtil = new NumberAxis();
    final NumberAxis bufSize = new NumberAxis();
    final NumberAxis prodCount = new NumberAxis();
    final NumberAxis devCount = new NumberAxis();
    @FXML
    public LineChart<Number, Number> pn_Rejects = new LineChart<>(prodCount, rejRate);
    @FXML
    public LineChart<Number, Number> pn_TimeInSys = new LineChart<>(prodCount, timeInSYS);
    @FXML
    public LineChart<Number, Number> pn_DevStress = new LineChart<>(prodCount, avgUtil);

    @FXML
    public LineChart<Number, Number> bs_Rejects = new LineChart<>(bufSize, rejRate);
    @FXML
    public LineChart<Number, Number> bs_TimeInSys = new LineChart<>(bufSize, timeInSYS);
    @FXML
    public LineChart<Number, Number> bs_DevStress = new LineChart<>(bufSize, avgUtil);

    @FXML
    public LineChart<Number, Number> sc_Rejects = new LineChart<>(devCount, rejRate);
    @FXML
    public LineChart<Number, Number> sc_TimeInSys = new LineChart<>(devCount, timeInSYS);
    @FXML
    public LineChart<Number, Number> sc_DevStress = new LineChart<>(devCount, avgUtil);

    @FXML
    public void initialize() {

        rejRate.setLabel("Вероятность отказа");
        timeInSYS.setLabel("Среднее время в сис.");
        avgUtil.setLabel("Наргруженность");
        bufSize.setLabel("Размер буффера");
        prodCount.setLabel("Кол-во источ.");
        devCount.setLabel("Кол-во приборов");

        XYChart.Series<Number, Number> pn_RejectsS = new XYChart.Series<>();
        pn_RejectsS.setName("Частота/кол-во ист.");
        XYChart.Series<Number, Number> pn_TimeInSysS = new XYChart.Series<>();
        pn_TimeInSysS.setName("Время/кол-во ист.");
        XYChart.Series<Number, Number> pn_DevStressS = new XYChart.Series<>();
        pn_DevStressS.setName("Нагруженность/кол-во ист.");
        XYChart.Series<Number, Number> bs_RejectsS = new XYChart.Series<>();
        bs_RejectsS.setName("Частота/размер буфера");
        XYChart.Series<Number, Number> bs_TimeInSysS = new XYChart.Series<>();
        bs_TimeInSysS.setName("Время/размер буфера");
        XYChart.Series<Number, Number> bs_DevStressS = new XYChart.Series<>();
        bs_DevStressS.setName("Нагруженность/размер буфера");
        XYChart.Series<Number, Number> sc_RejectsS = new XYChart.Series<>();
        sc_RejectsS.setName("Частота/кол-во прибор.");
        XYChart.Series<Number, Number> sc_TimeInSysS = new XYChart.Series<>();
        sc_TimeInSysS.setName("Время/кол-во прибор.");
        XYChart.Series<Number, Number> sc_DevStressS = new XYChart.Series<>();
        sc_DevStressS.setName("Нагруженность/кол-во прибор.");


        Statistics.getInstance();
        Statistics.countOfDevices = 4;
        Statistics.countOfClients = 10;
        Statistics.workTime = 100;
        Statistics.sizeOfBuffer = 3;
        Statistics.minimum = 0.1;
        Statistics.maximum = 0.2;
        Statistics.lambda = 5;

        ArrayList<Pair<Integer, Double>> err = new ArrayList<>();
        ArrayList<Pair<Integer, Double>> time = new ArrayList<>();
        ArrayList<Pair<Integer, Double>> stress = new ArrayList<>();

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

            bs_RejectsS.getData().add(new XYChart.Data<>(j, avgError/controller.getClients().size()));
            bs_DevStressS.getData().add(new XYChart.Data<>(j, deviceWork/controller.getDevices().size()));
            bs_TimeInSysS.getData().add(new XYChart.Data<>(j, timeInSystem/controller.getClients().size()));
        }

        bs_Rejects.getData().add(bs_RejectsS);
        bs_TimeInSys.getData().add(bs_TimeInSysS);
        bs_DevStress.getData().add(bs_DevStressS);

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

            pn_RejectsS.getData().add(new XYChart.Data<>(j, avgError/controller.getClients().size()));
            pn_DevStressS.getData().add(new XYChart.Data<>(j, deviceWork/controller.getDevices().size()));
            pn_TimeInSysS.getData().add(new XYChart.Data<>(j, timeInSystem/controller.getClients().size()));
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

            sc_RejectsS.getData().add(new XYChart.Data<>(j, avgError/controller.getClients().size()));
            sc_DevStressS.getData().add(new XYChart.Data<>(j, deviceWork/controller.getDevices().size()));
            sc_TimeInSysS.getData().add(new XYChart.Data<>(j, timeInSystem/controller.getClients().size()));

        }

        sc_Rejects.getData().add(sc_RejectsS);
        sc_TimeInSys.getData().add(sc_TimeInSysS);
        sc_DevStress.getData().add(sc_DevStressS);

        pn_Rejects.getData().add(pn_RejectsS);
        pn_TimeInSys.getData().add(pn_TimeInSysS);
        pn_DevStress.getData().add(pn_DevStressS);
    }

}