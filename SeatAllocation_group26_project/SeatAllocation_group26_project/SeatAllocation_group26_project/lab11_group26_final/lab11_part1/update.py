import os
os.system("pdftotext programmeCode.pdf code.txt")

class pair:
	name="hu"
		
	def __init__(self,x1,y1):
		self.x = x1
		self.y = y1

      
r1 = open("code.txt","r")
f1 = open("temp", "w")

line_num = 0;
while(line_num != 12) :
	s = r1.readline()
	line_num = line_num + 1

index=[] ####17 colleges
index.append(pair (39,49))
index.append(pair (1,3))	
index.append(pair (4,21))
index.append(pair (50,55))
index.append(pair (56,58))
index.append(pair (59,70))
index.append(pair (22,35))
index.append(pair (71,108))	
index.append(pair (36,38))
index.append(pair (138,140))
index.append(pair (141,143))
index.append(pair (144,147))
index.append(pair (148,170))
index.append(pair (109,137))
index.append(pair (174,192))
index.append(pair (193,216))
index.append(pair (171,173))

def valid(s):
	if(s == "\n" or s[0:len(s)-1]== "Code" or s[0:6]== "Course" or s[0:len(s)-1]== "Course Title" or s[0:len(s)-1]=="35" or s[0:len(s)-1]=="101"):
		return 0
	elif(s[0:len(s)-1]=="96" or s[0:len(s)-1]=="99" or s[0:len(s)-1]=="102" or s[0:len(s)-1]=="103"or s[0:3]== "171"):
		return 0	
	else:
		return 1
		
def change(s):
	if(s=="H4109"):
		return "H4109\n\n14"
	if(s=="H4118"):
		return "H4118\n\n16"
	if(s=="H4125"):
		return "H4125\n\n17"
	if(s=="B5226"):
		return "B5226\n\n53"
	if(s=="G4110"):
		return "G4110\n\n35"
	if(s=="M5249"):
		return "M5249\n\n95"
	if(s=="G5243"):
		return "G5243\n\n99"
	if(s=="R5223"):
		return "R5223\n\n111"
	if(s=="R5223"):
		return "R5223\n\n111"
	if(s=="R5507"):
		return "R5507\n\n171\n\nComputer Science and Engineering"	
	if(s=="14"):
		return "15"
	if(s=="15"):
		return "54"	
	if(s=="95"):
		return "96"	
	if(s[0:3]=="127"):
		return "127\n\nElectrical Engineering with M.Tech in Applied Mechanics with specialization in Biomedical Engineering\n\nM5218"
	if(s[0:3]=="119"):
		return "119\n\nAerospace Engineering with M.Tech. in Applied Mechanics with specialization in Biomedical Engineering\n\nM5202"
	if(s=="111"):
		return "111\n\nCivil Engineering"
	if(len(s) > 6 and s[0:3].isdigit()):
		if(s[0:3]=="215"):
			s = s+"\n\nV5305"
		if(s[0:3]=="216"):
			s = s+"\n\nV5801"
		s1 = s[0:3]
		s2 = s[4:len(s)]
		if(s1=="163"):
			s1 = "M4109\n\n"+s1
		return s1+"\n\n"+s2 				
	else:
		return s
							
college_num = 0
college=[]
while(line_num <= 1141) : #initialising college and initial
	if (s[0:6] == "Indian"):
		s = s[0:len(s)-1]
		college.append(s)
		college_num = college_num + 1
				
	elif (s[0:9] == "Institute"):
		s1 = s[0:len(s)-1]
		s = r1.readline()
		line_num = line_num+1
		s1 = s1+s[0:len(s)-1]
		college.append(s1)
		college_num = college_num + 1
	
	elif (valid(s) == 1):
		s1=s[0:len(s)-1]
		s = r1.readline()
		line_num = line_num+1
		if(len(s) > 1):
			if(s1 != "53" and s1 != "16" and s1 != "V5305"):
				s1 = s1+" "+s[0:len(s)-1]
				s1 = change(s1)
				f1.write(s1)
				f1.write("\n\n")
		else:
			s1 = change(s1)
			f1.write(s1)
			f1.write("\n\n")
	
	s = r1.readline()
	line_num = line_num+1
		
r1.close()
f1.close()	

r2 = open("temp","r")
f2 = open("temp2", "w")

line_num = 1;

while(line_num <= 1300):
	s = r2.readline()
	if(s != "\n"):
		f2.write(s)
	line_num += 1
	
r2.close()	
f2.close()

r2 = open("temp2","r")
f2 = open("data_u-2012.csv", "w")
line_num = 1;
s = r2.readline()	
while(line_num <= 1300) : 
	if len(s)<=6 and len(s)>1:#s is the serial number
		s1 = int(s[0:len(s)]) #sr num...an integer
		s = r2.readline() #program read
		s2 = s[0:len(s)-1] #program
		s = r2.readline() #code read
		s3 = s[0:len(s)-1] #CODE
		line_num += 2
		#print("code",line_num,s3,len(s))
		
		for i in range(0,college_num):
			if s1>=index[i].x and s1<=index[i].y:
				temp = college[i]+","+s2+","+s3+"\n"
				f2.write(temp);
			
	s = r2.readline()
	line_num = line_num+1
import os
os.system("rm code.txt")	
os.system("rm temp")
os.system("rm temp2")		

