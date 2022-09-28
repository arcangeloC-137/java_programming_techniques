package it.polito.tdp.libretto.model;

import java.time.LocalDate;

public class TestLibretto {
	
	Libretto lib ;
	
	private void run() {
		this.lib = new Libretto() ; // crea libretto vuoto
		
		//1. Inserire alcuni voti
		Voto v1 = new Voto("Tecniche di programmazione", 30, LocalDate.of(2020, 06, 15)) ;
		Voto v2 = new Voto("Analisi II", 28, LocalDate.of(2020, 06, 28)) ;

		lib.add(v1);
		lib.add(v2);
		if (lib.add(new Voto("Economia", 24, LocalDate.of(2020, 02, 14))) == false) {
			System.out.println("NOPE");
		}
		
		System.out.println(this.lib) ;
		
		//2. Stampa tutti i voti uguali a 28
		System.out.println(this.lib.stampaVotiUguali(28)) ;
		
		System.out.println(this.lib.estraiVotiUguali(28)) ;
		
		//3. Cerca esame superato conoscendo il corso
		String nomeCorso = "Fisica I";
		Voto votoMancante = lib.cercaNomeCorso(nomeCorso); //Contiene tutte le info sul voto
		System.out.println("Il voto di Fisica I e' "+votoMancante);
		
		//4./5. Verifica voti duplicati o in conflitto
		Voto economia2 = new Voto("Economia", 24, LocalDate.now());
		Voto economia3 = new Voto("Economia", 21, LocalDate.now());
		System.out.println("Economia con 24 e' duplicato: "+lib.isDuplicato(economia2)+
				"\n conflitto: "+lib.isConflitto(economia2));
		System.out.println("Economia con 21 e' duplicato: "+lib.isDuplicato(economia3)+
				"\n conflitto: "+lib.isConflitto(economia3));
		
		//6. Libretto Migliorato
		Libretto librettoMigliorato = lib.creaLibrettoMigliorato();
		System.out.println("\nMiglioramento libretto:");
		System.out.println(lib);
		System.out.println(librettoMigliorato);
		
		//7.1 Stampa in ordine alfabetico
		Libretto alfabetico = new Libretto(lib);
		alfabetico.ordinaPerCorso();
		System.out.println(alfabetico);
		
		//7.2 Stampa in ordine decrescente per voto
		Libretto perVoto = new Libretto(lib);
		perVoto.ordinaPerVoto();
		System.out.println(perVoto);
		
		//8. Elimina voti bassi
		lib.add(new Voto("Chimica", 19, LocalDate.now()));
		lib.ordinaPerCorso();
		System.out.println(lib);
		lib.canecllaVotiScarsi();
		System.out.println(lib);
		
	}

	public static void main(String[] args) {
		TestLibretto test = new TestLibretto() ;
		test.run();
	}

}
