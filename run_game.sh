#!/bin/sh

set -e

javac MyBot.java
./halite --replay-directory replays/ -vvv --width 40 --height 40 "java MyBot" "java -classpath ../v9/ MyBot"
