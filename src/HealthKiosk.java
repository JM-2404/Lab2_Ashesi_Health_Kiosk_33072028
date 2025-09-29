import java.util.Scanner;
import java.util.Random;

public class HealthKiosk {
    public static void main(String[] args) {
        Scanner health = new Scanner(System.in);
        Random random = new Random();


        // Welcome message
        System.out.println("Welcome to Ashesi Health Center Self-Service Kiosk!");
        System.out.println();

        // TASK 1: Service Router
        System.out.print("Enter service code (P/L/T/C): ");
        char serviceCode = health.next().charAt(0);

        // Convert to uppercase for case-insensitive comparison
        serviceCode = Character.toUpperCase(serviceCode);

        String serviceName;
        switch (serviceCode) {
            case 'P':
                serviceName = "PHARMACY";
                System.out.println("Go to: Pharmacy Desk");
                break;
            case 'L':
                serviceName = "LAB";
                System.out.println("Go to: Lab Desk");
                break;
            case 'T':
                serviceName = "TRIAGE";
                System.out.println("Go to: Triage Desk");
                break;
            case 'C':
                serviceName = "COUNSELING";
                System.out.println("Go to: Counseling Desk");
                break;
            default:
                System.out.println("Invalid service code");
                return; // Exit the program if invalid code
        }
        System.out.println();

        // TASK 2: Mini Health Metric (only for Triage)
        double metricValue = 0.0;
        String metricDisplay = "";

        if (serviceCode == 'T') {
            System.out.println("Health Metric Options:");
            System.out.println("1 - BMI quick calc");
            System.out.println("2 - Dosage round-up");
            System.out.println("3 - Simple trig helper");
            System.out.print("Enter your choice (1/2/3): ");
            int choice = health.nextInt();

            if (choice == 1) {
                // Option A: BMI calculation
                System.out.print("Enter weight(kg): ");
                double weight = health.nextDouble();
                System.out.print("Enter height(m): ");
                double height = health.nextDouble();

                // Calculate BMI
                double bmi = weight / Math.pow(height, 2);
                // Round to 1 decimal place
                bmi = Math.round(bmi * 10) / 10.0;
                metricValue = bmi;

                // Determine category
                String category;
                if (bmi < 18.5) {
                    category = "Underweight";
                } else if (bmi <= 24.9) {
                    category = "Normal";
                } else if (bmi <= 29.9) {
                    category = "Overweight";
                } else {
                    category = "Obese";
                }

                System.out.println("BMI: " + bmi + " Category: " + category);
                metricDisplay = "BMI=" + bmi;

            } else if (choice == 2) {
                // Option B: Dosage calculation
                System.out.print("Enter required dosage (mg): ");
                double dosage = health.nextDouble();

                // Calculate tablets needed (250mg per tablet)
                double tabletsNeeded = dosage / 250.0;
                int tablets = (int) Math.ceil(tabletsNeeded);
                metricValue = tablets;

                System.out.println("Tablets needed: " + tablets);
                metricDisplay = "Tablets=" + tablets;

            } else if (choice == 3) {
                // Option C: Trigonometry helper
                System.out.print("Enter angle in degrees: ");
                double degrees = health.nextDouble();

                // Convert to radians
                double radians = Math.toRadians(degrees);

                // Calculate sin and cos
                double sinValue = Math.sin(radians);
                double cosValue = Math.cos(radians);

                // Round to 3 decimal places
                sinValue = Math.round(sinValue * 1000) / 1000.0;
                cosValue = Math.round(cosValue * 1000) / 1000.0;
                metricValue = sinValue;

                System.out.println("sin(" + degrees + "°) = " + sinValue);
                System.out.println("cos(" + degrees + "°) = " + cosValue);
                metricDisplay = "Sin=" + sinValue;
            }
        }
        System.out.println();
        // TASK 3: ID Sanity Check
        // Generate random ID
        char randomChar = (char) ('A' + random.nextInt(26)); // Random A-Z
        int digit1 = random.nextInt(7) + 3; // Random 3-9
        int digit2 = random.nextInt(7) + 3;
        int digit3 = random.nextInt(7) + 3;
        int digit4 = random.nextInt(7) + 3;

        String shortID = "" + randomChar + digit1 + digit2 + digit3 + digit4;

        // Validate the generated ID
        boolean isValid =true;
        String errorMessage = "";

        if (shortID.length() != 5) {
            isValid = false;
            errorMessage = "Invalid length";
        } else if (!Character.isLetter(shortID.charAt(0))) {
            isValid = false;
            errorMessage = "Invalid: first char must be a letter";
        } else {
            // Check if positions 1-4 are digits
            for (int i = 1; i <= 4; i++) {
                if (!Character.isDigit(shortID.charAt(i))) {
                    isValid = false;
                    errorMessage = "Invalid: last 4 must be digits";
                    break;
                }
            }
        }

        if (isValid) {
            System.out.println("ID OK");
            System.out.println("Generated ID: " + shortID);
        } else {
            System.out.println(errorMessage);
        }
        System.out.println();

        // TASK 4: "Secure" Display Code
        System.out.print("Enter your first name: ");
        String firstName = health.next();

        // Get first character and convert to uppercase
        char baseCode = Character.toUpperCase(firstName.charAt(0));
        System.out.println("Base code = " + baseCode);

        // Shift letter forward by 2 positions (with wrap-around)
        char shiftedLetter = (char) ('A' + (baseCode - 'A' + 2) % 26);
        System.out.println("Shifted letter of base code = " + shiftedLetter);

        // Get last two characters of ID
        String lastTwo = shortID.substring(3, 5);
        System.out.println("Last two characters for ID (task 3): " + lastTwo);

        // Get metric value as integer
        int metricInt;
            if (serviceCode == 'T') {
            // For different metric types, handle differently
                if (metricDisplay.contains("BMI")) {
                metricInt = (int) Math.round(metricValue);
                } else if (metricDisplay.contains("Tablets")) {
                metricInt = (int) metricValue;
                } else { // Trig helper
                metricInt = (int) Math.round(metricValue * 100);
                }
            } else {
            metricInt = 0; // Default for non-triage services
            }
        String displayCode = shiftedLetter + lastTwo + "-" + metricInt;
        System.out.println("Display Code: " + displayCode);
        System.out.println();

        // TASK 5: Service Summary
        System.out.print("Summary: " + serviceName + " | ID=" + shortID);

        // Add metric info only for Triage
        if (serviceCode == 'T' && !metricDisplay.isEmpty()) {
            System.out.print(" | " + metricDisplay);
        }

        System.out.println(" | Code=" + displayCode);

        health.close();

    }

}


