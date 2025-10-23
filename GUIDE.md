# Project Guide & Feature List

## Goal
Build a Smart Diet Planner desktop app in Java that recommends personalized diets for vegetarian and non-vegetarian users.

## Features in this package
1. **User Profile Input** – name, age, gender, height, weight, activity level, food type (Vegetarian/Non-Vegetarian), goal.
2. **Calculations** – BMI, BMR (Mifflin-St Jeor), TDEE, adjusted calories for goals.
3. **Meal Plan Generator** – splits calories into Breakfast/Lunch/Snacks/Dinner and picks sample foods from `data/foods.csv`.
4. **GUI** – Swing-based screens: Welcome, Input form, Result table.
5. **Save Plan** – Save generated plan as a text file.

## How diet is recommended
- Use the user's BMR and activity level to compute TDEE (approx daily maintenance calories).
- Adjust TDEE by ±500 kcal for weight loss/gain.
- Distribute target calories across meals: Breakfast 25%, Lunch 35%, Dinner 30%, Snacks 10%.
- Choose sample dishes from the food database matching the user's food type.

## Extend this project (suggestions & libraries)
- **Food & Nutrition API**: Integrate Edamam or Spoonacular to fetch exact macros and portion sizes.
- **Database**: Use SQLite (sqlite-jdbc) to persist user profiles and history.
  - Add dependency: `sqlite-jdbc-<version>.jar`
  - Example code: use `java.sql.Connection` and `DriverManager.getConnection("jdbc:sqlite:yourdb.db")`
- **Charts**: Use JFreeChart to plot BMI or calorie trends.
- **Export to PDF**: Use iText or Apache PDFBox to produce printable diet reports.
- **Improve GUI**: Use custom fonts, icons, and better layouts. Consider JavaFX for richer UI if desired.
- **Machine Learning**: Collect user feedback and use simple regression/classification (Weka/TensorFlow) to improve recommendations.

## Files to update for customization
- `data/foods.csv` – edit or expand with more items (format: Type,Food,Calories)
- `service/DietCalculator.java` – tweak macro splits, meal percentages, or selection algorithm.

## Troubleshooting
- If `foods.csv` is not found, the app uses a tiny fallback food list embedded in `DietCalculator`.
- For Windows, ensure correct classpath and paths (use double backslashes or quotes).

## License & Attribution
This project template is provided as-is for educational purposes. Feel free to modify and extend.

----
If you'd like, I can:
- Add SQLite persistence and include `sqlite-jdbc` in the zip.
- Create a Maven project structure with `pom.xml`.
- Add PDF export using iText and include sample JARs.
Tell me which additions you want and I'll generate an updated zip.
