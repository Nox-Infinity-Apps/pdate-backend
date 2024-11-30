APP_NAME = GCGV
JAR_FILE = build/libs/$(APP_NAME).jar
MAIN_CLASS = com.example.Application

.DEFAULT_GOAL := help

help:
	@echo "Usage: make [target]"
	@echo
	@echo "Targets:"
	@echo "  build         Build the Spring Boot application."
	@echo "  run           Run the Spring Boot application."
	@echo "  clean         Clean the build directory."
	@echo "  test          Run all tests."
	@echo "  bootJar       Package the application as a jar."
	@echo "  docker-build  Build the Docker image."
	@echo "  docker-run    Run the Docker container."
	@echo "  stop          Stop the running Spring Boot application."
	@echo "  help          Display this help message."

build: generateJava cleanBuild bootJar

generateJava:
	@echo "Running generateJava task..."
	@./gradlew generateJava

cleanBuild:
	@echo "Cleaning generated sources..."
	@rm -rf build/generated/sources/dgs-codegen-generated-examples

bootJar:
	@echo "Building the Spring Boot application as a JAR..."
	@./gradlew bootJar

run:
	@./gradlew :modules:app:bootRun

clean:
	@./gradlew clean

test:
	@./gradlew test

docker-build:
	@docker build -t $(APP_NAME):latest .

docker-run:
	@docker run -p 8080:8080 $(APP_NAME):latest

stop:
	@pkill -f $(MAIN_CLASS)
