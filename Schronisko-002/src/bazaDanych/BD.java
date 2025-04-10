package bazaDanych;

import java.sql.ResultSet;
import java.sql.SQLException;

import modeleList.ModelListyKot;

public class BD {
	
	private static MenagerBazyDanych bd = MenagerBazyDanych.getInstance();
	
	public static void pobierzDaneKotow(ModelListyKot koty) {
		koty.removeAllElements();
		ResultSet rs;
		try {
			rs = bd.getData("SELECT id, nazwa FROM koty;");
			while(rs.next()){
				koty.dodajKota(
					rs.getInt("id"),
					rs.getString("nazwa")
				);
			}
		} catch(SQLException e) {
			System.out.println("Blad pobierania danych!");
		}
	}
	
	public static void dodajKota(String nazwa) {
		bd.updateDB("INSERT INTO koty (nazwa) VALUES ('" + nazwa + "');");
	}
}
