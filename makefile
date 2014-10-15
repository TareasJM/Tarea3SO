JCC = javac
JFLAGS = -g

default: Game.class Car.class Sound.class Main.class 

Game.class: Game.java
	$(JCC) $(JFLAGS) Game.java

Car.class: Car.java
	$(JCC) $(JFLAGS) Car.java

Sound.class: Sound.java
	$(JCC) $(JFLAGS) Sound.java

Main.class: Main.java
	$(JCC) $(JFLAGS) Main.java

clean: 
	$(RM) *.class
