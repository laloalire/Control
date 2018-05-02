package sample;

public class EnviarDatosArduino {
    public static void enviarDatos(String datos) {
        Thread hilo = new Thread(() ->{
            SerialArduino serial = new SerialArduino();
            serial.initialize();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serial.escribirSerial(datos);
            serial.close();
        });
        hilo.start();
    }
}
