
class pair:
		
	def __init__(self,x1,y1):
		self.x = x1
		self.y = y1


r1 = open("datafiles/ranklist.csv","r")

r1 = open("datafiles/chutiyap/csv_files/codes.csv", "r")
r2 = open("datafiles/chutiyap/csv_files/programs.csv", "r")
r3 = open("datafiles/chutiyap/csv_files/institute.csv", "r")

i1 = open("datafiles/chutiyap/csv_files/ge.csv", "r")
i2 = open("datafiles/chutiyap/csv_files/ge_pd.csv", "r")

i3 = open("datafiles/chutiyap/csv_files/obc.csv", "r")
i4 = open("datafiles/chutiyap/csv_files/obc_pd.csv", "r")

i5 = open("datafiles/chutiyap/csv_files/sc.csv", "r")
i6 = open("datafiles/chutiyap/csv_files/sc_pd.csv", "r")

i7 = open("datafiles/chutiyap/csv_files/st.csv", "r")
i8 = open("datafiles/chutiyap/csv_files/st_pd.csv", "r")

i9 = open("datafiles/chutiyap/csv_files/data_u-2012.csv", "r")

line_num = 0;
code=[]
institute=[]
gen = []
obc=[]
sc=[]
st=[]
genpd=[]
obcpd=[]
scpd=[]
stpd=[]
program=[]

while(line_num != 216) :
	s1 = r1.readline()
	s2 = r2.readline()
	s3 = r3.readline()
	s1 = s1[0:len(s1)-1]
	s2 = s2[0:len(s2)-1]
	s3 = s3[0:len(s3)-1]
	if(s1!=""and s2!="" and s3!="" ):
		code.append(s1)
		program.append(s2)
		institute.append(s3)
	
	s1=i1.readline()
	s2 = i2.readline()
	if(s1!="" and s2!=""):
		i=s1.find(",")
		s11=s1[0:i]
		s12=s1[i+1:len(s1)-1]
		#print(s11,s12)
		gen.append(pair(int(s11),int(s12)))

		i=s2.find(",")
		s21=s2[0:i]
		s22=s2[i+1:len(s2)-1]
		#print(s21,s22)
		genpd.append(pair(int(s21),int(s22)))
	
	s1=i3.readline()
	s2 = i4.readline()
	if(s1!="" and s2!=""):
		i=s1.find(",")
		s11=s1[0:i]
		s12=s1[i+1:len(s1)-1]
		#print(s11,s12)
		obc.append(pair(int(s11),int(s12)))

		i=s2.find(",")
		s21=s2[0:i]
		s22=s2[i+1:len(s2)-1]
		#print(s21,s22)
		obcpd.append(pair(int(s21),int(s22)))

	s1=i5.readline()
	s2 = i6.readline()
	if(s1!="" and s2!=""):
		i=s1.find(",")
		s11=s1[0:i]
		s12=s1[i+1:len(s1)-1]
		#print(s11,s12)
		sc.append(pair(int(s11),int(s12)))

		i=s2.find(",")
		s21=s2[0:i]
		s22=s2[i+1:len(s2)-1]
		#print(s21,s22)
		scpd.append(pair(int(s21),int(s22)))

	s1=i7.readline()
	s2 = i8.readline()
	if(s1!="" and s2!=""):
		i=s1.find(",")
		s11=s1[0:i]
		s12=s1[i+1:len(s1)-1]
		#print(s11,s12)
		st.append(pair(int(s11),int(s12)))

		i=s2.find(",")
		s21=s2[0:i]
		s22=s2[i+1:len(s2)-1]
		#print(s21,s22)
		stpd.append(pair(int(s21),int(s22)))					
	line_num+=1

#for i in range(0,215):
#	print(code[i],gen[i].x,gen[i].y,obc[i].x,obc[i].y,sc[i].x,sc[i].y,st[i].x,st[i].y,genpd[i].x,genpd[i].y,obcpd[i].x,obcpd[i].y,scpd[i].x,scpd[i].y,stpd[i].x,stpd[i].y)

#for i in range(0,215):

#		print(code[i],program[i],institute[i])
write = open("temp","w")
write.close()
read = open("temp","r")
def all2(r,cat,s):
	pref = []
	final = []
	index = []
	rank = int(r)
	#write(s)
	# = read.readline()
	print(len(s))
	for a in range(0,215):
		
		k = institute[a]
		k = k[0:len(k)]
		print(k)
		#print("gfu",s,k)
		if (s==k):
			#print(s,k)
			index.append(a)
	print(index)		
	if(cat=="gen"):

		for i in range(0,len(index)):
			k = index[i]
			if(rank>=gen[k].x and rank<=gen[k].y):
				print(gen[k].x,gen[k].y)
				pref.append(institute[k])
				pref.append(code[k])
				pref.append(program[k])
				pref.append(gen[k].x)
				pref.append(gen[k].y)
				final.append(pref)
				print (pref)
				pref = []	
	return final

l = all2("245","gen","Indian Institute of Technology Hyderabad")

for i in range(0,len(l)):
	for j in l[i]:
		print(j)

