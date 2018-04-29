# packer-problem
A package challenge similar to the knapsack problem

This problem presents an array Wt[]  =Weight, Val[] = value /price  and  Ind[] = index of items and a W threshold ,
which you are to find the set of valuable items  indexes S[] with total weight <= W .
The algorithm used to solve this problem is to find a possible subsets of Wt[].
  Given
  -Wt[]= {10,20,30}.
  -Val[]= {60,100,120}
  -Ind[] = {1,2,3}
  -W = 50

  All possible subsets of Wt[] would be {10},{20},{30},{10,20},{10,30},{20,30},{10,20,30} . and the possible total values respectively
  will be {60},{100},{120} ,{160},{180},{220},{280} . The answer to this subset after possible index selection is S[] = {2,3}
  because index 2,3 items are 30 and 20 and has total weight of 220 and it falls within the threshold W = 50.

  In code implementation, the subsets where was generated using a recursive algorithm to generate a set of  new Item objects in an
  ArrayList<ArrayList> data structure  i.e . The data set is now traversed to find all possible ArrayList<Item> that satifies the condition
