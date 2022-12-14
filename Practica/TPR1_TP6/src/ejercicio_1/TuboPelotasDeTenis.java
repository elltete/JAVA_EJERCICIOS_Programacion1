package ejercicio_1;

import implementaciones.PilaNodos;
import interfaces.Pila;

public class TuboPelotasDeTenis {
	
	private Pila<PelotaDeTenis> pelotas;
	
	public TuboPelotasDeTenis(int cantidadDeElementos) {
		pelotas = new PilaNodos<>(cantidadDeElementos);
	}

	public boolean estaLleno() {
		return pelotas.isFull();
	}

	public boolean estaVacio() {
		return pelotas.isEmpty();
	}

	public PelotaDeTenis extraer() {
		return pelotas.pop();
	}

	public void guardar(PelotaDeTenis pelota) {
		pelotas.push(pelota);
	}

}
