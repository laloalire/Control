package sample;

public class ArduinoManager {
    private static  SerialArduino serial;

    public static void iniciarArduino(){
        serial = new SerialArduino();
        serial.initialize();
    }
    public static void enviarDatos(String datos) {
        serial.escribirSerial(datos);
    }

    public static void cerrarConexion() {
        serial.close();
    }
}
