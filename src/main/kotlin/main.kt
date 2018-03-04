import queues.RoundRobin
import queues.ShortestJobFirst
import queues.Task
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val shortestJobFirst = ShortestJobFirst(listOf(
            Task("1", 11),
            Task("2", 5),
            Task("3", 8),
            Task("4", 3),
            Task("5", 1)
    ), 20)

    val roundRobin = RoundRobin(listOf(
            Task("1", 50),
            Task("2", 35),
            Task("3", 11),
            Task("4", 12),
            Task("5", 39)
    ), 80)


    val tasksExecutor = thread(start = true) {
        while (true) {
            if (!roundRobin.isFinished()) {
                roundRobin.run()
                shortestJobFirst.updateWaitTime(roundRobin.lastWaitingTime)
            }
            if (!shortestJobFirst.isFinished()) {
                shortestJobFirst.run()
                roundRobin.updateWaitTime(shortestJobFirst.lastWaitingTime)
            }
            if (roundRobin.isFinished() && shortestJobFirst.isFinished()) {
                break
            }
        }
    }

    thread(start = true) {
        Thread.sleep(1200)
        shortestJobFirst.add(Task("6", 9))
        roundRobin.add(Task("7", 12))
        shortestJobFirst.add(Task("8", 3))
        Thread.sleep(1200)
        roundRobin.add(Task("6", 33))
        shortestJobFirst.add(Task("7", 1))
        roundRobin.add(Task("8", 19))
    }

    tasksExecutor.join()
}
