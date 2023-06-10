# About

Vocal ChatGPT using **Whisper Ai**, **ChatGPT** and **TTS (coqui TTS)**

# Requirements

_Python version_: **Version: 3.9.0**

## Whisper AI

```agsl
choco install ffmpeg
pip3 install python-ffmpeg
pip3 install git+https://github.com/openai/whisper.git
pip3 uninstall torch
pip cache purge
pip3 install torch torchvision torchaudio --extra-index-url https://download.pytorch.org/whl/cu117
```

Also look at https://www.makeuseof.com/dictate-documents-openai-whisper/

## TTS

Using https://pypi.org/project/TTS/

````
git clone https://github.com/coqui-ai/TTS
pip install TTS
pip install -e .[all,dev,notebooks]
````
