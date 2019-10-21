import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simplex{
	private ArrayList<Integer> vectorSol; //Vector solucion del Simplex, la ultima posicion es la de la funcion evaluada
	private ArrayList<Integer> valFO; //valores de las variables en la funcion objetivo
	private List<List<Integer>> valRES; //valores de las varibales en las restricciones, la ultima posicion es el valor de la desigualdad de lado derecho
	private ArrayList<Integer> des; //Desigualdades donde: 0 es "<=" ,  1 es ">=" y 3 es "=" 
	private boolean maxi; //Si es true se maximizara con el metodo; en caso contrario se minimizara

	private List<List<Integer>> tablasIter;

	private ArrayList<Integer> varHolgura;
	private ArrayList<Integer> varArtificial;
 	
 	public Simplex(ArrayList<Integer> valFO, List<List<Integer>> valRES, ArrayList<Integer> des, boolean maxi){
 		this.valFO = valFO;
 		this.valRES = valRES;
 		this.des = des;
 		this.maxi = maxi;
 	}

 	public void resolverSimplex(){
 		System.out.println("DATOS DEL SIMPLEX");
 		if(maxi)
 			System.out.print("Maximizar ");
 		else
 			System.out.print("Minimizar "); 			
 		System.out.printf("F.O --> Z = ");
 		for (int i = 0; i < valFO.size(); i++) 
 			if(i == valFO.size() - 1)
 				System.out.print(valFO.get(i)+" x"+(i+1));
 			else
 				System.out.print(valFO.get(i)+" x"+(i+1)+"+ ");
 		System.out.println("");
 		System.out.println("Sujeto a las siguientes restricciones: ");

 		for (int i = 0; i < valRES.size(); i++) {
 			System.out.print("R"+i+": ");
 			for (int j = 0; j < valRES.get(i).size(); j++){
 				if (j == valRES.get(i).size() - 1){
 					if(des.get(i) == 0)
 						System.out.print(" <= ");
 					else if(des.get(i) == 1)
 						System.out.print(" >= ");
 					else if (des.get(i) == 2)
 						System.out.print(" = ");
 					System.out.print(valRES.get(i).get(j));
 				}
 				else if (j == valRES.get(i).size() - 2)
 					System.out.print(valRES.get(i).get(j)+" x"+(j+1));
 				else
 					System.out.print(valRES.get(i).get(j)+" x"+(j+1)+" + ");
 			}
 			System.out.println("");
 		}
 		System.out.println("----------------------------------------------------------");
 	}

 	public ArrayList<Integer> obeterVectorSol(){
 		return vectorSol;
 	}

 	public List<List<Integer>> obeterTablasIter(){
 		return tablasIter;
 	}

}