package br.com.persistencia.interfaces;

import java.util.List;

import br.com.modelo.Estado;

public interface EstadoGerenciable {

	public List <Estado> todosEstadosCombo();

	void guardar(Estado estado);

	List<Estado> consultaEstadoPeloNomeCombo(Estado estado);
	
}
