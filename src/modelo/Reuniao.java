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
		this.participantes.add(p);
	}
	
	public String getId() {
		return Integer.toString(id);
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
