import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;

public class Simplex{
	private ArrayList<Double> vectorSol; //Vector solucion del Simplex, la ultima posicion es la de la funcion evaluada
	private ArrayList<Double> valFO; //valores de las variables en la funcion objetivo
	private List<List<Double>> valRES; //valores de las varibales en las restricciones, la ultima posicion es el valor de la desigualdad de lado derecho
	private ArrayList<Integer> des; //Desigualdades donde: 0 es "<=" ,  1 es ">=" y 3 es "=" 
	private boolean maxi; //Si es true se maximizara con el metodo; en caso contrario se minimizara


	private int maxvar; //Numero maximo de variables
	private int maxres; //Numero maximo de restricciones

	private int numVarHol; //Numero de variables de holgura
	private int numVarArt; //Numero de variables artificiales

	private int sizeOfvector; //Tamaño de los vectores de la tabla 

	private List<List<Double>> tablasIter; //Tablas de iteracion SOLO VARIABLES DOUBLES
 	//Primero esta cj, luego zj y al final de cj-zj
	private List<List<Pair>> tablasIterj; //Tablas de iteracion SOLO VECTORES <DOUBLE, DOUBLE*M

	private List<List<HashMap<String, Integer>>> auxVecSol;

	private int numIter;

 	public Simplex(ArrayList<Double> valFO, List<List<Double>> valRES, ArrayList<Integer> des, boolean maxi){
 		this.valFO = valFO;
 		this.valRES = valRES;
 		this.des = des;
 		this.maxi = maxi;

 		maxvar = valFO.size(); 
 		maxres = valRES.size();

 		numVarHol = 0;
 		numVarArt = 0;

 		// Numero de columnas de las tablas de iteraccion = numero de variables de la FO + valor de lado derecho de la desigualdad
 		sizeOfvector = valFO.size() + 1;  //Posteriormete el tamaño de incrementa con las varibales de holgura y artificiales
 	}

 	public void resolverSimplex(){ // Simplex2

 		//PASO 1: MODELACIÓN MEDIANTE PROGRAMACIÓN LINEALPASO 1: MODELACIÓN MEDIANTE PROGRAMACIÓN LINEAL
 		
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

 		//PASO 2: CONVERTIR LAS INECUACIONES EN ECUACIONES


 		//Contamos el numero de varibales de holgura y artificiales para averiguar el tamaño de las columnas de la tabla
 		for (int i = 0; i < des.size(); i++){
 			if(des.get(i)== 0) // Si es "<="
 				numVarHol++;
			else if(des.get(i) == 1){ // Si es ">="
				numVarHol++;
				numVarArt++;
			} //Si es "=" NO SE HACE NADA
		}

		sizeOfvector += numVarHol + numVarArt; // El tamaño final de las columnas 

		//Lista de vectores cj, zj y cj-zj. Estos aumentaran en  +1 con cada iteracion
		tablasIterj = new ArrayList<List<Pair>>();

		tablasIter = new ArrayList<List<Double>>();
		for (int i = 0; i < maxres; i++) {
			tablasIter.add(new ArrayList<Double>(sizeOfvector)); //Creamos una nueva fila para la tabla de iteracion i
		}

		tablasIterj.add( new ArrayList<Pair>() );


		int posHol = maxvar; //Posicion de la variable de holgura
		int posArt = maxvar + numVarHol; //Posicion de la variable de artificial

		//Llenamos el vector cj	
		for (int i = 0; i < sizeOfvector-1 ; i++) {
			if (i < maxvar)
				tablasIterj.get(0).add(new Pair(valFO.get(i), 0.0));
			else if (i >= maxvar+numVarHol)
				tablasIterj.get(0).add(new Pair(0.0, 1.0));
			else
				tablasIterj.get(0).add(new Pair(0.0, 0.0));				
		}

		auxVecSol = new ArrayList< List< HashMap<String, Integer> > >();
		for (int i = 0; i < maxres; i++) {
			auxVecSol.add(new ArrayList<HashMap<String, Integer>>());
			auxVecSol.get(i).add();
		}

		//Llenamos la tabla de iteraciones de las varibles

		for (int i = 0; i < maxres; i++) {
			int auxposHol = posHol;
			int auxposArt = posArt;
			for (int j = 0 ; j < sizeOfvector; j++) {
				
				if (j < maxvar) //Agregamos el valor de las variables de la restriccion i
					tablasIter.get(i).add(valRES.get(i).get(j));
				else if (j >= maxvar) { //Ahora Las variables de holgura y artifiales
					if (des.get(i) == 0){
						if(j == auxposHol){
							tablasIter.get(i).add(1.0);
							posHol++;
						}
						else{
							tablasIter.get(i).add(0.0);
						}
					}else if (des.get(i) == 1){
						if (j == auxposHol){
							tablasIter.get(i).add(-1.0);
							posHol++;
						}
						else if (j == auxposArt){
							tablasIter.get(i).add(1.0);
							posArt++;
						}else
							tablasIter.get(i).add(0.0);
					}else if (des.get(i) == 2){
						tablasIter.get(i).add(0.0);
					}
				}
			}
			tablasIter.get(i).add(sizeOfvector-1, valRES.get(i).get(maxvar));
		}
		
		System.out.println("METODO SIMPLEX 2.0");
		System.out.print("cj: ");
		for (int i = 0; i < sizeOfvector - 1; i++) {
			System.out.printf("(%.1f + %.1fM)  ", tablasIterj.get(0).get(i).getKey(), tablasIterj.get(0).get(i).getValue() );
		}
		System.out.println("");

		System.out.println("Primer iteracion");
		for (int i = 0; i < maxres; i++) {
			System.out.printf("%13.2s", "x");
			for (int j = 0; j < sizeOfvector; j++) {
				System.out.printf("%13.2f ", tablasIter.get(i).get(j) );
			}
			System.out.println("");
		}

 	}

 	public ArrayList<Double> obeterVectorSol(){
 		return vectorSol;
 	}

 	public List<List<Double>> obeterTablasIterVar(){
 		return tablasIter;
 	}

 	public List<List<Pair>> obeterTablasIterVarzjcj(){
 		return tablasIterj;
 	} 

 	public Pair sumarVectores(Pair v1, Pair v2){
 		Pair aux = new Pair( (Double)v1.getKey() + (Double)v2.getKey(), (Double)v1.getValue() + (Double)v2.getValue() );
 		return aux;
 	}

}