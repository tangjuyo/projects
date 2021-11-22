# importing packages
from pytube import YouTube
from selenium import webdriver
from selenium.webdriver.common.by import By
import os
import re

options = webdriver.ChromeOptions()
options.headless = True
driver = webdriver.Chrome(options=options)
regex = re.compile(
    r'^(?:http|ftp)s?://'  # http:// or https://
    r'(?:(?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+(?:[A-Z]{2,6}\.?|[A-Z0-9-]{2,}\.?)|'  # domain...
    r'localhost|'  # localhost...
    r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})'  # ...or ip
    r'(?::\d+)?'  # optional port
    r'(?:/?|[/?]\S+)$', re.IGNORECASE)


def download_mp3_music(url_video, dest):
    yt = YouTube(url_video)
    video = yt.streams.filter(only_audio=True).first()
    aut = yt.author.lower()
    author(aut)
    destination = dest
    if "?" or "<" or ">" or "/" or "\\" or "*" or ":" or "|" or '\"' in yt.title:
        new_title = yt.title.translate({ord(c): None for c in '?<>/\\*:|"'})
        out_file = video.download(filename=new_title,output_path=destination)
    else :
        out_file = video.download(output_path=destination)
    new_file = out_file + '.mp3'
    os.chdir(dest)
    try:
        os.rename(out_file, new_file)
    except:
        os.remove(out_file)
    os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord/')


def find_url(keywords):
    driver.get("https://www.youtube.com/results?search_query=" + keywords)
    driver.find_element(By.XPATH,
                        '/html/body/ytd-app/div/ytd-page-manager/ytd-search/div[1]/ytd-two-column-search-results-renderer/div/ytd-section-list-renderer/div[2]/ytd-item-section-renderer/div[3]/ytd-video-renderer[1]/div[1]/div/div[1]/div/h3/a/yt-formatted-string').click()
    url = driver.current_url

    driver.get("https://www.google.com/")

    return url


def find_name(url_or_keywords):
    if re.match(regex, url_or_keywords):
        yt = YouTube(url_or_keywords)
        if "?" or "<" or ">" or "/" or "\\" or "*" or ":" or "|" or "\"" in yt.title:
            name = yt.title.translate({ord(c): None for c in '?<>/\\*:|"'})
        else:
            name = yt.title
    else:
        driver.get("https://www.youtube.com/results?search_query=" + url_or_keywords)
        name = driver.find_element(By.XPATH,
                                   '/html/body/ytd-app/div/ytd-page-manager/ytd-search/div[1]/ytd-two-column-search-results-renderer/div/ytd-section-list-renderer/div[2]/ytd-item-section-renderer/div[3]/ytd-video-renderer[1]/div[1]/div/div[1]/div/h3/a/yt-formatted-string').text
    return name


def init_browser():
    driver.get("https://www.youtube.com")
    driver.find_element(By.XPATH,
                        '/html/body/ytd-app/ytd-consent-bump-v2-lightbox/tp-yt-paper-dialog/div[2]/div[2]/div[5]/div[2]/ytd-button-renderer[2]/a/tp-yt-paper-button').click()


def find_auth(url):
    yt = YouTube(url)
    return yt.author.lower()


def author(author):
    os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/')
    list = os.listdir('.')
    if author in list:
        return
    else:
        os.makedirs(author)


def dirPl(pl_name):
    os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\playlist/')
    list = os.listdir('.')
    if pl_name in list:
        return
    else:
        os.makedirs(pl_name)
    os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord/')

