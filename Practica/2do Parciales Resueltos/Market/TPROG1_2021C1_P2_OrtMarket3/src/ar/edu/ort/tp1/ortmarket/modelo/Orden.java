package ar.edu.ort.tp1.ortmarket.modelo;

import ar.edu.ort.tp1.ortmarket.instrumentos.modelo.Cotizable;
import ar.edu.ort.tp1.ortmarket.instrumentos.modelo.InstrumentoFinanciero;

public class Orden implements Cotizable {
    private final int cantidad;
    private final InstrumentoFinanciero instrumentoFinanciero;
    private final Cliente cliente;
    private final boolean esCompra;

    public Orden(int cantidad, InstrumentoFinanciero instrumentoFinanciero, Cliente cliente, boolean esCompra) {
    	if (cantidad < 1 || instrumentoFinanciero == null || cliente == null) {
    		throw new RuntimeException("Datos invalidos en la creacion de la orden");
    	}
        this.cantidad = cantidad;
        this.instrumentoFinanciero = instrumentoFinanciero;
        this.cliente = cliente;
        this.esCompra = esCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public InstrumentoFinanciero getInstrumentoFinanciero() {
        return instrumentoFinanciero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isCompra() {
        return !esCompra;
    }

    @Override
    public double obtenerCotizacion() {
        // @TODO: A COMPLETAR
        return instrumentoFinanciero.obtenerCotizacion() * cantidad;
    }

	public String obtenerDatosParaHistorial() {
		return "Codigo: " + instrumentoFinanciero.getCodigo() + " - " + instrumentoFinanciero.getNombre() + " (" + instrumentoFinanciero.getEmisor() + ")";
	}
}
