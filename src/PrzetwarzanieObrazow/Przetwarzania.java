package PrzetwarzanieObrazow;

class Przetwarzania {

	int odcieni = 255;
	
	/*Negatyw*/
	/* PRE: poprawnie zapisana tablica pikseli.
	   POST: tablica pikleli o wartosciach zmienionych zgodnie z ponizszym wzorem */
		ObrazPanel negatyw(ObrazPanel obrazek){

			for(int i=0; i<obrazek.wymx*3; i++){
				for (int j=0; j<obrazek.wymy; j++){
					obrazek.obraz[i][j]=odcieni-obrazek.obraz[i][j];
				}
			}
	  return obrazek;
		}
		
		/*Progowanie*/
		/* PRE: poprawnie zapisana tablica pikseli, poprawnie pobrany prog
		   POST: tablica pikleli o wartosciach zmienionych zgodnie z ponizszym wzorem */
		ObrazPanel progowanie(ObrazPanel obrazek, int prog){
			
			for(int i=0; i<obrazek.wymx*3; i+=3){
			      for (int j=0; j<obrazek.wymy; j++){
				if((obrazek.obraz[i][j]+obrazek.obraz[i+1][j]+obrazek.obraz[i+2][j])/3<=((prog*odcieni)/100)){
					obrazek.obraz[i][j]=obrazek.obraz[i+1][j]=obrazek.obraz[i+2][j]=0;
				}
				else
					obrazek.obraz[i][j]=obrazek.obraz[i+1][j]=obrazek.obraz[i+2][j]=odcieni;	
			      }
			  } 
			return obrazek;
		}
		
		ObrazPanel Czarnobialy(ObrazPanel obrazek){
			
			for(int i=0; i<obrazek.wymx*3-3; i=i+3){
				for (int j=0; j<obrazek.wymy; j++){
					obrazek.obraz[i+2][j]=obrazek.obraz[i+1][j]=obrazek.obraz[i][j]=(obrazek.obraz[i][j] + obrazek.obraz[i+2][j] + obrazek.obraz[i+1][j])/3;
					}	
			}
		return obrazek;
		}
		
		/*Konturowanie*/
		ObrazPanel kontur(ObrazPanel obrazek){

		    Czarnobialy(obrazek);           /*Konwersja do formatu skali szarosci*/

		    for(int i=0; i<obrazek.wymx*3-3; i=i+3){
		      for (int j=0; j<obrazek.wymy-1; j++){
		    	  obrazek.obraz[i+2][j]=obrazek.obraz[i+1][j]= obrazek.obraz[i][j]=Math.abs(obrazek.obraz[i+3][j]-obrazek.obraz[i][j])+Math.abs(obrazek.obraz[i][j+1]-obrazek.obraz[i][j]);
		      }
		  }
		    negatyw(obrazek);
		  return obrazek;
		}

		
		/*Rozciaganie histogramu*/
		/* PRE: poprawnie zapisana tablica pikseli
		   POST: tablica pikleli o wartosciach zmienionych zgodnie z ponizszym wzorem */
		ObrazPanel rozhist(ObrazPanel obrazek){
			
			float min, max, zm;
			min=max=obrazek.obraz[0][0];
			
			for(int i=0; i<obrazek.wymx*3; i++){
			      for (int j=0; j<obrazek.wymy; j++){
				if(max<obrazek.obraz[i][j]){
				  max=obrazek.obraz[i][j];
				}
				if(min>obrazek.obraz[i][j]){
				  min=obrazek.obraz[i][j];
				}
			      }
			}
			    zm=(odcieni/(max-min));
			    for(int i=0; i<obrazek.wymx*3; i++){
			      for (int j=0; j<obrazek.wymy; j++){
			    	  obrazek.obraz[i][j]=(int) ((obrazek.obraz[i][j]-min)*zm);
			      }
			  }
			  return obrazek;
			}
		
		/*Rozmywanie o srednicy 5pxl*/
		/* PRE: poprawnie zapisana tablica pikseli
		   POST: tablica pikleli o wartosciach zmienionych zgodnie z ponizszym wzorem */
		ObrazPanel rozmywanie(ObrazPanel obrazek){
			
			for(int i=0; i<obrazek.wymx*3-6; i++){
			      for (int j=0; j<obrazek.wymy-2; j++){
			    	  if(i>6) /*Aby nie pobieralo pikseli spoza tablicy*/
			    		  obrazek.obraz[i][j]=((obrazek.obraz[i-6][j]+obrazek.obraz[i-3][j]+obrazek.obraz[i][j]+obrazek.obraz[i+3][j]+obrazek.obraz[i+6][j])/5);
			    	  if(j>6) /*Aby nie pobieralo pikseli spoza tablicy*/
			    		  obrazek.obraz[i][j]=((obrazek.obraz[i][j-2]+obrazek.obraz[i][j-1]+obrazek.obraz[i][j]+obrazek.obraz[i][j+1]+obrazek.obraz[i][j+2])/5);
			    	      }
			    	  }
			return obrazek;
			      }

		/*Korekcja gamma*/
		/* PRE: poprawnie zapisana tablica pikseli, poprawnie pobrany parametr gamma
		   POST: tablica pikleli o wartosciach zmienionych zgodnie z ponizszym wzorem */
		ObrazPanel kgamma(ObrazPanel obrazek, float pgamma){
			
			 for(int i=0; i<obrazek.wymx*3; i++){
			      for (int j=0; j<obrazek.wymy; j++){
			    	  obrazek.obraz[i][j]=(int) ((Math.pow( obrazek.obraz[i][j], 1/pgamma))/(Math.pow(odcieni, (1-pgamma)/pgamma)));
			      }
			  }
			 return obrazek;
		}
			
		
		/*Polprogoanie czerni*/
		/* PRE: poprawnie zapisana tablica pikseli, poprawnie pobrany prog
		   POST: tablica pikleli o wartosciach zmienionych zgodnie z ponizszym wzorem */
		ObrazPanel progowaniebl(ObrazPanel obrazek, int prog){

			for(int i=0; i<obrazek.wymx*3; i++){
				for (int j=0; j<obrazek.wymy; j++){
					if(obrazek.obraz[i][j]<=((prog*odcieni)/100))
						obrazek.obraz[i][j]=0;
				}
			}
			return obrazek;
		}

		/*Polprogoanie bieli*/
		/* PRE: poprawnie zapisana tablica pikseli, poprawnie pobrany prog
		   POST: tablica pikleli o wartosciach zmienionych zgodnie z ponizszym wzorem */
		ObrazPanel progowaniewh(ObrazPanel obrazek, int prog){

			for(int i=0; i<obrazek.wymx*3; i++){
				for (int j=0; j<obrazek.wymy; j++){
					if(obrazek.obraz[i][j]>((prog*odcieni)/100))
						obrazek.obraz[i][j]=odcieni;
				}
			}
			return obrazek;
		}

		ObrazPanel pogrub(ObrazPanel obrazek) {
			for(int k=0;k<4;k++){
			for(int i=0; i<(obrazek.wymx-1)*3; i++){
				for (int j=0; j<obrazek.wymy-1; j++){
						obrazek.obraz[i][j] += obrazek.obraz[i+3][j+1];
						if(obrazek.obraz[i][j]>255)
							obrazek.obraz[i][j] = 255; 
				}
			}
			}
			return obrazek;
		}
}	

		


