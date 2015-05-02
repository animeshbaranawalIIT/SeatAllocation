# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0007_student_category'),
    ]

    operations = [
        migrations.AddField(
            model_name='student',
            name='pref',
            field=models.CharField(max_length=1000000000, default=''),
            preserve_default=True,
        ),
    ]
