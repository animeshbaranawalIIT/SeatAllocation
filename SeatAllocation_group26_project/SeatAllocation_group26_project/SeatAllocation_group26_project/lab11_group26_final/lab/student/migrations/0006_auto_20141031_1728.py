# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0005_course'),
    ]

    operations = [
        migrations.AddField(
            model_name='student',
            name='gender',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='gepdrank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='gerank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='obcpdrank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='obcrank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='scpdrank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='scrank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='stpdrank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='student',
            name='strank',
            field=models.CharField(default='', max_length=10),
            preserve_default=True,
        ),
    ]
