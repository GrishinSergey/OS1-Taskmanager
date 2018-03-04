package queues

import java.time.LocalDateTime
import java.util.concurrent.PriorityBlockingQueue

class ShortestJobFirst(newTasks: List<Task>, quant: Int) : AbstractQueue(quant) {

    override val tasks = PriorityBlockingQueue<Task>()

    init { tasks.addAll(newTasks) }

    override fun run() {
        var quants = quant
        var task: Task
        var startDateTime: LocalDateTime
        var finishDateTime: LocalDateTime

        while (tasks.any() && quants >= 0) {
            task = tasks.poll()

            startDateTime = LocalDateTime.now()
            while (task.quant > 0) {
                quants--
                task.quant--
                Thread.sleep(100)
            }
            finishDateTime = LocalDateTime.now()

            tasks.remove(task)
            task.startTime = startDateTime
            task.exitTime = finishDateTime
            finished.add(task)

            lastWaitingTime = finishDateTime - startDateTime
            updateWaitTime(lastWaitingTime)
        }

        if (tasks.size == 0) {
            outExecutingResults("SJF")
        }
    }

}