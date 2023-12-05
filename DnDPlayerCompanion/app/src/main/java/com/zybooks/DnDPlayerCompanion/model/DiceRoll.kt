package com.zybooks.DnDPlayerCompanion.model

import com.zybooks.DnDPlayerCompanion.R

const val LARGEST_NUM = 20
const val SMALLEST_NUM = 1

class DiceRoll(diceNumber: Int) {
    var imageId = 0

    var number = SMALLEST_NUM
        set(value) {
            if (value in SMALLEST_NUM..LARGEST_NUM) {
                field = value
                imageId = when (value) {
                    1 -> R.drawable.d20_1
                    2 -> R.drawable.d20_2
                    3 -> R.drawable.d20_3
                    4 -> R.drawable.d20_4
                    5 -> R.drawable.d20_5
                    6 -> R.drawable.d20_6
                    7 -> R.drawable.d20_7
                    8 -> R.drawable.d20_8
                    9 -> R.drawable.d20_9
                    10 -> R.drawable.d20_10
                    11-> R.drawable.d20_11
                    12 -> R.drawable.d20_12
                    13 -> R.drawable.d20_13
                    14 -> R.drawable.d20_14
                    15 -> R.drawable.d20_15
                    16 -> R.drawable.d20_16
                    17 -> R.drawable.d20_17
                    18 -> R.drawable.d20_18
                    19 -> R.drawable.d20_19
                    else -> R.drawable.d20_20
                }
            }
        }

    init {
        number = diceNumber
    }

    fun roll() {
        number = (SMALLEST_NUM..LARGEST_NUM).random()
    }
}