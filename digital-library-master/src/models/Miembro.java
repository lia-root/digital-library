package models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import helpers.ShowMessageHelper;
import helpers.UpsertDataHelper;

public class Miembro extends Cuenta {
    private String status;
    private String fechaVencimiento; //DD/MM/AAAA

    private ArrayList<Reserva> misReservas;

    public Miembro(String usuario, String password, String correo, String tipo) {
        super(usuario, password, correo, tipo);
        this.status = "activo";
        this.misReservas = new ArrayList<>();
    }

    public void historial() {
        if (misReservas == null || misReservas.isEmpty()) {
            System.out.println("No tiene reservas en su historial.");
            return;
        }
        System.out.println("=== Historial de reservas ===");
        for (Reserva r : misReservas) {
            try {
                boolean vigente = r.vigencia_reserva();
                System.out.println("- " + r.getLibro().getAutor() + " | " + r.getFechaInicio() + " a " + r.getFechaFin() + " | Vigente: " + vigente);
            } catch (IllegalArgumentException e) {
                System.err.println("- Reserva con fecha invalida: " + e.getMessage());
            }
        }
    }

    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return this.status; }

    public void setFechaVencimiento(String fecha){this.fechaVencimiento = fecha; }
    public String getFechaVencimiento(){  return this.fechaVencimiento; }

    public void calcularNuevaFechaVencimiento(){
        String nuevaFechaCalculada = null;

        LocalDate hoy = LocalDate.now();
        LocalDate nuevaFecha = hoy.plusDays(30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        nuevaFechaCalculada = nuevaFecha.format(formatter);

        setFechaVencimiento(nuevaFechaCalculada);
    }

    public boolean ValidarAcceso(){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate fecha1 = LocalDate.now();
            LocalDate fecha2 = LocalDate.parse(getFechaVencimiento(), formatter);

            return fecha1.isAfter(fecha2) ? true : false;

        }catch (Exception e){
            return false;
        }
    }
}