package GUI.view;

import GUI.Waveform;
import GUI.actions.AutoMode;
import GUI.actions.NextStep;
import program.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Форма тестирования модели в шаговом режиме

public class StepMode {
  final Controller controller;
  final Waveform waveform;

  public StepMode(final Controller controller,
                  final Waveform waveform) {
    this.controller = controller;
    this.waveform = waveform;
  }

  public void start() {
    JFrame currentFrame = new JFrame() {
    };
    currentFrame.setVisible(true);
    currentFrame.setTitle("Пошаговый режим");
    currentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    currentFrame.setSize(1550, 850);
    currentFrame.setLocation(280, 150);
    currentFrame.setLayout(null);

    String[] bufferTableColumnNames = {"Index","Order"};
    String[][] bufferTableData = new String[Controller.statisticsPub.getBufferSize()][3];
    for (int i = 0; i < controller.getBuffer().getOrders().size(); i++) {
      bufferTableData[i][0] = String.valueOf(i);
    }
  // bufferTableData[0][1] = "";
    JTable bufferTable = new JTable(new DefaultTableModel(bufferTableData, bufferTableColumnNames));
    DefaultTableModel bufferTableModel = (DefaultTableModel) bufferTable.getModel();
    bufferTable.setMaximumSize(new Dimension(200, 200));

    String[] canselTableColumnNames = {"order"};
    String[][] canselTableData = new String[1][1];
    JTable canselTable = new JTable(new DefaultTableModel(canselTableData, canselTableColumnNames));
    DefaultTableModel canselTableModel = (DefaultTableModel) canselTable.getModel();
    canselTable.setMaximumSize(new Dimension(200, 200));

    String[] devicesTableColumnNames = {"Index", "Order"};
    String[][] devicesTableData = new String[Controller.statisticsPub.getDevicesCount()][2];
    for (int i = 0; i < Controller.statisticsPub.getDevicesCount(); i++) {
      devicesTableData[i][0] = String.valueOf(i);
    }
    JTable devicesTable = new JTable(new DefaultTableModel(devicesTableData, devicesTableColumnNames));
    DefaultTableModel devicesTableModel = (DefaultTableModel) devicesTable.getModel();
    devicesTable.setMaximumSize(new Dimension(200, 200));

    String[] calendarTableColumnNames = {"Время", "Информация", "Статус заказа", "Id заказа", "Выполнено", "Отменено"};
    String[][] calendarTableData = new String[0][5];
    JTable calendarTable = new JTable(new DefaultTableModel(calendarTableData, calendarTableColumnNames));
    DefaultTableModel resultsTableModel = (DefaultTableModel) calendarTable.getModel();
    calendarTable.getColumnModel().getColumn(0).setMinWidth(150);
    calendarTable.getColumnModel().getColumn(2).setMinWidth(70);

    JButton buttonNext = new JButton(
      new NextStep(controller, bufferTableModel, resultsTableModel,
                         devicesTableModel, canselTableModel, waveform));
    buttonNext.setText("Следующий шаг");
    buttonNext.setForeground(Color.GRAY);
    currentFrame.getContentPane().add(buttonNext).setBounds(820,750,150,30);


    currentFrame.getContentPane().add(new JScrollPane(waveform.getJPanel()))
            .setBounds(820, 20, 690, 500);

    JLabel bufferLbl = new JLabel("Буфер");
    bufferLbl.setForeground(Color.BLUE);
    currentFrame.getContentPane().add(bufferLbl)
            .setBounds(820, 530, 300, 15);
    currentFrame.getContentPane().add(new JScrollPane(bufferTable))
            .setBounds(820, 550, 300, 200);

    JLabel deviceLbl = new JLabel("Приборы");
    deviceLbl.setForeground(Color.BLUE);
    currentFrame.getContentPane().add(deviceLbl)
            .setBounds(1140, 530, 300, 15);
    currentFrame.getContentPane().add(new JScrollPane(devicesTable))
            .setBounds(1140, 550, 300, 100);

    JLabel cancelLbl = new JLabel("Отказ");
    cancelLbl.setForeground(Color.BLUE);
    currentFrame.getContentPane().add(cancelLbl)
            .setBounds(1140, 650, 300, 15);
    currentFrame.getContentPane().add(new JScrollPane(canselTable))
            .setBounds(1140, 670, 300, 50);

    JLabel infoLbl = new JLabel("История операций");
    infoLbl.setForeground(Color.BLUE);
    currentFrame.getContentPane().add(infoLbl)
            .setBounds(10, 20, 300, 15);
    currentFrame.getContentPane().add(new JScrollPane(calendarTable))
            .setBounds(10, 40, 800, 750);

    currentFrame.revalidate();
  }
}
