package ftt.ec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ParserXML_JSON {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		String diretorio = System.getProperty("user.dir");
		File file = new File(diretorio + "\\src\\ftt\\ec\\InputXML.xml");
		System.out.println("Lendo o arquivo em: " + diretorio + "\\src\\ftt\\ec\\InputXML.xml");
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			PilhaDinamica pilha = new PilhaDinamica();
			ArrayList<String> lista = new ArrayList<String>();
			String st, novoTexto;
			int contador = 0;
			lista.add("{");
			String[] vetor;
			String col = "";
			Estado estAnterior = new Estado();
			while ((st = br.readLine()) != null) {
				vetor = st.split("<");
				//Quando é uma tag
				if(vetor.length == 2) {										
					if(pilha.retornaTopo() != null && ("/" + pilha.retornaTopo().getValor()).equals(vetor[1].substring(0, vetor[1].length() - 1))) {						
						if(contador != 0 && !estAnterior.getValor().equals(pilha.retornaTopo().getValor()))
							col = "]";
						estAnterior = pilha.retornaTopo();
						pilha.desempilha();
						if(lista.get(lista.size() - 1).contains(",")) {
							novoTexto = lista.get(lista.size() - 1).replace(",", "");
							lista.set(lista.size() - 1, novoTexto);
						}
						if(col.equals("]")) {
							lista.add(col);
							col = "";
							contador = 0;
						}							
						lista.add("}");
					}													
					else {
						if(vetor[1].substring(0, vetor[1].length() - 1).equals(estAnterior.getValor())) {
							int ind = lista.indexOf("\"" + vetor[1].substring(0, vetor[1].length() - 1) + "\": ");
							if(ind != -1)
								lista.set(ind, "\"" + vetor[1].substring(0, vetor[1].length() - 1) + "\": [");
							contador++;
							novoTexto = lista.get(lista.size() - 1);
							lista.set(lista.size() - 1, novoTexto.concat(","));
						}
						else {
							lista.add("\"" + vetor[1].substring(0, vetor[1].length() - 1) + "\": ");
							contador = 0;
						}							
						pilha.empilha(vetor[1].substring(0, vetor[1].length() - 1));						
						lista.add("{");
					}
				}
				//Quando não é uma tag
				else {
					lista.add("\"" + vetor[1].split(">")[0] + "\": " + "\"" + vetor[1].split(">")[1] + "\",");
				}								
			}
			lista.add("}");
			if(pilha.Tamanho() != 0)
				System.out.println("Erro ao ler o arquivo!");
			else
				salvarArquivo(diretorio + "\\src\\ftt\\ec\\OutputJSON.json", lista);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException erro) {
			erro.printStackTrace();
		}
		finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void salvarArquivo(String caminho, ArrayList<String> lista) {
		BufferedWriter bw = null;
		try {
			File writeFile = new File(caminho);
			  writeFile.createNewFile();
			  bw = new BufferedWriter(new FileWriter(writeFile));
			  for(String texto : lista) {
				  bw.write(texto);
				  bw.newLine();
			  }
			  System.out.println("Salvo com sucesso em: " + caminho);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
}
