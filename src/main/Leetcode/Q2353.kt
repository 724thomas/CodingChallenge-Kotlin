package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
data class Pair(val name: String, val cuisine: String, var rating: Int)

class FoodRatings(foods: Array<String>, cuisines: Array<String>, ratings: IntArray) {
    val foodMap = mutableMapOf<String, Pair>()
    val cuisineMap = mutableMapOf<String, TreeSet<Pair>>()

    init {
        for (i in foods.indices) {
            val food = Pair(foods[i], cuisines[i], ratings[i])
            foodMap[food.name] = food
            cuisineMap.putIfAbsent(
                food.cuisine,
                TreeSet(compareByDescending<Pair> { it.rating }.thenBy {it.name}
                ))
            cuisineMap[food.cuisine]!!.add(food)
        }
    }

    fun changeRating(food: String, newRating: Int) {
        val pair: Pair = foodMap[food]?: return
        val set = cuisineMap[pair.cuisine]!!
        set.remove(pair)
        pair.rating = newRating
        set.add(pair)
    }

    fun highestRated(cuisine: String): String {
        return cuisineMap[cuisine]!!.first().name
    }

}

/**
 * Your FoodRatings object will be instantiated and called as such:
 * var obj = FoodRatings(foods, cuisines, ratings)
 * obj.changeRating(food,newRating)
 * var param_2 = obj.highestRated(cuisine)
 */