from flask import Flask
import random

app = Flask(__name__)

@app.route("/sensor")
def get_reading():
    speed = random.random() * 100
    return f"{{'speed': '{speed:.2f}'}}"
