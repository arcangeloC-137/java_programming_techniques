package it.polito.tdp.food.model;

import java.util.Comparator;

public class ComparatoreCalorieCondimenti implements Comparator<CondimentAndCalories> {

	@Override
	public int compare(CondimentAndCalories o1, CondimentAndCalories o2) {
		// TODO Auto-generated method stub
		return -o1.getCalories().compareTo(o2.getCalories());
	}

}
