# Really simple wrapper on Maven's command to build, test, & run farkle
# Maven is a package builder and runner tool for Java
#  https://maven.apache.org/

build:
	@echo "Building Farkle package - results in target/ directory"
	mvn package -DskipTests

test:
	@echo "Running tests -- not including toString tests"
	mvn test -Dgroups=!ToStringTest

vtest:
	@echo "Running  all tests"
	mvn test

mvn-run:
	@echo "Running Farkle main - see pom.xml for arguments passed"
	mvn exec:java

run: build
	@echo "Running Farkle main without maven overhead"
	java -jar target/farkle-*.jar

fast-run:
	java -jar target/farkle-*.jar

spellcheck:
	-codespell src/

setup-dependencies:
	apt update
	apt install -y maven python3-pip checkstyle
	pip3 install codespell

javadoc:
	@echo "Creating javadoc materials"
	@echo "These go into: target/site/apidocs/"
	@echo "Load up index.html to read them"
	-mvn javadoc:javadoc

lint:
	@echo "Running spotless linter to check source files"
	mvn spotless:check

lint-autofix:
	@echo "Autofixing linting errors"
	mvn spotless:apply
