package com.datastax.sample.service;

import java.util.Random;

import com.datastax.sample.dao.SampleDao;

public class Service {

	private SampleDao dao;

	public Service() {
		this.dao = new SampleDao();
	}

	public void insertRows(Integer startKey, Integer numberOfRows) {
		for ( int i = 0; i < numberOfRows; i++ ) {
			Integer rowKey = startKey + i;
			dao.insertRow(rowKey, "I am " + generateRandomNameAndCity() + " from row " + rowKey);
		}
	}

	public String selectRowValueByKey(Integer rowKey) {
		String rowValue = dao.selectRowValue(rowKey);
		return rowValue != null ? rowValue : ("No row with key " + rowKey + " was found");
	}
	
	private String generateRandomNameAndCity() {

		String[] cities = {"Tokyo", "Mumbai", "Mexico City", "Buenos Aires", "Paris", "Kuala Lumpur", "Hong Kong",
				"Madrid", "Perth", "Atlanta", "Chicago", "Singapore", "Belo Horizonte", "Montreal", "Tongren", "Salvador",
				"Bamako", "Hamburg", "Manaus", "Ottawa", "Melbourne", "Mecca", "Ogbomoso", "Pretoria", "Accra", "Belem",
				"Sofia", "Tegucigalpa", "Wellington", "Osaka", "Lagos", "Auckland", "Vientiane", "Raipur", "Sao Tome"};

		String[] names = {"Alina", "Carlos", "Dante", "Fabian", "Donna", "Carol", "Edgar", "Milton", "Raul", "Ivan",
				"Pooja", "Ayana", "Mohammad", "Sunil", "Maya", "Sasha", "Zachary", "Aysha", "Ajay", "Idris", "Abbas", "Samina",
				"Omar", "Assan", "Wesley", "Gordon", "Dean", "Iyad", "Guenther", "Karim", "Caroline", "Rasheed", "Yasser", "Freya",
				"Chetana", "Francisco","Marcus", "Melvin", "Alfred", "Julio", "Tiffany", "Carmen", "Zara", "Fayez", "Yusef",
				"Nasira", "Arwa", "Sonja", "Najib", "Joel", "Edwin", "Carla", "Tara", "Parul", "Farrukh", "Shaparat"};



		Random r = new Random();
		String name = names[r.nextInt(names.length)];
		String city = cities[r.nextInt(cities.length)];
		return name + " in " + city;
	}
}
