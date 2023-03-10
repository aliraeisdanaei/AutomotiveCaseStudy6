import random
import pygame
import time

import carla

client = carla.Client('localhost', 2000)

client.set_timeout(5.0) # seconds
client.load_world('Town02')
world = client.get_world()

blueprint_library = world.get_blueprint_library()

# vehicle_blueprints = blueprint_library.filter('*vehicle*')

ego_veh_bp = random.choice(blueprint_library.filter('vehicle.*.*'))
collision_sensor_bp = blueprint_library.find('sensor.other.collision')
camera_bp = blueprint_library.find('sensor.camera.rgb')

print(ego_veh_bp)

# transform = carla.Transform(carla.Location(x=230, y=195, z=40), carla.Rotation(yaw=180))
spawn_point = random.choice(world.get_map().get_spawn_points())
ego_vehicle = world.spawn_actor(ego_veh_bp, spawn_point)

# Create a transform to place the camera on top of the vehicle
camera_init_trans = carla.Transform(carla.Location(z=1.5))

camera = world.spawn_actor(camera_bp, camera_init_trans, attach_to=ego_vehicle)

# Start camera with PyGame callback
camera.listen(lambda image: image.save_to_disk('out/%06d.png' % image.frame))

spectator = world.get_spectator()
transform = ego_vehicle.get_transform()
spectator.set_transform(carla.Transform(transform.location + carla.Location(z=20), carla.Rotation(pitch=-90)))

         
ego_vehicle.apply_control(carla.VehicleControl(throttle=0.5))
print("Driving")

sec = 20
t_end = time.time() + sec
while time.time() < t_end:
    spectator.set_transform(carla.Transform(ego_vehicle.get_transform().location + carla.Location(z=20), carla.Rotation(pitch=-90)))
    # pass

print("Breaking")
ego_vehicle.apply_control(carla.VehicleControl(brake=0.5))
t_end = time.time() + sec
while time.time() < t_end:
    spectator.set_transform(carla.Transform(ego_vehicle.get_transform().location + carla.Location(z=20), carla.Rotation(pitch=-90)))


camera.destroy()
