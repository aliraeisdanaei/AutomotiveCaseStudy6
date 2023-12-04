run_carla = /opt/carla_simulator/CarlaUE4.sh 
run_api_file = run.py

python = python3.8

make:
	${run_carla} -quality-level=Low

run:
	${python} ${run_api_file}

drive:
	${python} run_drive.py

# sensor:
# 	set -gx FLASK_APP = run_sensor.py
# 	${python} -m flask run

# collision:
# 	set -gx FLASK_APP = run_collisiondetection.py
# 	${python} -m flask run
