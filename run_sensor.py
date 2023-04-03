import carla
from flask import Flask

app = Flask(__name__)

sensor_data = {}
# lambdas cannot contain assignments, so this needs
# to be wrapped in a function call
def save_data(data):
    global sensor_data
    sensor_data = {
        'frame': data.frame,
        'timestamp': data.timestamp,
        'transform': str(data.transform),
        'accelerometer': str(data.accelerometer),
        'gyroscope': str(data.gyroscope),
        'compass': data.compass
    }

@app.route("/sensor")
def return_current_sensor_data():
    return sensor_data

# Connect to CARLA server
client = carla.Client('localhost', 2000)
world = client.get_world()
world.wait_for_tick()
main_camera = world.get_actors().filter('sensor.camera.*')[0]

# Set up the sensor
blueprint = world.get_blueprint_library().find('sensor.other.imu')
transform = carla.Transform(carla.Location(x=0, y=0, z=0))
sensor = world.spawn_actor(blueprint, transform, attach_to=main_camera)
sensor.listen(lambda data: save_data(data))