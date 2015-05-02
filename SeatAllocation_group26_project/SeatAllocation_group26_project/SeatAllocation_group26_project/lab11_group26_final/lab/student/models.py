from django.db import models

# Create your models here.

class student(models.Model):
	roll_no=models.CharField(max_length=100,default="")
	password=models.CharField(max_length=10,default="")
	dob = models.CharField(max_length=8,default="")
	gender = models.CharField(max_length=10,default="")
	gerank = models.CharField(max_length=10,default="")
	obcrank = models.CharField(max_length=10,default="")
	scrank = models.CharField(max_length=10,default="")
	strank = models.CharField(max_length=10,default="")
	gepdrank = models.CharField(max_length=10,default="")
	obcpdrank = models.CharField(max_length=10,default="")
	scpdrank = models.CharField(max_length=10,default="")
	stpdrank= models.CharField(max_length=10,default="")
	category = models.CharField(max_length=6,default="")
	pref = models.CharField(max_length=1000000000,default="")

	def __str__(self):
		return self.pref

class course(models.Model):
	institute = models.CharField(max_length=100,default="")
	program = models.CharField(max_length=100,default="")
	code = models.CharField(max_length=100,default="")
	ge_open= models.CharField(max_length=100,default="")
	ge_close= models.CharField(max_length=100,default="")
	obc_open= models.CharField(max_length=100,default="")
	obc_close= models.CharField(max_length=100,default="")
	sc_open= models.CharField(max_length=100,default="")
	sc_close= models.CharField(max_length=100,default="")
	st_open= models.CharField(max_length=100,default="")
	st_close= models.CharField(max_length=100,default="")
	gepd_open= models.CharField(max_length=100,default="")
	gepd_close= models.CharField(max_length=100,default="")
	obcpd_open= models.CharField(max_length=100,default="")
	obcpd_close= models.CharField(max_length=100,default="")
	scpd_open= models.CharField(max_length=100,default="")
	scpd_close= models.CharField(max_length=100,default="")
	stpd_open= models.CharField(max_length=100,default="")
	stpd_close= models.CharField(max_length=100,default="")

	def __str__(self):
		return self.code+"\n"
	