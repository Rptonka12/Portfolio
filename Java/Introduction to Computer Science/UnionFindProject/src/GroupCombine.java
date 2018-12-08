/*
 * Rachel Pierstorff
 * Project 6
 * June 2, 2017
 * Part 1: interface for ADT described in phase 1
 */
public interface GroupCombine<T> {
	void createGroup(T data); //creates group of only x as element
	void combineSet(T a, T b); //performs union between two groups that contain x and y
	T find(T data); //returns an element that represents the group that contains x
}