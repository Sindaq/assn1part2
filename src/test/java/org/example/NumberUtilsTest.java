package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

class NumberUtilsTest {

    NumberUtils util = new NumberUtils();
    /**
     *
     * Step 1: understand the requirement, input type and output type
     *        Requirement: Add two list of integer, index by index, and returns another list
     *        Input type:
     *          Left: Integer List
     *          Right: Integer List
     *        Output type: Integer List
     * Step 2 (raw):  Perform partition and boundary analysis on input and output
     *        Each input: left | right
     *          Left | Right:
     *              Partitions:
     *                  Empty List - Valid
     *                  Null List - Invalid
     *                  Integer List w/ non integers - Invalid
     *                  Integer List w/ negative integers - Invalid
     *                  Integer List w/ positive integers, 0-9 - Valid
     *              Boundaries:
     *                  On-point: 0, 9
     *                  Off-point: -1, 10
     *        Combination of input:
     *          Left & Right:
     *              Partitions:
     *                  Left || Right Empty List - Valid
     *                  Left || Right Null List - Invalid
     *                  Left || Right Integer List w/ non integers - Invalid
     *                  Left || Right Integer List w/ negative integers - Invalid
     *                  Left && Right Integer Lists w/ positive integers, 0-9 - Valid
     *              Boundaries:
     *                  On-point: Left && Right Integer List length == 0
     *                  Off-point None
     *        Output:
     *              Partitions:
     *                  Empty List - Invalid
     *                  Null List - Invalid
     *                  Integer List w/ non integers - Invalid
     *                  Integer List w/ negative integers - Invalid
     *                  Integer List w/ positive integers, 0-9 - Valid
     *              Boundaries:
     *                  On-point: Integer List length == 1
     *                  Off-point: Integer List length == 0
     *  Step 3: Derive potential test cases
     *         1. Left = [], Right = [2], Output = [2]
     *         2. Left = [1], Right = [], Output = [1]
     *         3. Left = null, Right = [2], Output = null
     *         4. Left = [1], Right = null, Output = null
     *         5. Left = ["A"], Right = [2], Output = IllegalArgumentException
     *              Unnecessary because add() type checks
     *         6. Left = [1], Right = ["B"], Output = IllegalArgumentException
     *              Unnecessary because add() type checks
     *         7. Left = [-1], Right = [2], Output = IllegalArgumentException
     *         8. Left = [1], Right = [-2], Output = IllegalArgumentException
     *         9. Left = [11], Right = [2], Output = IllegalArgumentException
     *         10. Left = [1], Right = [12], Output = IllegalArgumentException
     *         11. Left = [1], Right = [2], Output = [3]
     *         12. Left = [1,2], Right = [3,4], Output = [4,6]
     *         13. Left = [5,6], Right = [7,8], Output = [1,3,4]
     *         14. Left = [1], Right = [2,3], Output = [2,4]
     *         15. Left = [0,1], Right = [2], Output = [3]
     *         16. Left = [], Right = [], Output = [0]
     *
     */

    @Test
    @Tag("left: Empty")
    void leftEmpty() {
        // Test case #1

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(2);
        right.add(2);

        assertEquals(expected, util.add(left, right));
    }

    @Test
    @Tag("right: Empty")
    void rightEmpty() {
        // Test case #2

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(1);
        left.add(1);

        assertEquals(expected, util.add(left, right));
    }



    @Test
    @Tag("left: Null")
    void leftNull() {
        // Test case #3

        List<Integer> right = new ArrayList<>();

        right.add(2);

        assertNull(util.add(null, right));
    }

    @Test
    @Tag("right: Null")
    void rightNull() {
        // Test case #4

        List<Integer> left = new ArrayList<>();

        left.add(1);

        assertNull(util.add(left, null));
    }

    @Test
    @Tag("left: Negative Integer")
    void leftNegative() {
        // Test case #7

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        right.add(2);
        left.add(-1);

       assertThrows(IllegalArgumentException.class, () -> {
            util.add(left, right);
        });
    }

    @Test
    @Tag("right: Negative Integer")
    void rightNegative() {
        // Test case #8

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        right.add(-2);
        left.add(1);

       assertThrows(IllegalArgumentException.class, () -> {
            util.add(left, right);
        });
    }

    @Test
    @Tag("left: Above 9")
    void leftGreater() {
        // Test case #9

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        left.add(10);
        right.add(2);


       assertThrows(IllegalArgumentException.class, () -> {
            util.add(left, right);
        });
    }

    @Test
    @Tag("right: Above 9")
    void rightGreater() {
        // Test case #10

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        left.add(1);
        right.add(12);

       assertThrows(IllegalArgumentException.class, () -> {
            util.add(left, right);
        });
    }

