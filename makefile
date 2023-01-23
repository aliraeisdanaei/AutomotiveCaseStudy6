run_carla = /opt/*arla*/bin/CarlaUE4.sh 
run_api_file = run.py

make:
	${run_carla}
	python ${run_api_file}
