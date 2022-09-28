package it.polito.tdp.food.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;

public class TestDAO {

	public static void main(String[] args) {
		FoodDAO dao = new FoodDAO();
		
		System.out.println(dao.listAllFood());
		Map<Integer, Condiment> map = new HashMap<>();
		dao.listAllCondiment(map);
		
		for(Condiment c: map.values()) {
			System.out.println(c.getDisplay_name()+" "+c.getCondiment_id()+"\n");
		}

	}

}