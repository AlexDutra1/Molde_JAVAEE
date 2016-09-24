package br.com.persistencia.implementacao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.modelo.Estado;
import br.com.persistencia.interfaces.EstadoGerenciable;

public class EstadoDAO implements EstadoGerenciable{

	private EntityManager manager;
	
	@Inject
	public EstadoDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void guardar(Estado estado) {

		EntityTransaction trx = this.manager.getTransaction();
		trx.begin();
		
		estado=this.manager.merge(estado);
		this.manager.merge(estado);
		
		trx.commit();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Estado> consultaEstadoPeloNomeCombo(Estado estado) {

		Query consulta=manager.createQuery("select a from Estado a where nome='"
		+estado.getNome()+"'",Estado.class);
		return consulta.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Estado> todosEstadosCombo() {

		Query consulta=manager.createQuery("select a from Estado a",Estado.class);
		return consulta.getResultList();
		
	}

	public List<Estado> pesquisaComCriterios(Estado estado) {
		// CRITERIA JPA

				CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();
				CriteriaQuery<Estado> criteriaQuery = criteriaBuilder
						.createQuery(Estado.class);
				Root<Estado> root = criteriaQuery.from(Estado.class);

				List<Predicate> condicoes = new ArrayList<Predicate>();

				// CONSULTA POR NOME DO ESTADO

				if (!estado.getNome().equals("")) {
					Path<String> atributoNome = root.get("nome");
					Predicate whereNome = criteriaBuilder.like(atributoNome,
							estado.getNome());
					condicoes.add(whereNome);
				}

				// CONSULTA POR SIGLA DO ESTADO

				if (!estado.getSigla().equals("")) {
					Path<String> atributoSigla = root.get("sigla");
					Predicate whereSigla = criteriaBuilder.like(atributoSigla,
							estado.getNome());
					condicoes.add(whereSigla);
				}

				// COMENTAMOS POR DOIS MOTIVOS
				// ARRUMAR ERROS DEPOIS-ADPATAR PARA USO NO FUTUTO
				// REFERENCIA

				Predicate[] condicoesComoArray = condicoes
						.toArray(new Predicate[condicoes.size()]);
				Predicate todasCondicoes = criteriaBuilder.and(condicoesComoArray);
				criteriaQuery.where(todasCondicoes);

				TypedQuery<Estado> query = this.manager.createQuery(criteriaQuery);

				List<Estado> list = query.getResultList();

				for (Estado usuario2 : list) {
					System.out.println("RESULTADO CRITERIA: " + usuario2.getNome());
					System.out.println("RESULTADO CRITERIA: " + usuario2.getSigla());
				}

				return list;
	}
	
	//EXCLUI CLIENTE
	public void excluir(Estado estadoExcluir) {
		
		EntityTransaction trx = this.manager.getTransaction();
		trx.begin();
		
		estadoExcluir=manager.merge(estadoExcluir);
		manager.remove(estadoExcluir);
		
		trx.commit();
		
	}

}
