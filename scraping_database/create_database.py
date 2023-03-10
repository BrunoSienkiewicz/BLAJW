from scraping import get_recipe_data
from database import Database
from database_io import Recipe


def create_database(path):
    with open("scraping_database/recipes_urls.txt", "r") as urls:
        id = 1
        recipes = []
        for url in urls:
            title, type, time, ingredients, steps = get_recipe_data(url)
            recipe = Recipe(id, title, type, time, ingredients, steps)
            recipes.append(recipe)
            id += 1
        database = Database(recipes)
        database.save_to_file(path)


def update_database(path):
    database = Database(None)
    database.load_from_file(path)
    with open("scraping_database/recipes_urls.txt", "r") as urls:
        id = len(database.recipes)
        recipes = database.recipes
        for i in range(0, id):
            urls.readline()
        id += 1
        for url in urls:
            title, type, time, ingredients, steps = get_recipe_data(url)
            recipe = Recipe(id, title, type, time, ingredients, steps)
            recipes.append(recipe)
            id += 1
        database = Database(recipes)
        database.save_to_file(path)


create_database("scraping_database/database.csv")
