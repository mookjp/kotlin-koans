package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {

    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}


operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval) = addTimeIntervals(interval, 1)
operator fun MyDate.plus(interval: RepeatedTimeIntervals) = addTimeIntervals(interval.interval, interval.times)

class RepeatedTimeIntervals(val interval: TimeInterval, val times: Int)
operator fun TimeInterval.times(times: Int) = RepeatedTimeIntervals(this, times)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return DateIterator(this)
    }

    operator fun contains(d: MyDate): Boolean {
        return d >= start && d <= endInclusive
    }
}

class DateIterator(val dateRange: DateRange): Iterator<MyDate> {
    private var current: MyDate = dateRange.start

    override fun hasNext(): Boolean = current <= dateRange.endInclusive

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }

}