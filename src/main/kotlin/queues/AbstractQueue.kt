package queues

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

abstract class AbstractQueue(val quant: Int) {

    protected abstract val tasks: Queue<Task>
    protected val finished = ArrayList<Task>()

    var lastWaitingTime: Long = 0

    abstract fun run()

    fun add(task: Task): Boolean = tasks.add(task)

    fun isFinished(): Boolean = tasks.isEmpty()

    protected fun outExecutingResults(algorithmName: String) {
        val pattern = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")
        println("algorithm task quants      prepared      start         exit  execution wait (ms)")
        finished.forEach { (num, _, quant, preparedTime, startTime, exitTime, waitTime) ->
            println(String.format(
                    "%9s %4s %6d %12s %12s %12s %9d %6d",
                    algorithmName,
                    num,
                    quant,
                    startTime.format(pattern),
                    preparedTime.format(pattern),
                    exitTime.format(pattern),
                    exitTime - preparedTime,
                    waitTime
            ))
        }
        println()
    }

    fun updateWaitTime(waitTime: Long) = tasks.forEach {task -> task.waitTime += waitTime }

}

operator fun LocalDateTime.minus(wait: LocalDateTime): Long =
        this.toInstant(ZoneOffset.UTC).toEpochMilli() -
        wait.toInstant(ZoneOffset.UTC).toEpochMilli()
