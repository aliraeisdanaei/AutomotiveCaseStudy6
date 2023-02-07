run_carla = /opt/CARLA_0.9.13/CarlaUE4.sh 
run_api_file = run.py

python = python2.7

make:
	${run_carla}

run:
	${python2.7} ${run_api_file}

drive:
	${python2.7} run_drive.py