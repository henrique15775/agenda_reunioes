package repositorio;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Set;
import modelo.Participante;
import modelo.Reuniao;


public class Repositorio {
	private TreeMap<String,Participante> participantes = new TreeMap<>();
	private ArrayList<Reuniao> reunioes = new ArrayList<>(); 

	
	public void adicionar(Participante p){
		this.participantes.put(p.getNome(), p);
	}
	public void remover(Participante p){
		this.participantes.remove(p.getNome());
	}
	public Participante localizarParticipante(String nome){
		return this.participantes.get(nome);
	}

	public void adicionar(Reuniao r){
		this.reunioes.add(r);
	}
	public void remover(Reuniao r){
		this.reunioes.remove(r);
	}
	public Reuniao localizarReuniao(int id){
		return this.reunioes.get(id-1);
	}
	
	public ArrayList<Participante> getParticipantes() {
		ArrayList<Participante> lista = new ArrayList<Participante>(this.participantes.values());
		return lista;
	}
	public ArrayList<Reuniao> getReunioes() {
		return this.reunioes;
	}

	
	public int getTotalParticipante(){
		return this.participantes.size();
	}
	public int getTotalReunioes(){
		return this.reunioes.size();
	}
	
	
	
}

