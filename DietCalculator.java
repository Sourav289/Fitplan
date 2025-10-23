package service;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DietCalculator {
    public static class Meal {
        public String name;
        public String items;
        public double calories;
        public Meal(String name, String items, double calories) { this.name = name; this.items = items; this.calories = calories; }
    }

    public static class Plan {
        public double bmi;
        public double bmr;
        public double tdee;
        public double targetCalories;
        public double carbPercent = 50;
        public double proteinPercent = 25;
        public double fatPercent = 25;
        public List<Meal> meals = new ArrayList<>();
    }

    private List<String[]> foodDb = new ArrayList<>();

    public DietCalculator() {
        loadFoodDatabase();
    }

    private void loadFoodDatabase() {
        // data/foods.csv is included in the project
        String path = "data/foods.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                // format: type,food,cal
                String[] parts = line.split(",");
                if (parts.length >= 3) foodDb.add(parts);
            }
        } catch (Exception e) {
            // fallback: small embedded DB
            foodDb.add(new String[]{"Veg","Oats + Milk + Banana","350"});
            foodDb.add(new String[]{"NonVeg","Eggs + Toast","300"});
        }
    }

    private List<String[]> filterByFoodType(String foodType) {
        List<String[]> out = new ArrayList<>();
        for (String[] r : foodDb) {
            String t = r[0].trim();
            if (foodType.equalsIgnoreCase("Vegetarian") && (t.equalsIgnoreCase("Veg") || t.equalsIgnoreCase("Vegetarian"))) out.add(r);
            else if (foodType.equalsIgnoreCase("Non-Vegetarian") && (t.equalsIgnoreCase("NonVeg") || t.equalsIgnoreCase("Non-Vegetarian"))) out.add(r);
        }
        return out;
    }

    public Plan generatePlan(User user) {
        Plan p = new Plan();
        double heightM = user.getHeightCm() / 100.0;
        p.bmi = user.getWeightKg() / (heightM * heightM);

        // BMR - Mifflin St Jeor
        double W = user.getWeightKg();
        double H = user.getHeightCm();
        double A = user.getAge();
        double S = user.getGender().equalsIgnoreCase("Male") ? 5 : -161;
        p.bmr = 10 * W + 6.25 * H - 5 * A + S;

        // activity factor
        double af = 1.2;
        String activity = user.getActivityLevel();
        if (activity.equalsIgnoreCase("Lightly active")) af = 1.375;
        else if (activity.equalsIgnoreCase("Moderately active")) af = 1.55;
        else if (activity.equalsIgnoreCase("Very active")) af = 1.725;
        p.tdee = p.bmr * af;

        // adjust for goal
        p.targetCalories = p.tdee;
        if (user.getGoal().equalsIgnoreCase("Weight Loss")) p.targetCalories = p.tdee - 500;
        else if (user.getGoal().equalsIgnoreCase("Weight Gain")) p.targetCalories = p.tdee + 500;

        if (p.targetCalories < 1200) p.targetCalories = 1200; // safety floor

        // split calories into meals
        double breakfast = p.targetCalories * 0.25;
        double lunch = p.targetCalories * 0.35;
        double dinner = p.targetCalories * 0.30;
        double snacks = p.targetCalories * 0.10;

        List<String[]> options = filterByFoodType(user.getFoodType());
        // Random rand = new Random();

        p.meals.add(new Meal("Breakfast", pickItems(options, breakfast), breakfast));
        p.meals.add(new Meal("Lunch", pickItems(options, lunch), lunch));
        p.meals.add(new Meal("Snacks", pickItems(options, snacks), snacks));
        p.meals.add(new Meal("Dinner", pickItems(options, dinner), dinner));

        return p;
    }

    private String pickItems(List<String[]> options, double targetCal) {
        if (options.isEmpty()) return "No food options available";
        // choose 1-3 items summing approx to targetCal by selecting random items
        StringBuilder sb = new StringBuilder();
        double sum = 0;
        int tries = 0;
        Random r = new Random();
        while (sum < targetCal * 0.85 && tries < 10) {
            String[] pick = options.get(r.nextInt(options.size()));
            double cal = Double.parseDouble(pick[2]);
            if (sum + cal > targetCal * 1.15 && sb.length() > 0) break;
            if (sb.length() > 0) sb.append(" + ");
            sb.append(pick[1]);
            sum += cal;
            tries++;
        }
        return sb.toString();
    }
}
