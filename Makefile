main: src/Main.java
	rm -rf ./bin && javac -d ./bin ./src/**/*.java src/*.java && cd ./bin && java Main
