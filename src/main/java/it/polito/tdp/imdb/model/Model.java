package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;


import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
ImdbDAO dao;
Graph<Director, DefaultWeightedEdge> grafo;
Map<Integer, Director> idMap;
List<Director> itera= new ArrayList<Director>();
List<Director> risultato = new ArrayList<Director>();
int maxAttori=-1;

public Model() {
	dao= new ImdbDAO();
	idMap = new HashMap<Integer, Director>();
}
	
public void creaGrafo (int anno) {
	this.grafo= new SimpleWeightedGraph<> (DefaultWeightedEdge.class);
	List<Director> dir= dao.getDirectors(anno, idMap);
	Graphs.addAllVertices(this.grafo, idMap.values());
	for (CoppiaDirettori cd: dao.getCoppiaDirettori(anno, idMap)) {
		Graphs.addEdgeWithVertices(this.grafo, cd.getD1(), cd.getD2(), cd.getNum());
		
	}
	
	
}
	
	public List<Integer> annida2004to2006() {
		List<Integer> anni= new ArrayList<Integer>();
		int anno1=2004;
		anni.add(anno1);
		int anno2=2005;
		anni.add(anno2);
		int anno3=2006;
		anni.add(anno3);
		return anni;
	}

	public int nVertici() {
		return this.grafo.vertexSet().size();
		
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	
	}
	
	public List<Director> getRegistiGrafo () {
		List<Director> vertici = new ArrayList<Director>(this.grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
	
	public int pesoGrafo(Director d1, Director d2) {
		return dao.getPeso(d1, d2);
	}
	
	public List<Director> getAdiacenza (Director vertice) {
		List<Director> vicini = new LinkedList<Director>(Graphs.neighborListOf(this.grafo, vertice));
		return vicini;
	}
	
	public List<Director> trovaEventi (Director d,int anno, int c) {
		maxAttori=c;
		List<Director>parziale = new ArrayList<Director>();
		itera= new ArrayList<Director>(this.getAdiacenza(d));
		trovaEventiRicorsivo(parziale, 0);
		return risultato;
	}
	
	private void trovaEventiRicorsivo(List<Director> parziale, int livello) {
		if (livello==itera.size()) {
			if (this.cercaTotale(parziale)>this.cercaTotale(risultato))
				risultato=new ArrayList <>(parziale);
			return;
		}
		else {
			for (int i=livello; i<itera.size(); i++) {
				parziale.add(itera.get(i));
				if (! this.superaPeso(parziale)) {
					trovaEventiRicorsivo(parziale, i);
				}
				parziale.remove(itera.get(i));
			}
		}
	}

	private boolean superaPeso(List<Director> parziale) {
		if (parziale.size()==0)
			return false;
		int sum=0;
	for (Director d: parziale) {
		sum+=d.peso;
	}
	if (sum>maxAttori)
		return false;
	else 
		return true;
	}

	private int cercaTotale(List<Director> parziale) {
		
		if (parziale.size()==0) 
			return 0;
		int i=0;
		for (Director d: parziale) {
			i=d.getPeso();
		}
		return i;
	}

	
}

