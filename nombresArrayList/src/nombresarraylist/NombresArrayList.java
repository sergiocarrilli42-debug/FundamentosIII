
package nombresarraylist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
class nameCount {
    String name;
    int count;
    public nameCount (String name){
                this.name = name;
                this.count = 1;
    }
public void incrementCount(){
    this.count++;
    }
public String getName() {
        return this.name + " (" + this.count + ")";
    }
}
public class NombresArrayList {

    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\Users\\sergi\\Documents\\FundamentosIII\\nombresArrayList\\listado.txt");
        ArrayList<String> namesList = readNamesFromFile(filePath.toString());
        printNames (namesList);
        System.out.println("-----");
        ArrayList<String> countedNames = countNames(namesList);
        printNames(countedNames);
        
    }
    public static ArrayList<String>readNamesFromFile (String filePath){
     ArrayList<String> names = new ArrayList<>();
     try{
         Stream<String> lines = Files.lines(Paths.get(filePath));
         for (String line : (Iterable<String>) lines::iterator){
             names.add(line);
         }
         lines.close();
     } catch (IOException e) {
         e.printStackTrace();
     }
     return names;
    }
    
    public static void printNames (ArrayList <String> nameList){
        int n=0;
        for(int i = 0; i<nameList.size(); i++){
        System.out.println(nameList.get(i));
        }
    }
    public static ArrayList<String> countNames(ArrayList<String> names){
        ArrayList<nameCount> countedNames = new ArrayList<>();
        int pos=0;//Track the position of the name
        for (String name : names){
            String [] splitName = name.split(" ");
            boolean found = false;
            for (nameCount  nc : countedNames){
                if (nc.name.equals(splitName[pos])){
                    nc.incrementCount();
                    found = true;
                    break;
                }
            }
            if (!found){
                countedNames.add(new nameCount(splitName[pos]));
            }
        }
        ArrayList<String> result = new ArrayList<>();
        for (nameCount nc : countedNames){
            result.add(nc.getName());
        }
        return result;
        
    }
//    public static void countNames(ArrayList <String> nameList){
//            ArrayList <String> nombresUnicos = new ArrayList<>();
//            ArrayList <Integer> contadores = new ArrayList<>();
//        for (String name : nameList){
//            //SEPARA EL NOMBRE POR ESPACIO
//            String [] partes = name.split(" ");
//            String nombre = partes[0];
//            //BUSCA SI EL NOMBRE EXISTE EN LA LISTA
//            int index = nombresUnicos.indexOf(nombre);
//            
//            if (index == -1){
//                //SI NO EXISTE LO AGREGAMOS CON CONTADOR
//                nombresUnicos.add(nombre);
//                contadores.add(1);  
//            }
//            else{
//                //SI YA EXISTE SUMAMOS 1 AL CONTADOR
//                contadores.set(index, contadores.get(index)+ 1);
//            }
//        }
//            System.out.println("Nombres Repetidos");
//            for (int i=0; i<nombresUnicos.size(); i++){
//                if (contadores.get(i)>1){
//                    System.out.println(nombresUnicos.get(i)+ " aparece " + contadores.get(i) + " veces");
//                }
//            }
//        
//
//        
//    }
}

    
