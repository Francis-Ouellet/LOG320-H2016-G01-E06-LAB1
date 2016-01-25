
public class DAnagrammeClasse {
	static boolean Estcompatible( String nom, String mot ){
		if (mot.length()==nom.length())
			return true;
		else 
			return false;
	}
	boolean ChercherUncarintab(char[] tabChar, char a ){
		int i=0;
		while(i<tabChar.length){
			if(a==tabChar[i]){
				return true;
			}
			i++;
		}return false;
	}  

	Boolean Anagramme(String nom, String mot,int j, char[] tabChar){
		if (Estcompatible(nom,mot)==true){
			
			if(j<mot.length()&& ChercherUncarintab( tabChar, nom.charAt(j))==true){
				nom=nom.replace(nom.charAt(j),"$".charAt(0) );
					j++;
				if(j==mot.length())
					return true;
			
			
			} else return false;
		
		
			
		}else return false;
		return Anagramme(nom,mot,j, tabChar);
	   
		}  
}
