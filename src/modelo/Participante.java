package modelo;

import java.util.ArrayList;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

public class Participante {
	
	private String nome;
	private	String email;
	private ArrayList<Reuniao> reunioes = new ArrayList<>();

		
	public Participante(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}
	
	
	public String getNome() {
		return this.nome;
	}


	public void adicionar(Reuniao r) {
		boolean flag = true;
		for(Reuniao x: reunioes) {
			if(x.getDatahora().isEqual(r.getDatahora())) {
				System.out.println("DEU RUIIIMM");
				flag = false;
				break;
			}
		}
		if(flag == true) {
		this.reunioes.add(r);
		}	}

	public void remover(Reuniao r) {
		this.reunioes.remove(r);
	}

	public String getEmail() {
		
		return this.email;
	}
	public ArrayList<Reuniao> getReunioes(){
		return this.reunioes;
	}
	
}

