/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package sets;

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {

  private FiniteSet points;

  private boolean complement;

  // DONE: fill in and document the representation
  //       Make sure to include the representation invariant (RI)
  //       and the abstraction function (AF).

  // Representation Invariant:
  // points != null has no NaNs, no infinities, and no duplicates
  // complement != null

  // Abstraction Function:
  // AF(this) = Set of numbers represented by
  //                points if complement == false
  //                Complement of points if complement == true

  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    // DONE: implement this
    this(vals, false);
  }

  public SimpleSet(float[] vals, boolean complement) {
    this.points = FiniteSet.of(vals);
    this.complement = complement;
  }

  public SimpleSet(FiniteSet points, boolean complement) {
    this.points = points;
    this.complement = complement;
  }

  // HINT: feel free to create other constructors!

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;
    return points.equals(other.points) && complement == other.complement;
    // DONE: replace this with a correct check
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    // DONE: implement this
    if (!complement) {
      // In this case, this is finite, so the size of the set is finite.
      // Specifically, points.size()
      return (float) points.size();
    } else {
      // In this case, this is infinite, so the size of the set is
      // positive infinity.
      return Float.POSITIVE_INFINITY;
    }
    // DONE: you should replace this value
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers. These
   *     floats will be turned into strings in the standard manner (the same as
   *     done by, e.g., String.valueOf(float)).
   */
  public String toString() {
    // DONE: implement this with a loop. document its invariant
    //       a StringBuilder may be useful for creating the string
    StringBuilder buf = new StringBuilder();
    if (complement && this.points.size() == 0) {
      // If the set is infinite and no element is excluded, we should return
      // the string "R".
      return "R";
    }
    if (this.points.size() > 0) {
      List<Float> pointsList = points.getPoints();
      // If points only has one element, there shouldn't be a ", " in the string.
      buf.append(pointsList.get(0));
      int i = 1;
      // Inv: pointsList = pL
      //      buf = pL.get(0), ", ", pL.get(1), ", ",..., pL.get(i - 1)
      while (i < this.points.size()) {
        buf.append(", " + pointsList.get(i));
        i++;
      }
    }
    if (complement) {
      return "R \\ {" + buf.toString() + "}";
    } else {
      return "{" + buf.toString() + "}";
    }
    // DONE: you should replace this value
  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {
    // DONE: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)

    // Case 1: this is finite (complement == false)
    // The SimpleSet contains the elements of points, so we return a new set
    // containing the complement of points by setting complement to true.
    // Case 2: this is infinite (complement == true)
    // The SimpleSet contains the complement of points, so we return a new set
    // containing the elements of points by setting complement to false.

    return new SimpleSet(points, !complement);  // DONE: you should replace this value
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {
    // DONE: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    if (this.complement && other.complement) {
      // In this case, both sets are infinite, so the resulting union should also
      // be infinite, excluding the elements that are not included in any set.
      // this.points is not in this and other.points is not in other, so the elements
      // that are included in both are not in the union.
      return new SimpleSet(this.points.intersection(other.points), true);
    } else if (this.complement && !other.complement) {
      // If this is infinite and other is finite, the resulting union should also
      // be infinite, excluding the elements that are not included in any set.
      // this.points is not in this and other.points is in other, so the elements
      // that are included in this.points but not in other.points are not in the union.
      return new SimpleSet(this.points.difference(other.points), true);
    } else if (!this.complement && other.complement) {
      // If this is finite and other is infinite, the resulting union should also
      // be infinite, excluding the elements that are not included in any set.
      // this.points is in this and other.points is not in other, so the elements
      // that are included in other.points but not in this.points are not in the union.
      return new SimpleSet(other.points.difference(this.points), true);
    } else {
      // If this is finite and other is finite, the resulting union should also
      // be finite, including only the elements that are in either set.
      return new SimpleSet(this.points.union(other.points), false);
    }
    // DONE: you should replace this value
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersected with other
   */
  public SimpleSet intersection(SimpleSet other) {
    // DONE: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.
    if (this.complement && other.complement) {
      // In this case, both sets are infinite, so the resulting intersection should
      // also be infinite, including only the elements that are in both sets.
      // this.points is not in this and other.points is not in other, so any element
      // in either of these is not in the intersection.
      return new SimpleSet(this.points.union(other.points), true);
    } else if (this.complement && !other.complement) {
      // If this is infinite and other is finite, the resulting intersection should
      // also be finite, including only the elements that are in both sets.
      // this.points is not in this and other.points is in other, so any element
      // that is in other.points and not in this.points should be included.
      return new SimpleSet(other.points.difference(this.points), false);
    } else if (!this.complement && other.complement) {
      // If this is finite and other is infinite, the resulting intersection should
      // also be finite, including only the elements that are in both sets.
      // this.points is in this and other.points is not in other, so any element
      // that is in this.points and not in other.points should be included.
      return new SimpleSet(this.points.difference(other.points), false);
    } else {
      // If both sets are finite, the resulting intersection should also be finite,
      // including only the elements that are in both sets.
      return new SimpleSet(this.points.intersection(other.points), false);
    }
    // DONE: you should replace this value
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {
    // DONE: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.
    if (this.complement && other.complement) {
      // If both sets are infinite, then the resulting difference should be finite,
      // including only the elements that are in this but not other.
      // this.points is not in this and other.points is not in other, so any element
      // that is in other.points but not this.points should be included in the
      // difference.
      return new SimpleSet(other.points.difference(this.points), false);
    } else if (this.complement && !other.complement) {
      // If this is infinite and other is finite, then the resulting difference should
      // be infinite, including only the elements that are in this but not other.
      // this.points is not in this and other.points is in other, so any element
      // that is in this.points or other.points should be excluded from the difference.
      return new SimpleSet(this.points.union(other.points), true);
    } else if (!this.complement && other.complement) {
      // If this is finite and other is infinite, then the resulting difference should
      // be finite, including only the elements that are in this but not other.
      // this.points is in this and other.points is not in other, so any element
      // that is in both this.points and other.points should be included the difference.
      return new SimpleSet(this.points.intersection(other.points), false);
    } else {
      // If both sets are finite, then the resulting difference should be finite,
      // including only the elements that are in this but not other.
      return new SimpleSet(this.points.difference(other.points), false);
    }
    // DONE: you should replace this value
  }

}
