import csv
import json


class Recipe():
    """
    Recipe class.
    Contains recipe with format: id, title, type, time, ingredients, steps
    """
    def __init__(self, id, title, type, time, ingredients, steps):
        """
        Initalizes recipe.

        Parameters:
            id: id of the recipe.
            title: name of the recipe.
            time: time to prepare recipe.
            ingredients: list of ingredients requred to prepare the recipe.
            steps: steps to prepare the recipe.
        """
        self._id = id
        self._title = title
        self._type = type
        self._time = time
        self._ingredients = ingredients
        self._steps = steps

    def id(self):
        return self._id

    def title(self):
        return self._title

    def type(self):
        return self._type

    def time(self):
        return self._time

    def ingredients(self):
        return self._ingredients

    def steps(self):
        return self._steps

    def __str__(self) -> str:
        """
        Returns:
            String representation of the recipe.
        """
        return f'{self._id}; {self._title}; {self._type}; {self._time}; {self._ingredients}; {self._steps}'


def read_from_json(file_handle):
    recipes = []
    data = json.load(file_handle)
    for item in data:
        id = item['id']
        title = item['title']
        type = item['type']
        time = item['time']
        ingredients = item['ingredients']
        steps = item['steps']
        recipe = Recipe(id, title, type, time, ingredients, steps)
        recipes.append(recipe)
    return recipes


def write_to_json(file_handle, recipes):
    data = []
    for recipe in recipes:
        id = recipe.id()
        title = recipe.title()
        type = recipe.type()
        time = recipe.time()
        ingredients = recipe.ingredients()
        steps = recipe.steps()
        recipe_data = {
            'id': id,
            'title': title, 
            'type': type, 
            'time': time, 
            'ingredients': ingredients, 
            'steps': steps
        }
        data.append(recipe_data)
    json.dump(data, file_handle)


def read_from_csv(file_handle):
    recipes = []
    reader = csv.DictReader(file_handle)
    for row in reader:
        id = row['id']
        title = row['title']
        type = row['type']
        time = row['time']
        ingredients = row['ingredients']
        steps = row['steps']
        recipe = Recipe(id, title, type, time, ingredients, steps)
        recipes.append(recipe)
    return recipes


def write_to_csv(file_handle, recipes):
    writer = csv.DictWriter(file_handle, ['id', 'title', 'type', 'time', 'ingredients', 'steps'])
    writer.writeheader()
    for recipe in recipes:
        id = recipe.id()
        title = recipe.title()
        type = recipe.type()
        time = recipe.time()
        ingredients = recipe.ingredients()
        steps = recipe.steps()
        writer.writerow({
            'id': id,
            'title': title, 
            'type': type, 
            'time': time, 
            'ingredients': ingredients, 
            'steps': steps
        })
