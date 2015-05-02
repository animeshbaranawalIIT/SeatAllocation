from django.http import HttpResponse
from django.http import HttpResponseRedirect
from student.models import student
from student.models import course
import csv
from django.shortcuts import render
from django.contrib.sessions.models import Session
from datetime import datetime

class pair:
		
	def __init__(self,x1,y1):
		self.x = x1
		self.y = y1

def update(request):
	r1 = open("lab/datafiles/ranklist.csv","r")
	r2 = open("lab/datafiles/choices.csv","r")
	i9 = open("lab/datafiles/sorted_data_u-2012.csv", "r")
	read = csv.reader(r1)
	read2 = csv.reader(r2)
	reader=csv.reader(i9)
	course.objects.all().delete()
	student.objects.all().delete()
	for row in reader:
		course.objects.create(institute=row[0],program=row[1],code=row[2],ge_open=row[3],ge_close=row[4],obc_open=row[5],obc_close=row[6],sc_open=row[7],sc_close=row[8],st_open=row[9],st_close=row[10],gepd_open=row[11],gepd_close=row[12],obcpd_open=row[13],obcpd_close=row[14],scpd_open=row[15],scpd_close=row[16],stpd_open=row[17],stpd_close=row[18]) 
	i9.close()
	count = 0
	for row in read:
		if(count != 0):
			student.objects.create(roll_no=row[0],gender=row[1],gerank=row[3],obcrank=row[4],scrank=row[5],strank=row[6],gepdrank=row[8],obcpdrank=row[9],scpdrank=row[10],stpdrank=row[11]) 
		count += 1
	r1.close()
	temp_l = []
	
	p=student.objects.all()	
	return HttpResponse(student)


def display(request):
	p=student.objects.all()
	display=""
	for std in p:
		display+=std.roll_no + std.password + "\n" 
	return HttpResponse(display)

def formfill(request):
	s = request.POST['user']
	if (s!= ""):
		message="User name filled"
		try:
			loggedin = student.objects.get(roll_no = s)
			p=student.objects.all()	 	
			for a in get_all_logged_in_users():
				if(loggedin == a):
					return render(request,'signup.html',{'mess':"Sorry,You are already registered"})
		except:
			p=student.objects.all()	 	
			return render(request,'signup.html',{'mess':"Sorry, you are not eligible to signup"})
	else:
		message = "You submitted no user"
		return render(request,'signup.html',{'mess':message})

	if (request.POST['pass'] != ""):
		message="password filled"
	else:
		message = "You submitted no password"
		return render(request,'signup.html',{'mess':message})

	if (request.POST['dob'] != ""):	
		d = request.POST['dob']
		date = d[0:2]
		month = d[3:5]
		year = d[6:10]
		if(date.isdigit() and month.isdigit() and year.isdigit()):
			d1 = int(date)
			m1 = int(month)
			y1 = int(year)
			if(d1<=31 and d1>0 and m1 <=12 and m1>0 and y1<=2014 and y1>1900):
				loggedin = student.objects.get(roll_no = s)
				loggedin.dob = d
				loggedin.password = request.POST['pass']
				loggedin.save()
				#student.objects.(roll_no=request.POST['user'],password=request.POST['pass'],dob = request.POST['dob'])
				message = "Welcome,Successful signup"
				return HttpResponseRedirect('/opener/')
			else:
				message = "You submitted wrong Date of birth"
				
		else:
			message = "You submitted wrong Date of birth"
	else:
		message="You entered no Date of Birth"

	return render(request,'signup.html',{'mess':message})

def signup(request):
	return render(request,'signup.html',{'mess':""})
		
	
def change_pwd(request):
	return render(request,'change_pwd.html')

def opener(request):
	return render(request,'signin.html')

def baser(request):
	return render(request,'index.html')

def signin(request):
	w1 = open("loggedin","w")
	p =student.objects.all()
	k = request.POST['user']
	k2 = request.POST['pwd']
	for s in p:
		if k=="" or k2=="":
			return render(request,'signin.html',{'mess':"Invalid Username or password"})

		if k== s.roll_no and k2==s.password :
			request.session['user']=k
			return render(request,'home.html',{'list':[]})

	return render(request,'signin.html',{'mess':"Invalid Username or password"})



def get_all_logged_in_users():
    # Query all non-expired sessions
    sessions = Session.objects.filter(expire_date__gte=datetime.now())
    uid_list = []

    for session in sessions:
        data = session.get_decoded()
        uid_list.append(data.get('user', None))

    # Query all logged in users based on id list
    return student.objects.filter(roll_no__in=uid_list)

