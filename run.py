import random
import pygame

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
spectator.set_transform(carla.Transform(transform.location + carla.Location(z=50), carla.Rotation(pitch=-90)))

while True:
       
    # creating a loop to check events that
    # are occurring
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            print('Exit simulator ', '*' * 10)
            break
         
        # checking if keydown event happened or not
        if event.type == pygame.KEYDOWN:
           
            # if keydown event happened
            # than printing a string to output
            print("A key has been pressed")


camera.destroy()
