package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval) = addTimeIntervals(interval, 1)
operator fun MyDate.plus(repeatedTimeInternal: RepeatedTimeInternal) = addTimeIntervals(repeatedTimeInternal.interval, repeatedTimeInternal.times)

operator fun TimeInterval.times(times : Int) = RepeatedTimeInternal(this, times)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator() = object: Iterator<MyDate> {
        var current = start

        override fun hasNext() = current <= endInclusive

        override fun next(): MyDate {
            val result = current
            current = current.nextDay()
            return result
        }
    }
}

data class RepeatedTimeInternal(val interval: TimeInterval, val times: Int)