def real_change_pwd(request):
	try:
		p =student.objects.all()
		k = request.POST['user']
		k2 = request.POST['dob']
		if(k=="" or k2==""):
			return render(request,'change_pwd.html',{'mess':"Field(s) are empty"}) 
		for s in p:
			if k== s.roll_no and k2==s.dob :
				s.password= request.POST['pas']
				s.save()
				return render(request,'signin.html',{'mess': "password successfully changed"})
		message = "Sorry, your entries are wrong"
		return render(request,'change_pwd.html',{'mess':message})
	except:
		return HttpResponseRedirect('/base/')	

def logout(request):
	del request.session['user']
	return render(request,'signin.html', {'mess':"You have succesfully logged out"})

def homeop(request):
	return render(request,'home.html')

def addpref(request):
	return render(request,"pref.html")

def submit_pref(request):
	inst = request.GET	["insti"]
	p = course.objects.all()
	list1 = []
	for a in p:
		if(a.institute == inst):
			list1.append(a)

	return render(request,"pref.html",{"list":list1})

def final_pref(request):
	codelist = request.POST.getlist("prog")
	loggedin = request.session.get("user")
	k = student.objects.get(roll_no = loggedin)
	count = 0
	list1 = []
	#return HttpResponse(codelist)
	#return HttpResponse(k.pref)
	for code in codelist:
		s = k.pref
		if(s.find(code) != -1):
			count = count
			if(count == 1):
				return HttpResponse(code)
		else:
			count += 1
			k.pref = k.pref+"_"+code
			list1.append(k.pref)
			k.save()
	#return HttpResponse(k.pref)
			
	return render(request,'pref.html',{'mess': "successfully added " + str(count) + " programmes"})					
def rmv_pref(request):
	codelist = request.POST.getlist("prog")
	#return HttpResponse(codelist)
	loggedin = request.session.get("user")
	#return HttpResponse(loggedin)
	k = student.objects.get(roll_no = loggedin)
	count = 0
	#return HttpResponse(k)
	for code in codelist:
		s = k.pref
		
		if(s.find(code) != -1):
			i = s.find(code)
			s1 = k.pref[0:i]
			s2 = k.pref[i+5:len(k.pref)]
			k.pref = s1+s2
			k.save()
			count += 1
		else:
			count = count
	"""pref = k.pref
	list1 = []
	if(pref == ""):
		return render(request,'editpref.html',{'mess': "No preferences to show"}) 
	while(len(pref) >=1):
		code1 = pref[1:6]
		pref = pref[6:len(pref)]
		list1.append(pref)
			#choice = course.objects.get(code = code1)
			#return HttpResponse(choice)
		#except Exception as e:
	return HttpResponse(list1)

		#list1.append(choice)"""
			
	return render(request,'.html',{'mess': "successfully removed" + str(count) + " programmes"})

def my_pref(request):
	loggedin = request.session.get("user")
	k = student.objects.get(roll_no = loggedin)
	pref = k.pref
	list1 = []
	count = 0
	if(pref == ""):
		return render(request,'editpref.html',{'mess': "No preferences to show"}) 
	while(pref != ""):
		code1 = pref[1:6]
		pref = pref[6:len(pref)]
		#if(count == 7):
			#return HttpResponse(pref)
		count +=1	
		#list1.append(code1)
		choice = course.objects.get(code = code1)
		list1.append(choice)
	#return HttpResponse(list1)		
	return render(request,'editpref.html',{'list':list1})


