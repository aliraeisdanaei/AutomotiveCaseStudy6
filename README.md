# How to use the API

Makefile is a good reference and should be updated with the new scripts

``make`` starts the Carla instance.
The path may have to be changed depending in the makefile on the insatllation. 

``make drive`` starts the driving instance. 
The controls can be read from the ``run_drive.py`` file. 

But the basic arrow keys should work as usual. 

Return toggles between autopilot. 

Tab switches vehicle. 

## Misc

A case study built using the Carla Automotive Simulator 

https://carla.readthedocs.io/en/latest/tuto_first_steps/#Launching-carla-and-connecting-the-client

The driving control has been built from this template
https://carla.readthedocs.io/en/latest/tuto_G_pygame/#pygame-for-vehicle-control

Adding C++ code to python
https://www.geeksforgeeks.org/how-to-call-c-c-from-python/