package br.com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.controller.formulario.ClienteFormulario;
import br.com.modelo.Cliente;
import br.com.modelo.Endereco;
import br.com.modelo.Estado;
import br.com.modelo.Interesse;
import br.com.modelo.Municipio;
import br.com.modelo.Telefone;
import br.com.servico.ClienteService;


@Named("clienteController")
@ApplicationScoped
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String abreIncluir ="incluirClientes";
	private static final String abrePesquisar="pesquisarClientes";

	@Inject
	private ClienteService service;
	
	@Inject
	private ClienteFormulario formulario;
	
	public String abreIncluir(){
		
		limparFormulario();
		this.formulario.setTodosEstados(this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo());
		this.formulario.setTodosMunicipios(this.getService().getMunicipioService().getNegocios().getDao().consultaTodosMunicipios());
		
		return abreIncluir;
	}
	
	public String abrePesquisa(){
		
		limparFormulario();
		this.formulario.setTodosEstados(this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo());
		this.formulario.setTodosMunicipios(this.getService().getMunicipioService().getNegocios().getDao().consultaTodosMunicipios());
		this.formulario.setTodosClientes(this.getService().getNegocios().getDao().consultarTodosDAO());
		
		return "pesquisarClientes";
	}
	
	public void limparFormulario(){
		this.formulario.setCliente(new Cliente());
		this.formulario.setEndereco(new Endereco());
		this.formulario.setInteresse(new Interesse());
		this.formulario.setTelefone(new Telefone());
		this.formulario.setListaTelefones(new ArrayList<Telefone>());
		this.formulario.setListaInteresses(new ArrayList<Interesse>());
		this.formulario.setListaPreferencias(new ArrayList<String>());
		this.formulario.setEstadoSelecionado(new Estado());
		this.formulario.setMunicipioSelecionado(new Municipio());
	}
	
	public void acaoAposAlterar(){
		
		//Salva no banco de dados
		this.service.getNegocios().getDao().guardar(this.getFormulario().getCliente());
		this.limparFormulario();
		
	}
	
	/*
	@PostConstruct
	public void init(){
		
		this.formulario.setTodosEstados(this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo());
		this.formulario.setTodosMunicipios(this.getService().getMunicipioService().getNegocios().getDao().consultaTodosMunicipios());
		this.formulario.setTodosClientes(this.getService().getNegocios().getDao().consultarTodosDAO());
	
	}
	*/
	public void acaoAposCadastrar(){
		
		//Configura genero ao cliente
		this.formulario.getCliente().setGenero(this.formulario.getEnumGenero());
	
		//Configura os telefones no cliente
		this.formulario.getCliente().setTelefone(this.formulario.getListaTelefones());
		
		//Configura endereco do usuario
		this.formulario.getCliente().setEndereco(this.formulario.getEndereco());

		//Configura o estado no endereco
		this.formulario.getCliente().getEndereco().setEstado(this.formulario.getEstadoSelecionado());
	
		//Configura o municipio no estado
		//this.formulario.getCliente().getEndereco().getEstado().(this.formulario.getMunicipioSelecionado());
		System.out.println("MUNICIPIO SELECIONADO: "+this.formulario.getMunicipioSelecionado().getNome());
		
		//Configura os interesses no cliente
		this.formulario.getCliente().setInteresses(this.formulario.getListaInteresses());
		
		//Salva no banco de dados
		this.service.getNegocios().getDao().guardar(this.getFormulario().getCliente());
		
		//Cria outro objeto cliente para ser preenchido novamente
		this.formulario.setCliente(new Cliente());
		this.formulario.setEndereco(new Endereco());
		this.formulario.setInteresse(new Interesse());
		this.formulario.setTelefone(new Telefone());
		this.formulario.setListaTelefones(new ArrayList<Telefone>());
		this.formulario.setListaInteresses(new ArrayList<Interesse>());
		this.formulario.setListaPreferencias(new ArrayList<String>());
		this.formulario.setEstadoSelecionado(new Estado());
		this.formulario.setMunicipioSelecionado(new Municipio());
		
	}
	
	public void adicionaTelefone(){
	
		this.formulario.getListaTelefones().add(this.formulario.getTelefone());
		
		//Cria outro objeto telefone para ser preenchido
		this.formulario.setTelefone(new Telefone());
	
	}
	
	public void adicionaInteresse(){
		
		this.formulario.getListaInteresses().add(this.formulario.getInteresse());
		
		//Cria outro objeto interesse para ser preenchido
		this.formulario.setInteresse(new Interesse());
	}
	
	public void excluiTelefone(String ddd, String numero){
		
		List <Telefone> lista= this.formulario.getListaTelefones();
		
		for (Telefone telefone : lista) {
			
			if(ddd.equals(telefone.getDdd())){
				if(numero.equals(telefone.getNumero())){
					lista.remove(telefone);
				}
			}
		}
		//ABAIXO não está fazendo diferença
		//RequestContext.getCurrentInstance().update(Arrays.asList("formCadastroCliente:tabelaTelefones"));
	}
	
	public void excluiInteresse(String nomeInteresse){
	
		List <Interesse> lista= this.formulario.getListaInteresses();
	
		for (Interesse interesse : lista) {
			
			if(nomeInteresse.equals(interesse.getNome())){
				lista.remove(lista.lastIndexOf(interesse));
			}
		}
		//ABAIXO não está fazendo diferença
		//RequestContext.getCurrentInstance().update(Arrays.asList("formCadastroCliente"));
	}
	
	public void atualizaComboMunicipio(AjaxBehaviorEvent event){
		
		this.formulario.setMunicipiosDoEstado(this.service.getMunicipioService()
				.getNegocios().getDao()
				.consultaMunicipiosPeloEstado(this.formulario.getEstadoSelecionado()));
		
	}
	
	public void excluirRegistro(Cliente clienteExcluir){
		
		this.service.getNegocios().getDao().excluir(clienteExcluir);
	
	}
	
	
	public String preparaEdicao(Cliente clienteEditar){
		
		this.formulario.setCliente(clienteEditar);

		return "editarCliente";
	}
	
	public String visualizaTelefones(Cliente cliente){

		//Consulta telefones por id do cliente Configura lista do formulario
		this.formulario.setListaTelefones(this.service.getTelefoneService().getNegocios().getDao().consultarPorIdCliente(cliente.getIdCliente()));
		
		return "visualizaTelefones.xhtml";
	}
	
	public String visualizaInteresses(Cliente cliente){
		
		this.formulario.setCliente(cliente);
		
		//Consulta telefones por id do cliente Configura lista do formulario
		this.formulario.setListaInteresses(this.service.getInteresseService().getNegocios().getDao().consultarPorIdCliente(cliente.getIdCliente()));
				
		return "visualizaInteresses.xhtml";
	}

	//CONSULTAS
	public void testePesquisaDeClientePorMunicipio(){
		
		System.out.println("Municipio Controle: "+this.formulario.getMunicipioSelecionado().getNome());
		
		this.formulario.setTodosClientes(this.service.getNegocios().getDao().consultaClientePorMunicipio(this.formulario.getMunicipioSelecionado()));
		
		List<Cliente> lista=this.formulario.getTodosClientes();
		
		
		for (Cliente cliente : lista) {
			System.out.println("Lista-controle: "+cliente.getNome());
		}
		
		RequestContext.getCurrentInstance().update(Arrays.asList("formPesquisaCliente:tabelaClientes"));
		RequestContext.getCurrentInstance().update("formPesquisaCliente:input_nome");
	}
	
	
	public void pesquisar(){
		
		//System.out.println("NO PESQUISAR: "+this.formulario.getMunicipioSelecionado().getNome());
		
		//Faz consulta pelo nome
		this.formulario.setTodosClientes(this.service
				.getNegocios().getDao()
				.consultaPorCriterios(
						this.formulario.getCliente(),
						this.formulario.getEnumGenero(),
						this.formulario.getTelefone(),
						this.formulario.getEndereco(),
						this.formulario.getEstadoSelecionado(),
						this.formulario.getMunicipioSelecionado()
						));
		/*
		System.out.println("TESTE 1: "+this.formulario.getCliente().getDataNascimento());
		
		if(this.formulario.getCliente().getDataNascimento()==null){
			System.out.println("É NULO "+this.formulario.getCliente().getDataNascimento());
		}else{
			System.out.println("NÃO É NULO "+this.formulario.getCliente().getDataNascimento());
		}
		*/
		//.name da nullpointer
		//System.out.println("TESTE 2: "+this.formulario.getEnumGenero().name());
		
		//ATUALIZA TABELA E CAMPO DE PESQUISA
		RequestContext.getCurrentInstance().update(Arrays.asList("formPesquisaCliente:tabelaClientes"));
		RequestContext.getCurrentInstance().update("formPesquisaCliente:input_nome");
		
		//Limpa campos apos cadastro
		this.formulario.getCliente().setNome("");
	
	}
	
	public ClienteService getService() {
		return service;
	}




	public void setService(ClienteService service) {
		this.service = service;
	}




	public ClienteFormulario getFormulario() {
		return formulario;
	}




	public void setFormulario(ClienteFormulario formulario) {
		this.formulario = formulario;
	}

	
	




}