    @Test
    @Tag("output: Single digit")
    void outputSingleDigits() {
        // Test case #11

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(3);
        left.add(1);
        right.add(2);

       assertEquals(expected, util.add(left, right));
    }

    @Test
    @Tag("output: Multiple digits")
    void outputMultipleDigits() {
        // Test case #12

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(2);
        expected.add(3);
        left.add(1);
        left.add(1);
        right.add(1);
        right.add(2);

       assertEquals(expected, util.add(left, right));
    }

    @Test
    @Tag("increase mutation coverage")
    void outputMultipleDigits1() {
        // Test case #12

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(1);
        expected.add(3);
        expected.add(5);
        left.add(1);
        left.add(2);
        left.add(3);
        right.add(1);
        right.add(2);

        assertEquals(expected, util.add(left, right));
    }

    @Test
    @Tag("output: Length > Greater Left | Right Length")
    // Output length is greater than greater of (left | right) length
    void outputGreaterLength() {
        // Test case #13

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(1);
        expected.add(3);
        expected.add(4);
        left.add(5);
        left.add(6);
        right.add(7);
        right.add(8);

       assertEquals(expected, util.add(left, right));
    }

    @Test
    @Tag("output: Length = Greater Length of Left | Right")
    // Output length is equal length to greater of (left | right) length
    void outputEqualLength() {
        // Test case #14

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(2);
        expected.add(4);
        left.add(1);
        right.add(2);
        right.add(3);

       assertEquals(expected, util.add(left, right));
    }

    @Test
    @Tag("output: Length = Lesser Length of Left | Right")
    // Output length is equal length to lesser of (left | right) length
    void outputLesserLength() {
        // Test case #15

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        expected.add(4);
        left.add(1);
        right.add(0);
        right.add(3);

       assertEquals(expected, util.add(left, right));
    }

    @Test
    @Tag("left: empty, right: empty, output: [0]")
    void emptyLeftRight() {
        // Test case #15

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

//        expected.add(0);

       assertEquals(expected, util.add(left, right));
    }

    @Test()
    void badLeft() {
        // Test case #15

        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> l3 = new ArrayList<>();
        List<Integer> l4 = new ArrayList<>();
        List<Integer> good = new ArrayList<>();

        List<Integer> e3 = new ArrayList<>();
        List<Integer> e4 = new ArrayList<>();


        l1.add(-1); //bad, Left < 0
        l2.add(10); //bad, left >9
        l3.add(0);
        l4.add(9);
        good.add(1);

        e3.add(1);
        e4.add(1);
        e4.add(0);



        assertEquals(e3, util.add(l3, good));
        assertEquals(e4, util.add(l4, good));
        assertThrows(IllegalArgumentException.class, ()->util.add(l1, good));
        assertThrows(IllegalArgumentException.class, ()->util.add(l2, good));
    }

    @Test()
    void badRight() {
        // Test case #15

        List<Integer> good = new ArrayList<>();

        List<Integer> r1 = new ArrayList<>();
        List<Integer> r2 = new ArrayList<>();
        List<Integer> r3 = new ArrayList<>();
        List<Integer> r4 = new ArrayList<>();

        List<Integer> e3 = new ArrayList<>();
        List<Integer> e4 = new ArrayList<>();


        r1.add(-1); //bad, Left < 0
        r2.add(10); //bad, left >9
        r3.add(0);
        r4.add(9);
        good.add(1);

        e3.add(1);
        e4.add(1);
        e4.add(0);

        assertEquals(e3, util.add(good, r3));
        assertEquals(e4, util.add(good, r4));
        assertThrows(IllegalArgumentException.class, ()->util.add(good, r1));
        assertThrows(IllegalArgumentException.class, ()->util.add(good, r2));
    }

    @Test()
    void badBoth() {
        // Test case #15

        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();

        List<Integer> r1 = new ArrayList<>();
        List<Integer> r2 = new ArrayList<>();


        l1.add(-1); //bad, Left < 0
        r1.add(10); //bad, Left < 0

        l2.add(10); //bad, left >9
        r2.add(-1); //bad, left >9

        assertThrows(IllegalArgumentException.class, ()->util.add(l1, r1));
        assertThrows(IllegalArgumentException.class, ()->util.add(l2, r2));
    }

    @Test()
    void mutant() {

        List<Integer> empty = new ArrayList<>();
        List<Integer> l = new ArrayList<>();
        List<Integer> r = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        l.add(0);
        r.add(0);
        expected.add(0);

        assertEquals(expected, util.add(l, r));
        assertEquals(expected, util.add(l, empty));
        assertEquals(expected, util.add(empty, r));
    }

}