from os.path import splitext
from database_io import read_from_csv, read_from_json, write_to_csv, write_to_json


class Database():
    """
    Database class.
    Contains list of recipes.
    """
    def __init__(self, recipes):
        """
        Initalizes database.

        Parameters:
            recipes: list of all recipes.
        """
        if not recipes:
            recipes = []
        self._recipes = recipes

    @property
    def recipes(self):
        """
        Returns:
            list of available recipes.
        """
        return self._recipes

    def load_from_file(self, path):
        """
        Loads recipes from file.
        """
        ext = splitext(path)[:-1]

        with open(path, "r", encoding="utf-8") as file_handle:
            if ext == "json":
                self._recipes = read_from_json(file_handle)
            else:
                self._recipes = read_from_csv(file_handle)

    def save_to_file(self, path):
        """
        Saves database to file.
        """
        ext = splitext(path)[:-1]

        with open(path, "w", encoding="utf-8") as file_handle:
            if ext == "json":
                write_to_json(file_handle, self._recipes)
            else:
                write_to_csv(file_handle, self._recipes)

