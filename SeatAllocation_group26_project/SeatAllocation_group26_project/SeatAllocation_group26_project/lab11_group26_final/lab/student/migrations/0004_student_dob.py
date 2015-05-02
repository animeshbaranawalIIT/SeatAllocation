# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0003_auto_20141030_0231'),
    ]

    operations = [
        migrations.AddField(
            model_name='student',
            name='dob',
            field=models.CharField(default='', max_length=8),
            preserve_default=True,
        ),
    ]
