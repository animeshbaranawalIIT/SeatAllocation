.PHONY: install dist

install: 
	tar -xvf SeatAllocation_group26_project.tar.gz && \
	cd ./SeatAllocation_group26_project/lab10_group26_final/code/ && \
	javadoc -private common galeshapley meritorder Main.java && \
	cd ../.. && \
	pdflatex presentation.tex && \
	rm *.aux *.log *.nav *.out *.snm *.toc && cd .. && \
	rm -r SeatAllocation_group26_project.tar.gz

dist: 
	cd ./SeatAllocation_group26_project/lab10_group26_final/ && \
	rm -f ./code/*.class ./code/*/*.class ./code/*.html ./code/*/*.html && \
	rm -f *~ .classpath .project && \
	rm -f ./code/package-list ./code/stylesheet.css ./code/*~ && \
	rm -rf ./code/resources && \
	cd .. && \
	rm -f *.aux *.log *.nav *.out *.snm *.toc *.pdf && \
	cd .. && \
	tar -cvf SeatAllocation_group26_project.tar.gz SeatAllocation_group26_project && \
	rm -rf SeatAllocation_group26_project
