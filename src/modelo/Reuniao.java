package modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

/* DUPLA:  LUÍS HENRIQUE FERREIRA FREIRE(20201370005) E CHRISTOPHER SILVA DE SOUSA(20201370024)*/



public class Reuniao {
	 private int id;
	 private LocalDateTime datahora;
	 private String assunto;
	 private ArrayList<Participante> participantes = new ArrayList<>(); 
	 
	 public Reuniao(int id, String datahora,String assunto ) {
		 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        LocalDateTime time2 = LocalDateTime.parse(datahora, dateTimeFormatter);
		 this.id = id;
		 this.datahora = time2;
		 this.assunto = assunto;
		// this.participantes = participantes;
		 
	 }

	public void adicionar(Participante p) {
		/*boolean flag = true;
		ArrayList<Reuniao> reunioes = p.getReunioes();
		for(Reuniao x: reunioes) {
			if(x.getDatahora().isEqual(this.getDatahora())) {
				System.out.println("DEU RUIIIMM");
				flag = false;
				break;
			}
		}*/
		//if(flag == true) {
		this.participantes.add(p);
	//}
	}
		
	
	
	public String getId() {
		return Integer.toString(id);
	}
	
	public void remover(Participante p) {
		this.participantes.remove(p);
	}
	

	public ArrayList<Participante> getParticipantes() {
		// TODO Auto-generated method stub
		return this.participantes;
	}

	public LocalDateTime getDatahora() {
		// TODO Auto-generated method stub
		return this.datahora;
	}

	public String getAssunto() {
		// TODO Auto-generated method stub
		return this.assunto;
	}
	 
	 
	 
	 
}
