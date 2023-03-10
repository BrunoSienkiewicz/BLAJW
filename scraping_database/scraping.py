# https://fajnegotowanie.pl/przepisy/rodzaje-posilkow/
import pandas as pd
from bs4 import BeautifulSoup
import requests
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import time
import json
from io import StringIO


def scrape_page_selenium(url):
    """
    Dynamic web page scraping.

    Returns:
        Soup of given page.
    """
    chrome_options = Options()
    chrome_options.headless = True
    driver = webdriver.Chrome(options=chrome_options)
    driver.get(url)
    time.sleep(1)
    page_content = driver.page_source
    soup = BeautifulSoup(page_content, "html.parser")
    return soup


def scrape_page(url):
    """
    Static webpage scraping.

    Returns:
        Soup of given page.
    """
    page = requests.get(url)
    soup = BeautifulSoup(page.content, "html.parser")
    return soup


def format_time(time):
    time = list(time)
    time_int = 0
    if 'H' in time:
        time_int += int(time[0]) * 60
        time.remove('H')
        time.remove(time[0])
    if 'M' in time:
        time.remove('M')
        time = ''.join(s for s in time)
        time_int += int(time)
    return time_int


def get_recipe_data(url):
    soup = scrape_page_selenium(url)
    scripts = soup.findAll("script", type='application/ld+json')
    recipe_data = scripts[1].contents[0]
    recipe_data = str(recipe_data).replace("\n","").replace("\t","")
    recipe_data = json.loads(recipe_data)
    title = recipe_data['name']
    type = list(recipe_data['recipeCategory'].split(", "))
    time = recipe_data['totalTime']
    time = format_time(time[2:])
    ingredients = recipe_data['recipeIngredient']
    steps = [step['text'] for step in recipe_data['recipeInstructions']]
    return title, type, time, ingredients, steps

get_recipe_data('https://fajnegotowanie.pl/przepisy/szybki-chleb-na-drozdzach/')
