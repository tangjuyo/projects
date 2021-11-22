import discord, re, os, asyncio, compress_mp4, random, playlist.playlist as pl
from discord.ext import commands, tasks
from dotenv import load_dotenv
from discord.ext.commands import has_permissions, MissingPermissions
from discord import Member
from playlist.playlist import Playlist
import spotify_to_mp3

load_dotenv(dotenv_path="config")
intents = discord.Intents().all()
bot = commands.Bot(command_prefix="&", intents=intents)
listqueue = []
listqueueurl = []
compress_mp4.init_browser()
regex = re.compile(
    r'^(?:http|ftp)s?://'  # http:// or https://
    r'(?:(?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+(?:[A-Z]{2,6}\.?|[A-Z0-9-]{2,}\.?)|'  # domain...
    r'localhost|'  # localhost...
    r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})'  # ...or ip
    r'(?::\d+)?'  # optional port
    r'(?:/?|[/?]\S+)$', re.IGNORECASE)

urlCheck = re.compile(r"^(https://open.spotify.com/playlist/)([a-zA-Z0-9]+)(.*)$")

pl = Playlist()
try:
    pl.readFilePl()
except:
    print("probleme lecture fichier de playlist")


def find(name, path):
    for root, dirs, files in os.walk(path):
        if name in files:
            return True
    return False


@bot.event
async def on_ready():
    await bot.change_presence(activity=discord.Activity(type=discord.ActivityType.listening, name="&h"))
    print("bot prêt")


@bot.command(name='join')
async def join(ctx):
    if not ctx.message.author.voice:
        await ctx.send("{} is not connected to a voice channel.".format(ctx.message.author.name))
        return
    else:
        channel = ctx.message.author.voice.channel
        await channel.connect()


@bot.command(name='leave')
async def leave(ctx):
    voice_client = ctx.message.guild.voice_client
    if voice_client.is_connected():
        await voice_client.disconnect()
    else:
        await ctx.send("The bot is not connected to a voice channel.")


@bot.command(name='ps', help="play spotify song/playlist")
async def ps(ctx, url):
    if re.match(urlCheck, url):
        i = 0
        newlist = spotify_to_mp3.download_from_spotify(url)
        os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord/')
        while i < len(newlist):
            await play(ctx, newlist[i])
            i = i + 1
    else:
        await ctx.send("lien spotify non valide")


@bot.command(name='play', help='To play song')
async def play(ctx, *url):
    chaine_url = " ".join(url)
    if not re.match(regex, chaine_url):
        music = compress_mp4.find_url(chaine_url)
    else:
        music = chaine_url
    server = ctx.message.guild
    if server.voice_client is None:
        await join(ctx)
    voice_channel = server.voice_client
    auth = compress_mp4.find_auth(music)
    # prend bcp de temps du au lancement chrome etc
    try:
        filename = compress_mp4.find_name(music) + ".mp3"
    except:
        filename = '.'
    if "?" or "<" or ">" or "/" or "\\" or "*" or ":" or "|" or "\"" in filename:
        filename = filename.translate({ord(c): None for c in '?<>/\\*"'})
    listqueueurl.append(music)
    listqueue.append(filename)
    if not voice_channel.is_playing():
        async with ctx.typing():
            if filename == '.':
                test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/'
            else:
                test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth.lower() + r'/' + listqueue[0]
                dest = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth.lower()
            try:
                with open(test):
                    voice_channel.play(
                        await discord.FFmpegOpusAudio.from_probe(executable="ffmpeg.exe", source=test),
                        after=lambda e: play_next(ctx))
            except IOError:
                compress_mp4.download_mp3_music(music, dest)
                voice_channel.play(
                    await discord.FFmpegOpusAudio.from_probe(executable="ffmpeg.exe", source=test),
                    after=lambda e: play_next(ctx))
            await ctx.send('**Now playing:** {}'.format(filename))
    else:
        await ctx.send('**Added To Queue:** {}'.format(filename))


