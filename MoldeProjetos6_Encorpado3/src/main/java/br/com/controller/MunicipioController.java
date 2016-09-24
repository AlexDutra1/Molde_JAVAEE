package br.com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.controller.formulario.MunicipioFormulario;
import br.com.modelo.Estado;
import br.com.modelo.Municipio;
import br.com.servico.MunicipioService;

@Named("municipioController")
@ApplicationScoped
public class MunicipioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MunicipioService service;

	@Inject
	private MunicipioFormulario formulario;

	public void acaoAposCadastrar() {

		// this.formulario.getEstadoSelecionado().setMunicipios(null);

		// Associa municipio ao estado
		this.formulario.getMunicipio().setEstado(
		this.formulario.getEstadoSelecionado());

		// Salva no banco de dados o municipio associado ao estado
		this.service.getNegocios().getDao()
		.guardar(this.getFormulario().getMunicipio());

		// Limpa campos do municipio
		this.formulario.setMunicipio(new Municipio());
		this.formulario.setEstadoSelecionado(new Estado());
		this.formulario.setTodosEstados(new ArrayList<Estado>());
	}

	@PostConstruct
	public void init() {

		this.formulario.setTodosEstados(this.service.getEstadoService()
				.getNegocios().getDao().todosEstadosCombo());
	}

	public void pesquisar() {

		System.out.println("TESTANDO MUNICIPIO: "
				+ this.formulario.getMunicipio().getNome());
		System.out.println("TESTANDO ESTADO: "
				+ this.formulario.getEstadoSelecionado().getNome());

		this.formulario.setTodosMunicipios(this.service
				.getNegocios()
				.getDao()
				.pesquisaComCriterios(this.formulario.getMunicipio(),
						this.formulario.getEstadoSelecionado()));

		// ATUALIZA TABELA E CAMPO DE PESQUISA
		RequestContext.getCurrentInstance().update(
				Arrays.asList("formPesquisaMunicipios:tabelaMunicipio"));

		/*
		 * //Limpa campos apos cadastro
		 * this.formulario.getUsuario().setNome("");
		 * this.formulario.getUsuario().setEmail("");
		 * this.formulario.getUsuario().setUsuario("");
		 * 
		 * //ATUALIZA CAMPOS RequestContext.getCurrentInstance().update(
		 * "formPesquisaUsuario:input_nome");
		 * RequestContext.getCurrentInstance()
		 * .update("formPesquisaUsuario:input_usuario");
		 * RequestContext.getCurrentInstance
		 * ().update("formPesquisaUsuario:input_email");
		 */

	}
	
	public String visualizar(Municipio municipioVisualizar) {

		this.formulario.setMunicipio(municipioVisualizar);

		return "visualizarMunicipio";
	}
	
	public void excluirRegistro(Municipio municipioExcluir) {

		this.service.getNegocios().getDao().excluir(municipioExcluir);

	}

	public String preparaEdicao(Municipio municipioEditar) {

		this.formulario.setMunicipio(municipioEditar);

		return "editarMunicipio";
	}

	public void atualizaComboEstado(AjaxBehaviorEvent event) {
		/*
		this.formulario.setTodosEstados(this.service.getEstadoService()
		.getNegocios().getDao().consultaEstadoPeloNomeCombo(this.formulario
		.getEstadoSelecionado()));
		*/
		
		/*
		 * this.formulario.setTodosMunicipios(this.service.getMunicipioService()
		 * .getNegocios().getDao()
		 * .consultaMunicipiosPeloEstado(this.formulario.
		 * getEstadoSelecionado()));
		 */
	}

	// GETS AND SETTERS

	public MunicipioService getService() {
		return service;
	}

	public void setService(MunicipioService service) {
		this.service = service;
	}

	public MunicipioFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(MunicipioFormulario formulario) {
		this.formulario = formulario;
	}

}
