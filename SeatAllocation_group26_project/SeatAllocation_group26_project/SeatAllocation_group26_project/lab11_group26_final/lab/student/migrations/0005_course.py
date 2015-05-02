# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0004_student_dob'),
    ]

    operations = [
        migrations.CreateModel(
            name='course',
            fields=[
                ('id', models.AutoField(primary_key=True, auto_created=True, serialize=False, verbose_name='ID')),
                ('institute', models.CharField(default='', max_length=100)),
                ('program', models.CharField(default='', max_length=100)),
                ('code', models.CharField(default='', max_length=100)),
                ('ge_open', models.CharField(default='', max_length=100)),
                ('ge_close', models.CharField(default='', max_length=100)),
                ('obc_open', models.CharField(default='', max_length=100)),
                ('obc_close', models.CharField(default='', max_length=100)),
                ('sc_open', models.CharField(default='', max_length=100)),
                ('sc_close', models.CharField(default='', max_length=100)),
                ('st_open', models.CharField(default='', max_length=100)),
                ('st_close', models.CharField(default='', max_length=100)),
                ('gepd_open', models.CharField(default='', max_length=100)),
                ('gepd_close', models.CharField(default='', max_length=100)),
                ('obcpd_open', models.CharField(default='', max_length=100)),
                ('obcpd_close', models.CharField(default='', max_length=100)),
                ('scpd_open', models.CharField(default='', max_length=100)),
                ('scpd_close', models.CharField(default='', max_length=100)),
                ('stpd_open', models.CharField(default='', max_length=100)),
                ('stpd_close', models.CharField(default='', max_length=100)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
