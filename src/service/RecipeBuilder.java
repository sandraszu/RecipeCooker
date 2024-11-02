package service;

import recipe.*;
import utility.AnsiPalette;
import utility.DisplayFormatter;
import utility.InputScanner;

import java.lang.String;
import java.util.ArrayList;

public class RecipeBuilder {

    public static void createRecipeFromUserInput() {
        String newRecipeName;
        do {
            newRecipeName = InputScanner.getUserInput("The name of this recipe is: ");
            if (RecipeFiler.recipeExists(newRecipeName)) {
                System.out.println(AnsiPalette.RED +
                        "A recipe with this name already exists! Please choose a different name.\n"
                        + AnsiPalette.RESET);
            }
        } while (RecipeFiler.recipeExists(newRecipeName));

        int newRecipeCookingMethod = InputScanner.getValidatedIntegerInput("""
                \n1. Vegetarians or vegans
                2. Gluten Avoidant
                3. Anyone
                This recipe is suitable for (1-3):\s""",1,3);

        Recipe recipe = null;

        switch (newRecipeCookingMethod) {
            case 1 -> {
                boolean isVegetarian = InputScanner.getValidatedBooleanInput(
                        "\nIs this recipe suitable for Vegans as well? Yes/No: ");

                recipe = RecipeFactory.createRecipe(DietaryPreference.VEGETARIAN, newRecipeName, new ArrayList<>(),
                        new ArrayList<>(), isVegetarian, false);
            }
            case 2 -> {
                boolean containsOats = InputScanner.getValidatedBooleanInput(
                        "Does the recipe contain oats? Yes/No: ");
                recipe = RecipeFactory.createRecipe(DietaryPreference.GLUTEN_FREE, newRecipeName, new ArrayList<>(),
                        new ArrayList<>(), false, containsOats);
            }
            case 3 -> recipe = RecipeFactory.createRecipe(DietaryPreference.ANY_DIET, newRecipeName, new ArrayList<>(),
                    new ArrayList<>(),false,false);
        }

        if (recipe != null) {
            RecipeEnricher.ingredients(recipe);
            RecipeEnricher.instructions(recipe);
            System.out.println(DisplayFormatter.getFormattedRecipe(recipe));
            RecipeFiler.saveRecipe(recipe);
        }
    }
}