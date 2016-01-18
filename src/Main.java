import java.util.Date;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;

public class Main {
	
	private static int[] PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
	
	public static void main(String[] args) {
        String[] dictionary = null;
		String[] words = null;
		String[] sanitizedWords = null;
		int totalAnagrams = 0;
		long start;
		long end;
		
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
		
		start = System.currentTimeMillis();
		// Pour chaque mot a trouver
		for (int i = 0; i < sanitizedWords.length; i++){
			int numberOfMatches = 0;
			// Pour chaque mot du dictionnaire
			for(int j = 0; j < dictionary.length; j++){
				if(AlgoMarco(sanitizedWords[i], dictionary[j])){
					numberOfMatches++;
					//System.out.println(dictionary[j]);
				}
			}
			System.out.println("Il y a " + numberOfMatches + " anagramme(s) du mot " + words[i]);
			totalAnagrams += numberOfMatches;
		}
		end = System.currentTimeMillis();
		
		System.out.println("Il y a un total de " + totalAnagrams + " anagrammes");
		System.out.println("Temps d'execution : " + (end - start) + " millisecondes");
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
	
	//  Chaque nombre plus grand que 1 est soit un nombre premier, soit un multiple de nombres premiers
	//	J'assigne les 26 nombres premiers aux 26 lettres de l'alphabet
	//  Chaque mot poss�de donc une valeur num�rique unique, peu importe l'ordre de ses lettres (abb (2*3*3 = 18) = bab (3*2*2 = 18))
	private static Boolean AlgoFrancis(String word1, String word2){
		if(word1.length() == word2.length()){
			// on s'assure que toutes les lettres sont majuscules, sinon le hash ne fonctionnerait pas
			// aussi, les majuscules arrivent en premier dans la table ASCII, on utilise donc des plus petits chiffres
			long hash1 = ToNumericHash(word1.toUpperCase().toCharArray());
			long hash2 = ToNumericHash(word2.toUpperCase().toCharArray());
			if(hash1 == -1 || hash2 == -1)
				return false;
			return hash1 == hash2;
		}
		else
			return false;
	}
	
	private static long ToNumericHash(char[] word){
		long value = 1L;
		for(int i = 0; i < word.length; i++ ){
			// Si le caract�re est dans la port�e des lettres majuscules en ASCII 
			if (word[i] >= 65 && word[i] <= 90){
				value = value * PRIMES[word[i] - 65];
			}
			else
				return -1;
		}
		return value;
		
	}
	
        private static int getArraySum(char[] array){
            int sum = 0;
            for(int i = 0; i < array.length; i++){
                    sum += array[i];
            }
            return sum;
        }
        
	private static Boolean AlgoMarco(String word1, String word2){
                int word1Length = word1.length();
                int word2Length = word2.length();
                
		if(word1Length != word2Length){
                    return false;
                }
                
                char[] chars1 = word1.toCharArray();
                char[] chars2 = word2.toCharArray();
                int sumWord1 = 0;
                int sumWord2 = 0;
                
                sumWord1 = getArraySum(chars1);
                sumWord2 = getArraySum(chars2);
                
                if(sumWord1 != sumWord2){
                    return false;
                }

                for(int i = 0; i < word1Length; i++){
                boolean foundChar = false;
                for(int j = 0; j < word2Length; j++){
                    if(chars1[i] == chars2[j]){
                        
                        //Dans la table ascii, aucune lettre n'est representee par 0. Alors c'est comme
                        //si on retirait le caractere de la chaine.
                        chars2[j] = 0;
                        
                        foundChar = true;
                        break;
                    }   
                }
                if(!foundChar){
                    return false;
                }
            }

            return true;
	}
	
	private static Boolean AlgoGuy(){
		
		return false;
	}
}
