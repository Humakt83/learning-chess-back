# learning-chess-back
Backend side of the learning chess application

##Description

This is a backend side of the learning chess application. Communication from the frontend happens through REST.
This is programmed with Java 8 and leans heavily on Spring Boot.

Learning chess utilizes past games when deciding what move to make. In theory, it should be able to defeat or get a tie against any
opponent given enough games and times. To test this, I created a relatively simple 
[sparring AI](https://github.com/Humakt83/sparring-chess) partner for this and made them play games against each other in loop. 
It took approximately 3000 games before learning chess either defeated or got a draw against it.

###Storing completed games to database

Basically, when the game is completed and it was not a draw, every board state of every move will be stored into the database. 
If there is no previous entry for the board state, then a new one is created with score of 1 or -1 depending whether the winner
was white or black player. If board entry exists already, then 1 or -1 is added to the existing score.

###Deciding what move to make

First learning-chess will create moves for the given board state and then fetch scores for those moves from the database defaulting
to 0 score for the move if no entry is stored. Learning chess will then pick a move with highest value for white player or lowest
for black player. If there are multiple moves with the same score a simple evaluation method is applied for board state to pick a 
move from those.
