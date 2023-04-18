run_carla = /opt/carla_simulator/CarlaUE4.sh 
run_api_file = run.py

python = python2.7

make:
	${run_carla}

run:
	${python} ${run_api_file}

drive:
	${python} run_drive.py

sensor:
	export FLASK_APP = run_sensor.py
	${python} -m flask run