--- King game server ---


Prerequisites:
- Java SE Development Kit 8


Run the application:
java -jar game-server.jar

This jar binds to port 8080.

Example of usage: curl http://localhost:8080/1/login


Possible improvements:
- Regularly remove expired tokens with a scheduler to reduce memory consumption. In this implementation tokens are only deleted when used to post a new score.
- Parametrize configurations (i.e port, session validity time).
