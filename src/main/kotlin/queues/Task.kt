package queues

import java.time.LocalDateTime

data class Task (val name: String,
                 var quant: Int,
                 val imQuant: Int = quant,
                 val preparedTime: LocalDateTime = LocalDateTime.now(),
                 var startTime: LocalDateTime = LocalDateTime.now(),
                 var exitTime: LocalDateTime = startTime,
                 var waitTime: Long = 0
) : Comparable<Task> {

    override fun compareTo(other: Task) = when {
        quant < other.quant -> -1
        quant > other.quant -> 1
        else -> 0
    }

}