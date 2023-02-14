import socket
 
# Create a connection to the server application on port 81
tcp_socket = socket.create_connection(('0.0.0.0', 81))
 
try:
    msg = "Hi. I am a TCP client sending data to the server"
    data = str.encode(msg)
    tcp_socket.sendall(data)
 
finally:
    print("Closing socket")
    tcp_socket.close()