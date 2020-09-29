all: compile_all_files
	java BookStoreDriver
	
compile_all_files:
	javac Book.java BookCollection.java BookStore.java BookStoreDriver.java HashTableMap.java

junit5.jar:
	wget http://pages.cs.wisc.edu/~cs400/junit5.jar
	
TestSuite.class:  junit5.jar
	javac -cp .:junit5.jar TestSuite.java

test: TestSuite.class compile_all_files
	java -jar junit5.jar --class-path . --scan-classpath --details tree

clean:
	$(RM) junit5.jar
	$(RM) *.class
