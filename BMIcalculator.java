 import java.util.Scanner;

 class BMIcalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your weight (in kilograms): ");
        double weight = scanner.nextDouble();

        System.out.print("Enter your height (feet): ");
        int feet = scanner.nextInt();

        System.out.print("Enter your height (inches): ");
        int inches = scanner.nextInt();

        double heightInMeters = convertToMeters(feet, inches);
        double bmi = calculateBMI(weight, heightInMeters);
        String bmiCategory = getBMICategory(bmi);

        System.out.printf("Your BMI is: %.2f%n", bmi);
        System.out.println("BMI Category: " + bmiCategory);

        scanner.close();
    }

    public static double convertToMeters(int feet, int inches) {
        double totalInches = feet * 12 + inches;
        return totalInches * 0.0254; // 1 inch = 0.0254 meters
    }

    public static double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    public static String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Normal weight";
        } else {
            return "Overweight";
        }
    }
}