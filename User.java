package model;

public class User {
    private String name;
    private int age;
    private String gender;
    private double heightCm;
    private double weightKg;
    private String activityLevel;
    private String foodType;
    private String goal;
    private Double targetWeight;

    public User(String name, int age, String gender, double heightCm, double weightKg,
                String activityLevel, String foodType, String goal, Double targetWeight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.activityLevel = activityLevel;
        this.foodType = foodType;
        this.goal = goal;
        this.targetWeight = targetWeight;
    }

    // getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public double getHeightCm() { return heightCm; }
    public double getWeightKg() { return weightKg; }
    public String getActivityLevel() { return activityLevel; }
    public String getFoodType() { return foodType; }
    public String getGoal() { return goal; }
    public Double getTargetWeight() { return targetWeight; }
}
