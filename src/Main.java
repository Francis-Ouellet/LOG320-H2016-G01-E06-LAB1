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
			sanitizedWords = readFile(args[1]);
			words = readFile(args[1]);
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
		/*
		// Traitement i,j pour chaque j 
		// Pour chaque mot a trouver
		for (int i = 0; i < sanitizedWords.length; i++){
			int numberOfMatches = 0;
			// Pour chaque mot du dictionnaire
			for(int j = 0; j < dictionary.length; j++){
				if(AlgoBase(sanitizedWords[i], dictionary[j])){
					numberOfMatches++;
					//System.out.println(dictionary[j]);
				}
			}
			System.out.println("Il y a " + numberOfMatches + " anagramme(s) du mot " + words[i]);
			totalAnagrams += numberOfMatches;
		}
		*/
		
		// Traitement i pour chaque i, puis j pour chaque j
		// Parcours des mots pour lesquels on cherche un anagramme
		for(int i = 0; i < sanitizedWords.length; i++){
			int numberOfMatches = 0;
			char[] array1 = sanitizedWords[i].toCharArray();
			// Calcul du hash pour le mot source de l'anagramme
			long hash1 = ToNumericHash(array1);
			if(hash1 != -1){
				// Calcul de la somme des lettres pour le mot source de l'anagramme
				int checkSum1 = getArraySum(array1);
				// Parcours des mots du dictionnaire
				for(int j = 0; j < dictionary.length; j++){
					// Si les deux mots ont la même longueur
					if(sanitizedWords[i].length() == dictionary[j].length()){
						char[] array2 = dictionary[j].toCharArray();
						// Calcul de la somme des lettres pour le mot du dictionnaire
						int checkSum2 = getArraySum(array2);
						// Si les deux mots ont la même somme, ils ont le potentiel de contenir les mêmes lettres
						if(checkSum1 == checkSum2){
							// Calcul du hash pour le mot du dictionnaire 
							long hash2 = ToNumericHash(dictionary[j].toCharArray());
							if(hash2 != -1){
								// Si les hash sont identiques, il s'agit d'un anagramme
								if(hash1 == hash2)
									numberOfMatches++;
							}
						}
					}
				}
				System.out.println("Il y a " + numberOfMatches + " anagramme(s) du mot " + words[i]);
				totalAnagrams += numberOfMatches;
			}
		}
		
		end = System.currentTimeMillis();
		
		System.out.println("Il y a un total de " + totalAnagrams + " anagrammes");
		System.out.println("Temps d'execution : " + (end - start) + " millisecondes");
	}
	
	private static String[] readFile(String filePath){
		String file = "";
		FileInputStream stream = null;
		BufferedReader reader = null;
		try{
			 stream = new FileInputStream(filePath);
			 reader = new BufferedReader(new InputStreamReader(stream));
			 String line = ""; 
			 while ((line = reader.readLine()) != null){
				 file = file + line + ";";
			 }
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
			reader.close();
			stream.close();
			}catch(Exception e){}
		}
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
		
		return s.toLowerCase();
	}
	
	private static Boolean AlgoBase(String string1, String string2){
		if(string1.length() == string2.length()){
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
		else
			return false;
	}
	
	//  Chaque nombre plus grand que 1 est soit un nombre premier, soit un multiple de nombres premiers
	//	J'assigne les 26 nombres premiers aux 26 lettres de l'alphabet
	//  Chaque mot possede donc une valeur numerique unique, peu importe l'ordre de ses lettres (abb (2*3*3 = 18) = bab (3*2*2 = 18))
	private static Boolean AlgoFrancis(String word1, String word2){
		if(word1.length() == word2.length()){
			
			char[] chars1 = word1.toCharArray();
            char[] chars2 = word2.toCharArray();
            
            if(getArraySum(chars1) != getArraySum(chars2)){
                return false;
            }
			// on s'assure que toutes les lettres sont majuscules, sinon le hash ne fonctionnerait pas
			// aussi, les majuscules arrivent en premier dans la table ASCII, on utilise donc des plus petits chiffres
			long hash1 = ToNumericHash(chars1);
			long hash2 = ToNumericHash(chars2);
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
			// Si le caractere est dans la portee des lettres minuscules en ASCII 
			if (word[i] >= 97 && word[i] <= 122){
				value = value * PRIMES[word[i] - 97];
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
