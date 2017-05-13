__author__ = 'MarioJ'

import sys, time, os.path, os
from tts import gTTS
from sys import platform as _platform


def get_os_dir():
    if _platform == "linux" or _platform == "linux2":
        return os.sep + "opt" + os.sep+ "voices" + os.sep
    elif _platform == "darwin":
        return os.sep + "opt" + os.sep + "voices" + os.sep
    elif _platform == "win32" or _platform == "win64":
        return "C" + os.pathsep + os.sep + " voices" + os.sep


# funcao pra realizar o tts
def makeTTS(text, lang):
    tts = gTTS(text, lang)
    soundPath = dir + str(int(round(time.time() * 1000))) + '.mp3'

    tts.save(soundPath)

    print(soundPath)


def textArgs(args):
    text = ""

    for arg in args[1:-1]:
        text += arg + " ";

    return text[:-1]


# faz log em arquivo
def log(text):
    filepath = dir + "py_tcc2.log"

    logFile = open(filepath, "a")
    logFile.write(text + "\n")
    logFile.close()


# argumentos de incializacao
textToSpeech = textArgs(sys.argv)
lang = sys.argv[-1]

# diretorio para salvar os arquivos de audio
dir = get_os_dir()

# checa se o diretorio existe, se nao crie-o
if not os.path.exists(dir):
    os.makedirs(dir)

log(textToSpeech + " ;lang= " + lang)

# invoca a funcao para converter texto para som
makeTTS(textToSpeech, lang)
