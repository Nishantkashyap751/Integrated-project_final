package QuantityMeasurementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.enums.WeightUnit;
import com.quantity.measurement.enums.VolumeUnit;
import com.quantity.measurement.model.Quantity;

@SpringBootApplication
public class MeasurementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeasurementApplication.class, args);

        System.out.println("--- UC11: Volume Measurement Demonstration ---");

        // Equality Comparisons
        Quantity<VolumeUnit> oneLitre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> thousandML = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        System.out.println("1.0 Litre == 1000.0 Millilitre: " + oneLitre.equals(thousandML));

        Quantity<VolumeUnit> oneGallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> litreFromGallon = new Quantity<>(3.78541, VolumeUnit.LITRE);
        System.out.println("1.0 Gallon == 3.78541 Litre: " + oneGallon.equals(litreFromGallon));

        // Unit Conversions
        Quantity<VolumeUnit> convertedLitre = oneGallon.convertTo(VolumeUnit.LITRE);
        System.out.println("1.0 Gallon converted to Litre: " + convertedLitre);

        Quantity<VolumeUnit> convertedML = oneLitre.convertTo(VolumeUnit.MILLILITRE);
        System.out.println("1.0 Litre converted to Millilitre: " + convertedML);

        // Addition Operations
        Quantity<VolumeUnit> sumLitre = oneLitre.add(new Quantity<>(2.0, VolumeUnit.LITRE));
        System.out.println("1.0 Litre + 2.0 Litre = " + sumLitre);

        Quantity<VolumeUnit> sumMixed = oneLitre.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        System.out.println("1.0 Litre + 1000.0 Millilitre = " + sumMixed);

        // Category Incompatibility (Cross-Category Type Safety)
        Quantity<LengthUnit> oneFeet = new Quantity<>(1.0, LengthUnit.FEET);
        System.out.println("1.0 Litre == 1.0 Feet: " + oneLitre.equals(oneFeet));

        Quantity<WeightUnit> oneKG = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        System.out.println("1.0 Litre == 1.0 Kilogram: " + oneLitre.equals(oneKG));
    }
}
