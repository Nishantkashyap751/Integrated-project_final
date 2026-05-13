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

        System.out.println("\n--- UC12: Subtraction & Division Operations ---");
        demonstrateSubtraction();
        demonstrateDivision();
    }

    private static void demonstrateSubtraction() {
        System.out.println("\n[Subtraction Demonstration]");
        
        // Length Subtraction
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> fiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
        System.out.println("10.0 FEET - 5.0 FEET = " + tenFeet.subtract(fiveFeet));

        Quantity<LengthUnit> sixInches = new Quantity<>(6.0, LengthUnit.INCH);
        System.out.println("10.0 FEET - 6.0 INCHES = " + tenFeet.subtract(sixInches));
        
        Quantity<LengthUnit> hundredTwentyInches = new Quantity<>(120.0, LengthUnit.INCH);
        System.out.println("120.0 INCHES - 5.0 FEET = " + hundredTwentyInches.subtract(fiveFeet));

        // Volume Subtraction
        Quantity<VolumeUnit> oneGallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> oneLitre = new Quantity<>(1.0, VolumeUnit.LITRE);
        System.out.println("1.0 Gallon - 1.0 Litre (in Gallons) = " + oneGallon.subtract(oneLitre));
    }

    private static void demonstrateDivision() {
        System.out.println("\n[Division Demonstration]");
        
        // Dimensionless Ratio
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> fiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
        System.out.println("10.0 FEET / 5.0 FEET = " + tenFeet.divide(fiveFeet));

        Quantity<VolumeUnit> oneGallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> oneLitre = new Quantity<>(1.0, VolumeUnit.LITRE);
        System.out.println("1.0 Gallon / 1.0 Litre = " + oneGallon.divide(oneLitre));
    }
}
