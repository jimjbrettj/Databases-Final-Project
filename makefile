build:
	mv jdj221/*.class .
	mv jdj221/*.java .
	mv jdj221/Manifest.txt .
	rm jdj221.jar
	javac *.java
	jar cfmv jdj221.jar Manifest.txt *.class 
	mv *.class jdj221
	mv *.java jdj221
	mv Manifest.txt jdj221



	

	

