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
 	
 	public Simplex(ArrayList<Integer> valFO, List<List<Integer>> valRES, ArrayList<Integer> des, boolean maxi){
 		this.valFO = valFO;
 		this.valRES = valRES;
 		this.des = des;
 		this.maxi = maxi;
 	}

 	public void resolverSimplex(){
 		
 	}

 	public ArrayList<Integer> obeterVectorSol(){
 		return vectorSol;
 	}

 	public List<List<Integer>> obeterTablasIter(){
 		return tablasIter;
 	}

}