def play_next(ctx):
    if len(listqueue) > 1:
        del listqueue[0]
        del listqueueurl[0]
        server = ctx.message.guild
        voice_channel = server.voice_client
        auth = compress_mp4.find_auth(listqueueurl[0])
        test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth.lower() + r'/' + listqueue[0]
        dest = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth.lower()
        try:
            with open(test):
                voice_channel.play(
                    discord.FFmpegOpusAudio(executable="ffmpeg.exe", source=test),
                    after=lambda e: play_next(ctx))
        except:
            compress_mp4.download_mp3_music(listqueueurl[0], dest)
            voice_channel.play(
                discord.FFmpegOpusAudio(executable="ffmpeg.exe", source=test),
                after=lambda e: play_next(ctx))
        asyncio.run_coroutine_threadsafe(ctx.send("**Now Playing : **" + listqueue[0]), bot.loop)
    elif len(listqueue) == 1:
        del listqueue[0]
        del listqueueurl[0]
        asyncio.run_coroutine_threadsafe(ctx.send("**No more songs in queue.**"), bot.loop)
    else:
        asyncio.run_coroutine_threadsafe(ctx.send("**No more songs in queue.**"), bot.loop)


@bot.command(name='skip')
async def skip(ctx):
    voice_client = ctx.message.guild.voice_client
    if voice_client.is_playing():
        if len(listqueue) != 0:
            await ctx.send("**Music Skipped : **" + listqueue[0])
        else:
            await ctx.send("**Music Skipped**")
        voice_client.stop()
    else:
        await ctx.send("The bot is not playing anything at the moment.")


@bot.command(name='queue')
async def queue(ctx):
    list_queue = '**Queue of Musics**' + "\n"
    i = 0
    while len(listqueue) > i:
        list_queue += "" + str(i + 1) + ")     " + listqueue[i] + "\n"
        i = i + 1
    list_queue += ""
    await ctx.send(list_queue)


@bot.command(name='supp', help='This commande remove the music indexed in the queue')
async def supp(ctx, number_remove: int):
    listqueue.remove(listqueue[number_remove - 1])
    listqueueurl.remove(listqueueurl[number_remove - 1])
    await queue(ctx)


@bot.command(name='pause', help='This command pauses the song')
async def pause(ctx):
    voice_client = ctx.message.guild.voice_client
    if voice_client.is_playing():
        await voice_client.pause()
    else:
        await ctx.send("The bot is not playing anything at the moment.")


@bot.command(name='resume', help='Resumes the song')
async def resume(ctx):
    voice_client = ctx.message.guild.voice_client
    if voice_client.is_paused():
        await voice_client.resume()
    else:
        await ctx.send("The bot was not playing anything before this. Use play_song command")


@bot.command(name='stop', help='Stops the song')
async def stop(ctx):
    voice_client = ctx.message.guild.voice_client
    if voice_client.is_playing():
        while len(listqueue) > 0:
            del listqueue[0]
            del listqueueurl[0]
        voice_client.stop()
    else:
        await ctx.send("The bot is not playing anything at the moment.")


@bot.command(name='p', help='play song')
async def p(ctx, *url):
    await play(ctx, *url)


@bot.command(name='h', help='Show all commands')
async def h(ctx):
    embedVar = discord.Embed(title="**Helping menu : **")
    embedVar.add_field(name="Bot Music",
                       value="`&play | (url or keywords) :` play the youtube music from the url/keywords \n`&skip :` skip the current music\n`&pause : ` do a pause in the current music\n`&resume : ` if current music are pause, unpaused it\n`&stop : ` stop the music ( if there are music in queue ) stop will remove them\n`&queue : ` show all music in queue\n`&supp | (index) :` remove music at the index given from the queue\n`&join : ` method to connect the bot on the user channel\n`&leave : ` method to disconnect the bot of the current channel \n `&prand | (number_music) | author(s): ` play randomly X musics in the music database\n `&aut :` show all authors in DataBase")
    embedVar.add_field(name="Moderation Commands",
                       value="`&delete | (number of message) :` delete the number of message in the channel\n`&kick | Member :` kick the member of the server")
    await ctx.send(embed=embedVar)


