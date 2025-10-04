package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class ParkingSystem(
    big: Int,
    medium: Int,
    small: Int,
) {
    val slots = mutableListOf(big, medium, small)

    fun addCar(carType: Int): Boolean =
        slots.getOrNull(carType-1)?.let { count ->
            if (count > 0) {
                slots[carType-1] = count-1
                true
            } else false
        } ?: false
}

/**
 * Your ParkingSystem object will be instantiated and called as such:
 * var obj = ParkingSystem(big, medium, small)
 * var param_1 = obj.addCar(carType)
 */