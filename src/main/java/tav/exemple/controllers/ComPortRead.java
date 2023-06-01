package tav.exemple.controllers;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import tav.exemple.views.VideoView;

public class ComPortRead {
    
    private final VideoView view;

    // Емкость буфера приема
    public final int  BUFSIZE_RD = 4096;
    // Строка приема
    static String readData = null;
    // Флаг открытия порта
    private boolean pOpen = false;
    // Главный счетчик принимаемых байт
    private short comAllCount = 0;
    // Программный приемный буфер
    private byte [] bufrd = new byte [BUFSIZE_RD];
    // Локальный счетчик принимаемых байт
    private int bufrdCount = 0;
    // Счетчик циклов приема
    private int countCom = 0;
//-----------------------------------------------------

    private String comPort = "";
    private SerialPort serialPort;
    private final PortReader portReader = new PortReader();

    public ComPortRead(VideoView view) {
        this.view = view;
        start();
        stop();
    }

    public void start (){

        comPort = (String) view.getChoiceNumberPort().getValue();
        serialPort = new SerialPort(comPort);

        view.getChoiceNumberPort().setOnAction((event) -> {
            comPort = (String) view.getChoiceNumberPort().getValue();
        });

        view.getReceivData().setOnAction(event -> {
            if(serialPort.isOpened() == false)
            {
                try {
                    serialPort = new SerialPort(comPort);
                    view.getStatePort().setText(comPort + " открыт");
                    view.getStatePort().setStyle("-fx-text-fill: green");

                    //Открываем порт
                    serialPort.openPort();
                    pOpen = true;
                    //Выставляем параметры
                    serialPort.setParams(SerialPort.BAUDRATE_115200,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    //Устанавливаем ивент лисенер и маску
                    serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                    pOpen = false;
                    view.getStatePort().setText(comPort + " занят");
                    view.getStatePort().setStyle("-fx-text-fill: purple");
                }
            }
        });
    }

    public void stop (){
        view.getStopDataData().setOnAction(event -> {
            if(serialPort.isOpened() == true) {
                try {
                    serialPort.purgePort(SerialPort.PURGE_RXCLEAR);
                    serialPort.closePort();
                    view.getStatePort().setText(comPort + " закрыт");
                    view.getStatePort().setStyle("-fx-text-fill: red");
                    readData = "";
                } catch (SerialPortException ex) {
                    //Logger.getLogger(tav.exemple.controllers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    class PortReader implements SerialPortEventListener {

        short comCount;
        short comLenght = 0;
        private byte[] arrReadComByte = new byte [BUFSIZE_RD];


        public void serialEvent(SerialPortEvent event) {

            if (pOpen == true) {

                if (event.isRXCHAR() && event.getEventValue() > 0) {
                    // Получаем ответ от устройства, обрабатываем данные и т.д.
                    try {
                        // Количество принятых байт в одном событии
                        comCount = (short) serialPort.getInputBufferBytesCount();
                        // Счетчик-накопитель принятых байт
                        comAllCount = (short) (comAllCount + comCount);
                        // Байты из буфера COM в матрицу arrReadComByte
                        arrReadComByte = serialPort.readBytes(comCount);
                        // Вход если в буфере COM имеются данные
                        if (comCount > 0) {
                            // Поиск синхросимволов и данных о длине пакета
                            for (int i = 0; i < comCount; i++) {
                                System.out.println(i);
                                if (arrReadComByte[i] == (byte) 0xa1 && arrReadComByte[i + 1] == (byte) 0xb2) {
                                    comLenght = (short) (((arrReadComByte[i + 2] & 0x00ff) << 8 | (arrReadComByte[i + 3] & 0x00ff)) + 4);
                                    System.out.println("comLenght" + comLenght);
                                } else
                                    System.out.println("break");
                                    break;
                            }
                        }
                        // Заполнение приемного буфера и строки readData
                        for (int i = 0; i < comCount; i++) {
                            bufrd[bufrdCount] = arrReadComByte[i];
                            System.out.println(arrReadComByte[i]);
                            readData = readData + String.format("%02X ", bufrd[bufrdCount]);
                            bufrdCount++;
                        }
                        // Принят весь павкет
                        if (comAllCount >= comLenght) {
                            countCom++;
                            view.getCountData().setText("Посылки: " + String.format("%d ", countCom));
                            // всего считано байт
                            view.getReadBytes().setText("Считано байт: " + Integer.toString(comAllCount));
                            view.getInfoBytes().setText("Иформационные байты:  " + Integer.toString(comAllCount - 4));
                            // обнулить глобальный счетчик байт
                            comAllCount = 0;
                            view.getInputTextArea().setText(null);
                            // вывод принятых данных в окно input
                            onDataReceived(readData);
                            // очистка строки приема
                            readData = "";
                            bufrdCount = 0;
                        }
                    } catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
        // метод отображения данных в input
        public void onDataReceived(String readData) {
            view.getInputTextArea().appendText(readData);
        }

    }

}
