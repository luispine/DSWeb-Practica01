package org.uv.dsweb.practica01.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 15-dy2xxLapDeLuis
 */
public class ClienteSocket extends Thread {

    private Socket cliente;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public ClienteSocket(Socket cliente) {
        this.cliente = cliente;
        InputStreamReader isr = null;

        try {
            isr = new InputStreamReader(this.cliente.getInputStream());
            reader = new BufferedReader(isr);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

        OutputStreamWriter osw = null;

        try {
            osw = new OutputStreamWriter(cliente.getOutputStream());
            writer = new BufferedWriter(osw);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                String operacion = reader.readLine();
                System.out.println("Operación-->" + operacion);
                
                int indexSuma = operacion.indexOf("+");
                String resultado = "";
                
                if(indexSuma != -1) {
                    String a = operacion.substring(0, indexSuma);
                    String b =operacion.substring(indexSuma + 1, operacion.length());
                    
                    int c = Integer.parseInt(a) + Integer.parseInt(b);
                    resultado ="a: " + a + ", b: " + b + ",c: " + String.valueOf(c);
                } else {
                    resultado = "Error...";
                }
                
                writer.write(resultado);
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
