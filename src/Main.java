
public class Main {
	public static void main(String[] args) {
        /*	TODO
         * 	1. Ouvrir les deux fichiers en param�tres et les placer dans deux String Arrays (dictionnaire et mots)
         * 	2. Traiter les chaines pour enlever les espaces et les accents
         * 	3. compteur total
         * 	4. Pour chaque mot
         * 	5.		compteur courant
         * 	6. 		Pour chaque mot du dictionnaire de m�me longueur que le mot
         * 	7.			Appeler l'algo
         * 	8. 			Si annagramme
         * 					incrementer compteur courant
         * 	9. 		Afficher le nombre d'annagrames du mot
         * 			incrementer 
         * */
		
    }
	
	private static Boolean AlgoBase(String string1, String string2){
            for(int i = 0; i < string1.length(); i++){
                boolean foundChar = false;
                int string2Length = string2.length();
                for(int j = 0; j < string2Length; j++){
                    if(string1.charAt(i) == string2.charAt(j)){
                        
                        //Remove the found index from string2
                        String tempString = "";
                        if(j > 0){
                            tempString += string2.substring(0, j);
                        }
                        if(j < (string2.length() - 1)){
                            tempString += string2.substring(j + 1);
                        }
                        string2 = tempString;
                        
                        //We took a char out of the string so we adjust 
                        //the length variable for the for loop
                        string2Length --;
                        
                        foundChar = true;
                    }   
                }
                if(!foundChar){
                    return false;
                }
            }

            return true;
	}
	
	private Boolean AlgoFrancis(){
		
		return false;
	}
	
	private Boolean AlgoMarco(){
		
		return false;
	}
	
	private Boolean AlgoGuy(){
		
		return false;
	}
}
