package com.cook.recipe.services;

import com.cook.recipe.converters.RecipeCommandToRecipe;
import com.cook.recipe.converters.RecipeToRecipeCommand;
import com.cook.recipe.domain.Recipe;
import com.cook.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    RecipeCommandToRecipe recipeCommandToRecipe;
    RecipeToRecipeCommand recipeToRecipeCommand;


    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipesByTestId() throws Exception{

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Optional<Recipe> recipeOptional = Optional.of(recipe);



        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById(1L);

        assertNotNull("Null recipe returned", returnedRecipe);
        verify(recipeRepository,times(1)).findById(anyLong());
    }
    @Test
    public void getRecipes() throws Exception{

        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);
        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);

        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository,never()).findById(anyLong());
    }

    @Test
    public void testDeleteById() throws Exception{
        Long idToDelete = Long.valueOf(2L);
        recipeService.deleteById(idToDelete);
        verify(recipeRepository,times(1)).deleteById(anyLong());
    }

}