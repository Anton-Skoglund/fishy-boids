import org.example.fishyboids.Util.Point;
import org.example.fishyboids.Util.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class VectorTest {


    // <init
    @Test
    public void init_amountElementsZero_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            Vector vec = new Vector(0);
        });
    }

    @Test
    public void init_AmountOfelemnts3_newVectorOfAmountElements3(){
        Vector vec = new Vector(3);
        assertEquals(vec.amountElements(), 3);
    }

    @Test
    public void init_FromDoubleArray_newVectorFullOfValues(){
        Vector vec = new Vector(new double[] {1, 2 ,3});
        assertArrayEquals(vec.getArray(), new double[] {1, 2, 3});
    }

    @Test
    public void init_FromPointsNegPosQuad_newVectorFullOfValues(){
        // x1 > x2
        // y1 < y2
        Vector vec = new Vector(new Point(10, 10), new Point(5, 15));
        assertArrayEquals(vec.getArray(), new double[] {-5, 5});
    }

    @Test
    public void init_FromPointsPosPosQuad_newVectorFullOfValues(){
        // x1 < x2
        // y1 < y2
        Vector vec = new Vector(new Point(10, 10), new Point(15, 15));
        assertArrayEquals(vec.getArray(), new double[] {5, 5});
    }

    @Test
    public void init_FromPointsPosNegQuad_newVectorFullOfValues(){
        // x1 < x2
        // y1 > y2
        Vector vec = new Vector(new Point(10, 10), new Point(15, 5));
        assertArrayEquals(vec.getArray(), new double[] {5, -5});
    }

    @Test
    public void init_FromPointsNegNegQuad_newVectorFullOfValues(){
        // x1 > x2
        // y1 > y2
        Vector vec = new Vector(new Point(10, 10), new Point(5, 5));
        assertArrayEquals(vec.getArray(), new double[] {-5, -5});
    }
    // init>


    // <length
    @Test
    public void getLength_twoFullVectors_lengthOfVector(){
        // x1 > x2
        // y1 > y2
        Vector vec = new Vector(new double[] {1, 2});
        assertEquals(vec.getLength(), Math.sqrt(5), 0.01);
    }

    @Test
    public void getLength_twoFullVectorsLonger_lengthOfVector(){
        // x1 > x2
        // y1 > y2
        Vector vec = new Vector(new double[] {1, 2, 4, 5, 6});
        assertEquals(vec.getLength(), Math.sqrt(82), 0.01);
    }
    // length>


    // <get
    @Test
    public void get_IndexOverBound_throwIndexOutOfBound(){
        Vector vec = new Vector(1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.get(1);
        });
    }

    @Test
    public void get_IndexUnderBound_throwIndexOutOfBound(){
        Vector vec = new Vector(1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.get(-1);
        });
    }

    @Test
    public void get_valueOnIndex(){
        Vector vec = new Vector(new double[] {1, 2 ,3});
        assertEquals(vec.get(0), 1);
    }

    @Test
    public void get_RangeSameIndex_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2, 3});
        assertArrayEquals(new double[] {2}, vec.get(1, 2));
    }

    @Test
    public void get_RangeIndexFromOverBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.get(7, 8);
        });
    }

    @Test
    public void get_RangeIndexFromUnderBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.get(-1, 2);
        });
    }

    @Test
    public void get_RangeIndexToOverBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.get(2, 7);
        });
    }

    @Test
    public void get_RangeIndexToUnderBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.get(-4, -2);
        });
    }

    @Test
    public void get_RangeIndexFromLargerThanTo_throwIndexOutOfBoundsException(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.get(4, 2);
        });
    }

    @Test
    public void get_RangeIndexFromEqualTo_throwIllegalArgumentException(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IllegalArgumentException.class, () -> {
            vec.get(2, 2);
        });
    }

    @Test
    public void get_RangeIndexGetWholeArray_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertArrayEquals(vec.get(0, vec.amountElements()), new double[] {1, 2, 3, 4, 5});
    }

    @Test
    public void get_Range_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertArrayEquals(vec.get(1, 3), new double[] {2, 3});
    }
    // get>



    // <getArray
    @Test
    public void getArray_fullArray_inputArray(){
        Vector vec = new Vector(new double[] {1, 2 ,3});
        assertArrayEquals(vec.getArray(), new double[] {1, 2, 3});
    }

    @Test
    public void getArray_emptyArray_inputArray(){
        Vector vec = new Vector(new double[] {});
        assertArrayEquals(vec.getArray(), new double[] {});
    }
    // getArray>



    // <set
    @Test
    public void set_ValueOverBound_throwIndexOutOfBoundsException(){
        Vector vec = new Vector(new double[] {1, 2 ,3});

        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.set(3, 2);
        });
    }

    @Test
    public void set_ValueUnderBound_throwIndexOutOfBoundsException(){
        Vector vec = new Vector(new double[] {1, 2 ,3});

        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.set(-1, 2);
        });
    }

    @Test
    public void set_ValueOnIndex_inputArrayChanged(){
        Vector vec = new Vector(new double[] {1, 2 ,3});
        vec.set(1, 0);

        assertArrayEquals(new double[] {1, 0, 3}, vec.getArray());
    }

    @Test
    public void set_ValueFromtoToIndex_inputArray(){
        Vector vec = new Vector(new double[] {1, 2 ,3});
        vec.set(1, 0);

        assertArrayEquals(new double[] {1, 0, 3}, vec.getArray());
    }


    @Test
    public void set_rangeWholeAmountElements_changedVector(){
        Vector vec = new Vector(new double[] {1, 2 ,3});
        vec.set(0, 3, 0.0);

        assertArrayEquals(vec.getArray(), new double[] {0, 0, 0});
    }

    @Test
    public void set_rangeSubAmountElements_changedVector(){
        Vector vec = new Vector(new double[] {1, 2 ,3});
        vec.set(1, 2, 0.0);

        assertArrayEquals(vec.getArray(), new double[] {1, 0, 3});
    }

    @Test
    public void set_rangeFromEqualTo_throwIllegalArgumentException(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IllegalArgumentException.class, () -> {
            vec.set(2, 2, 0.0);
        });
    }

    @Test
    public void set_rangeFromOverBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.set(7, 8, 0.0);
        });
    }

    @Test
    public void set_rangeFromUnderBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.set(-1, 2, 0.0);
        });
    }

    @Test
    public void set_rangeToOverBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.set(2, 7, 0.0);
        });
    }

    @Test
    public void set_rangeToUnderBound_newArrayWithValueFromInput(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.set(-4, -2, 0.0);
        });
    }

    @Test
    public void set_rangeFromLargerThanTo_throwIndexOutOfBoundsException(){
        Vector vec = new Vector(new double[] {1, 2 ,3, 4, 5});
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vec.set(4, 2, 0.0);
        });
    }
    // set>


    // <add
    @Test
    public void add_twoFullOfSameAmountOfelemnts_newSumOfSameAmountElements(){
        Vector vec1 = new Vector(new double[] {1, 2, 3});
        Vector vec2 = new Vector(new double[] {1, 2, 3});

        Vector sumVec = vec1.add(vec2);

        assertArrayEquals(sumVec.getArray(), new double[] {2, 4, 6});

    }

    @Test
    public void add_twoEmptyOfSameAmountOfelemnts_newEmptyOfSameAmountElements(){
        Vector vec1 = new Vector(1);
        Vector vec2 = new Vector(1);

        Vector sumVec = vec1.add(vec2);
        assertArrayEquals(sumVec.getArray(), new double[] {0});
    }

    @Test
    public void add_ofDifferent_throwIllegalArgumentException(){
        Vector vec1 = new Vector(1);
        Vector vec2 = new Vector(2);

        assertThrows(IllegalArgumentException.class, () -> {
            vec1.add(vec2);
        });
    }
    // add>


    // <scale
    @Test
    public void scale_fullWithScalar_sameVectorDifferentAmountElements(){
        Vector vec = new Vector(new double[] {1, 2, 3});
        vec.scale(2.0);
        assertArrayEquals(vec.getArray(), new double[] {2, 4, 6});
    }

    @Test
    public void scale_fullWithNegScaler_sameVectorDifferentAmountElements(){
        Vector vec = new Vector(new double[] {1, 2, 3});
        vec.scale(-2.0);
        assertArrayEquals(vec.getArray(), new double[] {-2, -4, -6});
    }

    @Test
    public void scale_emptyWithDouble_sameVectorDifferentAmountElements(){
        Vector vec = new Vector(2);
        vec.scale(2.0);
        assertArrayEquals(vec.getArray(), new double[] {0, 0});
    }
    // scale>


    // <dotProduct
    @Test
    public void dotProduct_twoFullOfSameAmountOfelemnts_newProductOfSameAmountElements(){
        Vector vec1 = new Vector(new double[] {1, 2, 3});
        Vector vec2 = new Vector(new double[] {1, 2, 3});

        Vector productVector = vec1.dotProduct(vec2);

        assertArrayEquals(productVector.getArray(), new double[] {1, 4, 9});

    }

    @Test
    public void dotProduct_twoEmptyOfSameAmountOfelemnts_newEmptyOfSameAmountElements(){
        Vector vec1 = new Vector(1);
        Vector vec2 = new Vector(1);

        Vector productVector = vec1.dotProduct(vec2);
        assertArrayEquals(productVector.getArray(), new double[] {0});
    }

    @Test
    public void dotProduct_ofDifferent_throwIllegalArgumentException(){
        Vector vec1 = new Vector(1);
        Vector vec2 = new Vector(2);

        assertThrows(IllegalArgumentException.class, () -> {
            vec1.dotProduct(vec2);
        });
    }
    // dotProduct>


    // <crossProduct
    @Test
    public void crossProduct_twoFullOfSameAmountOfelemnts_newProductOfSameAmountElements(){
        Vector vec1 = new Vector(new double[] {1, 2, 3});
        Vector vec2 = new Vector(new double[] {4, 5, 6});

        Vector productVector = vec1.crossProduct(vec2);

        assertArrayEquals(productVector.getArray(), new double[] {-3, 6, -3});

    }

    @Test
    public void crossProduct_twoEmptyOfSameAmountOfelemnts_newEmptyOfSameAmountElements(){
        Vector vec1 = new Vector(3);
        Vector vec2 = new Vector(3);

        Vector productVector = vec1.crossProduct(vec2);
        assertArrayEquals(productVector.getArray(), new double[] {0, 0, 0});
    }

    @Test
    public void crossProduct_ofDifferent_throwIllegalArgumentException(){
        Vector vec1 = new Vector(2);
        Vector vec2 = new Vector(2);

        assertThrows(IllegalArgumentException.class, () -> {
            vec1.crossProduct(vec2);
        });
    }
    // crossProduct>

}
