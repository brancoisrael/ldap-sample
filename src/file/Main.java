package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.NameAlreadyBoundException;

import ldap.LDAP;
import model.Usuario;

public class Main {

	public static void main(String[] args) {
		if(args.length<1) {
			System.out.println("Necessário informar o path do arquivo e a quantidade de colunas.");
			return;
		}
		
		try {
			File file = new File(args[0]);
			if(!file.exists())
				throw new IOException();
			
			LDAP ldap = new LDAP();
			
			List<Usuario> usuarios = new ArrayList();
			
			FileReader reader = new FileReader(file);

			BufferedReader lerArq = new BufferedReader(reader);

			String linha = lerArq.readLine();

			int numLinha = 1;

			while (linha != null) {
				
				if(linha.contains("# entry-id:")) {
					numLinha++;
					linha = lerArq.readLine();
					if(linha.contains("dn: uid=") && linha.contains("ou=identidades,o=sisdepen")) {
						
						Usuario us = new Usuario();
						usuarios.add(us);
						
						us.setUserPassword("passwd"+new Random().nextInt());
						
						boolean check =true;
						
						while(check) {
							numLinha++;
							linha = lerArq.readLine();
						
							if(linha.startsWith("mail:"))
								us.setEmail(linha.split(":")[1].trim());							
							else if(linha.startsWith("uid:"))
								us.setUid(linha.split(":")[1].trim());							
							else if(linha.startsWith("sn:"))
								us.setSn(linha.split(":")[1].trim());							
							else if(linha.startsWith("cn::")) 
								us.setCn(linha.split(":")[2].trim());
							else if(linha.startsWith("cn:")) {
								us.setCn(linha.split(":")[1].trim());
								
							}
							
							if(linha.startsWith("nsUniqueId:"))
								check =false;
						}
					}
				}
				
				numLinha++;
				linha = lerArq.readLine();
			}
			
			
			for(Usuario us : usuarios) {
				try {
					ldap.inserir(us);
				}
				catch(NameAlreadyBoundException e) {
					System.out.println("Usuário já existe: "+us.toString());
					e.printStackTrace();
				}
			}
		}
		
		catch(IOException e) {
			System.out.println("Erro: Arquivo {0} não encontrado".replace("{0}", args[0]));
		}
		catch (Exception e) {
			System.out.println("Erro ao conectar no servidor LDAP");
			e.printStackTrace();
		}
		
	}

}