@bot.command(name='delete', help='delete N message', pass_context=True)
@has_permissions(manage_messages=True)
async def delete(ctx, number_message: int):
    messages = await ctx.channel.history(limit=number_message + 1).flatten()
    for each_message in messages:
        await each_message.delete()


@delete.error
async def delete_error(error, ctx):
    if isinstance(error, MissingPermissions):
        text = "Sorry {}, you do not have permissions to do that!".format(ctx.message.author)
        await ctx.send(text)


@bot.command(name="kick", pass_context=True)
@has_permissions(manage_roles=True, ban_members=True)
async def kick(ctx, member: Member):
    await bot.kick(member)


@kick.error
async def kick_error(error, ctx):
    if isinstance(error, MissingPermissions):
        text = "Sorry {}, you do not have permissions to do that!".format(ctx.message.author)
        await ctx.send(text)


@bot.command(name="prand", help="play random music in database")
async def prand(ctx, number_of_music: int, *autheur):
    i = 0
    j = 0
    counter = 0
    auteurs = list(autheur)
    if len(auteurs) == 0:
        while j < number_of_music:
            auth = random.choice(os.listdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/'))
            test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth + r'/'
            try:
                music = random.choice(os.listdir(test))
            except:
                print("cette auteur n'a pas de musique : " + auth)

            while music in listqueue:
                auth = random.choice(os.listdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/'))
                test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth + r'/'
                music = random.choice(os.listdir(test))
            await play(ctx, music)
            j = j + 1
        return
    else:
        auth = random.choice(auteurs)
    while i < number_of_music:
        test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth + r'/'
        music = random.choice(os.listdir(test))
        while music in listqueue:
            auth = random.choice(auteurs)
            test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth + r'/'
            music = random.choice(os.listdir(test))
            counter = counter + 1
            if counter == 100:
                auteurs.remove(auth)
                if len(auteurs) == 0:
                    return
                else:
                    auth = random.choice(auteurs).lower()
                    test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/' + auth + r'/'
                    music = random.choice(os.listdir(test))
                counter = 0
        if not music == ".":
            await play(ctx, music)
        i = i + 1


@bot.command(name="np", help="Current music")
async def np(ctx):
    embedvar = discord.Embed()
    embedvar.add_field(name="**Current Music is : **", value=listqueue[0])
    await ctx.send(embed=embedvar)


@bot.command(name="aut", help="Show all auteurs in Database")
async def aut(ctx):
    os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\music/')
    embedvar = discord.Embed(title="All Authors in DataBase")
    list = os.listdir('.')
    i = 0
    premier_character = list[i][0]
    while i < len(list):
        test = "`" + list[i] + "` "
        if premier_character == list[i + 1][0] and i + 1 < len(list):
            test += ", `" + list[i + 1] + "` "
        i = i + 1
        embedvar.add_field(name="**" + premier_character + ":**", value=test, inline=False)
        premier_character = list[i][0]

    os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord/')
    await ctx.send(embed=embedvar)


@bot.command(name="stat", help="show statistiques")
async def stat(ctx):
    artistes = 0
    nombre_song = 0
    j = 0
    list = os.listdir('music/')
    while artistes < len(list):
        list2 = os.listdir('music/' + list[artistes])
        while j < len(list2):
            nombre_song = nombre_song + 1
            j = j + 1
        j = 0
        artistes = artistes + 1

    embedvar = discord.Embed(title="Somes Statistiques")
    embedvar.add_field(name="**Numbers of artistes :**", value="*" + str(artistes) + "*" + "   in database")
    embedvar.add_field(name="**Numbers of songs :**", value="*" + str(nombre_song) + "*" + "    in database")
    await ctx.send(embed=embedvar)


@bot.command(name="createPl", help="Create playlist")
async def createPL(ctx, nom_pl):
    try:
        pl.createPl(nom_pl)
        savePl()
        await ctx.send("`Playlist :` " + nom_pl + "has been created")
    except:
        await ctx.send("Problems until the creation of the playlist occured")


@bot.command(name="addMusic", help="add music to playlist")
async def addMusic(ctx, name_pl, *music_name):
    chaine_url = " ".join(music_name)
    music = compress_mp4.find_name(chaine_url)
    pl.ajoutMusic(name_pl, music)
    savePl()
    await ctx.send("`Music :`" + music + "added")


def addMusicall(name_pl, *music_name):
    chaine_url = " ".join(music_name)
    music = compress_mp4.find_name(chaine_url)
    pl.ajoutMusic(name_pl, music)
    print("music ajouté :" + music)


@bot.command(name="showPl")
async def showPl(ctx):
    await ctx.send(pl.printPl())


def savePl():
    pl.insertFile()


@bot.command(name="playlist", help="play local playlist")
async def playlist(ctx, name_playlist):
    server = ctx.message.guild
    if server.voice_client is None:
        await join(ctx)
    tab_pl = pl.readmusic(name_playlist)
    voice_channel = server.voice_client
    compress_mp4.dirPl(name_playlist)
    randomintmusic = random.choice(tab_pl)
    test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\playlist/' + name_playlist + r'/' + randomintmusic + ".mp3"
    print(test)
    dest = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord\playlist/' + name_playlist
    try:
        with open(test):
            voice_channel.play(
                discord.FFmpegOpusAudio(executable="ffmpeg.exe", source=test),
                after=lambda e: playlistnext(ctx, name_playlist, tab_pl))
    except:
        compress_mp4.download_mp3_music(compress_mp4.find_url(randomintmusic), dest)
        voice_channel.play(
            discord.FFmpegOpusAudio(executable="ffmpeg.exe", source=test),
            after=lambda e: playlistnext(ctx, name_playlist, tab_pl))
    asyncio.run_coroutine_threadsafe(ctx.send("**Now Playing : **" + randomintmusic), bot.loop)


def playlistnext(ctx, name_playlist, tab: []):
    server = ctx.message.guild
    voice_channel = server.voice_client
    randommusicint = random.choice(tab)
    test = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord/playlist/' + name_playlist + r'/' + randommusicint + '.mp3'
    dest = r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord/playlist/' + name_playlist
    try:
        with open(test):
            voice_channel.play(
                discord.FFmpegOpusAudio(executable="ffmpeg.exe", source=test),
                after=lambda e: playlistnext(ctx, name_playlist, tab))
    except:
        compress_mp4.download_mp3_music(compress_mp4.find_url(randommusicint), dest)
        voice_channel.play(
            discord.FFmpegOpusAudio(executable="ffmpeg.exe", source=test),
            after=lambda e: playlistnext(ctx, name_playlist, tab))
    asyncio.run_coroutine_threadsafe(ctx.send("**Now Playing : **" + randommusicint), bot.loop)


@bot.command(name="addplaylist")
async def addplaylist(ctx, name_playlist, urlplspotify):
    if re.match(urlCheck, urlplspotify):
        i = 0
        newlist = spotify_to_mp3.download_from_spotify(urlplspotify)
        os.chdir(r'C:\Users\julie\Documents\YT_Extractor\Bot_Dicord/')
        while i < len(newlist):
            addMusicall(name_playlist, newlist[i])
            i = i + 1
        savePl()
        await ctx.send("`Number of Musics added :` " + "**" + str(i) + "**")

    else:
        await ctx.send("lien spotify non valide")


bot.run(os.getenv("TOKEN"))
