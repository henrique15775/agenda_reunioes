package fachada;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import modelo.Participante;
import modelo.Reuniao;
import repositorio.Repositorio;



public class Fachada {
	private static Repositorio repositorio = new Repositorio();	//existe somente um repositorio
	
	public static ArrayList<Participante> listarParticipantes() {
		return repositorio.getParticipantes();
	}
	public static ArrayList<Reuniao> listarReunioes() {
		return repositorio.getReunioes();
	}

	public static Participante criarParticipante(String nome, String email) {
		nome = nome.trim();
		email = email.trim();
		
		Participante b = new Participante(nome,email);
		if(repositorio.localizarParticipante(nome) ==  null) {
			repositorio.adicionar(b);
		}
		
		return b;
		
	}

	public static Reuniao criarReuniao (String datahora, String assunto, ArrayList<String> nomes) {
		assunto = assunto.trim();
		//boolean
		int num_id = 1;
		int pos = 1;
		Reuniao  lastreuniao = null;
		
		for(Reuniao tofind:  repositorio.getReunioes()) {
			
			if(pos == repositorio.getTotalReunioes()) {
				lastreuniao = tofind;
				break;
			}
			pos += 1;
		}
		if(lastreuniao != null) {
			 num_id = Integer.parseInt(lastreuniao.getId()) + 1;
		}
		Reuniao a = new Reuniao(num_id,datahora,assunto);
		/*for(Reuniao x: repositorio.getReunioes()) {
			if(a.getDatahora().equals(x.getDatahora())) {
				System.out.println("Deu ruimmm");
				flag = false;
				break;
			}
		}*/
		
		if(nomes.size() >= 2) {
			for(String x:nomes) {
			boolean flag = true;
			Participante found = repositorio.localizarParticipante(x);
			
			if(found != null ) {
				/*for(Reuniao y: found.getReunioes()) {
					if(y.getDatahora().equals(a.getDatahora())) {
						flag = false;
						break; 
						
					}
				}
				*/
				
				for(Reuniao y: found.getReunioes()) {
					System.out.println("\n----Diferença entre duas datas");
					LocalDate dataToVerify = a.getDatahora().toLocalDate();
					LocalDate nascimento= dataToVerify;    
					Period p = Period.between(y.getDatahora().toLocalDate(),nascimento);
					int c = p.getYears();
					int m = p.getMonths();
					int d = p.getDays();
					
					if(d == 0) {
						
						LocalTime meiodia = a.getDatahora().toLocalTime();		//formato default
						//meiodia = LocalTime.of(12,0,0);						//alternativa
						Duration dur = Duration.between(y.getDatahora().toLocalTime(),meiodia);
						long horas = dur.toHours();
						long minutos = dur.toMinutes();
						
						if((minutos < 120 && minutos >= 0) ||  (minutos > -120 && minutos <= 0)) {
							
								flag = false;
							
							
						}
						
						
					}else {
						continue;
					}
						
					
					
					
				}
				
				
				if(flag == true) {
				a.adicionar(found);
				found.adicionar(a);
				}
				//repositorio.adicionar(found);
				}
			
			} // for
			
		}
		
		if(a.getParticipantes().size() >= 2) {
		
		repositorio.adicionar(a);
		}else {
			a = null;
		}
		
		
		
	
		
		return a;
	}

	public static void 	adicionarParticipanteReuniao(String nome, int id) throws Exception {
		nome = nome.trim();
		boolean flag = true;
		//localizar participante e reuniao no repositorio e adicioná-lo à reunião
		//enviarEmail(emaildestino, assunto, mensagem)

Participante p = repositorio.localizarParticipante(nome);
if(p==null)
  throw new Exception("nao pode adicionar - participante inexistente");

Reuniao r = repositorio.localizarReuniao(id);
if(r==null)
	throw new Exception("nao pode adicionar - reuniao inexistente");

for(Reuniao x: p.getReunioes()) {
	
	LocalDate dataToVerify = x.getDatahora().toLocalDate();
	LocalDate nascimento= dataToVerify;    
	Period periodo = Period.between(r.getDatahora().toLocalDate(),nascimento);
	int c = periodo.getYears();
	int m = periodo.getMonths();
	int d = periodo.getDays();
	
	System.out.println("periodo= "+c+"anos, "+m+"meses, "+d+"dias ");
	if(d == 0) {
		System.out.println("\n----Diferença entre horarios");
		LocalTime meiodia = x.getDatahora().toLocalTime();		//formato default
								//alternativa
		Duration dur = Duration.between(r.getDatahora().toLocalTime(),meiodia);
		long horas = dur.toHours();
		long minutos = dur.toMinutes();
		System.out.println("duração em horas=" + horas+"  ou em minutos="+minutos);
		if((minutos < 120 && minutos >= 0) ||  (minutos > -120 && minutos <= 0)) {
			flag = false;
			
			
		}
		
		
	}else {
		continue;
	}
	
	
	
	/*
	if(x.getDatahora().equals(r.getDatahora())) {
		System.out.println("DEU RUIMMMM");
		flag = false;
		break;
	}*/
}
if(flag == true) {
r.adicionar(p);
p.adicionar(r);
//enviarEmail(p.getEmail(),"Reuniao de " + r.getAssunto(),"Você foi inserido na reunião" + r.getAssunto() +" de " + r.getDatahora().toString());
}
	}
	public static void 	removerParticipanteReuniao(String nome, int id) throws Exception {
		nome = nome.trim();
		Reuniao r = repositorio.localizarReuniao(id);
		if(r==null)
			throw new Exception("nao pode adicionar - reuniao inexistente");

		//localizar participante e reuniao no repositorio e removê-lo da reunião
		Participante p = repositorio.localizarParticipante(nome);
		if(p==null)
			  throw new Exception("nao pode adicionar - participante inexistente");
		
		r.remover(p);
		p.remover(r);
		if(r.getParticipantes().size()  < 2) {
			cancelarReuniao(Integer.parseInt(r.getId()));
		}
		//enviarEmail(p.getEmail(), r.getAssunto(), "Você foi removido da reunião de " + r.getDatahora().toString() );
	}
	public static void	cancelarReuniao(int id) throws Exception {
		//localizar a reunião no repositório, removê-la de seus participantes e
		Reuniao r = repositorio.localizarReuniao(id);
		
		for(Participante x: r.getParticipantes()) {
			
				
				x.remover(r);
			
		}
		repositorio.remover(r);
		
		//removê-la do repositorio
		//enviarEmail(emaildestino, assunto, mensagem)

	}

