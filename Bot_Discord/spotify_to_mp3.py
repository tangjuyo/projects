# Downloads a Spotify playlist into a folder of MP3 tracks
# Jason Chen, 21 June 2020

import os
import spotipy
import spotipy.oauth2 as oauth2
import youtube_dl
from youtube_search import YoutubeSearch
import compress_mp4


# **************PLEASE READ THE README.md FOR USE INSTRUCTIONS**************


def write_playlist(username: str, playlist_id: str):
    spotify = connexion()
    tab_auteur = []
    tab_song = []
    results = spotify.user_playlist(username, playlist_id, fields='tracks,next,name')
    tracks = results['tracks']
    for item in tracks['items']:
        if 'track' in item:
            track = item['track']
        else:
            track = item
        try:
            tab_song.append(track['name'])
            tab_auteur.append(track['artists'][0]['name'])
        except:
            pass
    return tab_song, tab_auteur


def find_and_download_songs(tab: [], tab2: []):
    list_url_ytb = []
    i = 0
    TOTAL_ATTEMPTS = 10
    while i < len(tab):
        name, artist = tab[i], tab2[i]
        text_to_search = artist + " - " + name
        list_url_ytb.append(text_to_search)
        i = i + 1
    return list_url_ytb


# if __name__ == "__main__":
# Parameters
#   print("Please read README.md for use instructions.")
#  client_id = "8782dcfaf2dc45c29852508f67917469"
# client_secret = "e4da9b2584af494cae6424f1625a2dd3"
# username = "Julien"
# playlist_uri = input("Playlist URI (excluding \"spotify:playlist:\"): ")
# auth_manager = oauth2.SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
# spotify = spotipy.Spotify(auth_manager=auth_manager)
# playlist_name = write_playlist(username, playlist_uri)
# reference_file = "{}.txt".format(playlist_name)
# # Create the playlist folder
# if not os.path.exists(playlist_name):
#     os.makedirs(playlist_name)
# os.rename(reference_file, playlist_name + "/" + reference_file)
# os.chdir(playlist_name)
# find_and_download_songs(reference_file)
# print("Operation complete.")


def connexion():
    client_id = "8782dcfaf2dc45c29852508f67917469"
    client_secret = "e4da9b2584af494cae6424f1625a2dd3"
    auth_manager = oauth2.SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
    spotify = spotipy.Spotify(auth_manager=auth_manager)
    return spotify


def download_from_spotify(url_playlist):
    username = "Julien"
    playlist_uri = url_playlist
    tableau_artiste, tableau_song = write_playlist(username, playlist_uri)
    return find_and_download_songs(tableau_artiste, tableau_song)
