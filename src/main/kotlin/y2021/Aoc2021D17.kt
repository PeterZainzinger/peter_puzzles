package y2021

import shared.Point
import shared.splitLines
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.absoluteValue

typealias Velocity = Point

object Aoc2021D17 {


    data class TargetArea(val x: IntRange, val y: IntRange) {
        fun isInside(p: Point) = p.x in x && p.y in y
        fun canStillHit(p: Point) = p.x <= x.last + (x.last - x.first) && p.y + (y.last - y.first) > y.last
    }


    fun findHighestYValueCanHit(area: TargetArea): Int {
        val hits =
            (0..100).map { it to canHitWithY(area, Point.zero, it, area.x.last) }.filter { it.second.isNotEmpty() }
        val yValue = hits.maxByOrNull { it.first }!!.first
        val allVelocities = hits.filter { it.first == yValue }.flatMap { it.second }
        return allVelocities.flatMap { traj(area, Point.zero, it) }.maxByOrNull { it.y }!!.y
    }

    fun findCount(area: TargetArea): Int {
        val hits =
            (-100..100).map { it to canHitWithY(area, Point.zero, it, area.x.last) }.filter { it.second.isNotEmpty() }

        val possible = hits.flatMap { it.second }.toSet()
        return possible.size

    }

    private fun canHitWithY(area: TargetArea, start: Point, y: Int, xLimit: Int): List<Velocity> =
        (-xLimit..xLimit).map { Velocity(it, y) }.filter {
            willHit(area, start, it)
        }


    private fun willHit(area: TargetArea, start: Point, v: Velocity): Boolean {
        val startValue = start to v
        val traj = mutableListOf(startValue)
        while (area.canStillHit(traj.last().first)) {
            val last = traj.last()
            val newValue = nextState(last.first, last.second)
            traj.add(newValue)

            if (area.isInside(newValue.first)) {
                return true
            }
        }
        return false

    }

    private fun traj(area: TargetArea, start: Point, v: Velocity): List<Point> {
        val startValue = start to v
        val traj = mutableListOf(startValue)
        while (area.canStillHit(traj.last().first)) {
            val last = traj.last()
            val newValue = nextState(last.first, last.second)
            traj.add(newValue)
            if (area.isInside(newValue.first)) {
                break
            }
        }
        return traj.map { it.first }

    }

    private fun nextState(location: Point, velocity: Velocity): Pair<Point, Velocity> {
        val nextPoint = location + velocity
        val nextVelocity = Velocity(
            when {
                velocity.x == 0 -> 0
                velocity.x > 0 -> velocity.x - 1
                else -> velocity.x + 1
            }, velocity.y - 1
        )
        return nextPoint to nextVelocity
    }


}