def showprog(request):
	listofuser = get_all_logged_in_users()
	
	k = request.session['user']
	college=request.GET['insti']
	p1 = request.GET['prog']
	loggedin = student.objects.get(roll_no = k)

	if(loggedin.gerank != 0):
		s = "ge"
		r = loggedin.gerank
	if(loggedin.obcrank != 0):
		s = "obc"
		r = loggedin.obcrank	
	if(loggedin.scrank != 0):
		s = "sc"
		r = loggedin.scrank
	if(loggedin.strank != 0):
		s = "st"
		r = loggedin.strank
	if(loggedin.gepdrank != 0):
		s = "ge_pd"
		r = loggedin.gepdrank
	if(loggedin.obcpdrank != 0):
		s = "obc_pd"
		r = loggedin.obcpdrank
	if(loggedin.scpdrank != 0):
		s = "sc_pd"
		r = loggedin.scpdrank
	if(loggedin.stpdrank != 0):
		s = "st_pd"
		r = loggedin.stpdrank
	
	if(r==""):
		return render(request,'home.html',{'mess':"No rank alloted rank"})
	if(college!="none"):
		list1=[]
		if college=="i1":
			clist = course.objects.filter(code__icontains='A')
		elif college=="i2":
			clist = course.objects.filter(code__icontains='B')
		elif college=="i3":
			clist = course.objects.filter(code__icontains='C')
		elif college=="i4":
			clist = course.objects.filter(code__icontains='D')
		elif college=="i5":
			clist = course.objects.filter(code__icontains='E')
		elif college=="i6":
			clist = course.objects.filter(code__icontains='G')
		elif college=="i7":
			clist = course.objects.filter(code__icontains='H')
		elif college=="i8":
			clist = course.objects.filter(code__icontains='J')
		elif college=="i9":
			clist = course.objects.filter(code__icontains='K')
		elif college=="i10":
			clist = course.objects.filter(code__icontains='M')
		elif college=="i11":
			clist = course.objects.filter(code__icontains='N')
		elif college=="i12":
			clist = course.objects.filter(code__icontains='P')
		elif college=="i13":
			clist = course.objects.filter(code__icontains='R')
		elif college=="i14":
			clist = course.objects.filter(code__icontains='S')
		elif college=="i15":
			clist = course.objects.filter(code__icontains='U')
		elif college=="i16":
			clist = course.objects.filter(code__icontains='V')
		elif college=="i17":
			clist = course.objects.filter(code__icontains='W')
				
		for cor in clist:
			cand=int(r)
			m=""
			if s=="ge":
				colo=int(cor.ge_open)
				colc=int(cor.ge_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="obc":
				colo=int(cor.obc_open)
				colc=int(cor.obc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc":
				colo=int(cor.sc_open)
				colc=int(cor.sc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
			
			if s=="st":
				colo=int(cor.st_open)
				colc=int(cor.st_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="ge_pd":
				colo=int(cor.gepd_open)
				colc=int(cor.gepd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="obc_pd":
				colo=int(cor.obcpd_open)
				colc=int(cor.obcpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc_pd":
				colo=int(cor.scpd_open)
				colc=int(cor.scpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="st_pd":
				colo=int(cor.stpd_open)
				colc=int(cor.stpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

	else:
		list1 = []
		if p1=="p3":
			clist = course.objects.filter(program='Aerospace Engineering')
		elif p1=="p4":
			clist = course.objects.filter(program='Aerospace Engineering with M.Tech. in Applied Mechanics with specialization in Biomedical Engineering')
		elif p1=="p5":
			clist = course.objects.filter(program='Agricultural and Food Engineering')
		elif p1=="p6":
			clist = course.objects.filter(program='Agricultural and Food Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p7":
			clist = course.objects.filter(program='Applied Geology')
		elif p1=="p8":
			clist = course.objects.filter(program='Applied Geophysics')
		elif p1=="p9":
			clist = course.objects.filter(program='Applied Mathematics')
		elif p1=="p10":
			clist = course.objects.filter(program='Architecture')
		elif p1=="p11":
			clist = course.objects.filter(program='Biochemical Engineering')
		elif p1=="p12":
			clist = course.objects.filter(program='Biochemical Engineering and Biotechnology')
		elif p1=="p13":
			clist = course.objects.filter(program='Bioengineering with M.Tech in Biomedical Technology')
		elif p1=="p14":
			clist = course.objects.filter(program='Biological Engineering')
		elif p1=="p15":
			clist = course.objects.filter(program='Biological Sciences')
		elif p1=="p16":
			clist = course.objects.filter(program='Biological Sciences and Bioengineering')
		elif p1=="p17":
			clist = course.objects.filter(program='Biotechnology')
		elif p1=="p18":
			clist = course.objects.filter(program='Biotechnology and Biochemical Engineering')
		elif p1=="p19":
			clist = course.objects.filter(program='Ceramic Engineering')
		elif p1=="p20":
			clist = course.objects.filter(program='Chemical Engineering')
		elif p1=="p21":
			clist = course.objects.filter(program='Chemical Engineering with M.Tech. in Hydrocarbon Engineering')
		elif p1=="p22":
			clist = course.objects.filter(program='Chemical Science and Technology')
		elif p1=="p23":
			clist = course.objects.filter(program='Chemistry')
		elif p1=="p24":
			clist = course.objects.filter(program='Civil Engineering')
		elif p1=="p25":
			clist = course.objects.filter(program='Civil Engineering (Infrastructural Civil Engineering)')
		elif p1=="p26":
			clist = course.objects.filter(program='Civil Engineering with any of the listed specialization')
		elif p1=="p27":
			clist = course.objects.filter(program='Civil Engineering with M.Tech. in Applied Mechanics in any of the listed specializations')
		elif p1=="p28":
			clist = course.objects.filter(program='Civil Engineering with M.Tech. in Structural Engineering')
		elif p1=="p29":
			clist = course.objects.filter(program='Computer Science and Engineering')
		elif p1=="p30":
			clist = course.objects.filter(program='Design')
		elif p1=="p31":
			clist = course.objects.filter(program='Economics')
		elif p1=="p32":
			clist = course.objects.filter(program='Electrical Engineering')
		elif p1=="p33":
			clist = course.objects.filter(program='Electrical Engineering (Power)')
		elif p1=="p34":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech in Applied Mechanics with specialization in Biomedical Engineering')
		elif p1=="p35":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p36":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Communications and Signal Processing')
		elif p1=="p37":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Information and Communication Technology')
		elif p1=="p38":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Microelectronics')
		elif p1=="p39":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Power Electronics')
		elif p1=="p40":
			clist = course.objects.filter(program='Electronics and Communication Engineering')
		elif p1=="p41":
			clist = course.objects.filter(program='Electronics and Communication Engineering with M.Tech. in Wireless Communication')
		elif p1=="p42":
			clist = course.objects.filter(program='Electronics and Electrical Communication Engineering')
		elif p1=="p43":
			clist = course.objects.filter(program='Electronics and Electrical Communication Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p44":
			clist = course.objects.filter(program='Electronics and Electrical Engineering')
		elif p1=="p45":
			clist = course.objects.filter(program='Electronics Engineering')
		elif p1=="p46":
			clist = course.objects.filter(program='Energy Engineering with M.Tech. in Energy Systems Engineering')
		elif p1=="p47":
			clist = course.objects.filter(program='Engineering Design (Automotive Engineering)')
		elif p1=="p48":
			clist = course.objects.filter(program='Engineering Design (Biomedical Design)')
		elif p1=="p49":
			clist = course.objects.filter(program='Engineering Physics')																							
		elif p1=="p50":
			clist = course.objects.filter(program='Engineering Physics with M.Tech. in Engineering Physics with specialization in Nano Science')	
		elif p1=="p51":
			clist = course.objects.filter(program='Engineering Science')
		elif p1=="p52":
			clist = course.objects.filter(program='Environmental Engineering')
		elif p1=="p53":
			clist = course.objects.filter(program='Exploration Geophysics')
		elif p1=="p54":
			clist = course.objects.filter(program='Geological Technology')
		elif p1=="p55":
			clist = course.objects.filter(program='Geophysical Technology')
		elif p1=="p56":
			clist = course.objects.filter(program='Industrial Chemistry')
		elif p1=="p57":
			clist = course.objects.filter(program='Industrial Engineering')
		elif p1=="p58":
			clist = course.objects.filter(program='Industrial Engineering with M.Tech. in Industrial Engineering and Management')
		elif p1=="p59":
			clist = course.objects.filter(program='Instrumentation Engineering')
		elif p1=="p60":
			clist = course.objects.filter(program='Manufacturing Science and Engineering')
		elif p1=="p61":
			clist = course.objects.filter(program='Manufacturing Science and Engineering with M.Tech. in Industrial Engineering and Management')
		elif p1=="p62":
			clist = course.objects.filter(program='Material Science and Technology')
		elif p1=="p63":
			clist = course.objects.filter(program='Materials Science and Engineering')
		elif p1=="p64":
			clist = course.objects.filter(program='Mathematics and Computing')
		elif p1=="p65":
			clist = course.objects.filter(program='Mathematics and Scientific Computing')
		elif p1=="p66":
			clist = course.objects.filter(program='Mechanical Engineering')
		elif p1=="p67":
			clist = course.objects.filter(program='Mechanical Engineering (Intelligent Manufacturing)')
		elif p1=="p68":
			clist = course.objects.filter(program='Mechanical Engineering (Product design)')
		elif p1=="p69":
			clist = course.objects.filter(program='Mechanical Engineering (Thermal Engineering)')
		elif p1=="p70":
			clist = course.objects.filter(program='Mechanical Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p71":
			clist = course.objects.filter(program='Mechanical Engineering with M.Tech. in Computer Aided Design and Automation')
		elif p1=="p72":
			clist = course.objects.filter(program='Mechanical Engineering with M.Tech. in Computer Integrated Manufacturing')
		elif p1=="p73":
			clist = course.objects.filter(program='Metallurgical and Materials Engineering')
		elif p1=="p74":
			clist = course.objects.filter(program='Metallurgical and Materials Engineering with M.Tech. in Materials Engineering')
		elif p1=="p75":
			clist = course.objects.filter(program='Metallurgical and Materials Engineering with M.Tech. in Metallurgical and Materials Engineering')
		elif p1=="p76":
			clist = course.objects.filter(program='Metallurgical Engineering')
		elif p1=="p77":
			clist = course.objects.filter(program='Metallurgical Engineering and Materials Science')
		elif p1=="p78":
			clist = course.objects.filter(program='Metallurgical Engineering and Materials Science with M.Tech. in Ceramics and Composites')
		elif p1=="p79":
			clist = course.objects.filter(program='Metallurgical Engineering and Materials Science with M.Tech. in Metallurgical Process Engineering')
		elif p1=="p80":
			clist = course.objects.filter(program='Mineral Engineering')
		elif p1=="p81":
			clist = course.objects.filter(program='Mineral Engineering with M.Tech in Mineral Engineering')
		elif p1=="p82":
			clist = course.objects.filter(program='Mineral Engineering with MBA')
		elif p1=="p83":
			clist = course.objects.filter(program='Mining Engineering')
		elif p1=="p84":
			clist = course.objects.filter(program='Mining Engineering with M.Tech. in Mining Engineering')
		elif p1=="p85":
			clist = course.objects.filter(program='Mining Engineering with MBA')
		elif p1=="p86":
			clist = course.objects.filter(program='Mining Machinery Engineering')
		elif p1=="p87":
			clist = course.objects.filter(program='Mining Safety Engineering')
		elif p1=="p88":
			clist = course.objects.filter(program='Naval Architecture and Ocean Engineering')
		elif p1=="p89":
			clist = course.objects.filter(program='Naval Architecture and Ocean Engineering with M.Tech in Applied Mechanics in any of the listed specializations')
		elif p1=="p90":
			clist = course.objects.filter(program='Ocean Engineering and Naval Architecture')
		elif p1=="p91":
			clist = course.objects.filter(program='Petroleum Engineering')
		elif p1=="p92":
			clist = course.objects.filter(program='Petroleum Engineering with M.Tech in Petroleum Management')
		elif p1=="p93":
			clist = course.objects.filter(program='Pharmaceutics')
		elif p1=="p94":
			clist = course.objects.filter(program='Physics')
		elif p1=="p95":
			clist = course.objects.filter(program='Polymer Science and Technology')
		elif p1=="p96":
			clist = course.objects.filter(program='Process Engineering with MBA')
		elif p1=="p97":
			clist = course.objects.filter(program='Production and Industrial Engineering')
		elif p1=="p98":
			clist = course.objects.filter(program='Pulp and Paper Engineering')
		elif p1=="p99":
			clist = course.objects.filter(program='Quality Engineering Design and Manufacturing')
		elif p1=="p100":
			clist = course.objects.filter(program='Systems Science')
		elif p1=="p101":
			clist = course.objects.filter(program='Aerospace Engineering')																																																		
		 
		for cor in clist:
			cand=int(r)
			m=""
			if s=="ge":
				colo=int(cor.ge_open)
				colc=int(cor.ge_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="obc":
				colo=int(cor.obc_open)
				colc=int(cor.obc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc":
				colo=int(cor.sc_open)
				colc=int(cor.sc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
			
			if s=="st":
				colo=int(cor.st_open)
				colc=int(cor.st_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="ge_pd":
				colo=int(cor.gepd_open)
				colc=int(cor.gepd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="obc_pd":
				colo=int(cor.obcpd_open)
				colc=int(cor.obcpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc_pd":
				colo=int(cor.scpd_open)
				colc=int(cor.scpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="st_pd":
				colo=int(cor.stpd_open)
				colc=int(cor.stpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor) 

	return render(request,'home.html',{'list':list1})

def enter_rank(request):
	college=request.GET['insti']
	p1 = request.GET['prog']
	r = request.GET['rank']
	s=request.GET['cate'] 
	if(r==""):
		return render(request,'anyrank.html',{'mess':"You didnt enter any rank"})
	if(college!="none"):
		list1=[]
		if college=="i1":
			clist = course.objects.filter(code__icontains='A')
		elif college=="i2":
			clist = course.objects.filter(code__icontains='B')
		elif college=="i3":
			clist = course.objects.filter(code__icontains='C')
		elif college=="i4":
			clist = course.objects.filter(code__icontains='D')
		elif college=="i5":
			clist = course.objects.filter(code__icontains='E')
		elif college=="i6":
			clist = course.objects.filter(code__icontains='G')
		elif college=="i7":
			clist = course.objects.filter(code__icontains='H')
		elif college=="i8":
			clist = course.objects.filter(code__icontains='J')
		elif college=="i9":
			clist = course.objects.filter(code__icontains='K')
		elif college=="i10":
			clist = course.objects.filter(code__icontains='M')
		elif college=="i11":
			clist = course.objects.filter(code__icontains='N')
		elif college=="i12":
			clist = course.objects.filter(code__icontains='P')
		elif college=="i13":
			clist = course.objects.filter(code__icontains='R')
		elif college=="i14":
			clist = course.objects.filter(code__icontains='S')
		elif college=="i15":
			clist = course.objects.filter(code__icontains='U')
		elif college=="i16":
			clist = course.objects.filter(code__icontains='V')
		elif college=="i17":
			clist = course.objects.filter(code__icontains='W')
				
		for cor in clist:
			cand=int(r)
			m=""
			if s=="ge":
				colo=int(cor.ge_open)
				colc=int(cor.ge_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="obc":
				colo=int(cor.obc_open)
				colc=int(cor.obc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc":
				colo=int(cor.sc_open)
				colc=int(cor.sc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
			
			if s=="st":
				colo=int(cor.st_open)
				colc=int(cor.st_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="ge_pd":
				colo=int(cor.gepd_open)
				colc=int(cor.gepd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="obc_pd":
				colo=int(cor.obcpd_open)
				colc=int(cor.obcpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc_pd":
				colo=int(cor.scpd_open)
				colc=int(cor.scpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="st_pd":
				colo=int(cor.stpd_open)
				colc=int(cor.stpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

	else:
		list1 = []
		if p1=="p3":
			clist = course.objects.filter(program='Aerospace Engineering')
		elif p1=="p4":
			clist = course.objects.filter(program='Aerospace Engineering with M.Tech. in Applied Mechanics with specialization in Biomedical Engineering')
		elif p1=="p5":
			clist = course.objects.filter(program='Agricultural and Food Engineering')
		elif p1=="p6":
			clist = course.objects.filter(program='Agricultural and Food Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p7":
			clist = course.objects.filter(program='Applied Geology')
		elif p1=="p8":
			clist = course.objects.filter(program='Applied Geophysics')
		elif p1=="p9":
			clist = course.objects.filter(program='Applied Mathematics')
		elif p1=="p10":
			clist = course.objects.filter(program='Architecture')
		elif p1=="p11":
			clist = course.objects.filter(program='Biochemical Engineering')
		elif p1=="p12":
			clist = course.objects.filter(program='Biochemical Engineering and Biotechnology')
		elif p1=="p13":
			clist = course.objects.filter(program='Bioengineering with M.Tech in Biomedical Technology')
		elif p1=="p14":
			clist = course.objects.filter(program='Biological Engineering')
		elif p1=="p15":
			clist = course.objects.filter(program='Biological Sciences')
		elif p1=="p16":
			clist = course.objects.filter(program='Biological Sciences and Bioengineering')
		elif p1=="p17":
			clist = course.objects.filter(program='Biotechnology')
		elif p1=="p18":
			clist = course.objects.filter(program='Biotechnology and Biochemical Engineering')
		elif p1=="p19":
			clist = course.objects.filter(program='Ceramic Engineering')
		elif p1=="p20":
			clist = course.objects.filter(program='Chemical Engineering')
		elif p1=="p21":
			clist = course.objects.filter(program='Chemical Engineering with M.Tech. in Hydrocarbon Engineering')
		elif p1=="p22":
			clist = course.objects.filter(program='Chemical Science and Technology')
		elif p1=="p23":
			clist = course.objects.filter(program='Chemistry')
		elif p1=="p24":
			clist = course.objects.filter(program='Civil Engineering')
		elif p1=="p25":
			clist = course.objects.filter(program='Civil Engineering (Infrastructural Civil Engineering)')
		elif p1=="p26":
			clist = course.objects.filter(program='Civil Engineering with any of the listed specialization')
		elif p1=="p27":
			clist = course.objects.filter(program='Civil Engineering with M.Tech. in Applied Mechanics in any of the listed specializations')
		elif p1=="p28":
			clist = course.objects.filter(program='Civil Engineering with M.Tech. in Structural Engineering')
		elif p1=="p29":
			clist = course.objects.filter(program='Computer Science and Engineering')
		elif p1=="p30":
			clist = course.objects.filter(program='Design')
		elif p1=="p31":
			clist = course.objects.filter(program='Economics')
		elif p1=="p32":
			clist = course.objects.filter(program='Electrical Engineering')
		elif p1=="p33":
			clist = course.objects.filter(program='Electrical Engineering (Power)')
		elif p1=="p34":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech in Applied Mechanics with specialization in Biomedical Engineering')
		elif p1=="p35":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p36":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Communications and Signal Processing')
		elif p1=="p37":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Information and Communication Technology')
		elif p1=="p38":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Microelectronics')
		elif p1=="p39":
			clist = course.objects.filter(program='Electrical Engineering with M.Tech. in Power Electronics')
		elif p1=="p40":
			clist = course.objects.filter(program='Electronics and Communication Engineering')
		elif p1=="p41":
			clist = course.objects.filter(program='Electronics and Communication Engineering with M.Tech. in Wireless Communication')
		elif p1=="p42":
			clist = course.objects.filter(program='Electronics and Electrical Communication Engineering')
		elif p1=="p43":
			clist = course.objects.filter(program='Electronics and Electrical Communication Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p44":
			clist = course.objects.filter(program='Electronics and Electrical Engineering')
		elif p1=="p45":
			clist = course.objects.filter(program='Electronics Engineering')
		elif p1=="p46":
			clist = course.objects.filter(program='Energy Engineering with M.Tech. in Energy Systems Engineering')
		elif p1=="p47":
			clist = course.objects.filter(program='Engineering Design (Automotive Engineering)')
		elif p1=="p48":
			clist = course.objects.filter(program='Engineering Design (Biomedical Design)')
		elif p1=="p49":
			clist = course.objects.filter(program='Engineering Physics')																							
		elif p1=="p50":
			clist = course.objects.filter(program='Engineering Physics with M.Tech. in Engineering Physics with specialization in Nano Science')	
		elif p1=="p51":
			clist = course.objects.filter(program='Engineering Science')
		elif p1=="p52":
			clist = course.objects.filter(program='Environmental Engineering')
		elif p1=="p53":
			clist = course.objects.filter(program='Exploration Geophysics')
		elif p1=="p54":
			clist = course.objects.filter(program='Geological Technology')
		elif p1=="p55":
			clist = course.objects.filter(program='Geophysical Technology')
		elif p1=="p56":
			clist = course.objects.filter(program='Industrial Chemistry')
		elif p1=="p57":
			clist = course.objects.filter(program='Industrial Engineering')
		elif p1=="p58":
			clist = course.objects.filter(program='Industrial Engineering with M.Tech. in Industrial Engineering and Management')
		elif p1=="p59":
			clist = course.objects.filter(program='Instrumentation Engineering')
		elif p1=="p60":
			clist = course.objects.filter(program='Manufacturing Science and Engineering')
		elif p1=="p61":
			clist = course.objects.filter(program='Manufacturing Science and Engineering with M.Tech. in Industrial Engineering and Management')
		elif p1=="p62":
			clist = course.objects.filter(program='Material Science and Technology')
		elif p1=="p63":
			clist = course.objects.filter(program='Materials Science and Engineering')
		elif p1=="p64":
			clist = course.objects.filter(program='Mathematics and Computing')
		elif p1=="p65":
			clist = course.objects.filter(program='Mathematics and Scientific Computing')
		elif p1=="p66":
			clist = course.objects.filter(program='Mechanical Engineering')
		elif p1=="p67":
			clist = course.objects.filter(program='Mechanical Engineering (Intelligent Manufacturing)')
		elif p1=="p68":
			clist = course.objects.filter(program='Mechanical Engineering (Product design)')
		elif p1=="p69":
			clist = course.objects.filter(program='Mechanical Engineering (Thermal Engineering)')
		elif p1=="p70":
			clist = course.objects.filter(program='Mechanical Engineering with M.Tech. in any of the listed specializations')
		elif p1=="p71":
			clist = course.objects.filter(program='Mechanical Engineering with M.Tech. in Computer Aided Design and Automation')
		elif p1=="p72":
			clist = course.objects.filter(program='Mechanical Engineering with M.Tech. in Computer Integrated Manufacturing')
		elif p1=="p73":
			clist = course.objects.filter(program='Metallurgical and Materials Engineering')
		elif p1=="p74":
			clist = course.objects.filter(program='Metallurgical and Materials Engineering with M.Tech. in Materials Engineering')
		elif p1=="p75":
			clist = course.objects.filter(program='Metallurgical and Materials Engineering with M.Tech. in Metallurgical and Materials Engineering')
		elif p1=="p76":
			clist = course.objects.filter(program='Metallurgical Engineering')
		elif p1=="p77":
			clist = course.objects.filter(program='Metallurgical Engineering and Materials Science')
		elif p1=="p78":
			clist = course.objects.filter(program='Metallurgical Engineering and Materials Science with M.Tech. in Ceramics and Composites')
		elif p1=="p79":
			clist = course.objects.filter(program='Metallurgical Engineering and Materials Science with M.Tech. in Metallurgical Process Engineering')
		elif p1=="p80":
			clist = course.objects.filter(program='Mineral Engineering')
		elif p1=="p81":
			clist = course.objects.filter(program='Mineral Engineering with M.Tech in Mineral Engineering')
		elif p1=="p82":
			clist = course.objects.filter(program='Mineral Engineering with MBA')
		elif p1=="p83":
			clist = course.objects.filter(program='Mining Engineering')
		elif p1=="p84":
			clist = course.objects.filter(program='Mining Engineering with M.Tech. in Mining Engineering')
		elif p1=="p85":
			clist = course.objects.filter(program='Mining Engineering with MBA')
		elif p1=="p86":
			clist = course.objects.filter(program='Mining Machinery Engineering')
		elif p1=="p87":
			clist = course.objects.filter(program='Mining Safety Engineering')
		elif p1=="p88":
			clist = course.objects.filter(program='Naval Architecture and Ocean Engineering')
		elif p1=="p89":
			clist = course.objects.filter(program='Naval Architecture and Ocean Engineering with M.Tech in Applied Mechanics in any of the listed specializations')
		elif p1=="p90":
			clist = course.objects.filter(program='Ocean Engineering and Naval Architecture')
		elif p1=="p91":
			clist = course.objects.filter(program='Petroleum Engineering')
		elif p1=="p92":
			clist = course.objects.filter(program='Petroleum Engineering with M.Tech in Petroleum Management')
		elif p1=="p93":
			clist = course.objects.filter(program='Pharmaceutics')
		elif p1=="p94":
			clist = course.objects.filter(program='Physics')
		elif p1=="p95":
			clist = course.objects.filter(program='Polymer Science and Technology')
		elif p1=="p96":
			clist = course.objects.filter(program='Process Engineering with MBA')
		elif p1=="p97":
			clist = course.objects.filter(program='Production and Industrial Engineering')
		elif p1=="p98":
			clist = course.objects.filter(program='Pulp and Paper Engineering')
		elif p1=="p99":
			clist = course.objects.filter(program='Quality Engineering Design and Manufacturing')
		elif p1=="p100":
			clist = course.objects.filter(program='Systems Science')
		elif p1=="p101":
			clist = course.objects.filter(program='Aerospace Engineering')																																																		
		s=request.GET['cate'] 
		for cor in clist:
			cand=int(r)
			m=""
			if s=="ge":
				colo=int(cor.ge_open)
				colc=int(cor.ge_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="obc":
				colo=int(cor.obc_open)
				colc=int(cor.obc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc":
				colo=int(cor.sc_open)
				colc=int(cor.sc_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
			
			if s=="st":
				colo=int(cor.st_open)
				colc=int(cor.st_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="ge_pd":
				colo=int(cor.gepd_open)
				colc=int(cor.gepd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="obc_pd":
				colo=int(cor.obcpd_open)
				colc=int(cor.obcpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)

			if s=="sc_pd":
				colo=int(cor.scpd_open)
				colc=int(cor.scpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor)
						
			if s=="st_pd":
				colo=int(cor.stpd_open)
				colc=int(cor.stpd_close)
				if cand <= colc:
					if not cor in list1:
						list1.append(cor) 

	return render(request,'anyrank.html',{'list':list1})
	