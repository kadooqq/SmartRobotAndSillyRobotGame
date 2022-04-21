package model.tests;

import model.fieldObjects.landscape.SwampSegment;
import model.fieldObjects.landscape.characteristics.ViscosityCharacteristic;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SwampSegmentTest {
    @Test
    public void swampSegmentTest() {
        SwampSegment swampSegment = new SwampSegment(1);

        Assertions.assertTrue(swampSegment.getCharacteristic() instanceof ViscosityCharacteristic);
        Assertions.assertEquals(1, swampSegment.getCharacteristic().getCoefficient());
    }
}
