import java.util.Date;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;

public class Main {
	public static void main(String[] args) {
        String[] dictionary = null;
		String[] words = null;
		String[] sanitizedWords = null;
		int totalAnagrams = 0;
		Date start = new Date();
		Date end = new Date();
		
		// Recupere les fichiers en parametres
		if (args.length > 0){
			dictionary = readFile(args[0]);
			sanitizedWords = words = readFile(args[1]);
		}
		
		// Enleve les accents et les espaces
		if(dictionary != null){
			for (int i = 0; i < dictionary.length; i++) {
				dictionary[i] = sanitize(dictionary[i]);
			}
		}

		// Enleve les accents et les espaces		
		if(sanitizedWords != null){
			for (int i = 0; i < sanitizedWords.length; i++) {
				sanitizedWords[i] = sanitize(sanitizedWords[i]);
			}
		}
		
		start.getTime();
		// Pour chaque mot a trouver
		for (int i = 0; i < sanitizedWords.length; i++){
			int numberOfMatches = 0;
			// Pour chaque mot du dictionnaire
			for(int j = 0; j < dictionary.length; j++){
				// Si les deux mots sont de meme longueur
				if(sanitizedWords[i].length() == dictionary[j].length()){
					if(AlgoBase(sanitizedWords[i], dictionary[j])){
						numberOfMatches++;
					}
				}
			}
			System.out.println("Il y a " + numberOfMatches + " anagramme(s) du mot " + words[i]);
			totalAnagrams += numberOfMatches;
		}
		end.getTime();
		
		System.out.println("Il y a un total de " + totalAnagrams + " anagrammes");
		System.out.println("Temps d'execution : " + (end.getTime() - start.getTime()) + " millisecondes");
	}
	
	private static String[] readFile(String filePath){
		String file = "";
		try{
			FileInputStream stream = new FileInputStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			
			while ((line = reader.readLine()) != null){
				file += line + ";";
			}
			
		}catch(Exception e){}
		return file.split(";");
	}
	
	private static String sanitize(String s){
		// Enlever les espaces
		s = s.replaceAll("\\s", "");
		/*
			Code emprunte :
			Permet d'enlever les accents d'une string
			Source : http://stackoverflow.com/questions/15190656/easy-way-to-remove-utf-8-accents-from-a-string/15190787#15190787
		*/
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		/* Fin du code emprunte */
		
		return s;
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
                        break;
                    }   
                }
                if(!foundChar){
                    return false;
                }
            }

            if(string2.length() > 0)
                return false;
            
            return true;
	}
	
	private static Boolean AlgoFrancis(){
		
		return false;
	}
	
	private static Boolean AlgoMarco(){
		
		return false;
	}
	
	private static Boolean AlgoGuy(){
		
		return false;
	}
}
