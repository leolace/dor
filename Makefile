main: src/Main.java
	rm -rf ./bin && javac -d ./bin ./src/**/*.java src/*.java && cp -r ./imgs ./bin/ && cd ./bin && java Main
