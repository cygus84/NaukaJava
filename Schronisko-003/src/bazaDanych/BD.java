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

	public static long pobierzIloscKotow() {
		long ilosc = 0;
		ResultSet rs;
		try {
			rs = bd.getData("SELECT COUNT(*) AS ilosc FROM koty;");
			while(rs.next()) {
				ilosc = rs.getLong("ilosc");
				System.out.println("ilosc kotow " + ilosc);
			}
		} catch(SQLException e) {
			System.out.println("Blad pobierania danych!");
		}
		return ilosc;
	}

	public static void usunKota(int idKota) {
		bd.updateDB("DELETE FROM koty WHERE id = " + idKota + ";");
		
	}

	public static void zapiszDaneKota(String nowaNazwa, int idKota) {
		bd.updateDB(String.format("UPDATE koty SET nazwa='%s' WHERE id = %d;", nowaNazwa, idKota));
	}
}
