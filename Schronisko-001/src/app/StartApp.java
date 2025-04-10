package app;

import bazaDanych.BD;
import okna.OknoGlowne;

public class StartApp {

	public static void main(String[] args) {
		System.out.println("Start aplikacji schronisko-001!");
		
		//BD.dodajKota("Burek");
		new OknoGlowne();
	}
}
