package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) :
    Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        val yearDiff = year - other.year
        if (yearDiff == 0) {
            val monthDiff = month - other.month
            if (monthDiff == 0) {
                return dayOfMonth - other.dayOfMonth
            }
            return monthDiff
        }
        return yearDiff
    }
}

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = this
    .addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(timeInterval: TimeInterval.MultipliedTimeInterval) =
    this.addTimeIntervals(timeInterval.timeInterval, timeInterval.times)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(number: Int) =
        MultipliedTimeInterval(this, number)

    class MultipliedTimeInterval(val timeInterval: TimeInterval, val times: Int)
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) :
    ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        override fun next(): MyDate {
            val next = currentDate
            currentDate = currentDate.nextDay()
            return next
        }

        var currentDate: MyDate = start
        override fun hasNext(): Boolean = currentDate <= endInclusive
    }
}