	public static void	inicializar() throws Exception {
		//ler dos arquivos textos (formato anexo) os dados dos participantes e 
		//das reuniões e adicioná-los ao repositório
		//ler dos arquivos textos (formato anexo) os dados dos participantes e 
				//das reuniões e adicioná-los ao repositório

				Scanner arquivo1=null;
				Scanner arquivo2=null;
				try{
					arquivo1 = new Scanner( new File("C:\\Users\\2001h\\eclipse-workspace\\agenda_reuniao\\src\\arquivos\\participantes.txt"));
				}catch(FileNotFoundException e){
					throw new Exception("arquivo de participantes inexistente:");
				}
				try{
					arquivo2 = new Scanner( new File("C:\\Users\\2001h\\eclipse-workspace\\agenda_reuniao\\src\\arquivos\\reunioes.txt"));
				}catch(FileNotFoundException e){
					throw new Exception("arquivo de reunioes inexistente:");
				}

				String linha;	
				String[] partes;	
				String nome, email;
				while(arquivo1.hasNextLine()) {
					linha = arquivo1.nextLine().trim();		
					partes = linha.split(";");	
					nome = partes[0];
					email = partes[1];
					Participante p = new Participante(nome,email);
					repositorio.adicionar(p);
				} 
				arquivo1.close();			

				String id, datahora, assunto;
				String[] nomes;
				while(arquivo2.hasNextLine()) {
					linha = arquivo2.nextLine().trim();		
					partes = linha.split(";");	
					id = partes[0];
					datahora = partes[1];
					assunto = partes[2];
					nomes = partes[3].split(",");		
					Reuniao r = new Reuniao(Integer.parseInt(id), datahora, assunto);
					for(String n : nomes){
						Participante p = repositorio.localizarParticipante(n);
						p.adicionar(r);
						r.adicionar(p);
					}
					repositorio.adicionar(r);		
				} 
				arquivo2.close();	
		
	}
	public static void	finalizar() throws Exception {
		//gravar nos arquivos textos (formato anexo) os dados dos participantes e 
		//das reuniões que estão no repositório

		FileWriter arquivo1=null;
		FileWriter arquivo2=null;
		try{
			arquivo1 = new FileWriter( new File("participantes.csv") ); 
		}catch(IOException e){
			throw new Exception("problema na criação do arquivo de participantes");
		}
		try{
			arquivo2 = new FileWriter( new File("reunioes.csv") ); 
		}catch(IOException e){
			throw new Exception("problema na criação do arquivo de reunioes");
		}

		for(Participante p : repositorio.getParticipantes()) {
			arquivo1.write(p.getNome() +";" + p.getEmail() +"\n");	
		} 
		arquivo1.close();			

		ArrayList<String> lista;
		String nomes;
		for(Reuniao r : repositorio.getReunioes()) {
			lista = new ArrayList<>();
			for(Participante p : r.getParticipantes()) {
				lista.add(p.getNome());
			}
			nomes = String.join(",", lista);
			arquivo2.write(r.getId()+";"+r.getDatahora()+";"+r.getAssunto()+";"+nomes+"\n");	
		} 
		arquivo2.close();	
	}
	
	
	
	/**************************************************************
	 * 
	 * MÉTODO PARA ENVIAR EMAIL, USANDO UMA CONTA (SMTP) DO GMAIL
	 * ELE ABRE UMA JANELA PARA PEDIR A SENHA DO EMAIL DO EMITENTE
	 * ELE USA A BIBLIOTECA JAVAMAIL 1.6.2
	 * Lembrar de: 
	 * 1. desligar antivirus e de 
	 * 2. ativar opcao "Acesso a App menos seguro" na conta do gmail
	 * 
	 **************************************************************/
	public static void enviarEmail(String emaildestino, String assunto, String mensagem){
		try {
			//configurar emails
			String emailorigem = "luishenriquecuzaum@gmail.com";
			String senhaorigem = pegarSenha();

			//Gmail
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");

			Session session;
			session = Session.getInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailorigem, senhaorigem);
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setSubject(assunto);		
			message.setFrom(new InternetAddress(emailorigem));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emaildestino));
			message.setText(mensagem);   // usar "\n" para quebrar linhas
			Transport.send(message);

			//System.out.println("enviado com sucesso");

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * JANELA PARA DIGITAR A SENHA DO EMAIL
	 */
	public static String pegarSenha(){
		JPasswordField field = new JPasswordField(10);
		field.setEchoChar('*'); 
		JPanel painel = new JPanel();
		painel.add(new JLabel("Entre com a senha do seu email:"));
		painel.add(field);
		JOptionPane.showMessageDialog(null, painel, "Senha", JOptionPane.PLAIN_MESSAGE);
		String texto = new String(field.getPassword());
		return texto.trim();
	}
	
}
