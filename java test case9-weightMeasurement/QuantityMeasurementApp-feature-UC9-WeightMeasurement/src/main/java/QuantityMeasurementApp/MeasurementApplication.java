package QuantityMeasurementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;

//@SpringBootApplication
public class MeasurementApplication {
/* 
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    public static class Inch {
        private final double value;   

        public Inch(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inch other = (Inch) obj;
            return Double.compare(this.value, other.value) == 0; 
        }
    }*/

    public static void main(String[] args) {
        SpringApplication.run(MeasurementApplication.class, args);
/* 
        Feet f1 = new Feet(34.5);
        Feet f2 = new Feet(34.5);

        Inch i1 = new Inch(12.0);
        Inch i2 = new Inch(12.0);

        System.out.println(f1.equals(f2)); // true
        System.out.println(i1.equals(i2)); // true*/

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

		QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);

		QuantityLength q5 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		QuantityLength q6 = new QuantityLength(0.393701, LengthUnit.INCH);

		System.out.println(q1.equals(q2) ? "Equal (true)" : "Not Equal (false)");
		System.out.println(q3.equals(q4) ? "Equal (true)" : "Not Equal (false)");
		System.out.println(q5.equals(q6) ? "Equal (true)" : "Not Equal (false)");
	}



    }
