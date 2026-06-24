/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.services;

import Sipenta.serial.SerialDataHandler;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */

public class SerialService {
    
    private static SerialService instance;
    private SerialPort activePort;
    // Menyimpan handler yang akan menerima data serial
    private final List<SerialDataHandler<String>> handlers = new ArrayList<>();

    // Singleton
    private SerialService() {}
    
    public static synchronized SerialService getInstance() {
        if (instance == null) {
            instance = new SerialService();
        }
        return instance;
    }
    
    /**
     * Menambahkan handler baru
     */
    public void addHandler(SerialDataHandler<String> handler) {
        if (!handlers.contains(handler)) {
            handlers.add(handler);
        }
    }
    
    /**
     * Menghapus handler
     */
    public void removeHandler(SerialDataHandler<String> handler) {
        handlers.remove(handler);
    }
    
    /**
     * Membuka koneksi serial
     */
    public boolean connect(String portName, int baudRate) {

        if (activePort != null && activePort.isOpen()) {
            return true;
        }

        activePort = SerialPort.getCommPort(portName);
        activePort.setBaudRate(baudRate);

        activePort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_SEMI_BLOCKING,
                1000,
                0
        );
        
        if (activePort.openPort()) {
            System.out.println("INFO: Port " + portName + " terbuka.");
            setupListener();
            return true;
        } else {
            System.err.println("ERROR: Gagal membuka port " + portName);
            return false;
        }
    }
    
    /**
     * Listener data serial
     */
    private void setupListener() {

        activePort.addDataListener(new SerialPortDataListener() {
            
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }
            
            @Override
            public void serialEvent(SerialPortEvent event) {

                if (event.getEventType()
                        != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }
                
                try (Scanner scanner =
                        new Scanner(activePort.getInputStream())) {

                    if (scanner.hasNextLine()) {

                        String data =
                                scanner.nextLine().trim();

                        if (!data.isEmpty()) {
                            broadcast(data);
                        }
                    }
                    
                } catch (Exception e) {
                    System.err.println(
                            "Error membaca data serial: "
                            + e.getMessage());
                }
            }
        });
    }
    
    /**
     * Mengirim data ke semua handler
     */
    private void broadcast(String data) {

        for (SerialDataHandler<String> handler : handlers) {
            handler.onDataReceived(data);
        }
    }
    
    /**
     * Menutup koneksi serial
     */
    public void disconnect() {

        if (activePort != null && activePort.isOpen()) {

            activePort.removeDataListener();
            activePort.closePort();

            System.out.println("INFO: Port ditutup.");
        }
    }
    
    /**
     * Status koneksi
     */
    public boolean isConnected() {
        return activePort != null && activePort.isOpen();
    }
}