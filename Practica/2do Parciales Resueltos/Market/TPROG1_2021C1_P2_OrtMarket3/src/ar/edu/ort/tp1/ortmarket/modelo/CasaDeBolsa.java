package ar.edu.ort.tp1.ortmarket.modelo;

import java.util.ArrayList;

import ar.edu.ort.tp1.ortmarket.instrumentos.modelo.Bono;
import ar.edu.ort.tp1.ortmarket.instrumentos.modelo.FondoInversion;
import ar.edu.ort.tp1.ortmarket.instrumentos.modelo.InstrumentoFinanciero;
import ar.edu.ort.tp1.tdas.implementaciones.ColaNodos;
import ar.edu.ort.tp1.tdas.interfaces.Cola;

public class CasaDeBolsa {
	private ListaInstrumentosPorCodigo instrumentos;
	private Cola<Orden> ordenesPendientes;
	private Moneda[] monedas;
	private ListaDeClientesPorCuit clientes;

	public CasaDeBolsa() {
		instrumentos = new ListaInstrumentosPorCodigo();
		ordenesPendientes = new ColaNodos<>();
		clientes = new ListaDeClientesPorCuit();
		monedas = new Moneda[TipoMoneda.values().length];
		this.inicializarMonedas();
		this.inicializarInstrumentos();
	}

	/**
	 * Metodo para dar de alta a un Cliente en una posicion
	 *
	 * @param razonSocial
	 * @param cuit
	 * @param saldoInicial
	 */
	public void altaCliente(String cuit, String razonSocial, int saldoInicial) {
		// TODO: A COMPLETAR TRY CATCH
		try {
			Cliente cliente = clientes.search(cuit);
			if (cliente != null) {
				System.out.println("El cliente ya está registrado");
			}
			clientes.add(new Cliente(cuit, razonSocial, saldoInicial));
		} catch (RuntimeException exception) {
			System.out.println(exception.getMessage());
		}
	}

	/**
	 * Permite cargar ordenes pendientes que luego van a ser procesadas, se debe
	 * validar que el instrumento financiero exista al igual que el cliente
	 *
	 * @param cantidad
	 * @param codigoInstrumento
	 * @param idCliente
	 * @param esVenta
	 */
	public void cargarOrden(int cantidad, String codigoInstrumento, String idCliente, boolean esVenta) {
		// TODO: A completar
		try {
			ordenesPendientes.add(
					new Orden(cantidad, instrumentos.search(codigoInstrumento), clientes.search(idCliente), esVenta));
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
			System.out.println("Cantidad: " + cantidad);
			System.out.println("CodigoInstrumento: " + codigoInstrumento);
			System.out.println("Cliente: " + idCliente);
		}
	}

	public void imprimirHistorialDelCliente() {
		// TODO: A COMPLETAR
		System.out.println("-------------Imprimir Historial por Cliente-------------");
		for (Cliente cliente : clientes) {
			if (cliente != null) {
				cliente.imprimirHistorial();
			}
		}
	}

	public void imprimirListaPrecios() {
		instrumentos.listar();
	}

	private void inicializarInstrumentos() {
		// Metodo creado solo como caso de prueba.
		ArrayList<InstrumentoFinanciero> listaInstrumentos = new ArrayList<>();
		listaInstrumentos.add(new Bono("AL30", "BONO USD 2030 L.A.", "Gobierno Nacional Argentino", 0.75,
				monedas[TipoMoneda.DOLAR.ordinal()], 40000));
		listaInstrumentos.add(new Bono("GP30", "BONOS GLOBALES DE LA REP. ARG. 2030", "Gobierno Nacional Argentino",
				0.063, monedas[TipoMoneda.PESO.ordinal()], 8965555));
		listaInstrumentos.add(new Bono("YPFD", "BONOS YPF DOLAR", "Gobierno Nacional Argentino", 0.063,
				monedas[TipoMoneda.DOLAR.ordinal()], 100000));
		listaInstrumentos.add(new Bono("DOLAR", "BONO USD 2030 L.A.", "Gobierno Nacional Argentino", 0.75,
				monedas[TipoMoneda.DOLAR.ordinal()], 160000));

		FondoInversion fi = new FondoInversion("SE", "Superfondo Equilibrado Dolares", "Banco Deplaza", 10000);

		for (InstrumentoFinanciero instrumentoFinanciero : listaInstrumentos) {
			instrumentos.add(instrumentoFinanciero);
			if (((Bono) instrumentoFinanciero).getMoneda() == monedas[TipoMoneda.DOLAR.ordinal()]) {
				fi.agregarInstrumento(instrumentoFinanciero);
			}
		}
		instrumentos.add(fi);
	}

	private void inicializarMonedas() {
		double[] valores = { 1, 163, 192.5, .83 };
		for (int i = 0; i < monedas.length; i++) {
			monedas[i] = new Moneda(valores[i]);
		}
	}

	/**
	 * Metodo que tiene que procesar todas las ordenes pendiente de la Casa de
	 * Bolsa
	 */
	public void procesarOrdenesDeClientes() {
		System.out.println();
		while (!ordenesPendientes.isEmpty()) {
			Orden orden = ordenesPendientes.remove();
			Cliente cliente = orden.getCliente();
			cliente.procesarOrden(orden);
		}
		System.out.println("Ordenes procesadas");
	}
}